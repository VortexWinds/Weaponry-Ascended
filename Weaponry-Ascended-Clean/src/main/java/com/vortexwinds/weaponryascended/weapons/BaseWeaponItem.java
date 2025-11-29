package com.vortexwinds.weaponryascended.weapons;

import com.vortexwinds.weaponryascended.traits.WeaponTrait;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all custom weapons in Weaponry Ascended.
 * Provides foundation for the customizable weapon system where weapons can have multiple traits.
 */
public abstract class BaseWeaponItem extends net.minecraft.world.item.SwordItem implements Vanishable
{
    protected final WeaponTier tier;
    protected final float attackDamage;
    protected final float attackSpeed;

    public BaseWeaponItem(WeaponTier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier.getMaterial(), (int) attackDamage, attackSpeed, properties);
        this.tier = tier;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    /**
     * Gets the weapon tier this weapon uses for crafting and material properties
     */
    public WeaponTier getWeaponTier() {
        return tier;
    }

    /**
     * Adds weapon traits to the weapon's tooltip display
     */
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        
        // Get weapon traits from NBT
        List<WeaponTrait> traits = getWeaponTraits(stack);
        
        if (!traits.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.weaponryascended.weapon_traits").withStyle(ChatFormatting.GOLD));
            
            for (WeaponTrait trait : traits) {
                tooltip.add(Component.translatable("tooltip.weaponryascended.trait_format", 
                    Component.translatable("trait.weaponryascended." + trait.getId()), 
                    trait.getEffectDescription()).withStyle(ChatFormatting.GRAY));
            }
        }
    }

    /**
     * Retrieves all weapon traits from the item's NBT data
     */
    public List<WeaponTrait> getWeaponTraits(ItemStack stack) {
        List<WeaponTrait> traits = new ArrayList<>();
        
        if (stack.hasTag() && stack.getTag().contains("weaponTraits", Tag.TAG_LIST)) {
            ListTag traitList = stack.getTag().getList("weaponTraits", Tag.TAG_COMPOUND);
            
            for (int i = 0; i < traitList.size(); i++) {
                CompoundTag traitTag = traitList.getCompound(i);
                String traitId = traitTag.getString("traitId");
                int strength = traitTag.getInt("strength");
                
                // Find the trait by ID and add it to the list
                WeaponTrait trait = WeaponTrait.getTraitById(traitId);
                if (trait != null) {
                    traits.add(trait.createInstance(strength));
                }
            }
        }
        
        return traits;
    }

    /**
     * Adds a weapon trait to the item's NBT data
     */
    public void addWeaponTrait(ItemStack stack, WeaponTrait trait, int strength) {
        CompoundTag tag = stack.getOrCreateTag();
        ListTag traitList;
        
        if (tag.contains("weaponTraits", Tag.TAG_LIST)) {
            traitList = tag.getList("weaponTraits", Tag.TAG_LIST);
        } else {
            traitList = new ListTag();
        }
        
        // Check if trait already exists and update strength
        boolean traitFound = false;
        for (int i = 0; i < traitList.size(); i++) {
            CompoundTag existingTrait = traitList.getCompound(i);
            if (existingTrait.getString("traitId").equals(trait.getId())) {
                existingTrait.putInt("strength", strength);
                traitFound = true;
                break;
            }
        }
        
        // Add new trait if not found
        if (!traitFound) {
            CompoundTag newTrait = new CompoundTag();
            newTrait.putString("traitId", trait.getId());
            newTrait.putInt("strength", strength);
            traitList.add(newTrait);
        }
        
        tag.put("weaponTraits", traitList);
    }

    /**
     * Applies all weapon traits when the weapon is used
     */
    protected void applyWeaponTraits(Player player, ItemStack stack, WeaponUseContext context) {
        List<WeaponTrait> traits = getWeaponTraits(stack);
        
        for (WeaponTrait trait : traits) {
            trait.applyEffect(player, stack, context);
        }
    }

    /**
     * Context for weapon use, providing information about how the weapon was used
     */
    public static class WeaponUseContext {
        private final Player player;
        private final ItemStack weapon;
        private final boolean attacking;
        private final boolean blocking;
        private final boolean charging;
        private final net.minecraft.world.damagesource.DamageSource damageSource;
        
        public WeaponUseContext(Player player, ItemStack weapon, boolean attacking, boolean blocking, boolean charging) {
            this(player, weapon, attacking, blocking, charging, null);
        }
        
        public WeaponUseContext(Player player, ItemStack weapon, boolean attacking, boolean blocking, boolean charging, net.minecraft.world.damagesource.DamageSource damageSource) {
            this.player = player;
            this.weapon = weapon;
            this.attacking = attacking;
            this.blocking = blocking;
            this.charging = charging;
            this.damageSource = damageSource;
        }
        
        public Player getPlayer() { return player; }
        public ItemStack getWeapon() { return weapon; }
        public boolean isAttacking() { return attacking; }
        public boolean isBlocking() { return blocking; }
        public boolean isCharging() { return charging; }
        public net.minecraft.world.damagesource.DamageSource getDamageSource() { return damageSource; }
    }

    /**
     * Enum defining different weapon tiers with their material properties
     */
    public enum WeaponTier {
        WOOD(59, 2.0F, 0.8F, 0, 15),
        STONE(131, 4.0F, 0.6F, 1, 5),
        IRON(250, 6.0F, 0.6F, 2, 14),
        GOLD(32, 4.0F, 1.2F, 0, 22),
        DIAMOND(1561, 8.0F, 0.4F, 3, 10),
        NETHERITE(2031, 9.0F, 0.3F, 4, 15);
        
        private final int durability;
        private final float miningSpeed;
        private final float attackDamageBonus;
        private final int miningLevel;
        private final int enchantmentValue;
        
        WeaponTier(int durability, float miningSpeed, float attackDamageBonus, int miningLevel, int enchantmentValue) {
            this.durability = durability;
            this.miningSpeed = miningSpeed;
            this.attackDamageBonus = attackDamageBonus;
            this.miningLevel = miningLevel;
            this.enchantmentValue = enchantmentValue;
        }
        
        public int getDurability() { return durability; }
        public float getMiningSpeed() { return miningSpeed; }
        public float getAttackDamageBonus() { return attackDamageBonus; }
        public int getMiningLevel() { return miningLevel; }
        public int getEnchantmentValue() { return enchantmentValue; }
        
        public net.minecraft.world.item.crafting.Ingredient getIngredient() {
            return switch (this) {
                case WOOD -> net.minecraft.world.item.crafting.Ingredient.of(net.minecraft.world.item.Items.STICK);
                case STONE -> net.minecraft.world.item.crafting.Ingredient.of(net.minecraft.world.item.Items.COBBLESTONE);
                case IRON -> net.minecraft.world.item.crafting.Ingredient.of(net.minecraft.world.item.Items.IRON_INGOT);
                case GOLD -> net.minecraft.world.item.crafting.Ingredient.of(net.minecraft.world.item.Items.GOLD_INGOT);
                case DIAMOND -> net.minecraft.world.item.crafting.Ingredient.of(net.minecraft.world.item.Items.DIAMOND);
                case NETHERITE -> net.minecraft.world.item.crafting.Ingredient.of(net.minecraft.world.item.Items.NETHERITE_INGOT);
            };
        }
        
        public net.minecraft.world.item.Item getMaterial() {
            return switch (this) {
                case WOOD -> net.minecraft.world.item.Items.WOODEN_SWORD;
                case STONE -> net.minecraft.world.item.Items.STONE_SWORD;
                case IRON -> net.minecraft.world.item.Items.IRON_SWORD;
                case GOLD -> net.minecraft.world.item.Items.GOLDEN_SWORD;
                case DIAMOND -> net.minecraft.world.item.Items.DIAMOND_SWORD;
                case NETHERITE -> net.minecraft.world.item.Items.NETHERITE_SWORD;
            };
        }
    }
}