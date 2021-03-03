package com.android.custview.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactUtils {

    public static String getChineseToPinyin(String chinese) {
        StringBuilder builder = new StringBuilder();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        char[] charArray = chinese.toCharArray();
        for (char aCharArray : charArray) {
            if (Character.isSpaceChar(aCharArray)) {
                continue;
            }
            try {
                String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(aCharArray, format);
                if (pinyinArr != null) {
                    builder.append(pinyinArr[0]);
                } else {
                    builder.append(aCharArray);
                }
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
                builder.append(aCharArray);
            }
        }
        return builder.toString();
    }

    /**
     * 判断字符串是否是字母
     * @param str
     * @return
     */
    public static boolean isLetter(String str) {
        String regex=".*[a-zA-Z]+.*";
        Matcher m= Pattern.compile(regex).matcher(str);
        return m.matches();
    }
}
