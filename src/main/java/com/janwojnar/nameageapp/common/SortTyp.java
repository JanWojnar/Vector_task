package com.janwojnar.nameageapp.common;

/**
 * Enums containing signs which means parameter to sort.
 */
public enum SortTyp {

    /**
     * Sorting by age.
     */
    AGE('a'),
    /**
     * Sorting by name.
     */
    NAME('n');

    private final char typ;

    SortTyp(Character c) {
        this.typ = c;
    }

    /**
     * Creates enum from given char or throws Exception.
     *
     * @param c given char.
     * @return parsed enumeration..
     * @throws IllegalAccessException ..or throws Exception.
     */
    public static SortTyp of(Character c) throws IllegalAccessException {
        if (c != null) {
            c = Character.toLowerCase(c);
            for (SortTyp sortTyp : values()) {
                if (sortTyp.typ == c) {
                    return sortTyp;
                }
            }
        }
        throw new IllegalAccessException("SortTyp was not recognized!");
    }
}
