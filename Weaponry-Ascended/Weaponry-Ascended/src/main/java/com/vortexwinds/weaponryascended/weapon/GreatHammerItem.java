package com.vortexwinds.weaponryascended.weapon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class GreatHammerItem extends BaseWeaponItem {
    public GreatHammerItem(Properties properties, float baseAttackDamage, float baseAttackSpeed, Ingredient repairIngredient) {
        super(properties, baseAttackDamage, baseAttackSpeed, repairIngredient);
    }

    @Override
    public boolean isTwoHanded() {
        return true;
    }

    @Override
    public float getAttackReachBonus() {
        return 1.0f;
    }

    @Override
    public void onHitEntity(LivingEntity target, LivingEntity attacker, ItemStack stack) {
        Level level = target.level();
        if (!level.isClientSide) {
            // Great hammers have devastating impact damage with knockback
            target.knockback(2.0F, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
            
            // Area effect on solid blocks
            AABB area = new AABB(target.getX() - 2, target.getY() - 1, target.getZ() - 2, 
                               target.getX() + 2, target.getY() + 1, target.getZ() + 2);
            
            level.getEntitiesOfClass(LivingEntity.class, area, entity -> entity != attacker).forEach(entity -> {
                entity.hurt(attacker.damageSources().playerAttack((Player) attacker), 
                          getAttackDamage(stack) * 0.75f);
            });
            
            // Slow down the attacker slightly due to weight
            if (attacker instanceof Player player) {
                player.setSprinting(false);
            }
        }
        super.onHitEntity(target, attacker, stack);
    }

    @Override
    public void onBlockDestroyed(ItemStack stack, net.minecraft.world.level.BlockState block, 
                                net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player) {
        // Great hammers are very effective against stone but slow
        stack.setDamageValue(stack.getDamageValue() + 3);
        super.onBlockDestroyed(stack, block, level, player);
    }

    @Override
    public float getMiningSpeedMultiplier(net.minecraft.world.level.BlockState state) {
        return state.getMaterial().isSolid() ? 1.5f : 0.5f; // Fast on stone, slow on everything else
    }
}