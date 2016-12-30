/*
 * File:   uart2.h
 * Author: jimmy
 *
 * Created on November 15, 2016, 2:05 PM
 */

#ifndef UART2_H
#define	UART2_H

#ifdef	__cplusplus
extern "C" {
#endif

#define UART_BAUD_RATE      115200
#if UART_BAUD_RATE <= 9600
    // BRGH = 0
    #define UART_BRG_VALUE      FCY / 16 / UART_BAUD_RATE - 1
#else
    // BRGH = 1
    #define UART_BRG_VALUE      FCY / 4 / UART_BAUD_RATE - 1
#endif

    void INITIALIZE_UARTModule(void);


#ifdef	__cplusplus
}
#endif

#endif	/* UART2_H */

