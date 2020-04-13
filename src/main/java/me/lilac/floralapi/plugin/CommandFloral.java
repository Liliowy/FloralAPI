package me.lilac.floralapi.plugin;

import me.lilac.floralapi.root.command.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandFloral extends AbstractCommand {

    public CommandFloral() {
        super(true, "gui", "gudie");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        FloralAPI.getInstance().getGuiManager().getGUI("floral").open((Player) sender, 0);
    }

    @Override
    public List<String> onTab(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getSyntax() {
        return null;
    }
}
