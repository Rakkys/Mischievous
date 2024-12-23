package net.rakks.mischieve.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class KatanaSword extends SwordItem {
    public KatanaSword() {
        super(ToolMaterials.IRON, 3, -2.4F, new Item.Settings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);
        //TODO: Make it so that it ticks while you charge so you know what you're at
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;
            int useDuration = this.getMaxUseTime(stack) - remainingUseTicks;
            float charge = (float) useDuration / 20F;

            if (charge >= 0.5F) {
                BlockPos playerPos = player.getBlockPos();

                int radius = 5;

                radius = (int) (radius * (charge / 0.5));

                float damage = getAttackDamage() / 4;

                damage = damage * (charge / 0.5F);

                if (charge >= 2.5F) {
                    damage = damage * 2;
                }

                Box box = new Box(playerPos.add(-radius, -radius, -radius), playerPos.add(+radius, +radius, +radius));

                List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, box, entity -> entity != player);

                ((PlayerEntity) user).sendMessage(Text.literal("DEBUGGING: Charge is at " + charge));
                ((PlayerEntity) user).sendMessage(Text.literal("DEBUGGING: Damage is at " + damage));
                ((PlayerEntity) user).sendMessage(Text.literal("DEBUGGING: Range is at " + radius));

                for (LivingEntity entity : entities) {
                    DamageSource damageSource = new DamageSource(
                            world.getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(DamageTypes.PLAYER_ATTACK),
                            user);

                    world.addParticle(ParticleTypes.FLASH,
                            entity.getX(), entity.getY(), entity.getZ(),
                            0, 0.5, 0);

                    spawnParticles(ParticleTypes.DAMAGE_INDICATOR, world, entity);

                    world.playSound(user, entity.getBlockPos(),
                            SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS,
                            1f, 1f);

                    if (charge >= 2.5F) {
                        spawnParticles(ParticleTypes.CRIT, world, entity);
                    }

                    entity.damage(damageSource, damage);
                }
            }
        }
    }

    public void spawnParticles(ParticleEffect particleEffect, World world, LivingEntity entity) {
        for (int i = 0; i < 10; i++) {
            double offsetX = (world.random.nextDouble() - 0.5) * entity.getWidth();
            double offsetY = world.random.nextDouble() * entity.getHeight();
            double offsetZ = (world.random.nextDouble() - 0.5) * entity.getWidth();

            world.addParticle(particleEffect,
                    entity.getX() + offsetX,
                    entity.getY() + offsetY,
                    entity.getZ() + offsetZ,
                    0, 0.5, 0);
        }

    }
}
