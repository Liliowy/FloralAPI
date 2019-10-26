package me.lilac.floralapi.root;

import me.lilac.floralapi.root.storage.YMLFile;
import me.lilac.floralapi.root.utils.LocalizedText;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class FloralPlugin extends JavaPlugin implements RootPlugin {

    private static FloralPlugin instance;
    private YMLFile configFile;
    private YMLFile languageFile;
    private boolean hasPlaceholderAPI;
    private boolean hasVault;
    private Economy economy;

    // Called when the plugin starts.
    public abstract void onStartUp();

    // Called when the plugin stops.
    public abstract void onShutDown();

    // Called when the reload command is used.
    public abstract void onReload();

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getConsoleSender().sendMessage(new LocalizedText(getPluginTitle() + " &fimplementing " + getAPIs()).format());
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) hasPlaceholderAPI = true;
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) hasVault = true;
        economy = setupEconomy();

        onStartUp();
        reload();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(new LocalizedText(getPluginTitle() + " &fGoodbye. :waves:").format());
        onShutDown();
    }

    public void reload() {
        configFile = new YMLFile("config");
        languageFile = new YMLFile("language");
        onReload();
    }

    // Returns the name of the plugin.
    public String getPluginName() {
        return getDescription().getName();
    }

    private Economy setupEconomy() {
        if (!hasVault) return null;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return null;
        return rsp.getProvider();
    }

    // Returns a friendly name, usually with brackets.
    public abstract String getPluginTitle();

    // Returns all the APIs this plugin is using.
    public abstract String getAPIs();

    // Returns the configuration file.
    public YMLFile getConfigFile() {
        return configFile;
    }

    // Returns the language file.
    public YMLFile getLanguageFile() {
        return languageFile;
    }

    // Does the server have PlaceholderAPI installed?
    public boolean hasPlaceholderAPI() {
        return hasPlaceholderAPI;
    }

    // Does the server have vault installed?
    public boolean hasVault() {
        return hasVault;
    }

    public Economy getEconomy() {
        return economy;
    }

    public static FloralPlugin getInstance() {
        return instance;
    }
}
