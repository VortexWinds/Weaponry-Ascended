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

public class CrossbowItem extends BaseRangedWeaponItem {
    private boolean loaded = false;

    public CrossbowItem(Properties properties, float baseDrawSpeed, float baseArrowSpeed, Ingredient repairIngredient) {
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
    public AbstractArrow createArrow(Level level, Vec3 start, Vec3 direction, float power, ItemStack crossbowStack, ArrowItem arrowItem) {
        // Crossbows fire bolts instead of arrows
        if (arrowItem instanceof net.minecraft.world.item.BoltItem) {
            net.minecraft.world.entity.projectile.Arrow bolt = new Arrow(level, (LivingEntity) null, arrowItem);
            bolt.setPos(start.x, start.y, start.z);
            return bolt;
        } else {
            // If no bolt available, create regular arrow but it's less effective
            Arrow arrow = new Arrow(level, (LivingEntity) null, arrowItem);
            arrow.setPos(start.x, start.y, start.z);
            return arrow;
        }
    }

    @Override
    public float getDrawTime() {
        return baseDrawSpeed * 1.5f; // Crossbows take longer to reload
    }

    @Override
    public float getArrowVelocity(float power) {
        return baseArrowSpeed * 1.2f * power; // Crossbows have higher velocity
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        super.onUsingTick(stack, player, count);
        // Crossbow charging logic
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        // Crossbow firing logic
        if (entityLiving instanceof Player player) {
            // Check if crossbow was fully charged
            int useTime = this.getUseDuration(stack) - timeLeft;
            if (useTime >= getDrawTime()) {
                // Fire crossbow
                ItemStack arrowItem = consumeAmmo(player, stack);
                if (!arrowItem.isEmpty()) {
                    AbstractArrow arrow = createArrow(level, player.getEyePosition(1.0F), 
                                                   player.getLookAngle(), 1.0F, stack, 
                                                   (ArrowItem) arrowItem.getItem());
                    fire(player, stack, arrow);
                    level.addFreshEntity(arrow);
                }
            }
        }
    }

    @Override
    public int getEnchantmentValue() {
        return 20; // Very high enchantability
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 150; // Crossbows are more durable than bows
    }
}