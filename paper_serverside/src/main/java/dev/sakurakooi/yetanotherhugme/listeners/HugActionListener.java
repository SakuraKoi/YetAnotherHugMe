package dev.sakurakooi.yetanotherhugme.listeners;

import dev.sakurakooi.yetanotherhugme.YetAnotherHugMe;
import dev.sakurakooi.yetanotherhugme.utils.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HugActionListener implements Listener {
    @EventHandler
    public void onHugTicketClick(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Player player1) {
            Player player2 = e.getPlayer();
            if (ItemUtils.isHugTicket(player1.getActiveItem())) {
                if (!YetAnotherHugMe.getInstance().getModInstalledPlayers().contains(player1.getUniqueId())) {
                    player1.sendMessage(MiniMessage.miniMessage().deserialize(
                            "<#ff4081>YetAnotherHugMe<gray> >> <red>You needs install <aqua><click:open_url:https://github.com/SakuraKoi/YetAnotherHugMe>YetAnotherHugMe <red>mod to use Hug Ticket."
                    ));
                    return;
                }
                if (!YetAnotherHugMe.getInstance().getModInstalledPlayers().contains(player2.getUniqueId())) {
                    player1.sendMessage(MiniMessage.miniMessage().deserialize(
                            "<#ff4081>YetAnotherHugMe<gray> >> <red>The player you want to hug did not install <aqua>YetAnotherHugMe <red>mod yet."
                    ));
                    player2.sendMessage(MiniMessage.miniMessage().deserialize(
                            "<#ff4081>YetAnotherHugMe<gray> >> <yellow><player_name> <red>wants to hug you, but you did not install <aqua><click:open_url:https://github.com/SakuraKoi/YetAnotherHugMe>YetAnotherHugMe <red>mod yet.",
                            Placeholder.component("player_name", Component.text(player1.getName()))
                    ));
                    return;
                }

                if (YetAnotherHugMe.getHugManager().isRequesting(player1, player2)) {
                    player1.sendMessage(MiniMessage.miniMessage().deserialize(
                            "<#ff4081>YetAnotherHugMe<gray> >> <red>You have already sent a hug request to <aqua><player_name> <red>recently.",
                            Placeholder.component("player_name", Component.text(player2.getName()))
                    ));
                    return;
                }

                YetAnotherHugMe.getHugManager().sendRequest(player1, player2);

                player1.sendMessage(MiniMessage.miniMessage().deserialize(
                        "<#ff4081>YetAnotherHugMe<gray> >> <green>You have sent a hug request to <yellow><player_name>",
                        Placeholder.component("player_name", Component.text(player2.getName()))
                ));
                player2.sendTitlePart(TitlePart.SUBTITLE, MiniMessage.miniMessage().deserialize(
                        "<aqua><player_name> <green>wants to hug you, sneak & click to accept",
                        Placeholder.component("player_name", Component.text(player1.getName()))
                ));
            }
            if (player1.isSneaking() && YetAnotherHugMe.getHugManager().isRequesting(player2, player1)) {
                if (YetAnotherHugMe.getHugManager().isHugging(player2) || YetAnotherHugMe.getHugManager().isHugging(player1)) {
                    player1.sendMessage(MiniMessage.miniMessage().deserialize(
                            "<#ff4081>YetAnotherHugMe<gray> >> <red>Already in a hug"
                    ));
                    return;
                }

                YetAnotherHugMe.getHugManager().acceptRequest(player2, player1);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (!YetAnotherHugMe.getInstance().getModInstalledPlayers().contains(e.getPlayer().getUniqueId()))
            return;
        if (e.getFrom().getBlock().equals(e.getTo().getBlock()))
            return;
        if (YetAnotherHugMe.getHugManager().isHugging(e.getPlayer())) {
            YetAnotherHugMe.getHugManager().cancelHug(e.getPlayer());
        }
        // TODO check is any nearby player hugging and haven't sent animation -> send animation packet & add sent record
        // TODO check is any nearby hugging player out of range ->  remove sent record
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (!YetAnotherHugMe.getInstance().getModInstalledPlayers().contains(e.getPlayer().getUniqueId()))
            return;
        if (YetAnotherHugMe.getHugManager().isHugging(e.getPlayer())) {
            YetAnotherHugMe.getHugManager().cancelHug(e.getPlayer());
        }
    }
}
