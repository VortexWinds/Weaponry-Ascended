package com.vortexwinds.weaponryascended.traits;

import com.vortexwinds.weaponryascended.weapons.BaseWeaponItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Blocks;

/**
 * Fire trait - adds fire damage to weapons
 * Can be applied using: Blaze Rods, Blaze Powder
 */
public class FireTrait extends WeaponTrait {
    public FireTrait() {
        super("fire", "Fire", "Adds fire damage and burning effects to attacks", TraitType.ELEMENTAL, 5);
    }
    
    @Override
    public float getDamageModifier(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F + (0.1F * strength);
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking() && hitResult.getEntity() != null) {
            float fireDamage = 2.0F * strength;
            hitResult.getEntity().hurt(DamageSource.playerAttack(player), fireDamage);
        }
    }
    
    @Override
    public void applyEffect(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking()) {
            // Fire resistance effect would be applied here
        }
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Adds " + (2.0F * strength) + " fire damage";
    }
}

/**
 * Cold trait - adds cold damage and slows enemies
 * Can be applied using: Ice, Packed Ice
 */
public class ColdTrait extends WeaponTrait {
    public ColdTrait() {
        super("cold", "Cold", "Adds cold damage and slows enemies", TraitType.ELEMENTAL, 5);
    }
    
    @Override
    public float getDamageModifier(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F + (0.05F * strength);
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking() && hitResult.getEntity() != null) {
            float coldDamage = 1.5F * strength;
            hitResult.getEntity().hurt(DamageSource.playerAttack(player), coldDamage);
        }
    }
    
    @Override
    public float getDurabilityProtection(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F - (0.05F * strength);
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Adds " + (1.5F * strength) + " cold damage and slows enemies";
    }
}

/**
 * Lightning trait - adds lightning damage
 * Can be applied using: Redstone Block
 */
public class LightningTrait extends WeaponTrait {
    public LightningTrait() {
        super("lightning", "Lightning", "Adds lightning damage and stunning effects", TraitType.ELEMENTAL, 4);
    }
    
    @Override
    public float getDamageModifier(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F + (0.12F * strength);
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking() && hitResult.getEntity() != null) {
            float lightningDamage = 2.5F * strength;
            hitResult.getEntity().hurt(DamageSource.playerAttack(player), lightningDamage);
        }
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Adds " + (2.5F * strength) + " lightning damage";
    }
}

/**
 * Void trait - adds void damage and teleportation effects
 * Can be applied using: Ender Pearl
 */
public class VoidTrait extends WeaponTrait {
    public VoidTrait() {
        super("void", "Void", "Adds void damage and teleport effects", TraitType.ELEMENTAL, 3);
    }
    
    @Override
    public float getDamageModifier(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F + (0.08F * strength);
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking() && hitResult.getEntity() != null) {
            float voidDamage = 2.0F * strength;
            hitResult.getEntity().hurt(DamageSource.playerAttack(player), voidDamage);
        }
    }
    
    @Override
    public void applyEffect(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking() && Math.random() < 0.1 * strength) {
            // Teleportation effect
        }
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Adds " + (2.0F * strength) + " void damage and teleportation effects";
    }
}

/**
 * Light trait - adds light damage and healing effects
 * Can be applied using: Glowstone
 */
public class LightTrait extends WeaponTrait {
    public LightTrait() {
        super("light", "Light", "Adds light damage and healing effects", TraitType.ELEMENTAL, 4);
    }
    
    @Override
    public float getDamageModifier(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F + (0.06F * strength);
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking() && hitResult.getEntity() != null) {
            float lightDamage = 1.8F * strength;
            hitResult.getEntity().hurt(DamageSource.playerAttack(player), lightDamage);
        }
    }
    
    @Override
    public void applyEffect(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking()) {
            // Healing effect for the player
        }
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Adds " + (1.8F * strength) + " light damage and healing effects";
    }
}

/**
 * Physical trait - enhances physical weapon properties
 * Tiered based on metal progression: Copper < Iron < Gold < Diamond < Netherite
 */
