package me.lilac.floralapi.stem;

/**
 * FloralAPI's NMS API.
 */
public interface StemPlugin {

    /**
     * Gets the version of the Stem API.
     * @return The version of the Stem API.
     */
    default String getStemVersion() {
        return "1.0.0";
    }
}
