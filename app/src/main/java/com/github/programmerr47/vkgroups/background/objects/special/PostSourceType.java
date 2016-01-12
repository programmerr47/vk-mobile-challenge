package com.github.programmerr47.vkgroups.background.objects.special;

/**
 * @author Michael Spitsin
 * @since 2016-01-12
 */
public enum PostSourceType {
    VK("vk"),
    WIDGET("widget"),
    API("api"),
    RSS("rss"),
    SMS("sms");

    private String name;

    PostSourceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PostSourceType fromName(String name) {
        for (PostSourceType type : PostSourceType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }

        throw new IllegalArgumentException("There is no type for name: " + name);
    }
}
