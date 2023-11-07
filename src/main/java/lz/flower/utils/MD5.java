package lz.flower.utils;

import org.apache.commons.codec.digest.DigestUtils;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Description: md5加密解密
 */
public class MD5 {

    public static String md5(String dateString) throws Exception {
        MessageDigest md5 = null;
        byte[] digest = MessageDigest.getInstance("md5").digest(dateString.getBytes("utf-8"));
        String md5code = new BigInteger(1, digest).toString(16);
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    //ASCII 码使用指定的7 位或8 位二进制数组合来表示128 或256 种可能的字符。
    //（1）0～31及127(共33个)是控制字符或通信专用字符（其余为可显示字符），
    // 如控制符：LF（换行）、CR（回车）、FF（换页）、DEL（删除）、BS（退格)、BEL（响铃）等；通信专用字			   符：SOH（文头）、EOT（文尾）、ACK（确认）等；
    // ASCII值为8、9、10 和13 分别转换为退格、制表、换行和回车字符。它们并没有特定的图形显示，但会依不同的	应用程序，而对文本显示有不同的影响。
    // 2）32～126(共95个)是字符(32是空格），其中48～57为0到9十个阿拉伯数字。
    //（3）65～90为26个大写英文字母，97～122号为26个小写英文字母，其余为一些标点符号、运算符号等
    public static String md5PlusSalt(String keyword){
        //md5加密
        String md5 = DigestUtils.md5Hex(keyword);
        //md5+盐
        char[] cArray = md5.toCharArray();
        for(int i = 0;i < cArray.length; i++)
        {
            if(cArray[i] >= 48 &&cArray[i] <= 57)
            {
                cArray[i] = (char)(105-cArray[i]);
            }
        }
        return  String.valueOf(cArray);
    }
    //解密+盐
    public static String md5MinusSalt(String md5)
    {
        char[] cArray=md5.toCharArray();
        for(int i=0;i<cArray.length;i++)
        {
            if(cArray[i]>=48&&cArray[i]<=57)
            {
                cArray[i]=(char)(105-cArray[i]);
            }
        }
        return  String.valueOf(cArray);
    }
}
