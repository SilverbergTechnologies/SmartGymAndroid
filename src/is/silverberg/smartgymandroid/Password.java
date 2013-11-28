package is.silverberg.smartgymandroid;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Class for handling password methods
 * @author Daníel E. Vilhjálmsson
 *
 */
public class Password {
        
        DBHandler dbHandler = new DBHandler(null, null, null, 1);
        
        /**
         * A method to hash user passwords.
         * @param password
         * @param salt
         * @return String Array with hashed password and generated salt
         * @throws NoSuchAlgorithmException
         */
        public static String[] hashPassword(String password, String salt) throws NoSuchAlgorithmException {
                String[] s = new String[2];
                SecureRandom r = new SecureRandom();
                byte[] saltBytes = new byte[32];
                if(salt == null) {
                        r.nextBytes(saltBytes);
                } else {
                        saltBytes = fromHex(salt);
                }
                
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                digest.reset();
                digest.update(saltBytes);
                s[0] = toHex(digest.digest(password.getBytes()));
                s[1] = toHex(saltBytes);
                return s;
        }
        
        /**
         * A method to check if input password is valid
         * @param username
         * @param Password
         * @return
         * @throws NoSuchAlgorithmException
         */
        public static boolean checkPassword(String newPassword, String oldPassword, String salt) throws NoSuchAlgorithmException{
                boolean result = false;
                        
                String newPw = hashPassword(newPassword, salt)[0];
                if( oldPassword.equals(newPw) ) { result = true; }
                
                return result;
        }
        
        /**
         * A method to convert a hexadecimal String to byte array
         * @param hex
         * @return
         */
    public static byte[] fromHex(String hex)
    {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++)
        {
            binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }

    /**
     * A method to convert a byte array to a hexadecimal String
     * @param array
     * @return
     */
        public static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }
        else
        {
            return hex;
        }
    }
}