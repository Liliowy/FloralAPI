package me.lilac.floralapi.petal.gui;

/**
 * A clickable inventory item.
 */
public class Clickable {

    /**
     * The slot this clickable is in.
     */
    private int slot;

    /**
     * The clickable item.
     */
    private InventoryItem item;

    /**
     * How this item can be edited.
     */
    private EditableState state;

    /**
     * Creates a new clickable.
     * @param slot The slot this clickable is in.
     * @param item The clickable item.
     */
    public Clickable(int slot, InventoryItem item, EditableState state) {
        this.slot = slot;
        this.item = item;
        this.state = state;
    }

    /**
     * Gets the slot this clickable is in.
     * @return The slot this clickable is in.
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Gets the clickable item.
     * @return The clickable item.
     */
    public InventoryItem getItem() {
        return item;
    }

    /**
     * Gets the state of the item.
     * @return The editable state of the item.
     */
    public EditableState getState() {
        return state;
    }
}
