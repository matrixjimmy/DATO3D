/*
 * File:   constants.h
 * Author: jim.Lin
 *
 * Function:
 * 1. Global Constants
 * 2. I/O Convention abbr.
 * 3. Port definitions
 *
 * Created on 2016年5月14日, 下午 02:00
 */

#include <xc.h>
#include <stdint.h>
#include <stdbool.h>
#include <stdarg.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <dsp.h>
#include "system.h"
#include "user.h"
#include "pin_manager.h"
#include "stepmotor.h"
#include "QEI.h"
#include "DTG.h"
#include "protocol-i2c.h"

/********************** Global Constants **************************************/
/********************** XTAL Configuration ************************************
 *
 * FIN  = 10,000,000 Hz
 * FOSC = 50,000,000 Hz
 * FCY  = 25,000,000 Hz
 * TCY  = 40.00000 ns
 *
 * PLLDIV = 38 (user set)
 * PLLPRE = 0 (default)
 * PLLPOST = 1 (default)
 * M = PLLDIV + 2 = 40 (user set)
 * N1 = PLLPRE + 2 = 2 (default)
 * N2 = 2*( PLLPOST + 1) = 4 (default)
 *
 * ----------------------------------------------------------------------------
 *                                                     cf: dsPIC33F Family Ref.
 *                   M                                             Section 7.7
 * FOSC = FIN * ( -------)                                        Equation 7-3
 *                N1 * N2
 *
 *                         (PLLDIV + 2)                           (38+2)
 * FOSC = FIN * (----------------------------------)  = 10M *(----------------)
 *                ( PLLPRE + 2 )* (2*( PLLPOST + 1))           (0+2) * 2*(1+1)
 *
 *                 (38+2)
 * FOSC = 10M *(----------------) = 50,000,000 Hz
 *               (0+2) * 2*(1+1)
 *
 *----------------------------------------------------------------------------=
 *   Limit：                                           cf: dsPIC33F Family Ref.
 * 0.8 MHz ≤ FREF ≤ 8.0 MHz                                       Section 7.7
 *                1              1                                Equation 7-5
 * FREF = FIN * (----) = 10M * (---) = 5MHz     (OK)
 *                N1             2
 * ----------------------------------------------------------------------------
 * 100 MHZ ≤ FVCO ≤ 200 MHZ                           cf: dsPIC33F Family Ref.
 *                M             40                                 Section 7.7
 * FVCO = FIN * (----) = 10M * (---) = 200MHz    (OK)             Equation 7-6
 *                N1             2
 * ----------------------------------------------------------------------------
 * FOSC ≤ 80 MHz                                       cf: dsPIC33F Family Ref
 * FOSC = 50,000,000 Hz                          (OK)              Section 7.7
 *                                                                Equation 7-7
 * ----------------------------------------------------------------------------
 *
 *         Fosc        50M                            cf: dsPIC33FJ128MCx02/x04
 * FCY = (------)  =  ----- = 25 MHz                                Section 9.1
 *          2           2                                          Equation 9-1
 * ----------------------------------------------------------------------------
 */
#define USE_EXTERNAL_OSC            //使用外部震盪
#define USE_PLL                     //使用PLL

#ifdef USE_INTERNAL_FRC
    #define XTAL_FREQ               7372800ULL          // Internal OSC Frequency,7.37Mhz
#endif
#ifdef USE_EXTERNAL_OSC
    #define XTAL_FREQ               10000000ULL         // ON Board XTAL Frequency,10Mhz
#endif

#ifdef USE_PLL
    #define PLL_DIV  30
    #define PLL_PRE  0
    #define PLL_POST 0
#define PLL_M PLL_DIV+2
    #define PLL_N1  PLL_PRE+2
    #define PLL_N2  2*(PLL_POST+1)
    #define FOSC    XTAL_FREQ*((PLL_M)/(PLL_N1*PLL_N2))
#endif

#define FCY                     FOSC / 2
#define TCY                     1 / FCY

#include <libpic30.h>
/********************** UART Baud rate Setting ********************************/
#define BAUDRATE 115200
#if BAUDRATE != 9600
    #define BRGVAL ((FCY/BAUDRATE)/4)-1
#else
    #define BRGVAL ((FCY/BAUDRATE)/16)-1
#endif

/********************** I/O Convention abbr. **********************************/
#define	INPUT                   1                   // 定義1為輸入方向
#define	OUTPUT                  0                   // 定義0為輸出方向

#define HI                      1
#define LOW                     0

/********************** Interrupt Convention abbr. ****************************/
#define _ISR_PSV        __attribute__((__interrupt__, __auto_psv__))
#define _ISR_NO_PSV     __attribute__((__interrupt__, __no_auto_psv__))

#define NOT_BETWEEN(min, value, max)    (value > max || value < min)    //  (value > max || value < min)
#define NOT_BETWEENE(min, value, max)   (value >= max || value <= min)  // (value >= max || value <= min)
#define BETWEEN(min, value, max)        (value < max && value > min)    //  (value < max && value > min)
#define BETWEENE(min, value, max)       (value <= max && value >= min)  // (value <= max && value >= min)

void BSP_display(const char * msg, ...);
void moveServoUp(void);
void moveServoDown(void);
void servoStop(void);
void moveRollerForward(void);
void moveRollerBackward(void);
void rollerStop(void);