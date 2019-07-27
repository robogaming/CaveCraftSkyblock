package io.github.robogaming.ccrpg;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class CCRpg extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadConfig();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    void receiveItem(InventoryPickupItemEvent evt) {
        if (!checkItem(evt.getItem().getItemStack())) evt.setCancelled(true);
    }

    public boolean checkItem(ItemStack s) {
        return (s.getItemMeta().getLore().get(0).charAt(0) == '$');
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
