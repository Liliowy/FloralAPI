package me.lilac.floralapi.root;

import me.lilac.floralapi.petal.gui.GUIManager;
import me.lilac.floralapi.root.storage.SQLDatabase;
import me.lilac.floralapi.root.storage.YMLFile;
import me.lilac.floralapi.root.utils.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for FloralAPI.
 * Every plugin utilising FloralAPI should extend this class.
 * It's nice to implement each module, but not necessary.
 */
public abstract class FloralPlugin extends JavaPlugin implements RootPlugin {

    public static boolean UNPAID_MODE = false;

    /**
     * An instance of this class.
     */
    private static FloralPlugin instance;

    /**
     * The files that should be used by every floral plugin.
     */
    private YMLFile configFile, languageFile;

    /**
     * An instance of the Economy class.
     */
    private Economy economy;

    /**
     * An instance of the SQLDatabase class.
     */
    private SQLDatabase sqlDatabase;

    /**
     * An instance of the GUIManager class.
     */
    private GUIManager guiManager;

    /**
     * Whether or not the server has PlaceholderAPI installed.
     */
    private boolean hasPlaceholderAPI;

    /**
     * Called when the plugin starts up.
     */
    public abstract void onStartUp();

    /**
     * Called when the plugin shuts down.
     */
    public abstract void onShutDown();

    /**
     * Called when the plugin is reloaded via the reload subcommand.
     */
    public abstract void onReload();

    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        instance = this;

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) hasPlaceholderAPI = true;

        reload();
        onStartUp();
    }

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        onShutDown();
    }

    /**
     * Typically reloads the plugin files.
     * Called when the plugin is reloaded via the reload subcommand.
     */
    public void reload() {
        configFile = new YMLFile("config");
        languageFile = new YMLFile("language");
        onReload();
    }

    /**
     * Sets up the Economy class. Allows VaultAPI to be used.
     * Should be called onStartUp.
     * @return An instance of the Economy class.
     */
    public Economy setupEconomy() {
        return economy = new Economy();
    }

    /**
     * Sets up the SQLDatabase class. Allows SQL to be used.
     * Should be called onStartUp.
     * @return An instance of the SQLDatabase class.
     */
    public SQLDatabase setupDatabase() {
        return sqlDatabase = new SQLDatabase();
    }

    /**
     * Sets up the GUIManager class. Allows GUIs to be used.
     * Should be called onReload if GUI has configurable options.
     * @return An instance of the GUIManager class.
     */
    public GUIManager setupGUIManager() {
        return guiManager = new GUIManager();
    }

    /**
     * Gets the title of the plugin directly from the plugin.yml file.
     * @return The title of the plugin.
     */
    public String getPluginTitle() {
        return getDescription().getName();
    }

    /**
     * Gets the display name of the plugin. Typically used for internal prefixes.
     * @return The display name of the plugin.
     */
    public abstract String getPluginName();

    /**
     * Gets whether or not the server has the PlaceholderAPI plugin installed.
     * @return True if the server has PlaceholderAPI installed.
     */
    public boolean hasPlaceholderAPI() {
        return hasPlaceholderAPI;
    }

    /**
     * Gets an instance of this class.
     * @return An instance of this class.
     */
    public static FloralPlugin getInstance() {
        return instance;
    }

    /**
     * Gets the general configuration file.
     * @return An instance of the general configuration file (config.yml).
     */
    public YMLFile getConfigFile() {
        return configFile;
    }

    /**
     * Gets the language file.
     * @return An instance of the language file (language.yml).
     */
    public YMLFile getLanguageFile() {
        return languageFile;
    }

    /**
     * Gets the Economy.
     * @return An instance of the Economy class. Needed to use Vault Economy.
     */
    public Economy getEconomy() {
        return economy;
    }

    /**
     * Gets the SQLDatabase.
     * @return An instance of the SQLDatabase class. Needed to use SQL.
     */
    public SQLDatabase getSqlDatabase() {
        return sqlDatabase;
    }

    /**
     * Gets the GUIManager.
     * @return An instance of the GUIManager class. Needed to use GUIs.
     */
    public GUIManager getGuiManager() {
        return guiManager;
    }
}