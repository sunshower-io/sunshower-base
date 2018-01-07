package io.sunshower.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * Created by haswell on 5/24/17.
 */
public class ReaderInputStream extends InputStream {

    private final Reader reader;

    public ReaderInputStream(final Reader reader) {
        this.reader = reader;
    }

    @Override
    public int read() throws IOException {
        return reader.read();
    }


}
