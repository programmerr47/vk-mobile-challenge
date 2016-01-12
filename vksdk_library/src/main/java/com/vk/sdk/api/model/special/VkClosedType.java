package com.vk.sdk.api.model.special;

/**
 * @author Michael Spitsin
 * @since 2016-01-10
 */
public enum VkClosedType {
    OPEN(0),
    CLOSED(1),
    PRIVATE(2);

    private int id;

    VkClosedType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static VkClosedType fromId(int id) {
        for (VkClosedType type : VkClosedType.values()) {
            if (type.id == id) {
                return type;
            }
        }

        throw new IllegalArgumentException("There is no type for id: " + id);
    }
}