public class PhysicalTrait extends WeaponTrait {
    private final PhysicalTier tier;
    
    public PhysicalTrait(PhysicalTier tier) {
        super("physical_" + tier.name().toLowerCase(), "Physical " + tier.name(), 
              "Enhances physical weapon properties with " + tier.name() + " tier", 
              TraitType.PHYSICAL, tier.getMaxStrength());
        this.tier = tier;
    }
    
    @Override
    public float getDamageModifier(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F + (tier.getDamageBonus() * 0.01F * strength);
    }
    
    @Override
    public float getSpeedModifier(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F + (tier.getSpeedBonus() * 0.01F * strength);
    }
    
    @Override
    public float getDurabilityProtection(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F - (tier.getDurabilityBonus() * 0.01F * strength);
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Enhances physical properties with " + tier.getDamageBonus() + "% damage, " + 
               tier.getSpeedBonus() + "% speed, " + tier.getDurabilityBonus() + "% durability";
    }
    
    public enum PhysicalTier {
        COPPER(5, 2, 3, 3),
        IRON(10, 4, 6, 5),
        GOLD(8, 8, 2, 4),
        DIAMOND(15, 6, 10, 8),
        NETHERITE(20, 8, 15, 10);
        
        private final int maxStrength;
        private final int damageBonus;
        private final int speedBonus;
        private final int durabilityBonus;
        
        PhysicalTier(int maxStrength, int damageBonus, int speedBonus, int durabilityBonus) {
            this.maxStrength = maxStrength;
            this.damageBonus = damageBonus;
            this.speedBonus = speedBonus;
            this.durabilityBonus = durabilityBonus;
        }
        
        public int getMaxStrength() { return maxStrength; }
        public int getDamageBonus() { return damageBonus; }
        public int getSpeedBonus() { return speedBonus; }
        public int getDurabilityBonus() { return durabilityBonus; }
    }
}

/**
 * Durability trait - increases weapon longevity
 * Can be applied using: Obsidian, Reinforced Deepslate
 */
public class DurabilityTrait extends WeaponTrait {
    public DurabilityTrait() {
        super("durability", "Durability", "Increases weapon durability and reduces wear", TraitType.PHYSICAL, 8);
    }
    
    @Override
    public float getDurabilityProtection(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        return 1.0F - (0.2F * strength);
    }
    
    @Override
    public void applyEffect(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking()) {
            // Additional durability protection
        }
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Reduces durability loss by " + (20 * strength) + "%";
    }
}

/**
 * Bleed trait - causes enemies to bleed from physical damage
 * Can be applied using: Cactus < Flint < Nether Quartz < Echo Shard
 * Can only trigger from physical damage
 */
public class BleedTrait extends WeaponTrait {
    private final BleedTier tier;
    
    public BleedTrait(BleedTier tier) {
        super("bleed_" + tier.name().toLowerCase(), "Bleed " + tier.name(), 
              "Causes bleeding effects on physical damage", TraitType.SPECIAL, tier.getMaxStrength());
        this.tier = tier;
    }
    
    @Override
    public boolean canTrigger(DamageSource damageSource) {
        return damageSource == DamageSource.playerAttack(null) || 
               damageSource.getMsgId().contains("player");
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking() && hitResult.getEntity() != null && canTrigger(context.getDamageSource())) {
            float bleedDamage = tier.getDamage() * strength;
            hitResult.getEntity().hurt(DamageSource.playerAttack(player), bleedDamage);
        }
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Causes " + (tier.getDamage() * strength) + " bleed damage on physical attacks";
    }
    
    public enum BleedTier {
        CACTUS(2, 1.0F),
        FLINT(3, 1.5F),
        QUARTZ(4, 2.0F),
        ECHO_SHARD(5, 3.0F);
        
        private final int maxStrength;
        private final float damage;
        
        BleedTier(int maxStrength, float damage) {
            this.maxStrength = maxStrength;
            this.damage = damage;
        }
        
        public int getMaxStrength() { return maxStrength; }
        public float getDamage() { return damage; }
    }
}

