package com.cas;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AkSkDemo {
    /**
     * 使用请求 ”time（13 位时间戳）内容 + 请求内容（json）+ secret access key“ sha256 算法生成签名 signature
     *
     * @param time      13 位时间戳
     * @param data      生成签名的数据
     * @param accessKey Access Key
     * @return 签名
     */
    public static String getSignature(String time, String data, String accessKey) {
        String content = time + data + accessKey;
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            //signature = "e14826f9fc8842e96ba3137e9318df9774f4381ddfe928a667e65fc780beafe0"
            //使用 sha256 算法加密
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(content.getBytes(StandardCharsets.UTF_8));
            //转换为 16 进制
            byte[] encode = Hex.encode(messageDigest.digest());
            encodeStr = new String(encode);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    public static void main(String[] args) {
        String data = "{\"name\":\"hsw\",\"age\":18}";
//        String time = String.valueOf(System.currentTimeMillis());
        String time = "1642498897670";
        String accessKey = "4267bda75ea4450ebd1c05e4f2e418b9";
        String secretAccessKey = "fb3b30a6b4d34924ab6c310126d5bbd2";
        //使用请求 ”time（13 位时间戳）内容 + 请求内容（json）+ secret access key“ sha256 算法生成签名 signature
        String signature = getSignature(time, data, secretAccessKey);
        //请求粤居码接口
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, data);
        Request request = new Request.Builder()
                .url("https://gdrk.gdga.gd.gov.cn:9090/api-yaz/test/api-ys/zgyd/v1/createCode")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("accesskey", accessKey)
                .addHeader("signature", signature)
                .addHeader("time", time)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}