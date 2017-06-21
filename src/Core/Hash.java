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
    private static SecureRandom random = new SecureRandom();

    public static String getGlobalSalt()
    {
        return App.opt.salt;
    }

    public static String generateSalt(int length)
    {
        return new BigInteger(5 * length, random).toString(32);
    }

    public static String generateSalt()
    {
        return generateSalt(64);
    }

    public static String generate(String str, String salt)
    {
        if (!salt.equals(getGlobalSalt()))
        {
            salt = generate(salt);
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
            Core.App.debug(e);
            throw new RuntimeException(e);
        }
    }

    public static String generate(String str)
    {
        return generate(str, getGlobalSalt());
    }
}
