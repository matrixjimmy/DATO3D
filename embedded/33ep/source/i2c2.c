#include "constant.h"

/* private: */
static void I2C2ModuleWriteOP(uint8_t deviceAddr);
static void I2C2ModuleResendOP(uint8_t deviceAddr);
static void I2C2ModuleReadOP(uint8_t deviceAddr);
static void I2C2ModuleSendStop(void);
static void I2C2ModuleWrite(uint8_t data);
static uint8_t I2C2ModuleRead(void);
static void I2C2ModuleIdle(void);

/* Local objects ------------------------------------------------------*/
void INITIALIZE_I2C2Module(void) {
    I2C2BRG = 0x33;
    I2C2CON = 0x8000;
    I2C2STAT = 0x00;

    /* MCP23008 + LCD Initialize */
    LCD_RS_SetLow();
    LCD_RW_SetLow();
    {
        I2C2ModuleIdle();
        /* All Pin set as output */
        I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
        I2C2ModuleWrite(MCP23008_REG_IODIR_ADDRESS);
        I2C2ModuleWrite(0x00);
        I2C2ModuleSendStop();

        I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
        I2C2ModuleWrite(MCP23008_REG_IOCON_ADDRESS);
        I2C2ModuleWrite(0x20); // SEQOP = 1
        I2C2ModuleSendStop();

        I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
        I2C2ModuleWrite(MCP23008_REG_OLAT_ADDRESS);
        /* Start LCD INIT Progress */
        LCD_E_SetHigh();
        I2C2ModuleWrite(LCD_INSTRUCTION_FN_SET);
        LCD_E_SetLow();
        I2C2ModuleSendStop();

        __delay_ms(5);

        I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
        I2C2ModuleWrite(MCP23008_REG_OLAT_ADDRESS);
        LCD_E_SetHigh();
        I2C2ModuleWrite(LCD_INSTRUCTION_FN_SET);
        LCD_E_SetLow();
        I2C2ModuleSendStop();
        __delay_us(100);

        I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
        I2C2ModuleWrite(MCP23008_REG_OLAT_ADDRESS);
        LCD_E_SetHigh();
        I2C2ModuleWrite(LCD_INSTRUCTION_FN_SET);
        LCD_E_SetLow();
        I2C2ModuleSendStop();

        __delay_ms(5);

        /* Start Set LCD */
        I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
        I2C2ModuleWrite(MCP23008_REG_OLAT_ADDRESS);
        LCD_E_SetHigh();
        I2C2ModuleWrite(LCD_INSTRUCTION_FN_SET &
                LCD_INSMASK_8_BITs &
                LCD_INSMASK_2_LINE &
                LCD_INSMASK_5x7_DOTs);
        LCD_E_SetLow();
        I2C2ModuleSendStop();
        __delay_us(40);

        I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
        I2C2ModuleWrite(MCP23008_REG_OLAT_ADDRESS);
        LCD_E_SetHigh();
        I2C2ModuleWrite(LCD_INSTRUCTION_DISP &
                LCD_INSMASK_DISPLAY_OFF &
                LCD_INSMASK_CURSOR_OFF &
                LCD_INSMASK_BLINK_OFF);
        LCD_E_SetLow();
        I2C2ModuleSendStop();
        __delay_us(40);

        I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
        I2C2ModuleWrite(MCP23008_REG_OLAT_ADDRESS);
        LCD_E_SetHigh();
        I2C2ModuleWrite(LCD_INSTRUCTION_DISP &
                LCD_INSMASK_DISPLAY_ON &
                LCD_INSMASK_CURSOR_ON &
                LCD_INSMASK_BLINK_ON);
        LCD_E_SetLow();
        I2C2ModuleSendStop();
        __delay_us(40);

        I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
        I2C2ModuleWrite(MCP23008_REG_OLAT_ADDRESS);
        LCD_E_SetHigh();
        I2C2ModuleWrite(LCD_INSTRUCTION_CLS);
        LCD_E_SetLow();
        I2C2ModuleSendStop();
        __delay_us(1640);

        I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
        I2C2ModuleWrite(MCP23008_REG_OLAT_ADDRESS);
        LCD_E_SetHigh();
        I2C2ModuleWrite(LCD_INSTRUCTION_ENTRY &
                LCD_INSMASK_INC &
                LCD_INSMASK_SHIFT_NORM);
        LCD_E_SetLow();
        I2C2ModuleSendStop();
        __delay_us(40);
    }
    LCD_RS_SetLow();
    LCD_RW_SetLow();
#if 1
    /* check MCP23008 IO = PASSED */
//    {
//        I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
//        I2C2ModuleWrite(MCP23008_REG_IODIR_ADDRESS);
//        I2C2ModuleWrite(0x00);
//        I2C2ModuleSendStop();
//
//        I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
//        I2C2ModuleWrite(MCP23008_REG_IOCON_ADDRESS);
//        I2C2ModuleWrite(0x20);
//        I2C2ModuleSendStop();
//
//        I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
//        I2C2ModuleWrite(MCP23008_REG_OLAT_ADDRESS);
//        uint8_t i;
//        for(i = 0; i < 0xFF; i++) {
//            I2C2ModuleWrite(i);
//        }
//        I2C2ModuleSendStop();
//    }

    /* LCD Write Test = PASSED */
//    {
//
//        char * str = "1234567890123456789012345678901234567890";
//        //LCDModuleSetCursorPosition(LCD_LINE_2, 1);
//        LCDModuleWriteString((uint8_t *)str);
//        __delay_ms(100);
//        LCDModuleClearDisplay();
//
//        uint8_t word, column, line;
//        for(word = 0x00, column = 0, line = 0; word < 0xFF; word++, column++) {
//            if(column==16) {
//                line++;
//                column = 0;
//                LCDModuleSetDDRAMCursorPosition(line%2, column);
//            }
//            LCDModuleWriteChar(word);
//            __delay_ms(100); // for display purpose
//        }
//        __delay_ms(100);
//        LCDModuleClearDisplay();
//    }

    /* MCP23008 Read Test = PASSED */
//    uint8_t regVal;
//    {
//        I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
//        I2C2ModuleWrite(MCP23008_REG_IODIR_ADDRESS);
//        I2C2ModuleResendOP(MCP23008_ADDRESS_READ(MCP23008_HW_ADDRESS));
//        regVal = I2C2ModuleRead();
//        I2C2ModuleSendStop();
//    }
    /* LCD Write CGRAM Test = PASSED */
    uint8_t cgramPattern[] = {0,0,0,0,0,0,0,0}; // <-- Character Pattern
    LCDModuleSetCGRAM( 0, cgramPattern);
    LCDModuleSetCGRAM( 1, cgramPattern);
    LCDModuleSetCGRAM( 2, cgramPattern);
    LCDModuleSetCGRAM( 3, cgramPattern);
    LCDModuleSetCGRAM( 4, cgramPattern);
    LCDModuleSetCGRAM( 5, cgramPattern);
    LCDModuleSetCGRAM( 6, cgramPattern);
    LCDModuleSetCGRAM( 7, cgramPattern);

    LCDModuleClearDisplay();

    LCDModuleWriteChar(0);
    LCDModuleWriteChar(1);
    LCDModuleWriteChar(2);
    LCDModuleWriteChar(3);
    LCDModuleWriteChar(4);
    LCDModuleWriteChar(5);
    LCDModuleWriteChar(6);
    LCDModuleWriteChar(7);
#endif
    IFS3bits.MI2C2IF = 0;
    IEC3bits.MI2C2IE = 1;
}

