package com.vortexwinds.weaponryascended.weapon;

import com.vortexwinds.weaponryascended.weapons.BaseWeaponItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ThrowingWeaponItem extends BaseWeaponItem {
    private final boolean isRetrievable;

    public ThrowingWeaponItem(Properties properties, float baseAttackDamage, float baseAttackSpeed, 
                             Ingredient repairIngredient, boolean isRetrievable) {
        super(properties, baseAttackDamage, baseAttackSpeed, repairIngredient);
        this.isRetrievable = isRetrievable;
    }

    @Override
    public boolean isTwoHanded() {
        return false; // One-handed weapon
    }

    @Override
    public float getAttackReachBonus() {
        return -0.5f; // Shorter melee reach to encourage throwing
    }

    @Override
    public void onHitEntity(LivingEntity target, LivingEntity attacker, ItemStack stack) {
        // When used in melee, throwing weapons are weaker
        Level level = target.level();
        if (!level.isClientSide()) {
            float damage = getAttackDamage(stack) * 0.7f; // Reduced melee damage
            target.hurt(attacker.damageSources().playerAttack((Player) attacker), damage);
        }
        super.onHitEntity(target, attacker, stack);
    }

    @Override
    public void onBlockDestroyed(ItemStack stack, net.minecraft.world.level.BlockState block, 
                                net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player) {
        // Throwing weapons are not designed for mining
        stack.setDamageValue(stack.getDamageValue() + 2);
        super.onBlockDestroyed(stack, block, level, player);
    }

    @Override
    public float getBlockBreakingEfficiency() {
        return 0.4f; // Poor for block breaking
    }

    public boolean isRetrievable() {
        return isRetrievable;
    }

    public ThrownThrowingWeapon createThrownWeapon(Level level, Vec3 start, Vec3 direction, 
                                                  float power, LivingEntity thrower) {
        ThrownThrowingWeapon projectile = new ThrownThrowingWeapon(level, thrower, this);
        projectile.setPos(start.x, start.y, start.z);
        
        // Apply throwing velocity
        Vec3 velocity = direction.scale(power * 1.5f);
        projectile.setDeltaMovement(velocity);
        
        return projectile;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return isRetrievable ? 30 : 15; // Retrievable weapons last longer
    }
}