package me.lilac.floralapi.root.command;

import me.lilac.floralapi.root.utils.Language;
import me.lilac.floralapi.root.utils.LocalizedText;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * A command manager that manages other command managers.
 * Used for having multiple different commands.
 * This manager acts as a way to have a help command to show all commands,
 * multiple command managers can be used without a senior command manager,
 * but no help menu will be shown.
 */
public class SeniorCommandManager extends CommandManager {

    /**
     * A list of command managers used by this manager.
     */
    private List<CommandManager> commandManagers;

    /**
     * Creates a new instance of the senior command manager.
     * @param mainCommandLabel The main command label.
     * @param mainSyntax The main command syntax
     */
    public SeniorCommandManager(String mainCommandLabel, String mainSyntax) {
        super(mainCommandLabel, mainSyntax);
        commandManagers = new ArrayList<>();
    }

    @Override
    public void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(new LocalizedText("prefix").format());
        for (CommandManager manager : commandManagers) {
            if (manager.getMainCommand() == null) {
                String label = manager.getMainCommandLabel();
                sender.sendMessage(new LocalizedText(
                        Language.COLOR.getFormatted() + manager.getMainSyntax() + " &7- " +
                                new LocalizedText("command-" + label + "-description").format()
                ).format());

                return;
            }

            AbstractCommand command = manager.getMainCommand();
            String label = command.getLabels().get(0);
            sender.sendMessage(new LocalizedText(
                    new LocalizedText(command.isJuniorCommand() ? label + "-command-color" : "command-color").format() +
                            command.getSyntax() + " &7- ").format() +
                    new LocalizedText((command.isJuniorCommand() ? label + "-command-" : "command-") + "" +
                            command.getLabels().get(0) + "-description"
                    ).format());
        }
    }

    /**
     * Adds a new command manager to this manager.
     * @param manager The command manager to add.
     * @return An instance of this class.
     */
    public SeniorCommandManager addCommandManager(CommandManager manager) {
        commandManagers.add(manager);
        return this;
    }

    /**
     * Gets all command managers for this manager.
     * @return A list of the command managers.
     */
    public List<CommandManager> getCommandManagers() {
        return commandManagers;
    }
}
