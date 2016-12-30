#include "constants.h"
#include "DTG.h"
//***********************區域變數********************************/
#define LED_State_Off   0
#define LED_State_On    1
#define LED_State_Blink 2

#define LED1_PWR        0
#define LED2_PAPER      1
#define LED3_INK        2

extern enum I2CInstruction instructionState;
extern bool QEIMotor_Enable;

enum DTG_STATE currentState;

struct DTG dtg;

void Init_DTGState(void) {
    currentState = DTG_PowerOff;
    //燈沒亮代表busy
    //燈亮T1數字會+1
    //燈暗T2數字會+1
    dtg.LED_T2[LED1_PWR] = 0;
    dtg.LED_T2[LED2_PAPER] = 0;
    dtg.LED_T2[LED3_INK] = 0;
    dtg.LED_T1[LED1_PWR] = 0;
    dtg.LED_T1[LED2_PAPER] = 0;
    dtg.LED_T1[LED3_INK] = 0;
}

enum DTG_STATE ReadDTG_State(void) {
    return currentState;
}

/**
 * 利用時間中斷(每200ms進函式)計數PORT_LED1 PORT_LED2 PORT_LED3 閃爍的情形
 * - LED 一閃一滅:忙碌
 * - LED亮長過於1600ms 待命
 */
void ReadDTG_State_interrupt() {
    dtg.LED_IO[LED1_PWR] = PORT_LED_PWR;
    dtg.LED_IO[LED2_PAPER] = PORT_LED_PAPER;
    dtg.LED_IO[LED3_INK] = PORT_LED_INK;
    uint8_t i;
    for (i = 0; i < 3; i++) {
        if (dtg.LED_IO[i]) {//當LED 燈亮起來的時後，計數器計時亮了多久
            //分別用T1計時LED亮了多久，T2計時暗了多久
            if (dtg.LED_T1[i] < 8) {
                dtg.LED_T1[i]++; //計時器開始計時，
                dtg.LED_State[i] = LED_State_Blink; //如果LED亮了1500ms以內，代表LED閃爍
            } else { //當計時器計數超過1600ms時
                dtg.LED_State[i] = LED_State_On; //如果LED亮太久，代表LED持續亮著
            }
            dtg.LED_T2[i] = 0; //重置LED暗計數器
        } else { //當LED1 燈暗起來的時後，計數器計時暗了多久
            if (dtg.LED_T2[i] < 8) {
                dtg.LED_T2[i]++;
                dtg.LED_State[i] = LED_State_Blink; //如果LED暗了1600ms以內，2代表LED忙碌
            } else {
                dtg.LED_State[i] = LED_State_Off; //如果LED暗太久，代表關機或錯誤訊息
            }
            dtg.LED_T1[i] = 0; //重置LED亮計數器
        }
    }

    dtg.State[LED1_PWR] = dtg.LED_State[LED1_PWR];
    dtg.State[LED2_PAPER] = dtg.LED_State[LED2_PAPER];
    dtg.State[LED3_INK] = dtg.LED_State[LED3_INK];

    if ((dtg.LED_State[LED1_PWR] == LED_State_Off) &&
            (dtg.LED_State[LED2_PAPER] == LED_State_Off) &&
            (dtg.LED_State[LED3_INK] == LED_State_Off)) {
        currentState = DTG_PowerOff;
    } else if (
            (dtg.LED_State[LED1_PWR] == LED_State_On)&&
            (dtg.LED_State[LED2_PAPER] == LED_State_Off)&&
            (dtg.LED_State[LED3_INK] == LED_State_Off)) {
        currentState = DTG_PowerOn;
    } else if (
            (dtg.LED_State[LED1_PWR] == LED_State_Blink)&&
            (dtg.LED_State[LED2_PAPER] == LED_State_Off)&&
            (dtg.LED_State[LED3_INK] == LED_State_Off)) {
        currentState = DTG_Busy;
    } else if (
            (dtg.LED_State[LED1_PWR] == LED_State_Blink)&&
            (dtg.LED_State[LED2_PAPER] == LED_State_On)&&
            (dtg.LED_State[LED3_INK] == LED_State_Off)) {
        currentState = DTG_NoPaper;
    } else if (
            (dtg.LED_State[LED1_PWR] == LED_State_Blink)&&
            (dtg.LED_State[LED2_PAPER] == LED_State_Blink)&&
            (dtg.LED_State[LED3_INK] == LED_State_Off)) {
        currentState = DTG_PaperJam;
    } else if (
            (dtg.LED_State[LED1_PWR] == LED_State_Off)&&
            (dtg.LED_State[LED2_PAPER] == LED_State_Blink)&&
            (dtg.LED_State[LED3_INK] == LED_State_Blink)) {
        currentState = DTG_Error;
    }
}


