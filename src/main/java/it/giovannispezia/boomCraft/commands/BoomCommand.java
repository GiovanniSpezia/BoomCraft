package it.giovannispezia.boomCraft.commands;

import it.giovannispezia.boomCraft.BoomCraft;
import it.giovannispezia.boomCraft.enums.ExplosionType;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BoomCommand implements CommandExecutor, TabCompleter {

    private final BoomCraft plugin;

    public BoomCommand(BoomCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return true;

        if (!player.hasPermission("boom.use")) {
            player.sendMessage("§cNon hai il permesso!");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§6=== BoomCraft ===");
            player.sendMessage("§e/boom <tipo>");
            player.sendMessage("§e/boom <tipo> <power>");
            player.sendMessage("§e/boom <tipo> <x> <y> <z>");
            player.sendMessage("§e/boom <tipo> <power> <x> <y> <z>");
            player.sendMessage("§e/boom give <tipo>");
            return true;
        }

        // GIVE
        if (args[0].equalsIgnoreCase("give")) {

            if (!player.hasPermission("boom.admin")) {
                player.sendMessage("§cNon hai il permesso!");
                return true;
            }

            if (args.length < 2) {
                player.sendMessage("§cUso: /boom give <tipo>");
                return true;
            }

            ExplosionType type = ExplosionType.fromString(args[1]);
            if (type == null) {
                player.sendMessage("§cTipo non valido!");
                return true;
            }

            player.getInventory().addItem(plugin.getTNTManager().createTNT(type));
            player.sendMessage("§aHai ricevuto TNT: " + type.name());
            return true;
        }

        ExplosionType type = ExplosionType.fromString(args[0]);
        if (type == null) {
            player.sendMessage("§cTipo non valido!");
            return true;
        }

        float power = 4f;
        Location loc = player.getLocation();

        try {
            if (args.length == 1) {
                // solo tipo → su player
                // Nessuna azione extra, parametri di default
            }

            else if (args.length == 2) {
                power = Float.parseFloat(args[1]);
            }

            else if (args.length == 4) {
                loc = parseLocation(player, args[1], args[2], args[3]);
            }

            else if (args.length == 5) {
                power = Float.parseFloat(args[1]);
                loc = parseLocation(player, args[2], args[3], args[4]);
            }

            else {
                player.sendMessage("§cUso: /boom <tipo> [power] [x y z]");
                return true;
            }

        } catch (NumberFormatException e) {
            player.sendMessage("§cNumero non valido!");
            return true;
        }

        plugin.getExplosionManager().createExplosion(loc, type, power);

        player.sendMessage("§aBoom!");
        player.sendMessage("§7Power: §f" + power);
        player.sendMessage("§7Coordinate: §f" +
                loc.getBlockX() + " " +
                loc.getBlockY() + " " +
                loc.getBlockZ());

        return true;
    }

    private Location parseLocation(Player player, String xArg, String yArg, String zArg) {

        double x = parseCoordinate(player.getLocation().getX(), xArg);
        double y = parseCoordinate(player.getLocation().getY(), yArg);
        double z = parseCoordinate(player.getLocation().getZ(), zArg);

        return new Location(player.getWorld(), x, y, z);
    }

    private double parseCoordinate(double base, String input) {
        if (input.equals("~")) return base;

        if (input.startsWith("~")) {
            if (input.length() == 1) return base;
            return base + Double.parseDouble(input.substring(1));
        }

        return Double.parseDouble(input);
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        List<String> list = new ArrayList<>();

        if (!(sender instanceof Player)) return list;

        if (args.length == 1) {
            list.add("normal");
            list.add("fire");
            list.add("shockwave");
            list.add("give");
            return list;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            list.add("normal");
            list.add("fire");
            list.add("shockwave");
            return list;
        }

        if (args.length == 2) {
            list.add("1");
            list.add("5");
            list.add("10");
            return list;
        }

        // Tab per coordinate precise e ondine
        if ((args.length == 3 || args.length == 4 || args.length == 5) && !args[0].equalsIgnoreCase("give")) {
            Player player = (Player) sender;
            Location loc = player.getLocation();
            switch (args.length) {
                case 3 -> {
                    list.add("~");
                    list.add(String.valueOf(loc.getBlockX()));
                }
                case 4 -> {
                    list.add("~");
                    list.add(String.valueOf(loc.getBlockY()));
                }
                case 5 -> {
                    list.add("~");
                    list.add(String.valueOf(loc.getBlockZ()));
                }
            }
            return list;
        }

        return list;
    }
}