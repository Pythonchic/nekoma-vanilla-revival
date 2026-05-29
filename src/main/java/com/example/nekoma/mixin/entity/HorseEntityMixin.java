package com.example.nekoma.mixin.entity;

import com.example.nekoma.constants.HorseValues;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HorseEntity.class)
public abstract class HorseEntityMixin {

    // ========== Разведение ==========
    @Inject(method = "createChild", at = @At("RETURN"))
    private void onCreateChild(ServerWorld world, PassiveEntity other, CallbackInfoReturnable<PassiveEntity> cir) {
        PassiveEntity result = cir.getReturnValue();
        if (!(result instanceof HorseEntity child)) return;

        HorseEntity self = (HorseEntity) (Object) this;
        HorseEntity partner = (HorseEntity) other;

        double fatherSpeed = self.getAttributeBaseValue(EntityAttributes.MOVEMENT_SPEED);
        double motherSpeed = partner.getAttributeBaseValue(EntityAttributes.MOVEMENT_SPEED);

        double childSpeed = ((fatherSpeed + motherSpeed) / 2.0) * HorseValues.BREED_MULTIPLIER;

        if (childSpeed > HorseValues.BRED_MAX_SPEED) {
            childSpeed = HorseValues.BRED_MAX_SPEED;
        }

        child.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(childSpeed);
    }
}