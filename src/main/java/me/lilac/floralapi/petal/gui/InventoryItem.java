package me.lilac.floralapi.petal.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * An item that can be clicked inside an inventory.
 */
public abstract class InventoryItem {

    /**
     * The ItemStack of the InventoryItem.
     */
    private ItemStack item;

    /**
     * Whether or not this item can be removed from the GUI.
     */
    private boolean isRemoveable;

    /**
     * Creates a new inventory item.
     * @param item The ItemStack to use.
     * @param removable Whether or not this item can be removed from the GUI.
     */
    public InventoryItem(ItemStack item, boolean removable) {
        this.item = item;
        this.isRemoveable = removable;
    }

    /**
     * Called when this item is clicked.
     * @param event An InventoryClickEvent.
     */
    public abstract void onClick(InventoryClickEvent event);

    /**
     * @return The ItemStack of this InventoryItem.
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * @return True if this item can be removed from the inventory.
     */
    public boolean isRemoveable() {
        return isRemoveable;
    }
}

