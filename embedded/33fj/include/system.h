/******************************************************************************/
/* System Level #define Macros                                                */
/******************************************************************************/

/* System wide Setup */
void SystemSetup(void);
/* Handles clock switching/osc initialization */
void ConfigureOscillator(void);
void ConfigurePeripheral(void);
void ConfigureTimer1(void);
void ConfigureTimer2(void);
void ConfigureTimer3(void);
void ConfigureTimer4(void);
void ConfigureTimer5(void);
void ConfigureQEI1(void);
void ConfigureI2C(void);
void ConfigureInputCapture1(void);
void ConfigureOutputCompare1(void);
void ConfigureOutputCompare2(void);
void ConfigureUART1(void);


/**
  Section: Type defines
 */

/**
 * CORCON initialization type enumerator. Supported types:
 * CORCON_MODE_PORVALUES
 * CORCON_MODE_ENABLEALLSATNORMAL_ROUNDBIASED
 * CORCON_MODE_ENABLEALLSATNORMAL_ROUNDUNBIASED
 * CORCON_MODE_DISABLEALLSAT_ROUNDBIASED
 * CORCON_MODE_DISABLEALLSAT_ROUNDUNBIASED
 * CORCON_MODE_ENABLEALLSATSUPER_ROUNDBIASED
 * CORCON_MODE_ENABLEALLSATSUPER_ROUNDUNBIASED
 */
typedef enum tagCORCON_MODE_TYPE
{
    CORCON_MODE_PORVALUES   = 0x0020,                       /** Use POR values of CORCON */
    CORCON_MODE_ENABLEALLSATNORMAL_ROUNDBIASED = 0x00E2,    /** Enable saturation for ACCA, ACCB
                                                             *  and Dataspace write, enable normal
                                                             *  ACCA/ACCB saturation mode and set
                                                             *  rounding to Biased (conventional)
                                                             *  mode. Rest of CORCON settings are
                                                             *  set to the default POR values.
                                                             *  */
    CORCON_MODE_ENABLEALLSATNORMAL_ROUNDUNBIASED = 0x00E0,  /** Enable saturation for ACCA, ACCB
                                                             *  and Dataspace write, enable normal
                                                             *  ACCA/ACCB saturation mode and set
                                                             *  rounding to Unbiased (convergent)
                                                             *  mode. Rest of CORCON settings are
                                                             *  set to the default POR values.
                                                             *  */
    CORCON_MODE_DISABLEALLSAT_ROUNDBIASED = 0x0022,         /** Disable saturation for ACCA, ACCB
                                                             *  and Dataspace write and set
                                                             *  rounding to Biased (conventional)
                                                             *  mode. Rest of CORCON settings are
                                                             *  set to the default POR values.
                                                             *  */
    CORCON_MODE_DISABLEALLSAT_ROUNDUNBIASED = 0x0020,       /** Disable saturation for ACCA, ACCB
                                                             *  and Dataspace write and set
                                                             *  rounding to Unbiased (convergent)
                                                             *  mode. Rest of CORCON settings are
                                                             *  set to the default POR values.
                                                             *  */
    CORCON_MODE_ENABLEALLSATSUPER_ROUNDBIASED = 0x00F2,    /** Enable saturation for ACCA, ACCB
                                                             *  and Dataspace write, enable super
                                                             *  ACCA/ACCB saturation mode and set
                                                             *  rounding to Biased (conventional)
                                                             *  mode. Rest of CORCON settings are
                                                             *  set to the default POR values.
                                                             *  */
    CORCON_MODE_ENABLEALLSATSUPER_ROUNDUNBIASED = 0x00F0,  /** Enable saturation for ACCA, ACCB
                                                             *  and Dataspace write, enable super
                                                             *  ACCA/ACCB saturation mode and set
                                                             *  rounding to Unbiased (convergent)
                                                             *  mode. Rest of CORCON settings are
                                                             *  set to the default POR values.
                                                             *  */
} SYSTEM_CORCON_MODES;

/**
 * RCON error type enumerator. Supported types:
 * ERR_RCON_TRAPR
 * ERR_RCON_IOPUWR
 * ERR_RCON_CM
 * ERR_RCON_WDTO_ISR
 */
typedef enum tagERROR_TYPE
{
    ERR_RCON_TRAPR      = 1, /** A Trap Conflict Reset has occurred **/
    ERR_RCON_IOPUWR     = 2, /** An illegal opcode detection, an illegal address mode or Uninitialized W register used as an
                             *   Address Pointer caused a Reset **/
    ERR_RCON_CM         = 3, /** A Configuration Mismatch Reset has occurred **/
    ERR_RCON_WDTO_ISR   = 4  /** WDT time-out has occurred **/
}SYSTEM_RESET_TYPES;

/**
 * RESET CAUSE Masks. Supported masks:
 * RESET_MASK_WDTO
 * RESET_MASK_SWR
 * REST_MASK_EXTR
 * RESET_MASK_CM
 * RESET_MASK_IOPUWR
 * RESET_MASK_TRAPR
 */
typedef enum tagRESET_MASKS
{
  RESET_MASK_WDTO = 0x0010,
  RESET_MASK_SWR = 0x0040,
  RESET_MASK_EXTR = 0x0080,
  RESET_MASK_CM = 0x0200,
  RESET_MASK_IOPUWR = 0x4000,
  RESET_MASK_TRAPR = 0x8000,
} RESET_MASKS;

/**
* Checks reset cause, flashes UI with an error code as a result.
*
* Note: this function should be called before any use of CLRWDT
* since it has a side-effect of clearing the appropriate bits in the
* register showing reset cause (see DS70602B page 8-10)
*/
uint16_t SYSTEM_GetResetCause(void);


/**
 * Initializes the CPU core control register.
 * @example
 * <code>
 * CORCON_Initialize();
 * </code>
 */
inline static void CORCON_Initialize()
{
    CORCON = (CORCON & 0x00F2) | CORCON_MODE_PORVALUES;    // POR value
}

/**
 * Sets the CPU core control register operating mode to a value that is decided by the
 * SYSTEM_CORCON_MODES argument.
 * @param modeValue SYSTEM_CORCON_MODES initialization mode specifier
 * @example
 * <code>
 * CORCON_ModeOperatingSet(CORCON_MODE_ENABLEALLSATNORMAL_ROUNDUNBIASED);
 * </code>
 */
inline static void CORCON_ModeOperatingSet(SYSTEM_CORCON_MODES modeValue)
{
    CORCON = (CORCON & 0x00F2) | modeValue;
}

/**
 * Sets the value of CPU core control register.
 * @param value value that needs to be written to the CPU core control register
 * @example
 * <code>
 * CORCON_RegisterValueSet(0x00E2);
 * </code>
 */
inline static void CORCON_RegisterValueSet(uint16_t value)
{
    CORCON = value;
}

/**
 * Gets the value of CPU core control register.
 * @return value of the CPU core control register
 * @example
 * <code>
 * corconSave = CORCON_RegisterValueGet();
 * </code>
 */
inline static uint16_t CORCON_RegisterValueGet(void)
{
    return CORCON;
}

/**
 * It handles the reset cause by clearing the cause register values.
 * Its a weak function user can override this function.
 * @return None
 * @example
 * <code>
 * SYSTEM_ResetCauseHandler();
 * </code>
 */
void __attribute__ ((weak)) SYSTEM_ResetCauseHandler(void);

/**
 * This function resets the reset cause register.
 * @return None
 * @example
 * <code>
 * SYSTEM_ResetCauseClearAll();
 * </code>
 */
void SYSTEM_ResetCauseClearAll();