package me.lilac.floralapi.petal.gui;

import me.lilac.floralapi.root.FloralPlugin;
import me.lilac.floralapi.root.utils.LocalizedText;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Utility class for creating Inventory GUIs.
 */
public abstract class InventoryGUI implements Listener {

    /**
     * An instance of the main class.
     */
    private FloralPlugin plugin;

    /**
     * The ID of this GUI.
     */
    private String ID;

    /**
     * The title of this GUI.
     */
    private String title;

    /**
     * The size of this GUI.
     */
    private int size;

    /**
     * The pages this gui has.
     */
    private List<Inventory> pages;

    /**
     * The pages last opened by each player.
     */
    private Map<UUID, Integer> playerPages;

    /**
     * A list of the clickable pages this GUI has.
     */
    private List<ClickablePage> clickables;

    /**
     * Creates a new InventoryGUI with the given parameters.
     * @param title The title of the GUI.
     * @param size The size of the GUI.
     * @param pages How many pages the GUI has.
     * @param ID The ID of the GUI.
     */
    public InventoryGUI(LocalizedText title, int size, int pages, String ID) {
        clickables = new ArrayList<>();
        playerPages = new HashMap<>();
        this.pages = new ArrayList<>();
        this.title = title.format();
        this.size = size;
        this.ID = ID;

        this.plugin = FloralPlugin.getInstance();

        for (int i = 0; i < pages; i++) {
            this.pages.add(Bukkit.createInventory(new GUIHolder(), size, this.title));
            clickables.add(new ClickablePage());
        }
    }

    /**
     * Called when the GUI is registered.
     * Adds the items to the array.
     */
    public void onRegister() {
        addItems();
    }

    /**
     * Opens a page for a player.
     * @param player The player to open the GUI for.
     * @param page The page to open on.
     */
    public void open(Player player, int page) {
        player.openInventory(this.pages.get(page));
        playerPages.put(player.getUniqueId(), page);
    }

    /**
     * Adds items to the GUI. Can be called in the constructor, onRegister or open. Open for dynamic GUIs.
     */
    public abstract void addItems();

    /**
     * Adds an item to the inventory.
     * @param page The page to add the item to.
     * @param slot The slot to add the item in.
     * @param item The item to add.
     */
    public void addItem(int page, int slot, InventoryItem item) {
        pages.get(page).setItem(slot, item.getItem());
        clickables.get(page).getClickables().add(new Clickable(slot, item));
    }

    /**
     * Creates a 4 sided border in the given page with the given ItemStack.
     * @param page The page to border.
     * @param item The ItemStack to be placed.
     */
    public void setBorder(int page, ItemStack item) {
        for (int i = 0; i < 9; i++) pages.get(page).setItem(i, item);
        for (int i = 9; i < size - 9; i += 9) pages.get(page).setItem(i, item);
        for (int i = 17; i < size - 9; i += 9) pages.get(page).setItem(i, item);
        for (int i = size - 9; i < size; i++) pages.get(page).setItem(i, item);
    }

    /**
     * Creates a 4 sided border in every page.
     * @param item The ItemStack to be placed.
     */
    public void setBorder(ItemStack item) {
        for (int i = 0; i < pages.size(); i++) setBorder(i, item);
    }

    /**
     * @return The ID of this GUI.
     */
    public String getID() {
        return ID;
    }

    /**
     * @return The title of this GUI.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The size of this GUI.
     */
    public int getSize() {
        return size;
    }

    /**
     * @return The pages of this GUI.
     */
    public List<Inventory> getPages() {
        return pages;
    }

    /**
     * @return The pages last opened by players.
     */
    public Map<UUID, Integer> getPlayerPages() {
        return playerPages;
    }

    /**
     * @return The clickable pages for this GUI.
     */
    public List<ClickablePage> getClickables() {
        return clickables;
    }

    /**
     * @return An instance of the main class.
     */
    public FloralPlugin getPlugin() {
        return plugin;
    }
}