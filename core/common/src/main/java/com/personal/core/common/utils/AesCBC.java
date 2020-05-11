package com.personal.core.common.utils;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理
 * 对原始数据进行AES加密后，在进行Base64编码转化；
 * 正确
 */
@Slf4j
public class AesCBC {
    /**
     * 已确认
     * 加密用的Key 可以用26个字母和数字组成
     * 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    private static String sKey="1234567890123456";
    private static String ivParameter="1234567890123456";
    private static final String encodingFormat = "utf-8";
    private static AesCBC instance=null;
    private AesCBC(){

    }
    public static AesCBC getInstance(){
        if (instance==null) {
            instance= new AesCBC();
        }
        return instance;
    }

    /**
     * @Function: AesEncryptStrategyImpl.java
     * @Description: aes加密
     *
     * @param: orignalStr 明文
     * @param: encryKey aes密钥
     * @return：返回结果描述
     * @throws：异常描述
     *
     * @author: tit_xuemenglin
     * @date: 2019年3月23日 下午3:48:47
     *
     * Modification History:
     * Date         Author          Version            Description
     *---------------------------------------------------------*
     * 2019年3月23日     tit_xuemenglin           v1.0.0               修改原因
     */
    public static String encrypt(String orignalStr,String encryKey) {
        String encryptMsg = "";
        try {
            encryptMsg = base64Encode(aesEncryptToBytes(orignalStr, encryKey));
        } catch (Exception e) {
            log.error("AES加密失败" , e);
        }
        return encryptMsg;
    }

    private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(encryptKey.getBytes());
        kgen.init(128, random);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    private static String base64Encode(byte[] bytes) {
        return (new BASE64Encoder()).encode(bytes);
    }

    /**
     * @Function: AesEncryptStrategyImpl.java
     * @Description: AES解密
     *
     * @param:@param encryptStr 密文
     * @param:@param encryKey  aes密钥
     * @param:@return 描述：
     * @return：返回结果描述
     * @throws：异常描述
     *
     * @author: tit_xuemenglin
     * @date: 2019年3月23日 下午3:56:59
     *
     * Modification History:
     * Date         Author          Version            Description
     *---------------------------------------------------------*
     * 2019年3月23日     tit_xuemenglin           v1.0.0               修改原因
     */
    public static String decrypt(String encryptStr,String encryKey) {
        String decrypMsg = "";
        try {
            decrypMsg = aesDecryptByBytes(base64Decode(encryptStr), encryKey);
        } catch (Exception e) {
            log.error("AES解密失败" , e);
        }
        return decrypMsg;
    }

    private static byte[] base64Decode(String base64Code) throws Exception {
        if(StringUtils.isNotEmpty(base64Code)) {
            return (new BASE64Decoder()).decodeBuffer(base64Code);
        }
        return null;
    }

    private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(decryptKey.getBytes());
        kgen.init(128, random);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }
    /**
     * 加密
     * @param sSrc
     * @param sKey
     * @param ivParameter
     * @return
     * @throws Exception
     */
    public static String encrypt(String sSrc, String sKey, String ivParameter) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(encodingFormat));
        //此处使用BASE64做转码。
        return new BASE64Encoder().encode(encrypted);
    }

    /**
     * 解密
     * @param sSrc
     * @param sKey
     * @param ivParameter
     * @return
     * @throws Exception
     */
    public static String decrypt(String sSrc, String sKey, String ivParameter) throws Exception {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            //先用base64解密
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original,encodingFormat);
            return originalString;
    }
    
    public static String getSecretKey() {
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            log.error("获取随机key失败",e);
        }
        //要生成多少位，只需要修改这里即可128, 192或256
        kg.init(128);
        SecretKey sk = kg.generateKey();
        BASE64Encoder base64E = new BASE64Encoder();
        String key = base64E.encode(sk.getEncoded());
        key = key.substring(0,16);
        return key;
    }
    public static void getAutoCreateAESKey(String filePath) throws NoSuchAlgorithmException, IOException {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
        SecretKey sk = kg.generateKey();
        byte[] b = sk.getEncoded();
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(b);
        fos.flush();
        fos.close();
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }


    /**
     * 生成密钥
     * @return
     * @throws Exception
     */
    public static Key getKey(String strKey) throws Exception {
        //创建密钥生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        //初始化密钥
        keyGenerator.init(new SecureRandom(strKey.getBytes()));
        //生成密钥
        SecretKey getKey = keyGenerator.generateKey();
        System.out.println("生成密钥:" + parseByte2HexStr(getKey.getEncoded()) + "----" + parseByte2HexStr(getKey.getEncoded()).length());
        return getKey;

    }
    public static void main(String[] args) throws Exception {
        // 需要加密的字串
/*        String cSrc = "{\"status\": \"1\",\t\"type\": \"2\",\"author\": \"3\"}";
        System.out.println("加密前的字串是："+cSrc);
        // 加密
        String enString = AesCBC.encrypt(cSrc,sKey,ivParameter);
        System.out.println("加密后的字串是："+ enString);

        System.out.println("1jdzWuniG6UMtoa3T6uNLA==".equals(enString));

        // 解密
        String DeString = AesCBC.decrypt(enString,sKey,ivParameter);
        System.out.println("解密后的字串是：" + DeString);*/
        getSecretKey();
    }
}