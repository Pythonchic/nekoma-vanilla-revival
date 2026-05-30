package com.example.nekoma.mixin.entity;

import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PhantomEntity.class)
public abstract class PhantomEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onMobTick(CallbackInfo ci) {
        PhantomEntity self = (PhantomEntity) (Object) this;
        BlockPos pos = self.getBlockPos();
        World world = self.getEntityWorld();
        int light = world.getLightLevel(pos);

        if (light >= 7) {
            self.setTarget(null);
        }
    }
}