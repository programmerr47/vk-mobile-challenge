package com.github.programmerr47.vkgroups.background.objects.special;

/**
 * @author Michael Spitsin
 * @since 2016-01-10
 */
public enum MemberStatus {
    NOT_MEMBER(0),
    MEMBER(1),
    NOT_SURE(2),
    REFUSE(3),
    APPLY(4),
    INVITED(5);

    private int id;

    MemberStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MemberStatus fromId(int id) {
        for (MemberStatus type : MemberStatus.values()) {
            if (type.id == id) {
                return type;
            }
        }

        throw new IllegalArgumentException("There is no status for id: " + id);
    }
}
