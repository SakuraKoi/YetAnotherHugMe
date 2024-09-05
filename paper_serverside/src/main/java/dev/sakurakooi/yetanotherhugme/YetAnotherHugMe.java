package dev.sakurakooi.yetanotherhugme;

import dev.sakurakooi.yetanotherhugme.listeners.HandshakeListener;
import dev.sakurakooi.yetanotherhugme.network.HugMeHandshakeHandler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.UUID;

public final class YetAnotherHugMe extends JavaPlugin {
    @Getter
    private static YetAnotherHugMe instance;

    @Getter
    private final HashSet<UUID> modInstalledPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new HandshakeListener(), this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "yetanotherhugme:handshake", new HugMeHandshakeHandler());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
