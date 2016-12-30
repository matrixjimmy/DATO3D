/*
 * File:   oc.h
 * Author: jimmy
 *
 * Created on November 18, 2016, 4:56 PM
 */

#ifndef OC_H
#define	OC_H

#ifdef	__cplusplus
extern "C" {
#endif

#define OCxR_MIN    10 / 1000000 * FCY / 2

    void INITIALIZE_OC1(uint32_t steps);
    void OC1_Start(void);
    void OC1_Stop(void);
    void OC1_PrimaryValueSet(uint32_t priVal);
    void OC1_SecondaryValueSet(uint32_t secVal);
    bool OC1_IsCompareCycleComplete(void);
    void OC1_InterruptFunctionSet(bool (*functionPointer)(void));
    bool OC1_doDefault(void);
    bool OC1_doUpMost(void);
    bool OC1_doUpZero(void);
    bool OC1_doDownZero(void);
    bool OC1_doDownMost(void);
    bool OC1_doTopFlat(void);

    void INITIALIZE_OC2(uint32_t steps);
    void OC2_Start(void);
    void OC2_Stop(void);
    void OC2_PrimaryValueSet(uint32_t priVal);
    void OC2_SecondaryValueSet(uint32_t secVal);
    bool OC2_IsCompareCycleComplete(void);
    void OC2_InterruptFunctionSet(bool (*functionPointer)(void));
    bool OC2_doDefault(void);
    bool OC2_doUpMost(void);
    bool OC2_doUpZero(void);
    bool OC2_doDownZero(void);
    bool OC2_doDownMost(void);
    bool OC2_doTopFlat(void);

#ifdef	__cplusplus
}
#endif

#endif	/* OC_H */

