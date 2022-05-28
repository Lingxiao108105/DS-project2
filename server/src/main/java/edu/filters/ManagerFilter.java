package edu.filters;


import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.filter.Filter;
import com.alipay.sofa.rpc.filter.FilterInvoker;
import edu.common.exception.AuthorizeException;
import edu.common.exception.ErrorAuthorizeException;
import edu.common.exception.ExceptionAuthorizeException;
import edu.data.ClusterInfo;
import edu.dto.ClientInfo;

public class ManagerFilter extends Filter {

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

        if(!ClusterInfo.getInstance().getManager().equals(clientInfo)){
            //return unauthorized: not enough permission
            response.setAppResponse(new ExceptionAuthorizeException("Forbidden! You are not the manager!"));
            return response;
        }

        response = invoker.invoke(request);
        return response;
    }
}
