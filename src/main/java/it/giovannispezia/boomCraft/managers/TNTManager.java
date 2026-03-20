package it.giovannispezia.boomCraft.managers;

import it.giovannispezia.boomCraft.BoomCraft;
import it.giovannispezia.boomCraft.enums.ExplosionType;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;


public class TNTManager {

    private final NamespacedKey key;

    public TNTManager(BoomCraft plugin) {
        if (plugin == null) throw new IllegalArgumentException("Plugin instance cannot be null");
        this.key = new NamespacedKey(plugin, "tnt-type");
    }

    // 🔥 CREA TNT CUSTOM
    public ItemStack createTNT(@NotNull ExplosionType type) {
        if (type == null) throw new IllegalArgumentException("ExplosionType cannot be null");
        ItemStack item = new ItemStack(Material.TNT);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        // Utilizza API moderne per display name e lore
        net.kyori.adventure.text.Component displayName;
        java.util.List<net.kyori.adventure.text.Component> lore;
        switch (type) {
            case NORMAL -> {
                displayName = net.kyori.adventure.text.Component.text("Normal TNT").color(net.kyori.adventure.text.format.NamedTextColor.WHITE).decorate(net.kyori.adventure.text.format.TextDecoration.BOLD);
                lore = java.util.List.of(
                        net.kyori.adventure.text.Component.text("Esplosione standard").color(net.kyori.adventure.text.format.NamedTextColor.GRAY),
                        net.kyori.adventure.text.Component.text("Boom classico").color(net.kyori.adventure.text.format.NamedTextColor.DARK_GRAY)
                );
            }
            case FIRE -> {
                displayName = net.kyori.adventure.text.Component.text("Fire TNT 🔥").color(net.kyori.adventure.text.format.NamedTextColor.RED).decorate(net.kyori.adventure.text.format.TextDecoration.BOLD);
                lore = java.util.List.of(
                        net.kyori.adventure.text.Component.text("Esplosione infuocata").color(net.kyori.adventure.text.format.NamedTextColor.GRAY),
                        net.kyori.adventure.text.Component.text("Brucia tutto").color(net.kyori.adventure.text.format.NamedTextColor.DARK_GRAY)
                );
            }
            case SHOCKWAVE -> {
                displayName = net.kyori.adventure.text.Component.text("Shockwave TNT 🌪").color(net.kyori.adventure.text.format.NamedTextColor.AQUA).decorate(net.kyori.adventure.text.format.TextDecoration.BOLD);
                lore = java.util.List.of(
                        net.kyori.adventure.text.Component.text("Spinge via i giocatori").color(net.kyori.adventure.text.format.NamedTextColor.GRAY),
                        net.kyori.adventure.text.Component.text("Effetto onda d'urto").color(net.kyori.adventure.text.format.NamedTextColor.DARK_GRAY)
                );
            }
            default -> {
                displayName = net.kyori.adventure.text.Component.text("TNT Sconosciuta").color(net.kyori.adventure.text.format.NamedTextColor.WHITE).decorate(net.kyori.adventure.text.format.TextDecoration.BOLD);
                lore = java.util.List.of(
                        net.kyori.adventure.text.Component.text("Tipo non riconosciuto").color(net.kyori.adventure.text.format.NamedTextColor.GRAY)
                );
            }
        }
        meta.displayName(displayName);
        meta.lore(lore);
        // Salva tipo TNT
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, type.name());

        item.setItemMeta(meta);
        return item;
    }

    // 🔍 OTTIENI TIPO TNT DA ITEM
    public ExplosionType getType(@NotNull ItemStack item) {
        if (item.getType() != Material.TNT || !item.hasItemMeta()) return null;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        String value = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (value == null) return null;

        try {
            return ExplosionType.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public NamespacedKey getKey() {
        return key;
    }
}