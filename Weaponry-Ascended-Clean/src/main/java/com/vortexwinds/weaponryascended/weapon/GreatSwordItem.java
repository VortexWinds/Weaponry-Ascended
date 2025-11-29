package com.vortexwinds.weaponryascended.weapon;

import com.vortexwinds.weaponryascended.weapons.BaseWeaponItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class GreatSwordItem extends BaseWeaponItem {
    public GreatSwordItem(Properties properties, float baseAttackDamage, float baseAttackSpeed, Ingredient repairIngredient) {
        super(properties, baseAttackDamage, baseAttackSpeed, repairIngredient);
    }

    @Override
    public boolean isTwoHanded() {
        return true;
    }

    @Override
    public float getAttackReachBonus() {
        return 2.0f; // Extra reach for great swords
    }

    @Override
    public void onHitEntity(LivingEntity target, LivingEntity attacker, ItemStack stack) {
        // Great swords have a wide sweeping attack
        Level level = target.level();
        if (!level.isClientSide()) {
            Vec3 attackerPos = attacker.position();
            Vec3 targetPos = target.position();
            double distance = attackerPos.distanceTo(targetPos);
            
            // Affect entities within 3 blocks in a cone
            for (LivingEntity nearby : level.getEntitiesOfClass(LivingEntity.class, 
                target.getBoundingBox().inflate(3.0))) {
                if (nearby != attacker && nearby != target && distanceToLine(nearby.position(), attackerPos, targetPos) < 2.0) {
                    float damage = stack.getDamageValue() / (float) stack.getMaxDamage();
                    nearby.hurt(attacker.damageSources().playerAttack((Player) attacker), 
                        getAttackDamage(stack) * 0.5f);
                }
            }
        }
        super.onHitEntity(target, attacker, stack);
    }

    @Override
    public void onBlockDestroyed(ItemStack stack, net.minecraft.world.level.BlockState block, 
                                net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player) {
        // Great swords are slower at breaking blocks
        stack.setDamageValue(stack.getDamageValue() + 2);
        super.onBlockDestroyed(stack, block, level, player);
    }

    private double distanceToLine(Vec3 point, Vec3 start, Vec3 end) {
        Vec3 line = end.subtract(start);
        Vec3 toPoint = point.subtract(start);
        double lineLen = line.length();
        if (lineLen == 0) return toPoint.length();
        return toPoint.subtract(line.scale(toPoint.dot(line) / (lineLen * lineLen))).length();
    }
}