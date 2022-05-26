package edu.data;

import edu.LifeCycle;
import edu.dto.ClientInfo;

import java.util.concurrent.ConcurrentHashMap;

/**
 * information of the whole cluster
 */
public class ClusterInfo implements LifeCycle {

    private static ClusterInfo clusterInfo = null;
    private static Integer id = 0;

    public static ClusterInfo getInstance(){
        if(clusterInfo == null){
            clusterInfo = new ClusterInfo();
            clusterInfo.init();
        }
        return clusterInfo;
    }

    Integer managerId = null;
    ConcurrentHashMap<Integer, ClientInfo> acceptedClients;
    ConcurrentHashMap<Integer, ClientInfo> waitListClients;
    ConcurrentHashMap<Integer, ClientInfo> deniedClients;
    ConcurrentHashMap<Integer, ClientInfo> kickedClients;

    @Override
    public void init() {
        acceptedClients = new ConcurrentHashMap<>();
        waitListClients = new ConcurrentHashMap<>();
        deniedClients = new ConcurrentHashMap<>();
        kickedClients = new ConcurrentHashMap<>();
    }

    @Override
    public void stop() {
        //TODO close all the peers
        acceptedClients = null;
        waitListClients = null;
        deniedClients = null;
        kickedClients= null;
        managerId = null;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public ClientInfo addManager(String name){
        if(managerId != null){
            return null;
        }

        ClientInfo clientInfo = new ClientInfo(getNextId(), name);
        this.managerId = clientInfo.getId();
        acceptClient(clientInfo);
        return clientInfo;
    }

    public boolean acceptClient(ClientInfo clientInfo){
        this.waitListClients.remove(clientInfo.getId());
        this.acceptedClients.put(clientInfo.getId(),clientInfo);
        return true;
    }

    /**
     * request to join the cluster
     * return null if the name already in the cluster
     * @param name name of client
     * @return client info
     */
    public ClientInfo requestToJoin(String name){
        for(ClientInfo clientInfo: acceptedClients.values()){
            if(name.equals(clientInfo.getName())){
                return null;
            }
        }
        for(ClientInfo clientInfo: waitListClients.values()){
            if(name.equals(clientInfo.getName())){
                return null;
            }
        }
        ClientInfo clientInfo = new ClientInfo(getNextId(), name);
        waitListClients.put(clientInfo.getId(),clientInfo);
        return clientInfo;
    }

    private Integer getNextId(){
        ClusterInfo.id ++;
        return ClusterInfo.id;
    }

    public boolean isAcceptedClient(ClientInfo clientInfo){
        return this.acceptedClients.containsKey(clientInfo.getId());
    }

    public void deny(ClientInfo clientInfo){
        this.waitListClients.remove(clientInfo.getId());
        this.deniedClients.put(clientInfo.getId(),clientInfo);
    }

    public boolean isDeniedClient(ClientInfo clientInfo){
        return this.deniedClients.containsKey(clientInfo.getId());
    }

    public void kick(ClientInfo clientInfo){
        this.acceptedClients.remove(clientInfo.getId());
        this.kickedClients.put(clientInfo.getId(),clientInfo);
    }

    public boolean isKickedClient(ClientInfo clientInfo){
        return this.kickedClients.containsKey(clientInfo.getId());
    }
}
