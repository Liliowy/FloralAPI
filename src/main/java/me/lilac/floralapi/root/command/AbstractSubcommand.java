package me.lilac.floralapi.root.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class AbstractSubcommand {

    private String label;
    private boolean playerOnly;

    public AbstractSubcommand(String label) {
        this.label = label;
    }

    public AbstractSubcommand(String label, boolean playerOnly) {
        this.label = label;
        this.playerOnly = playerOnly;
    }

    public String getLabel() {
        return label;
    }

    public boolean isPlayerOnly() {
        return playerOnly;
    }

    public abstract void onCommand(CommandSender sender, String[] args);
    public abstract List<String> onTab(CommandSender sender, String[] args);
    public abstract String getPermission();
    public abstract String getSyntax();

}
