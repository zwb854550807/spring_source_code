/**
 * 中信银行卡中心版权所有
 */

import com.google.common.base.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密工具类
 *
 * @author T_chenhewen_kzx
 * @date 2018年9月18日
 */
public class AESUtil {

    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final String AES_TYPE = "AES";

    private static final String AES_MODE = "AES/ECB/PKCS5Padding";

    /**
     * 通用加密
     *
     * @param data  原始数据
     * @param key   密钥
     * @param toHex 密文是否转成16进制
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key, boolean toHex) throws Exception {
        if (data == null) {
            return null;
        }
        byte[] content = null;
        content = data.getBytes();
        byte[] result = doAES(content, key, Cipher.ENCRYPT_MODE);
        if (result == null) {
            return null;
        }
        if (toHex) {
            return byteArr2HexStr(result);
        } else {
            return new String(result, DEFAULT_CHARSET);
        }

    }

    /**
     * 通用解密
     *
     * @param data  原始数据
     * @param key   密钥
     * @param toHex 密文是否16进制
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String key, boolean toHex) throws Exception {
        if (data == null) {
            return null;
        }
        byte[] content = null;
        if (toHex) {
            content = hexStr2ByteArr(data);
        } else {
            content = data.getBytes(DEFAULT_CHARSET);
        }
        byte[] result = doAES(content, key, Cipher.DECRYPT_MODE);
        if (result == null) {
            return null;
        }
        return new String(result, DEFAULT_CHARSET);
    }

    /**
     * 加密解密
     *
     * @param content 要加密或解密的内容
     * @param key     密钥
     * @param mode    加密Cipher.ENCRYPT_MODE 或 解密 Cipher.DECRYPT_MODE
     * @return
     * @throws Exception
     */
    public static byte[] doAES(byte[] content, String key, int mode) throws Exception {

        if (content == null) {
            return null;
        }

        // 密钥补位
        int plus = 16 - key.length();
        byte[] data = key.getBytes(DEFAULT_CHARSET);
        byte[] raw = new byte[16];
        byte[] plusbyte = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
        for (int i = 0; i < 16; i++) {
            if (data.length > i)
                raw[i] = data[i];
            else
                raw[i] = plusbyte[plus];
        }

        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES_TYPE);
        // 5.生成密码器
        Cipher cipher = Cipher.getInstance(AES_MODE);

        cipher.init(mode, skeySpec);

        byte[] result = cipher.doFinal(content);

        return result;
    }

    /**
     * 将byte数组转换为表示16进制值的字符串
     *
     * @param arrB
     * @return 密文String
     */
    public static String byteArr2HexStr(byte[] arrB) {
        int len = arrB.length;
        StringBuilder sb = new StringBuilder(len * 2);
        for (int i = 0; i < len; i++) {
            int tmp = arrB[i];
            while (tmp < 0) {
                tmp += 256;
            }
            if (tmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(tmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组
     *
     * @param strIn
     * @return 明文byte[]
     * @throws Exception
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes(Charsets.UTF_8);
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2, Charsets.UTF_8);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public static void main(String args[]) {
        String data = "2831227302";
        try {
            String result = new String(
                    Base64.encodeBase64((AESUtil.doAES(data.getBytes(DEFAULT_CHARSET), "aesSecretkey", Cipher.ENCRYPT_MODE))));
            System.out.println("加密后的数据:" + result);

            String result2 = new String(AESUtil.doAES(Base64.decodeBase64("pm9s2WikPmNNAWEfrw/qXg=="), "aesSecretkey", Cipher.DECRYPT_MODE));
            System.out.println("解密后的数据:" + result2);


        } catch (Exception e) {
            logger.error("异常", e);
        }
    }

}
