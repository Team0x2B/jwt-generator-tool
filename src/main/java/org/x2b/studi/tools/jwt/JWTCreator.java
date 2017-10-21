package org.x2b.studi.tools.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

public class JWTCreator {

    public String createJwt(Map<String, String> claims, String secret) throws UnsupportedEncodingException {
        return createJwt(claims, secret, null, null, null);
    }

    public String createJwt(Map<String, String> claims, String secret, Date iat, Date nbf, Date exp) throws UnsupportedEncodingException {
        com.auth0.jwt.JWTCreator.Builder jwtBuilder = JWT.create()
                .withIssuer(claims.get("issuer"));

        jwtBuilder.withIssuedAt(iat);
        jwtBuilder.withNotBefore(nbf);
        jwtBuilder.withExpiresAt(exp);

        for (Map.Entry<String, String> entry : claims.entrySet()) {
            jwtBuilder.withClaim(entry.getKey(), entry.getValue());
        }

        return jwtBuilder.sign(Algorithm.HMAC256(secret));
    }
}
