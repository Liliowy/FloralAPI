package me.lilac.floralapi.petal.gui;

public enum EditableState {

    OUT,  // Items can be taken out.
    IN, // Items can be placed in.
    SWAP, // Items can be swapped, IN counts as a swap.
    NONE // The item cannot be moved.
}
