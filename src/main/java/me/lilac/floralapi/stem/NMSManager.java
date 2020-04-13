package me.lilac.floralapi.stem;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import net.minecraft.server.v1_15_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;

import java.util.UUID;

/**
 * Class for managing common NMS methods.
 */
public class NMSManager {

    /**
     * @return The NMS Server.
     */
    public MinecraftServer getServer() {
        return ((CraftServer) Bukkit.getServer()).getServer();
    }

    /**
     * @param world The Bukkit world to convert.
     * @return The NMS World.
     */
    public WorldServer getWorld(org.bukkit.World world) {
        return ((CraftWorld) world).getHandle();
    }

    /**
     * Creates a game profile with the given UUID and name.
     * @param uuid The uuid of the player.
     * @param name The name of the player.
     * @return A new game profile.
     */
    public GameProfile getGameProfile(UUID uuid, String name) {
        return new GameProfile(uuid, name);
    }
}
