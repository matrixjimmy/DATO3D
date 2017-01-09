/*
 * File:   gcode.h
 * Author: jimmy
 *
 * Created on December 12, 2016, 10:28 AM
 */

#ifndef GCODE_H
#define	GCODE_H

#ifdef	__cplusplus
extern "C" {
#endif

#define FOREACH_GCODE(FN) \
            FN(E00) \
            \
            FN(G00) \
            FN(G01) \
            FN(G02) \
            FN(G03) \
            FN(G04) \
            FN(G05) \
            FN(G06) \
            FN(G07) \
            FN(G08) \
            FN(G09) \
            FN(G0A) \
            FN(G0B) \
            FN(G0C) \
            FN(G0D) \
            FN(G0E) \
            FN(G0F) \
            FN(G0G) \
            \
            FN(G10) \
            FN(G11) \
            FN(G12) \
            FN(G13) \
            FN(G14) \
            \
            FN(G20) \
            FN(G21) \
            FN(G22) \
            FN(G23) \
            FN(G24) \
            \
            FN(M00) \
            FN(M01) \
            FN(M02)

#define GENERATE_ENUM(ENUM) ENUM,
#define GENERATE_STRING(STRING) #STRING,
#define COMPARE_WITH_CODE(STRING, GCODE) (0 == strncmp(STRING, GCODE_STRING[GCODE], 3))

    enum GCode {
        FOREACH_GCODE(GENERATE_ENUM)
    };

    static const char * GCODE_STRING[] = {
        FOREACH_GCODE(GENERATE_STRING)
    };


#ifdef	__cplusplus
}
#endif

#endif	/* GCODE_H */

