package com.github.programmerr47.vkgroups.background.objects.special;

/**
 * @author Michael Spitsin
 * @since 2016-01-10
 */
public enum ClosedType {
    OPEN(0),
    CLOSED(1),
    PRIVATE(2);

    private int id;

    ClosedType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ClosedType fromId(int id) {
        for (ClosedType type : ClosedType.values()) {
            if (type.id == id) {
                return type;
            }
        }

        throw new IllegalArgumentException("There is no type for id: " + id);
    }
}
