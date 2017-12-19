package io.sunshower.common.crypto;

/**
 * Created by haswell on 11/8/16.
 */

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import java.io.*;
import java.util.*;

@Embeddable
@XmlRootElement
public class Multihash implements Serializable {
    public enum Type {
        SHA1(0x11, 20),
        SHA_2_256(0x12, 32),
        SHA_2_512(0x13, 64),
        SHA_3(0x14, 64),
        BLAKE_2_B(0x40, 64),
        BLAKE_2_S(0x41, 32);

        public int index, length;

        Type(int index, int length) {
            this.index = index;
            this.length = length;
        }

        private static Map<Integer, Type> lookup = new TreeMap<>();

        static {
            for (Type t : Type.values())
                lookup.put(t.index, t);
        }

        public static Type lookup(int t) {
            if (!lookup.containsKey(t))
                throw new IllegalStateException("Unknown Multihash type: " + t);
            return lookup.get(t);
        }
    }

    @Column(
        insertable = false,
        updatable = false
    )
    @XmlAttribute
    @Enumerated(EnumType.ORDINAL)
    private Type type;


    @Column(
            insertable = false,
            updatable = false
    )
    @XmlElement(name = "address")
    @XmlSchemaType(name = "hex64Binary")
    private byte[] hash;

    public Multihash() {

    }


    public Multihash(Type type, byte[] hash) {
        if (hash.length > 127)
            throw new IllegalStateException("Unsupported hash size: " + hash.length);
        if (hash.length != type.length)
            throw new IllegalStateException("Incorrect hash length: " + hash.length + " != " + type.length);
        this.type = type;
        this.hash = hash;
    }

    public Multihash(byte[] multihash) {
        this(Type.lookup(multihash[0] & 0xff), Arrays.copyOfRange(multihash, 2, multihash.length));
    }

    public Type getType() {
        return type;
    }


    public byte[] toBytes() {
        byte[] res = new byte[hash.length + 2];
        res[0] = (byte) type.index;
        res[1] = (byte) hash.length;
        System.arraycopy(hash, 0, res, 2, hash.length);
        return res;
    }

    public void serialize(DataOutput dout) throws IOException {
        dout.write(toBytes());
    }

    public static Multihash deserialize(DataInput din) throws IOException {
        int type = din.readUnsignedByte();
        int len = din.readUnsignedByte();
        Type t = Type.lookup(type);
        byte[] hash = new byte[len];
        din.readFully(hash);
        return new Multihash(t, hash);
    }

    @Override
    public String toString() {
        return toBase58();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Multihash))
            return false;
        return type == ((Multihash) o).type && Arrays.equals(hash, ((Multihash) o).hash);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(hash) ^ type.hashCode();
    }

    public String toHex() {
        StringBuilder res = new StringBuilder();
        for (byte b : toBytes())
            res.append(String.format("%x", b & 0xff));
        return res.toString();
    }

    public static Multihash fromString(String s) {
        return fromBase58(s);
    }

    public String toBase58() {
        return Base58.encode(toBytes());
    }

    public static Multihash fromHex(String hex) {
        if (hex.length() % 2 != 0)
            throw new IllegalStateException("Uneven number of hex digits!");
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        for (int i = 0; i < hex.length() - 1; i += 2)
            bout.write(Integer.valueOf(hex.substring(i, i + 2), 16));
        return new Multihash(bout.toByteArray());
    }

    public static Multihash fromBase58(String base58) {
        return new Multihash(Base58.decode(base58));
    }
}