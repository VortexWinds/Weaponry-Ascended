package com.vortexwinds.weaponryascended.traits;

import com.vortexwinds.weaponryascended.weapons.BaseWeaponItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Base class for all weapon traits in the Weaponry Ascended system.
 * Traits are special effects that can be applied to weapons to modify their behavior,
 * damage, or provide additional abilities based on what items are used to craft them.
 */
public abstract class WeaponTrait {
    private final String id;
    private final String name;
    private final String description;
    private final TraitType type;
    private final int maxStrength;
    
    public WeaponTrait(String id, String name, String description, TraitType type, int maxStrength) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.maxStrength = maxStrength;
    }
    
    /**
     * Applies the trait effect when the weapon is used
     */
    public abstract void applyEffect(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context);
    
    /**
     * Modifies the damage dealt by the weapon
     * Return the damage multiplier (1.0 = no change)
     */
    public float getDamageModifier(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F;
    }
    
    /**
     * Modifies the attack speed of the weapon
     * Return the speed multiplier (1.0 = no change)
     */
    public float getSpeedModifier(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F;
    }
    
    /**
     * Provides additional durability protection
     * Return the durability loss multiplier (1.0 = normal, 0.5 = half durability loss)
     */
    public float getDurabilityProtection(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F;
    }
    
    /**
     * Provides special effects on hit
     */
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        // Default implementation - no special effect
    }
    
    /**
     * Provides special effects on breaking blocks
     */
    public void onHitBlock(Player player, ItemStack weapon, BlockHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        // Default implementation - no special effect
    }
    
    /**
     * Determines if this trait can be combined with another trait
     */
    public boolean canCombineWith(WeaponTrait other) {
        return true; // Default allows all combinations
    }
    
    /**
     * Creates an instance of this trait with a specific strength level
     */
    public WeaponTrait createInstance(int strength) {
        return new Instance(this, Math.max(1, Math.min(strength, maxStrength)));
    }
    
    /**
     * Gets a human-readable description of the trait's effect at the given strength
     */
    public String getEffectDescription(int strength) {
        return description;
    }
    
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public TraitType getType() { return type; }
    public int getMaxStrength() { return maxStrength; }
    
    /**
     * Find a trait by its ID from the registry
     */
    public static WeaponTrait getTraitById(String id) {
        // This would be implemented with a proper registry system
        // For now, return null - this will be filled in later
        return null;
    }
    
    /**
     * Wrapper class that holds a trait and its strength level
     */
    public static class Instance {
        private final WeaponTrait trait;
        private final int strength;
        
        public Instance(WeaponTrait trait, int strength) {
            this.trait = trait;
            this.strength = strength;
        }
        
        public WeaponTrait getTrait() { return trait; }
        public int getStrength() { return strength; }
        
        @Override
        public String toString() {
            return trait.getName() + " (" + strength + ")";
        }
    }
    
    /**
     * Enum representing different types of weapon traits
     */
    public enum TraitType {
        DAMAGE("damage", "Increases weapon damage"),
        SPEED("speed", "Increases attack speed"),
        DURABILITY("durability", "Increases weapon durability"),
        SPECIAL("special", "Provides special abilities"),
        ELEMENTAL("elemental", "Adds elemental damage"),
        UTILITY("utility", "Provides utility effects");
        
        private final String id;
        private final String description;
        
        TraitType(String id, String description) {
            this.id = id;
            this.description = description;
        }
        
        public String getId() { return id; }
        public String getDescription() { return description; }
    }
}