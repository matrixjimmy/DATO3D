#include "constant.h"

static void I2C1ModuleWriteOP(uint8_t deviceAddr);
static void I2C1ModuleResendOP(uint8_t deviceAddr);
static void I2C1ModuleReadOP(uint8_t deviceAddr);
static void I2C1ModuleSendStop(void);
static void I2C1ModuleWrite(uint8_t data);
static uint8_t I2C1ModuleRead(void);
static void I2C1ModuleIdle(void);

void INITIALIZE_I2C1Module(void) {
    I2C1CON = 0x00;
    I2C1STAT = 0x00;
    I2C1BRG = 244; //0x33;

    I2C1CONbits.I2CEN = 1;
}

void I2C1ModuleWriteOP(uint8_t deviceAddr) {
    I2C1CONbits.SEN = 1;
    while(I2C1CONbits.SEN);
    INTERRUPT_I2C1_FLAG_CLR();
    do {
        I2C1TRN = I2C_OP_WRITE(deviceAddr);
        while(I2C1STATbits.TRSTAT); // transmit is in progress (8-bits + ACK)
    } while(I2C1STATbits.ACKSTAT);  // slave is NACK
    INTERRUPT_I2C1_FLAG_CLR();
}

void I2C1ModuleResendOP(uint8_t deviceAddr) {
    I2C1CONbits.RSEN = 1;
    while(I2C1CONbits.RSEN);
    INTERRUPT_I2C1_FLAG_CLR();
    do {
        I2C1TRN = deviceAddr;
        while(I2C1STATbits.TRSTAT); // transmit is in progress (8-bits + ACK)
    } while(I2C1STATbits.ACKSTAT);  // slave is NACK
    INTERRUPT_I2C1_FLAG_CLR();
}

void I2C1ModuleSendStop(void) {
    I2C1CONbits.PEN = 1;
    I2C1CONbits.RCEN = 0;
    I2C1STATbits.IWCOL = 0;
    I2C1STATbits.BCL = 0;
    I2C1ModuleIdle();
    INTERRUPT_I2C1_FLAG_CLR();
}

void I2C1ModuleWrite(uint8_t data) {
    I2C1TRN = data;
    while(I2C1STATbits.TRSTAT);
    INTERRUPT_I2C1_FLAG_CLR();
}

void I2C1ModuleReadOP(uint8_t deviceAddr) {
    I2C1CONbits.SEN = 1;
    while(I2C1CONbits.SEN);
    INTERRUPT_I2C1_FLAG_CLR();
    do {
        I2C1TRN = I2C_OP_READ(deviceAddr);
        while(I2C1STATbits.TRSTAT);
    } while(I2C1STATbits.ACKSTAT);
    INTERRUPT_I2C1_FLAG_CLR();
}

uint8_t I2C1ModuleRead(void) {
    uint8_t data;
    I2C1CONbits.RCEN = 1;
    while(I2C1CONbits.RCEN);
    while(!I2C1STATbits.RBF);
    INTERRUPT_I2C1_FLAG_CLR();
    data = I2C1RCV;
    // can do check here
    I2C1CONbits.ACKEN = 1;
    INTERRUPT_I2C1_FLAG_CLR();
    return data;
}

void I2C1ModuleIdle(void) {
    while(I2C1CONbits.SEN ||
            I2C1CONbits.RSEN ||
            I2C1CONbits.PEN ||
            I2C1CONbits.RCEN ||
            I2C1CONbits.ACKEN ||
            I2C1STATbits.TRSTAT);
}

void Write33FJInstruction(uint8_t instruction) {
    I2C1ModuleIdle();
    I2C1ModuleWriteOP(DASINK_33FJ_HW_ADDRESS);
    I2C1ModuleWrite(instruction);
    I2C1ModuleSendStop();
}

uint8_t Read33FJResult(void) {
    uint8_t result = 0;
    I2C1ModuleReadOP(DASINK_33FJ_HW_ADDRESS);

    I2C1CONbits.RCEN = 1;
    while(I2C1CONbits.RCEN);
    result = I2C1RCV;
    I2C1CONbits.ACKEN = 1;
    I2C1CONbits.ACKDT = 1;

    I2C1ModuleSendStop();
    return result;
}

void _ISR_PSV _MI2C1Interrupt ( void ) {
    IFS1bits.MI2C1IF = 0;
}
