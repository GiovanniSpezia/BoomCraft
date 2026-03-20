package it.giovannispezia.boomCraft.listeners;

import it.giovannispezia.boomCraft.BoomCraft;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class TNTPlaceListener implements Listener {

    private final BoomCraft plugin;

    public TNTPlaceListener(BoomCraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlace(@NotNull BlockPlaceEvent e) {
        ItemMeta meta = e.getItemInHand().getItemMeta();
        if (meta == null) return;

        NamespacedKey key = plugin.getTNTManager().getKey();
        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;

        String type = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (type == null) return;

        // Rimuove il blocco piazzato
        e.getBlockPlaced().setType(org.bukkit.Material.AIR);

        // Spawna la TNT custom
        TNTPrimed tnt = (TNTPrimed) e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.TNT);
        tnt.setFuseTicks(60);

        tnt.getPersistentDataContainer().set(key, PersistentDataType.STRING, type);
    }
}