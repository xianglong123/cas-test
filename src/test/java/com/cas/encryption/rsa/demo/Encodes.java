package com.cas.encryption.rsa.demo;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 *  封装各种格式的编码解码工具类.
 * 1.Commons-Codec的 hex/base64 编码
 * 2.自制的base62 编码
 * 3.Commons-Lang的xml/html escape
 * 4.JDK提供的URLEncoder
 * 
 * @author yuzhou
 * @date 2017年8月5日
 * @time 下午5:27:33
 *
 */
public class Encodes {

	public static final String DEFAULT_URL_ENCODING = "UTF-8";
	private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	/**
	 * Hex编码.
	 */
	public static String encodeHex(byte[] input) {
		return new String(Hex.encodeHex(input));
	}

	/**
	 * Hex解码.
	 */
	public static byte[] decodeHex(String input) throws DecoderException {
		return Hex.decodeHex(input.toCharArray());
	}

	/**
	 * Base64编码.
	 */
	public static String encodeBase64(byte[] input) {
		return new String(Base64.encodeBase64(input));
	}

	/**
	 * Base64编码.
	 */
	public static String encodeBase64(byte[] input, String charset) throws UnsupportedEncodingException {
		return new String(Base64.encodeBase64(input), charset);
	}
	
	/**
	 * Base64编码.
	 */
	public static String encodeBase64(String input) throws UnsupportedEncodingException {
		return new String(Base64.encodeBase64(input.getBytes(DEFAULT_URL_ENCODING)));
	}

	/**
	 * Base64解码.
	 */
	public static byte[] decodeBase64(String input) {
		return Base64.decodeBase64(input.getBytes());
	}

	/**
	 * Base64解码.
	 */
	public static byte[] decodeBase64(String input, String charset) throws UnsupportedEncodingException {
		return Base64.decodeBase64(input.getBytes(charset));
	}
	
	/**
	 * Base64解码.
	 */
	public static String decodeBase64String(String input) throws UnsupportedEncodingException {
		return new String(Base64.decodeBase64(input.getBytes()), DEFAULT_URL_ENCODING);
	}

	/**
	 * Base62编码。
	 */
	public static String encodeBase62(byte[] input) {
		char[] chars = new char[input.length];
		for (int i = 0; i < input.length; i++) {
			chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
		}
		return new String(chars);
	}

	/**
	 * Html 转码.
	 */
	public static String escapeHtml(String html) {
		return StringEscapeUtils.escapeHtml4(html);
	}

	/**
	 * Html 解码.
	 */
	public static String unescapeHtml(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml4(htmlEscaped);
	}

	/**
	 * Xml 转码.
	 */
	public static String escapeXml(String xml) {
		return StringEscapeUtils.escapeXml10(xml);
	}

	/**
	 * Xml 解码.
	 */
	public static String unescapeXml(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}

	/**
	 * URL 编码, Encode默认为UTF-8. 
	 */
	public static String urlEncode(String part) throws UnsupportedEncodingException {
		return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
	}

	/**
	 * URL 编码
	 * @param part
	 * @param encoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlEncode(String part, String encoding) throws UnsupportedEncodingException {
		return URLEncoder.encode(part, encoding);
	}

	/**
	 * URL 解码, Encode默认为UTF-8.
	 *
	 * @param part
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlDecode(String part) throws UnsupportedEncodingException {
		return URLDecoder.decode(part, DEFAULT_URL_ENCODING);

	}

	/**
	 * URL 解码
	 * @param part
	 * @param encoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlDecode(String part, String encoding) throws UnsupportedEncodingException {
		return URLDecoder.decode(part, encoding);

	}
}