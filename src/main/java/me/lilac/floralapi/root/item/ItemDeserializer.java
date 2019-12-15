package me.lilac.floralapi.root.item;

import me.lilac.floralapi.root.storage.YMLFile;
import me.lilac.floralapi.root.utils.LocalizedText;
import me.lilac.floralapi.petal.item.ItemBuilder;
import me.lilac.floralapi.root.item.ItemPlaceholderManager;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeWrapper;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for deserializing ItemStacks.
 */
public class ItemDeserializer {

    /**
     * Wrapper for {@link #deserialize(YMLFile file, String path, ItemPlaceholderManager ipm)} without requiring an IPM.
     * @param file The file to read from.
     * @param path THe path to get the data from.
     * @return The deserialized ItemStack.
     */
    public static ItemStack deserialize(YMLFile file, String path) {
        return deserialize(file, path, new ItemPlaceholderManager());
    }

    /**
     * Creates an ItemStack from a configuration section.
     * @param file The file to read from.
     * @param path The path to get the data from.
     * @return The deserialized ItemStack.
     */
    public static ItemStack deserialize(YMLFile file, String path, ItemPlaceholderManager ipm) {
        ItemBuilder item = new ItemBuilder(file.getMaterial(path + ".material"));
        if (file.contains(path + ".amount")) item.setAmount(file.getInt(path + ".amount"));
        if (file.contains(path + ".durability") && item.getMeta() instanceof Damageable)
            item.setDurability(file.getInt(path + ".durability"));
        if (file.contains(path + ".display-name"))
            item.setDisplayName(new LocalizedText(file.getString(path + ".display-name"), file).withIPM(ipm).format());
        if (file.contains(path + ".lore"))
            item.setLore(new LocalizedText(path + ".lore").fromArray(file).withIPM(ipm).toArray());

        if (file.contains(path + ".enchantments")) {
            Map<Enchantment, Integer> enchantments = new HashMap<>();
            for (String ench : file.getStringList(path + ".enchantments")) {
                String[] enchSplit = ench.split(":");
                Enchantment enchantment = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(enchSplit[0]));
                enchantments.put(enchantment, Integer.parseInt(enchSplit[1]));
            }
            item.setEnchantments(enchantments);
        }

        if (file.contains(path + ".item-flags")) {
            List<ItemFlag> flags = new ArrayList<>();
            for (String flag : file.getStringList(path + ".item-flags"))
                flags.add(ItemFlag.valueOf(flag));
            item.addItemFlags(flags);
        }

        if (file.contains(path + ".unbreakable")) item.setUnbreakable(file.getBoolean(path + ".unbreakable"));
        if (file.contains(path + ".glowing")) item.setGlowing(file.getBoolean(path + ".glowing"));

        if (file.contains(path + ".banner-patterns") && item.getMeta() instanceof BannerMeta) {
            List<Pattern> patterns = new ArrayList<>();
            for (String string : file.getStringList(path + ".banner-patterns")) {
                String[] patternSplit = string.split(":");
                patterns.add(new Pattern(DyeColor.valueOf(patternSplit[0]), PatternType.valueOf(patternSplit[1])));
            }
            item.addBannerPatterns(patterns);
        }

        if (file.contains(path + ".spawner-type") && item.getType() == Material.SPAWNER)
            ((CreatureSpawner) ((BlockStateMeta) item.getMeta()).getBlockState())
                    .setSpawnedType(EntityType.valueOf(file.getString(path + ".spawner-type")));

        if (file.contains(path + ".book.author") && item.getMeta() instanceof BookMeta)
            item.setBookAuthor(new LocalizedText(file.getString(path + ".book.author"), file).withIPM(ipm).format());

        if (file.contains(path + ".book.title") && item.getMeta() instanceof BookMeta)
            item.setBookTitle(new LocalizedText(file.getString(path + ".book.title"), file).withIPM(ipm).format());