/**
 * Burn trait - adds burning effects from fire damage
 * Can be applied using: Flint and Steel, Lava Bucket
 * Can only be triggered by fire damage
 */
public class BurnTrait extends WeaponTrait {
    public BurnTrait() {
        super("burn", "Burn", "Intensifies burning effects from fire damage", TraitType.SPECIAL, 6);
    }
    
    @Override
    public boolean canTrigger(DamageSource damageSource) {
        return damageSource == DamageSource.inFire || 
               damageSource == DamageSource.onFire ||
               damageSource.getMsgId().contains("fire");
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking() && hitResult.getEntity() != null && canTrigger(context.getDamageSource())) {
            float burnDamage = 1.5F * strength;
            hitResult.getEntity().hurt(DamageSource.playerAttack(player), burnDamage);
        }
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Intensifies fire damage by " + (1.5F * strength);
    }
}

/**
 * Poison trait - adds poison effects to attacks
 * Can be applied using: Zombie Flesh < Spider Eye < Fermented Spider Eye
 * Effect only triggers if weapon is imbued with this trait
 */
public class PoisonTrait extends WeaponTrait {
    private final PoisonTier tier;
    
    public PoisonTrait(PoisonTier tier) {
        super("poison_" + tier.name().toLowerCase(), "Poison " + tier.name(), 
              "Adds poison effects to attacks", TraitType.SPECIAL, tier.getMaxStrength());
        this.tier = tier;
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking() && hitResult.getEntity() != null) {
            float poisonDamage = tier.getDamage() * strength;
            hitResult.getEntity().hurt(DamageSource.playerAttack(player), poisonDamage);
        }
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Adds " + (tier.getDamage() * strength) + " poison damage";
    }
    
    public enum PoisonTier {
        ZOMBIE_FLESH(3, 0.5F),
        SPIDER_EYE(4, 1.0F),
        FERMENTED_SPIDER_EYE(5, 1.5F);
        
        private final int maxStrength;
        private final float damage;
        
        PoisonTier(int maxStrength, float damage) {
            this.maxStrength = maxStrength;
            this.damage = damage;
        }
        
        public int getMaxStrength() { return maxStrength; }
        public float getDamage() { return damage; }
    }
}

/**
 * Warping trait - echoes of prior shadow damage
 * Can be applied using: Dragon's Breath
 * Effect triggered by shadow damage causing the target to take echoes of prior shadow damage taken
 */
public class WarpingTrait extends WeaponTrait {
    public WarpingTrait() {
        super("warping", "Warping", "Echoes prior shadow damage on new attacks", TraitType.SPECIAL, 4);
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking() && hitResult.getEntity() != null) {
            // Implementation would track previous shadow damage and echo it
            float warpDamage = 2.0F * strength;
            hitResult.getEntity().hurt(DamageSource.playerAttack(player), warpDamage);
        }
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Echoes " + (2.0F * strength) + " shadow damage";
    }
}

/**
 * Blinded trait - temporarily blinds targets from light damage
 * Can be applied using: Shroomlight
 * Effect triggered by light damage causing the target to become blinded for a short duration
 */
public class BlindedTrait extends WeaponTrait {
    public BlindedTrait() {
        super("blinded", "Blinded", "Blinds targets struck by light damage", TraitType.SPECIAL, 4);
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking() && hitResult.getEntity() != null) {
            float blindDamage = 1.2F * strength;
            hitResult.getEntity().hurt(DamageSource.playerAttack(player), blindDamage);
        }
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Blinds targets and adds " + (1.2F * strength) + " light damage";
    }
}

/**
 * Freeze trait - progressively slows targets with cold damage
 * Can be applied using: Snowball < Snow Block
 * Effect triggered by cold damage causing the target to become increasingly slowed with consecutive attacks (3 total) before becoming "Frozen"
 */
public class FreezeTrait extends WeaponTrait {
    private final FreezeTier tier;
    
