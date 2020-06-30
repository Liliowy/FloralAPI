package me.lilac.floralapi.root.item;

import org.bukkit.entity.Player;

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
     * @param replace The string to replace with.
     */
    public ItemPlaceholderManager addPlaceholder(String placeholder, String replace) {
        placeholders.put(placeholder, replace);
        return this;
    }

    /**
     * Adds a placeholder to the IPM.
     * @param placeholder The placeholder.
     * @param replace The int to replace with.
     */
    public ItemPlaceholderManager addPlaceholder(String placeholder, int replace) {
        return addPlaceholder(placeholder, replace + "");
    }

    /**
     * Adds a placeholder to the IPM.
     * @param placeholder The placeholder.
     * @param replace The double to replace with.
     */
    public ItemPlaceholderManager addPlaceholder(String placeholder, double replace) {
        return addPlaceholder(placeholder, replace + "");
    }

    /**
     * Adds a placeholder to the IPM.
     * @param placeholder The placeholder.
     * @param replace The float to replace with.
     */
    public ItemPlaceholderManager addPlaceholder(String placeholder, float replace) {
        return addPlaceholder(placeholder, replace + "");
    }

    /**
     * Adds a placeholder to the IPM.
     * @param placeholder The placeholder.
     * @param replace The long to replace with.
     */
    public ItemPlaceholderManager addPlaceholder(String placeholder, long replace) {
        return addPlaceholder(placeholder, replace + "");
    }

    /**
     * @return All the placeholders in this IPM.
     */
    public Map<String, String> getPlaceholders() {
        return placeholders;
    }
}