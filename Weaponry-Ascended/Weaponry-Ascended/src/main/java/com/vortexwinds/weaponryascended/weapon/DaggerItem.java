package com.vortexwinds.weaponryascended.weapon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class DaggerItem extends BaseWeaponItem {
    public DaggerItem(Properties properties, float baseAttackDamage, float baseAttackSpeed, Ingredient repairIngredient) {
        super(properties, baseAttackDamage, baseAttackSpeed, repairIngredient);
    }

    @Override
    public boolean isTwoHanded() {
        return false; // One-handed weapon
    }

    @Override
    public float getAttackReachBonus() {
        return 0.25f; // Very short reach
    }

    @Override
    public float getAttackSpeed(ItemStack stack) {
        float speed = super.getAttackSpeed(stack);
        // Daggers are very fast
        return speed * 1.5f;
    }

    @Override
    public void onHitEntity(LivingEntity target, LivingEntity attacker, ItemStack stack) {
        Level level = target.level();
        if (!level.isClientSide) {
            // Daggers specialize in critical hits and bleeding
            float damage = getAttackDamage(stack);
            
            // High critical hit chance
            if (Math.random() < 0.4) {
                damage *= 1.8f; // 80% bonus damage on crit
                
                // Critical hit effects
                target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.WEAKNESS, 100, 2));
            }
            
            // Bleeding effect - daggers cause wounds that don't heal easily
            if (stack.hasTag() && stack.getTag().getBoolean("bleeding")) {
                target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.WEAKNESS, 150, 1));
            }
            
            // Backstab bonus - extra damage when hitting from behind
            Vec3 attackerDir = attacker.getLookAngle();
            Vec3 targetDir = target.position().subtract(attacker.position()).normalize();
            double angle = Math.acos(attackerDir.dot(targetDir));
            
            if (angle > Math.PI * 0.75) { // Hit from behind
                damage *= 1.5f;
            }
            
            target.hurt(attacker.damageSources().playerAttack((Player) attacker), damage);
        }
        super.onHitEntity(target, attacker, stack);
    }

    @Override
    public void onBlockDestroyed(ItemStack stack, net.minecraft.world.level.BlockState block, 
                                net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player) {
        // Daggers are poor for block breaking
        stack.setDamageValue(stack.getDamageValue() + 3);
        super.onBlockDestroyed(stack, block, level, player);
    }

    @Override
    public float getBlockBreakingEfficiency() {
        return 0.3f; // Very poor for blocks
    }

    @Override
    public boolean isCorrectToolForDrops(net.minecraft.world.level.BlockState state) {
        return false; // Daggers don't affect blocks
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 50; // Daggers are fragile due to thin blade
    }

    @Override
    public int getEnchantmentValue() {
        return 25; // High enchantability for precision weapon
    }
}