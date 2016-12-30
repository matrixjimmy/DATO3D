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

/*
 void I2C_init(void)
{
	int temp;
    TRISEbits.TRISE6 = 0;      // Make RA14 and Output  - SCL3 Digital Input
    ODCEbits.ODE6 = 1;			//Set as an open drain 
    LATEbits.RE6 = 0;         // Force Low 

    TRISEbits.TRISE7 = 0;      // Make RA15 and Output - SDA3 Digital Input
    ODCEbits.ODE7 = 1;			//Set as an open drain 
    LATEbits.RE7 = 0;         // Force Low  
	
	I2C3BRG = 0x47 ; // Fosc= 8 MHz, SCK=100 kHz	
	I2C3CONbits.DISSLW = 1;	// Disable slew rate control	
	I2C3CONbits.I2CEN = 1;	// Enable I2C Mode
	temp = I2C3RCV;	 // read buffer to clear buffer full
}

void I2C_idle(void)
{
    while(I2C1CONbits.SEN || 
		 I2C1CONbits.RSEN || 
		  I2C1CONbits.PEN || 
		 I2C1CONbits.RCEN || 
        I2C1CONbits.ACKEN || 
		I2C1STATbits.TRSTAT);
}

void I2C_start(void)
{
	I2C2CONbits.SEN = 1;
   int x = 0;
   I2C2CONbits.ACKDT = 0;
   I2C2CONbits.SEN = 1;
   Nop();
   while (I2C2CONbits.SEN)
   {
      Delay10us(1);
      x++;
      if (x > 20)
      break;
   }
   Delay10us(2);

}

void I2C_restart(void)
{
	I2C2CONbits.RSEN = 1;
	I2C_idle();			
}

void I2C_stop(void)
{
	I2C2CONbits.PEN = 1;	//initiate stop bit
	I2C2CONbits.RCEN = 0;	// Receives sequence not in progress
	I2C2STATbits.IWCOL = 0;	// No Write Collision Detect
	I2C2STATbits.BCL = 0;	// No Master Bus Collision Detect
	I2C_idle();				// Wait until I2C Bus is Inactive
}

int I2C_send_byte(int data)
{
	I2C2CONbits.SEN = 1;		// Set start bit
	while (!I2C2CONbits.SEN);	// wait until start bit = 0
	I2C2TRN = data; 			// load the outgoing data byte
	while (!I2C2STATbits.TRSTAT);	// wait until master transmit is not inprograss = 0
	
	if( I2C2STATbits.ACKSTAT ) // NACK was detected last
	{
		I2C2CONbits.PEN = 1;		// Set stop bit
		return 1;					// ret 1 if NAK
	}
	I2C2CONbits.PEN = 1;		// Set stop bit

}

unsigned char I2C_receive_byte(char ack)
{
	unsigned char data = 0;

	I2C_idle();					// Wait until I2C Bus is Inactive
	I2C1CONbits.RCEN = 1;	//set I2C module to receive
	while(I2C1CONbits.RCEN);
	
	while (!I2C1STATbits.RBF)	//wait for response
	{	Nop();	}

	I2C1STATbits.I2COV = 0;

	data = I2C1RCV;

	I2C1CONbits.ACKDT = ack;
	I2C1CONbits.ACKEN = 1;
	while(I2C1CONbits.ACKEN == 1);

	return data;	//return data
}

void I2C_write(char addr, char subaddr, char value)
{
	I2C_start();
	I2C_send_byte(addr);
	I2C_send_byte(subaddr);
	I2C_send_byte(value);
	I2C_stop();
}

unsigned char I2C_read(char addr, char subaddr)
{
	unsigned char temp;
	I2C_start();
	I2C_send_byte(addr);
	I2C_send_byte(subaddr);
	I2C_restart();
	I2C_send_byte(addr | 0x01);
	temp = I2C_receive_byte(I2C_NACK);
	
	I2C_stop();
	
  return temp;
}

char I2C_ping(int addr)
{
  unsigned char temp;
   I2C_start();
   temp = I2C_send_byte(addr);
   I2C_stop();
  return temp;
}

void I2CwriteByte(char Address, char Register, char Data)
{
	I2C_start();
	I2C_send_byte(Address);  // Set register address
	I2C_send_byte(Register);
	I2C_send_byte(Data);
	I2C_stop();
}

void I2Cread(char Address, char Register, char Nbytes, char* DataI2C)
{
	I2C_start();
	I2C_send_byte(Address);  // Set register address
	I2C_send_byte(Register);
	I2C_stop();
  I2C_read(Address, Nbytes); 
  char index=0;
  while (index!=Nbytes)
    DataI2C[index++]=I2C_receive_byte(I2C_NACK);
}


 */