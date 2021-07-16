package com.cas.webservice.impl;

import com.cas.constant.Constants;
import com.cas.webservice.WebServiceI;
import com.cas.webservice.pojo.pre.PreOperationsRequest;
import com.cas.webservice.pojo.pre.PreOperationsResponse;
import com.cas.webservice.pojo.res.OperationResultNotifyResponse;
import com.cas.webservice.pojo.res.OperationResultRequest;

import javax.jws.WebService;

/**
 * @author xianglong
 * SEI的具体实现
 * 访问地址：http://localhost:8202/tsm/api?wsdl
 * <p>
 * 使用@WebService注解标注WebServiceI接口的实现类WebServiceImpl
 * <p>
 */
@WebService(name = "WebServiceI",// 暴露服务名称
        targetNamespace = Constants.TSM_URL,
        serviceName = "WebServiceI",
        endpointInterface = "com.cas.webservice.WebServiceI")
public class WebServiceImpl implements WebServiceI {

    @Override
    public PreOperationsResponse preOperations(PreOperationsRequest request) {
        // 逻辑处理
        return null;
    }

    @Override
    public OperationResultNotifyResponse operationResult(OperationResultRequest resultRequest) {
        // 逻辑处理
        return null;
    }
}
