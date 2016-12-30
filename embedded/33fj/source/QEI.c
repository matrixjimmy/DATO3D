#include "constants.h"

extern bool QEIMotor_Enable;
extern uint_fast32_t QEI1_Total;
extern uint_fast16_t QEI1Diff;

extern uint_fast32_t QEI2_Total;
extern uint_fast16_t QEI2Diff;

//***********************區域變數********************************/
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
    QEI1Current = POS1CNT; //存下目前QEI的數值，以免在real time中個別讀到不同的值

    QEI1_L = QEI1Current; //存下QEI的總共步數 ，存下低位元組
    if (QEI1Current >= QEI1Previous) { //讀取QEI的數值，比之前的大(代表往前跑,沒有遇到進位的問題)
        if (checkQEI1carry < 500) { //進位機制，以避免短時間又進位
            checkQEI1carry++; //進位機制用
        }
        QEI1_Total = (((uint_fast32_t)QEI1_H << 16) + QEI1Current); //算出QEI 32bit的值
        QEI1Diff = QEI1Current - QEI1Previous; //算出與前一次QEI的差值，輸入步進馬達
    } else { //if (QEI1Current < QEI1Previous) { //讀取QEI的數值，比之前的小(代表往前跑,遇到進位的瞬間)
        //存下QEI的總共步數 ,QEI進位的時候
        if (checkQEI1carry >= 500) { //進位機制，以避免短時間又進位
            QEI1_H++; //存下QEI的總共步數 ，高位元組進位
            checkQEI1carry = 0; //進位機制用
        }
        QEI1_Total = (((uint_fast32_t)QEI1_H << 16) + QEI1Current); //算出QEI 32bit的值
        QEI1Diff = QEI1Current + (UINT_FAST16_MAX - QEI1Previous); //算出與前一次QEI的差值，輸入步進馬達
    }
    QEI1Previous = QEI1Current; //更新算完QEI 16bit的值
}

void Read32bitQEI1_Down(void) {
    QEI1Current = POS1CNT; //存下目前QEI的數值，以免在real time中個別讀到不同的值
    QEI1_L = QEI1Current; //存下QEI的總共步數 ，存下低位元組
    if (QEI1Current < QEI1Previous) { //讀取QEI的數值，比之前的小(代表往後跑,沒有遇到進位的問題)
        if (checkQEI1carry < 500) { //進位機制，以避免短時間又進位
            checkQEI1carry++; //進位機制用
        }
    } else { //if (QEI1Current > QEI1Previous) { //讀取QEI的數值，比之前的大(代表往後跑,遇到退位的瞬間)
        if (checkQEI1carry >= 500) { //進位機制，以避免短時間又進位
            QEI1_H--; //存下QEI的總共步數 ，高位元組退位
            checkQEI1carry = 0; //進位機制用
        }
    }
    QEI1_Total = (((uint_fast32_t)QEI1_H << 16) + QEI1Current); //最後算出QEI 32bit的值
    QEI1Previous = QEI1Current; //更新算完QEI 16bit的值
}

void Read32bitQEI2_Up(void) {
    QEI2Current = POS2CNT; //存下目前QEI的數值，以免在real time中個別讀到不同的值

    QEI2_L = QEI2Current; //存下QEI的總共步數 ，存下低位元組
    if (QEI2Current >= QEI2Previous) { //讀取QEI的數值，比之前的大(代表往前跑,沒有遇到進位的問題)
        if (checkQEI2carry < 500) { //進位機制，以避免短時間又進位
            checkQEI2carry++; //進位機制用
        }
        QEI2_Total = (((uint_fast32_t)QEI2_H << 16) + QEI2Current); //算出QEI 32bit的值
        QEI2Diff = QEI2Current - QEI2Previous; //算出與前一次QEI的差值，輸入步進馬達
    } else { //if (QEI2Current < QEI2Previous) { //讀取QEI的數值，比之前的小(代表往前跑,遇到進位的瞬間)
        //存下QEI的總共步數 ,QEI進位的時候
        if (checkQEI2carry >= 500) { //進位機制，以避免短時間又進位
            QEI2_H++; //存下QEI的總共步數 ，高位元組進位
            checkQEI2carry = 0; //進位機制用
        }
        QEI2_Total = (((uint_fast32_t)QEI2_H << 16) + QEI2Current); //算出QEI 32bit的值
        QEI2Diff = QEI2Current + (65536 - QEI2Previous); //算出與前一次QEI的差值，輸入步進馬達
    }
    QEI2Previous = QEI2Current; //更新算完QEI 16bit的值
}

void Read32bitQEI2_Down(void) {
    QEI2Current = POS2CNT; //存下目前QEI的數值，以免在real time中個別讀到不同的值
    QEI2_L = QEI2Current; //存下QEI的總共步數 ，存下低位元組
    if (QEI2Current < QEI2Previous) { //讀取QEI的數值，比之前的小(代表往後跑,沒有遇到進位的問題)
        if (checkQEI2carry < 500) { //進位機制，以避免短時間又進位
            checkQEI2carry++; //進位機制用
        }
    } else { //if (QEI2Current > QEI2Previous) { //讀取QEI的數值，比之前的大(代表往後跑,遇到退位的瞬間)
        if (checkQEI2carry >= 500) { //進位機制，以避免短時間又進位
            QEI2_H--; //存下QEI的總共步數 ，高位元組退位
            checkQEI2carry = 0; //進位機制用
        }
    }
    QEI2_Total = (((uint_fast32_t)QEI2_H << 16) + QEI2Current); //最後算出QEI 32bit的值
    QEI2Previous = QEI2Current; //更新算完QEI 16bit的值
}