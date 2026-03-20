package it.giovannispezia.boomCraft.listeners;

import it.giovannispezia.boomCraft.BoomCraft;
import it.giovannispezia.boomCraft.enums.ExplosionType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class TNTListener implements Listener {

    private final BoomCraft plugin;

    public TNTListener(BoomCraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onExplode(@NotNull EntityExplodeEvent e) {
        if (!(e.getEntity() instanceof TNTPrimed tnt)) return;

        var loc = tnt.getLocation();
        var world = loc.getWorld();
        if (world == null) return;

        // Prova a leggere il tipo custom dal PersistentDataContainer
        var key = plugin.getTNTManager().getKey();
        String typeName = tnt.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        ExplosionType type = ExplosionType.SHOCKWAVE;
        if (typeName != null) {
            ExplosionType parsed = ExplosionType.fromString(typeName);
            if (parsed != null) type = parsed;
        }

        e.setCancelled(true);
        plugin.getExplosionManager().createExplosion(loc, type, 4);
    }
}