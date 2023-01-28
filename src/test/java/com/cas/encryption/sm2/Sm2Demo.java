package com.cas.encryption.sm2;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.MessageFormat;

import javax.crypto.Cipher;

import cn.hutool.crypto.PemUtil;
import com.cas.des.des3_ecb.HexConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Sm2Demo {

	private static final BouncyCastleProvider PROVIDER = new BouncyCastleProvider();

	public static void main(String[] args) throws UnsupportedEncodingException {
		// 生成密钥
		KeyPair keyPair = generate();
		System.out.println(MessageFormat.format("完成软加密生成SM2密钥, 公钥为: {0}, 私钥为: {1}", bytesToHex(keyPair.getPublic().getEncoded()),
				bytesToHex(keyPair.getPrivate().getEncoded())));

		/**
		 * {"signature":"304402201DE58F754235138C6E96FAEA388B6E3C3CE13651DE0F9B65440A6E19DCF80CDA02202260CF9B84092CC0A30782E9A62962D5422A5F52F51DD22EB2EBD08413E37295",
		 * "returnCode":"000000",
		 * "returnMessage":"操作成功",
		 * "dsopSessionId":"20220810160219417596",
		 * "mobileNo":"04940A1B8F19206B90A63BDD3AEF38BAE33170E0692F2735E621987F6D8D7AF42ECE99007978F0909F88360FA9915F24B90C9BB3DEA90C4B8C34FFF63396D259E7B88C8C86910DB93E50FBB1150FA65365ED5B326B7AA0ACA0AE27A95E75C8283147BDFB7230ED11F23ECE56
		 * "}
		 */

		byte[] plaintext = "13800138000".getBytes("ASCII");
		byte[] ciphertext = encrypt(plaintext, keyPair.getPublic().getEncoded());// SM2的密文是随机的，无法比对密文值，只能通过解密验证
		byte[] plaintext2 = decrypt(HexConverter.hexString2ByteArray("04940A1B8F19206B90A63BDD3AEF38BAE33170E0692F2735E621987F6D8D7AF42ECE99007978F0909F88360FA9915F24B90C9BB3DEA90C4B8C34FFF63396D259E7B88C8C86910DB93E50FBB1150FA65365ED5B326B7AA0ACA0AE27A95E75C8283147BDFB7230ED11F23ECE56"), HexConverter.hexString2ByteArray("308193020100301306072A8648CE3D020106082A811CCF5501822D047930770201010420C9C8A12064639BBA5343E22790ED86035CCD1EB85B192206BEE4531C85FF16E5A00A06082A811CCF5501822DA14403420004F8476495862610B35854A3454F79CE62AB2C551C471D16AF9ED8D0A2003FE5DC40A03502AA1582611F16E5AA533A5162AC52B23A98FB2C99EFDAD80C6BDFEAD9"));
		System.out.println(MessageFormat.format("解密后的明文为: {0}", new String(plaintext2, "ASCII")));

		// 1234567890
		// 15811317734
		// 9FEF8AB4-ECFF-4D90-B493-244C5B150E9D
		//
		byte[] message = "158113177349FEF8AB4-ECFF-4D90-B493-244C5B150E9D1234567890".getBytes("UTF-8");
		byte[] signature = sign(message, HexConverter.hexString2ByteArray("308193020100301306072A8648CE3D020106082A811CCF5501822D047930770201010420C9C8A12064639BBA5343E22790ED86035CCD1EB85B192206BEE4531C85FF16E5A00A06082A811CCF5501822DA14403420004F8476495862610B35854A3454F79CE62AB2C551C471D16AF9ED8D0A2003FE5DC40A03502AA1582611F16E5AA533A5162AC52B23A98FB2C99EFDAD80C6BDFEAD9"));
		boolean verifyResult = verify("2022081016021941759604940A1B8F19206B90A63BDD3AEF38BAE33170E0692F2735E621987F6D8D7AF42ECE99007978F0909F88360FA9915F24B90C9BB3DEA90C4B8C34FFF63396D259E7B88C8C86910DB93E50FBB1150FA65365ED5B326B7AA0ACA0AE27A95E75C8283147BDFB7230ED11F23ECE56000000操作成功".getBytes("UTF-8"),
				HexConverter.hexString2ByteArray("304402201DE58F754235138C6E96FAEA388B6E3C3CE13651DE0F9B65440A6E19DCF80CDA02202260CF9B84092CC0A30782E9A62962D5422A5F52F51DD22EB2EBD08413E37295"),
				HexConverter.hexString2ByteArray("3059301306072A8648CE3D020106082A811CCF5501822D034200046DF9768BD6359D7A4A64597E40BA3068B570D1F5AD00E2797F7E004B907C64AD21A399114D58F1E654EB9A455348CEBF80ECB4194F2B21CEC7A518231DA4C146"));// 验签结果：true表示成功，false表示失败
		System.out.println(MessageFormat.format("验签结果为: {0}, 签名：{1}", verifyResult, HexConverter.byteArray2HexString(signature)));
	}

	public static KeyPair generate() {
		try {// 获取SM2椭圆曲线的参数
			final ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");
			// 获取一个椭圆曲线类型的密钥对生成器
			final KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", PROVIDER);
			// 使用SM2的算法区域初始化密钥生成器
			kpg.initialize(sm2Spec, new SecureRandom());
			// 获取密钥对
			KeyPair keyPair = kpg.generateKeyPair();

			return keyPair;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] encrypt(byte[] plaintext, byte[] publicKey) {
		try {
			Cipher cipher = Cipher.getInstance("SM2", PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance("EC", PROVIDER).generatePublic(new X509EncodedKeySpec(publicKey)));
			byte[] chipertext = cipher.doFinal(plaintext);

			return chipertext;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] decrypt(byte[] ciphertext, byte[] privateKey) {
		try {
			Cipher cipher = Cipher.getInstance("SM2", PROVIDER);
			cipher.init(Cipher.DECRYPT_MODE, KeyFactory.getInstance("EC", PROVIDER).generatePrivate(new PKCS8EncodedKeySpec(privateKey)));
			byte[] plaintext = cipher.doFinal(ciphertext);

			return plaintext;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] sign(byte[] src, byte[] privateKey) {
		try {
			Signature signature = Signature.getInstance("SM3withSm2", PROVIDER);
			signature.initSign(KeyFactory.getInstance("EC", PROVIDER).generatePrivate(new PKCS8EncodedKeySpec(privateKey)));
			signature.update(src);
			return signature.sign();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean verify(byte[] src, byte[] signatureBytes, byte[] publicKey) {
		try {
			Signature signature = Signature.getInstance("SM3withSm2", PROVIDER);
			signature.initVerify(KeyFactory.getInstance("EC", PROVIDER).generatePublic(new X509EncodedKeySpec(publicKey)));
			signature.update(src);
			return signature.verify(signatureBytes);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	private static String bytesToHex(byte[] bs) {
		return org.apache.commons.codec.binary.Hex.encodeHexString(bs).toUpperCase();
	}
}
