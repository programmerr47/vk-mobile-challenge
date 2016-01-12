package com.vk.sdk.api.model.special;

/**
 * @author Michael Spitsin
 * @since 2016-01-10
 */
public enum VkGroupType {
    GROUP(0, "group"),
    PAGE(1,"page"),
    EVENT(2, "event");

    private int id;
    private String name;

    VkGroupType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static VkGroupType fromName(String name) {
        for (VkGroupType type : VkGroupType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }

        return GROUP;
    }

    public static VkGroupType fromId(int id) {
        for (VkGroupType type : VkGroupType.values()) {
            if (type.id == id) {
                return type;
            }
        }

        return GROUP;
    }
}
