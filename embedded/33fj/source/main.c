/*
 * File:   main.c
 * Author: jimmy ,jim
 * Edit:2016/06/25
 */
#include "constants.h"

//***********************全域變數********************************/
enum I2CInstruction instructionState = IDLE, prevInstructionState = IDLE;
uint16_t DTG_Print_Quantity = 0; //列印張數

int Enable_I2C_Update = 0; //控制在TIMER中斷中的i2c相關程式
bool QEIMotor_Enable = 0;
uint_fast32_t QEI1_Total = 0;
uint_fast16_t QEI1Diff = 0;

uint_fast32_t QEI2_Total = 0;
uint_fast16_t QEI2Diff = 0;

static uint16_t rollerSpeed = 0;

/********************* main program ***************************************************/
int main(void) {
    SystemSetup();
    SYSTEM_ResetCauseHandler();
    BSP_display("System Initialized");
    Init_DTGState();
    /* delay的時間去設定Setup */
    __delay_ms(2000); //讀取LED需要花2秒時間

    if (DTG_PowerOff == ReadDTG_State()) { //讀取LED
        instructionState = PRESS_PWR;
    }
    enum DTG_STATE prevDTGState = DTG_PowerOff;
    enum I2CInstruction prevI2CState = IDLE;
    while (1) {
        static uint8_t dtgStateBusyCount = 0;
        static uint8_t dtgStatePaperJamCount = 0;
        if(prevDTGState != ReadDTG_State()) {
            { // 避免錯誤，需要做印表機重新開機
                if(prevDTGState == DTG_Busy && ReadDTG_State() == DTG_PaperJam) {
                    dtgStateBusyCount++;
                } else if(prevDTGState == DTG_PaperJam && ReadDTG_State() == DTG_Busy) {
                    dtgStatePaperJamCount++;
                } else if(ReadDTG_State() == DTG_NoPaper && instructionState != START_PRINT) {
                    SW_DELETE_SetLow();
                    __delay_ms(2000);
                    SW_DELETE_SetHigh();
                    __delay_ms(2000);
                } else if(ReadDTG_State() == DTG_Error) {
                    SW_PWR_SetLow();
                    __delay_ms(2000);
                    SW_PWR_SetHigh();

                    if(HI == LimitSwitchStart_GetValue()) {
                        setStepMotorSlowRunToStart(true);
                        while(HI == LimitSwitchStart_GetValue());
                        setStepMotorSlowRunToStart(false);
                    }

                    SW_PWR_SetLow();
                    __delay_ms(2000);
                    SW_PWR_SetHigh();
                } else {
                    dtgStateBusyCount = 0;
                    dtgStatePaperJamCount = 0;
                }
                if(dtgStateBusyCount > 2 && dtgStatePaperJamCount > 2) {
                    SW_PWR_SetLow();
                    __delay_ms(2000);
                    SW_PWR_SetHigh();
                }
                __delay_ms(2000);
            }
            prevDTGState = ReadDTG_State();
            BSP_display("DTG:0x%X, busy[%u], jam[%u]", prevDTGState, dtgStateBusyCount, dtgStatePaperJamCount);
        }
        if(DTG_PaperJam == prevDTGState) { // 測試過有幾次可以
            SW_PAPER_SetLow();
            __delay_ms(1500);
            SW_PAPER_SetHigh();
            __delay_ms(2000);
//            if(DTG_Busy == ReadDTG_State() || DTG_PaperJam == ReadDTG_State()) {
//                __delay_ms(1000);
//                SW_DELETE_SetLow();
//                __delay_ms(1500);
//                SW_DELETE_SetHigh();
//                __delay_ms(2000);
//            }
//            if (LOW == FEED_IN_GetValue()) {
//                BSP_display("Feed In = Low");
//                FEED_OUT_SetLow();
//            } else {
//                BSP_display("Feed In = High");
//                FEED_OUT_SetHigh();
//            }
        }
        if(prevI2CState != instructionState) {
            prevI2CState = instructionState;
            BSP_display("INSTRUCTION:0x%X", prevI2CState);
        }
        switch (instructionState) {
            case RESET:
                if(LimitSwitchStart_GetValue()) {//reset之後，設定要往前跑多少，直到碰到結束的極限開關
                    setStepMotorSlowRunToStart(true);
                    while(LimitSwitchStart_GetValue());
                    setStepMotorSlowRunToStart(false);
                }
                if(DTG_PowerOn == ReadDTG_State()) {
                    SW_PWR_SetLow();
                    __delay_ms(1500);
                    SW_PWR_SetHigh();
                    __delay_ms(3000);
                }
                __asm__ volatile("reset");
                break;
            case START_PRINT:
                BSP_display("Start Print");
                DTG_Start();
                break;
            case GO_TO_START:
                if(LimitSwitchStart_GetValue()) {//reset之後，設定要往前跑多少，直到碰到結束的極限開關
                    setStepMotorSlowRunToStart(true);
                    while(LimitSwitchStart_GetValue());
                    setStepMotorSlowRunToStart(false);
                } else {
                    instructionState = IDLE;
                }
                break;
            case GO_TO_END:
                if (LimitSwitchEnd_GetValue()) { //reset之後，設定要往前跑多少，直到碰到結束的極限開關
                    setStepMotorSlowRunToEnd(true);
                    while(LimitSwitchEnd_GetValue());
                    setStepMotorSlowRunToEnd(false);
                } else {
                    instructionState = IDLE;
                }
                break;
            case PRESS_PWR:
                SW_PWR_SetLow();
                __delay_ms(1500);
                SW_PWR_SetHigh();
                __delay_ms(3000);
                instructionState = IDLE;
                break;
            case PRESS_INK:
                SW_INK_SetLow();
                __delay_ms(5000);
                SW_INK_SetHigh();
                __delay_ms(1500);
                instructionState = IDLE;
                break;
            case PRESS_PAPER:
                SW_PAPER_SetLow();
                __delay_ms(1500);
                SW_PAPER_SetHigh();
                __delay_ms(3000);
                instructionState = IDLE;
                break;
            case PRESS_DELETE:
                SW_DELETE_SetLow();
                __delay_ms(1500);
                SW_DELETE_SetHigh();
                __delay_ms(3000);
                instructionState = IDLE;
                break;
            case TURN_OFF_PRINTER:
                if (DTG_PowerOff != ReadDTG_State()) {
                    instructionState = PRESS_PWR;
                }
                break;
            case TURN_ON_PRINTER:
                if (DTG_PowerOff == ReadDTG_State()) {
                    instructionState = PRESS_PWR;
                }
                break;

            case POWDER_START:
                BSP_display("POWDER:Start");
                if (LimitSwitchEnd_GetValue()) { // 歸位到鋪粉初始位置
                    setStepMotorSlowRunToEnd(true);
                    while(HI == LimitSwitchEnd_GetValue());
                    setStepMotorSlowRunToEnd(false);
                }
                BSP_display("POWDER:to LSW  End  Complete, Start Powder Process");
//                { // 舊的鋪粉程序
//                    rollerStop();
//
//                    moveServoDown();
//                    __delay_ms(100);
//
//                    moveRollerForward();
//                    __delay_ms(1000);
//                    servoStop();
//
//                    if(LimitSwitchStart_GetValue()) {
//                        setStepMotorSlowRunToStart(true);
//                        while(HI == LimitSwitchStart_GetValue());
//                        setStepMotorSlowRunToStart(false);
//                    }
//                }
                {
                    rollerStop();
                    moveServoDown();
                    __delay_ms(100);
                    servoStop();
                    moveRollerBackward();
                    __delay_ms(1000);
                    if(LimitSwitchStart_GetValue()) {
                        setStepMotorSlowRunToStart(true);
                        //__delay_ms(10000);  // 走到建構跟供粉中間的時候
                        //moveRollerForward();
                        //moveServoDown();
                        while(HI == LimitSwitchStart_GetValue());
                        setStepMotorSlowRunToStart(false);
                    }
                }
                BSP_display("POWDER:to LSW Start Complete");
                moveServoUp();
                __delay_ms(100);
                servoStop();
                BSP_display("POWDER:Complete");
                rollerStop();
                instructionState = IDLE;
                uint8_t counter = 0;
                while(counter++ < 20) {
                    if(DTG_NoPaper == ReadDTG_State()) {
                        instructionState = START_PRINT;
                        break;
                    } else if(DTG_Busy == ReadDTG_State()) {
                        counter = 0;
                        continue;
                    }
                    __delay_ms(500);
                }
                break;
            case SERVO_UP:
                moveServoUp();
                instructionState = IDLE;
                break;
            case SERVO_DOWN:
                moveServoDown();
                instructionState = IDLE;
                break;
            case SERVO_STOP:
                servoStop();
                instructionState = IDLE;
                break;
            case ROLLER_FORWARD:
                moveRollerForward();
                instructionState = IDLE;
                break;
            case ROLLER_BACKWARD:
                moveRollerBackward();
                instructionState = IDLE;
                break;
            case ROLLER_STOP:
                rollerStop();
                instructionState = IDLE;
                break;
            case SET_ROLLER_SPEED:
                BSP_display("Param:%u", rollerSpeed);
                instructionState = IDLE;
                break;

            case SUCCESS:
                break;
            case FAIL:
                break;

            case STOP_PRINT: // do nothing, 中斷會偵測這個State
            case IDLE:
            default:
                break;
        }
    }
}

