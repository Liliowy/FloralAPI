package me.lilac.floralapi.stem;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import net.minecraft.server.v1_15_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;

import java.util.UUID;

public class NMSManager {

    public MinecraftServer getServer() {
        return ((CraftServer) Bukkit.getServer()).getServer();
    }

    public WorldServer getWorld(org.bukkit.World world) {
        return ((CraftWorld) world).getHandle();
    }

    public GameProfile getGameProfile(UUID uuid, String name) {
        return new GameProfile(uuid, name);
    }
}
