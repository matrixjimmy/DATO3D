/*
 * File:   bsp.h
 * Author: jimmy
 *
 * Created on November 15, 2016, 3:05 PM
 */

#ifndef BSP_H
#define	BSP_H

#ifdef	__cplusplus
extern "C" {
#endif

#include "MCP23008.h"
#include "LCD_SSC2E16.h"
#include "i2c.h"
#include "oc.h"
#include "uart2.h"
#define BSP_TICKS_PER_SEC       2000U
#define BSP_S(time_)            (BSP_TICKS_PER_SEC * time_)
#define BSP_MS(time_)           (BSP_S(time_) / 1000U)

    void BSP_display(char const *msg, ...);
    void BSP_exit(void);
    void BSP_onKeyboardInput(uint8_t key);
    void BSP_GCodeParser(char * command);

#ifdef	__cplusplus
}
#endif

#endif	/* BSP_H */

