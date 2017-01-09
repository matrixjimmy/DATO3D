#include "constants.h"

void PIN_MANAGER_Initialize(void) {

    /* 設定AN0-AN5(RA0-RA1,RB0-RB3)為Digtal */
    /* 這樣RA0-RA1,RB0-RB3才能使用 */
    /* 0=analog 1=digtal */
    ADPCFG = 0xffff ;

    /* 設定RA0為 INPUT:進紙馬達訊號 */
    FEED_IN_SetDigitalInput();
    //CNEN1bits.CN1IE = 1;

    /* 設定RA1為 INPUT:LED1 */
    LED_PWR_SetDigitalInput();

    /* 設定RA4為 INPUT:LED2 */
    LED_PAPER_SetDigitalInput();

    /* 設定RB0為 INPUT:LED3 */
    LED_INK_SetDigitalInput();

    /* 設定RB1為 OUTPUT:A開關(有關進紙) */
    FEED_OUT_SetDigitalOutput();
    FEED_OUT_SetHigh();

    /* 設定RB2為 INPUT:B開關(有關墨車) */
    DIR_SWITCHB = INPUT;

    /* 設定RB3為 INPUT:C開關(有關墨車) */
    DIR_SWITCHC = INPUT;

    /* RB6腳位用到RP6上:當LimitSwitchStart (要INPUT)*/
    LimitSwitchStart_SetDigitalInput();
    CNEN2bits.CN24IE = 1;
    CNPU2bits.CN24PUE = 1;

    /* RB7腳位用到RP7上:當LimitSwitchEnd (要INPUT)*/
    LimitSwitchEnd_SetDigitalInput();
    CNEN2bits.CN23IE = 1;
    CNPU2bits.CN23PUE = 1;
    IFS1bits.CNIF = 0;
    IEC1bits.CNIE = 1;
    IPC4bits.CNIP = 3;

    /* RB14為馬達輸出(OUTPUT):PULSE */
    STEP_PULSE_SetDigitalOutput();
    STEP_PULSE_SetLow();

    /* RB15為馬達輸出(OUTPUT):DIRECTION */
    STEP_DIRECTION_SetDigitalOutput();
    STEP_DIRECTION_SetHigh();

    /** 滾粉馬達 */
//    L298PEN_SetDigitalOutput();
//    L298PIN1_SetDigitalOutput();
//    L298PIN2_SetDigitalOutput();
    ROLLER_DIRECTION_SetDigitalOutput();
    ROLLER_PULSE_SetDigitalOutput();

    SW_PWR_SetDigitalOutput();
    SW_PWR_SetHigh();
    SW_PAPER_SetDigitalOutput();
    SW_PAPER_SetHigh();
    SW_INK_SetDigitalOutput();
    SW_INK_SetHigh();
    SW_DELETE_SetDigitalOutput();
    SW_DELETE_SetHigh();

    /** 伺服馬達/滾粉機構升降 */
    ServoLeft_SetDigitalOutput();
    ServoRight_SetDigitalOutput();

    /** 設定TRIG腳(OUTPUT) */
    RP19_SetDigitalOutput();
    //RP20_SetDigitalOutput();
    RP19_SetLow();
    //RP20_SetLow();
/**************** Peripheral Pin Select(PPS) ********************************/
//   Unlock Registers
    __builtin_write_OSCCONL(OSCCON & 0xbf);

    { // 印表機端輸入
        /* 設定(Peripheral Pin Select)(Input) RP4 =  = QEA1 */
        RPINR14bits.QEA1R = 4;

        /* 設定(Peripheral Pin Select)(Input) RP5 =  = QEB1 */
        RPINR14bits.QEB1R = 5;

        /* 設定(Peripheral Pin Select)(Input) RP0 =  = IC8 */
        //RPINR10bits.IC8R = 0;

        /* 設定(Peripheral Pin Select)(Input) RP1 =  = IC2 */
        //RPINR7bits.IC2R = 1;

        /* 設定(Peripheral Pin Select)(Input) RP2 =  = IC7 */
        //RPINR10bits.IC7R = 2;

        /* 設定(Peripheral Pin Select)(Input) RP6 = LimitSwitchStart (B6) = INT1 */
        //RPINR0bits.INT1R = 6;
    }

    { // 滾粉相關輸出
        /* 設定(Peripheral Pin Select)(Output) RP16 = L298N ENA = OC1 */
        //RPOR8bits.RP16R = 0b10010;
        /* 設定(Peripheral Pin Select)(Output) RP20 = Roller Pulse = OC1 */
        RPOR10bits.RP20R = 0b10010;

        /* 設定(Peripheral Pin Select)(Output) RP23 = Stepper Pulse(B14) = OC2 */
        //RPOR11bits.RP23R = 0b10011;

        /* 設定(Peripheral Pin Select)(Output) RP22 = ServoLeft(C6) = OC3 */
        RPOR11bits.RP22R = 0b10100;

        /* 設定(Peripheral Pin Select)(Output) RP23 = ServoRight(C7) = OC4 */
        RPOR11bits.RP23R = 0b10101;

        /* 設定(Peripheral Pin Select)(Input) RP24 = 滾粉Encoder(C8) = QEA2 */
        RPINR16bits.QEA2R = 24;

        /* 設定(Peripheral Pin Select)(Input) RP25 = 滾粉Encoder(C9) = QEB2 */
        RPINR16bits.QEB2R = 25;
    }

    /* Configure UART */
    RPINR18bits.U1RXR = 13;
    RPOR6bits.RP12R = 0x0003;

    //   Lock Registers
    __builtin_write_OSCCONL(OSCCON | 0x40);
}