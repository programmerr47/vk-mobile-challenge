package com.github.programmerr47.vkgroups.background.objects.special;

/**
 * @author Michael Spitsin
 * @since 2016-01-12
 */
public enum AttachmentType {
    PHOTO("photo"),
    POSTED_PHOTO("posted_photo"),
    VIDEO("video"),
    AUDIO("audio"),
    DOC("doc"),
    GRAFFITI("graffiti"),
    LINK("link"),
    NOTE("note"),
    APP("app"),
    PAGE("page"),
    POLL("poll"),
    ALBUM("album"),
    PHOTO_LIST("photo_list");

    private String name;

    AttachmentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static AttachmentType fromName(String name) {
        for (AttachmentType type : AttachmentType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }

        throw new IllegalArgumentException("There is no type for name: " + name);
    }
}
