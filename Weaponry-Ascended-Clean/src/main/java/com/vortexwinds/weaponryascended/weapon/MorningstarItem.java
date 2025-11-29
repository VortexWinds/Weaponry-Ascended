package com.vortexwinds.weaponryascended.weapon;

import com.vortexwinds.weaponryascended.weapons.BaseWeaponItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class MorningstarItem extends BaseWeaponItem {
    public MorningstarItem(Properties properties, float baseAttackDamage, float baseAttackSpeed, Ingredient repairIngredient) {
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
        if (!level.isClientSide()) {
            // Morningstars cause massive armor penetration damage
            float armorDamage = getAttackDamage(stack) * 1.3f;
            target.hurt(attacker.damageSources().playerAttack((Player) attacker), armorDamage);
            
            // Add stagger effect
            if (stack.hasTag() && stack.getTag().getBoolean("stagger")) {
                // Stagger logic - temporary disable attacking
                target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.WEAKNESS, 100, 1));
            }
            
            // Chance to cause critical hits against heavily armored targets
            if (target.getArmorValue() > 5 && Math.random() < 0.3) {
                target.hurt(attacker.damageSources().playerAttack((Player) attacker), 
                          getAttackDamage(stack) * 0.5f); // Extra critical damage
            }
        }
        super.onHitEntity(target, attacker, stack);
    }

    @Override
    public float getBlockBreakingEfficiency() {
        return 0.8f; // Not great for breaking blocks due to spike design
    }
}