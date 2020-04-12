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
     * Creates a new inventory item.
     * @param item The ItemStack to use.
     */
    public InventoryItem(ItemStack item) {
        this.item = item;
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
}

