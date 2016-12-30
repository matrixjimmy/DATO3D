/******************************************************************************/
/* Files to Include                                                           */
/******************************************************************************/

#include "constant.h"

/******************************************************************************/
/* System Level Functions                                                     */
/*                                                                            */
/* Custom oscillator configuration funtions, reset source evaluation          */
/* functions, and other non-peripheral microcontroller initialization         */
/* functions get placed in system.c.                                          */
/*                                                                            */
/******************************************************************************/

void SYSTEM_Initialize(void)
{
    OSCILLATOR_Initialize();
    PIN_MANAGER_Initialize();
    WDT_WatchdogtimerSoftwareDisable();
    INTERRUPT_Initialize();
    INITIALIZE_I2C1Module();
    //INITIALIZE_I2C2Module();
    INITIALIZE_UARTModule();
}

void OSCILLATOR_Initialize(void)
{
    // Select Internal FRC at POR
    //__FOSCSEL(FNOSC_FRC & IESO_OFF);
    // Enable Clock Switching and Configure Primary Oscillator in XT mode
    //__FOSC(FCKSM_CSECMD & OSCIOFNC_OFF & POSCMD_NONE);
    // Configure PLL prescaler, PLL postscaler, PLL divisor
    PLLFBD=46;//                        M=48
    CLKDIVbits.PLLPOST=0;//             N2=2
    CLKDIVbits.PLLPRE=0;//              N1=2
    // Initiate Clock Switch to FRC oscillator with PLL (NOSC=0b001)
    __builtin_write_OSCCONH(0x03);
    __builtin_write_OSCCONL(OSCCON | 0x01);
    // Wait for Clock switch to occur
    while (OSCCONbits.COSC!= 0b011);
    // Wait for PLL to lock
    while (OSCCONbits.LOCK!= 1);
}

void INTERRUPT_Initialize (void)
{
    //    UERI: UART2 Error
    //    Priority: 1
        IPC16bits.U2EIP = 1;
    //    UTXI: UART2 Transmitter
    //    Priority: 5
        IPC7bits.U2TXIP = 4;
    //    URXI: UART2 Receiver
    //    Priority: 4
        IPC7bits.U2RXIP = 7;
    //    CNI: Change Notification Interrupt
    //    Priority: 2
        IPC4bits.CNIP = 1;
    //    MICI: I2C1 Master Events
    //    Priority: 3
        IPC4bits.MI2C1IP = 1;
    //    SICI: I2C1 Slave Events
    //    Priority: 2
        IPC4bits.SI2C1IP = 1;
    //    MICI: I2C2 Master Events
    //    Priority: 1
        IPC12bits.MI2C2IP = 1;
    //    SICI: I2C2 Slave Events
    //    Priority: 1
        IPC12bits.SI2C2IP = 1;
    //    OCI: Output Compare 1
    //    Priority: 1
        IPC0bits.OC1IP = 1;
    //    TI: Timer 1
    //    Priority: 1
        IPC0bits.T1IP = 1;
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