package dev.sakurakooi.yetanotherhugme.listeners;

import dev.sakurakooi.yetanotherhugme.YetAnotherHugMe;
import dev.sakurakooi.yetanotherhugme.utils.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.HashMap;

public class HugActionListener implements Listener {
    private HashMap<Player, Pair<Player, Long>> hugRequestMap = new HashMap<>();

    @EventHandler
    public void onHugTicketClick(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {
            Player player1 = (Player) e.getRightClicked();
            Player player2 = e.getPlayer();
            if (ItemUtils.isHugTicket(player1.getActiveItem())) {
                if (!YetAnotherHugMe.getInstance().getModInstalledPlayers().contains(player1.getUniqueId())) {
                    player1.sendMessage(Component.join(JoinConfiguration.spaces(),
                            Component.text("YetAnotherHugMe").color(TextColor.color(0xff4081)).decorate(TextDecoration.BOLD),
                            Component.text(">>").color(TextColor.color(NamedTextColor.GRAY)),
                            Component.text("You needs install").color(NamedTextColor.RED),
                            Component.text("YetAnotherHugMe").color(NamedTextColor.AQUA).decorate(TextDecoration.UNDERLINED).clickEvent(ClickEvent.openUrl("https://github.com/SakuraKoi/YetAnotherHugMe"))
                            Component.text("mod to use Hug Ticket.").color(NamedTextColor.RED)
                    ));
                    return;
                }
                if (!YetAnotherHugMe.getInstance().getModInstalledPlayers().contains(player2.getUniqueId())) {
                    player1.sendMessage(Component.join(JoinConfiguration.spaces(),
                            Component.text("YetAnotherHugMe").color(TextColor.color(0xff4081)).decorate(TextDecoration.BOLD),
                            Component.text(">>").color(TextColor.color(NamedTextColor.GRAY)),
                            Component.text("The player you want to hug did not install").color(NamedTextColor.RED),
                            Component.text("YetAnotherHugMe").color(NamedTextColor.AQUA).decorate(TextDecoration.UNDERLINED).clickEvent(ClickEvent.openUrl("https://github.com/SakuraKoi/YetAnotherHugMe")),
                            Component.text("mod yet.").color(NamedTextColor.RED)
                    ));
                    player2.sendMessage(Component.join(JoinConfiguration.spaces(),
                            Component.text("YetAnotherHugMe").color(TextColor.color(0xff4081)).decorate(TextDecoration.BOLD),
                            Component.text(">>").color(TextColor.color(NamedTextColor.GRAY)),
                            Component.text(player1.getName()).color(NamedTextColor.RED),
                            Component.text("wants to hug you, but you did not install").color(NamedTextColor.RED),
                            Component.text("YetAnotherHugMe").color(NamedTextColor.AQUA).decorate(TextDecoration.UNDERLINED).clickEvent(ClickEvent.openUrl("https://github.com/SakuraKoi/YetAnotherHugMe")),
                            Component.text("mod yet.").color(NamedTextColor.RED)
                    ));
                    return;
                }

                if (hugRequestMap.containsKey(player1) && hugRequestMap.get(player1).getLeft().equals(player2)) {
                    if (System.currentTimeMillis() - hugRequestMap.get(player1).getRight() < 30000) {
                        player1.sendMessage(Component.join(JoinConfiguration.spaces(),
                                Component.text("YetAnotherHugMe").color(TextColor.color(0xff4081)).decorate(TextDecoration.BOLD),
                                Component.text(">>").color(TextColor.color(NamedTextColor.GRAY)),
                                Component.text("You have already sent a hug request to ").color(NamedTextColor.RED),
                                Component.text(player2.getName()).color(NamedTextColor.AQUA),
                                Component.text(" recently.").color(NamedTextColor.RED)
                        ));
                        return;
                    }
                }

                // TODO send hug request
            }
            if (player1.isSneaking() && hugRequestMap.containsKey(player2)) {
                // TODO accept hug request
            }
        }
    }

    // TODO handle player teleport & quit... etc
}
