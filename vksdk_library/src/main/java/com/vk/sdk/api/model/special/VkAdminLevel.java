package com.vk.sdk.api.model.special;

/**
 * @author Michael Spitsin
 * @since 2016-01-10
 */
public enum VkAdminLevel {
    NO(0),
    MODERATOR(1),
    EDITOR(2),
    ADMIN(3);

    private int id;

    VkAdminLevel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static VkAdminLevel fromId(int id) {
        for (VkAdminLevel type : VkAdminLevel.values()) {
            if (type.id == id) {
                return type;
            }
        }

        throw new IllegalArgumentException("There is no level for id: " + id);
    }
}
