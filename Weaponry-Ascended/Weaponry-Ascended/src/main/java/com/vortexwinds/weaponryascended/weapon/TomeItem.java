package com.vortexwinds.weaponryascended.weapon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class TomeItem extends BaseDefensiveItem {
    public TomeItem(Properties properties, float baseDefense, int maxDurability, Ingredient repairIngredient) {
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
        if (!level.isClientSide) {
            if (stack.hasTag() && stack.getTag().contains("magic_type")) {
                String magicType = stack.getTag().getString("magic_type");
                
                switch (magicType) {
                    case "healing":
                        // Healing spell
                        if (user instanceof Player player) {
                            player.heal(5.0f);
                        }
                        break;
                    case "protection":
                        // Temporary protection buff
                        user.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                            net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE, 200, 1));
                        break;
                    case "mana":
                        // Mana restoration (if implemented)
                        break;
                    case "clarity":
                        // Remove negative effects
                        user.removeAllEffects();
                        user.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                            net.minecraft.world.effect.MobEffects.NIGHT_VISION, 300, 0));
                        break;
                }
            }
            
            // Reduce tome durability when used
            stack.setDamageValue(stack.getDamageValue() + 1);
        }
    }

    @Override
    public void onHit(LivingEntity user, Entity attacker, ItemStack stack) {
        Level level = user.level();
        if (!level.isClientSide && stack.hasTag() && stack.getTag().contains("magic_type")) {
            String magicType = stack.getTag().getString("magic_type");
            
            switch (magicType) {
                case "fire":
                    // Fire reflection
                    attacker.setSecondsOnFire(3);
                    break;
                case "lightning":
                    // Lightning reflection
                    if (attacker instanceof LivingEntity) {
                        ((LivingEntity) attacker).addEffect(new 
                            net.minecraft.world.effect.MobEffectInstance(
                            net.minecraft.world.effect.MobEffects.WEAKNESS, 100, 2));
                    }
                    break;
                case "frost":
                    // Frost reflection
                    if (attacker instanceof LivingEntity) {
                        ((LivingEntity) attacker).addEffect(new 
                            net.minecraft.world.effect.MobEffectInstance(
                            net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 200, 2));
                    }
                    break;
            }
        }
        super.onHit(user, attacker, stack);
    }

    @Override
    public float getDefenseValue() {
        return 2.0f; // Low physical defense, high magical defense
    }

    @Override
    public int getEnchantmentValue() {
        return 30; // Very high enchantability for magical items
    }
}