package edu.data;

import edu.LifeCycle;
import edu.Main;
import edu.dto.ClientInfo;
import edu.service.Impl.ClusterServiceImpl;

import java.util.List;
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

    ClientInfo manager = null;
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
        acceptedClients = null;
        waitListClients = null;
        deniedClients = null;
        kickedClients= null;
        manager = null;
        clusterInfo = null;
    }

    public ClientInfo getManager() {
        return manager;
    }

    public List<ClientInfo> getAcceptedClients() {
        return List.copyOf(acceptedClients.values());
    }

    public List<ClientInfo> getWaitListClients() {
        return List.copyOf(waitListClients.values());
    }

    public ClientInfo addManager(String name, String address){
        if(manager != null){
            return null;
        }

        ClientInfo clientInfo = new ClientInfo(getNextId(), name, address);
        this.manager = clientInfo;
        acceptClient(clientInfo);
        return clientInfo;
    }

    public boolean acceptClient(ClientInfo clientInfo){
        this.waitListClients.remove(clientInfo.getId());
        this.acceptedClients.put(clientInfo.getId(),clientInfo);
        return true;
    }

    /**
     * when a new client join the cluster with an address,
     * the old client using same address must fail
     * @return
     */
    public void removeClientWithAddress(String address){
        for(ClientInfo clientInfo: acceptedClients.values()){
            if(address.equals(clientInfo.getAddress())){
                //manager crash will delete all the client and restart canvas
                if(clientInfo.equals(manager)) {
                    removeFromAcceptedClient(clientInfo);
                    return;
                }
                removeFromAcceptedClient(clientInfo);
            }
        }

        for(ClientInfo clientInfo: waitListClients.values()){
            if(address.equals(clientInfo.getAddress())){
                removeFromWaitListClient(clientInfo);
            }
        }
    }

    /**
     * request to join the cluster
     * return null if the name already in the cluster or no manager
     * @param name name of client
     * @return client info
     */
    public ClientInfo requestToJoin(String name, String address){

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
        ClientInfo clientInfo = new ClientInfo(getNextId(), name, address);
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

    public void removeFromAcceptedClient(ClientInfo clientInfo){
        this.acceptedClients.remove(clientInfo.getId());
        //manager want to leave or crash
        if(clientInfo.equals(ClusterInfo.getInstance().getManager())){
            ClusterServiceImpl.closeAllPeer();
            Main.restartCanvas();
        }

    }


    public boolean isWaitListClient(ClientInfo clientInfo){
        return this.waitListClients.containsKey(clientInfo.getId());
    }

    public void removeFromWaitListClient(ClientInfo clientInfo){
        this.waitListClients.remove(clientInfo.getId());
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
