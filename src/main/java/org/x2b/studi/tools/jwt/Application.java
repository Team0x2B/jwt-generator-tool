package org.x2b.studi.tools.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Application {


    public static void main(String[] args) throws IOException {
        String outputFilePath = "signed_token.json";
        if (args.length > 1) {
            outputFilePath = args[1];
        }

        File outputFile = new File(outputFilePath);
        if(outputFile.exists()) {
            outputFile.delete();
        }
        outputFile.createNewFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(generateJWT());
        }
    }


    private static String generateJWT() throws IOException {
        Token token = new ObjectMapper()
                .readValue(new File("token_claims.json"), Token.class);
        System.out.println("Input was: \n" + token);
        JWTCreator creator = new JWTCreator();
        return creator.createJwt(token.getClaims(), token.getSecret());
    }
}
