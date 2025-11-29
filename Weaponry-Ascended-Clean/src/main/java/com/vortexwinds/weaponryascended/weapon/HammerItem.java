package com.vortexwinds.weaponryascended.weapon;

import com.vortexwinds.weaponryascended.weapons.BaseWeaponItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HammerItem extends BaseWeaponItem {
    public HammerItem(Properties properties, float baseAttackDamage, float baseAttackSpeed, Ingredient repairIngredient) {
        super(properties, baseAttackDamage, baseAttackSpeed, repairIngredient);
    }

    @Override
    public boolean isTwoHanded() {
        return false; // One-handed weapon
    }

    @Override
    public float getAttackReachBonus() {
        return 0.5f; // Short reach but devastating
    }

    @Override
    public void onHitEntity(LivingEntity target, LivingEntity attacker, ItemStack stack) {
        Level level = target.level();
        if (!level.isClientSide()) {
            // Hammers cause massive single-target damage
            float damage = getAttackDamage(stack) * 1.2f;
            
            // Extra damage to armored targets
            if (target.getArmorValue() > 0) {
                damage *= 1.3f;
            }
            
            target.hurt(attacker.damageSources().playerAttack((Player) attacker), damage);
            
            // Stagger effect
            target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                net.minecraft.world.effect.MobEffects.WEAKNESS, 50, 2));
            
            // Heavy knockback
            Vec3 direction = target.position().subtract(attacker.position()).normalize();
            target.knockback(1.5F, direction.x, direction.z);
        }
        super.onHitEntity(target, attacker, stack);
    }

    @Override
    public float getMiningSpeedMultiplier(BlockState state) {
        // Hammers excel at stone and ore breaking
        if (state.getMaterial().isSolid() && !state.getMaterial().isWood()) {
            return 1.5f; // Fast on stone/ore, slow on wood
        }
        return 0.7f;
    }

    @Override
    public void onBlockDestroyed(ItemStack stack, BlockState block, Level level, net.minecraft.world.entity.player.Player player) {
        // Hammers consume less durability on stone, more on wood
        int durabilityLoss = block.getMaterial().isSolid() && !block.getMaterial().isWood() ? 1 : 2;
        stack.setDamageValue(stack.getDamageValue() + durabilityLoss);
        super.onBlockDestroyed(stack, block, level, player);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
        return state.getMaterial().isSolid() && !state.getMaterial().isWood();
    }
}