package io.github.robogaming.cavecraftskyblock;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class Events implements Listener {
    CaveCraftSkyblock plugin = (CaveCraftSkyblock) Bukkit.getPluginManager().getPlugin("CaveCraftSkyblock");
    String prefix = plugin.prefix;

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().get(player.getUniqueId() + ".joined") == null) {
            plugin.getConfig().set(player.getUniqueId() + ".money", 0);
            plugin.getConfig().set(player.getUniqueId() + ".joined", true);
        }
    }

    @EventHandler
    void onCrouch(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld() != Bukkit.getWorld("world")) {
            for (int x = -3; x < 3; x++) {
                for (int y = -3; y < 3; y++) {
                    for (int z = -3; z < 3; z++) {
                        Block b = player.getWorld().getBlockAt(plugin.translateWhole(player.getLocation(), x, y, z));
                        if (b.getType() == Material.SAPLING) {
                            b.setType(Material.AIR);
                            player.getWorld().generateTree(plugin.translateWhole(player.getLocation(), x, y, z), TreeType.TREE);
                            player.spawnParticle(Particle.VILLAGER_HAPPY, plugin.translateWhole(player.getLocation(), x, y, z), 30);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    void onHurt(EntityDamageEvent event) {
        Player player = (Player)event.getEntity();
        if (player != null) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
                return;
            }
            if (event.getDamage() >= player.getHealth()) {
                event.setCancelled(true);
                double money = plugin.getConfig().getDouble(player.getUniqueId() + ".money");
                plugin.getConfig().set(player.getUniqueId() + ".money", Math.floor(money/2));
                player.sendMessage("&k" + ChatColor.RED + "F" + ChatColor.RESET + ChatColor.RED + " You died and lost half of your money.");
                player.setHealth(player.getMaxHealth());
                player.setFoodLevel(20);
                player.teleport(player.getWorld().getSpawnLocation());
            }
        }
    }
}
