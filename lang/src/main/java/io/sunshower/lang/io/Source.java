package io.sunshower.lang.io;

import java.io.IOException;


public interface Source {
    long size() throws IOException;
    char next() throws IOException;

    boolean hasNext();
}
