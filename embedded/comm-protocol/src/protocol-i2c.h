/*
 * File:   protocol-i2c.h
 * Author: jimmy
 *
 * Created on December 5, 2016, 3:57 PM
 */

#ifndef PROTOCOL_I2C_H
#define	PROTOCOL_I2C_H

#ifdef	__cplusplus
extern "C" {
#endif

#define I2C_SLAVE_DEFAULT_ADDRESS          0b0100000

#define I2C_REG_INDEX_STAT          1
#define I2C_REG_INDEX_QEI1L         2
#define I2C_REG_INDEX_QEI1H         3
#define I2C_REG_INDEX_QEI1O         4
#define I2C_REG_INDEX_QEI2L         5
#define I2C_REG_INDEX_QEI2H         6
#define I2C_REG_INDEX_QEI2O         7

    enum I2CInstruction {
        IDLE             = 0x00,
        RESET            = 0x01,

        READ_STAT        = 0x11,
        READ_QEI1        = 0x12,
        READ_QEI2        = 0x13,
        START_PRINT      = 0x14,
        STOP_PRINT       = 0x15,
        GO_TO_START      = 0x16,
        GO_TO_END        = 0x17,

        POWDER_START     = 0x1A,
        SERVO_UP         = 0x1B,
        SERVO_DOWN       = 0x1C,
        SERVO_STOP       = 0x1D,
        ROLLER_FORWARD   = 0x1E,
        ROLLER_STOP      = 0x1F,
        ROLLER_BACKWARD  = 0x18,
        SET_ROLLER_SPEED = 0x10,

        PRESS_PWR        = 0x20,
        PRESS_INK        = 0x21,
        PRESS_PAPER      = 0x22,
        PRESS_DELETE     = 0x23,
        TURN_OFF_PRINTER = 0x24,
        TURN_ON_PRINTER  = 0x25,

        SUCCESS          = 0x30,
        FAIL             = 0x31,

        MAX_STATE
    };

//    enum RXInstruction {
//
//    };


#ifdef	__cplusplus
}
#endif

#endif	/* PROTOCOL_I2C_H */
