package net.rakks.mischieve.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.rakks.mischieve.Mischievous;
import net.rakks.mischieve.registries.GameRulesRegistry;

import java.util.Set;

public class MagicMirrorItem extends Item {
    public MagicMirrorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        user.setCurrentHand(hand);

        if (!(user instanceof ServerPlayerEntity)) {
            return TypedActionResult.consume(itemStack);
        }

        if (user.getWorld().getGameRules().getBoolean(GameRulesRegistry.INSTANT_MAGIC_MIRROR)) {
            ServerPlayerEntity player = (ServerPlayerEntity) user;
            teleportUser(player);

            return TypedActionResult.success(itemStack);
        } else {

            return TypedActionResult.consume(itemStack);
        }
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        int useDuration = this.getMaxUseTime(stack) - remainingUseTicks;

        if (useDuration == 20) {
            world.addParticle(ParticleTypes.FLASH, user.getX(), user.getY() + 1, user.getZ(), 0, 0, 0);

            world.playSound(null,
                    user.getBlockPos(), SoundEvents.BLOCK_IRON_DOOR_CLOSE,
                    SoundCategory.PLAYERS);

            //TODO: Figure out how to use noteblock noises
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 36000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof ServerPlayerEntity)) {
            return;
        }


        ServerPlayerEntity player = (ServerPlayerEntity) user;
        int useDuration = this.getMaxUseTime(stack) - remainingUseTicks;

        if (useDuration >= 20) {
            teleportUser(player);
        }
    }

    private void teleportUser(ServerPlayerEntity player) {
        BlockPos playerRespawnPos = player.getSpawnPointPosition();
        RegistryKey<World> playerRespawnWorldKey = player.getSpawnPointDimension();
        ServerWorld playerRespawnWorld = player.getServer().getWorld(playerRespawnWorldKey);

        player.sendMessage(Text.literal("MagicMirrorItem.playerRespawnPos == " + playerRespawnPos));
        player.sendMessage(Text.literal("MagicMirrorItem.playerRespawnWorld == " + playerRespawnWorld));

        if (playerRespawnPos != null && playerRespawnWorld != null) {
            Set<PositionFlag> flags = Set.of(PositionFlag.X, PositionFlag.Y, PositionFlag.Z);
            player.teleport(playerRespawnWorld,
                    playerRespawnPos.getX(), playerRespawnPos.getY(), playerRespawnPos.getZ(),
                    flags, player.getYaw(), player.getPitch());
        } else {
            player.sendMessage(Text.translatable("mischieve.magic_mirror.error.respawn_location"), true);
        }
    }
}
