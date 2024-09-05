package dev.sakurakooi.yetanotherhugme.listeners;

import dev.sakurakooi.yetanotherhugme.YetAnotherHugMe;
import dev.sakurakooi.yetanotherhugme.packet.S2CHugMeHandshakeReqPacket;
import dev.sakurakooi.yetanotherhugme.utils.NettySerializerWrapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HandshakeListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().sendPluginMessage(YetAnotherHugMe.getInstance(), S2CHugMeHandshakeReqPacket.ID,
                NettySerializerWrapper.writePacket(S2CHugMeHandshakeReqPacket.WRITER, new S2CHugMeHandshakeReqPacket(1)));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        YetAnotherHugMe.getInstance().getModInstalledPlayers().remove(e.getPlayer().getUniqueId());
    }
}
