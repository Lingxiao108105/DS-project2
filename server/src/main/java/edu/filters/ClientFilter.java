package edu.filters;


import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.filter.Filter;
import com.alipay.sofa.rpc.filter.FilterInvoker;

public class ClientFilter extends Filter {

    @Override
    public boolean needToLoad(FilterInvoker invoker) {
        return true;
    }

    @Override
    public SofaResponse invoke(FilterInvoker invoker, SofaRequest request) throws SofaRpcException {
        SofaResponse response = invoker.invoke(request);
        return response;

//        //check whether the request from a valid user
//        Object o = request.getRequestProp("ClientInfo");
//        ClientInfo clientInfo = (ClientInfo) o;
//
//        SofaResponse response = new SofaResponse();
//
//        //sanity check
//        if(clientInfo == null){
//            //TODO return not register error
//            return response;
//        }else if(clientInfo.getId() == null){
//            //TODO return no client id
//            return response;
//        }
//
//        //check
//        if(!ClusterInfo.getInstance().isAcceptedClient(clientInfo)){
//            if(ClusterInfo.getInstance().isDeniedClient(clientInfo)){
//                //TODO return isDeniedClient
//                return response;
//            }
//            if(ClusterInfo.getInstance().isKickedClient(clientInfo)){
//                //TODO return isKickedClient
//                return response;
//            }
//        }
//
//        //is client
//        response = invoker.invoke(request);
//        return response;
    }
}
