package com.vortexwinds.weaponryascended.weapon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class ShieldItem extends BaseDefensiveItem {
    public ShieldItem(Properties properties, float baseDefense, int maxDurability, Ingredient repairIngredient) {
        super(properties, baseDefense, maxDurability, repairIngredient);
    }

    @Override
    public boolean isTwoHanded() {
        return false; // One-handed defensive item
    }

    @Override
    public SlotRestriction getSlotRestriction() {
        return SlotRestriction.OFFHAND_ONLY;
    }

    @Override
    public void onDefensiveUse(LivingEntity user, ItemStack stack) {
        // Shields primarily work through blocking mechanics
        // Additional defensive abilities could be added here
        Level level = user.level();
        if (!level.isClientSide && stack.hasTag() && stack.getTag().getBoolean("fortified")) {
            // Fortified shields provide extra protection
            user.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE, 100, 1));
        }
    }

    @Override
    public void onHit(LivingEntity user, Entity attacker, ItemStack stack) {
        Level level = user.level();
        if (!level.isClientSide) {
            // Calculate damage reduction
            float damage = attacker instanceof LivingEntity ? 
                ((LivingEntity) attacker).getAttackDamage() : 1.0f;
            
            float reducedDamage = Math.max(0.1f, damage - getDefenseValue());
            
            // Apply blocking bonus if user is actively blocking
            if (user instanceof Player player && player.isBlocking()) {
                // Perfect block mechanics could be implemented here
                reducedDamage *= 0.1f; // 90% damage reduction when actively blocking
                
                // Chance to reflect damage back to attacker
                if (stack.hasTag() && stack.getTag().getBoolean("reflective") && Math.random() < 0.3) {
                    attacker.hurt(player.damageSources().playerAttack(player), reducedDamage * 0.5f);
                }
            }
            
            // Shield durability damage
            stack.setDamageValue(stack.getDamageValue() + 2);
        }
    }

    @Override
    public float getDefenseValue() {
        return 5.0f; // High physical defense
    }

    @Override
    public int getEnchantmentValue() {
        return 15; // Moderate enchantability
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 200; // Shields are very durable
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == net.minecraft.world.item.Items.IRON_INGOT ||
               repair.getItem() == net.minecraft.world.item.Items.GOLD_INGOT ||
               repair.getItem() == net.minecraft.world.item.Items.DIAMOND;
    }
}