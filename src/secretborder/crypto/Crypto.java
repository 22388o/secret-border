package secretborder.crypto;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class Crypto {

    private static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";
    private static final String RANDOM_NUMBER_ALGORITHM_PROVIDER = "SUN";

    private static final BigInteger MAXPRIVATEKEY
            = new BigInteger("00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364140", 16);

    /**
     * Generate a random private key that can be used with Secp256k1.
     *
     * @return 
     */
    public static byte[] generatePrivateKey() {
        SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM, RANDOM_NUMBER_ALGORITHM_PROVIDER);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            secureRandom = new SecureRandom();
        }

        // Generate the key, skipping as many as desired.
        byte[] privateKeyAttempt = new byte[32];
        secureRandom.nextBytes(privateKeyAttempt);
        BigInteger privateKeyCheck = new BigInteger(1, privateKeyAttempt);

        while (privateKeyCheck.compareTo(BigInteger.ZERO) == 0
                || privateKeyCheck.compareTo(MAXPRIVATEKEY) == 1) {
            secureRandom.nextBytes(privateKeyAttempt);
            privateKeyCheck = new BigInteger(1, privateKeyAttempt);
        }

        return privateKeyAttempt;
    }

}
