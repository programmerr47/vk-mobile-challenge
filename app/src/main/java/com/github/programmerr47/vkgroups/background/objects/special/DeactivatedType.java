package com.github.programmerr47.vkgroups.background.objects.special;

/**
 * @author Michael Spitsin
 * @since 2016-01-10
 */
public enum DeactivatedType {
    DELETED("deleted"),
    BANNED("banned"),
    NO(null);

    private String id;

    DeactivatedType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static DeactivatedType fromId(String id) {
        for (DeactivatedType type : DeactivatedType.values()) {
            if ((type.id == null && id == null) ||
                    (type.id != null && type.id.equals(id))) {
                return type;
            }
        }

        return NO;
    }
}
