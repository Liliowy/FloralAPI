package me.lilac.floralapi.root.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * A wrapper for Vault Economy.
 */
public class Economy {

    /**
     * An instance of the Vault Economy class.
     */
    private static net.milkbowl.vault.economy.Economy economy;

    /**
     * Creates a new Economy object.
     */
    public Economy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) return;

        RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> serviceProvider = Bukkit.getServer()
                .getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (serviceProvider == null) return;

        economy = serviceProvider.getProvider();
    }

    /**
     * Gives money to a player.
     * @param player The player to receive the money.
     * @param amount The amount of money to give.
     */
    public void giveMoney(OfflinePlayer player, double amount) {
        economy.depositPlayer(player, amount);
    }

    /**
     * Takes money from a player.
     * @param player The player to lose the money.
     * @param amount The amount of money to take.
     */
    public void takeMoney(OfflinePlayer player, double amount) {
        economy.withdrawPlayer(player, amount);
    }

    /**
     * Sets money for a player.
     * @param player The player who's money to edit.
     * @param amount The amount of money to set.
     */
    public void setMoney(OfflinePlayer player, double amount) {
        takeMoney(player, getMoney(player));
        giveMoney(player, amount);
    }

    /**
     * Gets the money from a player.
     * @param player The player to get money from.
     * @return The amount of money the player has.
     */
    public double getMoney(OfflinePlayer player) {
        return economy.getBalance(player);
    }

    /**
     * Gets an instance of the Vault Economy class.
     * @return An instance of the Vault Economy class.
     */
    public static net.milkbowl.vault.economy.Economy getEconomy() {
        return economy;
    }
}

