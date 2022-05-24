package edu.config;

import java.net.InetAddress;

public class RpcServiceConfig {

    private static Integer registerPort = 10000;
    private static InetAddress canvasServiceAddress;

    public static Integer getRegisterPort() {
        return registerPort;
    }

    public static void setRegisterPort(Integer registerPort) {
        RpcServiceConfig.registerPort = registerPort;
    }

    public static InetAddress getCanvasServiceAddress() {
        return canvasServiceAddress;
    }

    public static void setCanvasServiceAddress(InetAddress canvasServiceAddress) {
        RpcServiceConfig.canvasServiceAddress = canvasServiceAddress;
    }
}
