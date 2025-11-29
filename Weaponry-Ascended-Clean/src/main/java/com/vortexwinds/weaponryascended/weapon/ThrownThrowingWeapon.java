package com.vortexwinds.weaponryascended.weapon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownThrowingWeapon extends ThrowableItemProjectile {
    private final ThrowingWeaponItem weaponItem;

    public ThrownThrowingWeapon(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
        this.weaponItem = null;
    }

    public ThrownThrowingWeapon(Level level, LivingEntity thrower, ThrowingWeaponItem weaponItem) {
        super(weaponItem.asItem().getDefaultInstance().getItem(), thrower, level);
        this.weaponItem = weaponItem;
    }

    @Override
    protected Item getDefaultItem() {
        return weaponItem != null ? weaponItem : super.getDefaultItem();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity target = result.getEntity();
        Entity thrower = getOwner();
        
        if (thrower instanceof net.minecraft.world.entity.player.Player player) {
            // Apply weapon damage
            float damage = weaponItem != null ? weaponItem.getAttackDamage(new ItemStack(weaponItem)) : 1.0f;
            target.hurt(player.damageSources().playerAttack(player), damage);
            
            // Add weapon trait effects if applicable
            ItemStack weaponStack = new ItemStack(weaponItem);
            if (weaponStack.hasTag() && weaponStack.getTag().contains("traits")) {
                // Apply trait effects on hit
                // This could be expanded to handle various weapon traits
            }
        }
        
        // Handle weapon retrieval or consumption
        if (weaponItem != null && weaponItem.isRetrievable()) {
            // Weapon can be retrieved - drop it as an item
            target.level().addFreshEntity(new net.minecraft.world.entity.item.ItemEntity(
                target.level(), target.getX(), target.getY(), target.getZ(), 
                new ItemStack(weaponItem)));
        } else {
            // Weapon is consumed on throw
            this.discard();
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        
        if (weaponItem != null) {
            if (weaponItem.isRetrievable()) {
                // Drop the weapon where it hit
                level().addFreshEntity(new net.minecraft.world.entity.item.ItemEntity(
                    level(), getX(), getY(), getZ(), new ItemStack(weaponItem)));
            }
            // Remove the projectile
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        
        // Add spin effect for throwing weapons
        if (weaponItem != null) {
            this.setRotation(getYRot() + 20, getXRot() + 10);
        }
    }
}