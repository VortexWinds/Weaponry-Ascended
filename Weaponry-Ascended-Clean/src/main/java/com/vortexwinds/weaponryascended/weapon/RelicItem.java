package com.vortexwinds.weaponryascended.weapon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class RelicItem extends BaseDefensiveItem {
    public RelicItem(Properties properties, float baseDefense, int maxDurability, Ingredient repairIngredient) {
        super(properties, baseDefense, maxDurability, repairIngredient);
    }

    @Override
    public boolean isTwoHanded() {
        return false; // One-handed defensive item
    }

    @Override
    public SlotRestriction getSlotRestriction() {
        return SlotRestriction.OFFHAND_ONLY;
    }

    @Override
    public void onDefensiveUse(LivingEntity user, ItemStack stack) {
        Level level = user.level();
        if (!level.isClientSide() && stack.hasTag() && stack.getTag().contains("relic_power")) {
            String relicPower = stack.getTag().getString("relic_power");
            
            switch (relicPower) {
                case "lifesteal":
                    // Steal life from nearby enemies
                    level.getEntitiesOfClass(LivingEntity.class, user.getBoundingBox().inflate(5.0))
                        .stream()
                        .filter(entity -> entity != user && entity.getTeam() != user.getTeam())
                        .forEach(entity -> {
                            if (Math.random() < 0.3) {
                                user.heal(2.0f);
                                entity.hurt(user.damageSources().playerAttack((Player) user), 1.0f);
                            }
                        });
                    break;
                case "warding":
                    // Apply warding effect
                    user.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                        net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE, 300, 2));
                    user.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                        net.minecraft.world.effect.MobEffects.FIRE_RESISTANCE, 300, 1));
                    break;
                case "fury":
                    // Increase attack power temporarily
                    user.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                        net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 200, 2));
                    break;
                case "wisdom":
                    // Increase experience gain and spell power
                    user.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                        net.minecraft.world.effect.MobEffects.HERO_OF_THE_VILLAGE, 300, 1));
                    break;
            }
            
            // Relic durability cost for activation
            stack.setDamageValue(stack.getDamageValue() + 3);
        }
    }

    @Override
    public void onHit(LivingEntity user, Entity attacker, ItemStack stack) {
        Level level = user.level();
        if (!level.isClientSide() && stack.hasTag() && stack.getTag().contains("relic_power")) {
            String relicPower = stack.getTag().getString("relic_power");
            
            switch (relicPower) {
                case "guardian":
                    // Guardian spirits protect from attacks
                    attacker.hurt(user.damageSources().playerAttack((Player) user), 
                                attacker instanceof LivingEntity ? 
                                ((LivingEntity) attacker).getAttackDamage() * 0.5f : 1.0f);
                    break;
                case "vengeance":
                    // Return part of the damage
                    float damage = attacker instanceof LivingEntity ? 
                        ((LivingEntity) attacker).getAttackDamage() : 1.0f;
                    attacker.hurt(user.damageSources().playerAttack((Player) user), damage * 0.3f);
                    break;
            }
            
            // Relic takes minor damage on being hit
            stack.setDamageValue(stack.getDamageValue() + 1);
        }
    }

    @Override
    public float getDefenseValue() {
        return 3.0f; // Moderate defense with special abilities
    }

    @Override
    public int getEnchantmentValue() {
        return 35; // Very high enchantability for magical relics
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 100; // Relics have moderate durability
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true; // Relics always have a magical glow
    }
}