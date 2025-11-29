package com.vortexwinds.weaponryascended.traits;

import com.vortexwinds.weaponryascended.weapons.BaseWeaponItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Fire trait that adds fire damage and burning effects to weapons.
 * Can be applied to weapons using fire-related items like blaze powder or flint and steel.
 */
public class FireTrait extends WeaponTrait {
    public FireTrait() {
        super("fire", "Fire", "Adds fire damage and burning effects to attacks", TraitType.ELEMENTAL, 5);
    }
    
    @Override
    public float getDamageModifier(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F + (0.1F * strength); // 10% damage increase per strength level
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        // Apply fire damage if attacking an entity
        if (context.isAttacking() && hitResult.getEntity() != null) {
            float fireDamage = 2.0F * strength; // 2 damage per strength level
            hitResult.getEntity().hurt(DamageSource.playerAttack(player), fireDamage);
            
            // Add burning effect (fire would normally be handled by the fire damage type)
            // This is a simplified example - actual implementation would need proper effect handling
        }
    }
    
    @Override
    public void applyEffect(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        // Fire trait provides warmth effect - grants fire resistance slightly
        if (context.isAttacking()) {
            // Effect would be applied through Minecraft's effect system
            // This is a placeholder for the actual implementation
        }
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Adds " + (2.0F * strength) + " fire damage and burning effects";
    }
}

/**
 * Frost trait that adds cold damage and slows down enemies.
 * Can be applied using ice, snow, or frost-related materials.
 */
class FrostTrait extends WeaponTrait {
    public FrostTrait() {
        super("frost", "Frost", "Adds cold damage and slows enemies", TraitType.ELEMENTAL, 5);
    }
    
    @Override
    public float getDamageModifier(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F + (0.05F * strength); // 5% damage increase per strength level
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking() && hitResult.getEntity() != null) {
            float frostDamage = 1.5F * strength; // 1.5 damage per strength level
            hitResult.getEntity().hurt(DamageSource.playerAttack(player), frostDamage);
            
            // Apply slowness effect (would need proper effect implementation)
        }
    }
    
    @Override
    public float getDurabilityProtection(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F - (0.1F * strength); // Slightly reduces durability loss
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Adds " + (1.5F * strength) + " frost damage and slows enemies";
    }
}

/**
 * Sharpness trait that increases weapon sharpness and damage output.
 * Can be applied using sharpening stones or sharp materials.
 */
class SharpnessTrait extends WeaponTrait {
    public SharpnessTrait() {
        super("sharpness", "Sharpness", "Increases weapon sharpness and damage", TraitType.DAMAGE, 10);
    }
    
    @Override
    public float getDamageModifier(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F + (0.15F * strength); // 15% damage increase per strength level
    }
    
    @Override
    public boolean canCombineWith(WeaponTrait other) {
        // Sharpness doesn't combine well with other damage traits
        return other.getType() != TraitType.DAMAGE;
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Increases damage by " + (int)(15 * strength) + "%";
    }
}

/**
 * Speed trait that increases attack speed and reduces attack cooldown.
 * Can be applied using speed-enhancing materials or lightweight components.
 */
class SpeedTrait extends WeaponTrait {
    public SpeedTrait() {
        super("speed", "Speed", "Increases attack speed and reduces cooldown", TraitType.SPEED, 8);
    }
    
    @Override
    public float getSpeedModifier(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F + (0.1F * strength); // 10% speed increase per strength level
    }
    
    @Override
    public void applyEffect(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        // Speed trait makes the player feel more agile
        if (context.isAttacking()) {
            // Would modify attack cooldown in actual implementation
        }
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Increases attack speed by " + (int)(10 * strength) + "%";
    }
}

/**
 * Durability trait that increases weapon longevity and reduces wear.
 * Can be applied using durable materials like obsidian or reinforced components.
 */
class DurabilityTrait extends WeaponTrait {
    public DurabilityTrait() {
        super("durability", "Durability", "Increases weapon durability and reduces wear", TraitType.DURABILITY, 8);
    }
    
    @Override
    public float getDurabilityProtection(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F - (0.2F * strength); // Reduces durability loss by 20% per strength level
    }
    
    @Override
    public void applyEffect(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        // Durability trait provides additional protection during combat
        if (context.isAttacking()) {
            // Would add additional durability in actual implementation
        }
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Reduces durability loss by " + (20 * strength) + "%";
    }
}

/**
 * Toxic trait that adds poison effects to attacks.
 * Can be applied using poisonous materials or toxic substances.
 */
class ToxicTrait extends WeaponTrait {
    public ToxicTrait() {
        super("toxic", "Toxic", "Adds poison effects to attacks", TraitType.SPECIAL, 6);
    }
    
    @Override
    public float getDamageModifier(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F + (0.03F * strength); // Small damage increase
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking() && hitResult.getEntity() != null) {
            float poisonDamage = 1.0F * strength; // 1 poison damage per strength level
            hitResult.getEntity().hurt(DamageSource.playerAttack(player), poisonDamage);
            
            // Apply poison effect (would need proper effect system implementation)
        }
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Adds " + strength + " poison damage and applies poison effects";
    }
}

/**
 * Explosion trait that adds explosive properties to attacks.
 * Can be applied using explosive materials like TNT or gunpowder.
 */
class ExplosionTrait extends WeaponTrait {
    public ExplosionTrait() {
        super("explosion", "Explosion", "Adds explosive damage to attacks", TraitType.SPECIAL, 5);
    }
    
    @Override
    public float getDamageModifier(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F + (0.08F * strength); // 8% damage increase per strength level
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking() && hitResult.getEntity() != null) {
            float explosionDamage = 3.0F * strength; // 3 explosive damage per strength level
            hitResult.getEntity().hurt(DamageSource.explosion(player), explosionDamage);
            
            // Apply knockback effect (explosion force)
        }
    }
    
    @Override
    public boolean canCombineWith(WeaponTrait other) {
        // Explosion trait is incompatible with fire trait (too volatile)
        return other.getId() != "fire";
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Adds " + (3.0F * strength) + " explosive damage and knockback";
    }
}