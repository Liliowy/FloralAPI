package me.lilac.floralapi.root.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.lilac.floralapi.petal.chat.ChatMessage;
import me.lilac.floralapi.root.FloralPlugin;
import me.lilac.floralapi.root.item.ItemPlaceholderManager;
import me.lilac.floralapi.root.storage.YMLFile;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A friendly way to get localized and formatted text.
 */
public class LocalizedText {

    /**
     * An instance of the main class.
     */
    private FloralPlugin plugin;

    /**
     * The text to be localized and formatted.
     */
    private String text;

    /**
     * Creates a new LocalizedText object with the given parameter.
     * @param text The text to be localized and formatted.
     */
    public LocalizedText(String text) {
        plugin = FloralPlugin.getInstance();
        if (plugin.getLanguageFile().contains(text) && plugin.getLanguageFile().isString(text)) this.text = plugin.getLanguageFile().getString(text);
        else this.text = text;
    }

    /**
     * Creates a new LocalizedText object with the given parameter.
     * @param text The text to be localized and formatted.
     * @param file The file to read to find the text.
     */
    public LocalizedText(String text, YMLFile file) {
        if (file.contains(text) && file.isString(text)) this.text = file.getString(text);
        else this.text = text;
    }

    /**
     * Creates a new LocalizedText object from an list.
     * @param array The string list to be localized and formatted.
     */
    public LocalizedText(List<String> array) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < array.size(); i++) {
            if (i != 0) builder.append("\n").append(array.get(i));
            else builder.append(array.get(i));
        }

        this.text = builder.toString();
    }

    /**
     * Creates a new LocalizedText object from a string list in the language file.
     * @return An instance of this class.
     */
    public LocalizedText fromArray() {
        List<String> array = plugin.getLanguageFile().getStringList(text);
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < array.size(); i++) {
            if (i != 0) builder.append("\n").append(array.get(i));
            else builder.append(array.get(i));
        }

        this.text = builder.toString();

        return this;
    }

    /**
     * Creates a new LocalizedText object from a string list in the language file.
     * @param file The file to read to find the text.
     * @return An instance of this class.
     */
    public LocalizedText fromArray(YMLFile file) {
        List<String> array = file.getStringList(text);
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < array.size(); i++) {
            if (i != 0) builder.append("\n").append(array.get(i));
            else builder.append(array.get(i));
        }

        this.text = builder.toString();

        return this;
    }

    /**
     * Sets PlaceholderAPI placeholders in this text.
     * @param player The player for the placeholders.
     * @return An instance of this class.
     */
    public LocalizedText withPlaceholderAPI(OfflinePlayer player) {
        if (!plugin.hasPlaceholderAPI()) text = PlaceholderAPI.setPlaceholders(player, text);
        return this;
    }

    /**
     * Sets a local placeholder in this text.
     * @param placeholder The placeholder to replace, without the %.
     * @param replaceWith The string to replace the placeholder.
     * @return An instance of this class.
     */
    public LocalizedText withPlaceholder(String placeholder, String replaceWith) {
        text = text.replace("%" + placeholder + "%", replaceWith);
        return this;
    }

    /**
     * Sets the %prefix% placeholder.
     * @return An instance of this class.
     */
    public LocalizedText withPrefixPlaceholder() {
        text = text.replace("%prefix%", plugin.getLanguageFile().getString("prefix"));
        return this;
    }

    /**
     * Adds the plugin prefix to the text.
     * @return An instance of this class.
     */
    public LocalizedText withPrefix() {
        text = "%prefix% " + text;
        return withPrefixPlaceholder();
    }


    public LocalizedText withIPM(ItemPlaceholderManager ipm) {
        if (ipm == null || ipm.getPlaceholders().isEmpty()) return this;
        for (String placeholder : ipm.getPlaceholders().keySet())
            withPlaceholder(placeholder, ipm.getPlaceholders().get(placeholder));
        return this;
    }

    /**
     * Formats the text with colour and formatting codes.
     * @return The text formatted with colour codes.
     */
    public String format() {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Converts a string to be config writable.
     * @param text The text to convert.
     * @return The config ready string.
     */
    public static String serialize(String text) {
        return text.replace(ChatColor.COLOR_CHAR, '&');
    }

    /**
     * Converts a list to be config writable.
     * @param list The list to convert.
     * @return The config ready list.
     */
    public static List<String> serialize(List<String> list) {
        List<String> serialized = new ArrayList<>();
        for (String string : list) serialized.add(serialize(string));
        return serialized;
    }

    /**
     * Converts the text to a list.
     * @return The converted text as a list.
     */
    public List<String> toArray() {
        return new ArrayList<>(Arrays.asList(format().split("\n")));
    }

    /**
     * Converts the text to a ChatMessage, ready for hover and click events.
     * @return The converted text as a ChatMessage.
     */
    public ChatMessage toChatMessage() {
        return new ChatMessage(text);
    }

    @Override
    public String toString() {
        return text;
    }
}