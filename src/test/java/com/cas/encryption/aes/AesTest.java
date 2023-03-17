package com.cas.encryption.aes;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.cas.des.des3_ecb.HexConverter;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2023/3/1 1:25 下午
 * @desc
 */
public class AesTest {

    public static void main(String[] args) {
        byte[] key = HexConverter.hexString2ByteArray("c011694fb2ff7c4dc497ae987f0d0dd9");
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        String json = "{\"algFlag\":\"SM4_CBC\",\"divAlgFlag\":[22],\"divData\":[\"MTIzNDU2Nzg5MDEyMzQ1Ng==\"],\"divIv\":[\"AAAAAAAAAAAAAAAAAAAAAA==\"],\"divNum\":1,\"inputData\":\"MTIzNDU2Nzg5MDAwMDAwMA==\",\"iv\":\"AAAAAAAAAAAAAAAAAAAAAA==\",\"key\":\"1000\",\"storageType\":0}";

        //加密为16进制表示
        String encryptHex = aes.encryptBase64(json);
        System.out.println(encryptHex);
        String checkVal = "aQGHn0SibQl5KQbL";

        String timestemp = "20230301144000";
        System.out.println("timeSmp = " + timestemp);
        MD5 md5 = MD5.create();
        String hex = md5.digestHex(encryptHex + checkVal + timestemp);


        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCzUCgSmZC7ybiRTa3BJvAOi0twpQstQrlZXP5ISZwpYBm1LGTHKRmEmEqlBPdZOIxrTl7FnIlda4oW2OUsq8Yo+m42iuIAC7EcxkkgWfmUkNHrt+LZZsFZvSBwZL17wTaqEG7zAKTB2Cx3UurD/j52q3chcYaUHmX724lxN6hLcwIDAQAB";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALNQKBKZkLvJuJFNrcEm8A6LS3ClCy1CuVlc/khJnClgGbUsZMcpGYSYSqUE91k4jGtOXsWciV1rihbY5Syrxij6bjaK4gALsRzGSSBZ+ZSQ0eu34tlmwVm9IHBkvXvBNqoQbvMApMHYLHdS6sP+PnardyFxhpQeZfvbiXE3qEtzAgMBAAECgYBGrpaVOl5gf7QW5slSpZAkE4lxxkUhGreoP61mfkmlz6FilrTFAvDxoPxvjwUXcOB9Hf/iooFNR2o/9lBkKvPpojv7eQxUhylFl6+HFlnctW3KMKnjX/hSZK+QvRflS2nUyh1dxp1rA8xx10pI4o+nhG9FdByifr/bF9dJ1GSQkQJBAPCqFTcd71gG+7y5uA11r3jbyWHUHKF65m2MseynYU8vmW/bvFKkFxCtslXdYlHDZbpl/nbuN0C3jHAU0Nxo/jECQQC+vULByhCT+cFXM2ioJmCVlezrOeiCEjpmXVxeBxYd51UVWTBeEdqit0/2qg5FYXbDFqM4aRP2SdbfySOLBcbjAkAMERsbNl8H1OHOZql500rHDo4cX5xH5HelSoapTQMuCz7wQt5AboXRikQE1iLV4qw5GmAV6n/OuOVLwV9vjbwhAkB28UbD2eHkpQZv/lrxIJ1yKoAHX6EiG4PXebiz2e9szmk8WtXSk4enmHCA/SLRGSoaFe/KqZwNJZadDqzfcMbJAkEAl3jiSwPqzx7sv4F4vJIFJcGLCAYzPmFnN+l1TC+6VXZwmrZk0/JuTlSiyo7Ob23YU6gOEu/XKwqMkyN45M2/3A==";
        RSA rsa = new RSA(privateKey, publicKey);
        String encrypt = rsa.encryptBase64(HexConverter.hexString2ByteArray(hex), KeyType.PrivateKey);

        System.out.println("sign = " + encrypt);


    }

}
