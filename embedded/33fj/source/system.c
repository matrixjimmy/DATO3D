/******************************************************************************/
/* Files to Include                                                           */
/******************************************************************************/
#include "constants.h"

/******************************************************************************/
/* System Level Functions                                                     */
/*                                                                            */
/* Custom oscillator configuration funtions, reset source evaluation          */
/* functions, and other non-peripheral microcontroller initialization         */
/* functions get placed in system.c                                           */
/*                                                                            */

/******************************************************************************/

void SystemSetup(void) {
    ConfigureOscillator();
    PIN_MANAGER_Initialize();
    ConfigureTimer1();
    //ConfigureTimer2();
    //ConfigureTimer3();
    ConfigureTimer4();
    ConfigureTimer5();
    ConfigureQEI1();
    ConfigureI2C();
    ConfigureUART1();
}

void ConfigureOscillator(void) {
    PLLFBDbits.PLLDIV = PLL_DIV; //PLL_M
    CLKDIVbits.PLLPRE = PLL_PRE; //PLL_N1
    CLKDIVbits.PLLPOST = PLL_POST; //PLL_N2
    CLKDIVbits.DOZEN = 0;

    /* Wait for PLL to lock, if PLL is used */
    while (OSCCONbits.LOCK != 1);
}

/********************* Timer 1 Configuration **********************************/

/* Tcy = 40.00000 ns
 * Timer 1 Prescaler = 1: 1
 * Timer 1 Clock  40.00000ns * 1   =     40  ns =  0.0400     us
 * Timer 1 Interrupt Period =  1250   * 0.04 us =  0.05   ms  =  50  us
 */
void ConfigureTimer1(void) {
    T1CON = 0;
    T1CONbits.TCKPS = 0b00;
    PR1 = 2000;
    T1CONbits.TON = 1;
    _T1IF = 0;
    _T1IP = 7;
    _T1IE = 1;
}

void ConfigureTimer2(void) {
    // config for OC
    T2CON = 0;
    T2CONbits.TCKPS = 0b11;
    PR2 = 31250;
    T2CONbits.TON = 0;
    _T2IF = 0;
    _T2IP = 5;
    _T2IE = 1;
}

void ConfigureTimer3(void) {
    // config for OC
    T3CON = 0;
    T3CONbits.TCKPS = 0b11;
    PR3 = 31250;
    T3CONbits.TON = 0;
    _T3IF = 0;
    _T3IP = 5;
    _T3IE = 1;
}

//設計200ms進一次中斷

void ConfigureTimer4(void) {
    T4CON = 0;
    T4CONbits.TCKPS = 0b11;
    PR4 = 31250;
    T4CONbits.TON = 1;
    _T4IF = 0;
    _T4IP = 4;
    _T4IE = 1;
}

//設計1ms進一次中斷

void ConfigureTimer5(void) {
    T5CON = 0;
    T5CONbits.TCKPS = 0b11; // 0.1ms // 0b11 -> 0.4ms
    PR5 = 64;
    T5CONbits.TON = 1;
    _T5IF = 0;
    _T5IP = 3;
    _T5IE = 1;
}

void ConfigureQEI1(void) {
    QEI1CON = 0;
    DFLT1CON = 0;
    QEI1CONbits.QEIM = 0b111;
    DFLT1CONbits.CEID = 0;
    POS1CNT = 0;
    MAX1CNT = UINT16_MAX;
    _QEI1IF = 0;
    _QEI1IE = 0;
}

void ConfigureQEI2(void) {
    QEI2CON = 0;
    DFLT2CON = 0;
    QEI2CONbits.QEIM = 0b111;
    POS2CNT = 0;
    MAX2CNT = UINT16_MAX;
    _QEI2IF = 0;
    _QEI2IE = 0;
}

void ConfigureI2C(void) {
    // initialize the hardware
    // ACKEN disabled; STREN disabled; GCEN disabled; SMEN disabled; DISSLW enabled; I2CSIDL disabled; ACKDT Sends ACK; SCLREL Holds; RSEN disabled; IPMIEN disabled; A10M 7 Bit; PEN disabled; RCEN disabled; SEN disabled; I2CEN enabled;
    I2C1CON = 0x0000;
    I2C1CONbits.IPMIEN = 1;
    I2C1CONbits.STREN = 1;
    I2C1CONbits.I2CEN = 1;
    // P disabled; S disabled; BCL disabled; I2COV disabled; IWCOL disabled;
    I2C1STAT = 0x0;
    I2C1MSK = I2C_SLAVE_DEFAULT_ADDRESS;
    I2C1ADD = I2C_SLAVE_DEFAULT_ADDRESS;
    _MI2C1IF = 0;
    _MI2C1IE = 0;
    _SI2C1IF = 0;
    _SI2C1IP = 6;
    _SI2C1IE = 1;
}

/********************* UART1 Configuration ************************************/
void ConfigureUART1(void) { // Baud = 115200 , 8-N-1
    __C30_UART = 1;
    U1MODE = (0x8008 & ~(1 << 15));
    U1STA = 0x0;
    U1BRG = BRGVAL;
    U1MODEbits.UARTEN = 1;
    U1STAbits.UTXEN = 1;
    IEC0bits.U1RXIE = 1;
}

