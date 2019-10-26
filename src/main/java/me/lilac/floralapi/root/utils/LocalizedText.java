package me.lilac.floralapi.root.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.lilac.floralapi.root.FloralPlugin;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A nice way to get formatted text and text from a language file.
public class LocalizedText {

    private FloralPlugin plugin;
    private String text;

    public LocalizedText(String text) {
        plugin = FloralPlugin.getInstance();

        if (plugin.getLanguageFile().contains(text)) this.text = plugin.getLanguageFile().getString(text);
        else this.text = text;
    }

    public LocalizedText(List<String> array) {
        for (int i = 0; i < array.size(); i++) {
            if (i != 0) this.text += "\n" + array.get(i);
            else this.text += array.get(i);
        }
    }

    // Sets placeholder API placeholders in the text.
    public LocalizedText withPlaceholderAPI(OfflinePlayer player) {
        if (!FloralPlugin.getInstance().hasPlaceholderAPI()) text = PlaceholderAPI.setPlaceholders(player, text);
        return this;
    }

    // Sets a local placeholder in the text.
    public LocalizedText withPlaceholder(String placeholder, String replace) {
        text = text.replace("%" + placeholder + "%", replace);
        return this;
    }

    // Sets the prefix placeholder.
    public LocalizedText withPrefixPlaceholder() {
        text = text.replace("%prefix%", FloralPlugin.getInstance().getLanguageFile().getString("prefix"));
        return this;
    }

    // Adds the prefix to the text.
    public LocalizedText withPrefix() {
        text = "%prefix% " + text;
        return withPrefixPlaceholder();
    }

    // Returns the text, formatted.
    public String format() {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    // Returns the formatted text in an array, splitting at '\n'.
    public List<String> toArray() {
        return new ArrayList<>(Arrays.asList(format().split("\n")));
    }
}