void BSP_display(const char* msg, ...) {
//#if __DEBUG
    va_list argptr;
    va_start(argptr, msg);
    vfprintf(stdout, msg, argptr);
    printf("\n");
    va_end(argptr);
//#endif
}

void _ISR_NO_PSV _CNInterrupt(void) { // 這邊只做回歸到初始點/結束點的功能
    if(LOW == LimitSwitchStart_GetValue() && (GO_TO_START == instructionState/* || POWDER_START == instructionState*/)) {
        setStepMotorSlowRunToEnd(false);
        BSP_display("CN:LSW Start Pressed");
        instructionState = IDLE;
    }
    if(LOW == LimitSwitchEnd_GetValue() && (GO_TO_END == instructionState/* || POWDER_START == instructionState*/)) {
        setStepMotorSlowRunToStart(false);
        BSP_display("CN:LSW  End  Pressed");
        instructionState = IDLE;
    }

    IFS1bits.CNIF = 0;
}

void moveServoUp(void) {
    T3CONbits.TON = 0;
    T3CONbits.TCKPS = 0b10; // prescale 1:64
    PR3 = 12500;            // Period = 20mS
    //OC3RS = 1250;           // right 90 deg = 2000uS
    OC3RS = 1562;           // right > 90 deg = 2500uS
    OC3CONbits.OCTSEL = 1;  // Timer3 is selected
    //OC4RS = 625;            // left 90 deg = 1000uS
    //OC4RS = 312;            // left > 90 deg = 500uS
    OC4RS = 200;
    OC4CONbits.OCTSEL = 1;  // Timer3 is selected

    T3CONbits.TON = 1;
    OC3CONbits.OCM = 0b110;
    OC4CONbits.OCM = 0b110;
}

