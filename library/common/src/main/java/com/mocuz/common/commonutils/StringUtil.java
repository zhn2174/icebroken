package com.mocuz.common.commonutils;

import android.graphics.Paint;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.webkit.URLUtil;

import com.umeng.socialize.sina.helper.MD5;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String操作工具类
 */
public abstract class StringUtil {

    private static DecimalFormat fmt = new DecimalFormat("##,###,###,##0.00");

    private StringUtil() {
        throw new InstantiationError("工具类无法实例化");
    }

    /**
     * 统计字符串长度,一个双字节字符长度计2，ASCII字符计1
     *
     * @param str 字符串
     */
    public static int getLength(String str) {
        return str.replaceAll("[^\\x00-\\xff]", "aa").length();
    }

    public static String getStringByBytes(byte[] bs) {
        return new String(bs);
    }

    /**
     * 判断字符串是否为空，即为null或""
     */
    public static boolean isEmpty(String str) {
        return ((str == null) || (str.length() == 0));
    }

    /**
     * 判断字符串是否不为空，即不为null且不为""
     */
    public static boolean isNotEmpty(String str) {
        return (!(isEmpty(str)));
    }

    /**
     * 判断字符串是否为空白，即为null或为" "
     */
    public static boolean isBlank(String str) {
        return ((str == null) || (str.trim().length() == 0));
    }

    /**
     * 判断字符串是否不为空白，即不为null且不为" "
     */
    public static boolean isNotBlank(String str) {
        return (!(isBlank(str)));
    }

