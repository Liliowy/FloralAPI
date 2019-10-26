package me.lilac.floralapi.root;

public interface RootPlugin {

    // Defines the version of the Root API.
    default String getRootVersion() {
        return "1.0.0";
    }
}
