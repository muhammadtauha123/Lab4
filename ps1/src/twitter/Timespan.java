package twitter;

import java.time.Instant;

/**
 * Immutable datatype representing an interval starting from one date/time and
 * ending at a no earlier date/time. The interval includes its endpoints.
 * 
 * DO NOT CHANGE THIS CLASS.
 */
public class Timespan {

    private final Instant start;
    private final Instant end;

    /* Rep invariant: start <= end. */

    /**
     * Make a Timespan.
     * 
     * @param start
     *            starting date/time
     * @param end
     *            ending date/time. Requires end >= start.
     */
    public Timespan(Instant start, Instant end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("requires start <= end");
        }
        this.start = start;
        this.end = end;
    }

    /**
     * @return the starting point of the interval
     */
    public Instant getStart() {
        return start;
    }

    /**
     * @return the ending point of the interval
     */
    public Instant getEnd() {
        return end;
    }

    /**
     * Check if a given Instant is within the timespan.
     *
     * @param timestamp the Instant to check
     * @return true if the timestamp is within the timespan, false otherwise
     */
    public boolean contains(Instant timestamp) {
        return !(timestamp.isBefore(start) || timestamp.isAfter(end));
    }

    /*
     * @see Object.toString()
     */
    @Override
    public String toString() {
        return "[" + this.getStart() + "..." + this.getEnd() + "]";
    }

    /*
     * @see Object.equals()
     */
    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Timespan)) {
            return false;
        }

        Timespan that = (Timespan) thatObject;
        return this.start.equals(that.start) && this.end.equals(that.end);
    }

    /*
     * @see Object.hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + start.hashCode();
        result = prime * result + end.hashCode();
        return result;
    }
}
