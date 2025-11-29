package com.vortexwinds.weaponryascended.weapon;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class BaseRangedWeaponItem extends BaseWeaponItem {
    protected float baseDrawSpeed;
    protected float baseArrowSpeed;

    public BaseRangedWeaponItem(Properties properties, float baseDrawSpeed, float baseArrowSpeed, Ingredient repairIngredient) {
        super(properties, 1.0f, 1.0f, repairIngredient); // Base damage/speed for ranged weapons are handled differently
        this.baseDrawSpeed = baseDrawSpeed;
        this.baseArrowSpeed = baseArrowSpeed;
    }

    public abstract AbstractArrow createArrow(Level level, Vec3 start, Vec3 direction, float power, ItemStack weaponStack, ArrowItem arrowItem);
    public abstract float getDrawTime();
    public abstract float getArrowVelocity(float power);

    public boolean hasAmmo(Player player, ItemStack weaponStack) {
        // Check if player has arrows in inventory or quiver
        return player.getInventory().countItem(item -> item.getItem() instanceof ArrowItem) > 0 ||
               player.getOffhandItem().getItem() instanceof ArrowItem;
    }

    public ItemStack consumeAmmo(Player player, ItemStack weaponStack) {
        // Consume one arrow from inventory
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof ArrowItem) {
                stack.shrink(1);
                if (stack.isEmpty()) {
                    player.getInventory().setItem(i, ItemStack.EMPTY);
                }
                return stack;
            }
        }
        
        // Check offhand
        ItemStack offhand = player.getOffhandItem();
        if (offhand.getItem() instanceof ArrowItem) {
            offhand.shrink(1);
            return offhand;
        }
        
        return ItemStack.EMPTY;
    }

    public boolean canFire(Player player, ItemStack weaponStack) {
        return hasAmmo(player, weaponStack) && !weaponStack.isEmpty() && 
               player.getAttackStrengthScale(0.5F) >= 1.0F;
    }

    public void fire(Player player, ItemStack weaponStack, AbstractArrow arrow) {
        // Apply weapon damage to arrow
        float weaponDamage = getAttackDamage(weaponStack);
        if (arrow instanceof net.minecraft.world.entity.projectile.Arrow) {
            ((net.minecraft.world.entity.projectile.Arrow) arrow).setBaseDamage(weaponDamage);
        }
        
        // Apply weapon durability cost
        weaponStack.setDamageValue(weaponStack.getDamageValue() + 1);
        
        // Consume ammo
        consumeAmmo(player, weaponStack);
    }
}