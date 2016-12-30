package com.anhe3d.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by jimmy on 2016/12/12.
 */
public class MachineStepCalculator {
    /**
     * 步進馬達分割數
     */
    private static int stepperDivision = 16;
    /**
     * 螺桿每圈前進量
     */
    private static int screwDisplacementPerRound = 2;
    /**
     * 剪刀腳長度
     */
    private static int scissorLegLength = 130;
    /**
     * 建構槽初始高度
     */
    private static BigDecimal initialZAxisHeight = new BigDecimal(0);
    /**
     * 供粉槽初始高度
     */
    private static BigDecimal initialEAxisHeight = new BigDecimal(0);

    public static BigDecimal calculateZ(BigDecimal thickness) {
        BigDecimal a = new BigDecimal("6.641598622378119E-15");
        BigDecimal b = new BigDecimal("-8.59191731801952E18");
        BigDecimal c = new BigDecimal("7.382104319968374E37");
        BigDecimal d = new BigDecimal("1.4510302866801025E37");
        BigDecimal e = new BigDecimal("5.803563135843283E34");
        return a.multiply(b.add(bigSqrt(c.add(d.multiply(thickness)).subtract(e.multiply(thickness.pow(2))))));
    }

    public static BigDecimal calculateE(BigDecimal thickness) {
        BigDecimal a = new BigDecimal("8.138168673796826E-20");
        BigDecimal b = new BigDecimal("-1.1638412006003677E24");
        BigDecimal c = new BigDecimal("1.3545263402149052E48");
        BigDecimal d = new BigDecimal("1.0376833833876781E46");
        BigDecimal e = new BigDecimal("9.663325312580863E43");
        return a.multiply(b.add(bigSqrt(c.add(d.multiply(thickness)).subtract(e.multiply(thickness.pow(2)))))).add(new BigDecimal(5731));
        // 5731是Magic Number...
    }

    private static final BigDecimal SQRT_DIG = new BigDecimal(150);
    private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());

    private static BigDecimal sqrtNewtonRaphson(BigDecimal c, BigDecimal xn, BigDecimal precision) {
        BigDecimal fx = xn.pow(2).add(c.negate());
        BigDecimal fpx = xn.multiply(new BigDecimal(2));
        BigDecimal xn1 = fx.divide(fpx, 2 * SQRT_DIG.intValue(), RoundingMode.HALF_DOWN);
        xn1 = xn.add(xn1.negate());
        BigDecimal currentSquare = xn1.pow(2);
        BigDecimal currentPrecision = currentSquare.subtract(c);
        currentPrecision = currentPrecision.abs();
        if (currentPrecision.compareTo(precision) <= -1) {
            return xn1;
        }
        return sqrtNewtonRaphson(c, xn1, precision);
    }

    private static BigDecimal bigSqrt(BigDecimal c) {
        return sqrtNewtonRaphson(c, new BigDecimal(1), new BigDecimal(1).divide(SQRT_PRE));
    }

    public static void main(String[] args) {

        System.out.println(calculateZ(new BigDecimal("0.05")).longValue());
        BigDecimal prevE = calculateE(new BigDecimal("0").multiply(new BigDecimal("7.5")).divide(new BigDecimal("100")));
        BigDecimal nextE = calculateE(new BigDecimal("5").multiply(new BigDecimal("7.5")).divide(new BigDecimal("100")));

        System.out.println(prevE.subtract(nextE).longValue());
    }
}
