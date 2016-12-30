#include "constant.h"

void INITIALIZE_UARTModule(void) {
    // STSEL 1; IREN disabled; PDSEL 8N; UARTEN enabled; RTSMD disabled; USIDL disabled; WAKE disabled; ABAUD disabled; LPBACK disabled; BRGH enabled; URXINV disabled; UEN TX_RX;
    U2MODE = (0x8008
            & ~(1<<15)  // disabling UARTEN bit
#if UART_BAUD_RATE <= 9600
            & ~(1<<3)   // disabling BRGH bit
#endif
            );
    // UTXISEL0 TX_ONE_CHAR; UTXINV disabled; OERR NO_ERROR_cleared; URXISEL RX_ONE_CHAR; UTXBRK COMPLETED; UTXEN disabled; ADDEN disabled;
    U2STA = 0x0;
    U2BRG = UART_BRG_VALUE;

    IEC1bits.U2RXIE = 1;
    U2MODEbits.UARTEN = 1;
    U2STAbits.UTXEN = 1;
}

void _ISR_PSV _U2RXInterrupt(void) {
    static size_t currentCharPos = 0;
    static char instructionBuf[16] = { ' ' };
    IFS1bits.U2RXIF = 0;
    uint8_t key = U2RXREG;
    switch(key) {
        case '\33': {
            // RESET
            __asm__ volatile("reset");
            break;
        }
        case '\n': {
            BSP_GCodeParser(instructionBuf);
            currentCharPos = 0;
            memset(instructionBuf, ' ', sizeof(instructionBuf));
            break;
        }
        default: {
            instructionBuf[currentCharPos++] = key;
#if __DEBUG
            printf("%c", key);
#endif
            if(16 == currentCharPos) currentCharPos = 0; // prevent overflow
            break;
        }
    }
}

void _ISR_PSV _U2TXInterrupt(void) {
    IFS1bits.U2TXIF = 0;
}

void _ISR_PSV _U2ErrInterrupt(void) {
    if ((U2STAbits.OERR == 1)) {
        U2STAbits.OERR = 0;
    }

    IFS4bits.U2EIF = 0;
}
