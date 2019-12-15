package me.lilac.floralapi.root.item;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for managing placeholders in ItemStacks.
 */
public class ItemPlaceholderManager {

    /**
     * The placeholders.
     */
    private Map<String, String> placeholders;

    /**
     * Creates a new ItemPlaceholderManager.
     */
    public ItemPlaceholderManager() {
        this.placeholders = new HashMap<>();
    }

    /**
     * Adds a placeholder to the IPM.
     * @param placeholder The placeholder.
     * @param replacer The string to replace with.
     */
    public void addPlaceholders(String placeholder, String replacer) {
        placeholders.put(placeholder, replacer);
    }

    /**
     * @return All the placeholders in this IPM.
     */
    public Map<String, String> getPlaceholders() {
        return placeholders;
    }
}