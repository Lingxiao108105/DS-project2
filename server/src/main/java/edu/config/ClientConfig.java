package edu.config;

import edu.dto.ClientInfo;

import java.util.concurrent.ConcurrentHashMap;

public class ClientConfig {

    Integer managerId = null;
    ConcurrentHashMap<Integer, ClientInfo> acceptedClients;
    ConcurrentHashMap<Integer, ClientInfo> waitListClients;


}
