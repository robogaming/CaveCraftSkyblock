package io.github.robogaming.cavecraftskyblock;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;

public final class CaveCraftSkyblock extends JavaPlugin {
    String prefix = ChatColor.BLUE + "[" + ChatColor.WHITE + "CaveCraft" + ChatColor.BLUE + "]" + ChatColor.WHITE + ": ";

    @Override
    public void onEnable() {
        // Plugin startup logic

        if (getConfig().getString("fileExists") != "true") {
            getServer().getLogger().info("Creating config...");
            getConfig().options().copyDefaults(true);
            saveConfig();
            saveDefaultConfig();
        }
        reloadConfig();

        getServer().getLogger().info("Registering events...");
        getServer().getPluginManager().registerEvents(new Events(), this);

        getServer().getLogger().info("Startup complete!");
    }

    public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {
        Player player = (Player)sender;
        String uuid = player.getUniqueId().toString();
        boolean success = false;

        switch (name) {
            case "is":
                if (Bukkit.getWorld(uuid) != null) {
                    player.setVelocity(new Vector(0,0,0));
                    player.teleport(new Location(Bukkit.getWorld(uuid), 0, 130, 0));
                    player.sendMessage(prefix + "You have been sent to your island.");
                } else {
                    if (Bukkit.getWorld(uuid) != null) Bukkit.getWorld(uuid).getWorldFolder().delete();
                    player.getInventory().clear();
                    WorldCreator creator = new WorldCreator(uuid);
                    creator.generator(new VoidGen());
                    World island = Bukkit.createWorld(creator);
                    island.setSpawnLocation(0, 130, 0);
                    island.getBlockAt(new Location(island, 0, 100, 0)).setType(Material.GRASS);
                    island.generateTree(new Location(island, 0, 101, 0), TreeType.TREE);
                    player.sendMessage(prefix + "Your island was created.");
                    player.setVelocity(new Vector(0,0,0));
                    player.teleport(new Location(island, 0, 130, 0));
                    player.sendMessage(prefix + "You have been sent to your island.");
                }
                success = true;
                break;
            case "isreset":
                if (Bukkit.getWorld(uuid) != null) Bukkit.getWorld(uuid).getWorldFolder().delete();
                getConfig().set(player.getUniqueId() + ".money", 0);
                player.getInventory().clear();
                WorldCreator creator = new WorldCreator(uuid);
                creator.generator(new VoidGen());
                World island = Bukkit.createWorld(creator);
                island.setSpawnLocation(0, 130, 0);
                island.getBlockAt(new Location(island, 0, 100, 0)).setType(Material.GRASS);
                island.generateTree(new Location(island, 0, 101, 0), TreeType.TREE);
                player.sendMessage(prefix + "Your island was reset.");
                player.setVelocity(new Vector(0,0,0));
                player.teleport(new Location(island, 0, 130, 0));
                player.sendMessage(prefix + "You have been sent to your island.");
                success = true;
                break;
        }

        saveConfig();
        return success;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveConfig();
    }

    public static Location translate(Location loc, double x, double y, double z) {
        return new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z);
    }

    public static Location translateWhole(Location loc, int x, int y, int z) {
        return new Location(loc.getWorld(), (int)Math.round(loc.getX()) + x, (int)Math.round(loc.getY()) + y, (int)Math.round(loc.getZ()) + z);
    }
}