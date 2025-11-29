package com.vortexwinds.weaponryascended.weapon;

import com.vortexwinds.weaponryascended.weapons.BaseWeaponItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BattleAxeItem extends BaseWeaponItem {
    public BattleAxeItem(Properties properties, float baseAttackDamage, float baseAttackSpeed, Ingredient repairIngredient) {
        super(properties, baseAttackDamage, baseAttackSpeed, repairIngredient);
    }

    @Override
    public boolean isTwoHanded() {
        return true;
    }

    @Override
    public float getAttackReachBonus() {
        return 1.5f;
    }

    @Override
    public void onHitEntity(LivingEntity target, LivingEntity attacker, ItemStack stack) {
        Level level = target.level();
        if (!level.isClientSide()) {
            // Battle axes excel at breaking shields and armor
            if (target instanceof Player player && player.isBlocking()) {
                // Shield breaking bonus
                player.hurt(attacker.damageSources().playerAttack(player), 
                          getAttackDamage(stack) * 1.4f);
                player.blockBreakingCooldown = 0; // Reset block breaking cooldown
            } else {
                target.hurt(attacker.damageSources().playerAttack((Player) attacker), 
                          getAttackDamage(stack) * 1.1f);
            }
            
            // Chopping effect on wood-based entities (if applicable)
            // This could be expanded to affect wood armor pieces, etc.
        }
        super.onHitEntity(target, attacker, stack);
    }

    @Override
    public float getMiningSpeedMultiplier(BlockState state) {
        // Battle axes are excellent for wood and similar materials
        if (state.is(Blocks.LOG) || state.is(Blocks.LOG_2) || 
            state.is(Blocks.WOOD) || state.is(Blocks.WOOD_2) ||
            state.getBlock().asItem().toString().contains("wood")) {
            return 2.0f; // Double speed on wood
        }
        return 0.8f; // Slower on other materials due to weight
    }

    @Override
    public void onBlockDestroyed(ItemStack stack, BlockState block, Level level, net.minecraft.world.entity.player.Player player) {
        // Battle axes consume more durability when breaking non-wood blocks
        int durabilityLoss = block.getMaterial().isWood() ? 1 : 2;
        stack.setDamageValue(stack.getDamageValue() + durabilityLoss);
        super.onBlockDestroyed(stack, block, level, player);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
        return state.getMaterial().isWood();
    }
}