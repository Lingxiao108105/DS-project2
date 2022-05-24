package edu.data;

import edu.dto.ClientInfo;

import java.util.concurrent.ConcurrentHashMap;

/**
 * information of the whole cluster
 */
public class ClusterInfo {

    Integer managerId = null;
    ConcurrentHashMap<Integer, ClientInfo> acceptedClients;
    ConcurrentHashMap<Integer, ClientInfo> waitListClients;


}
