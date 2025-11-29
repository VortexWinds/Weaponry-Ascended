package com.vortexwinds.weaponryascended.weapon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class TotemItem extends BaseDefensiveItem {
    private boolean activated = false;
    private int activationCooldown = 0;

    public TotemItem(Properties properties, float baseDefense, int maxDurability, Ingredient repairIngredient) {
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
        if (!level.isClientSide() && !activated && activationCooldown <= 0) {
            if (stack.hasTag() && stack.getTag().contains("totem_power")) {
                String totemPower = stack.getTag().getString("totem_power");
                
                // Activate totem power
                activated = true;
                activationCooldown = 600; // 30 second cooldown (at 20 ticks per second)
                
                switch (totemPower) {
                    case "life":
                        // Heal user and nearby allies
                        user.heal(10.0f);
                        level.getEntitiesOfClass(LivingEntity.class, user.getBoundingBox().inflate(8.0))
                            .stream()
                            .filter(entity -> entity.getTeam() == user.getTeam() || entity == user)
                            .forEach(entity -> entity.heal(5.0f));
                        break;
                    case "death":
                        // Damage nearby enemies
                        level.getEntitiesOfClass(LivingEntity.class, user.getBoundingBox().inflate(8.0))
                            .stream()
                            .filter(entity -> entity != user && entity.getTeam() != user.getTeam())
                            .forEach(entity -> entity.hurt(user.damageSources().playerAttack((Player) user), 8.0f));
                        break;
                    case "protection":
                        // Create protective aura
                        level.getEntitiesOfClass(LivingEntity.class, user.getBoundingBox().inflate(8.0))
                            .stream()
                            .filter(entity -> entity.getTeam() == user.getTeam() || entity == user)
                            .forEach(entity -> entity.addEffect(new 
                                net.minecraft.world.effect.MobEffectInstance(
                                net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE, 400, 2)));
                        break;
                    case "warrior":
                        // Boost combat abilities
                        level.getEntitiesOfClass(LivingEntity.class, user.getBoundingBox().inflate(8.0))
                            .stream()
                            .filter(entity -> entity.getTeam() == user.getTeam() || entity == user)
                            .forEach(entity -> {
                                entity.addEffect(new 
                                    net.minecraft.world.effect.MobEffectInstance(
                                    net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 400, 2));
                                entity.addEffect(new 
                                    net.minecraft.world.effect.MobEffectInstance(
                                    net.minecraft.world.effect.MobEffects.SPEED, 400, 1));
                            });
                        break;
                }
                
                // Totem durability cost for activation
                stack.setDamageValue(stack.getDamageValue() + 5);
            }
        }
    }

    @Override
    public void onHit(LivingEntity user, Entity attacker, ItemStack stack) {
        Level level = user.level();
        if (!level.isClientSide() && stack.hasTag() && stack.getTag().contains("totem_power")) {
            String totemPower = stack.getTag().getString("totem_power");
            
            // Automatic protection when hit
            switch (totemPower) {
                case "warding":
                    // Automatic damage reduction
                    attacker.hurt(user.damageSources().playerAttack((Player) user), 
                                attacker instanceof LivingEntity ? 
                                ((LivingEntity) attacker).getAttackDamage() * 0.2f : 0.5f);
                    break;
                case "thorns":
                    // Reflect damage back
                    float damage = attacker instanceof LivingEntity ? 
                        ((LivingEntity) attacker).getAttackDamage() : 1.0f;
                    attacker.hurt(user.damageSources().playerAttack((Player) user), damage * 0.4f);
                    break;
            }
            
            // Minor damage from being hit
            stack.setDamageValue(stack.getDamageValue() + 1);
        }
        
        // Handle cooldowns
        if (activationCooldown > 0) {
            activationCooldown--;
            if (activationCooldown <= 0) {
                activated = false;
            }
        }
    }

    @Override
    public float getDefenseValue() {
        return 4.0f; // Good defense with special abilities
    }

    @Override
    public int getEnchantmentValue() {
        return 40; // Highest enchantability for powerful items
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 50; // Totems are consumed when fully activated
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true; // Totems always have magical properties
    }

    public boolean isActivated() {
        return activated;
    }

    public boolean isOnCooldown() {
        return activationCooldown > 0;
    }

    public int getCooldownRemaining() {
        return activationCooldown / 20; // Return in seconds
    }
}