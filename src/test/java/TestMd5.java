import com.hxh.utils.EncryptUtils;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class TestMd5 {

    @Test
    public void testMd5() throws NoSuchAlgorithmException {
//        MessageDigest md5 = MessageDigest.getInstance("MD5");//md5摘要对象
//        md5.update("admin".getBytes());//打乱
//        byte[] digest = md5.digest();//摘要运算
//        System.out.println(digest);
//        System.out.println(Arrays.toString(digest));
//        System.out.println(EncryptUtils.toHexString(digest));
//        //摘要计算(密码摘要运算+账号)

        MessageDigest md5=MessageDigest.getInstance("MD5");
        md5.update("admin".getBytes());
        byte[] digest=md5.digest();
        System.out.println(digest);
        System.out.println(EncryptUtils.toHexString(digest));
    }
}
