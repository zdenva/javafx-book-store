package org.but.feec.javafx.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies;
import org.but.feec.javafx.api.AuthenticationDetails;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class AuthenticationService {

    private static final Random RANDOM = new SecureRandom();
    public static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    public AuthenticationDetails genSaltHash(String password){
        AuthenticationDetails authenticationDetails = new AuthenticationDetails();
        byte [] saltBytes = getNextSalt();
        byte[] hashBytes = BCrypt.with(LongPasswordStrategies.hashSha512(BCrypt.Version.VERSION_2A)).hash(12, saltBytes, password.getBytes(StandardCharsets.UTF_8));
        String hash = Base64.getEncoder().encodeToString(hashBytes);
        String salt = Base64.getEncoder().encodeToString(saltBytes);
        System.out.println("Hash: " + hash);
        System.out.println("Salt: " + salt);
        authenticationDetails.setPasswordSalt(salt);
        authenticationDetails.setPasswordHash(hash);
        return authenticationDetails;
    }

    public boolean verifyPassword(String password, AuthenticationDetails authenticationDetails){
        byte[] saltBytes = Base64.getDecoder().decode(authenticationDetails.getPasswordSalt());
        String expectedHash = authenticationDetails.getPasswordHash();
        byte[] hashBytes = BCrypt.with(LongPasswordStrategies.hashSha512(BCrypt.Version.VERSION_2A)).hash(12, saltBytes, password.getBytes(StandardCharsets.UTF_8));
        String hash = Base64.getEncoder().encodeToString(hashBytes);
        return hash.equals(expectedHash);
    };
}