void _ISR_PSV _U1TXInterrupt(void) {
    _U1TXIF = 0;
}
extern uint16_t DTG_Print_Quantity;
void _ISR_PSV _U1RXInterrupt(void) {
    _U1RXIF = 0;
    static size_t currentCharPos = 0;
    static char instructionBuf[16] = { ' ' };
    uint8_t key = U1RXREG;
    switch(key) {
        case '\33': {
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
        }
        case '\n': {
            if(0 == strncmp(instructionBuf, "n", 1)) {
                printf("Print Quantity: %u\n", DTG_Print_Quantity);
            } else if(0 == strncmp(instructionBuf, "s", 1)) {
                printf("[%u][%u][%x]\n",
                    (LimitSwitchEnd),
                    (LimitSwitchStart),
                    (ReadDTG_State())
                );
            }
            currentCharPos = 0;
            memset(instructionBuf, ' ', sizeof(instructionBuf));
            break;
        }
        default: {
            instructionBuf[currentCharPos++] = key;
            printf("%c", key);
            if(16 == currentCharPos) currentCharPos = 0; // prevent overflow
            break;
        }
    }
}

void _ISR_PSV _U1ErrInterrupt(void) {
    if ((U1STAbits.OERR == 1)) {
        U1STAbits.OERR = 0;
    }

    _U1EIF = 0;
}




/**
 Section: Function prototypes
*/
bool SYSTEM_ResetCauseFromSoftware(uint16_t resetCause);
bool SYSTEM_ResetCauseFromWatchdogTimer(uint16_t resetCause);
bool SYSTEM_ResetCauseFromConfigurationMismatch(uint16_t resetCause);
bool SYSTEM_ResetCauseFromIllegalOpcode(uint16_t resetCause);
bool SYSTEM_ResetCauseFromExternal(uint16_t resetCause);
bool SYSTEM_ResetCauseFromTrap(uint16_t resetCause);
void SYSTEM_ResetCauseClear(RESET_MASKS resetFlagMask);

uint16_t SYSTEM_GetResetCause(void)
{
    return RCON;
}

void __attribute__ ((weak)) SYSTEM_ResetCauseHandler(void)
{
    uint16_t resetCause = SYSTEM_GetResetCause();
    if(SYSTEM_ResetCauseFromTrap(resetCause))
    {
      SYSTEM_ResetCauseClear(RESET_MASK_TRAPR);
      //Do something
      printf("SYSTEM:TRAPR\n");
    }
    if(SYSTEM_ResetCauseFromIllegalOpcode(resetCause))
    {
      SYSTEM_ResetCauseClear(RESET_MASK_IOPUWR);
      //Do something
      printf("SYSTEM:IOPUWR\n");
    }
    if(SYSTEM_ResetCauseFromConfigurationMismatch(resetCause))
    {
      SYSTEM_ResetCauseClear(RESET_MASK_CM);
      //Do something
      printf("SYSTEM:CM\n");
    }
    if(SYSTEM_ResetCauseFromExternal(resetCause))
    {
      SYSTEM_ResetCauseClear(RESET_MASK_EXTR);
      //Do something
      printf("SYSTEM:EXTR\n");
    }
    if(SYSTEM_ResetCauseFromSoftware(resetCause))
    {
      SYSTEM_ResetCauseClear(RESET_MASK_SWR);
      //Do something
      printf("SYSTEM:SWR\n");
    }
    if(SYSTEM_ResetCauseFromWatchdogTimer(resetCause))
    {
      SYSTEM_ResetCauseClear(RESET_MASK_WDTO);
      //Do something
      printf("SYSTEM:WDTO\n");
    }
}

bool SYSTEM_ResetCauseFromTrap(uint16_t resetCause)
{
    bool resetStatus = false;
    if(resetCause & RESET_MASK_TRAPR)
    {
      resetStatus = true;
    }
    return resetStatus;
}

bool SYSTEM_ResetCauseFromIllegalOpcode(uint16_t resetCause)
{
    bool resetStatus = false;
    if(resetCause & RESET_MASK_IOPUWR)
    {
      resetStatus = true;
    }
    return resetStatus;
}

bool SYSTEM_ResetCauseFromConfigurationMismatch(uint16_t resetCause)
{
    bool resetStatus = false;
    if(resetCause & RESET_MASK_CM)
    {
      resetStatus = true;
    }
    return resetStatus;
}

bool SYSTEM_ResetCauseFromExternal(uint16_t resetCause)
{
    bool resetStatus = false;
    if(resetCause & RESET_MASK_EXTR)
    {
      resetStatus = true;
    }
    return resetStatus;
}

bool SYSTEM_ResetCauseFromSoftware(uint16_t resetCause)
{
    bool resetStatus = false;
    if(resetCause & RESET_MASK_SWR)
    {
      resetStatus = true;
    }
    return resetStatus;
}

bool SYSTEM_ResetCauseFromWatchdogTimer(uint16_t resetCause)
{
    bool resetStatus = false;
    if(resetCause & RESET_MASK_WDTO)
    {
      resetStatus = true;
    }
    return resetStatus;
}

void SYSTEM_ResetCauseClear(RESET_MASKS resetFlagMask)
{
     RCON = RCON & (~resetFlagMask);
}

void SYSTEM_ResetCauseClearAll()
{
    RCON = 0x00;
}