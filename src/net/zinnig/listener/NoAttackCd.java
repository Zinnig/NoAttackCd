package net.zinnig.listener;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NoAttackCd implements Listener {
    /**
     * onJoin and onRespawn execute if a player has joined/respawned.
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        resetCoolDown(p);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        resetCoolDown(p);
    }
    /**
    * @param p The Player whose base attack speed needs to be set to 100.
    */

    public void resetCoolDown(Player p) {
        p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(100D);
    }

    /**
     * Executes if a player has a weapon in their main hand.
     */
    @EventHandler
    public void onWeaponInHand(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();
        int itemID = e.getNewSlot();
        ItemStack item = p.getInventory().getItem(itemID);
        selectItem(item, p);
    }
    /**
     * Executes if a player clicks on something in their inventory.
     */
    @EventHandler
    public void onInvClick(InventoryClickEvent e) {

        try {
            ItemStack item = e.getCurrentItem();
            Player p = (Player) e.getWhoClicked();
            selectItem(item, p);
        } catch (NullPointerException ignored){
        }

    }
    /**
     * Executes if a player picks up an item.
     */
    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e){
        ItemStack item = e.getItem().getItemStack();
        Player p =  (Player) e.getEntity();
        selectItem(item, p);

    }
    /**
     * Selects the item and how much damage should be put on it.
     * @param item the itemstack of the item
     * @param p the player whose weapon/item needs to change
     */
    private void selectItem(ItemStack item, Player p) {
        try {
            String iName = item.getType().toString();
            ItemMeta iMeta = item.getItemMeta();
            switch (iName) {
                case "WOODEN_SHOVEL":
                case "GOLD_SHOVEL":
                    setAttackDamage(1, iMeta, item, p);
                    break;
                case "STONE_SHOVEL":
                    setAttackDamage(2, iMeta, item, p);
                    break;
                case "DIAMOND_SHOVEL":
                case "STONE_AXE":
                case "WOODEN_SWORD":
                case "GOLD_SWORD":
                    setAttackDamage(4, iMeta, item, p);
                    break;
                case "IRON_SHOVEL":
                case "GOLD_AXE":
                case "WOODEN_AXE":
                    setAttackDamage(3, iMeta, item, p);
                    break;
                case "IRON_AXE":
                case "STONE_SWORD":
                    setAttackDamage(5, iMeta, item, p);
                    break;
                case "DIAMOND_AXE":
                case "IRON_SWORD":
                    setAttackDamage(6, iMeta, item, p);
                    break;
                case "DIAMOND_SWORD":
                    setAttackDamage(7, iMeta, item, p);
                    break;
            }

        } catch (NullPointerException ignored) {

        }
    }

    /**
     *
     * @param amount the amount of damage that the weapon should have
     * @param iMeta the item meta of the item
     * @param item the itemstack of the item
     * @param p the player whose weapon/item's attack speed needs to be set.
     */
    private void setAttackDamage(double amount, ItemMeta iMeta, ItemStack item, Player p) {
        if (amount != 0) {
            AttributeModifier modifier = new AttributeModifier(p.getUniqueId(), "generic.attackDamage", amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            try {
                iMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                System.out.println(iMeta.getAttributeModifiers().size());
                item.setItemMeta(iMeta);
            } catch (IllegalArgumentException ex) {
                System.out.println("Modifier already applied!");
            }
        }
    }
}
