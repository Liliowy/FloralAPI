package me.lilac.floralapi.root.command;

import me.lilac.floralapi.root.FloralPlugin;
import me.lilac.floralapi.root.utils.LocalizedText;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SubcommandReload extends AbstractSubcommand {

    public SubcommandReload() {
        super("reload");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        FloralPlugin.getInstance().reload();
        sender.sendMessage(new LocalizedText("reloaded").withPrefix().format());
    }

    @Override
    public List<String> onTab(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public String getPermission() {
        return FloralPlugin.getInstance().getPluginName() + ".reload";
    }

    @Override
    public String getSyntax() {
        return "reload";
    }
}
