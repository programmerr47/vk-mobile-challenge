package com.github.programmerr47.vkgroups.background.objects.special;

/**
 * @author Michael Spitsin
 * @since 2016-01-10
 */
public enum AdminLevel {
    NO(0),
    MODERATOR(1),
    EDITOR(2),
    ADMIN(3);

    private int id;

    AdminLevel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static AdminLevel fromId(int id) {
        for (AdminLevel type : AdminLevel.values()) {
            if (type.id == id) {
                return type;
            }
        }

        throw new IllegalArgumentException("There is no level for id: " + id);
    }
}
