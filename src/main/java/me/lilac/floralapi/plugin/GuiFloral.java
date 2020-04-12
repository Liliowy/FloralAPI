package me.lilac.floralapi.plugin;

import me.lilac.floralapi.petal.gui.EditableState;
import me.lilac.floralapi.petal.gui.InventoryItem;
import me.lilac.floralapi.petal.gui.StaticInventoryGUI;
import me.lilac.floralapi.petal.item.ItemBuilder;
import me.lilac.floralapi.root.utils.Language;
import me.lilac.floralapi.root.utils.LocalizedText;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiFloral extends StaticInventoryGUI {

    public GuiFloral() {
        super(new LocalizedText("&lCustom Minecraft Plugins"), 9, 1, "floral");
    }

    @Override
    public void addItems() {
        ItemStack two = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("&2I can't move!").build();
        ItemStack three = new ItemBuilder(Material.DIAMOND).setDisplayName("&cTake me!").build();
        ItemStack four = new ItemBuilder(Material.STONE).setDisplayName("&bSwap me!").build();

        ItemStack five = new ItemBuilder(Material.STICK).setDisplayName("&e&lNull").build();

        addItem(0, 5, new InventoryItem(five) {
           @Override
           public void onClick(InventoryClickEvent event) {
               new LocalizedText("Not a thing babay").asDebugMessage().sendMessage(event.getWhoClicked());
           }
        });

        addItem(0, 0, new InventoryItem(null) {
            @Override
            public void onClick(InventoryClickEvent event) {
                new LocalizedText("Slot Clicked - Item can be placed in").asDebugMessage().sendMessage(event.getWhoClicked());
            }
        }, EditableState.IN);

        addItem(0, 1, new InventoryItem(two) {
            @Override
            public void onClick(InventoryClickEvent event) {
                new LocalizedText("Slot Clicked - Item can not be moved").asDebugMessage().sendMessage(event.getWhoClicked());
            }
        }, EditableState.NONE);

        addItem(0, 2, new InventoryItem(three) {
            @Override
            public void onClick(InventoryClickEvent event) {
                new LocalizedText("Slot Clicked - Item can be taken out").asDebugMessage().sendMessage(event.getWhoClicked());
            }
        }, EditableState.OUT);

        addItem(0, 3, new InventoryItem(four) {
            @Override
            public void onClick(InventoryClickEvent event) {
                new LocalizedText("Slot Clicked - Item can swapped").asDebugMessage().sendMessage(event.getWhoClicked());
            }
        }, EditableState.SWAP);
    }
}
