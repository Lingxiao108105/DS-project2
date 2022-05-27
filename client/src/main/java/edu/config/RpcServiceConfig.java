package edu.config;

public class RpcServiceConfig {

    public static String ipAddress = "127.0.0.1";

    public static Integer registerPort = 9000;

    public static String getConnectAddress() {
        return ipAddress + ":" + registerPort;
    }

    public static String getIpAddress() {
        return ipAddress;
    }

    public static void setIpAddress(String ipAddress) {
        RpcServiceConfig.ipAddress = ipAddress;
    }

    public static Integer getRegisterPort() {
        return registerPort;
    }

    public static void setRegisterPort(Integer registerPort) {
        RpcServiceConfig.registerPort = registerPort;
    }

}
