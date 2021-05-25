package com.cas.controller.webservice.impl;

import com.cas.controller.webservice.WebServiceI;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author gacl
 * SEI的具体实现
 * 访问地址：http://localhost:8088/demo/api?wsdl
 *
 * 使用@WebService注解标注WebServiceI接口的实现类WebServiceImpl
 *
 * 客户端调用：com.cas.webservice.TestHelloWorld
 */
@WebService(name = "WebServiceI",// 暴露服务名称
        targetNamespace = "http://webservice.controller.cas.com", // 命名空间，一般是接口的包名
        endpointInterface = "com.cas.controller.webservice.WebServiceI"
)
public class WebServiceImpl implements WebServiceI {

    @Override
    public String sayHello(String name) {
        System.out.println("WebService sayHello " + name);
        return "sayHello " + name;
    }
}
