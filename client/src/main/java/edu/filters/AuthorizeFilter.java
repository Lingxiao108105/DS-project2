package edu.filters;


import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.filter.Filter;
import com.alipay.sofa.rpc.filter.FilterInvoker;
import edu.common.exception.AuthorizeException;
import edu.common.exception.ErrorAuthorizeException;
import edu.common.exception.ExceptionAuthorizeException;
import edu.config.ClientConfig;
import edu.javafx.ErrorMessageGUIController;
import edu.javafx.ExceptionMessageGUIController;
import javafx.application.Platform;

public class AuthorizeFilter extends Filter {

    @Override
    public boolean needToLoad(FilterInvoker invoker) {
        return true;
    }

    @Override
    public SofaResponse invoke(FilterInvoker invoker, SofaRequest request) throws SofaRpcException {
        //add authorize information
        request.addRequestProp("ClientInfo", ClientConfig.clientInfo);
        SofaResponse response = invoker.invoke(request);
        Object o = response.getAppResponse();
        //catch AuthorizeException, if successfully catch, return null in response
        if(o instanceof AuthorizeException){
            AuthorizeException authorizeException = (AuthorizeException) o;
            //authorizeException.printStackTrace();
            if(o instanceof ErrorAuthorizeException){
                Platform.runLater(()->{
                    new ErrorMessageGUIController(authorizeException.getMessage());
                });
            }else if(o instanceof ExceptionAuthorizeException){
                Platform.runLater(()->{
                    new ExceptionMessageGUIController(authorizeException.getMessage());
                });
            }
            response = new SofaResponse();
            response.setAppResponse(null);
        }
        return response;
    }
}
