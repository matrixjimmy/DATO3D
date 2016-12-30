#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Include project Makefile
ifeq "${IGNORE_LOCAL}" "TRUE"
# do not include local makefile. User is passing all local related variables already
else
include Makefile
# Include makefile containing local settings
ifeq "$(wildcard nbproject/Makefile-local-default.mk)" "nbproject/Makefile-local-default.mk"
include nbproject/Makefile-local-default.mk
endif
endif

# Environment
MKDIR=mkdir -p
RM=rm -f 
MV=mv 
CP=cp 

# Macros
CND_CONF=default
ifeq ($(TYPE_IMAGE), DEBUG_RUN)
IMAGE_TYPE=debug
OUTPUT_SUFFIX=elf
DEBUGGABLE_SUFFIX=elf
FINAL_IMAGE=dist/${CND_CONF}/${IMAGE_TYPE}/33fj.${IMAGE_TYPE}.${OUTPUT_SUFFIX}
else
IMAGE_TYPE=production
OUTPUT_SUFFIX=hex
DEBUGGABLE_SUFFIX=elf
FINAL_IMAGE=dist/${CND_CONF}/${IMAGE_TYPE}/33fj.${IMAGE_TYPE}.${OUTPUT_SUFFIX}
endif

ifeq ($(COMPARE_BUILD), true)
COMPARISON_BUILD=-mafrlcsj
else
COMPARISON_BUILD=
endif

# Object Directory
OBJECTDIR=build/${CND_CONF}/${IMAGE_TYPE}

# Distribution Directory
DISTDIR=dist/${CND_CONF}/${IMAGE_TYPE}

# Source Files Quoted if spaced
SOURCEFILES_QUOTED_IF_SPACED=source/main.c source/configuration_bits.c source/DTG.c source/interrupts.c source/pin_manager.c source/QEI.c source/stepmotor.c source/system.c source/timer.c source/traps.c

# Object Files Quoted if spaced
OBJECTFILES_QUOTED_IF_SPACED=${OBJECTDIR}/source/main.o ${OBJECTDIR}/source/configuration_bits.o ${OBJECTDIR}/source/DTG.o ${OBJECTDIR}/source/interrupts.o ${OBJECTDIR}/source/pin_manager.o ${OBJECTDIR}/source/QEI.o ${OBJECTDIR}/source/stepmotor.o ${OBJECTDIR}/source/system.o ${OBJECTDIR}/source/timer.o ${OBJECTDIR}/source/traps.o
POSSIBLE_DEPFILES=${OBJECTDIR}/source/main.o.d ${OBJECTDIR}/source/configuration_bits.o.d ${OBJECTDIR}/source/DTG.o.d ${OBJECTDIR}/source/interrupts.o.d ${OBJECTDIR}/source/pin_manager.o.d ${OBJECTDIR}/source/QEI.o.d ${OBJECTDIR}/source/stepmotor.o.d ${OBJECTDIR}/source/system.o.d ${OBJECTDIR}/source/timer.o.d ${OBJECTDIR}/source/traps.o.d

# Object Files
OBJECTFILES=${OBJECTDIR}/source/main.o ${OBJECTDIR}/source/configuration_bits.o ${OBJECTDIR}/source/DTG.o ${OBJECTDIR}/source/interrupts.o ${OBJECTDIR}/source/pin_manager.o ${OBJECTDIR}/source/QEI.o ${OBJECTDIR}/source/stepmotor.o ${OBJECTDIR}/source/system.o ${OBJECTDIR}/source/timer.o ${OBJECTDIR}/source/traps.o

# Source Files
SOURCEFILES=source/main.c source/configuration_bits.c source/DTG.c source/interrupts.c source/pin_manager.c source/QEI.c source/stepmotor.c source/system.c source/timer.c source/traps.c


CFLAGS=
ASFLAGS=
LDLIBSOPTIONS=

############# Tool locations ##########################################
# If you copy a project from one host to another, the path where the  #
# compiler is installed may be different.                             #
# If you open this project with MPLAB X in the new host, this         #
# makefile will be regenerated and the paths will be corrected.       #
#######################################################################
# fixDeps replaces a bunch of sed/cat/printf statements that slow down the build
FIXDEPS=fixDeps

.build-conf:  ${BUILD_SUBPROJECTS}
ifneq ($(INFORMATION_MESSAGE), )
	@echo $(INFORMATION_MESSAGE)
endif
	${MAKE}  -f nbproject/Makefile-default.mk dist/${CND_CONF}/${IMAGE_TYPE}/33fj.${IMAGE_TYPE}.${OUTPUT_SUFFIX}

