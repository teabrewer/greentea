package tb.greentea.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Fruit {

    APPLE(100),
    BANANA(101),
    ORANGE(102);

    private final int fruitId;
}
