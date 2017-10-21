package org.x2b.studi.tools.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class TestJWTCreator {


    @Test
    public void testCreateJwt() throws UnsupportedEncodingException {
        Map<String, String> claims = new HashMap<>();

        String key = "secret";

        claims.put("uuid", "abcd-efgh-ijklm");
        claims.put("issuer", "test-issuer");

        JWTCreator creator = new JWTCreator();
        String token = creator.createJwt(claims, key);


        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(key))
                .withIssuer("test-issuer")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        String uuidClaim = jwt.getClaim("uuid").asString();
        Assert.assertEquals("abcd-efgh-ijklm", uuidClaim);
    }

    @Test(expected = JWTVerificationException.class)
    public void testCreateJwtBadIssuer() throws UnsupportedEncodingException {
        Map<String, String> claims = new HashMap<>();

        String key = "secret";

        claims.put("uuid", "abcd-efgh-ijklm");
        claims.put("issuer", "bad-issuer");

        JWTCreator creator = new JWTCreator();
        String token = creator.createJwt(claims, key);


        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(key))
                .withIssuer("test-issuer")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        String uuidClaim = jwt.getClaim("uuid").asString();
        Assert.assertEquals("abcd-efgh-ijklm", uuidClaim);
    }

    @Test(expected = JWTVerificationException.class)
    public void testCreateJctBadSignature() throws UnsupportedEncodingException {
        Map<String, String> claims = new HashMap<>();

        String signingKey = "imahacker";
        String expectedKey = "secret";

        claims.put("uuid", "abcd-efgh-ijklm");
        claims.put("issuer", "test-issuer");

        JWTCreator creator = new JWTCreator();
        String token = creator.createJwt(claims, signingKey);


        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(expectedKey))
                .withIssuer("test-issuer")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        String uuidClaim = jwt.getClaim("uuid").asString();
        Assert.assertEquals("abcd-efgh-ijklm", uuidClaim);
    }
}
