package com.github.programmerr47.vkgroups.background.objects.special;

/**
 * @author Michael Spitsin
 * @since 2016-01-12
 */
public enum PostType {
    POST("post"),
    COPY("copy"),
    REPLY("reply"),
    POSTPONE("postpone"),
    SUGGEST("suggest");

    private String name;

    PostType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PostType fromName(String name) {
        for (PostType type : PostType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }

        throw new IllegalArgumentException("There is no type for name: " + name);
    }
}
