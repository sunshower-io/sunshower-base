package io.sunshower.common.io;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


@RunWith(JUnitPlatform.class)
public class BytesTest {

    @Test
    public void ensureBytesCanReadFromInputStream() throws IOException {
        final ByteArrayInputStream bos = new ByteArrayInputStream("hello world!".getBytes());
        final byte[] data = Bytes.read(bos);
        assertThat(new String(data), is("hello world!"));

    }

}