    /**
     * 检查字符串是不是jsonObject
     *
     * @param str
     * @return
     */
    public static boolean isJsonObject(String str) {
        if (isBlank(str)) {
            return false;
        }
        try {
            new org.json.JSONObject(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 检查字符串是不是jsonArray
     *
     * @param str
     * @return
     */
    public static boolean isJsonArray(String str) {
        if (isBlank(str)) {
            return false;
        }
        try {
            new JSONArray(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isUrl(String url) {
        if (Patterns.WEB_URL.matcher(url).matches() || URLUtil.isValidUrl(url)) {
            return true;
        }
        return false;
    }

    /**
     * 生成长度为5到10的随机字符串. 随机字符串的内容包含[1-9 A-Z a-z]的字符.
     *
     * @return 随机字符串.
     */
    public static String buildRandomString() {
        return buildRandomString(5, 10);
    }

    /**
     * 生成随机字符串. 随机字符串的内容包含[1-9 A-Z a-z]的字符.
     *
     * @param length 必须为正整数 随机字符串的长度
     * @return 随机字符串.
     */
    public static synchronized String buildRandomString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length只能是正整数!");
        }
        Random random = new Random();
        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < length; i++) {
            ret.append(CHAR_RANDOMS[random.nextInt(CHAR_RANDOMS.length)]);
        }
        random = null;
        return ret.toString();
    }

    /**
     * /** 生成随机字符串. 随机字符串的内容包含[1-9 A-Z a-z]的字符.
     *
     * @param min 必须为正整数 随机字符串的最小长度
     * @param max 必须为正整数 随机字符串的最大长度
     * @return 随机字符串.
     */
    public static synchronized String buildRandomString(int min, int max) {
        if (min <= 0) {
            throw new IllegalArgumentException("Min 只能是正整数!");
        } else if (max <= 0) {
            throw new IllegalArgumentException("Max 只能是正整数!");
        } else if (min > max) {
            throw new IllegalArgumentException("Min 必须小于或等于 Max!");
        }
        Random random = new Random();
        int length = random.nextInt(max - min + 1) + min;
        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < length; i++) {
            ret.append(CHAR_RANDOMS[random.nextInt(CHAR_RANDOMS.length)]);
        }
        random = null;
        return ret.toString();
    }

    /**
     * 截取固定超出长度str 结尾...
     *
     * @param str    需要截图的string
     * @param length 长度
     * @Description
     */
    public static String subStringLength(String str, int length) {
        if (isNotEmpty(str) && str.length() > length) {
            str = str.substring(0, length) + "...";
        }
        return str;
    }

    private static final char[] CHAR_RANDOMS = {'1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z'};

    /**
     * 字符串转为int
     *
     * @param str
     * @Description
     */
    public static int StringToInt(String str) {
        int result = 0;
        if (null == str) {
            return 0;
        }
        try {
            result = Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 按默认格式格式化字符串
     *
     * @param str
     * @Description
     */
    public static String parseFormatStr(String str) {
        String result = "";
        if (isNotEmpty(str)) {
            try {
                result = "" + fmt.parse(str);
            } catch (ParseException e) {
                Log.e("TAG", "parse the double error!", e);
            }
        }
        return result;
    }

    /**
     * 按指定格式格式化字符串
     *
     * @param str
     * @param df
     * @Description
     */
    public static String parseFormatStr(String str, DecimalFormat df) {
        String result = "";
        try {
            result = "" + df.parse(str);
        } catch (ParseException e) {
        }
        return result;
    }

    /**
     * 将长整型按万为底且保留一位格式化为字符串
     *
     * @param count
     * @Description
     */
    public static String formateCount(long count) {
        if (count >= 1000) {
            BigDecimal bg = new BigDecimal(count * 1.0 / 1000);
            return bg.setScale(1, BigDecimal.ROUND_DOWN).toString() + "k";
        }
        return count + "";
    }

    /**
     * 保留两位小数
     *
     * @param count
     * @return
     */
    public static String formateDecimal2(double count) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(count).replaceAll(",", ".");
    }


    /**
     * 将double按默认格式格式化为字符串
     *
     * @param str
     * @Description
     */
    public static String formateStr(Double str) {
        return fmt.format(str);
    }

    /**
     * 登录检测输入的手机号
     *
     * @param phoneNum
     * @return
     */
    public static boolean checkPhoneNum(String phoneNum) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }

    /**
     * 将html占位符转化为正常字符
     *
     * @param str
     * @return
     */
    public static String Repex(String str) {
        String inputstr = str;
        String temptext = "";
        if (null != inputstr && !"".equals(inputstr)) {
            if (inputstr.indexOf("&amp;") > -1) {
                inputstr = inputstr.replaceAll("&amp;", "&");
            }
            if (inputstr.indexOf("&lt;") > -1) {
                inputstr = inputstr.replaceAll("&lt;", "<");
            }
            if (inputstr.indexOf("&gt;") > -1) {
                inputstr = inputstr.replaceAll("&gt;", ">");
            }
            if (inputstr.indexOf("&apos;") > -1) {
                inputstr = inputstr.replaceAll("&apos;", "'");
            }
            if (inputstr.indexOf("&quot;") > -1) {
                inputstr = inputstr.replaceAll("&quot;", "\"");
            }
            if (inputstr.indexOf("&#034;") > -1) {
                inputstr = inputstr.replaceAll("&#034;", "\"");
            }
            temptext = inputstr;
        }
        return temptext;
    }

    /**
     * 检查链接是否有效
     *
     * @param input
     * @return true:有效，false:无效
     * @Description
     */
    public static boolean checkURL(CharSequence input) {
        if (TextUtils.isEmpty(input)) {
            return false;
        }
        Pattern URL_PATTERN = Patterns.WEB_URL;
        boolean isURL = URL_PATTERN.matcher(input).matches();
        if (!isURL) {
            String urlString = input + "";
            if (URLUtil.isNetworkUrl(urlString)) {
                try {
                    new URL(urlString);
                    isURL = true;
                } catch (Exception e) {
                }
            }
        }
        return isURL;
    }

    /**
     * 输入流转换为String对象
     *
     * @param is
     * @return
     * @throws IOException
     * @Description
     */
    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    /**
     * 检查文件是否为图片 png、jpg
     *
     * @param path
     * @return
     * @Description
     */
    public static boolean checkUrlIsImgFile(String path) {
        boolean isImgFile = false;
        // 获取扩展名
        String fileEnd = path.substring(path.lastIndexOf(".") + 1, path.length()).toLowerCase();
        if (fileEnd.equals("png") || fileEnd.equals("jpg")) {
            isImgFile = true;
        } else {
            isImgFile = false;
        }
        return isImgFile;
    }

    /**
     * 检查文件是否为pdf
     *
     * @param path
     * @return
     * @Description
     */
    public static boolean checkUrlIsPdfFile(String path) {
        boolean isPdfFile = false;
        // 获取扩展名
        String fileEnd = path.substring(path.lastIndexOf(".") + 1, path.length()).toLowerCase();
        if (fileEnd.equals("pdf")) {
            isPdfFile = true;
        } else {
            isPdfFile = false;
        }
        return isPdfFile;
    }

    /**
     * 检查文件是否为视屏 mp4、3gp
     *
     * @param path
     * @return
     * @Description
     */
    public static boolean checkUrlIsVideoFile(String path) {
        boolean isVideoFile = false;
        // 获取扩展名
        String fileEnd = path.substring(path.lastIndexOf(".") + 1, path.length()).toLowerCase();
        if (fileEnd.equals("mp4") || fileEnd.equals("3gp")) {
            isVideoFile = true;
        } else {
            isVideoFile = false;
        }
        return isVideoFile;
    }


    /**
     * 获取16个字节时间戳
     *
     * @return
     * @throws UnsupportedEncodingException
     * @Description
     */
    public static String getTimestamp16() throws UnsupportedEncodingException {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
        String formateDate = sdf.format(d);
        byte[] a = formateDate.getBytes("utf-8");
        byte[] b = new byte[16];
        System.arraycopy(a, 0, b, 0, 16);
        return new String(b);
    }

    /**
     * 获取16个字节时间戳
     *
     * @return
     * @throws UnsupportedEncodingException
     * @Description
     */
    public static String getEmpty16() throws UnsupportedEncodingException {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 15; i++) {
            buffer.append(" ");
        }
        return buffer.toString();
    }

    /**
     * 手机号星号显示
     *
     * @return
     */
    public static String phoneCutDispaly(String mobile) {
        if (isBlank(mobile)) {
            return "";
        }
        char[] m = mobile.toCharArray();
        for (int i = 0; i < m.length; i++) {
            if (i >= 4 && i <= 7) {
                m[i] = '*';
            }
        }
        return String.valueOf(m);
    }


    /**
     * 获取百分比字符串
     *
     * @param percent
     * @return
     */
    public static String getPercentString(float percent) {
        return String.format(Locale.US, "%d%%", (int) (percent * 100));
    }

    /**
     * 删除字符串中的空白符
     *
     * @param content
     * @return String
     */
    public static String removeBlanks(String content) {
        if (content == null) {
            return null;
        }
        StringBuilder buff = new StringBuilder();
        buff.append(content);
        for (int i = buff.length() - 1; i >= 0; i--) {
            if (' ' == buff.charAt(i) || ('\n' == buff.charAt(i)) || ('\t' == buff.charAt(i))
                    || ('\r' == buff.charAt(i))) {
                buff.deleteCharAt(i);
            }
        }
        return buff.toString();
    }

    /**
     * 获取32位uuid
     *
     * @return
     */
    public static String get32UUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成唯一号
     *
     * @return
     */
    public static String get36UUID() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();
        return uniqueId;
    }

