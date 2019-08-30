import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 加密签名类
 *
 * @author T_fushuping_kzx
 */
public class MD5Sign {

    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f'};

    private static MessageDigest messagedigest = null;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            nsaex.printStackTrace();
        }
    }

    /**
     * 返回16进制32位md5结果</br>
     * 可用于生成3des加密密钥
     *
     * @param s
     * @return
     */
    public static String getMD5String(String s) {
        if (StringUtils.isEmpty(s)) {
            return "";
        }
        try {
            return getMD5String(s.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    private static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    /**
     * 校验请求参数签名
     *
     * @param req
     * @param appKey
     * @param originSign
     * @param <T>
     * @return
     * @throws InnerInvocationException
     */
    public static <T> boolean validateSign(T req, String appKey, String originSign) {
        String buildSign = buildSign(req, appKey);
        return buildSign.equals(originSign);
    }

    /**
     * 获取签名字符串
     *
     * @param t
     * @param appKey
     * @param <T>
     * @return
     */
    public static <T> String buildSign(T t, String appKey) {

        String signContent = getSignatureContentStr(t);
        System.out.println(signContent);

        return getMD5String(signContent + appKey);
    }

    /**
     * 获取实体对应的签名字符串
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String getSignatureContentStr(T t) {
        String result = "";
        if (t == null) {
            return result;
        }
        try {
            Map<String, String> map = getSortedField(t);

            if (map == null) {
                return result;
            }
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("&");
            }
            String content = sb.toString();
            if (StringUtils.isEmpty(content) || StringUtils.isBlank(content)) {
                return result;
            }
            // 去除最后的&
            if (content.endsWith("&")) {
                content = StringUtils.substringBeforeLast(content, "&");
            }
            result = content;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取字典序排序的字符串
     *
     * @param t
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static <T> TreeMap<String, String> getSortedField(T t) throws IllegalAccessException {

        TreeMap<String, String> map = new TreeMap<String, String>();
        if (t == null) {
            return null;
        }
        if (isSampleType(t)) {
            throw new RuntimeException("无效的数据类型: " + t.getClass().getSimpleName());
        }
        Field[] fields = t.getClass().getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return null;
        }
        for (Field field : fields) {
            field.setAccessible(true);
            if (!field.isAccessible()) {
                continue;
            }

            Object obj = field.get(t);

            if (obj == null) {
                continue;
            }
            // 去除无需签名域
            String fieldName = field.getName();
            if (fieldName == null || "sign".equals(fieldName) || "serialVersionUID".equals(fieldName)) {
                continue;
            }
            // 如果是list拿出list里面的值
            if (obj instanceof List) {
                @SuppressWarnings("unchecked")
                List<T> listT = (List<T>) obj;
                for (T t2 : listT) {
                    map.putAll(getSortedField(t2));
                }
                continue;
            }
            if (!isSampleType(obj)) {
                map.putAll(getSortedField(obj));
                continue;
            }
            // 只处理下面几种数据类型
            if (isToSignType(obj)) {
                map.put(field.getName(), obj.toString());
            }
        }
        return map;
    }

    private static <T> boolean isToSignType(T t) {
        if (t instanceof String) {
            return true;
        }
        if (t instanceof Integer) {
            return true;
        }
        if (t instanceof Long) {
            return true;
        }
        if (t instanceof Boolean) {
            return true;
        }
        if (t instanceof Float) {
            return true;
        }
        if (t instanceof Double) {
            return true;
        }
        if (t instanceof Byte) {
            return true;
        }
        if (t instanceof Short) {
            return true;
        }
        if (t instanceof Character) {
            return true;
        }
        return false;
    }

    private static <T> boolean isSampleType(T t) {
        if (t instanceof String) {
            return true;
        }
        if (t instanceof Integer) {
            return true;
        }
        if (t instanceof Long) {
            return true;
        }
        if (t instanceof String[]) {
            return true;
        }
        if (t instanceof List) {
            return true;
        }
        if (t instanceof Map) {
            return true;
        }
        if (t instanceof Boolean) {
            return true;
        }
        if (t instanceof Float) {
            return true;
        }
        if (t instanceof Double) {
            return true;
        }
        if (t instanceof Byte) {
            return true;
        }
        if (t instanceof Short) {
            return true;
        }
        if (t instanceof Character) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
	 /*CiticRequest request = new CiticRequest();
	 String key = "1";
	 List<Banner> bannerList = new ArrayList<Banner>();
	 Banner banner = new Banner();
	 Coupon coupon = new Coupon();
	 coupon.setBrand_id("1");
	 banner.setActivityId(1);
	 banner.setCityName("2");
	 banner.setBannerId(2);
	 String[] ss = { "1", "2","6","3" };
	 Map<String, String> sss = new HashMap<>();
	 sss.put("yy", "yy");
	 
	 request.setRows(bannerList);
	// request.setSessionUserId("2");// 生成签名 String sign =
	 request.setData(ss);
	 bannerList.add(banner);
	 
	 System.out.println("json = " + JSONObject.toJSONString(request));
	 String sign = buildSign(request, key);
	 System.out.println(sign); // 校验签名 boolean
	 Boolean validate = validateSign(request, key, sign);
	 System.out.println(validate);
	 */
	 }

}