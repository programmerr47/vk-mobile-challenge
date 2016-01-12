package com.github.programmerr47.vkgroups.background.objects.special;

/**
 * @author Michael Spitsin
 * @since 2016-01-12
 */
public enum PlatformType {
    ANDROID("android"),
    IPHONE("iphone"),
    WPHONE("wphone");

    private String name;

    PlatformType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PlatformType fromName(String name) {
        for (PlatformType type : PlatformType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }

        throw new IllegalArgumentException("There is no type for name: " + name);
    }
}
