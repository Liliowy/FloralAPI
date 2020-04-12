package me.lilac.floralapi.petal.gui;

import me.lilac.floralapi.root.FloralPlugin;
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
    public DynamicInventoryGUI(LocalizedText title, int size, int pages, String ID, Player player) {
        super(title, size, pages, ID);
        this.player = player;
    }

    /**
     * Opens the GUI for a specific player.
     * Registers this GUI on open.
     * @param player The player to open the GUI for.
     * @param page The page to open on.
     */
    @Override
    public void open(Player player, int page) {
        super.open(player, page);
        FloralPlugin.getInstance().getGuiManager().registerGUI(this);
    }

    /**
     * Called when the GUI is registered..
     * Define all items in this function.
     */
    @Override
    public abstract void addItems();

    /**
     * Gets the player who owns this GUI.
     * @return The player who owns this GUI.
     */
    public Player getPlayer() {
        return player;
    }
}