package com.example.nekoma.mixin.item;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.fabricmc.fabric.api.item.v1.FabricComponentMapBuilder;

import java.util.List;
import java.util.ArrayList;

import net.minecraft.item.consume.ConsumeEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class GlisteringMelonSliceMixin {

    @Inject(method = "getComponents", at = @At("RETURN"), cancellable = true)
    private void onGetComponents(CallbackInfoReturnable<ComponentMap> cir) {
        Item self = (Item) (Object) this;

        if (self == Items.GLISTERING_MELON_SLICE) {
            var builder = ComponentMap.builder().addAll(cir.getReturnValue());

            FoodComponent food = new FoodComponent.Builder()
                    .nutrition(5)
                    .saturationModifier(1.2f)
                    .alwaysEdible()
                    .build();

            ((FabricComponentMapBuilder) builder).getOrCreate(DataComponentTypes.FOOD, () -> food);

            StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 0);
            ApplyEffectsConsumeEffect consumeEffect = new ApplyEffectsConsumeEffect(effect, 1.0f);

            ConsumableComponent consumable = ConsumableComponent.builder()
                    .consumeSeconds(1.6f)
                    .consumeEffect(consumeEffect)
                    .build();

            ((FabricComponentMapBuilder) builder).getOrCreate(DataComponentTypes.CONSUMABLE, () -> consumable);

            cir.setReturnValue(builder.build());
        }
    }
}