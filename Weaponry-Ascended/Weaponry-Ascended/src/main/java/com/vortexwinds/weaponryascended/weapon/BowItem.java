package com.vortexwinds.weaponryascended.weapon;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BowItem extends BaseRangedWeaponItem {
    public BowItem(Properties properties, float baseDrawSpeed, float baseArrowSpeed, Ingredient repairIngredient) {
        super(properties, baseDrawSpeed, baseArrowSpeed, repairIngredient);
    }

    @Override
    public boolean isTwoHanded() {
        return true;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.hasTag() && stack.getTag().getBoolean("enchanted");
    }

    @Override
    public AbstractArrow createArrow(Level level, Vec3 start, Vec3 direction, float power, ItemStack bowStack, ArrowItem arrowItem) {
        Arrow arrow = new Arrow(level, (LivingEntity) null, arrowItem);
        arrow.setPos(start.x, start.y, start.z);
        
        // Apply bow traits to arrow
        if (bowStack.hasTag() && bowStack.getTag().contains("traits")) {
            // Add arrow modification based on bow traits
            // This could be expanded to add special effects to arrows
        }
        
        return arrow;
    }

    @Override
    public float getDrawTime() {
        return baseDrawSpeed;
    }

    @Override
    public float getArrowVelocity(float power) {
        return baseArrowSpeed * power;
    }

    @Override
    public int getEnchantmentValue() {
        return 15; // High enchantability for bows
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 100; // Bows have good durability
    }
}