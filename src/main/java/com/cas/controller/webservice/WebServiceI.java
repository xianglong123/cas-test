package com.cas.controller.webservice;

import javax.jws.WebService;

/**
 * @author gacl
 * 定义SEI(WebService EndPoint Interface(终端))
 */
//使用@WebService注解标注WebServiceI接口
@WebService(name = "WebServiceI",// 暴露服务名称
        targetNamespace = "http://webservice.controller.cas.com" // 命名空间，一般是接口的包名
    )
public interface WebServiceI {

    //使用@WebMethod注解标注WebServiceI接口中的方法
    String sayHello(String name);

}
