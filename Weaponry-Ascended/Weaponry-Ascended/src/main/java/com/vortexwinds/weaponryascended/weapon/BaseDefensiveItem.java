package com.vortexwinds.weaponryascended.weapon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public abstract class BaseDefensiveItem extends Item {
    protected float baseDefense;
    protected int maxDurability;

    public BaseDefensiveItem(Properties properties, float baseDefense, int maxDurability, Ingredient repairIngredient) {
        super(properties.durability(maxDurability));
        this.baseDefense = baseDefense;
        this.maxDurability = maxDurability;
    }

    public enum SlotRestriction {
        OFFHAND_ONLY,
        MAINHAND_ONLY,
        EITHER_HAND
    }

    public abstract SlotRestriction getSlotRestriction();
    public abstract float getDefenseValue();
    public abstract void onDefensiveUse(LivingEntity user, ItemStack stack);
    public abstract void onHit(LivingEntity user, Entity attacker, ItemStack stack);

    @Override
    public boolean canEquip(ItemStack stack, net.minecraft.world.entity.EquipmentSlot armorType, Entity entity) {
        if (armorType != net.minecraft.world.entity.EquipmentSlot.OFFHAND) {
            return false;
        }
        
        SlotRestriction restriction = getSlotRestriction();
        return restriction == SlotRestriction.OFFHAND_ONLY || restriction == SlotRestriction.EITHER_HAND;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.hasTag() && stack.getTag().getBoolean("enchanted");
    }

    @Override
    public int getEnchantmentValue() {
        return 10; // Base enchantment value
    }

    public float getAttackDamage(ItemStack stack) {
        return 0.0f; // Defensive items don't deal damage in melee
    }

    public float getAttackSpeed(ItemStack stack) {
        return 0.0f; // Defensive items don't affect attack speed
    }

    public void onBlockBreak(LivingEntity user, ItemStack stack) {
        // Defensive items aren't used for block breaking
    }
}