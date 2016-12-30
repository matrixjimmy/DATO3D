package com.anhe3d;

import com.anhe3d.util.MachineStepCalculator;
import griffon.core.artifact.GriffonService;
import griffon.metadata.ArtifactProviderFor;
import jssc.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonService;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jimmy on 2016/11/24.
 */
@Singleton
@ArtifactProviderFor(GriffonService.class)
@Slf4j
public class SerialService extends AbstractGriffonService {

    private SerialPort serialPort;

    /* Data received from port */
    private StringBuilderWriter serialPortInputData;

    /* Data prepared to send */
    private StringBuilder serialPortOutputData;

    public SerialService() {
        serialPortOutputData = new StringBuilder();
    }

    public List<String> getPortNames() {
        return Arrays.asList(SerialPortList.getPortNames());
    }

    void initSerialPort(String portName) throws SerialPortException {
        serialPort = new SerialPort(portName);
        if (!serialPort.isOpened()) {
            serialPort.openPort();
            serialPort.setParams(115200, 8, 1, 0);
        }
        serialPortInputData = new StringBuilderWriter();
        //addDefaultEventListener();
        addExecutingEventListener();
    }

    public boolean isPortOpened() {
        return serialPort != null && serialPort.isOpened();
    }

    void closePort() {
        if (serialPort.isOpened()) {
            try {
                serialPort.removeEventListener();
            } catch (SerialPortException ignored) {
            } finally {
                try {
                    serialPort.closePort();
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void write(CommProtocol protocol) {
        serialPortOutputData.append(protocol.code() + "\n");
        if (StringUtils.containsNone(serialPortOutputData, "\n")) {
            serialPortOutputData.append("\n");
        }
        try {
            serialPort.writeString(serialPortOutputData.toString());
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        serialPortOutputData = new StringBuilder();
    }

    public void write(String data) {
        serialPortOutputData.append(data).append("\n");
        try {
            serialPort.writeString(serialPortOutputData.toString());
            serialPortOutputData = new StringBuilder();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    String read() {
        String rtn = serialPortInputData.toString();
        try {
            serialPort.removeEventListener();
            serialPortInputData = new StringBuilderWriter();
            addDefaultEventListener();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return rtn;
    }

    public void bindDefaultEventListener() {
        try {
            serialPort.removeEventListener();
            addDefaultEventListener();
        } catch (SerialPortException ignored) {
        }
    }

    public void bindExecutingEventListener() {
        try {
            serialPort.removeEventListener();
            addExecutingEventListener();
        } catch (SerialPortException ignored) {
        }
    }

    private void addDefaultEventListener() throws SerialPortException {
        serialPort.addEventListener((evt) -> {
            if (evt.isRXCHAR() && evt.getEventValue() > 0) {
                try {
                    serialPortInputData.append(serialPort.readString(evt.getEventValue()));
                } catch (SerialPortException e) {
                    log.error("Serial Port Reception Error", e);
                }
            }
        }, SerialPortEvent.RXCHAR);
    }

    private static boolean printerBusyFlag = false;

    public boolean getPrinterBusyFlag() {
        try {
            serialPort.removeEventListener();
        } catch (SerialPortException ignored) {
        }
        String readString = null;
        write(CommProtocol.GET_33FJ_STATUS);
        try {
            readString = serialPort.readString(3, 1000);
            if (!StringUtils.containsOnly(readString, "1234567890")) {
                readString = StringUtils.remove(readString, StringUtils.LF);
                readString = StringUtils.remove(readString, StringUtils.SPACE);
            }
        } catch (SerialPortException | SerialPortTimeoutException ignored) {
        }
        if (StringUtils.isNotBlank(readString) && StringUtils.isNumeric(readString)) {
            int result = Integer.parseInt(readString, 16);
            if (255 == result) { // 讀取錯誤
                System.out.println("Error");
            } else {
                int lswStart = result & 0b10000000;
                int lswEnd = result & 0b01000000;
                int dtgStatus = (result & 0b00111000) >> 3;
                int ledInk = result & 0b00000100;
                int ledPaper = result & 0b00000010;
                int ledPower = result & 0b00000001;
                if (0 != lswStart) {
                    System.out.println("Printer at Start Position");
                }
                if (0 != lswEnd) {
                    System.out.println("Printer at  End  Position");
                }
                if (0 != ledInk) {
                    System.out.println("Led INK");
                }
                if (0 != ledPaper) {
                    System.out.println("Led Paper");
                }
                if (0 != ledPower) {
                    System.out.println("Led Power");
                }
                switch (dtgStatus) {
                    case 0:
                        System.out.println("Printer Power Off");
                        printerBusyFlag = false;
                        break;
                    case 1:
                        System.out.println("Printer Power On");
                        printerBusyFlag = false;
                        break;
                    case 2:
                        System.out.println("Printer Busy");
                        printerBusyFlag = true;
                        break;
                    case 3:
                        System.out.println("Printer No Paper");
                        printerBusyFlag = true;
                        break;
                    case 4:
                        System.out.println("Printer Paper Jam");
                        printerBusyFlag = true;
                        break;
                    case 5:
                        System.out.println("Printer Error");
                        printerBusyFlag = true;
                        break;
                    default:
                        System.out.println("dtg status error...");
                        printerBusyFlag = true;
                }
            }
        }
        return printerBusyFlag;
    }

    public boolean getPlatformBusyFlag() {
        int result = 0;
        try {
            serialPort.removeEventListener();
        } catch (SerialPortException ignored) {
        }
        String readString = null;
        write(CommProtocol.GET_33EP_STATUS);
        try {
            readString = serialPort.readString(3, 1000);
            if (!StringUtils.containsOnly(readString, "1234567890")) {
                readString = StringUtils.remove(readString, StringUtils.LF);
                readString = StringUtils.remove(readString, StringUtils.SPACE);
            }
        } catch (SerialPortException | SerialPortTimeoutException ignored) {
        }
        if (StringUtils.isNotBlank(readString) && StringUtils.isNumeric(readString)) {
            result = Integer.parseInt(readString, 16);
            if (255 == result) {
                System.err.println("Read Serial Port Error...");
            } else {
                int eAtTop = result & (0b1 << 5);
                int eAtBottom = result & (0b1 << 4);
                int oc2 = result & (0b1 << 3);
                int zAtTop = result & (0b1 << 2);
                int zAtBottom = result & (0b1 << 1);
                int oc1 = result & 0b1;
                if (0 != eAtBottom) {
                    log.debug("E at Bottom");
                }
                if (0 != eAtTop) {
                    log.debug("E at Top");
                }
                if (0 != oc2) {
                    log.info("OC2 is running");
                }
                if (0 != zAtBottom) {
                    log.debug("Z at Bottom");
                }
                if (0 != zAtTop) {
                    log.debug("Z at Top");
                }
                if (0 != oc1) {
                    log.info("OC1 is running");
                }
            }
        }
        return 0 != (result & 0b00001001);
    }

    private void addExecutingEventListener() throws SerialPortException {
        serialPort.addEventListener((evt) -> {
            String readString = null;
            try {
                readString = serialPort.readString(evt.getEventValue());
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
            if (StringUtils.isNoneBlank(readString)) {
                int result = Integer.getInteger(readString);
//                if(255 == result) { // 讀取錯誤
//                    return;
//                }
//                int lswStart  = result & 0b10000000;
//                int lswEnd    = result & 0b01000000;
//                int dtgStatus = (result & 0b00111000) >> 3;
//                int ledInk    = result & 0b00000100;
//                int ledPaper  = result & 0b00000010;
//                int ledPower  = result & 0b00000001;
//                if(1 == lswStart) {
//                    System.out.println("Printer at Start Position");
//                }
//                if(1 == lswEnd) {
//                    System.out.println("Printer at  End  Position");
//                }
//                if(1 == ledInk) {
//                    System.out.println("Led INK");
//                }
//                if(1 == ledPaper) {
//                    System.out.println("Led Paper");
//                }
//                if(1 == ledPower) {
//                    System.out.println("Led Power");
//                }
//                switch (dtgStatus) {
//                    case 0:
//                        System.out.println("Printer Power Off");
//                        printerBusyFlag = false;
//                        break;
//                    case 1:
//                        System.out.println("Printer Power On");
//                        printerBusyFlag = false;
//                        break;
//                    case 2:
//                        System.out.println("Printer Busy");
//                        printerBusyFlag = true;
//                        break;
//                    case 3:
//                        System.out.println("Printer No Paper");
//                        printerBusyFlag = true;
//                        break;
//                    case 4:
//                        System.out.println("Printer Paper Jam");
//                        printerBusyFlag = true;
//                        break;
//                    case 5:
//                        System.out.println("Printer Error");
//                        printerBusyFlag = true;
//                        break;
//                    default:
//                        System.out.println("dtg status error...");
//                        printerBusyFlag = true;
//                }
                if (8 == result) {
                    printerBusyFlag = false;
                } else {
                    printerBusyFlag = true;
                }
            }
        }, SerialPortEvent.RXCHAR);
    }

    public void doPlatformElevation(int layer) {
        BigDecimal zPrevStep = MachineStepCalculator.calculateZ(new BigDecimal("1000").add(new BigDecimal((layer - 1) * 5)).divide(new BigDecimal(100)));
        BigDecimal zCurrStep = MachineStepCalculator.calculateZ(new BigDecimal("1000").add(new BigDecimal(layer * 5)).divide(new BigDecimal(100)));
        BigDecimal zStep = zCurrStep.subtract(zPrevStep);
        write(CommProtocol.Z_GO_DOWN_STEP.code() + zStep.longValue());
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }

//        BigDecimal ePrevStep = MachineStepCalculator.calculateE(new BigDecimal("1000").add(new BigDecimal((layer) * 5)).divide(new BigDecimal(100)));
//        BigDecimal eCurrStep = MachineStepCalculator.calculateE(new BigDecimal("1000").add(new BigDecimal(layer * 5)).divide(new BigDecimal(100)));
//        BigDecimal eStep = eCurrStep.subtract(ePrevStep); // 這邊還沒測定高度，沒辦法計算
//        write(CommProtocol.E_GO_UP_STEP.code() + eStep.longValue());
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException ignored) {
//        }
        write(CommProtocol.POWDER_START);
    }
}
