package me.lilac.floralapi.root.item;

import me.lilac.floralapi.root.storage.YMLFile;
import me.lilac.floralapi.root.utils.LocalizedText;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for serializing ItemStacks.
 */
public class ItemSerializer {

    /**
     * Serializes an ItemStack to a Map<String, Object>.
     * Key Examples: material, amount, display-name.
     * @param stack The ItemStack to serialize.
     * @return The serialized ItemStack.
     */
    public static Map<String, Object> serialize(ItemStack stack) {
        Map<String, Object> data = new HashMap<>();

        data.put("material", stack.getType().toString());
        data.put("amount", stack.getAmount());
        if (!stack.hasItemMeta()) return data;
        ItemMeta meta = stack.getItemMeta();

        if (meta instanceof Damageable) data.put("durability", ((Damageable) meta).getDamage());
        if (meta.hasDisplayName()) data.put("display-name", LocalizedText.serialize(meta.getDisplayName()));
        if (meta.hasLore()) data.put("lore", LocalizedText.serialize(meta.getLore()));

        if (meta.hasEnchants()) {
            List<String> enchantments = new ArrayList<>();
            for (Enchantment enchantment : meta.getEnchants().keySet()) {
                enchantments.add(enchantment.getKey().getKey() + ":" + meta.getEnchantLevel(enchantment));
            }
            data.put("enchantments", enchantments);
        }

        if (meta.getItemFlags() != null && !meta.getItemFlags().isEmpty()) {
            List<String> flags = new ArrayList<>();
            for (ItemFlag flag : meta.getItemFlags()) flags.add(flag.toString());
            data.put("item-flags", flags);
        }

        if (meta.isUnbreakable()) data.put("unbreakable", meta.isUnbreakable());

        if (meta.hasEnchant(Enchantment.DURABILITY) && meta.getEnchantLevel(Enchantment.DURABILITY) == 0) data.put("glowing", true);

        if (meta instanceof BannerMeta) {
            List<String> patterns = new ArrayList<>();
            for (Pattern pattern : ((BannerMeta) meta).getPatterns())
                patterns.add(pattern.getColor().toString() + ":" + pattern.getPattern().toString());
            data.put("banner-patterns", patterns);
        }

        if (meta instanceof BlockStateMeta && stack.getType() == Material.SPAWNER)
            data.put("spawner-type", ((CreatureSpawner) ((BlockStateMeta) meta).getBlockState()).getSpawnedType().toString());

        if (meta instanceof BookMeta) {
            if (((BookMeta) meta).hasAuthor()) data.put("book.author", LocalizedText.serialize(((BookMeta) meta).getAuthor()));
            if (((BookMeta) meta).hasTitle()) data.put("book.title", LocalizedText.serialize(((BookMeta) meta).getTitle()));
            if (((BookMeta) meta).hasPages()) data.put("book.pages", LocalizedText.serialize(((BookMeta) meta).getPages()));
        }

        if (meta instanceof EnchantmentStorageMeta) {
            List<String> enchantments = new ArrayList<>();
            for (Enchantment enchantment : ((EnchantmentStorageMeta) meta).getStoredEnchants().keySet()) {
                enchantments.add(enchantment.getKey().getKey() + ":" + ((EnchantmentStorageMeta) meta).getEnchantLevel(enchantment));
            }
            data.put("enchantments", enchantments);
        }

        if (meta instanceof FireworkMeta) {
            int i = 0;
            for (FireworkEffect effect : ((FireworkMeta) meta).getEffects()) {
                List<Integer> colors = new ArrayList<>();
                List<Integer> fadeColors = new ArrayList<>();

                for (Color color : effect.getColors()) colors.add(color.asRGB());
                for (Color color : effect.getFadeColors()) fadeColors.add(color.asRGB());

                data.put("effects." + i + ".type", effect.getType());
                data.put("effects." + i + ".colors", colors);
                data.put("effects." + i + ".fade-colors", colors);
                data.put("effects." + i + ".trail", effect.hasTrail());
                data.put("effects." + i + ".flicker", effect.hasFlicker());
                i++;
            }

            data.put("power", ((FireworkMeta) meta).getPower());
        }

        if (meta instanceof LeatherArmorMeta) data.put("color", ((LeatherArmorMeta) meta).getColor().asRGB());

        if (meta instanceof PotionMeta) {
            int i = 0;
            for (PotionEffect effect : ((PotionMeta) meta).getCustomEffects()) {
                data.put("effects." + i + ".type", effect.getType().toString());
                data.put("effects." + i + ".amplifier", effect.getAmplifier());
                data.put("effects." + i + ".duration", effect.getDuration());
                data.put("effects." + i + ".icon", effect.hasIcon());
                data.put("effects." + i + ".particles", effect.hasParticles());
                data.put("effects." + i + ".ambient", effect.isAmbient());
                i++;
            }

            data.put("color", ((PotionMeta) meta).getColor().asRGB());
        }

        if (meta instanceof SkullMeta) data.put("skull-owner", ((SkullMeta) meta).getOwningPlayer().getName());

        if (meta instanceof SuspiciousStewMeta) {
            int i = 0;
            for (PotionEffect effect : ((SuspiciousStewMeta) meta).getCustomEffects()) {
                data.put("effects." + i + ".type", effect.getType().toString());
                data.put("effects." + i + ".amplifier", effect.getAmplifier());
                data.put("effects." + i + ".duration", effect.getDuration());
                data.put("effects." + i + ".icon", effect.hasIcon());
                data.put("effects." + i + ".particles", effect.hasParticles());
                data.put("effects." + i + ".ambient", effect.isAmbient());
                i++;
            }
        }

        if (meta instanceof TropicalFishBucketMeta) {
            data.put("tropical-fish.body-color", ((TropicalFishBucketMeta) meta).getBodyColor().toString());
            data.put("tropical-fish.pattern", ((TropicalFishBucketMeta) meta).getPattern().toString());
            data.put("tropical-fish.pattern-color", ((TropicalFishBucketMeta) meta).getPatternColor().toString());
        }

        return data;
    }

    /**
     * Saves a deserialized ItemStack to a file.
     * Useful for readability and editability.
     * @param data The deserialized ItemStack to save.
     * @param file The file to save to.
     * @param path The path the ItemStack should be saved to.
     */
    public static void saveToFile(Map<String, Object> data, YMLFile file, String path) {
        for (String string : data.keySet()) {
            file.set(path + "." + string, data.get(string));
        }
    }

    /**
     * Converts multiple ItemStacks to Base64.
     * Not intended for human reading or editing.
     * @param items The ItemStacks to serialize.
     * @return The Base64 String obtained from the ItemStacks.
     * @throws IOException
     */
    public static String toBase64(ItemStack[] items) throws IOException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(items.length);

            for (ItemStack item : items) {
                dataOutput.writeObject(item);
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}