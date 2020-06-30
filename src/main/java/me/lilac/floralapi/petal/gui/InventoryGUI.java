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
     * Whether or not this inventory is fully editable by default.
     */
    private boolean editable;

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

        for (int i = 0; i < pages; i++) addPage(size, title);
    }

    /**
     * Called when the GUI is registered.
     */
    public void onRegister() {
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
     * Opens page 0 for a player.
     * @param player The player to open the GUI for.
     */
    public void open(Player player) {
        player.openInventory(this.pages.get(0));
        playerPages.put(player.getUniqueId(), 0);
    }

    /**
     * Adds a new inventory page.
     * @param size The size of the new page.
     * @param title The title of the new page.
     */
    public void addPage(int size, LocalizedText title) {
        this.pages.add(Bukkit.createInventory(new GUIHolder(), size, title.format()));
        clickables.add(new ClickablePage());
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
        addItem(page, slot, item, EditableState.NONE);
    }

    /**
     * Adds an item to the inventory, specifying an editable state.
     * @param page The page to add the item to.
     * @param slot The slot to add the item in.
     * @param item The item to add.
     * @param state How this item can be edited.
     */
    public void addItem(int page, int slot, InventoryItem item, EditableState state) {
        if (item.getItem() != null) pages.get(page).setItem(slot, item.getItem());
        clickables.get(page).getClickables().add(new Clickable(slot, item, state));
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
     * Refreshes the GUI, adds all items again.
     */
    public void refreshGUI() {
        for (Inventory inv : getPages()) inv.clear();
        addItems();
    }

    /**
     * Calculates the inventory size (a multiple of nine) based on the given input.
     * @param inSize The size to calculate from.
     * @return The correct inventory size (multiple of nine).
     */
    public int getSizeBy9(int inSize) {
        for (int i = 9; i < 54; i += 9) {
            if (inSize > i) return i;
        }

        return 27;
    }

    /**
     * Sets this inventory as editable.
     * @param editable Whether or not this inventory can be edited (excludes added itemw).
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
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
     * @return Whether or not this GUI is editable by default.
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @return The pages of this GUI.
     */
    public List<Inventory> getPages() {
        return pages;
    }

    /**
     * Gets a specific page from the GUI.
     * @param page The page to get.
     * @return The page found.
     */
    public Inventory getPage(int page) {
       return getPages().get(page);
    }

    /**
     * @return The pages last opened by players.
     */
    public Map<UUID, Integer> getPlayerPages() {
        return playerPages;
    }

    /**
     * Gets the page number for a player.
     * @param uuid The player to get.
     * @return The page that this player has open.
     */
    public int getPlayerPageNumber(UUID uuid) {
        return getPlayerPages().get(uuid);
    }

    /**
     * Gets the inventory that a player has open.
     * @param uuid The player to get.
     * @return The inventory that this player has open.
     */
    public Inventory getPlayerPage(UUID uuid) {
        return getPage(getPlayerPageNumber(uuid));
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