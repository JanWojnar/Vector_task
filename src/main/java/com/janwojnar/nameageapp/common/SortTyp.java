package com.janwojnar.nameageapp.common;

public enum SortTyp {
    AGE('a'),
    NAME('n');

    private final char typ;

    SortTyp(Character c) {
        this.typ = c;
    }

    public static SortTyp of(Character c) throws IllegalAccessException {
        c = Character.toLowerCase(c);
        for (SortTyp sortTyp : values()) {
            if (sortTyp.typ == c) {
                return sortTyp;
            }
        }
        throw new IllegalAccessException("SortTyp was not recognized!");
    }
}
