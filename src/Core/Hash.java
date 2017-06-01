package Core;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Formatter;

public class Hash
{
    public static String getGlobalSalt()
    {
        return App.opt.salt;
    }

    public static String generateSalt(int length)
    {
        SecureRandom random = new SecureRandom();
        return new BigInteger(length + 100, random).toString(32).substring(0, length);
    }

    public static String generateSalt()
    {
        return generateSalt(64);
    }

    public static String generate(String str, String salt)
    {
        if (!salt.equals(getGlobalSalt()))
        {
            salt = generate(salt, getGlobalSalt());
        }

        try
        {
            SecretKeySpec key = new SecretKeySpec(salt.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");

            mac.init(key);
            byte bytes[] = mac.doFinal(str.getBytes());
            Formatter formatter = new Formatter();

            for (byte b : bytes)
            {
                formatter.format("%02x", b);
            }

            return formatter.toString();
        }
        catch (NoSuchAlgorithmException | InvalidKeyException e)
        {
            e.printStackTrace(System.err);
        }
    }

    public static String generate(String str)
    {
        return generate(str, generateSalt());
    }
}
