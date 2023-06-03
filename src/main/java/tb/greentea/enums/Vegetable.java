package tb.greentea.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Vegetable {

    BROCCOLI(100),
    CARROT(101),
    TOMATO(102);

    private final int id;

    public static Vegetable of(Fruit fruit) {
        return switch (fruit) {
            case APPLE -> BROCCOLI;
            case BANANA -> CARROT;
            case ORANGE -> TOMATO;
        };
    }
}