void I2C2ModuleWriteOP(uint8_t deviceAddr) {
    I2C2CONbits.SEN = 1;
    while(I2C2CONbits.SEN);
    INTERRUPT_I2C2_FLAG_CLR();
    do {
        I2C2TRN = I2C_OP_WRITE(deviceAddr);
        while(I2C2STATbits.TRSTAT); // transmit is in progress (8-bits + ACK)
    } while(I2C2STATbits.ACKSTAT);  // slave is NACK
    INTERRUPT_I2C2_FLAG_CLR();
}

void I2C2ModuleResendOP(uint8_t deviceAddr) {
    I2C2CONbits.RSEN = 1;
    while(I2C2CONbits.RSEN);
    INTERRUPT_I2C2_FLAG_CLR();
    do {
        I2C2TRN = deviceAddr;
        while(I2C2STATbits.TRSTAT); // transmit is in progress (8-bits + ACK)
    } while(I2C2STATbits.ACKSTAT);  // slave is NACK
    INTERRUPT_I2C2_FLAG_CLR();
}

void I2C2ModuleSendStop(void) {
    I2C2CONbits.PEN = 1;
    I2C2CONbits.RCEN = 0;
    I2C2STATbits.IWCOL = 0;
    I2C2STATbits.BCL = 0;
    I2C2ModuleIdle();
    INTERRUPT_I2C2_FLAG_CLR();
}

void I2C2ModuleWrite(uint8_t data) {
    I2C2TRN = data;
    while(I2C2STATbits.TRSTAT);
    INTERRUPT_I2C2_FLAG_CLR();
}

void I2C2ModuleReadOP(uint8_t deviceAddr) {
    I2C2CONbits.SEN = 1;
    while(I2C2CONbits.SEN);
    INTERRUPT_I2C2_FLAG_CLR();
    do {
        I2C2TRN = I2C_OP_READ(deviceAddr);
        while(I2C2STATbits.TRSTAT);
    } while(I2C2STATbits.ACKSTAT);
    INTERRUPT_I2C2_FLAG_CLR();
}

uint8_t I2C2ModuleRead(void) {
    uint8_t data;
    I2C2CONbits.RCEN = 1;
    while(I2C2CONbits.RCEN);
    while(!I2C2STATbits.RBF);
    INTERRUPT_I2C2_FLAG_CLR();
    data = I2C2RCV;
    // can do check here
    I2C2CONbits.ACKEN = 1;
    INTERRUPT_I2C2_FLAG_CLR();
    return data;
}

