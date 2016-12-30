/*
 * File:   DTG.h
 * Author: jim
 *
 * Created on 2016年6月9日, 下午 8:52
 */

#ifndef DTG_H
#define	DTG_H

    void Init_DTGState(void);
    enum DTG_STATE ReadDTG_State(void);
    void ReadDTG_State_interrupt(void);
    void DTG_Start(void);
    void DTG_Setting(void);

    enum DTG_STATE {
        DTG_PowerOff    = 0x00,
        DTG_PowerOn     = 0x01,
        DTG_Busy        = 0x02,
        DTG_NoPaper     = 0x03,
        DTG_PaperJam    = 0x04,
        DTG_Error       = 0x05,
    };

    struct DTG {
        uint16_t LED_IO[3];
        uint16_t LED_T1[3];
        uint16_t LED_T2[3];
        uint16_t LED_State[3];
        uint16_t State[3];
    };

    extern uint16_t DTG_Print_Quantity;
    extern struct DTG dtg;
#endif
