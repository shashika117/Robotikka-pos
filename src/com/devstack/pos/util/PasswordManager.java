package com.devstack.pos.util;

import org.mindrot.BCrypt;

//Solid -> Single Responsibility
public class PasswordManager {
    public static String encryptPassword(String plainText){
        return BCrypt.hashpw(plainText,BCrypt.gensalt(10));
    }
}
