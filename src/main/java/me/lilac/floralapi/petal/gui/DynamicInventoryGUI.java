package me.lilac.floralapi.petal.gui;

import me.lilac.floralapi.root.utils.LocalizedText;
import org.bukkit.entity.Player;

/**
 * A wrapper for {@link InventoryGUI} for inventories that may need to be different per player, or change often.
 */
public abstract class DynamicInventoryGUI extends InventoryGUI {

    private Player player;

    /**
     * Creates a new InventoryGUI with the given parameters.
     *
     * @param title The title of the GUI.
     * @param size  The size of the GUI.
     * @param pages How many pages the GUI has.
     * @param ID    The ID of the GUI.
     */
    public DynamicInventoryGUI(LocalizedText title, int size, int pages, String ID, Player player, GUIManager guiManager) {
        super(title, size, pages, ID);
        this.player = player;
        guiManager.registerGUI(this);
        open(player, 0);
    }

    /**
     * Called when the GUI is registered..
     * Define all items in this function.
     */
    @Override
    public abstract void addItems();

    public Player getPlayer() {
        return player;
    }
}