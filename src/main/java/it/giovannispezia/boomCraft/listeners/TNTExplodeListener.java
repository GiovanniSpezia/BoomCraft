package it.giovannispezia.boomCraft.listeners;

import it.giovannispezia.boomCraft.BoomCraft;
import it.giovannispezia.boomCraft.enums.ExplosionType;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class TNTExplodeListener implements Listener {

    private final BoomCraft plugin;

    public TNTExplodeListener(BoomCraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onExplode(@NotNull EntityExplodeEvent e) {
        if (!(e.getEntity() instanceof TNTPrimed tnt)) return;

        NamespacedKey key = plugin.getTNTManager().getKey();
        if (!tnt.getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;

        String typeName = tnt.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        ExplosionType type = ExplosionType.fromString(typeName);
        if (type == null) {
            // Se il tipo non è valido, fallback a comportamento vanilla
            return;
        }
        e.setCancelled(true);
        plugin.getExplosionManager().createExplosion(tnt.getLocation(), type, 4);
    }
}