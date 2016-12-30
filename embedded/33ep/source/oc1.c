#include "constant.h"

extern bool zAtTop;
extern bool zAtBottom;
static uint32_t oc1CountSteps;
static bool (*doFunctionPointer)(void);

void INITIALIZE_OC1(uint32_t steps) {
    OC1CON1 = 0;
    OC1CON2 = 0;
    OC1TMR = 0;
    OC1RS = 10000;
    OC1R = 0;

    OC1CON2bits.SYNCSEL = 0b11111;
    OC1CON1bits.OCTSEL = 0b111;
    OC1CON1bits.OCM = 0b011;

    oc1CountSteps = steps;

    IFS0bits.OC1IF = false;
    IEC0bits.OC1IE = true;
}

void OC1_Start(void) {
    OC1CON1bits.OCM = 0x3;
}

void OC1_Stop(void) {
    oc1CountSteps = 0;
    OC1CON1bits.OCM = 0x0;
    OC1_InterruptFunctionSet(OC1_doDefault);
}

void OC1_PrimaryValueSet(uint32_t priVal) {
    OC1R = priVal;
}

void OC1_SecondaryValueSet(uint32_t secVal) {
    OC1RS = secVal;
}

void OC1_InterruptFunctionSet(bool (*functionPointer)(void)) {
    doFunctionPointer = functionPointer;
}

void _ISR_NO_PSV _OC1Interrupt(void) {
    IFS0bits.OC1IF = 0;
    if((*doFunctionPointer)()) {
        OC1_Stop();
    }
}

bool OC1_doDefault(void) {
    static uint32_t oc1CountCallBack = 0; /* count the output pulses */
    zAtBottom = false;
    zAtTop = false;
    if(++oc1CountCallBack == oc1CountSteps * 2) {
        BSP_display("OC1:done\n");
        oc1CountCallBack = 0;
        return true;
    }
    return false;
}

bool OC1_doUpMost(void) {
    static uint32_t oc1CountCallBack = 0; /* count the output pulses */
    if(0 == ZPLW_GetValue() || ++oc1CountCallBack == oc1CountSteps * 2) {
        BSP_display("OC1:Z Up Most Complete\n");
        return true;
    }
    return false;
}

bool OC1_doUpZero(void) {
    static uint32_t oc1CountCallBack = 0; /* count the output pulses */
    if(0 == ZPLW_GetValue() || ++oc1CountCallBack == oc1CountSteps * 2) {
        uint16_t count = 0;
        OC1_Stop();
        BSP_display("OC1:Z Up Zero Start:%lu\n", oc1CountCallBack);
        ZDIR_Toggle();
        do {
            ZPUL_Toggle();
            __delay_ms(1);
            count++;
        } while(0 == ZPLW_GetValue());
        zAtTop = true;
        zAtBottom = false;
        BSP_display("OC1:Z Up Zero Complete:%u\n", count);
        oc1CountCallBack = 0;
        return true;
    }
    return false;
}

bool OC1_doDownZero(void) {
    static uint32_t oc1CountCallBack = 0; /* count the output pulses */
    if(0 == ZMLW_GetValue() || ++oc1CountCallBack == oc1CountSteps * 2) {
        uint16_t count = 0;
        OC1_Stop();
        BSP_display("OC1:Z Down Zero Start:%lu\n", oc1CountCallBack);
        ZDIR_Toggle();
        do {
            ZPUL_Toggle();
            __delay_ms(1);
            count++;
        } while(0 == ZMLW_GetValue());
        zAtTop = false;
        zAtBottom = true;
        BSP_display("OC1:Z Down Zero Complete:%u\n", count);
        oc1CountCallBack = 0;
        return true;
    }
    return false;
}

bool OC1_doDownMost(void) {
    static uint32_t oc1CountCallBack = 0; /* count the output pulses */
    if(0 == ZMLW_GetValue() || ++oc1CountCallBack == oc1CountSteps * 2) {
        BSP_display("OC1:Z Down Most Complete\n");
        return true;
    }
    return false;
}

bool OC1_doTopFlat(void) {
    static uint32_t oc1CountCallBack = 0; /* count the output pulses */
    if(0 == ZPLW_GetValue() || ++oc1CountCallBack == oc1CountSteps * 2) {
        uint16_t count = 0;
        OC1_Stop();
        BSP_display("OC1:Z Up Zero Start:%lu\n", oc1CountCallBack);
        ZDIR_Toggle();
        do {
            ZPUL_Toggle();
            __delay_ms(1);
            count++;
        } while(0 == ZPLW_GetValue());
        zAtTop = true;
        zAtBottom = false;
        BSP_display("OC1:Z Up Zero Complete:%u\n", count);
        oc1CountCallBack = 0;
        oc1CountSteps = 39894;
        OC1_InterruptFunctionSet(OC1_doDefault);
        OC1_Start();
    }
    return false;
}