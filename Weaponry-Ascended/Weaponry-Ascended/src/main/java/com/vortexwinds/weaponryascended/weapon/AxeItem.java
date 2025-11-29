package com.vortexwinds.weaponryascended.weapon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class AxeItem extends BaseWeaponItem {
    public AxeItem(Properties properties, float baseAttackDamage, float baseAttackSpeed, Ingredient repairIngredient) {
        super(properties, baseAttackDamage, baseAttackSpeed, repairIngredient);
    }

    @Override
    public boolean isTwoHanded() {
        return false; // One-handed weapon
    }

    @Override
    public float getAttackReachBonus() {
        return 0.75f;
    }

    @Override
    public void onHitEntity(LivingEntity target, LivingEntity attacker, ItemStack stack) {
        Level level = target.level();
        if (!level.isClientSide) {
            // Axes excel at breaking shields and dealing bonus damage to wood-based materials
            if (target instanceof Player player && player.isBlocking()) {
                // Shield breaking bonus
                player.hurt(attacker.damageSources().playerAttack(player), 
                          getAttackDamage(stack) * 1.2f);
                player.blockBreakingCooldown = 0;
            } else {
                target.hurt(attacker.damageSources().playerAttack((Player) attacker), 
                          getAttackDamage(stack));
            }
            
            // Bleeding effect from sharp blade
            if (stack.hasTag() && stack.getTag().getBoolean("bleeding")) {
                target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.WEAKNESS, 60, 1));
            }
        }
        super.onHitEntity(target, attacker, stack);
    }

    @Override
    public float getMiningSpeedMultiplier(BlockState state) {
        // Axes are excellent for wood
        if (state.is(Blocks.LOG) || state.is(Blocks.LOG_2) || 
            state.is(Blocks.WOOD) || state.is(Blocks.WOOD_2) ||
            state.getBlock().asItem().toString().contains("wood")) {
            return 1.5f;
        }
        return 0.9f; // Slightly slower on other materials
    }

    @Override
    public void onBlockDestroyed(ItemStack stack, BlockState block, Level level, net.minecraft.world.entity.player.Player player) {
        // Axes consume durability based on material
        int durabilityLoss = block.getMaterial().isWood() ? 1 : 2;
        stack.setDamageValue(stack.getDamageValue() + durabilityLoss);
        super.onBlockDestroyed(stack, block, level, player);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
        return state.getMaterial().isWood();
    }
}