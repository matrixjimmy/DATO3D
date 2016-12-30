/*
 * File:   MCP23008.h
 * Author: jimmy
 *
 * Created on November 16, 2016, 4:27 PM
 */

#ifndef MCP23008_H
#define	MCP23008_H

#ifdef	__cplusplus
extern "C" {
#endif
//                                            AAA
//                                            210
#define MCP23008_HW_ADDRESS             0b0100000

#define MCP23008_REG_IODIR_ADDRESS      0x00
#define MCP23008_REG_IPOL_ADDRESS       0x01
#define MCP23008_REG_GPINTEN_ADDRESS    0x02
#define MCP23008_REG_DEFVAL_ADDRESS     0x03
#define MCP23008_REG_INTCON_ADDRESS     0x04
#define MCP23008_REG_IOCON_ADDRESS      0x05
#define MCP23008_REG_GPPU_ADDRESS       0x06
#define MCP23008_REG_INTF_ADDRESS       0x07
#define MCP23008_REG_INTCAP_ADDRESS     0x08
#define MCP23008_REG_GPIO_ADDRESS       0x09
#define MCP23008_REG_OLAT_ADDRESS       0x0A


#ifdef	__cplusplus
}
#endif

#endif	/* MCP23008_H */