MP_PROCESSOR_OPTION=33FJ128MC804
MP_LINKER_FILE_OPTION=,--script=p33FJ128MC804.gld
# ------------------------------------------------------------------------------------
# Rules for buildStep: compile
ifeq ($(TYPE_IMAGE), DEBUG_RUN)
${OBJECTDIR}/source/main.o: source/main.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/main.o.d 
	@${RM} ${OBJECTDIR}/source/main.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/main.c  -o ${OBJECTDIR}/source/main.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/main.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_ICD3=1    -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/main.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/configuration_bits.o: source/configuration_bits.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/configuration_bits.o.d 
	@${RM} ${OBJECTDIR}/source/configuration_bits.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/configuration_bits.c  -o ${OBJECTDIR}/source/configuration_bits.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/configuration_bits.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_ICD3=1    -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/configuration_bits.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/DTG.o: source/DTG.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/DTG.o.d 
	@${RM} ${OBJECTDIR}/source/DTG.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/DTG.c  -o ${OBJECTDIR}/source/DTG.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/DTG.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_ICD3=1    -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/DTG.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/interrupts.o: source/interrupts.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/interrupts.o.d 
	@${RM} ${OBJECTDIR}/source/interrupts.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/interrupts.c  -o ${OBJECTDIR}/source/interrupts.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/interrupts.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_ICD3=1    -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/interrupts.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/pin_manager.o: source/pin_manager.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/pin_manager.o.d 
	@${RM} ${OBJECTDIR}/source/pin_manager.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/pin_manager.c  -o ${OBJECTDIR}/source/pin_manager.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/pin_manager.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_ICD3=1    -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/pin_manager.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/QEI.o: source/QEI.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/QEI.o.d 
	@${RM} ${OBJECTDIR}/source/QEI.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/QEI.c  -o ${OBJECTDIR}/source/QEI.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/QEI.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_ICD3=1    -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/QEI.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/stepmotor.o: source/stepmotor.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/stepmotor.o.d 
	@${RM} ${OBJECTDIR}/source/stepmotor.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/stepmotor.c  -o ${OBJECTDIR}/source/stepmotor.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/stepmotor.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_ICD3=1    -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/stepmotor.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/system.o: source/system.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/system.o.d 
	@${RM} ${OBJECTDIR}/source/system.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/system.c  -o ${OBJECTDIR}/source/system.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/system.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_ICD3=1    -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/system.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/timer.o: source/timer.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/timer.o.d 
	@${RM} ${OBJECTDIR}/source/timer.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/timer.c  -o ${OBJECTDIR}/source/timer.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/timer.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_ICD3=1    -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/timer.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/traps.o: source/traps.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/traps.o.d 
	@${RM} ${OBJECTDIR}/source/traps.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/traps.c  -o ${OBJECTDIR}/source/traps.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/traps.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_ICD3=1    -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/traps.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
else
${OBJECTDIR}/source/main.o: source/main.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/main.o.d 
	@${RM} ${OBJECTDIR}/source/main.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/main.c  -o ${OBJECTDIR}/source/main.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/main.o.d"        -g -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/main.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/configuration_bits.o: source/configuration_bits.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/configuration_bits.o.d 
	@${RM} ${OBJECTDIR}/source/configuration_bits.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/configuration_bits.c  -o ${OBJECTDIR}/source/configuration_bits.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/configuration_bits.o.d"        -g -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/configuration_bits.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/DTG.o: source/DTG.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/DTG.o.d 
	@${RM} ${OBJECTDIR}/source/DTG.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/DTG.c  -o ${OBJECTDIR}/source/DTG.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/DTG.o.d"        -g -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/DTG.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/interrupts.o: source/interrupts.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/interrupts.o.d 
	@${RM} ${OBJECTDIR}/source/interrupts.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/interrupts.c  -o ${OBJECTDIR}/source/interrupts.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/interrupts.o.d"        -g -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/interrupts.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/pin_manager.o: source/pin_manager.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/pin_manager.o.d 
	@${RM} ${OBJECTDIR}/source/pin_manager.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/pin_manager.c  -o ${OBJECTDIR}/source/pin_manager.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/pin_manager.o.d"        -g -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/pin_manager.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/QEI.o: source/QEI.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/QEI.o.d 
	@${RM} ${OBJECTDIR}/source/QEI.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/QEI.c  -o ${OBJECTDIR}/source/QEI.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/QEI.o.d"        -g -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/QEI.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/stepmotor.o: source/stepmotor.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/stepmotor.o.d 
	@${RM} ${OBJECTDIR}/source/stepmotor.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/stepmotor.c  -o ${OBJECTDIR}/source/stepmotor.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/stepmotor.o.d"        -g -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/stepmotor.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/system.o: source/system.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/system.o.d 
	@${RM} ${OBJECTDIR}/source/system.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/system.c  -o ${OBJECTDIR}/source/system.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/system.o.d"        -g -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/system.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/timer.o: source/timer.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/timer.o.d 
	@${RM} ${OBJECTDIR}/source/timer.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/timer.c  -o ${OBJECTDIR}/source/timer.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/timer.o.d"        -g -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/timer.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/source/traps.o: source/traps.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} "${OBJECTDIR}/source" 
	@${RM} ${OBJECTDIR}/source/traps.o.d 
	@${RM} ${OBJECTDIR}/source/traps.o 
	${MP_CC} $(MP_EXTRA_CC_PRE)  source/traps.c  -o ${OBJECTDIR}/source/traps.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/source/traps.o.d"        -g -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -O0 -I"include" -I"../comm-protocol/src" -msmart-io=1 -Wall -msfr-warn=off  
	@${FIXDEPS} "${OBJECTDIR}/source/traps.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
