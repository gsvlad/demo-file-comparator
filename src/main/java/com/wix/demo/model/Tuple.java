package com.wix.demo.model;

import java.util.Objects;

/**
 * For the purpose of this task there is no need to convert eventId and dateCreated to numbers
 */
public class Tuple implements Comparable<Tuple> {
    public final String eventId;
    public final String dateCreated;
    public final String host;

    public Tuple(String eventId, String dateCreated, String host) {
        this.eventId = eventId;
        this.dateCreated = dateCreated;
        this.host = host;
    }

    @Override
    public int compareTo(Tuple other) {
        return dateCreated.compareTo(other.dateCreated);
    }

    @Override
    public String toString() {
        return "(" + eventId + ", " + dateCreated + ", " + host + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple tuple = (Tuple) o;
        return Objects.equals(eventId, tuple.eventId) &&
                Objects.equals(dateCreated, tuple.dateCreated) &&
                Objects.equals(host, tuple.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, dateCreated, host);
    }
}
