package com.vortexwinds.weaponryascended.weapon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class StaffItem extends BaseWeaponItem {
    public StaffItem(Properties properties, float baseAttackDamage, float baseAttackSpeed, Ingredient repairIngredient) {
        super(properties, baseAttackDamage, baseAttackSpeed, repairIngredient);
    }

    @Override
    public boolean isTwoHanded() {
        return true;
    }

    @Override
    public float getAttackReachBonus() {
        return 2.0f; // Staves have very long reach for magic
    }

    @Override
    public void onHitEntity(LivingEntity target, LivingEntity attacker, ItemStack stack) {
        Level level = target.level();
        if (!level.isClientSide) {
            // Staves can channel magic through attacks
            if (stack.hasTag() && stack.getTag().contains("magic_type")) {
                String magicType = stack.getTag().getString("magic_type");
                
                switch (magicType) {
                    case "lightning":
                        // Lightning magic
                        target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                            net.minecraft.world.effect.MobEffects.WEAKNESS, 200, 2));
                        break;
                    case "frost":
                        // Frost magic  
                        target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                            net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 300, 3));
                        break;
                    case "fire":
                        // Fire magic
                        target.setSecondsOnFire(5);
                        break;
                    case "arcane":
                        // Arcane magic - mana drain
                        if (target instanceof Player player) {
                            // Drain player mana (if mod has mana system)
                        }
                        break;
                }
            }
        }
        super.onHitEntity(target, attacker, stack);
    }

    @Override
    public void onBlockDestroyed(ItemStack stack, net.minecraft.world.level.BlockState block, 
                                net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player) {
        // Staves are not designed for block breaking
        stack.setDamageValue(stack.getDamageValue() + 5); // Heavy durability cost
        super.onBlockDestroyed(stack, block, level, player);
    }

    @Override
    public float getBlockBreakingEfficiency() {
        return 0.1f; // Very poor at breaking blocks
    }

    @Override
    public boolean isCorrectToolForDrops(net.minecraft.world.level.BlockState state) {
        return false; // Staves don't affect blocks
    }
}