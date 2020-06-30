package me.lilac.floralapi.petal.chat;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

/**
 * Manages hoverable and clickable components within a {@link ChatMessage}.
 */
public class ChatComponent {

    private String message;
    private HoverEvent hoverEvent;
    private ClickEvent clickEvent;

    public ChatComponent(String message) {
        this.message = ChatColor.translateAlternateColorCodes('&', message);
    }

    public ChatComponent(String message, HoverEvent hoverEvent) {
        this(message);
        this.hoverEvent = hoverEvent;
    }

    public ChatComponent(String message, ClickEvent clickEvent) {
        this(message);
        this.clickEvent = clickEvent;
    }

    public ChatComponent(String message, HoverEvent hoverEvent, ClickEvent clickEvent) {
        this(message, hoverEvent);
        this.clickEvent = clickEvent;
    }

    /**
     * Sets the hover event for this component.
     * @param action The action for this hover event.
     * @param value The value for the hover event.
     * @return This chat component.
     */
    public ChatComponent setHoverEvent(HoverEvent.Action action, String value) {
        BaseComponent[] nValue = TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', value));
        this.hoverEvent = new HoverEvent(action, nValue);
        return this;
    }

    /**
     * Sets the click event for this component.
     * @param action The action for this click event.
     * @param value The value for this click event.
     * @return This chat component.
     */
    public ChatComponent setClickEvent(ClickEvent.Action action, String value) {
        this.clickEvent = new ClickEvent(action, ChatColor.translateAlternateColorCodes('&', value));
        return this;
    }

    /**
     * @return The message of this component. Useful for logging to console.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return The hover event for this component.
     */
    public HoverEvent getHoverEvent() {
        return hoverEvent;
    }

    /**
     * @return The click event for this component.
     */
    public ClickEvent getClickEvent() {
        return clickEvent;
    }

    @Override
    public String toString() {
        return "ChatComponent{" +
                "message='" + message + '\'' +
                ", hoverEvent=" + hoverEvent.toString() +
                ", clickEvent=" + clickEvent.toString() +
                '}';
    }
}
