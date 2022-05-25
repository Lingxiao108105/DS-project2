package edu.security;


import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.filter.Filter;
import com.alipay.sofa.rpc.filter.FilterInvoker;
import edu.data.ClusterInfo;
import edu.dto.CanvasUpdateRequest;
import edu.dto.ClientInfo;

public class ClientFilter extends Filter {

    @Override
    public boolean needToLoad(FilterInvoker invoker) {
        return true;
    }

    @Override
    public SofaResponse invoke(FilterInvoker invoker, SofaRequest request) throws SofaRpcException {
        //TODO filter client not accepted
//        Object o = request.getRequestProp(ClientInfo.class.getName());
//        if(o == null){
//            return ;
//        }
//        if(o instanceof CanvasUpdateRequest){
//            CanvasUpdateRequest canvasUpdateRequest = (CanvasUpdateRequest) o;
//            ClientInfo clientInfo = canvasUpdateRequest.getClientInfo();
//            if(!ClusterInfo.getInstance().isAcceptedClient(clientInfo)){
//                return new SofaResponse();
//            }
//        }
        System.out.println(request.getRequestProps());
        SofaResponse response = invoker.invoke(request);
        return response;
    }
}
