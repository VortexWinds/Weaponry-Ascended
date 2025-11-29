package com.vortexwinds.weaponryascended.weapon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WandItem extends BaseWeaponItem {
    public WandItem(Properties properties, float baseAttackDamage, float baseAttackSpeed, Ingredient repairIngredient) {
        super(properties, baseAttackDamage, baseAttackSpeed, repairIngredient);
    }

    @Override
    public boolean isTwoHanded() {
        return false; // One-handed weapon
    }

    @Override
    public float getAttackReachBonus() {
        return 0.5f; // Wands have short reach but are precise
    }

    @Override
    public void onHitEntity(LivingEntity target, LivingEntity attacker, ItemStack stack) {
        Level level = target.level();
        if (!level.isClientSide) {
            // Wands focus magic for precise attacks
            if (stack.hasTag() && stack.getTag().contains("magic_type")) {
                String magicType = stack.getTag().getString("magic_type");
                
                switch (magicType) {
                    case "healing":
                        // Healing magic - heal attacker
                        if (attacker instanceof Player player) {
                            player.heal(getAttackDamage(stack) * 2.0f);
                        }
                        break;
                    case "shadow":
                        // Shadow magic - apply darkness
                        target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                            net.minecraft.world.effect.MobEffects.DARKNESS, 200, 1));
                        break;
                    case "earth":
                        // Earth magic - slow and heavy attacks
                        target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                            net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 150, 2));
                        break;
                    case "wind":
                        // Wind magic - knockback
                        Vec3 direction = target.position().subtract(attacker.position()).normalize();
                        target.knockback(1.5F, direction.x, direction.z);
                        break;
                }
            }
            
            // Base magical damage
            target.hurt(attacker.damageSources().playerAttack((Player) attacker), 
                      getAttackDamage(stack) * 0.8f); // Slightly less damage but more effects
        }
        super.onHitEntity(target, attacker, stack);
    }

    @Override
    public void onBlockDestroyed(ItemStack stack, net.minecraft.world.level.BlockState block, 
                                net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player) {
        // Wands are not designed for block breaking
        stack.setDamageValue(stack.getDamageValue() + 3);
        super.onBlockDestroyed(stack, block, level, player);
    }

    @Override
    public float getBlockBreakingEfficiency() {
        return 0.2f; // Very poor at breaking blocks
    }

    @Override
    public boolean isCorrectToolForDrops(net.minecraft.world.level.BlockState state) {
        return false; // Wands don't affect blocks
    }

    @Override
    public int getEnchantmentValue() {
        return 25; // Very high enchantability for magic items
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 75; // Wands are relatively fragile
    }
}