//主程式進入列印程序
extern uint_fast8_t forwardPulseCount;
extern uint_fast8_t backwardPulseCount;
void DTG_Start(void) {
    bool PrintDone = 0;
    BSP_display("DTG:Slow to start init");
    setStepMotorSlowRunToStart(true);
    while (LimitSwitchStart_GetValue()); //回到起始點
    setStepMotorSlowRunToStart(false);
    //暫停CN功能
    IEC1bits.CNIE = 0;
    STEP_PULSE_SetLow();
    BSP_display("DTG:Slow to start finish");

    while (currentState != DTG_PowerOn) {
        if (currentState == DTG_NoPaper) {
            BSP_display("DTG:DTG_NoPaper==>Press_Paper");
            SW_PAPER_SetLow();
            __delay_ms(2000);
            SW_PAPER_SetHigh();
            __delay_ms(50);
            break;
        } else if (currentState == DTG_Error) {
            BSP_display("DTG:DTG_Error==>Reset and Restart");
            SW_DELETE_SetLow();
            __delay_ms(2000);
            SW_DELETE_SetHigh();
            __delay_ms(2000);
            SW_PWR_SetLow();
            __delay_ms(2000);
            SW_PWR_SetHigh();
            __delay_ms(2000);
        }
    }
    BSP_display("DTG:Start Print Process");
    do {
        if (STOP_PRINT == instructionState) { //遇到按下停止按鍵時，跳出
            instructionState = PRESS_DELETE;
            break;
        }
        if (LOW == FEED_IN_GetValue()) {
            __delay_ms(100); // delay一些時間
            if (LOW == FEED_IN_GetValue()) { // 再次確認是進紙的訊號
                if (HI == LimitSwitchStart_GetValue()) {
                    break; // 在列印初檢查 平台是否還在起始點
                }
                QEIMotor_Enable = true;
                __delay_ms(800); //總計要delay 800ms ，SWA要LOW下去
                FEED_OUT_SetLow();

                //__delay_ms(1500); //等待1500ms，等待平台回到初始值
                while(QEI1CONbits.UPDN);
                while(!QEI1CONbits.UPDN);
                BSP_display("DTG:fwd:%u, rev:%u", forwardPulseCount, backwardPulseCount);

                while (LimitSwitchEnd_GetValue()) { //等到印到終點
                    if (STOP_PRINT == instructionState) { //遇到按下停止按鍵時，跳出
                        instructionState = PRESS_DELETE;
                        break;
                    }
                    if(DTG_Error == ReadDTG_State()) {
                        QEIMotor_Enable = false;
                        SW_PWR_SetLow();
                        __delay_ms(1500);
                        SW_PWR_SetHigh();
                        __delay_ms(1500);

                        __delay_ms(2000)

                        SW_PWR_SetLow();
                        __delay_ms(1500);
                        SW_PWR_SetHigh();
                        __delay_ms(1500);
                    }
                }

                QEIMotor_Enable = false;
                FEED_OUT_SetHigh();
//                while (dtg.State[LED1_PWR] == LED_State_Blink) { //等待忙碌結束
//                    if (dtg.State[LED2_PAPER] == LED_State_On) {
//                        break;
//                    }
//                }
                PrintDone = true;
                DTG_Print_Quantity++; //紀錄目前列印張數+1
            }
        }
    } while (PrintDone == false);
    BSP_display("DTG:Print Job is Done");
    BSP_display("DTG:fwd:%u, rev:%u", forwardPulseCount, backwardPulseCount);
    forwardPulseCount = 0;
    backwardPulseCount = 0;
    instructionState = IDLE;
    // 恢復CN功能
    IEC1bits.CNIE = 1;
}