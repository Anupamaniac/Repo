package com.rosettastone.succor.model.bandit;

/**
 *  The {@code Range} represents an object for ranging of product rights.
 */

public class Range {
    private final int low;
    private final int hi;

    public Range(int low, int hi) {
        this.low = low;
        this.hi = hi;
        if (low > hi) {
            throw new IllegalArgumentException();
        }
    }

    public String toString() {
        return low + "-" + hi;
    }

    public int getLow() {
        return low;
    }

    public int getHi() {
        return hi;
    }

    public boolean isEmpty() {
        return ((hi == low)  && (hi == 0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Range range = (Range) o;

        if (hi != range.hi) return false;
        if (low != range.low) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = low;
        result = 31 * result + hi;
        return result;
    }
}