void moveServoDown(void) {
    T3CONbits.TON = 0;
    T3CONbits.TCKPS = 0b10; // prescale 1:64
    PR3 = 12500;            // Period = 20mS
    OC3RS = 937;            // right center = 1500uS
    OC3CONbits.OCTSEL = 1;  // Timer3 is selected
    OC4RS = 937;            // left center = 1500uS
    OC4CONbits.OCTSEL = 1;  // Timer3 is selected
    T3CONbits.TON = 1;
    OC3CONbits.OCM = 0b110;
    OC4CONbits.OCM = 0b110;
}

void servoStop(void) {
    _T3IE = 0;
    _OC4IE = 0;
    T3CON = 0;
    TMR3 = 0;
    OC3CON = 0;
    OC3R = 0;
    OC3RS = 0;
    OC4CON = 0;
    OC4R = 0;
    OC4RS = 0;
}

void moveRollerForward(void) {
    RollerForward();
    _T2IE = 0;
    _OC1IE = 0;
    T2CON = 0;
    T2CONbits.TCKPS = 0b01; // prescale 1:8
    PR2 = rollerSpeed;
    OC1R = 0;
    OC1CONbits.OCM = 0b011; // Toggle Mode
    OC1CONbits.OCTSEL = 0;  // Timer 2 is selected
    T2CONbits.TON = 1;
}

