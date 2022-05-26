package edu.security;

import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.filter.Filter;
import com.alipay.sofa.rpc.filter.FilterInvoker;
import edu.config.ClientConfig;

public class ClientRegisterFilter extends Filter {

    @Override
    public boolean needToLoad(FilterInvoker invoker) {
        return true;
    }

    @Override
    public SofaResponse invoke(FilterInvoker invoker, SofaRequest request) throws SofaRpcException {

        SofaResponse response = invoker.invoke(request);
        return response;
//        SofaResponse response = invoker.invoke(request);
//
//        //check whether response get correct result or get an exception
//        Object appResponse = response.getAppResponse();
//        if(appResponse instanceof RuntimeException){
//            //TODO deal with exception
//            response.setAppResponse(null);
//        }
//        return response;
    }
}
