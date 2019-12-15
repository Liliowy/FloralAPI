package me.lilac.floralapi.root.command;

import me.lilac.floralapi.root.FloralPlugin;
import me.lilac.floralapi.root.utils.LocalizedText;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for managing commands and subcommands.
 */
public class CommandManager implements CommandExecutor, TabCompleter {

    /**
     * An instance of the main class.
     */
    private FloralPlugin plugin;

    /**
     * The main command label.
     */
    private String mainCommandLabel;

    /**
     * The main command syntax.
     */
    private String mainSyntax;

    /**
     * The main command.
     * Optional. Used for overriding the main help command.
     */
    private AbstractSubcommand mainCommand;

    /**
     * A list of subcommands used by this command.
     */
    private List<AbstractSubcommand> subcommands;

    /**
     * Creates a new instance of the CommandManager.
     * This creates a new command.
     * @param mainCommandLabel The main command that the player will use. E.g. '/command'.
     * @param mainSyntax The main syntax that the player will see. E.g. '/command <reload|help>'.
     */
    public CommandManager(String mainCommandLabel, String mainSyntax) {
        this.plugin = FloralPlugin.getInstance();
        this.mainCommandLabel = mainCommandLabel;
        this.subcommands = new ArrayList<>();
        this.mainSyntax = mainSyntax;
        plugin.getCommand(mainCommandLabel).setExecutor(this);
        plugin.getCommand(mainCommandLabel).setTabCompleter(this);
    }

    /**
     * Creates a new instance of the CommandManager.
     * This creates a new command.
     * @param mainCommand The main command to run. This is used instead of the default help command.
     */
    public CommandManager(AbstractSubcommand mainCommand) {
        this(mainCommand.getLabel(), mainCommand.getSyntax());
        this.mainCommand = mainCommand;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (mainCommand != null) {
            mainCommand.onCommand(sender, args);
            return false;
        }

        if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))
                || (args.length == 1 && args[0].equalsIgnoreCase("?"))) {
            displayHelpMessage(sender);
            return false;
        }

        for (AbstractSubcommand subcommand : subcommands) {
            if (!args[0].equalsIgnoreCase(subcommand.getLabel())) continue;
            if (subcommand.getPermission() != null && !sender.hasPermission(subcommand.getPermission())) {
                sender.sendMessage(new LocalizedText("no-permission").withPrefixPlaceholder().format());
                continue;
            }

            if (subcommand.isPlayerOnly() && !(sender instanceof Player)) {
                sender.sendMessage(new LocalizedText("player-only").withPrefixPlaceholder().format());
                continue;
            }

            subcommand.onCommand(sender, truncateArgs(args));
            return false;
        }

        sender.sendMessage(new LocalizedText("invalid-arguments").withPrefixPlaceholder()
                .withPlaceholder("syntax", mainSyntax).format());

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String cmd, String[] args) {
        List<String> tab = new ArrayList<>();

        if (mainCommand != null) return mainCommand.onTab(sender, args);

        if (args.length == 1) {
            for (AbstractSubcommand subcommand : subcommands) {
                if (subcommand.getPermission() != null && !sender.hasPermission(subcommand.getPermission())) continue;
                if (!subcommand.isPlayerOnly() && !(sender instanceof Player)) continue;
                if (subcommand.getLabel().contains(args[0])) tab.add(subcommand.getLabel());
            }

            return tab;
        }

        for (AbstractSubcommand subcommand : subcommands) {
            if (!args[0].equalsIgnoreCase(subcommand.getLabel())) continue;
            if (!sender.hasPermission(subcommand.getPermission())) continue;;
            if (subcommand.getPermission() != null && !subcommand.isPlayerOnly() && !(sender instanceof Player)) continue;
            tab = subcommand.onTab(sender, truncateArgs(args));

            if (tab == null) return new ArrayList<>();

            if (tab.contains("players")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getName().contains(args[0])) tab.add(player.getName());
                }
                tab.remove("players");
            }

            return tab;
        }

        return tab;
    }

    /**
     * Decreases the arguments for use in subcommands.
     * @param args The arguments sent with the command.
     * @return The arguments sent with the command, aside from the first one.
     */
    private String[] truncateArgs(String[] args) {
        String[] trueArgs = new String[args.length - 1];

        for (int i = 0; i < args.length; i++) {
            if (i == 0) continue;
            trueArgs[i - 1] = args[i];
        }

        return trueArgs;
    }

    /**
     * Sends a default help message to the sender.
     * @param sender The player or console who sent the command.
     */
    private void displayHelpMessage(CommandSender sender) {
        sender.sendMessage(new LocalizedText("prefix").format());
        for (AbstractSubcommand subcommand : subcommands) {
            if (subcommand.getPermission() != null && !sender.hasPermission(subcommand.getPermission())) continue;
            sender.sendMessage(new LocalizedText("&d/" + mainCommandLabel + " " + subcommand.getSyntax() + " &7- ").format() +
                    new LocalizedText("command-" + subcommand.getLabel() + "-description").format());
        }
    }

    /**
     * Adds a new subcommand to this command.
     * @param subcommand The subcommand to add.
     * @return An instance of this class.
     */
    public CommandManager addSubcommand(AbstractSubcommand subcommand) {
        this.subcommands.add(subcommand);
        return this;
    }

    /**
     * Gets the subcommands for this command.
     * @return A list of subcommands for this command.
     */
    public List<AbstractSubcommand> getSubcommands() {
        return subcommands;
    }

    /**
     * Gets the overriden main command.
     * @return The overriden main command.
     */
    public AbstractSubcommand getMainCommand() {
        return mainCommand;
    }

    /**
     * Gets the main command label.
     * @return The main command label.
     */
    public String getMainCommandLabel() {
        return mainCommandLabel;
    }

    /**
     * Gets the main command syntax.
     * @return The main command syntax.
     */
    public String getMainSyntax() {
        return mainSyntax;
    }
}