void moveRollerBackward(void) {
    RollerBackward();
    _T2IE = 0;
    _OC1IE = 0;
    T2CON = 0;
    TMR2 = 0;
    T2CONbits.TCKPS = 0b01; // prescale 1:8
    PR2 = rollerSpeed;
    OC1R = 0;
    OC1CONbits.OCM = 0b011; // Toggle Mode
    OC1CONbits.OCTSEL = 0;  // Timer 2 is selected
    T2CONbits.TON = 1;
}

void rollerStop(void) {
//    RollerStop();
//    L298PEN_SetLow();
    T2CON = 0;
    OC1CON = 0;
}

void _ISR_PSV _SI2C1Interrupt(void) {
    static uint8_t dummyAddrToRead = 0;
    static uint8_t instruction = 0;
    static uint8_t returnData = 0;
    static bool flag = false, hlFlag = true, completeFlag = false; // hlFlag = true : High Byte, false : Low Byte
    _SI2C1IF = 0;

    if (I2C1STATbits.D_A == 0 && I2C1STATbits.R_W == 0) { // [W] address
        dummyAddrToRead = I2C1RCV;
    } else if (I2C1STATbits.D_A == 1 && I2C1STATbits.R_W == 0) { // [W] data
        if(flag) {
            uint8_t temp = I2C1RCV;
            if(hlFlag) {
                BSP_display("High: %u", temp);
                rollerSpeed = (uint16_t) temp << 8;
                hlFlag = false;
                instruction = IDLE;
            } else {
                BSP_display("Low: %u", temp);
                rollerSpeed += temp;
                hlFlag = true;
                flag = false;
                completeFlag = true;
                instruction = IDLE;
            }
        } else {
            instruction = I2C1RCV;
        }
        Nop();
        Nop();
        Nop();
        if(READ_STAT == instruction) {
            prevInstructionState = instructionState;
        } else if(SET_ROLLER_SPEED == instruction) {
            flag = true;
        } else if(completeFlag) {
            completeFlag = false;
            instructionState = SET_ROLLER_SPEED;
        } else {
            instructionState = instruction;
        }
        BSP_display("I2C:[W'%u']'%u'", dummyAddrToRead, instruction);
        I2C1CONbits.SCLREL = 1;
    } else if (I2C1STATbits.D_A == 1 && I2C1STATbits.R_W == 1) { // [R]
        dummyAddrToRead = I2C1RCV;
        if(instructionState == READ_STAT) {
            returnData =
                    (LimitSwitchEnd << 7) |
                    (LimitSwitchStart << 6) |
                    (ReadDTG_State() << 3)
                    ;
            instructionState = prevInstructionState;
            prevInstructionState = IDLE;
        } else {
            returnData = NULL; // process rtn data
        }
        BSP_display("I2C:[R'%u']'%u'; prevInstruction'%u'", dummyAddrToRead, returnData, instructionState);
        I2C1TRN = returnData;
        Nop();
        Nop();
        Nop();
        I2C1CONbits.SCLREL = 1;
    } else if (I2C1STATbits.D_A == 0 && I2C1STATbits.R_W == 1) {
        I2C1CONbits.SCLREL = 1;
    }
}
