package com.ms;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testJwt() {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTI2NDIzMjAsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiYWRtaW4iXSwianRpIjoiYmFhNWU0OWQtNzhhOS00NjQ0LWIzNWEtNzg1NjYwODViMWM3IiwiY2xpZW50X2lkIjoid2ViIiwic2NvcGUiOlsiYXBwIl19.DdQleCWrDLdaGAc5Pgxn9JN1S38gU5DRF7nN7zf4FeGU06cnoThlQdGk467cS6kjD0Z7t3GlQ3qNxbYrHqcFb29BLJzzwMSshMe4ULZ-G9B_diHSrTe-JM3K34N1J8pw1ueAoaesBigp0UHsuCSx3nL0N8O3kiShD_fCJkWuhTXXyJ7h7iJI5WPZmJe2Ycy6HuwvHgKzPY9nJRHl4Sy5gfnF6LW_z9yzhQOZKq4_a0bD0ilaGjBy0YR7bXwv6Gk--dF45w6HWydj0kmu3yE46ZDPx74HM08p5uJ9NYm8e8pfyRDKzY6ZZ4B4GHIlUQCh5utLsoj7yOArucgQ5fXuGw";
//        Jwt jwt = JwtHelper.decode(token);
//        System.out.println(jwt.toString());
        String publicKey  = "-----BEGIN PUBLIC KEY-----\n" +
                "          MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjkNRusWuGPvygmfK8B8E\n" +
                "          aWGf10DYcMrNrE9pTLfkyhiareZN3vNs65weHpuY37Z2kdsM0CMc62zJnU8I4IhF\n" +
                "          QQmF+bNYMXMxi0wGRBAOxUHH628IYNkiLC8j+l4xA+ur3zdy4a/ufWFynQYP0mod\n" +
                "          gLF2uEajU4nvAhiRfe5DYXwq3C65AkCwbw+9SGUnQFgosqznphP32Mlv8ycoLJ+v\n" +
                "          Di2nSNJKvF2TvZ1FNB8XWEfz/0EyQTvsavtXo4rSvML5ryCIjGTWbJy0sF+ZlWS+\n" +
                "          BG+Ulq7J1AbJ6zjT1m5GMtr9M9Hnkwv39SbmElL+I8OrRTUqwI3Yr4cvizIplycm\n" +
                "          awIDAQAB\n" +
                "          -----END PUBLIC KEY-----";
        System.out.println(JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey)));
    }
}
