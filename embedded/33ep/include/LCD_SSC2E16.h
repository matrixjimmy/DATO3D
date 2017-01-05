/*
 * File:   LCD_SSC2E16.h
 * Author: jimmy
 *
 * Created on November 16, 2016, 4:56 PM
 */

#ifndef LCD_SSC2E16_H
#define	LCD_SSC2E16_H

#ifdef	__cplusplus
extern "C" {
#endif

#define LCD_INSTRUCTION_CLS     0b00000001

#define LCD_INSTRUCTION_HOME    0b00000010

#define LCD_INSTRUCTION_ENTRY   0b00000111
#define LCD_INSMASK_INC         0b00000111
#define LCD_INSMASK_DEC         0b00000101
#define LCD_INSMASK_SHIFT_NORM  0b00000110

#define LCD_INSTRUCTION_DISP    0b00001111
#define LCD_INSMASK_DISPLAY_ON  0b00001111
#define LCD_INSMASK_DISPLAY_OFF 0b00001011
#define LCD_INSMASK_CURSOR_ON   0b00001110
#define LCD_INSMASK_CURSOR_OFF  0b00001100
#define LCD_INSMASK_BLINK_ON    0b00001111
#define LCD_INSMASK_BLINK_OFF   0b00001110

#define LCD_INSTRUCTION_SHIFT   0b00011100
#define LCD_INSMASK_DISP_SHIFT  0b00011100
#define LCD_INSMASK_CURSOR_MOVE 0b00010100
#define LCD_INSMASK_SHIFT_LEFT  0b00011100
#define LCD_INSMASK_SHIFT_RIGHT 0b00011000

#define LCD_INSTRUCTION_FN_SET  0b00111100
#define LCD_INSMASK_8_BITs      0b00111100
#define LCD_INSMASK_4_BITs      0b00101100
#define LCD_INSMASK_2_LINE      0b00111100
#define LCD_INSMASK_1_LINE      0b00110100
#define LCD_INSMASK_5x7_DOTs    0b00111100
#define LCD_INSMASK_5x10_DOTs   0b00111000

#define LCD_INSMASK_CGRAM_ADDR(addr_)  ((1<<6)| (0b111111&addr_))
#define LCD_INSMASK_DDRAM_ADDR(data_)  ((1<<7)|(0b1111111&data_))

#ifdef LCD_DISP_LINE
    enum LCD_Line { // value as base ddram address
        LCD_LINE_1 = 0x00,
        LCD_LINE_2 = 0x40,
    #if LCD_DISP_LINE > 2
        LCD_LINE_3 = 0x00,
        LCD_LINE_4 = 0x00,
    #endif
    };
#endif

    inline static void LCD_setE(void) {
        LCD_E_SetHigh();
    }

    inline static void LCD_unsetE(void) {
        LCD_E_SetLow();
    }

    inline static void LCD_setRS(void) {
        LCD_RS_SetHigh();
    }

    inline static void LCD_unsetRS(void) {
        LCD_RS_SetLow();
    }

    inline static void LCD_setRead(void) {
        LCD_RW_SetHigh();
    }

    inline static void LCD_setWrite(void) {
        LCD_RW_SetLow();
    }


#ifdef	__cplusplus
}
#endif

#endif	/* LCD_SSC2E16_H */
