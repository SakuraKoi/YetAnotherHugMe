package dev.sakurakooi.yetanotherhugme.network;

import dev.sakurakooi.yetanotherhugme.YetAnotherHugMe;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class HugMeHandshakeHandler implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (YetAnotherHugMe.getInstance().getModInstalledPlayers().contains(player.getUniqueId()))
            return;
        YetAnotherHugMe.getInstance().getModInstalledPlayers().add(player.getUniqueId());
        YetAnotherHugMe.getInstance().getLogger().info("Player " + player.getName() + " handshake animation protocol!");
    }
}
