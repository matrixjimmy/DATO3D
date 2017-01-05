
#include "constant.h"
#include "gcode.h"
#include "protocol-i2c.h"

/**
 * with line separator
 * @param msg
 * @param ...
 */
void BSP_display(const char* msg, ...) {
#if __DEBUG
    va_list argptr;
    va_start(argptr, msg);
    vfprintf(stdout, msg, argptr);
    va_end(argptr);
    printf("\n");
#endif
}

extern bool zAtTop;
extern bool zAtBottom;
extern bool eAtTop;
extern bool eAtBottom;

void BSP_GCodeParser(char * command) {
    char * cmd = strtok(command, " ");
    BSP_display("GCODE:[from PC]:%s", cmd);
    if(COMPARE_WITH_CODE(cmd, E00)) {
        Write33FJInstruction(RESET);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G00)) {
        BSP_display("GCODE:[to 33FJ]Press Power Button");
        Write33FJInstruction(PRESS_PWR);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G01)) {
        BSP_display("GCODE:[to 33FJ]Press Ink Button");
        Write33FJInstruction(PRESS_INK);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G02)) {
        BSP_display("GCODE:[to 33FJ]Press Paper Button");
        Write33FJInstruction(PRESS_PAPER);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G03)) {
        BSP_display("GCODE:[to 33FJ]Press Delete Button");
        Write33FJInstruction(PRESS_DELETE);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G04)) {
        BSP_display("GCODE:[to 33FJ]Turn On Printer");
        Write33FJInstruction(TURN_ON_PRINTER);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G05)) {
        BSP_display("GCODE:[to 33FJ]Turn Off Printer");
        Write33FJInstruction(TURN_OFF_PRINTER);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G06)) {
        BSP_display("GCODE:[to 33FJ]Start Printing");
        Write33FJInstruction(START_PRINT);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G07)) {
        BSP_display("GCODE:[to 33FJ]Stop Printing");
        Write33FJInstruction(STOP_PRINT);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G08)) {
        BSP_display("GCODE:[to 33FJ]Go to Start Position");
        Write33FJInstruction(GO_TO_START);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G09)) {
        BSP_display("GCODE:[to 33FJ]Go to End Position");
        Write33FJInstruction(GO_TO_END);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G0A)) {
        BSP_display("GCODE:[to 33FJ]Powder Start");
        Write33FJInstruction(POWDER_START);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G0B)) {
        BSP_display("GCODE:[to 33FJ]Servo Up");
        Write33FJInstruction(SERVO_UP);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G0C)) {
        BSP_display("GCODE:[to 33FJ]Servo Down");
        Write33FJInstruction(SERVO_DOWN);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G0D)) {
        BSP_display("GCODE:[to 33FJ]Servo Stop");
        Write33FJInstruction(SERVO_STOP);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G0E)) {
        BSP_display("GCODE:[to 33FJ]Roller Forward");
        Write33FJInstruction(ROLLER_FORWARD);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G0F)) {
        BSP_display("GCODE:[to 33FJ]Roller Stop");
        Write33FJInstruction(ROLLER_STOP);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G0G)) {
        BSP_display("GCODE:[to 33FJ]Roller Forward");
        Write33FJInstruction(ROLLER_BACKWARD);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G10)) {
        BSP_display("GCODE:Go to Z TOP");
        ZDIR_SetLow();
        OC1_InterruptFunctionSet(OC1_doUpZero);
        INITIALIZE_OC1(UINT_FAST32_MAX);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G11)) {
        cmd = strtok(NULL, " ");
        uint32_t step = atoll(cmd);
        BSP_display("GCODE:Z Up Step");
        ZDIR_SetLow();
        OC1_InterruptFunctionSet(OC1_doDefault);
        INITIALIZE_OC1(step);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G12)) {
        cmd = strtok(NULL, " ");
        uint32_t step = atoll(cmd);
        BSP_display("GCODE:Z Down Step");
        ZDIR_SetHigh();
        OC1_InterruptFunctionSet(OC1_doDefault);
        INITIALIZE_OC1(step);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G13)) {
        BSP_display("GCODE:Go to Z BOTTOM");
        ZDIR_SetHigh();
        OC1_InterruptFunctionSet(OC1_doDownZero);
        INITIALIZE_OC1(UINT_FAST32_MAX);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G14)) {
        BSP_display("GCODE:Go to Z top Flat");
        ZDIR_SetLow();
        OC1_InterruptFunctionSet(OC1_doTopFlat);
        INITIALIZE_OC1(UINT_FAST32_MAX);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G20)) {
        BSP_display("GCODE:Go to E TOP");
        EDIR_SetLow();
        OC2_InterruptFunctionSet(OC2_doUpZero);
        INITIALIZE_OC2(UINT_FAST32_MAX);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G21)) {
        cmd = strtok(NULL, " ");
        uint32_t step = atoll(cmd);
        BSP_display("GCODE:E Up Step");
        EDIR_SetLow();
        OC2_InterruptFunctionSet(OC2_doDefault);
        INITIALIZE_OC2(step);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G22)) {
        cmd = strtok(NULL, " ");
        uint32_t step = atoll(cmd);
        BSP_display("GCODE:E Down Step");
        EDIR_SetHigh();
        OC2_InterruptFunctionSet(OC2_doDefault);
        INITIALIZE_OC2(step);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G23)) {
        BSP_display("GCODE:Go to E BOTTOM");
        EDIR_SetHigh();
        OC2_InterruptFunctionSet(OC2_doDownZero);
        INITIALIZE_OC2(UINT_FAST32_MAX);
        return;
    } else if(COMPARE_WITH_CODE(cmd, G24)) {
        BSP_display("GCODE:Go to E top Flat");
        EDIR_SetLow();
        OC2_InterruptFunctionSet(OC2_doTopFlat);
        INITIALIZE_OC2(UINT_FAST32_MAX);
        return;
    } else if(COMPARE_WITH_CODE(cmd, M00)) {
        BSP_display("GCODE:Get System(33EP) Status");
        uint_fast8_t rtn = 0;
        if(OC1CON1bits.OCM != 0) {
            rtn |= (0b1 << 0);
        }
        if(zAtBottom) {
            rtn |= (0b1 << 1);
        }
        if(zAtTop) {
            rtn |= (0b1 << 2);
        }
        if(OC2CON1bits.OCM != 0) {
            rtn |= (0b1 << 3);
        }
        if(eAtBottom) {
            rtn |= (0b1 << 4);
        }
        if(eAtTop) {
            rtn |= (0b1 << 5);
        }
        printf("%3u", rtn);
        return;
    } else if(COMPARE_WITH_CODE(cmd, M01)) {
        BSP_display("GCODE:Get System(33FJ) Status");
        Write33FJInstruction(READ_STAT);
        printf("%3u", Read33FJResult());
        return;
    } else {
        BSP_display("GCODE: cannot parse");
    }
}
