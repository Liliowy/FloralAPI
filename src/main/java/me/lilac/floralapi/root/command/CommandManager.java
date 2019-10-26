package me.lilac.floralapi.root.command;

import me.lilac.floralapi.root.FloralPlugin;
import me.lilac.floralapi.root.utils.LocalizedText;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    private FloralPlugin plugin;
    private String mainCommandLabel;
    private AbstractSubcommand mainCommand;
    private List<AbstractSubcommand> subcommands;

    public CommandManager(String mainCommandLabel) {
        this.plugin = FloralPlugin.getInstance();
        this.mainCommandLabel = mainCommandLabel;
        this.subcommands = new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (mainCommand != null) {
            mainCommand.onCommand(sender, args);
            return false;
        }

        if (args.length == 0) {
            displayHelpMessage(sender);
            return false;
        }

        for (AbstractSubcommand subcommand : subcommands) {
            if (!args[0].equalsIgnoreCase(subcommand.getLabel())) continue;;
            if (!sender.hasPermission(subcommand.getPermission())) {
                sender.sendMessage(new LocalizedText("no-permission").withPrefixPlaceholder().format());
                continue;
            }

            if (!subcommand.isPlayerOnly() && !(sender instanceof Player)) {
                sender.sendMessage(new LocalizedText("player-only").withPrefixPlaceholder().format());
                continue;
            }

            subcommand.onCommand(sender, truncateArgs(args));
            return false;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String cmd, String[] args) {
        List<String> tab = new ArrayList<>();

        if (mainCommand != null) return mainCommand.onTab(sender, args);

        if (args.length == 1) {
            for (AbstractSubcommand subcommand : subcommands) {
                if (!sender.hasPermission(subcommand.getPermission())) continue;
                if (!subcommand.isPlayerOnly() && !(sender instanceof Player)) continue;
                tab.add(subcommand.getLabel());
            }

            return tab;
        }

        for (AbstractSubcommand subcommand : subcommands) {
            if (!args[0].equalsIgnoreCase(subcommand.getLabel())) continue;
            if (!sender.hasPermission(subcommand.getPermission())) continue;;
            if (!subcommand.isPlayerOnly() && !(sender instanceof Player)) continue;
            return subcommand.onTab(sender, args);
        }

        return tab;
    }

    private String[] truncateArgs(String[] args) {
        String[] trueArgs = new String[args.length - 1];

        for (int i = 0; i < args.length; i++) {
            if (i == 0) continue;
            trueArgs[i - 1] = args[i];
        }

        return trueArgs;
    }

    private void displayHelpMessage(CommandSender sender) {
        sender.sendMessage(new LocalizedText(plugin.getPluginTitle()).format());
        for (AbstractSubcommand subcommand : subcommands) {
            if (!sender.hasPermission(subcommand.getPermission())) continue;
            sender.sendMessage(new LocalizedText("&d/" + mainCommandLabel + " " + subcommand.getSyntax() + " &7- ").format() +
                    new LocalizedText("command-" + subcommand.getLabel() + "-description").format());
        }
    }

    public CommandManager overrideMainCommand(AbstractSubcommand mainCommand) {
        this.mainCommand = mainCommand;
        return this;
    }

    public CommandManager addSubcommand(AbstractSubcommand subcommand) {
        this.subcommands.add(subcommand);
        return this;
    }

    public List<AbstractSubcommand> getSubcommands() {
        return subcommands;
    }

    public AbstractSubcommand getMainCommand() {
        return mainCommand;
    }

    public String getMainCommandLabel() {
        return mainCommandLabel;
    }
}
