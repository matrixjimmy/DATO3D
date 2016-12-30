#include "constants.h"

extern enum I2CInstruction instructionState;
extern bool QEIMotor_Enable;
extern uint_fast16_t QEI1Diff;
//***********************區域變數********************************/
static bool direction;
uint_fast8_t forwardPulseCount;
uint_fast8_t backwardPulseCount;
static bool isSlowRunToStart = false;
static bool isSlowRunToEnd = false;

void setStepperDirection(int32_t pulse) {
    if (direction) { //方向為正的時候
        STEP_DIRECTION_SetLow();
        forwardPulseCount += pulse;
    } else { //方向為負的時候
        STEP_DIRECTION_SetHigh();
        backwardPulseCount += pulse;
    }
}

void pollingPrinterEncoderToRegister(void) {
    /*********************讀取QEI ***********************************************/
    if (QEI1CONbits.UPDN) { //QEI必定正轉的時候
        Read32bitQEI1_Up();
        if (QEIMotor_Enable && (QEI1Diff < 100)) { //避免差值過大機制
            direction = true;
            setStepperDirection(QEI1Diff); //把要跑的步數輸入到步進馬達
        }
    } else { //QEI必定反轉的時候
        Read32bitQEI1_Down();
        if (QEIMotor_Enable && (QEI1Diff < 100)) { //避免差值過大機制
            direction = false;
            setStepperDirection(QEI1Diff); //把要跑的步數輸入到步進馬達
        }
    }

}

// 控制步進馬達的脈波
// 50us中斷:控制一次

void pollingStepperPulse(void) {
    static bool i = 0;  // 這邊應該會輸出25%的PWM方波
    if (direction) { //控制馬達正轉
        // 如果停止按鍵被按下 或 如果碰到極限開關_結束點
        if ((STOP_PRINT == instructionState)||(LOW == LimitSwitchEnd_GetValue())) {
            forwardPulseCount = 0;
        } else {
            if (forwardPulseCount > 3) {
                if (i) {
                    STEP_PULSE_SetHigh();
                    i = 0;
                } else {
                    STEP_PULSE_SetLow();
                    forwardPulseCount -= 4;
                    i = 1;
                }
            }
        }
    } else { //控制馬達逆轉
        // 如果停止按鍵被按下 或 如果碰到極限開關_起始點
        if ((STOP_PRINT == instructionState)||(LOW == LimitSwitchStart_GetValue())) {
            backwardPulseCount = 0;
        } else {
            if (backwardPulseCount > 3) {
                if (i) {
                    STEP_PULSE_SetHigh();
                    i = 0;
                } else {
                    STEP_PULSE_SetLow();
                    backwardPulseCount -= 4;
                    i = 1;
                }
            }
        }
    }
}

inline void setStepMotorSlowRunToStart(bool set) {
    isSlowRunToStart = set;
}

inline void setStepMotorSlowRunToEnd(bool set) {
    isSlowRunToEnd = set;
}

void MoterRunSlow(void) {
    if(isSlowRunToStart && isSlowRunToEnd) {
        isSlowRunToStart = false;
        isSlowRunToEnd   = false;
        return; // 有錯
    }
    if (isSlowRunToEnd) {
        if (LimitSwitchEnd_GetValue()) { //在平台還沒回到起始點時
            STEP_PULSE ^= 1;
            STEP_DIRECTION_SetLow();
        }
    }
    if (isSlowRunToStart) {
        if (LimitSwitchStart_GetValue()) { //在平台還沒跑到終點時
            STEP_PULSE ^= 1;
            STEP_DIRECTION_SetHigh();
        }
    }
}

