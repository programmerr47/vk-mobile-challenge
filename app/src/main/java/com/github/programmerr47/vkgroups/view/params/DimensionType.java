package com.github.programmerr47.vkgroups.view.params;

/**
 * @author Mihail Spitsin
 * @since 2/18/2016
 */
public enum DimensionType {
    WIDTH(0) {
        @Override
        public int getSquareDimension(int viewWidth, int viewHeight) {
            return viewWidth;
        }
    },
    HEIGHT(1) {
        @Override
        public int getSquareDimension(int viewWidth, int viewHeight) {
            return viewHeight;
        }
    },
    MIN(2) {
        @Override
        public int getSquareDimension(int viewWidth, int viewHeight) {
            if (viewWidth != 0 && viewHeight != 0) {
                return Math.min(viewWidth, viewHeight);
            } else if (viewWidth == 0) {
                return viewHeight;
            } else {
                return viewWidth;
            }
        }
    },
    MAX(3) {
        @Override
        public int getSquareDimension(int viewWidth, int viewHeight) {
            if (viewWidth != 0 && viewHeight != 0) {
                return Math.max(viewWidth, viewHeight);
            } else if (viewWidth == 0) {
                return viewHeight;
            } else {
                return viewWidth;
            }
        }
    };

    private final int id;

    DimensionType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract int getSquareDimension(int viewWidth, int viewHeight);

    public static DimensionType fromId(int id) {
        for (DimensionType dimensionType : DimensionType.values()) {
            if (dimensionType.id == id) {
                return dimensionType;
            }
        }

        throw new IllegalArgumentException("There is not type for id: " + id);
    }
}
