package me.lilac.floralapi.petal.chat;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * A hoverable and clickable message made from several components.
 */
public class ChatMessage {

    private List<ChatComponent> components;

    public ChatMessage() {
        components = new ArrayList<>();
    }

    /**
     * Adds a new component to this chat message.
     * @param component The component to add.
     * @return This chat message.
     */
    public ChatMessage addComponent(ChatComponent component) {
        components.add(component);
        return this;
    }

    /**
     * Sends the message to a player.
     * @param player The player to send the message to.
     */
    public void send(Player player) {
        TextComponent message = new TextComponent();

        for (ChatComponent component : components) {
            TextComponent text = new TextComponent(TextComponent.fromLegacyText(component.getMessage()));
            text.setHoverEvent(component.getHoverEvent());
            text.setClickEvent(component.getClickEvent());
            message.addExtra(text);
        }

        player.spigot().sendMessage(message);
    }

    /**
     * Sends an actionbar message to a player.
     * @param player The player to send the message to.
     * @param message The message to send.
     */
    public static void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)));
    }
}
