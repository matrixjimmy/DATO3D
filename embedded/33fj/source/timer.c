#include "constants.h"
#include "DTG.h"
/*********************每50us(0.05ms) 進一次Function******************************/
//  1. 設定Timer FLAG
//  2. 控制步進馬達
//  3. 讀取QEA 數據
void _ISR_PSV _T1Interrupt(void) {
    _T1IF = 0;
    /*********************每50us 進一次Function**********************************/
    // 1.控制步進馬達
    pollingPrinterEncoderToRegister();
    pollingStepperPulse();
}

void _ISR_PSV _T2Interrupt(void) {
    _T2IF = 0;

}

void _ISR_PSV _T3Interrupt(void) {
    _T3IF = 0;
}

/*********************每200ms 進一次Function***********************************/
//  1.讀取 L1800 LED狀態
//  2.偵測按鍵，直接在L1800上控制
void _ISR_PSV _T4Interrupt(void) {
    _T4IF = 0;
    /* 讀取 L1800 3個LED狀態 */
    ReadDTG_State_interrupt();

}

/*********************每0.1ms 進一次Function***********************************/
//  1.步進馬達緩動
//  2.鋪粉時看直流馬達轉速
void _ISR_PSV _T5Interrupt(void) {
    _T5IF = 0;
    MoterRunSlow();
}
