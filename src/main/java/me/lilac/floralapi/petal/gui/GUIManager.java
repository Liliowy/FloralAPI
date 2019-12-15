package me.lilac.floralapi.petal.gui;

import me.lilac.floralapi.root.FloralPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for managing GUIs & GUI events.
 */
public class GUIManager implements Listener {

    /**
     * The registered inventories.
     */
    private Map<String, InventoryGUI> inventories;

    /**
     * Creates a new instance of the GUIManager. Should only be one.
     */
    public GUIManager() {
        inventories = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, FloralPlugin.getInstance());
    }

    /**
     * Registers a GUI.
     * @param gui The GUI to register.
     * @return An instance of this class.
     */
    public GUIManager registerGUI(InventoryGUI gui) {
        inventories.put(gui.getID(), gui);
        gui.onRegister();
        return this;
    }

    /**
     * Manages click events for every registered GUI.
     * @param event An InventoryClickEvent.
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof GUIHolder)) return;
        String title = ChatColor.stripColor(event.getView().getTitle());
        Player player = (Player) event.getWhoClicked();

        for (InventoryGUI gui : inventories.values()) {
            if (!title.equalsIgnoreCase(ChatColor.stripColor(gui.getTitle()))) continue;
            event.setCancelled(true);
            List<ClickablePage> clickablePages = gui.getClickables();
            int page = gui.getPlayerPages().get(player.getUniqueId());
            ClickablePage openPage = clickablePages.get(page);

            for (Clickable clickable : openPage.getClickables()) {
                if (clickable.getSlot() != event.getSlot()) return;
                InventoryItem item = clickable.getItem();
                event.setCancelled(!item.isRemoveable());
                item.onClick(event);
                break;
            }

            break;
        }
    }

    /**
     * Manages close events for every registered GUI.
     * Used for deregistering dynamic GUIs.
     * @param event An InventoryCloseEvent.
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getInventory().getHolder() instanceof GUIHolder)) return;
        String title = ChatColor.stripColor(event.getView().getTitle());
        Player player = (Player) event.getPlayer();

        for (InventoryGUI gui : inventories.values()) {
            if (!title.equalsIgnoreCase(ChatColor.stripColor(gui.getTitle()))) continue;
            if (!(gui instanceof DynamicInventoryGUI)) continue;
            inventories.remove(gui.getID());
            break;
        }
    }

    /**
     * Gets a specific GUI from the given ID.
     * @param ID The ID of the GUI to get.
     * @return An InventoryGUI with the ID.
     */
    public InventoryGUI getGUI(String ID) {
        return inventories.get(ID);
    }

    /**
     * @return The registered GUIs.
     */
    public Map<String, InventoryGUI> getInventories() {
        return inventories;
    }
}