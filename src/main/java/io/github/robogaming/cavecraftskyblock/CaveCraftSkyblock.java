package io.github.robogaming.cavecraftskyblock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public final class CaveCraftSkyblock extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults(true);

        getServer().getLogger().info("Startup complete!");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new VoidGen();
    }

    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        return false;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}