/*
 * File:   constant.h
 * Author: jimmy
 *
 * Created on November 15, 2016, 1:46 PM
 */

#ifndef CONSTANT_H
#define	CONSTANT_H

#ifdef	__cplusplus
extern "C" {
#endif
//#define __DEBUG 1

#include <xc.h>
#include <stdint.h>
#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdarg.h>
#include <dsp.h>

#include "pin_manager.h"
#include "system.h"

/* Microcontroller MIPs (FCY) */
#define _XTAL_FREQ      60000000UL
#define FCY             _XTAL_FREQ
#include <libpic30.h>

/* ISR attribute */
#define _ISR_PSV __attribute__((interrupt, auto_psv))
#define _ISR_NO_PSV __attribute__((interrupt, no_auto_psv))

/* LCD Param */
#define LCD_DISP_LINE    2U
#define LCD_DISP_COLUMN  16U

/* IF Macros */
#define NOT_BETWEEN(min, value, max)    (value > max || value < min)    //  (value > max || value < min)
#define NOT_BETWEENE(min, value, max)   (value >= max || value <= min)  // (value >= max || value <= min)
#define BETWEEN(min, value, max)        (value < max && value > min)    //  (value < max && value > min)
#define BETWEENE(min, value, max)       (value <= max && value >= min)  // (value <= max && value >= min)

#include "bsp.h"

#ifdef	__cplusplus
}
#endif

#endif	/* CONSTANT_H */
