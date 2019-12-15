package me.lilac.floralapi.stem;

import net.minecraft.server.v1_14_R1.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Utility class used for managing packets.
 */
public class PacketManager {

    /**
     * Sends a packet to a player.
     * @param packet The packet to send.
     * @param player The player to send the packet to.
     */
    public void send(Packet<?> packet, Player player) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    /**
     * Sends a packet to multiple players.
     * @param packet The packet to send.
     * @param players A list of players to send the packet to.
     */
    public void send(Packet<?> packet, List<Player> players) {
        for (Player player : players) send(packet, player);
    }

    /**
     * Sends a packet to every online player.
     * @param packet The packet to send.
     */
    public void send(Packet<?> packet) {
        for (Player player : Bukkit.getOnlinePlayers()) send(packet, player);
    }
}
