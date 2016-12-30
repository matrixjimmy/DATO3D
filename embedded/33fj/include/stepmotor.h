/*
 * File:   stepmotor.h
 * Author: jim
 *
 * Created on 2016年6月22日, 上午 11:08
 */

#ifndef STEPMOTOR_H
#define	STEPMOTOR_H

    void setStepperDirection (int32_t pulse);
    void pollingPrinterEncoderToRegister(void);
    void pollingStepperPulse(void);
    void MoterRunSlow(void);
    void setStepMotorSlowRunToStart(bool EN);
    void setStepMotorSlowRunToEnd(bool EN);

#endif

