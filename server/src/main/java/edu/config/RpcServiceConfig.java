package edu.config;

public class RpcServiceConfig {

    private static Integer registerPort = 10000;

    public static Integer getRegisterPort() {
        return registerPort;
    }

    public static void setRegisterPort(Integer registerPort) {
        RpcServiceConfig.registerPort = registerPort;
    }

}
