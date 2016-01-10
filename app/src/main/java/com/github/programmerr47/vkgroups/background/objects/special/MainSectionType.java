package com.github.programmerr47.vkgroups.background.objects.special;

/**
 * @author Michael Spitsin
 * @since 2016-01-10
 */
public enum MainSectionType {
    NO(0),
    PHOTO(1),
    DISCUSS(2),
    AUDIO(3),
    VIDEO(4),
    MARKET(5);

    private int id;

    MainSectionType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MainSectionType fromId(int id) {
        for (MainSectionType type : MainSectionType.values()) {
            if (type.id == id) {
                return type;
            }
        }

        throw new IllegalArgumentException("There is no type for id: " + id);
    }
}
