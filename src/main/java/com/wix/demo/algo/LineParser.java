package com.wix.demo.algo;

import com.wix.demo.model.ParsedLine;
import com.wix.demo.model.Tuple;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public final class LineParser {

    private final static int UUID_LENGTH = 36;

    /**
     * @return {@link ParsedLine} or null if line malformed
     */
    public static ParsedLine parseLine(String line) {
        String[] parts = line.split("\t");

        if (parts.length < 1 || parts.length > 2) {
            return null;
        }

        String uuid = parts[0].trim();
        if (uuid.length() != UUID_LENGTH) {
            return null;
        }

        String tuples = parts.length == 2 ? parts[1] : "";

        return new ParsedLine(uuid, parseTuples(tuples));
    }

    private static List<Tuple> parseTuples(String string) {
        String[] parts = string.trim().split("[()]");
        return Arrays.stream(parts)
                .filter(it -> !it.trim().isEmpty())
                .map(LineParser::parseTuple)
                .filter(Objects::nonNull) // skip malformed tuples
                .collect(toList());
    }

    private static Tuple parseTuple(String string) {
        String[] parts = string.trim().split(" ");

        if (parts.length != 3) {
            // maybe we have empty values
            parts = Arrays.stream(parts)
                    .filter(it -> !it.isEmpty())
                    .toArray(String[]::new);

            if (parts.length != 3) {
                return null;
            }
        }
        return new Tuple(parts[0], parts[1], parts[2]);
    }
}
