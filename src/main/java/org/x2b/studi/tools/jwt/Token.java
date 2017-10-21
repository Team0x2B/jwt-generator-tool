package org.x2b.studi.tools.jwt;

import java.util.HashMap;

public class Token {

    private String secret;

    private HashMap<String, String> claims;

    public String getSecret() {
        return secret;
    }

    public HashMap<String, String> getClaims() {
        return claims;
    }

    @Override
    public String toString() {
        return "Secret: " + secret + " \n" +
                claims.toString();
    }
}
