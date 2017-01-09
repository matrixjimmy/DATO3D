package com.anhe3d;

/**
 * Created by jimmy on 2016/12/14.
 */
public enum CommProtocol {
    RESET_33FJ("E00"),
    RESET_33EP("\33"),
    PRESS_POWER("G00"),
    PRESS_INK("G01"),
    PRESS_PAPER("G02"),
    PRESS_DELETE("G03"),

    TURN_ON_PRINTER("G04"),
    TURN_OFF_PRINTER("G05"),

    START_PRINT("G06"),
    STOP_PRINT("G07"),

    GO_TO_START("G08"),
    GO_TO_END("G09"),

    POWDER_START("G0A"),

    SERVO_UP("G0B"),
    SERVO_DOWN("G0C"),
    SERVO_STOP("G0D"),

    ROLLER_FORWARD("G0E"),
    ROLLER_STOP("G0F"),
    ROLLER_BACKWARD("G0G"),

    Z_GO_TO_TOP("G10"),
    Z_GO_UP_STEP("G11 "),
    Z_GO_DOWN_STEP("G12 "),
    Z_GO_TO_BOTTOM("G13"),
    Z_GO_FLAT("G14"),

    E_GO_TO_TOP("G20"),
    E_GO_UP_STEP("G21 "),
    E_GO_DOWN_STEP("G22 "),
    E_GO_TO_BOTTOM("G23"),
    E_GO_FLAT("G24"),

    GET_33EP_STATUS("M00"),
    GET_33FJ_STATUS("M01"),
    SET_ROLLER_SPEED("M02"),;

    private String code;

    CommProtocol(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
