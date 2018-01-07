package io.sunshower.io;


import java.io.*;
import java.nio.file.FileVisitOption;
import java.nio.file.Path;
import java.util.Comparator;

/**
 * Created by haswell on 5/22/17.
 */
public class Files {

    public static void delete(File path) throws IOException {
        Path p = path.toPath();
        java.nio.file.Files.walk(p, FileVisitOption.FOLLOW_LINKS)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    public static byte[] read(File write) throws IOException {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream(4092);
        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(write))
        ) {
            copy(bis, bos, 4092);
        }
        return bos.toByteArray();
    }

    public static void copy(InputStream input,
                            OutputStream output
    ) throws IOException {
        copy(input, output, 4092);
    }

    public static void copy(InputStream input,
                            OutputStream output,
                            int bufferSize
    ) throws IOException {
        byte[] buf = new byte[bufferSize];
        int bytesRead = input.read(buf);
        while (bytesRead != -1) {
            output.write(buf, 0, bytesRead);
            bytesRead = input.read(buf);
        }
        output.flush();
    }

    public static void copy(
            Reader reader,
            BufferedWriter writer
    ) throws IOException {
        char[] buf = new char[4092];
        int bytesRead = reader.read(buf);
        while (bytesRead != -1) {
            writer.write(buf, 0, bytesRead);
            bytesRead = reader.read(buf);
        }
        writer.flush();
    }

    public static byte[] read(InputStream read) throws IOException {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        copy(read, bos, 1024);
        return bos.toByteArray();
    }
}
