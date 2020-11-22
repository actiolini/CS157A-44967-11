package moviebuddy.util;

import java.util.Arrays;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;

public class Passwords {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int SALT_SIZE = 16;
    private static final int ITERATIONS = 80000;
    private static final int KEY_LENGTH = 256;
    private static final String HASH_ALGORITHM = "PBKDF2WITHHMACSHA256";

    private Passwords() {
    }

    public static byte[] getSalt() {
        byte[] salt = new byte[SALT_SIZE];
        RANDOM.nextBytes(salt);
        return salt;
    }

    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(HASH_ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public static boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash) {
        byte[] pwdHash = hash(password, salt);
        if (pwdHash.length != expectedHash.length)
            return false;
        return Arrays.equals(pwdHash, expectedHash);
    }
}
