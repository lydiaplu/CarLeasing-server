package com.lydiaplullc.CarLeasing.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    // encrypted password
    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password, salt);
    }

    // check password
    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
