package edu.common;

import edu.config.RpcClientConfig;

public class ErrorMessage {

    public static String SERVER_ERROR = "Fail to connect to server" + RpcClientConfig.serverIPAddress + ":" + RpcClientConfig.serverPort + "!\nConnect to another server or try later!";
    public final static String NOT_ENOUGH_ARGUMENT_ERROR = "5 arguments are required! \n<serverIPAddress> <serverPort> <username> <role> <localIpAddress>";
}