    public FreezeTier(FreezeTier tier) {
        super("freeze_" + tier.name().toLowerCase(), "Freeze " + tier.name(), 
              "Progressively slows targets with cold damage", TraitType.SPECIAL, tier.getMaxStrength());
        this.tier = tier;
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking() && hitResult.getEntity() != null) {
            float freezeDamage = tier.getDamage() * strength;
            hitResult.getEntity().hurt(DamageSource.playerAttack(player), freezeDamage);
        }
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Progressive slowing with " + (tier.getDamage() * strength) + " cold damage";
    }
    
    public enum FreezeTier {
        SNOWBALL(3, 1.0F),
        SNOW_BLOCK(4, 1.5F);
        
        private final int maxStrength;
        private final float damage;
        
        FreezeTier(int maxStrength, float damage) {
            this.maxStrength = maxStrength;
            this.damage = damage;
        }
        
        public int getMaxStrength() { return maxStrength; }
        public float getDamage() { return damage; }
    }
}

/**
 * Shatter trait - doubles damage on frozen targets
 * Can only trigger on "Frozen" target causing the next instance of damage to be doubled
 */
public class ShatterTrait extends WeaponTrait {
    public ShatterTrait() {
        super("shatter", "Shatter", "Doubles damage on frozen targets", TraitType.COMBO, 3);
    }
    
    @Override
    public boolean canCombineWith(WeaponTrait other) {
        return other.getId().contains("freeze") || other.getType() == TraitType.ELEMENTAL;
    }
    
    @Override
    public float getDamageModifier(Player player, ItemStack weapon, BaseWeaponItem.WeaponUseContext context) {
        // Double damage on frozen targets - would check target state
        return 2.0F;
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Doubles damage on frozen targets";
    }
}

/**
 * Shocking trait - stuns targets from lightning damage
 * Can be applied using: Redstone Dust
 * Only triggers on lightning damage causing the target to be repeatedly stunned for brief instances and causing them to take marginally more lightning damage
 */
public class ShockingTrait extends WeaponTrait {
    public ShockingTrait() {
        super("shocking", "Shocking", "Stuns targets struck by lightning damage", TraitType.SPECIAL, 4);
    }
    
    @Override
    public boolean canTrigger(DamageSource damageSource) {
        return damageSource.getMsgId().contains("lightning") || 
               damageSource.getMsgId().contains("electric");
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking() && hitResult.getEntity() != null && canTrigger(context.getDamageSource())) {
            float shockDamage = 1.3F * strength; // Marginally more lightning damage
            hitResult.getEntity().hurt(DamageSource.playerAttack(player), shockDamage);
        }
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Stuns targets and adds " + (1.3F * strength) + " lightning damage";
    }
}

/**
 * Quake trait - creates shockwaves on impact
 * Can be applied using: TNT
 * Effect only triggers if weapon is imbued with this trait causing shockwaves on impact with the target or ground
 */
public class QuakeTrait extends WeaponTrait {
    public QuakeTrait() {
        super("quake", "Quake", "Creates shockwaves on impact", TraitType.SPECIAL, 3);
    }
    
    @Override
    public void onHitEntity(Player player, ItemStack weapon, EntityHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        if (context.isAttacking()) {
            float quakeDamage = 2.5F * strength;
            hitResult.getEntity().hurt(DamageSource.playerAttack(player), quakeDamage);
        }
    }
    
    @Override
    public void onHitBlock(Player player, ItemStack weapon, BlockHitResult hitResult, BaseWeaponItem.WeaponUseContext context) {
        // Create shockwave effect on block hit
        float quakeDamage = 1.0F * strength;
        player.hurt(DamageSource.playerAttack(player), quakeDamage);
    }
    
    @Override
    public boolean canCombineWith(WeaponTrait other) {
        return !other.getId().contains("fire"); // Incompatible with fire
    }
    
    @Override
    public String getEffectDescription(int strength) {
        return "Creates " + (2.5F * strength) + " shockwave damage on impact";
    }
}