package com.wix.demo.model;

import java.util.List;

/**
 * Object representation of line. Contains uuid and list of tuples
 */
public class ParsedLine implements Comparable<ParsedLine> {
    public final String uuid;
    public final List<Tuple> tuples;

    public ParsedLine(String uuid, List<Tuple> tuples) {
        this.uuid = uuid;
        this.tuples = tuples;
    }

    @Override
    public int compareTo(ParsedLine another) {
        return uuid.compareTo(another.uuid);
    }
}
