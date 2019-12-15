package me.lilac.floralapi.petal.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import me.lilac.floralapi.root.utils.LocalizedText;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.bukkit.potion.PotionEffect;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Utility class for building ItemStacks.
 */
public class ItemBuilder {

    /**
     * The ItemStack being edited.
     */
    private ItemStack item;

    /**
     * The ItemMeta being edited.
     */
    private ItemMeta meta;

    /**
     * Creates a new instance of the ItemBuilder.
     * @param material The material of the ItemStack.
     */
    public ItemBuilder(Material material) {
        item = new ItemStack(material);
        meta = item.getItemMeta();
    }

    /**
     * Creates a new instance of the ItemBuilder.
     * @param item The ItemStack to edit.
     */
    public ItemBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    /**
     * Sets the material for this ItemStack.
     * @param material The material to use.
     * @return An instance of this class.
     */
    public ItemBuilder setMaterial(Material material) {
        item.setType(material);
        return this;
    }

    /**
     * Sets the amount for this ItemStack.
     * @param amount The amount to use.
     * @return An instance of this class.
     */
    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    /**
     * Sets the durability for this ItemStack.
     * @param durability The durability to use.
     * @return An instance of this class.
     */
    public ItemBuilder setDurability(int durability) {
        if (meta instanceof Damageable) ((Damageable) meta).setDamage(durability);
        return this;
    }

    /**
     * Sets the display name for this ItemStack.
     * @param name The display name to use.
     * @return An instance of this class.
     */
    public ItemBuilder setDisplayName(String name) {
        meta.setDisplayName(new LocalizedText(name).format());
        return this;
    }

    /**
     * Sets the lore for this ItemStack.
     * @param lore The lore to use.
     * @return An instance of this class.
     */
    public ItemBuilder setLore(List<String> lore) {
        meta.setLore(new LocalizedText(lore).toArray());
        return this;
    }

    /**
     * Adds an enchantment to this ItemStack.
     * @param enchantment The enchantment to add.
     * @param level The level of the enchantment to add.
     * @return An instance of this class.
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        if (meta instanceof EnchantmentStorageMeta) {
            ((EnchantmentStorageMeta) meta).addStoredEnchant(enchantment, level, true);
        } else {
            meta.addEnchant(enchantment, level, true);
        }

        return this;
    }

    /**
     * Sets the enchantments on this ItemStack.
     * @param enchantments The enchantments to use.
     * @return An instance of this class.
     */
    public ItemBuilder setEnchantments(Map<Enchantment, Integer> enchantments) {
        if (meta instanceof EnchantmentStorageMeta) {
            ((EnchantmentStorageMeta) meta).getStoredEnchants().clear();
            for (Enchantment enchantment : enchantments.keySet())
                ((EnchantmentStorageMeta) meta).addStoredEnchant(enchantment, enchantments.get(enchantment), true);
        } else {
            meta.getEnchants().clear();
            for (Enchantment enchantment : enchantments.keySet())
                meta.addEnchant(enchantment, enchantments.get(enchantment), true);
        }

        return this;
    }

    /**
     * Adds item flags  to this ItemStack.
     * @param flags The item flags to add.
     * @return An instance of this class.
     */
    public ItemBuilder addItemFlags(List<ItemFlag> flags) {
        for (ItemFlag flag : flags) meta.addItemFlags(flag);
        return this;
    }

    /**
     * Sets if this ItemStack is unbreakable.
     * @param unbreakable If this ItemStack if unbreakable.
     * @return An instance of this class.
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Sets if this ItemStack is glowing.
     * @param glowing If this ItemStack is glowing.
     * @return An instance of this class.
     */
    public ItemBuilder setGlowing(boolean glowing) {
        if (glowing) meta.addEnchant(Enchantment.DURABILITY, 0, true);
        else meta.removeEnchant(Enchantment.DURABILITY);
        return this;
    }

    /**
     * Adds a banner pattern to the ItemStack.
     * @param patterns The patterns to add.
     * @return An instance of this class.
     */
    public ItemBuilder addBannerPatterns(List<Pattern> patterns) {
        if (meta instanceof BannerMeta)  {
            for (Pattern pattern : patterns)
                ((BannerMeta) meta).addPattern(pattern);
        }
        return this;
    }

    /**
     * Sets the spawner type for the ItemStack.
     * @param type The spawner type to use.
     * @return An instance of this class.
     */
    public ItemBuilder setSpawnerType(EntityType type) {
        if (meta instanceof BlockStateMeta && item.getType() == Material.SPAWNER)
            ((CreatureSpawner) ((BlockStateMeta) meta).getBlockState()).setSpawnedType(type);
        return this;
    }

    /**
     * Sets the book author for the ItemStack.
     * @param author The author to use.
     * @return An instance of this class.
     */
    public ItemBuilder setBookAuthor(String author) {
        if (meta instanceof BookMeta) ((BookMeta) meta).setAuthor(new LocalizedText(author).format());
        return this;
    }

