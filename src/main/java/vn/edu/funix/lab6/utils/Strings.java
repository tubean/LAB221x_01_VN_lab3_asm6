package vn.edu.funix.lab6.utils;

import org.springframework.util.StringUtils;

import java.util.Optional;

public final class Strings extends StringUtils {
    public static final String EMPTY = "";

    /**
     * check null pointer for string
     * @param value string
     * @return empty if string is null, otherwise return string
     */
    public static String nvl(final String value) {
        return Optional.ofNullable(value).orElse(EMPTY);
    }

}
