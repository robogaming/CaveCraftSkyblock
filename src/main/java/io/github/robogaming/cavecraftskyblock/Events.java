package io.github.robogaming.cavecraftskyblock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

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
                player.sendMessage(ChatColor.MAGIC.toString() + ChatColor.RED + "[]" + ChatColor.RESET + ChatColor.RED + "You died and lost half of your money.");
                player.setHealth(player.getMaxHealth());
                player.setFoodLevel(20);
                player.teleport(player.getWorld().getSpawnLocation());
            }
        }
    }
}
