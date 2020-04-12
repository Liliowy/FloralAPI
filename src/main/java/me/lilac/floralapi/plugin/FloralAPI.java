package me.lilac.floralapi.plugin;

import me.lilac.floralapi.root.FloralPlugin;
import me.lilac.floralapi.root.command.CommandManager;

public class FloralAPI extends FloralPlugin {

    @Override
    public void onStartUp() {
        UNPAID_MODE = true; // REMOVE IF CUSTOMER HAS PAID FOR PLUGIN.
        new CommandManager("floralapi", "/floralapi help").addSubcommand(new CommandFloral());
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