    /**
     * Sets the book title for the ItemStack.
     * @param title The title to use.
     * @return An instance of this class.
     */
    public ItemBuilder setBookTitle(String title) {
        if (meta instanceof BookMeta) ((BookMeta) meta).setTitle(new LocalizedText(title).format());
        return this;
    }

    /**
     * Adds a book page to the ItemStack.
     * @param pages The pages to add.
     * @return An instance of this class.
     */
    public ItemBuilder addBookPages(List<String> pages) {
        if (meta instanceof BookMeta) {
            for (String page : pages) ((BookMeta) meta).addPage(new LocalizedText(page).format());
        }
        return this;
    }

    /**
     * Adds a potion effect to the ItemStack.
     * @param overwrite Whether the current effects should be overwritten.
     * @param effects The effects to use.
     * @return An instance of this class.
     */
    public ItemBuilder addPotionEffects(boolean overwrite, List<PotionEffect> effects) {
        if (meta instanceof PotionMeta) {
            for (PotionEffect effect : effects)
                ((PotionMeta) meta).addCustomEffect(effect, overwrite);
        } else if (meta instanceof SuspiciousStewMeta) {
            for (PotionEffect effect : effects)
                ((SuspiciousStewMeta) meta).addCustomEffect(effect, overwrite);
        }

        return this;
    }

    /**
     * Adds a firework effect to the ItemStack.
     * @param effect The effects to use.
     * @return An instance of this class.
     */
    public ItemBuilder addFireworkEffect(FireworkEffect... effect) {
        if (meta instanceof FireworkEffectMeta) {
            ((FireworkEffectMeta) meta).setEffect(effect[0]);
        } else if (meta instanceof FireworkMeta) {
            ((FireworkMeta) meta).addEffects(effect);
        }

        return this;
    }

    /**
     * Sets the firework power on the ItemStack.
     * @param power The firework power to use.
     * @return An instance of this class.
     */
    public ItemBuilder setFireworkPower(int power) {
        if (meta instanceof FireworkMeta) ((FireworkMeta) meta).setPower(power);
        return this;
    }

    /**
     * Sets the color of the ItemStack.
     * @param color The color to use.
     * @return An instance of this class.
     */
    public ItemBuilder setColor(int color) {
        if (meta instanceof LeatherArmorMeta) ((LeatherArmorMeta) meta).setColor(Color.fromRGB(color));
        else if (meta instanceof PotionMeta) ((PotionMeta) meta).setColor(Color.fromRGB(color));
        return this;
    }

    /**
     * Sets the skull owner of the ItemStack.
     * @param skullOwner The skull owner to use.
     * @return An instance of this class.
     */
    public ItemBuilder setSkullOwner(String skullOwner) {
        if (meta instanceof SkullMeta) {
            if (skullOwner.length() < 17) {
                ((SkullMeta) meta).setOwner(skullOwner);
            } else if (skullOwner.contains("-")) {
                ((SkullMeta) meta).setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(skullOwner)));
            } else {
                setSkullOwnerBase64(skullOwner);
            }
        }

        return this;
    }

    /**
     * Sets the skull owner of the ItemStack from base64.
     * @param base64 The skull owner to use.
     * @return An instance of this class.
     */
    public ItemBuilder setSkullOwnerBase64(String base64) {
        if (meta instanceof SkullMeta) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), "");
            profile.getProperties().put("textures", new Property("textures", base64));

            try {
                Field profileField = meta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(meta, profile);
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return this;
    }

    /**
     * Sets the tropical fish body color for the ItemStack.
     * @param color The color to use.
     * @return An instance of this class.
     */
    public ItemBuilder setTropicalFishBodyColor(DyeColor color) {
        if (meta instanceof TropicalFishBucketMeta) ((TropicalFishBucketMeta) meta).setBodyColor(color);
        return this;
    }

    /**
     * Sets the tropical fish pattern for the ItemStack.
     * @param pattern The pattern to use.
     * @return An instance of this class.
     */
    public ItemBuilder setTropicalFishPattern(TropicalFish.Pattern pattern) {
        if (meta instanceof TropicalFishBucketMeta) ((TropicalFishBucketMeta) meta).setPattern(pattern);
        return this;
    }

    /**
     * Sets the tropical fish pattern color for the ItemStack.
     * @param color The color to use.
     * @return An instance of this class.
     */
    public ItemBuilder setTropicalFishPatternColor(DyeColor color) {
        if (meta instanceof TropicalFishBucketMeta) ((TropicalFishBucketMeta) meta).setPatternColor(color);
        return this;
    }

    /**
     * Gets the ItemMeta for the ItemStack.
     * @return The ItemMeta.
     */
    public ItemMeta getMeta() {
        return meta;
    }

    /**
     * Gets the Material of the ItemStack.
     * @return The Material.
     */
    public Material getType() {
        return item.getType();
    }

    /**
     * Builds the ItemStack.
     * @return An ItemStack with the given properties.
     */
    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}