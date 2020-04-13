package me.lilac.floralapi.plugin;

import me.lilac.floralapi.root.FloralPlugin;
import me.lilac.floralapi.root.command.CommandManager;
import me.lilac.floralapi.root.command.CommandReload;

/**
 * Testing & Example Plugin
 */
public class FloralAPI extends FloralPlugin {

    @Override
    public void onStartUp() {
        new CommandManager("floralapi", "/floralapi help")
                .addSubcommand(new CommandFloral())
                .addSubcommand(new CommandReload());
    }

    @Override
    public void onShutDown() {

    }

    @Override
    public void onReload() {
        setupGUIManager().registerGUI(new GuiFloral());
    }

    @Override
    public String getPluginName() {
        return null;
    }
}
