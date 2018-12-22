package io.sunshower.common;

import java.util.Locale;

public class OS {
    public enum Type {
        Windows,
        Linux,
        Apple,
        Other
    }


    protected static Type type;

    public static Type resolve() {
        if (type == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
                type = Type.Apple
                ;
            } else if (OS.indexOf("win") >= 0) {
                type = Type.Windows;
            } else if (OS.indexOf("nux") >= 0) {
                type = Type.Linux;
            } else {
                type = Type.Other;
            }
        }
        return type;
    }

}
