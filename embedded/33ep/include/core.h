/*
 * File:   core.h
 * Author: jimmy
 *
 * Created on December 9, 2016, 2:24 PM
 */

#ifndef CORE_H
#define	CORE_H

#ifdef	__cplusplus
extern "C" {
#endif

    enum CoreSignals {
        TIME_TICK_SIG = Q_USER_SIG,

        PC_INSTRUCTION_SIG,

        PROCESS_START_SIG,
        PROCESS_END_SIG,

        LAYER_START_SIG,
        LAYER_END_SIG,

        MAX_PUB_SIG,    /* the last published signal */

        POLLING_TIMEOUT_SIG,

        INSTRUCTION_RECEIVED_SIG,
        INSTRUCTION_RELAYED_SIG,

        PRINT_START_SIG,
        PRINT_END_SIG,
        POWDER_START_SIG,
        POWDER_END_SIG,

        LCD_DISPLAY_SIG,

        STEPPER_Z_STOP_SIG,
        STEPPER_Z_RUN_SIG,

        STEPPER_E_STOP_SIG,
        STEPPER_E_RUN_SIG,

        MAX_SIG         /* the last signal (keep always last) */
    };

    typedef struct {
        QEvt super;
        uint16_t ref_num;
        char *instruction;
    } InstructionEvt;

    enum StepperDirection {
        STEPPER_FORWARD,
        STEPPER_BACKWARD
    };

    typedef struct tagStepperRunEvt {
        QEvt super;
        uint16_t ref_num;
        int16_t requestStep;
    } StepperRequestEvt;

    void Platform_ctor(void);

    void Stepper_Z_ctor(void);
    void Stepper_E_ctor(void);

    /* opaque pointers to active objects in the application */
    extern QActive * const AO_Platform;
    extern QActive * const AO_stepper_z;
    extern QActive * const AO_stepper_e;

#ifdef	__cplusplus
}
#endif

#endif	/* CORE_H */

