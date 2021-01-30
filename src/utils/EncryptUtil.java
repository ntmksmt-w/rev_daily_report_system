package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class EncryptUtil {
    public static String getPasswordEncrypt(String plain_p, String pepper) {
        String ret = "";

        if(plain_p != null && !plain_p.equals("")) {
            byte[] bytes;
            // パスワードb01の例: b016Ab3mtmG
            String password = plain_p + pepper;
            try {
                // SHA-256はどんな値も必ず32バイトの配列にする。1要素1バイトで要素数32。
                // 1バイトは10進数で-128〜127。10進数で値が格納されている。例 [17, 36, -92, ...]
                bytes = MessageDigest.getInstance("SHA-256").digest(password.getBytes());
                // 4bitで16進数の1文字、8bit(1バイト)で16進数の2文字にあたるため、64文字になる
                // 16進数の64文字の文字列に変換 1124A45D9F4DEE0C991FBFCD5B8388E8D9D3682BEABA2D3104AEEA371A173DCD
                ret = DatatypeConverter.printHexBinary(bytes);
            } catch(NoSuchAlgorithmException ex) {}
        }

        return ret;
    }
}
