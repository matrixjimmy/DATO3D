#include "constants.h"

extern bool QEIMotor_Enable;
extern uint_fast32_t QEI1_Total;
extern uint_fast16_t QEI1Diff;

extern uint_fast32_t QEI2_Total;
extern uint_fast16_t QEI2Diff;

static uint_fast16_t QEI1_L;
static uint_fast16_t QEI1_H;
static uint_fast16_t QEI1Current;
static uint_fast16_t QEI1Previous;
static uint_fast16_t checkQEI1carry;

static uint_fast16_t QEI2_L;
static uint_fast16_t QEI2_H;
static uint_fast16_t QEI2Current;
static uint_fast16_t QEI2Previous;
static uint_fast16_t checkQEI2carry;

void Read32bitQEI1_Up(void) {
    QEI1Current = POS1CNT;
    QEI1_L = QEI1Current;
    if (QEI1Current >= QEI1Previous) {
        if (checkQEI1carry < 500) {
            checkQEI1carry++;
        }
        QEI1_Total = (((uint_fast32_t)QEI1_H << 16) + QEI1Current);
        QEI1Diff = QEI1Current - QEI1Previous;
    } else {
        if (checkQEI1carry >= 500) {
            QEI1_H++;
            checkQEI1carry = 0;
        }
        QEI1_Total = (((uint_fast32_t)QEI1_H << 16) + QEI1Current);
        QEI1Diff = QEI1Current + (UINT_FAST16_MAX - QEI1Previous);
    }
    QEI1Previous = QEI1Current;
}

void Read32bitQEI1_Down(void) {
    QEI1Current = POS1CNT;
    QEI1_L = QEI1Current;
    if (QEI1Current < QEI1Previous) {
        if (checkQEI1carry < 500) {
            checkQEI1carry++;
        }
    } else {
        if (checkQEI1carry >= 500) {
            QEI1_H--;
            checkQEI1carry = 0;
        }
    }
    QEI1_Total = (((uint_fast32_t)QEI1_H << 16) + QEI1Current);
    QEI1Previous = QEI1Current;
}

void Read32bitQEI2_Up(void) {
    QEI2Current = POS2CNT;

    QEI2_L = QEI2Current;
    if (QEI2Current >= QEI2Previous) {
        if (checkQEI2carry < 500) {
            checkQEI2carry++;
        }
        QEI2_Total = (((uint_fast32_t)QEI2_H << 16) + QEI2Current);
        QEI2Diff = QEI2Current - QEI2Previous;
    } else {
        if (checkQEI2carry >= 500) {
            QEI2_H++;
            checkQEI2carry = 0;
        }
        QEI2_Total = (((uint_fast32_t)QEI2_H << 16) + QEI2Current);
        QEI2Diff = QEI2Current + (65536 - QEI2Previous);
    }
    QEI2Previous = QEI2Current;
}

void Read32bitQEI2_Down(void) {
    QEI2Current = POS2CNT;
    QEI2_L = QEI2Current;
    if (QEI2Current < QEI2Previous) {
        if (checkQEI2carry < 500) {
            checkQEI2carry++;
        }
    } else {
        if (checkQEI2carry >= 500) {
            QEI2_H--;
            checkQEI2carry = 0;
        }
    }
    QEI2_Total = (((uint_fast32_t)QEI2_H << 16) + QEI2Current);
    QEI2Previous = QEI2Current;
}
