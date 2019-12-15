package me.lilac.floralapi.petal.gui;

import java.util.ArrayList;
import java.util.List;

/**
 * Clickable pages for InventoryGUIs.
 */
public class ClickablePage {

    /**
     * All the clickables in this page.
     */
    private List<Clickable> clickables;

    /**
     * Creates a new clickable page.
     */
    public ClickablePage() {
        this.clickables = new ArrayList<>();
    }

    /**
     * Gets a list of all the clickables in this page.
     * @return A list of all the clickables in this page.
     */
    public List<Clickable> getClickables() {
        return clickables;
    }
}
