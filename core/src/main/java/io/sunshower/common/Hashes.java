package io.sunshower.common;


import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by haswell on 7/17/17.
 */
public class Hashes {


    public interface HashCode {

        Algorithm getAlgorithm();


        default byte[] digest(ByteBuffer buf) {
            return digest(buf.array());

        }

        default byte[] digest(byte[] data) {
            try {
                return MessageDigest
                        .getInstance(getAlgorithm().getName())
                        .digest(data);
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(e);
            }
        }
    }



    public enum Algorithm {
        MD2("MD2"),
        MD5("MD5"),
        SHA1("SHA-1"),
        SHA256("SHA-256"),
        SHA384("SHA-384"),
        SHA512("SHA-512");


        private final String name;
        Algorithm(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static HashCode hashCode(Algorithm algorithm) {
        return new DefaultHashCode(algorithm);
    }



    private static class DefaultHashCode implements HashCode {
        final Algorithm algorithm;
        private DefaultHashCode(final Algorithm algorithm) {
            this.algorithm = algorithm;
        }

        @Override
        public Algorithm getAlgorithm() {
            return algorithm;
        }
    }

}
