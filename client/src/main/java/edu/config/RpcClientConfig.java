package edu.config;

/**
 * rpc client config store the address of server
 * @author lingxiao
 */
public class RpcClientConfig {

    public static String serverIPAddress = "127.0.0.1";
    public static String serverPort = "10000";

    public static String getConnectAddress() {
        return serverIPAddress + ":" + serverPort;
    }

    public static String getServerIPAddress() {
        return serverIPAddress;
    }

    public static void setServerIPAddress(String serverIPAddress) {
        RpcClientConfig.serverIPAddress = serverIPAddress;
    }

    public static String getServerPort() {
        return serverPort;
    }

    public static void setServerPort(String serverPort) {
        RpcClientConfig.serverPort = serverPort;
    }
}
