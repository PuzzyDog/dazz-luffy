package wei.lab.spring.daze;

import java.util.Arrays;
import java.util.List;

/**
 * created by weixuecai on 2019/4/12
 */
public class App4 {
    public static void main(String[] args) {
        List<String> cases = Arrays.asList(
                "172.168.5.1",
                "0.0.0.0",
                "0.0.0.1",
                "-0.0.0.1", //非法字符
                " 172.168.5.1 ",    //首尾含有空格
                "172. 168.5.1", //合法空格
                "172.168  .5.1",    //合法空格
                "172.1 58.5.1", //两个数字之间一个空格
                "172.1   58.5.1",   //两个数字之间两个空格
                "256.1.1.1");   //某个段内的数据<0 || > 255

        for(String str: cases) {
            Long ret = convert(str);
            System.out.printf("%15s:  %s\n", str, (ret != null)? ret : null);
        }
    }

    private static Long convert(String str) {
        long result = 0;

        int curNum = 0;
        char pre = NULL_CHAR;
        for(int i=0; i<str.length(); i++) {
            char ch = str.charAt(i);
            if(!isValidChar(ch)) {
                return null;
            }

            if(isSpace(ch) && isDigit(pre) && i + 1 < str.length() && isDigit(str.charAt(i+1))) {
                return null;
            }

            if(isSpace(ch)) {
                //'pre' 保持不变，以处理两个数字之间多个空格的情况
                continue;
            } else if(isDigit(ch)) {
                curNum = curNum * 10 + (ch - '0');
                pre = ch;
            } else {//is dot
                if(curNum <0 || curNum > 255) {
                    return null;
                }
                result  = (result << 8 ) | curNum;
                curNum = 0;
                pre = DOT;
            }
        }

        if(curNum <0 || curNum > 255) {
            return null;
        }

        return (result << 8 ) | curNum;
    }

    private static boolean isSpace(char ch) {
        return ch == SPACE;
    }

    private static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private static boolean isDot(char ch) {
        return ch == DOT;
    }

    private static boolean isValidChar(char ch) {
        return isDigit(ch) || isSpace(ch) || isDot(ch);
    }

    private static char SPACE = ' ';
    private static char DOT = '.';
    private static char NULL_CHAR = '#';

}
