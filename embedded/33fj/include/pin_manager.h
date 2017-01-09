/*
 * File:   pin_manager.h
 * Author: jimmy
 *
 * Created on November 25, 2016, 12:23 PM
 */

#ifndef PIN_MANAGER_H
#define	PIN_MANAGER_H

#ifdef	__cplusplus
extern "C" {
#endif
/********************** I/O Definition ****************************************/
/** 預留IO連接 */
#define RP19                    LATCbits.LATC3
#define PORT_RP19               PORTCbits.RC3
#define DIR_RP19                TRISCbits.TRISC3
#define RP19_SetHigh()          do { LATCbits.LATC3 = HI; } while(0)
#define RP19_SetLow()           do { LATCbits.LATC3 = LOW;} while(0)
#define RP19_Toggle()           do { LATCbits.LATC3 = ~LATCbits.LATC3; } while(0)
#define RP19_GetValue()         PORTCbits.RC3
#define RP19_SetDigitalInput()  do { TRISCbits.TRISC3 = INPUT; } while(0)
#define RP19_SetDigitalOutput() do { TRISCbits.TRISC3 = OUTPUT; } while(0)

//#define RP20                    LATCbits.LATC4
//#define PORT_RP20               PORTCbits.RC4
//#define DIR_RP20                TRISCbits.TRISC4
//#define RP20_SetHigh()          do { LATCbits.LATC4 = HI; } while(0)
//#define RP20_SetLow()           do { LATCbits.LATC4 = LOW;} while(0)
//#define RP20_Toggle()           do { LATCbits.LATC4 = ~LATCbits.LATC4; } while(0)
//#define RP20_GetValue()         PORTCbits.RC4
//#define RP20_SetDigitalInput()  do { TRISCbits.TRISC4 = INPUT; } while(0)
//#define RP20_SetDigitalOutput() do { TRISCbits.TRISC4 = OUTPUT; } while(0)

/** 印表機連接 */
/* [OUTPUT] SW_PWR */
#define SW_PWR                     LATAbits.LATA7
#define PORT_SW_PWR                PORTAbits.RA7
#define DIR_SW_PWR                 TRISAbits.TRISA7
#define SW_PWR_SetHigh()           do { LATAbits.LATA7 = HI; } while(0)
#define SW_PWR_SetLow()            do { LATAbits.LATA7 = LOW;} while(0)
#define SW_PWR_Toggle()            do { LATAbits.LATA7 = ~LATAbits.LATA7; } while(0)
#define SW_PWR_GetValue()          PORTAbits.RA7
#define SW_PWR_SetDigitalInput()   do { TRISAbits.TRISA7 = INPUT; } while(0)
#define SW_PWR_SetDigitalOutput()  do { TRISAbits.TRISA7 = OUTPUT; } while(0)
/* [OUTPUT] SW_PAPER */
#define SW_PAPER                     LATAbits.LATA8
#define PORT_SW_PAPER                PORTAbits.RA8
#define DIR_SW_PAPER                 TRISAbits.TRISA8
#define SW_PAPER_SetHigh()           do { LATAbits.LATA8 = HI; } while(0)
#define SW_PAPER_SetLow()            do { LATAbits.LATA8 = LOW;} while(0)
#define SW_PAPER_Toggle()            do { LATAbits.LATA8 = ~LATAbits.LATA8; } while(0)
#define SW_PAPER_GetValue()          PORTAbits.RA8
#define SW_PAPER_SetDigitalInput()   do { TRISAbits.TRISA8 = INPUT; } while(0)
#define SW_PAPER_SetDigitalOutput()  do { TRISAbits.TRISA8 = OUTPUT; } while(0)
/* [OUTPUT] SW_INK */
#define SW_INK                     LATAbits.LATA9
#define PORT_SW_INK                PORTAbits.RA9
#define DIR_SW_INK                 TRISAbits.TRISA9
#define SW_INK_SetHigh()           do { LATAbits.LATA9 = HI; } while(0)
#define SW_INK_SetLow()            do { LATAbits.LATA9 = LOW;} while(0)
#define SW_INK_Toggle()            do { LATAbits.LATA9 = ~LATAbits.LATA9; } while(0)
#define SW_INK_GetValue()          PORTAbits.RA9
#define SW_INK_SetDigitalInput()   do { TRISAbits.TRISA9 = INPUT; } while(0)
#define SW_INK_SetDigitalOutput()  do { TRISAbits.TRISA9 = OUTPUT; } while(0)
/* [OUTPUT] SW_DELETE */
#define SW_DELETE                     LATAbits.LATA10
#define PORT_SW_DELETE                PORTAbits.RA10
#define DIR_SW_DELETE                 TRISAbits.TRISA10
#define SW_DELETE_SetHigh()           do { LATAbits.LATA10 = HI; } while(0)
#define SW_DELETE_SetLow()            do { LATAbits.LATA10 = LOW;} while(0)
#define SW_DELETE_Toggle()            do { LATAbits.LATA10 = ~LATAbits.LATA10; } while(0)
#define SW_DELETE_GetValue()          PORTAbits.RA10
#define SW_DELETE_SetDigitalInput()   do { TRISAbits.TRISA10 = INPUT; } while(0)
#define SW_DELETE_SetDigitalOutput()  do { TRISAbits.TRISA10 = OUTPUT; } while(0)
/* [INPUT] LED_PWR */
#define	LED_PWR                 LATBbits.LATB0
#define PORT_LED_PWR            PORTBbits.RB0
#define	DIR_LED_PWR             TRISBbits.TRISB0
#define LED_PWR_GetValue()         PORTBbits.RB0
#define LED_PWR_SetDigitalInput()  do { TRISBbits.TRISB0 = INPUT; } while(0)
#define LED_PWR_SetDigitalOutput() do { TRISBbits.TRISB0 = OUTPUT; } while(0)
/* [INPUT] LED_PAPER */
#define	LED_PAPER                 LATBbits.LATB1
#define PORT_LED_PAPER            PORTBbits.RB1
#define	DIR_LED_PAPER             TRISBbits.TRISB1
#define LED_PAPER_GetValue()         PORTBbits.RB1
#define LED_PAPER_SetDigitalInput()  do { TRISBbits.TRISB1 = INPUT; } while(0)
#define LED_PAPER_SetDigitalOutput() do { TRISBbits.TRISB1 = OUTPUT; } while(0)
/* [INPUT] LED_INK */
#define	LED_INK                 LATBbits.LATB2
#define PORT_LED_INK            PORTBbits.RB2
#define	DIR_LED_INK             TRISBbits.TRISB2
#define LED_INK_GetValue()         PORTBbits.RB2
#define LED_INK_SetDigitalInput()  do { TRISBbits.TRISB2 = INPUT; } while(0)
#define LED_INK_SetDigitalOutput() do { TRISBbits.TRISB2 = OUTPUT; } while(0)
/* [INPUT] FEED_IN 進紙開關輸入 */
#define	FEED_IN                 LATBbits.LATB3
#define PORT_FEED_IN            PORTBbits.RB3
#define	DIR_FEED_IN             TRISBbits.TRISB3
#define FEED_IN_GetValue()         PORTBbits.RB3
#define FEED_IN_SetDigitalInput()  do { TRISBbits.TRISB3 = INPUT; } while(0)
#define FEED_IN_SetDigitalOutput() do { TRISBbits.TRISB3 = OUTPUT; } while(0)
/* [OUTPUT] FEED_OUT 進紙開關輸出 */
#define FEED_OUT                LATAbits.LATA0
#define PORT_FEED_OUT           PORTAbits.RA0
#define	DIR_FEED_OUT            TRISAbits.TRISA0
#define FEED_OUT_SetHigh()           do { LATAbits.LATA0 = HI; } while(0)
#define FEED_OUT_SetLow()            do { LATAbits.LATA0 = LOW;} while(0)
#define FEED_OUT_Toggle()            do { LATAbits.LATA0 = ~LATAbits.LATA0; } while(0)
#define FEED_OUT_GetValue()         PORTAbits.RA0
#define FEED_OUT_SetDigitalInput()  do { TRISAbits.TRISA0 = INPUT; } while(0)
#define FEED_OUT_SetDigitalOutput() do { TRISAbits.TRISA0 = OUTPUT; } while(0)

/* [INPUT] SWITCHB代替定義B2 B開關(有關墨車) */
#define SWITCHB                 LATAbits.LATA1
#define PORT_SWITCHB            PORTAbits.RA1
#define	DIR_SWITCHB             TRISAbits.TRISA1

/* [INPUT] SWITCHC代替定義B3 C開關(有關墨車) */
#define SWITCHC                 LATAbits.LATA4
#define PORT_SWITCHC            PORTAbits.RA4
#define	DIR_SWITCHC             TRISAbits.TRISA4

/** 步進連接 */
/* [OUTPUT] STEP_PULSE */
#define	STEP_PULSE             LATBbits.LATB14
#define PORT_STEP_PULSE        PORTBbits.RB14
#define	DIR_STEP_PULSE         TRISBbits.TRISB14
#define STEP_PULSE_SetHigh()           do { LATBbits.LATB14 = HI; } while(0)
#define STEP_PULSE_SetLow()            do { LATBbits.LATB14 = LOW;} while(0)
#define STEP_PULSE_Toggle()            do { LATBbits.LATB14 = ~LATBbits.LATB14; } while(0)
#define STEP_PULSE_GetValue()         PORTBbits.RB14
#define STEP_PULSE_SetDigitalInput()  do { TRISBbits.TRISB14 = INPUT; } while(0)
#define STEP_PULSE_SetDigitalOutput() do { TRISBbits.TRISB14 = OUTPUT; } while(0)

/* [OUTPUT] STEP_DIRECTION */
#define	STEP_DIRECTION         LATBbits.LATB15
#define PORT_STEP_DIRECTION    PORTBbits.RB15
#define	DIR_STEP_DIRECTION     TRISBbits.TRISB15
#define STEP_DIRECTION_SetHigh()           do { LATBbits.LATB15 = HI; } while(0)
#define STEP_DIRECTION_SetLow()            do { LATBbits.LATB15 = LOW;} while(0)
#define STEP_DIRECTION_Toggle()            do { LATBbits.LATB15 = ~LATBbits.LATB15; } while(0)
#define STEP_DIRECTION_GetValue()         PORTBbits.RB15
#define STEP_DIRECTION_SetDigitalInput()  do { TRISBbits.TRISB15 = INPUT; } while(0)
#define STEP_DIRECTION_SetDigitalOutput() do { TRISBbits.TRISB15 = OUTPUT; } while(0)

/** 滾粉馬達 */
/* [OUTPUT] ROLLER_DIRECTION */
#define	ROLLER_DIRECTION         LATCbits.LATC5
#define PORT_ROLLER_DIRECTION    PORTCbits.RC5
#define	DIR_ROLLER_DIRECTION     TRISCbits.TRISC5
#define ROLLER_DIRECTION_SetHigh()           do { LATCbits.LATC5 = HI; } while(0)
#define ROLLER_DIRECTION_SetLow()            do { LATCbits.LATC5 = LOW;} while(0)
#define ROLLER_DIRECTION_Toggle()            do { LATCbits.LATC5 = ~LATBbits.LATC15; } while(0)
#define ROLLER_DIRECTION_GetValue()         PORTCbits.RC5
#define ROLLER_DIRECTION_SetDigitalInput()  do { TRISCbits.TRISC5 = INPUT; } while(0)
#define ROLLER_DIRECTION_SetDigitalOutput() do { TRISCbits.TRISC5 = OUTPUT; } while(0)

/* [OUTPUT] ROLLER_PULSE */
#define	ROLLER_PULSE         LATCbits.LATC4
#define PORT_ROLLER_PULSE    PORTCbits.RC4
#define	DIR_ROLLER_PULSE     TRISCbits.TRISC4
#define ROLLER_PULSE_SetHigh()           do { LATCbits.LATC4 = HI; } while(0)
#define ROLLER_PULSE_SetLow()            do { LATCbits.LATC4 = LOW;} while(0)
#define ROLLER_PULSE_Toggle()            do { LATCbits.LATC4 = ~LATBbits.LATC15; } while(0)
#define ROLLER_PULSE_GetValue()         PORTCbits.RC4
#define ROLLER_PULSE_SetDigitalInput()  do { TRISCbits.TRISC4 = INPUT; } while(0)
#define ROLLER_PULSE_SetDigitalOutput() do { TRISCbits.TRISC4 = OUTPUT; } while(0)
/* 滾粉馬達Macro */
#define RollerForward()      do { LATCbits.LATC5 = LOW; } while(0)
#define RollerBackward()     do { LATCbits.LATC5 = HI; } while(0)
//#define RollerStop()         do { LATCbits.LATC1 = HI; LATCbits.LATC2 = HI; } while(0)
///* [OUTPUT] L298PEN */
//#define L298PEN               LATCbits.LATC0
//#define PORT_L298PEN          PORTCbits.RC0
//#define DIR_L298PEN           TRISCbits.TRISC0
//#define L298PEN_SetHigh()           do { LATCbits.LATC0 = HI; } while(0)
//#define L298PEN_SetLow()            do { LATCbits.LATC0 = LOW;} while(0)
//#define L298PEN_Toggle()            do { LATCbits.LATC0 = ~LATCbits.LATC0; } while(0)
//#define L298PEN_GetValue()          PORTCbits.RC0
//#define L298PEN_SetDigitalInput()   do { TRISCbits.TRISC0 = INPUT; } while(0)
//#define L298PEN_SetDigitalOutput()  do { TRISCbits.TRISC0 = OUTPUT; } while(0)
///* [OUTPUT] L298PIN1 */
//#define L298PIN1               LATCbits.LATC1
//#define PORT_L298PIN1          PORTCbits.RC1
//#define DIR_L298PIN1           TRISCbits.TRISC1
//#define L298PIN1_SetHigh()           do { LATCbits.LATC1 = HI; } while(0)
//#define L298PIN1_SetLow()            do { LATCbits.LATC1 = LOW;} while(0)
//#define L298PIN1_Toggle()            do { LATCbits.LATC1 = ~LATCbits.LATC1; } while(0)
//#define L298PIN1_GetValue()          PORTCbits.RC1
//#define L298PIN1_SetDigitalInput()   do { TRISCbits.TRISC1 = INPUT; } while(0)
//#define L298PIN1_SetDigitalOutput()  do { TRISCbits.TRISC1 = OUTPUT; } while(0)
///* [OUTPUT] L298PIN2 */
//#define L298PIN2             LATCbits.LATC2
//#define PORT_L298PIN2        PORTCbits.RC2
//#define DIR_L298PIN2         TRISCbits.TRISC2
//#define L298PIN2_SetHigh()           do { LATCbits.LATC2 = HI; } while(0)
//#define L298PIN2_SetLow()            do { LATCbits.LATC2 = LOW;} while(0)
//#define L298PIN2_Toggle()            do { LATCbits.LATC2 = ~LATCbits.LATC2; } while(0)
//#define L298PIN2_GetValue()          PORTCbits.RC2
//#define L298PIN2_SetDigitalInput()   do { TRISCbits.TRISC2 = INPUT; } while(0)
//#define L298PIN2_SetDigitalOutput()  do { TRISCbits.TRISC2 = OUTPUT; } while(0)

/** 伺服馬達/滾粉機構升降 */
/* [OUTPUT] ServoLeft */
#define ServoLeft              LATCbits.LATC6
#define PORT_ServoLeft         PORTCbits.RC6
#define DIR_ServoLeft          TRISCbits.TRISC6
#define ServoLeft_SetHigh()           do { LATCbits.LATC6 = HI; } while(0)
#define ServoLeft_SetLow()            do { LATCbits.LATC6 = LOW;} while(0)
#define ServoLeft_Toggle()            do { LATCbits.LATC6 = ~LATCbits.LATC6; } while(0)
#define ServoLeft_GetValue()          PORTCbits.RC6
#define ServoLeft_SetDigitalInput()   do { TRISCbits.TRISC6 = INPUT; } while(0)
#define ServoLeft_SetDigitalOutput()  do { TRISCbits.TRISC6 = OUTPUT; } while(0)
/* [OUTPUT] ServoRight */
#define ServoRight              LATCbits.LATC7
#define PORT_ServoRight         PORTCbits.RC7
#define DIR_ServoRight          TRISCbits.TRISC7
#define ServoRight_SetHigh()           do { LATCbits.LATC7 = HI; } while(0)
#define ServoRight_SetLow()            do { LATCbits.LATC7 = LOW;} while(0)
#define ServoRight_Toggle()            do { LATCbits.LATC7 = ~LATCbits.LATC7; } while(0)
#define ServoRight_GetValue()          PORTCbits.RC7
#define ServoRight_SetDigitalInput()   do { TRISCbits.TRISC7 = INPUT; } while(0)
#define ServoRight_SetDigitalOutput()  do { TRISCbits.TRISC7 = OUTPUT; } while(0)

/** 極限開關連接 */
/* [INPUT] 開始列印極限位置 */
#define	LimitSwitchStart        LATBbits.LATB6
#define PORT_LimitSwitchStart   PORTBbits.RB6
#define	DIR_LimitSwitchStart    TRISBbits.TRISB6
#define LimitSwitchStart_GetValue()         PORTBbits.RB6
#define LimitSwitchStart_SetDigitalInput()  do { TRISBbits.TRISB6 = INPUT; } while(0)
#define LimitSwitchStart_SetDigitalOutput() do { TRISBbits.TRISB6 = OUTPUT; } while(0)
/* [INPUT] 結束列印極限位置 */
#define	LimitSwitchEnd          LATBbits.LATB7
#define PORT_LimitSwitchEnd     PORTBbits.RB7
#define	DIR_LimitSwitchEnd      TRISBbits.TRISB7
#define LimitSwitchEnd_GetValue()         PORTBbits.RB7
#define LimitSwitchEnd_SetDigitalInput()  do { TRISBbits.TRISB7 = INPUT; } while(0)
#define LimitSwitchEnd_SetDigitalOutput() do { TRISBbits.TRISB7 = OUTPUT; } while(0)

    void PIN_MANAGER_Initialize(void);

#ifdef	__cplusplus
}
#endif

#endif	/* PIN_MANAGER_H */

