package com.cas.webservice;

import com.cas.constant.Constants;
import com.cas.webservice.pojo.pre.PreOperationsRequest;
import com.cas.webservice.pojo.pre.PreOperationsResponse;
import com.cas.webservice.pojo.res.OperationResultNotifyResponse;
import com.cas.webservice.pojo.res.OperationResultRequest;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * @author xianglong
 * 定义SEI(WebService EndPoint Interface(终端))
 */
@WebService(name = "WebServiceI",// 暴露服务名称
        targetNamespace = Constants.TSM_URL // 命名空间，一般是接口的包名
)
public interface WebServiceI {

    @WebMethod(operationName = "preOperations")
    @WebResult(name = "PreOperationsReqResponse", targetNamespace = Constants.TSM_URL)
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    PreOperationsResponse preOperations(@WebParam(name = "PreOperationsReq", targetNamespace = Constants.TSM_URL) PreOperationsRequest request);

    @WebMethod(operationName = "OperationResultNotify")
    @WebResult(name = "OperationResultNotifyResponse", targetNamespace = Constants.TSM_URL)
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    OperationResultNotifyResponse operationResult(@WebParam(name = "OperationResultNotify", targetNamespace = Constants.TSM_URL) OperationResultRequest resultRequest);

}
