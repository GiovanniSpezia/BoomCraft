package it.giovannispezia.boomCraft;

import it.giovannispezia.boomCraft.commands.BoomCommand;
import it.giovannispezia.boomCraft.listeners.TNTExplodeListener;
import it.giovannispezia.boomCraft.listeners.TNTPlaceListener;
import it.giovannispezia.boomCraft.managers.ExplosionManager;
import it.giovannispezia.boomCraft.managers.TNTManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class BoomCraft extends JavaPlugin {

    private static BoomCraft instance;
    private ExplosionManager explosionManager;
    private TNTManager tntManager;

    private static final String BOOM_COMMAND = "boom";
    private static final String ENABLED_MSG = "§aBoomCraft attivo!";
    private static final String DISABLED_MSG = "§cBoomCraft disattivato!";

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        // Manager
        explosionManager = new ExplosionManager();
        tntManager = new TNTManager(this);

        // Comando /boom
        PluginCommand boomCmd = getCommand(BOOM_COMMAND);
        if (boomCmd != null) {
            BoomCommand boomCommand = new BoomCommand(this);
            boomCmd.setExecutor(boomCommand);
            boomCmd.setTabCompleter(boomCommand);
        } else {
            getLogger().severe("Comando /boom non trovato nel plugin.yml!");
        }

        // Listener TNT
        getServer().getPluginManager().registerEvents(new TNTPlaceListener(this), this);
        getServer().getPluginManager().registerEvents(new TNTExplodeListener(this), this);

        getLogger().info(ENABLED_MSG);
    }

    @Override
    public void onDisable() {
        getLogger().info(DISABLED_MSG);
    }

    public static BoomCraft getInstance() {
        return instance;
    }

    public ExplosionManager getExplosionManager() {
        return explosionManager;
    }

    public TNTManager getTNTManager() {
        return tntManager;
    }
}