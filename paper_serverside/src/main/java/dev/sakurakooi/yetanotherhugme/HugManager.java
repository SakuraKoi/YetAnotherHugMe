package dev.sakurakooi.yetanotherhugme;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.papermc.paper.entity.LookAnchor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class HugManager {
    private final HashMap<UUID, UUID> hugRequests = new HashMap<>();

    private final Table<UUID, UUID, HugAnimationEnum> huggingPlayer = HashBasedTable.create();

    public boolean isRequesting(Player player1, Player player2) {
        return Objects.equals(hugRequests.get(player1.getUniqueId()), player2.getUniqueId());
    }

    public void sendRequest(Player player1, Player player2) {
        hugRequests.put(player1.getUniqueId(), player2.getUniqueId());
        Bukkit.getScheduler().runTaskLater(YetAnotherHugMe.getInstance(), () -> {
            hugRequests.remove(player1.getUniqueId());
        }, 20 * 30);
    }

    public boolean isHugging(Player player) {
        return huggingPlayer.containsRow(player.getUniqueId()) || huggingPlayer.containsColumn(player.getUniqueId());
    }

    public void acceptRequest(Player player1, Player player2) {
        hugRequests.remove(player1.getUniqueId());
        HugAnimationEnum hugAnimation = HugAnimationEnum.values()[(int) (Math.random() * HugAnimationEnum.values().length)];
        doAcceptHug(player1, player2, hugAnimation);
    }

    private void doAcceptHug(Player player1, Player player2, HugAnimationEnum hugAnimation) {
        // TODO add player to table & start cleanup task
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

        // TODO start animation & send to nearby player
    }
}
