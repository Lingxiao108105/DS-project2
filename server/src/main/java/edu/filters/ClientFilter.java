package edu.filters;


import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.filter.Filter;
import com.alipay.sofa.rpc.filter.FilterInvoker;
import edu.common.exception.ErrorAuthorizeException;
import edu.data.ClusterInfo;
import edu.dto.ClientInfo;

/**
 * check whether the request is from an accepted client
 * @author lingxiao
 */
public class ClientFilter extends Filter {

    @Override
    public boolean needToLoad(FilterInvoker invoker) {
        return true;
    }

    @Override
    public SofaResponse invoke(FilterInvoker invoker, SofaRequest request) throws SofaRpcException {

        //check whether the request from a valid user
        Object o = request.getRequestProp("ClientInfo");
        ClientInfo clientInfo = (ClientInfo) o;

        SofaResponse response = new SofaResponse();

        //sanity check
        if(clientInfo == null){
            //return not register error
            response.setAppResponse(new ErrorAuthorizeException("Please register to this server first!"));
            return response;
        }else if(clientInfo.getId() == null){
            //return no client id
            response.setAppResponse(new ErrorAuthorizeException("No client id! Please register to this server first to get an id!"));
            return response;
        }

        //check
        if(!ClusterInfo.getInstance().isAcceptedClient(clientInfo)){
            if(ClusterInfo.getInstance().isWaitListClient(clientInfo)){
                //return isWaitListClient
                response.setAppResponse(new ErrorAuthorizeException("You are in wait list! Please wait for manager to reply on your request!"));
                return response;
            }else if(ClusterInfo.getInstance().isDeniedClient(clientInfo)){
                //return isDeniedClient
                response.setAppResponse(new ErrorAuthorizeException("You have been denied to join the server!"));
                return response;
            }else if(ClusterInfo.getInstance().isKickedClient(clientInfo)){
                //return isKickedClient
                response.setAppResponse(new ErrorAuthorizeException("You have been kicked from server!"));
                return response;
            }else {
                response.setAppResponse(new ErrorAuthorizeException("Please register to this server first! Your previous server might crash!"));
                return response;
            }
        }

        //is valid client
        response = invoker.invoke(request);
        return response;
    }
}
