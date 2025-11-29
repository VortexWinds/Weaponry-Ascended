package com.vortexwinds.weaponryascended.weapon;

import com.vortexwinds.weaponryascended.weapons.BaseWeaponItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class MaceItem extends BaseWeaponItem {
    public MaceItem(Properties properties, float baseAttackDamage, float baseAttackSpeed, Ingredient repairIngredient) {
        super(properties, baseAttackDamage, baseAttackSpeed, repairIngredient);
    }

    @Override
    public boolean isTwoHanded() {
        return false; // One-handed weapon
    }

    @Override
    public float getAttackReachBonus() {
        return 0.5f;
    }

    @Override
    public void onHitEntity(LivingEntity target, LivingEntity attacker, ItemStack stack) {
        Level level = target.level();
        if (!level.isClientSide()) {
            // Maces specialize in breaking armor and shields
            float damage = getAttackDamage(stack);
            
            // Bonus damage against heavily armored targets
            if (target.getArmorValue() > 5) {
                damage *= 1.4f;
            }
            
            // Chance to destroy shields
            if (target instanceof Player player && player.isBlocking() && Math.random() < 0.3) {
                // Shield breaking
                player.blockBreakingCooldown = 0;
            }
            
            target.hurt(attacker.damageSources().playerAttack((Player) attacker), damage);
            
            // Stagger effect - maces are designed to break formations
            target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                net.minecraft.world.effect.MobEffects.WEAKNESS, 80, 1));
            
            // Concussion effect (short stun)
            if (stack.hasTag() && stack.getTag().getBoolean("concussion")) {
                target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.CONFUSION, 60, 0));
            }
        }
        super.onHitEntity(target, attacker, stack);
    }

    @Override
    public void onBlockDestroyed(ItemStack stack, net.minecraft.world.level.BlockState block, 
                                net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player) {
        // Maces are not designed for mining
        stack.setDamageValue(stack.getDamageValue() + 2);
        super.onBlockDestroyed(stack, block, level, player);
    }

    @Override
    public float getBlockBreakingEfficiency() {
        return 0.5f; // Poor for block breaking
    }

    @Override
    public boolean isCorrectToolForDrops(net.minecraft.world.level.BlockState state) {
        return false; // Maces don't affect blocks
    }
}