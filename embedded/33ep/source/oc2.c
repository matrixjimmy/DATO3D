#include "constant.h"

extern bool eAtTop;
extern bool eAtBottom;
static uint32_t oc2CountSteps;
static bool (*doFunctionPointer)(void);

void INITIALIZE_OC2(uint32_t steps) {
    OC2CON1 = 0;
    OC2CON2 = 0;
    OC2TMR = 0;
    OC2RS = 10000;
    OC2R = 0;

    OC2CON2bits.SYNCSEL = 0b11111;
    OC2CON1bits.OCTSEL = 0b111;
    OC2CON1bits.OCM = 0b011;

    oc2CountSteps = steps;

    IFS0bits.OC2IF = false;
    IEC0bits.OC2IE = true;
}

void OC2_Start(void) {
    OC2CON1bits.OCM = 0x3;
}

void OC2_Stop(void) {
    oc2CountSteps = 0;
    OC2CON1bits.OCM = 0x0;
    OC2_InterruptFunctionSet(OC2_doDefault);
}

void OC2_PrimaryValueSet(uint32_t priVal) {
    OC2R = priVal;
}

void OC2_SecondaryValueSet(uint32_t secVal) {
    OC2RS = secVal;
}

void OC2_InterruptFunctionSet(bool (*functionPointer)(void)) {
    doFunctionPointer = functionPointer;
}

void _ISR_NO_PSV _OC2Interrupt(void) {
    IFS0bits.OC2IF = 0;
    if((*doFunctionPointer)()) {
        OC2_Stop();
    }
}

bool OC2_doDefault(void) {
    static uint32_t oc2CountCallBack = 0; /* count the output pulses */
    eAtTop = false;
    eAtBottom = false;
    if(++oc2CountCallBack == oc2CountSteps * 2) {
        BSP_display("OC2:done\n");
        oc2CountCallBack = 0;
        return true;
    }
    return false;
}

bool OC2_doUpMost(void) {
    static uint32_t oc2CountCallBack = 0; /* count the output pulses */
    if(0 == EPLW_GetValue() || ++oc2CountCallBack == oc2CountSteps * 2) {
        BSP_display("OC2:E Up Most Complete\n");
        return true;
    }
    return false;
}

bool OC2_doUpZero(void) {
    static uint32_t oc2CountCallBack = 0; /* count the output pulses */
    if(0 == EPLW_GetValue() || ++oc2CountCallBack == oc2CountSteps * 2) {
        uint16_t count = 0;
        OC2_Stop();
        BSP_display("OC2:E Up Zero Start:%lu\n",oc2CountCallBack);
        EDIR_SetHigh();
        do {
            EPUL_Toggle();
            __delay_ms(1);
            count++;
        } while(0 == EPLW_GetValue());
        eAtTop = true;
        eAtBottom = false;
        BSP_display("OC2:E Up Zero Complete:%u\n", count);
        oc2CountCallBack = 0;
        return true;
    }
    return false;
}

bool OC2_doDownZero(void) {
    static uint32_t oc2CountCallBack = 0; /* count the output pulses */
    if(0 == EMLW_GetValue() || ++oc2CountCallBack == oc2CountSteps * 2) {
        uint16_t count = 0;
        OC2_Stop();
        BSP_display("OC2:E Down Zero Start:%lu\n",oc2CountCallBack);
        EDIR_SetLow();
        do {
            EPUL_Toggle();
            __delay_ms(1);
            count++;
        } while(0 == EMLW_GetValue());
        eAtTop = false;
        eAtBottom = true;
        BSP_display("OC2:E Down Zero Complete:%u\n", count);
        oc2CountCallBack = 0;
        return true;
    }
    return false;
}

bool OC2_doDownMost(void) {
    static uint32_t oc2CountCallBack = 0; /* count the output pulses */
    if(0 == EMLW_GetValue() || ++oc2CountCallBack == oc2CountSteps * 2) {
        BSP_display("OC2:E Down Most Complete\n");
        return true;
    }
    return false;
}

bool OC2_doTopFlat(void) {
    static uint32_t oc2CountCallBack = 0; /* count the output pulses */
    if(0 == EPLW_GetValue() || ++oc2CountCallBack == oc2CountSteps * 2) {
        uint16_t count = 0;
        OC2_Stop();
        BSP_display("OC2:E Up Zero Start:%lu\n", oc2CountCallBack);
        EDIR_Toggle();
        do {
            EPUL_Toggle();
            __delay_ms(1);
            count++;
        } while(0 == EPLW_GetValue());
        eAtTop = true;
        eAtBottom = false;
        BSP_display("OC2:E Up Zero Complete:%u\n", count);
        oc2CountCallBack = 0;
        oc2CountSteps = 32330;
        OC2_InterruptFunctionSet(OC2_doDefault);
        OC2_Start();
    }
    return false;
}