package me.lilac.floralapi.root.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A subcommand to be used within a main command.
 */
public abstract class AbstractCommand {

    /**
     * The label for the subcommand.
     */
    private List<String> labels;

    /**
     * Is the subcommand player only, or can the console use it?
     */
    private boolean playerOnly;

    /**
     * Creates a new subcommand with the given label.
     * @param labels The names of the subcommand. E.g. /command <label>. Multiple for aliases.
     */
    public AbstractCommand(String... labels) {
        this.labels = new ArrayList<>(Arrays.asList(labels));
    }

    /**
     * Creates a new subcommand.
     * @param labels The names of the subcommand. E.g. /command <label>. Multiple for aliases.
     * @param playerOnly Whether or not this command can be used by only players, and not the console.
     */
    public AbstractCommand(boolean playerOnly, String... labels) {
        this.labels = new ArrayList<>(Arrays.asList(labels));
        this.playerOnly = playerOnly;
    }

    /**
     * Gets the command label.
     * @return The command label.
     */
    public List<String> getLabels() {
        return labels;
    }

    /**
     * Gets whether or not this command is player only.
     * @return True if this command cannot be used by the console.
     */
    public boolean isPlayerOnly() {
        return playerOnly;
    }

    /**
     * Called when the command is executed.
     * @param sender The player or console who executed this command.
     * @param args The arguments executed with this command.
     */
    public abstract void onCommand(CommandSender sender, String[] args);

    /**
     * Called when tab completion is executed.
     * @param sender The player or console who tab completed.
     * @param args The arguments executed with this tab completion.
     * @return The tab completion results.
     */
    public abstract List<String> onTab(CommandSender sender, String[] args);

    /**
     * Gets the permission needed to execute this command.
     * @return The permission needed to execute this command.
     */
    public abstract String getPermission();

    // The syntax for this command. Example: give <player> <item> <amount>

    /**
     * Gets the syntax for this command. E.g. 'give <player> <item> <amount>'.
     * @return The syntax for this command.
     */
    public abstract String getSyntax();
}
