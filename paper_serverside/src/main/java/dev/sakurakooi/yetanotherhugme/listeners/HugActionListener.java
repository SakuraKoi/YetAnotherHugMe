package dev.sakurakooi.yetanotherhugme.listeners;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import dev.sakurakooi.yetanotherhugme.HugAnimationEnum;
import dev.sakurakooi.yetanotherhugme.YetAnotherHugMe;
import dev.sakurakooi.yetanotherhugme.utils.ItemUtils;
import io.papermc.paper.entity.LookAnchor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.TitlePart;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;

public class HugActionListener implements Listener {
    private HashMap<Player, Pair<Player, Long>> hugRequestMap = new HashMap<>();
    private Table<Player, Player, Pair<Long, HugAnimationEnum>> hugMap = HashBasedTable.create();

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
                            Component.text("YetAnotherHugMe").color(NamedTextColor.AQUA).decorate(TextDecoration.UNDERLINED).clickEvent(ClickEvent.openUrl("https://github.com/SakuraKoi/YetAnotherHugMe")),
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

                hugRequestMap.put(player1, Pair.of(player2, System.currentTimeMillis()));
                player1.sendMessage(Component.join(JoinConfiguration.spaces(),
                        Component.text("YetAnotherHugMe").color(TextColor.color(0xff4081)).decorate(TextDecoration.BOLD),
                        Component.text(">>").color(TextColor.color(NamedTextColor.GRAY)),
                        Component.text("You have sent a hug request to ").color(NamedTextColor.AQUA),
                        Component.text(player2.getName()).color(NamedTextColor.RED)
                ));
                player2.sendTitlePart(TitlePart.SUBTITLE, Component.join(JoinConfiguration.spaces(),
                        Component.text(player1.getName()).color(NamedTextColor.AQUA),
                        Component.text(" wants to hug you, sneak & click to accept").color(NamedTextColor.GREEN)
                ));
            }
            if (player1.isSneaking() && hugRequestMap.containsKey(player2)) {
                if (checkIsInHug(player2, player1)) {
                    player1.sendMessage(Component.join(JoinConfiguration.spaces(),
                            Component.text("YetAnotherHugMe").color(TextColor.color(0xff4081)).decorate(TextDecoration.BOLD),
                            Component.text(">>").color(TextColor.color(NamedTextColor.GRAY)),
                            Component.text("Already in a hug").color(NamedTextColor.RED)
                    ));
                    return;
                }
                hugRequestMap.remove(player2);

                HugAnimationEnum hugAnimation = HugAnimationEnum.values()[(int) (Math.random() * HugAnimationEnum.values().length)];
                hugMap.put(player2, player1, Pair.of(System.currentTimeMillis(), hugAnimation));

                player2.lookAt(player1.getEyeLocation(), LookAnchor.EYES);

                @NotNull Vector senderLookVec = player2.getEyeLocation().getDirection();
                double horizontalDistance = Math.sqrt(senderLookVec.getX() * senderLookVec.getX() + senderLookVec.getZ() * senderLookVec.getZ());
                double offsetX = (senderLookVec.getX() / horizontalDistance) * 1.3;
                double offsetZ = (senderLookVec.getZ() / horizontalDistance) * 1.3;

                double receiverX = player2.getX() + offsetX;
                double receiverY = player2.getY();
                double receiverZ = player2.getZ() + offsetZ;

                player1.teleport(new Location(player1.getWorld(), receiverX, receiverY, receiverZ));
                player1.setSneaking(false);
                player1.lookAt(player2.getEyeLocation(), LookAnchor.EYES);
            }
        }

        // TODO handle player teleport & quit... etc
    }

    private boolean checkIsInHug(Player player1, Player player2) {
        Collection<Pair<Long, HugAnimationEnum>> values;
        if (hugMap.containsRow(player1)) {
            values = hugMap.row(player1).values();
        } else if (hugMap.containsColumn(player1)) {
            values = hugMap.column(player1).values();
        } else if (hugMap.containsRow(player2)) {
            values = hugMap.row(player2).values();
        } else if (hugMap.containsColumn(player2)) {
            values = hugMap.column(player2).values();
        } else {
            return false;
        }
        return values.stream().anyMatch(pair -> System.currentTimeMillis() - pair.getLeft() < (long) pair.getRight().getAnimationTicks() * (1000 / 20));
    }
}
