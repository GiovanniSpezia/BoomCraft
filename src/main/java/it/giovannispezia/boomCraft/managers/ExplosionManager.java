package it.giovannispezia.boomCraft.managers;

import it.giovannispezia.boomCraft.enums.ExplosionType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class ExplosionManager {

    public void createExplosion(@NotNull Location loc, @NotNull ExplosionType type, float power) {

        World world = loc.getWorld();
        if (world == null) return;

        float vanillaPower = Math.min(power, 10);

        world.createExplosion(loc, vanillaPower, false, true);

        if (power > 10) {

            int extra = (int) (power / 5);

            for (int i = 0; i < extra; i++) {

                double x = (Math.random() - 0.5) * 6;
                double y = (Math.random() - 0.5) * 4;
                double z = (Math.random() - 0.5) * 6;

                Location extraLoc = loc.clone().add(x, y, z);
                world.createExplosion(extraLoc, 4, false, true);
            }
        }

        // 🌪 SHOCKWAVE
        if (type == ExplosionType.SHOCKWAVE) {
            for (Entity entity : world.getNearbyEntities(loc, 6, 6, 6)) {

                if (entity instanceof Player target) {

                    Vector dir = target.getLocation().toVector().subtract(loc.toVector());

                    if (dir.lengthSquared() == 0) continue;

                    // Applica knockback solo se il player non è in modalità spettatore o creativa
                    if (target.getGameMode() == org.bukkit.GameMode.SURVIVAL || target.getGameMode() == org.bukkit.GameMode.ADVENTURE) {
                        target.setVelocity(dir.normalize().multiply(Math.max(1, power / 2)));
                    }
                }
            }
        }

        // 🔥 FIRE
        if (type == ExplosionType.FIRE) {
            int radius = Math.max(2, (int)power);
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -radius; dz <= radius; dz++) {
                        Location fireLoc = loc.clone().add(dx, dy, dz);
                        Block block = world.getBlockAt(fireLoc);
                        if (block.getType() == Material.AIR && block.getRelative(0, -1, 0).getType().isSolid()) {
                            block.setType(Material.FIRE);
                        }
                    }
                }
            }
        }

        world.spawnParticle(Particle.EXPLOSION, loc, 5);
        world.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);
    }
}