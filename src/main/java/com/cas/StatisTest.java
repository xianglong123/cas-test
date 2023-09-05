//package com.cas;
//
//import com.sensorsdata.analytics.javasdk.ISensorsAnalytics;
//import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
//import com.sensorsdata.analytics.javasdk.consumer.DebugConsumer;
//import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//public class StatisTest {
//
//
//    public static void main(String[] args) throws InvalidArgumentException {
//
//
//        // http://{$service_name}.cloud.sensorsdata.cn:8106/sa?project={$project_name}&token={$project_token}
//        String serverUrl = "https://uba.cmpay.com:8106/sa?project=default";
////        String serverUrl = "https://uba.cmpay.com:8106/debug";
//
//        ISensorsAnalytics sa = new SensorsAnalytics(new DebugConsumer(serverUrl, false));
//
//        Map<String, Object> properties = new HashMap<>();
//// '$time' 属性是系统预置属性，表示事件发生的时间，如果不填入该属性，则
////            默认使用系统当前时间
//        // 固定
//        properties.put("$time", new Date());// 9545112
//        properties.put("requirement_id", 112);
//        properties.put("track_sign", "aszsf.返回结果.sim_api_result.2158");
//        properties.put("activity_name", "数字身份二重礼"); // id置换name
//
//        // 查询
//        properties.put("sim_version", "3.0+"); //
//        properties.put("banner_name", "http://127.0.0.1/"); //
//        properties.put("originalProvince", "北京"); // dicp_user查询
//        properties.put("channelSource", "1001"); // printChannel
//
//        // 传递
//        properties.put("txn_name", "完成数字身份开通"); //
//        properties.put("mbl_no", "15811317734");
//
//        sa.track("15811317734", true, "sim_api_result", properties);
//        sa.flush();
//    }
//
//}