endif

# ------------------------------------------------------------------------------------
# Rules for buildStep: assemble
ifeq ($(TYPE_IMAGE), DEBUG_RUN)
else
endif

# ------------------------------------------------------------------------------------
# Rules for buildStep: assemblePreproc
ifeq ($(TYPE_IMAGE), DEBUG_RUN)
else
endif

# ------------------------------------------------------------------------------------
# Rules for buildStep: link
ifeq ($(TYPE_IMAGE), DEBUG_RUN)
dist/${CND_CONF}/${IMAGE_TYPE}/33fj.${IMAGE_TYPE}.${OUTPUT_SUFFIX}: ${OBJECTFILES}  nbproject/Makefile-${CND_CONF}.mk  ../comm-protocol/dist/default/debug/comm-protocol.a  
	@${MKDIR} dist/${CND_CONF}/${IMAGE_TYPE} 
	${MP_CC} $(MP_EXTRA_LD_PRE)  -o dist/${CND_CONF}/${IMAGE_TYPE}/33fj.${IMAGE_TYPE}.${OUTPUT_SUFFIX}  ${OBJECTFILES_QUOTED_IF_SPACED}    ../comm-protocol/dist/default/debug/comm-protocol.a  -mcpu=$(MP_PROCESSOR_OPTION)        -D__DEBUG -D__MPLAB_DEBUGGER_ICD3=1  -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)   -mreserve=data@0x800:0x81F -mreserve=data@0x820:0x821 -mreserve=data@0x822:0x823 -mreserve=data@0x824:0x825 -mreserve=data@0x826:0x84F   -Wl,,,--defsym=__MPLAB_BUILD=1,--defsym=__MPLAB_DEBUG=1,--defsym=__DEBUG=1,--defsym=__MPLAB_DEBUGGER_ICD3=1,$(MP_LINKER_FILE_OPTION),--stack=16,--check-sections,--data-init,--pack-data,--handles,--isr,--no-gc-sections,--fill-upper=0,--stackguard=16,--no-force-link,--smart-io,-Map="${DISTDIR}/${PROJECTNAME}.${IMAGE_TYPE}.map",--report-mem,--memorysummary,dist/${CND_CONF}/${IMAGE_TYPE}/memoryfile.xml$(MP_EXTRA_LD_POST) 
	
else
dist/${CND_CONF}/${IMAGE_TYPE}/33fj.${IMAGE_TYPE}.${OUTPUT_SUFFIX}: ${OBJECTFILES}  nbproject/Makefile-${CND_CONF}.mk  ../comm-protocol/dist/default/production/comm-protocol.a 
	@${MKDIR} dist/${CND_CONF}/${IMAGE_TYPE} 
	${MP_CC} $(MP_EXTRA_LD_PRE)  -o dist/${CND_CONF}/${IMAGE_TYPE}/33fj.${IMAGE_TYPE}.${DEBUGGABLE_SUFFIX}  ${OBJECTFILES_QUOTED_IF_SPACED}    ../comm-protocol/dist/default/production/comm-protocol.a  -mcpu=$(MP_PROCESSOR_OPTION)        -omf=elf -DXPRJ_default=$(CND_CONF)  -legacy-libc  $(COMPARISON_BUILD)  -Wl,,,--defsym=__MPLAB_BUILD=1,$(MP_LINKER_FILE_OPTION),--stack=16,--check-sections,--data-init,--pack-data,--handles,--isr,--no-gc-sections,--fill-upper=0,--stackguard=16,--no-force-link,--smart-io,-Map="${DISTDIR}/${PROJECTNAME}.${IMAGE_TYPE}.map",--report-mem,--memorysummary,dist/${CND_CONF}/${IMAGE_TYPE}/memoryfile.xml$(MP_EXTRA_LD_POST) 
	${MP_CC_DIR}/xc16-bin2hex dist/${CND_CONF}/${IMAGE_TYPE}/33fj.${IMAGE_TYPE}.${DEBUGGABLE_SUFFIX} -a  -omf=elf  
	
endif


# Subprojects
.build-subprojects:
	cd ../comm-protocol && ${MAKE}  -f Makefile CONF=default


# Subprojects
.clean-subprojects:
	cd ../comm-protocol && rm -rf "build/default" "dist/default"

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r build/default
	${RM} -r dist/default

# Enable dependency checking
.dep.inc: .depcheck-impl

DEPFILES=$(shell "${PATH_TO_IDE_BIN}"mplabwildcard ${POSSIBLE_DEPFILES})
ifneq (${DEPFILES},)
include ${DEPFILES}
endif
