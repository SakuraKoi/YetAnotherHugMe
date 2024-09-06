package dev.sakurakooi.yetanotherhugme;

import dev.sakurakooi.yetanotherhugme.listeners.HandshakeListener;
import dev.sakurakooi.yetanotherhugme.listeners.HugActionListener;
import dev.sakurakooi.yetanotherhugme.network.HugMeHandshakeHandler;
import dev.sakurakooi.yetanotherhugme.utils.ItemUtils;
import io.papermc.paper.event.server.ServerResourcesReloadedEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.UUID;

public final class YetAnotherHugMe extends JavaPlugin implements Listener {
    private final NamespacedKey RECIPE_KEY = new NamespacedKey(this, "hug_ticket");

    @Getter
    private static YetAnotherHugMe instance;

    @Getter
    private final HashSet<UUID> modInstalledPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new HandshakeListener(), this);
        Bukkit.getPluginManager().registerEvents(new HugActionListener(), this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "yetanotherhugme:handshake", new HugMeHandshakeHandler());
        registerRecipe();
    }

    private void registerRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(RECIPE_KEY, ItemUtils.createTicket());
        recipe.shape("BBB", "WWW", "PPP");
        recipe.setIngredient('B', Material.LIGHT_BLUE_WOOL);
        recipe.setIngredient('W', Material.WHITE_WOOL);
        recipe.setIngredient('P', Material.PINK_WOOL);
        recipe.setGroup("HugMe");
        Bukkit.getServer().addRecipe(recipe);
    }

    private void unregisterRecipe() {
        Bukkit.getServer().removeRecipe(RECIPE_KEY, true);
    }

    @Override
    public void onDisable() {
        unregisterRecipe();
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasDiscoveredRecipe(RECIPE_KEY)) {
            e.getPlayer().discoverRecipe(RECIPE_KEY);
        }
    }

    @EventHandler
    public void onServerResourcesReload(ServerResourcesReloadedEvent event) {
        unregisterRecipe();
        registerRecipe();
    }
}