    public static String makeMd5(String source) {
        return MD5.hexdigest(source);
    }

    public static final String filterUCS4(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }

        if (str.codePointCount(0, str.length()) == str.length()) {
            return str;
        }

        StringBuilder sb = new StringBuilder();

        int index = 0;
        while (index < str.length()) {
            int codePoint = str.codePointAt(index);
            index += Character.charCount(codePoint);
            if (Character.isSupplementaryCodePoint(codePoint)) {
                continue;
            }

            sb.appendCodePoint(codePoint);
        }

        return sb.toString();
    }

    /**
     * counter ASCII character as one, otherwise two
     *
     * @param str
     * @return count
     */
    public static int counterChars(String str) {
        // return
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            int tmp = (int) str.charAt(i);
            if (tmp > 0 && tmp < 127) {
                count += 1;
            } else {
                count += 2;
            }
        }
        return count;
    }

    //获取距离
    public static String getDistance(Double distance) {
        if (distance == null || distance <= 0) {
            return "";
        }
//        DecimalFormat df = new DecimalFormat("#.00");
//        df.format()
        return distance / 1000 + "km";
    }

    //获取经纬度距离(km)
    public static String getDistance(double lat1, double lon1, double lat2, double lon2) {
        float[] results = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        return formateDecimal2(results[0]/1000);
    }


    /**
     * 获取想要的长度的空格
     *
     * @param textSize  字体大小
     * @param spaceSize 空格空间
     * @return
     */
    public static String getSpaceString(float textSize, float spaceSize) {
        String space = " ";

        Paint paint = new Paint();
        paint.setTextSize(textSize);
        float width = paint.measureText(space);
        int len = (int) Math.ceil(spaceSize / width);
        while (space.length() < len) {
            space = space.concat(" ");
        }
        return space;
    }

}
