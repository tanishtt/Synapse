package com.tanishmohanta.synapse.user_service.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Bcrypt {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean matchPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
