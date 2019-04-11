/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securepassexp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Scanner;

/**
 *
 * @author hamzewi
 */
public class SecurePassExp {

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException{
        Scanner sc = new Scanner (System.in);
        String passwordToHash = sc.next();
        byte[] salt = getSalt();
        
        for(byte b : salt){
    System.out.print("" + b + " ");
            }
        System.out.println("");
        String securePassword = getSecurePassword(passwordToHash,salt);
        System.out.println(securePassword); 
       String passVerify = sc.next();
        String regeneratedPassowrdToVerify = getSecurePassword(passVerify,salt);
        System.out.println(regeneratedPassowrdToVerify); 
        
        if (securePassword .equals(regeneratedPassowrdToVerify)) {
            System.out.println("That 's ok ");
        }else {
            System.out.println("Nope :p ");
        }
    }
     
    private static String getSecurePassword(String passwordToHash, byte[] salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    private static String getSecurePassword(String passwordToHash) throws NoSuchProviderException{
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] salt = getSalt();
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
     
    
    private static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException{
    
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
    
}
