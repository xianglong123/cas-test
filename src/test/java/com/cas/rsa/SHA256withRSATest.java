package com.cas.rsa;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 下午2:49 2021/5/24
 * @version: V1.0
 * @review: 数字签名/验签
 */
public class SHA256withRSATest {

    public static void main(String[] args) {
        String privateKeyBase64 = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO0WE8Z33dDZ7Ee/wXNpMcFvBlK5eIOw9ghEgSF1KCnELtd8dA31X5CWAHkl/YQHbUS0Ampd0WNjWWNAO0O/qgfXxhRIooKsX3Q5fthpmL806eayEMjtLgX8UDgux/pZInamavJlShWwZo6QP05kMoBbpLWtgWU9FvYWIjqxood1AgMBAAECgYAitbx77kEaikkaIQQGrEQ1TFaZ5l7zMsV2ZOcd2Zvhb4AnGqiYAZA16AwWMH5q5bZ03EDNR0J4QUuNKRJ0IvuWpbgsdvW2djVDmysCyovUp0xmtRNiUdRBXZM2W25uY8Pf+uCrkoOfbuCSGjEl5OqBU2LRGvFvRgUSAh5QXo7XQQJBAPgth4VhSKoObP3PLXBkr0REL/3TOUCXcUuw7oq9ovGgNP4biXq3D7aHtw7V1y53xgPh1kd0F6y87oXUDNcP27UCQQD0jw2W8GysFVV6TdJ1zuo6sx7xEkDwUsB6eHR0JYH5yCDOXD6ufwpdeLTdwXweFfiqnbZqfJWw8m6g2/NHTNTBAkBmX5GZqYmp7I11HTMnO8E4rdAGKk1hoZbwnCmqPZOrjGSPtZg+cKkgqFcQ3ZEH8AxUqaIUk4T4km7p8ZDiXf5JAkEAripmwjTtH8x9uIFNvgqi8zT3dzXJu50jaNT6EnFX1Fx7SJuXJExjRFgfbsJJs19wXYm+DK43KPIuaS7bXc7QwQJBALLMY7u1rIERWyuCXa3KpO4gtALEHcLXn+ptUmU5SV+/xm4PHR0qYnsvdry9/XG1m3mPYnvSDFIftL4+RDEOosw=";
        String publicKeyBase64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDtFhPGd93Q2exHv8FzaTHBbwZSuXiDsPYIRIEhdSgpxC7XfHQN9V+QlgB5Jf2EB21EtAJqXdFjY1ljQDtDv6oH18YUSKKCrF90OX7YaZi/NOnmshDI7S4F/FA4Lsf6WSJ2pmryZUoVsGaOkD9OZDKAW6S1rYFlPRb2FiI6saKHdQIDAQAB";
        System.out.println("私钥=== "+ privateKeyBase64);
        System.out.println("公钥=== "+ publicKeyBase64);
        // 公私钥对
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, privateKeyBase64, publicKeyBase64);
        byte[] bytes = "{\"appletInstanceAid\":\"D15600010180000000000004B0015200\",\"bearer\":6,\"msisdn\":\"13261144197\",\"opType\":2,\"spid\":\"YDSZSF\",\"timestamp\":\"1630661573994\"}".getBytes();
        byte[] sign1 = sign.sign(bytes);
//
        byte[] bytes1 = Base64.encodeBase64(sign1);
        System.out.println("BASE64: " + new String(bytes1));

        boolean verify = sign.verify(bytes, sign1);
        System.out.println(verify);

        // MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDtFhPGd93Q2exHv8FzaTHBbwZSuXiDsPYIRIEhdSgpxC7XfHQN9V+QlgB5Jf2EB21EtAJqXdFjY1ljQDtDv6oH18YUSKKCrF90OX7YaZi/NOnmshDI7S4F/FA4Lsf6WSJ2pmryZUoVsGaOkD9OZDKAW6S1rYFlPRb2FiI6saKHdQIDAQAB
        // MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO0WE8Z33dDZ7Ee/wXNpMcFvBlK5eIOw9ghEgSF1KCnELtd8dA31X5CWAHkl/YQHbUS0Ampd0WNjWWNAO0O/qgfXxhRIooKsX3Q5fthpmL806eayEMjtLgX8UDgux/pZInamavJlShWwZo6QP05kMoBbpLWtgWU9FvYWIjqxood1AgMBAAECgYAitbx77kEaikkaIQQGrEQ1TFaZ5l7zMsV2ZOcd2Zvhb4AnGqiYAZA16AwWMH5q5bZ03EDNR0J4QUuNKRJ0IvuWpbgsdvW2djVDmysCyovUp0xmtRNiUdRBXZM2W25uY8Pf+uCrkoOfbuCSGjEl5OqBU2LRGvFvRgUSAh5QXo7XQQJBAPgth4VhSKoObP3PLXBkr0REL/3TOUCXcUuw7oq9ovGgNP4biXq3D7aHtw7V1y53xgPh1kd0F6y87oXUDNcP27UCQQD0jw2W8GysFVV6TdJ1zuo6sx7xEkDwUsB6eHR0JYH5yCDOXD6ufwpdeLTdwXweFfiqnbZqfJWw8m6g2/NHTNTBAkBmX5GZqYmp7I11HTMnO8E4rdAGKk1hoZbwnCmqPZOrjGSPtZg+cKkgqFcQ3ZEH8AxUqaIUk4T4km7p8ZDiXf5JAkEAripmwjTtH8x9uIFNvgqi8zT3dzXJu50jaNT6EnFX1Fx7SJuXJExjRFgfbsJJs19wXYm+DK43KPIuaS7bXc7QwQJBALLMY7u1rIERWyuCXa3KpO4gtALEHcLXn+ptUmU5SV+/xm4PHR0qYnsvdry9/XG1m3mPYnvSDFIftL4+RDEOosw=
    }
// 5fbac1ee203b41c9f13f46bd81f99f572732a56fe6d3b96d038589f730b43a91115ce04e76ae6ebdfe14c0d49d148cc294af530236bf4a4c849bcef42245bba8df018c86151da80e507c4ea6578e6a3481bb406dce98d98998435746b1c1358ee7f4f2c3c105d4e9ffc4936e08d0dc5b3b530c8d0e6ccee2652249cd727c6090


    @Test
    public void test() {
//        SM4-CBC
    }

}
