package com.vortexwinds.weaponryascended.weapon;

import com.vortexwinds.weaponryascended.weapons.BaseWeaponItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ScytheItem extends BaseWeaponItem {
    public ScytheItem(Properties properties, float baseAttackDamage, float baseAttackSpeed, Ingredient repairIngredient) {
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
            // Scythes have harvesting bonus - bonus damage to plants, crops, and vines
            if (target instanceof net.minecraft.world.entity.animal.Animal plant) {
                target.hurt(attacker.damageSources().playerAttack((Player) attacker), 
                    getAttackDamage(stack) * 1.5f);
            }
            
            // Apply bleed effect
            if (stack.hasTag() && stack.getTag().getBoolean("bleeding")) {
                // Add bleeding logic here
                target.hurt(attacker.damageSources().mobAttack((net.minecraft.world.entity.Mob) attacker), 2.0f);
            }
        }
        super.onHitEntity(target, attacker, stack);
    }

    @Override
    public float getBlockBreakingEfficiency() {
        return 1.2f; // Slightly faster at breaking plants
    }
}