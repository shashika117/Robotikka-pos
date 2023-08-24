package com.devstack.pos.util;

import org.mindrot.BCrypt;

//Solid -> Single Responsibility
public class PasswordManager {
    public static String encryptPassword(String plainText){
        return BCrypt.hashpw(plainText,BCrypt.gensalt(10));
    }
    public static boolean checkPassword(String plaintText, String hashPassword){
        return BCrypt.checkpw(plaintText,hashPassword);
    }
}
