package io.sunshower.common.crypto;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by haswell on 11/17/16.
 */
public class Hashes {
    static final Map<Multihash.Type, HashFunction> hashes;

    static {
        hashes = new EnumMap<>(Multihash.Type.class) ;
        hashes.put(Multihash.Type.SHA_2_256, new SHA256());
        hashes.put(Multihash.Type.SHA_2_512, new SHA512());
    }

    public static HashFunction create(Multihash.Type type) {
        return hashes.get(type);
    }






    public interface HashFunction {
        String hash(Object...objects);
        Multihash compute(byte[] data);

    }

    public static class SHA256 implements HashFunction {

        @Override
        public String hash(Object... objects) {
            try {
                final MessageDigest digest = MessageDigest.getInstance("SHA-256");
                final StringBuilder b = new StringBuilder();
                for(Object o : objects) {
                    final byte[] values = o == null ?
                            "".getBytes() : o.toString().getBytes();

                    b.append(Base58.encode(values));
                }
                return Base58.encode(digest.digest(b.toString().getBytes()));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Multihash compute(byte[] data) {
            try {
                final MessageDigest digest = MessageDigest.getInstance("SHA-256");
                return new Multihash(Multihash.Type.SHA_2_256, digest.digest(data));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class SHA512 implements HashFunction {

        @Override
        public String hash(Object... objects) {
            try {
                final MessageDigest digest = MessageDigest.getInstance("SHA-512");
                final StringBuilder b = new StringBuilder();
                for(Object o : objects) {
                    final byte[] values = o == null ?
                            "".getBytes() : o.toString().getBytes();

                    b.append(Base58.encode(values));
                }
                return Base58.encode(digest.digest(b.toString().getBytes()));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Multihash compute(byte[] data) {
            try {
                final MessageDigest digest = MessageDigest.getInstance("SHA-512");
                return new Multihash(Multihash.Type.SHA_2_512, digest.digest(data));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
