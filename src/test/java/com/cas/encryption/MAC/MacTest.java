package com.cas.encryption.MAC;

import com.cas.des.des3_ecb.HexConverter;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/5/5 11:46 上午
 * @desc MAC消息校验码
 */
public class MacTest {

    public static final String DEFAULT_ICV = "00000000000000000000000000000000";

    public static void main(String[] args) {
        byte[] padding = HexConverter.hexString2ByteArray(
        "BDB515F86AD7178AF55959F100000161011282012843336F67455579463346386142785730747A59336774707235455661735831506D574359326F334C336F67784D4441774D4445784D4455794D4449794D4451784D7A49774D6A49784D44457A4D5145414141454271526F784536614630662F6E3730536B52355A3048754675466D3736546F496D6179347870425A2F736B7953524E3950782B6763754557616D6D6C4159564255635630454F784C4A4C5858553242786F3356366E456A4A6B2F6E45746D4E4E42504D6B6D496A2F772F545644524456434E4445794D45554349447974372B494F6D6234787743484A384453655858356E744B334135715A75336C68514756704C79646170416945416D7361485152384378653351345466574C50796C4B4468766A65563067636E333658726C624B316E625638411301001401001501001601001740333837344443353738363133394437353230443145364344313541433332393737324635393437394645413143453046463138354341374532374138344133428000000000");
        SM4Engine engine = new SM4Engine();
        org.bouncycastle.crypto.Mac mac = new CBCBlockCipherMac(engine, engine.getBlockSize() * 8, null);
        byte[] initVector = HexConverter.hexString2ByteArray(DEFAULT_ICV);
        byte[] key = HexConverter.hexString2ByteArray("684D45564A6267434A72554E4D326559");
        byte[] bytes = doMac(mac, key, initVector, padding);
        System.out.println(HexConverter.byteArray2HexString(bytes));
    }

    private static byte[] doMac(org.bouncycastle.crypto.Mac mac, byte[] key, byte[] iv, byte[] data) {
        CipherParameters cipherParameters = new KeyParameter(key);
        mac.init(new ParametersWithIV(cipherParameters, iv));
        mac.update(data, 0, data.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return result;
    }


}
