package io.sunshower.common.io;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by haswell on 3/20/17.
 */
public class BytesTest {

    @Test
    public void ensureBytesCanReadFromInputStream() throws IOException {
        final ByteArrayInputStream bos = new ByteArrayInputStream("hello world!".getBytes());
        final byte[] data = Bytes.read(bos);
        assertThat(new String(data), is("hello world!"));

    }

}