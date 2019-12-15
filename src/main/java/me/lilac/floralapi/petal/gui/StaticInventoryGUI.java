package me.lilac.floralapi.petal.gui;

import me.lilac.floralapi.root.utils.LocalizedText;

/**
 * A wrapper for {@link InventoryGUI} InventoryGUI for non-changing, or rarely changing GUIs.
 */
public abstract class StaticInventoryGUI extends InventoryGUI {

    /**
     * Creates a new static inventory GUI.
     * Adds the items to the GUI.
     * @param title The title of the GUI.
     * @param size The size of the GUI.
     * @param pages How many pages the GUI has.
     * @param ID The ID of the GUI.
     */
    public StaticInventoryGUI(LocalizedText title, int size, int pages, String ID) {
        super(title, size, pages, ID);
    }

    /**
     * Called when the GUI is registered.
     * Define all items in this function.
     */
    @Override
    public abstract void addItems();
}
