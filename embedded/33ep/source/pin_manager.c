#include "constant.h"

/**
    void PIN_MANAGER_Initialize(void)
*/
void PIN_MANAGER_Initialize(void)
{
    /****************************************************************************
     * Setting the Output Latch SFR(s)
     ***************************************************************************/
    LATA = 0x0000;
    LATB = 0x0380;
    LATC = 0x2DC2;
    LATD = 0x0000;
    LATE = 0x0000;
    LATF = 0x0000;
    LATG = 0x0000;

    /****************************************************************************
     * Setting the GPIO Direction SFR(s)
     ***************************************************************************/
    TRISA = 0x1F93;
    TRISB = 0xDC77;
    TRISC = 0x1235;
    TRISD = 0x0160;
    TRISE = 0x8000;
    TRISF = 0x0003;
    TRISG = 0x03C0;

    /****************************************************************************
     * Setting the Weak Pull Up and Weak Pull Down SFR(s)
     ***************************************************************************/
    CNPDA = 0x0802;
    CNPDB = 0x4002;
    CNPDC = 0x0000;
    CNPDD = 0x0000;
    CNPDE = 0x0000;
    CNPDF = 0x0000;
    CNPDG = 0x0340;
    CNPUA = 0x1081;
    CNPUB = 0x8001;
    CNPUC = 0x0000;
    CNPUD = 0x0000;
    CNPUE = 0x0000;
    CNPUF = 0x0000;
    CNPUG = 0x0080;

    /****************************************************************************
     * Setting the Open Drain SFR(s)
     ***************************************************************************/
    ODCA = 0x0000;
    ODCB = 0x0000;
    ODCC = 0x0000;
    ODCD = 0x0000;
    ODCE = 0x0000;
    ODCF = 0x0000;
    ODCG = 0x0000;

    /****************************************************************************
     * Setting the Analog/Digital Configuration SFR(s)
     ***************************************************************************/
    ANSELA = 0x0000;
    ANSELB = 0x0000;
    ANSELC = 0x0000;
    ANSELE = 0x0000;

    /****************************************************************************
     * Set the PPS
     ***************************************************************************/
    __builtin_write_OSCCONL(OSCCON & 0xbf); // unlock PPS

    RPOR0bits.RP35R = 0x0003;   //RB3->UART2:U2TX;
    RPINR19bits.U2RXR = 0x0022;   //RB2->UART2:U2RX;
    RPOR2bits.RP39R = 0x0011;   //RB7->OC2:OC2;
    RPOR3bits.RP40R = 0x0010;   //RB8->OC1:OC1;
    RPOR3bits.RP41R = 0x0012;   //RB9->OC3:OC3;
    //RPOR5bits.RP55R = 0x0012;   //RC7->OC3:OC3;

    __builtin_write_OSCCONL(OSCCON | 0x40); // lock   PPS

    /****************************************************************************
     * Interrupt On Change for group CNENA - any
     ***************************************************************************/
  	CNENAbits.CNIEA0 = 1; // Pin : RA0
  	CNENAbits.CNIEA1 = 1; // Pin : RA1
  	CNENAbits.CNIEA11 = 1; // Pin : RA11
  	CNENAbits.CNIEA12 = 1; // Pin : RA12
  	CNENAbits.CNIEA7 = 1; // Pin : RA7

    /****************************************************************************
     * Interrupt On Change for group CNENB - any
     ***************************************************************************/
  	CNENBbits.CNIEB0 = 1; // Pin : RB0
  	CNENBbits.CNIEB1 = 1; // Pin : RB1
  	CNENBbits.CNIEB14 = 1; // Pin : RB14
  	CNENBbits.CNIEB15 = 1; // Pin : RB15

    /****************************************************************************
     * Interrupt On Change for group CNENE - any
     ***************************************************************************/
	  CNENEbits.CNIEE15 = 1; // Pin : RE15

    /****************************************************************************
     * Interrupt On Change for group CNENG - any
     ***************************************************************************/
  	CNENGbits.CNIEG6 = 1; // Pin : RG6
  	CNENGbits.CNIEG7 = 1; // Pin : RG7
  	CNENGbits.CNIEG8 = 1; // Pin : RG8
  	CNENGbits.CNIEG9 = 1; // Pin : RG9

    IEC1bits.CNIE = 1; // Enable CNI interrupt
}

/* Interrupt service routine for the CNI interrupt. */
void _ISR_NO_PSV _CNInterrupt (void) {
    if(IFS1bits.CNIF == 1) {
        // Clear the flag
        IFS1bits.CNIF = 0;
    }
}