void I2C2ModuleIdle(void) {
    while(I2C2CONbits.SEN ||
            I2C2CONbits.RSEN ||
            I2C2CONbits.PEN ||
            I2C2CONbits.RCEN ||
            I2C2CONbits.ACKEN ||
            I2C2STATbits.TRSTAT);
}

bool LCDModuleBusy(void) {
    return ((LCDModuleCurrentStatus() & 0b10000000) != 0);
}

uint8_t LCDModuleCurrentStatus(void) {
    uint8_t status;
    I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
    LCD_RW_SetHigh();
    I2C2ModuleWrite(MCP23008_REG_IODIR_ADDRESS);
    I2C2ModuleWrite(0x00); // temp set all GPIO as input
    I2C2ModuleSendStop();

    I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
    I2C2ModuleWrite(MCP23008_REG_GPIO_ADDRESS);
    I2C2ModuleResendOP(I2C_OP_READ(MCP23008_HW_ADDRESS));
    LCD_E_SetHigh();
    status = I2C2ModuleRead();
    LCD_E_SetLow();
    I2C2ModuleSendStop();
    LCD_RW_SetLow();

    I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
    I2C2ModuleWrite(MCP23008_REG_IODIR_ADDRESS);
    I2C2ModuleWrite(0xFF); // set all GPIO as output
    I2C2ModuleSendStop();
    return status;
}

void LCDModuleReturnHome(void) { // LCD OP lasts 1.64ms max
    I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
    I2C2ModuleWrite(MCP23008_REG_GPIO_ADDRESS);
    LCD_RS_SetLow();
    LCD_RW_SetLow();
    LCD_E_SetHigh();
    I2C2ModuleWrite(LCD_INSTRUCTION_HOME);
    LCD_E_SetLow();
    I2C2ModuleSendStop();
}

void LCDModuleClearDisplay(void) { // LCD OP lasts 1.64ms max
    I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
    I2C2ModuleWrite(MCP23008_REG_GPIO_ADDRESS);
    LCD_RS_SetLow();
    LCD_RW_SetLow();
    LCD_E_SetHigh();
    I2C2ModuleWrite(LCD_INSTRUCTION_CLS);
    LCD_E_SetLow();
    I2C2ModuleSendStop();
    //while(LCDModuleBusy());
    __delay_us(1640);
}

void LCDModuleSetDDRAMCursorPosition(uint8_t line, uint8_t column) {
    uint8_t DDRAMAddr = 0x80 + (line==1?0x40:0x00) + column;
    if(DDRAMAddr < 0x80) return;
    I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
    I2C2ModuleWrite(MCP23008_REG_OLAT_ADDRESS);
    LCD_E_SetHigh();
    I2C2ModuleWrite(DDRAMAddr);
    LCD_E_SetLow();
    I2C2ModuleSendStop();
    __delay_us(40);
}

/**
 * Charactor Code: 0~7
 * CGRAM 543 210
 *      Code 0~7
 */
void LCDModuleSetCGRAM(uint8_t addr, uint8_t pattern[]) {
    uint8_t i;
    for(i = 0; i < 8; i++) {
        I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
        I2C2ModuleWrite(MCP23008_REG_OLAT_ADDRESS);
        LCD_E_SetHigh();
        I2C2ModuleWrite(0b1000000 | ((0b111 & addr) << 3) | (0b111 & i));
        LCD_E_SetLow();
        I2C2ModuleSendStop();
        __delay_us(40);
        LCD_RS_SetHigh();
        I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
        I2C2ModuleWrite(MCP23008_REG_OLAT_ADDRESS);
        LCD_E_SetHigh();
        I2C2ModuleWrite(0b11111 & pattern[i]);
        LCD_E_SetLow();
        I2C2ModuleSendStop();
        LCD_RS_SetLow();
        __delay_us(40);
    }
}

void LCDModuleWriteChar(const uint8_t c) {
    LCD_RS_SetHigh();
    I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
    I2C2ModuleWrite(MCP23008_REG_OLAT_ADDRESS);
    LCD_E_SetHigh();
    I2C2ModuleWrite(c);
    LCD_E_SetLow();
    I2C2ModuleSendStop();
    LCD_RS_SetLow();
    __delay_us(40);
}

void LCDModuleWriteString(const uint8_t * pCharBuf) {
    LCD_RS_SetHigh();
    I2C2ModuleWriteOP(MCP23008_HW_ADDRESS);
    I2C2ModuleWrite(MCP23008_REG_OLAT_ADDRESS);
    while(*pCharBuf) {
        I2C2ModuleWrite(*pCharBuf++);
        LCD_E_SetHigh();
        __delay_us(10);
        LCD_E_SetLow();
        __delay_us(30);
    }
    LCD_RS_SetLow();
}

void _ISR_PSV _MI2C2Interrupt ( void ) {
    IFS3bits.MI2C2IF = 0;
}
