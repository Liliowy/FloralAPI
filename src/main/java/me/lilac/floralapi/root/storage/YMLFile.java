package me.lilac.floralapi.root.storage;

import me.lilac.floralapi.root.FloralPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class YMLFile {

    private File file;
    private FileConfiguration config;

    public YMLFile(String name) {
        FloralPlugin plugin = FloralPlugin.getInstance();

        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
        file = new File(plugin.getDataFolder(), name + ".yml");
        if (!file.exists()) plugin.saveResource(name + ".yml", false);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void set(String path, Object value) {
        getConfig().set(path, value);
    }

    public ConfigurationSection getDefaultSection() {
        return getConfig().getDefaultSection();
    }

    public ConfigurationSection getSection(String path) {
        return getConfig().getConfigurationSection(path);
    }

    public boolean contains(String path) {
        return getConfig().contains(path);
    }

    public boolean isList(String path) {
        return getConfig().isList(path);
    }

    public String getString(String path) {
        return getConfig().getString(path);
    }

    public int getInt(String path) {
        return getConfig().getInt(path);
    }

    public boolean getBoolean(String path) {
        return getConfig().getBoolean(path);
    }

    public float getFloat(String path) {
        return (float) getConfig().getDouble(path);
    }

    public double getDouble(String path) {
        return getConfig().getDouble(path);
    }

    public long getLong(String path) {
        return getConfig().getLong(path);
    }

    public List<String> getStringList(String path) {
        return getConfig().getStringList(path);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return file;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
