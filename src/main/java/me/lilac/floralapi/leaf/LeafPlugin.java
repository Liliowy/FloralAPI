package me.lilac.floralapi.leaf;

/**
 * FloralAPI's Minigame API.
 */
public interface LeafPlugin {

    /**
     * @return The version of the Leaf API.
     */
    default String getLeafVersion() {
        return "1.0.0";
    }
}
