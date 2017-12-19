package io.sunshower.common.io;

import java.io.*;

/**
 * Created by haswell on 3/20/17.
 */
public class Bytes {

    public static byte[] read(File file) throws IOException {
        return read(new BufferedInputStream(new FileInputStream(file)));
    }



    public static byte[] read(InputStream inputStream) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream(8192);
        final byte[] data = new byte[1024];
        int read;
        try(InputStream is = inputStream) {
            while ((read = inputStream.read(data)) != -1) {
                output.write(data, 0, read);
            }
        }
        return output.toByteArray();
    }
}
