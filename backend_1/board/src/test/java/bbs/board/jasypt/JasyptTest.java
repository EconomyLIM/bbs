package bbs.board.jasypt;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * date           : 2024-12-19
 * created by     : 임경재
 * description    :
 */
@SpringBootTest
public class JasyptTest {

    String encryptKey;
    @Test
    void jasypt(){
        encryptKey = System.getProperty("jasypt.encryptor.password");

        String url = "jdbc:mysql://localhost:3306/highgarden_db?serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
        String username = "highgarden";
        String password = "1234";

        String slackUrl = "https://hooks.slack.com/services/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";

        String encryptUrl = jasyptEncrypt(url);
        String encryptUsername = jasyptEncrypt(username);
        String encryptPassword = jasyptEncrypt(password);
        String encryptSlackUrl = jasyptEncrypt(slackUrl);

        String decryptUrl= jasyptDecryt(encryptUrl);
        String decryptUsername= jasyptDecryt(encryptUsername);
        String decryptPassword= jasyptDecryt(encryptPassword);
        String decryptSlackUrl= jasyptDecryt(encryptSlackUrl);

        System.out.println("encryptUrl : " + encryptUrl);
        System.out.println("encryptUsername : " + encryptUsername);
        System.out.println("encryptPassword : " + encryptPassword);
        System.out.println("encryptSlackUrl = " + encryptSlackUrl);

        System.out.println("decryptUrl : " + decryptUrl);
        System.out.println("decryptUserName : " + decryptUsername);
        System.out.println("decryptPassword : " + decryptPassword);
        System.out.println("decryptSlackUrl : " + decryptSlackUrl);

        Assertions.assertThat(url).isEqualTo(jasyptDecryt(encryptUrl));
        Assertions.assertThat(slackUrl).isEqualTo(jasyptDecryt(encryptSlackUrl));
    }


    private String jasyptEncrypt(String input) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(encryptKey);
        return encryptor.encrypt(input);
    }

    private String jasyptDecryt(String input){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(encryptKey);
        return encryptor.decrypt(input);
    }

}
