package com.vortexwinds.weaponryascended.weapon;

import com.vortexwinds.weaponryascended.weapons.BaseWeaponItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class WarGlaiveItem extends BaseWeaponItem {
    public WarGlaiveItem(Properties properties, float baseAttackDamage, float baseAttackSpeed, Ingredient repairIngredient) {
        super(properties, baseAttackDamage, baseAttackSpeed, repairIngredient);
    }

    @Override
    public boolean isTwoHanded() {
        return false; // One-handed weapon
    }

    @Override
    public float getAttackReachBonus() {
        return 1.0f; // Medium reach for glaives
    }

    @Override
    public void onHitEntity(LivingEntity target, LivingEntity attacker, ItemStack stack) {
        Level level = target.level();
        if (!level.isClientSide()) {
            // War Glaives excel at disarming and cutting through armor
            if (target instanceof Player player && Math.random() < 0.4) {
                // 40% chance to disarm target
                player.drop(player.getInventory().getSelected(), true);
            }
            
            // Increased damage against armored targets
            float damage = getAttackDamage(stack) * (1.0f + target.getArmorValue() * 0.1f);
            target.hurt(attacker.damageSources().playerAttack((Player) attacker), damage);
            
            // Bleeding effect
            if (stack.hasTag() && stack.getTag().getBoolean("bleeding")) {
                target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.WEAKNESS, 100, 1));
            }
        }
        super.onHitEntity(target, attacker, stack);
    }

    @Override
    public void onBlockDestroyed(ItemStack stack, net.minecraft.world.level.BlockState block, 
                                net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player) {
        // War Glaives are decent for light cutting work
        stack.setDamageValue(stack.getDamageValue() + 1);
        super.onBlockDestroyed(stack, block, level, player);
    }

    @Override
    public float getBlockBreakingEfficiency() {
        return 1.1f; // Slightly better than average
    }
}