        if (file.contains(path + ".book.pages") && item.getMeta() instanceof BookMeta)
            item.addBookPages(new LocalizedText(path + ".book.pages").fromArray(file).withIPM(ipm).toArray());

        if (item.getMeta() instanceof FireworkMeta || item.getMeta() instanceof FireworkEffectMeta) {
            if (file.contains(path + ".effects")) {
                List<FireworkEffect> effects = new ArrayList<>();

                for (String id : file.getSection(path + ".effects").getKeys(false)) {
                    FireworkEffect.Type type = FireworkEffect.Type.valueOf(file.getString(path + ".effects." + id + ".type"));
                    List<Color> colors = new ArrayList<>();
                    List<Color> fadeColors = new ArrayList<>();

                    for (int color : file.getIntList(path + ".effects." + id + ".colors"))
                        colors.add(Color.fromRGB(color));
                    for (int color : file.getIntList(path + ".effects." + id + ".fade-colors"))
                        fadeColors.add(Color.fromRGB(color));
                    boolean trail = file.contains(path + ".effects." + id + ".trail") && file.getBoolean(path + ".effects." + id + ".trail");
                    boolean flicker = file.contains(path + ".effects." + id + ".flicker") && file.getBoolean(path + ".effects." + id + ".flicker");

                    effects.add(FireworkEffect.builder().flicker(flicker).trail(trail).withFade(fadeColors)
                            .withColor(colors).with(type).build());
                }

                item.setFireworkPower(file.contains(path + ".power") ? file.getInt(path + ".power") : 1);
            }
        }

        if (item.getMeta() instanceof SkullMeta && file.contains(path + ".skull-owner"))
            item.setSkullOwner(file.getString(path + ".skull-owner"));

        if (item.getMeta() instanceof LeatherArmorMeta && file.contains(path + ".color"))
            item.setColor(file.getInt(path + ".color"));

        if (item.getMeta() instanceof SuspiciousStewMeta || item.getMeta() instanceof PotionMeta) {
            if (file.contains(path + ".effects")) {
                List<PotionEffect> effects = new ArrayList<>();

                for (String id : file.getSection(path + ".effects").getKeys(false)) {
                    PotionEffectType type = PotionEffectTypeWrapper.getByName(file.getString(path + ".effects." + id + ".type"));
                    int amplifier = file.getInt(path + ".effects." + id + ".amplifier");
                    int duration = file.getInt(path + ".effects." + id + ".duration");
                    boolean icon = file.contains(path + ".effects." + id + ".icon") && file.getBoolean(path + ".effects." + id + ".icon");
                    boolean particles = file.contains(path + ".effects." + id + ".particles") && file.getBoolean(path + ".effects." + id + ".particles");
                    boolean ambient = file.contains(path + ".effects." + id + ".ambient") && file.getBoolean(path + ".effects." + id + ".ambient");
                    effects.add(new PotionEffect(type, duration, amplifier, ambient, particles, icon));
                }

                item.addPotionEffects(false, effects);

                if (item.getMeta() instanceof PotionMeta && file.contains(path + ".color"))
                    item.setColor(file.getInt(path + ".color"));
            }
        }

        if (item.getMeta() instanceof TropicalFishBucketMeta) {
            if (file.contains("tropical-fish.body-color"))
                item.setTropicalFishBodyColor(DyeColor.valueOf(file.getString("tropical-fish.body-color")));
            if (file.contains("tropical-fish.pattern"))
                item.setTropicalFishPattern(TropicalFish.Pattern.valueOf(file.getString("tropical-fish.pattern")));
            if (file.contains("tropical-fish.pattern-color"))
                item.setTropicalFishPatternColor(DyeColor.valueOf(file.getString("tropical-fish.pattern-color")));
        }

        return item.build();
    }

    /**
     * Creates an ItemStack from a Base64 String.
     * @param file The file to read from.
     * @param path The path to get the data from.
     * @return An array of deserialized ItemStacks.
     */
    public static ItemStack[] fromBase64(YMLFile file, String path) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(file.getString(path)));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}