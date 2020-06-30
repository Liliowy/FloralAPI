package me.lilac.floralapi.root.storage;

import me.lilac.floralapi.root.FloralPlugin;
import me.lilac.floralapi.root.utils.LocalizedText;

public class ConfigurationUpdater {

    private FloralPlugin plugin;
    private YMLFile file;

    public ConfigurationUpdater(FloralPlugin plugin, YMLFile file) {
        plugin = FloralPlugin.getInstance();

        String oldVerStr = plugin.getConfigFile().getString("version");
        String newVerStr = plugin.getDescription().getVersion();
        oldVerStr = oldVerStr.replace(".", "");
        newVerStr = newVerStr.replace(".", "");

        int oldVersion = Integer.parseInt(oldVerStr);
        int newVersion = Integer.parseInt(newVerStr);

        if (newVersion <= oldVersion) return;

        new LocalizedText(plugin.getPluginTitle() + " &eFound &6" + file.getConfig().getName() + " &eVersion: &6" + oldVerStr +
                "&e, but " + plugin.getPluginName() + " is Version: &6" + newVerStr +
                "&e. Updating &6" + file.getConfig().getName() + "&e.");

        updateConfig();
    }

    public void updateConfig() {
        /*
        Store old config values,
        Regenerate config,
        Place new config values in.
         */
    }
}
