package com.example.nekoma.mixin.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Item.class)
public class RawMeatMixin {

    private static final Random RANDOM = new Random();

    @Inject(method = "finishUsing", at = @At("HEAD"))
    private void onFinishUsing(ItemStack stack, World world, net.minecraft.entity.LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (!(user instanceof PlayerEntity player)) return;

        Item food = stack.getItem();

        // Сырая говядина, свинина, баранина, крольчатина, курица
        if (food == Items.BEEF || food == Items.PORKCHOP || food == Items.MUTTON || food == Items.RABBIT || food == Items.CHICKEN) {
            applyRawMeatEffects(player);
        }
    }

    private void applyRawMeatEffects(PlayerEntity player) {
        float chance = RANDOM.nextFloat();

        if (chance < 0.05f) {
            // 5% — отравление
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200, 0));
        } else if (chance < 0.20f) {
            // 15% — голод
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0));
        }
        // 80% — ничего
    }
}