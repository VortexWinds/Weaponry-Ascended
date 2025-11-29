package com.vortexwinds.weaponryascended.items;

import com.vortexwinds.weaponryascended.weapons.BaseWeaponItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

/**
 * Custom sword implementation that can be enhanced with traits
 * and customized with different materials and components.
 */
public class CustomSwordItem extends BaseWeaponItem {
    
    public CustomSwordItem(WeaponTier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }
    
    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        // Allow repair with the base material or enhanced components
        return switch (tier) {
            case WOOD -> repair.getItem() == net.minecraft.world.item.Items.STICK;
            case STONE -> repair.getItem() == net.minecraft.world.item.Items.COBBLESTONE;
            case IRON -> repair.getItem() == net.minecraft.world.item.Items.IRON_INGOT;
            case GOLD -> repair.getItem() == net.minecraft.world.item.Items.GOLD_INGOT;
            case DIAMOND -> repair.getItem() == net.minecraft.world.item.Items.DIAMOND;
            case NETHERITE -> repair.getItem() == net.minecraft.world.item.Items.NETHERITE_INGOT;
        } || repair.getItem() == getRegistryName().getItem("enhanced_component");
    }
    
    @Override
    public boolean hurtEnemy(ItemStack stack, net.minecraft.world.entity.LivingEntity target, net.minecraft.world.entity.LivingEntity attacker) {
        // Apply weapon traits when hitting an enemy
        if (attacker instanceof Player player) {
            WeaponUseContext context = new WeaponUseContext(player, stack, true, false, false);
            applyWeaponTraits(player, stack, context);
            
            // Get weapon traits for hit effects
            if (stack.hasTag() && stack.getTag().contains("weaponTraits", net.minecraft.nbt.Tag.TAG_LIST)) {
                EntityHitResult hitResult = new EntityHitResult(target);
                onHitEntity(player, stack, hitResult, context);
            }
        }
        
        return super.hurtEnemy(stack, target, attacker);
    }
    
    @Override
    public void onDestroyed(net.minecraft.world.item.ItemStack stack, net.minecraft.world.entity.LivingEntity entity, 
                           net.minecraft.world.damagesource.DamageSource damageSource) {
        super.onDestroyed(stack, entity, damageSource);
        
        // Apply durability protection from traits when weapon breaks
        if (entity instanceof Player player) {
            WeaponUseContext context = new WeaponUseContext(player, stack, false, false, false);
            float durabilityProtection = getDurabilityProtection(player, stack, context);
            
            // This would be used to reduce or prevent weapon destruction in actual implementation
            if (durabilityProtection < 1.0F) {
                // Apply additional durability protection logic here
            }
        }
    }
    
    @Override
    public void postHurt(net.minecraft.world.item.ItemStack stack, net.minecraft.world.entity.LivingEntity target, 
                        net.minecraft.world.entity.LivingEntity attacker) {
        super.postHurt(stack, target, attacker);
        
        // Apply post-hit effects from traits
        if (attacker instanceof Player player) {
            WeaponUseContext context = new WeaponUseContext(player, stack, true, false, false);
            
            // Apply traits that have post-hit effects
            getWeaponTraits(stack).forEach(trait -> {
                trait.applyEffect(player, stack, context);
            });
        }
    }
    
    @Override
    public int getEnchantmentValue() {
        return tier.getEnchantmentValue();
    }
    
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        // Allow custom enchantments on custom weapons
        if (enchantment == Enchantments.SHARPNESS || 
            enchantment == Enchantments.UNBREAKING ||
            enchantment == Enchantments.KNOCKBACK ||
            enchantment == Enchantments.FIRE_ASPECT) {
            return true;
        }
        
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }
    
    /**
     * Calculates the total damage output including weapon traits
     */
    public float getTotalDamage(Player player, ItemStack stack, WeaponUseContext context) {
        float baseDamage = getDamage() + 1.0F; // Base sword damage
        float traitModifier = 1.0F;
        
        // Apply damage modifiers from all traits
        for (WeaponTrait trait : getWeaponTraits(stack)) {
            traitModifier *= trait.getDamageModifier(player, stack, context);
        }
        
        return baseDamage * traitModifier;
    }
    
    /**
     * Calculates the total attack speed including speed modifiers from traits
     */
    public float getTotalAttackSpeed(Player player, ItemStack stack, WeaponUseContext context) {
        float baseSpeed = getAttackSpeed();
        float speedModifier = 1.0F;
        
        // Apply speed modifiers from all traits
        for (WeaponTrait trait : getWeaponTraits(stack)) {
            speedModifier *= trait.getSpeedModifier(player, stack, context);
        }
        
        return baseSpeed * speedModifier;
    }
    
    /**
     * Gets the durability loss multiplier including protection from traits
     */
    public float getDurabilityLoss(Player player, ItemStack stack, WeaponUseContext context, float baseLoss) {
        float protection = 1.0F;
        
        // Apply durability protection from all traits
        for (WeaponTrait trait : getWeaponTraits(stack)) {
            protection *= trait.getDurabilityProtection(player, stack, context);
        }
        
        return baseLoss * protection;
    }
}