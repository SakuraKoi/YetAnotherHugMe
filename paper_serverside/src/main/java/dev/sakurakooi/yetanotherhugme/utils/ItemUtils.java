package dev.sakurakooi.yetanotherhugme.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class ItemUtils {
    private static NamespacedKey namespacedKey = new NamespacedKey("yetanotherhugme", "hug_ticket");
    public static ItemStack createTicket() {
        ItemStack itemStack = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setEnchantmentGlintOverride(true);
        meta.setMaxStackSize(1);
        meta.displayName(Component.text("Hug Ticket").color(TextColor.color(0xff4081)).decorate(TextDecoration.BOLD));
        meta.lore(Arrays.asList(
                Component.text("Right click to hug someone!").color(TextColor.color(0xc6ff00)),
                Component.text("You can only use this ticket once.")));
        meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.BOOLEAN, true);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static boolean isHugTicket(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer().has(namespacedKey, PersistentDataType.BOOLEAN);
    }
}
