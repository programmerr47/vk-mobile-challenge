package com.github.programmerr47.vkgroups.background.objects.special;

/**
 * @author Michael Spitsin
 * @since 2016-01-10
 */
public enum GroupType {
    GROUP("group"),
    PAGE("page"),
    EVENT("event");

    private String name;

    GroupType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static GroupType fromName(String name) {
        for (GroupType type : GroupType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }

        throw new IllegalArgumentException("There is no type for name: " + name);
    }
}
