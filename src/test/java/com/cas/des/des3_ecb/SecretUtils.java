package com.cas.des.des3_ecb;

import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecretUtils {
    //根秘钥
    final static byte[] keyBytes = HexConverter.hexString2ByteArray("404142434445464748494A4B4C4D4E4F48494A4B4C4D4E4F");
    private static final String Algorithm = "DESede";    //3DES算法

    private static byte[] ivs = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
    private static IvParameterSpec iv = new IvParameterSpec(ivs);

    /************************************************************
     Function:       // encryptMode(byte[] src,byte[] key)
     Description:    // 3DES_ECB_EN
     Input:          // src-源数据(byte[]) key-加密秘钥(byte[])
     Output:         // 加密后的数据
     Return:         // byte[]
     *************************************************************/
    public static byte[] encryptMode(byte[] src, byte[] key) {
        try {
            System.out.println("没到8bytes:" + HexConverter.byteArray2HexString(src));
            SecretKey deskey = new SecretKeySpec(key, Algorithm);    //生成密钥21
            Cipher c1 = Cipher.getInstance("DESede/ECB/NoPadding");    //实例化负责加密/解密的Cipher工具类22
            c1.init(Cipher.ENCRYPT_MODE, deskey);    //初始化为加密模式23
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /************************************************************
     Function:       // decryptMode(byte[] src,byte[] key)
     Description:    // 3DES_ECB_DE
     Input:          // src-源数据(byte[]) key-解密秘钥(byte[])
     Output:         // 解密后的数据
     Return:         // byte[]
     *************************************************************/
    public static byte[] decryptMode(byte[] src, byte[] key) {
        try {
            SecretKey deskey = new SecretKeySpec(key, Algorithm);
            Cipher c1 = Cipher.getInstance("DESede/ECB/NoPadding");
            c1.init(Cipher.DECRYPT_MODE, deskey);    //初始化为解密模式44
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /************************************************************
     Function:       // length_process(String src)
     Description:    // 计算MAC时用到，字符串长度处理为8byte的倍数
     Input:          // src-待变长的源数据(String)
     Output:         // 变长后后的源数据
     Return:         // String
     *************************************************************/
    private static String length_process(String src) {
        if (src.length() % 16 == 0) {
            return src;
        } else if (src.length() % 16 == 14) {
            return src + "80";
        } else {
            while (src.length() % 8 != 0) {
                src += "0";
            }
            return src;
        }
    }

}
