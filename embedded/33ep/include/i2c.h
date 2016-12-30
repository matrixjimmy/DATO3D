/*
 * File:   i2c.h
 * Author: jimmy
 *
 * Created on November 15, 2016, 3:52 PM
 */

#ifndef I2C_H
#define	I2C_H

#ifdef	__cplusplus
extern "C" {
#endif

#define I2C_OP_WRITE(address_) ( address_ << 1)
#define I2C_OP_READ(address_)  ((address_ << 1) + 1)

#define DASINK_33FJ_HW_ADDRESS 0b0100000

    void INITIALIZE_I2C1Module(void);

    inline static void INTERRUPT_I2C1_FLAG_CLR(void) {
        IFS1bits.MI2C1IF = 0;
    }

    void Write33FJInstruction(uint8_t instruction);
    uint8_t Read33FJResult(void);

    void INITIALIZE_I2C2Module(void);

    inline static void INTERRUPT_I2C2_FLAG_CLR(void) {
        IFS3bits.MI2C2IF = 0;
    }

    inline static void INTERRUPT_I2C2_ENABLE(void) {
        IEC3bits.MI2C2IE = 1;
    }

    inline static void INTERRUPT_I2C2_DISABLE(void) {
        IEC3bits.MI2C2IE = 0;
    }

    bool LCDModuleBusy(void);
    uint8_t LCDModuleCurrentStatus(void);
    void LCDModuleReturnHome(void);
    void LCDModuleClearDisplay(void);
    void LCDModuleSetDDRAMCursorPosition(uint8_t line, uint8_t column);
    void LCDModuleSetCGRAM(uint8_t addr, uint8_t pattern[]);
    void LCDModuleWriteChar(const uint8_t c);
    void LCDModuleWriteString(const uint8_t * charBuf);

#ifdef	__cplusplus
}
#endif

#endif	/* I2C_H */
