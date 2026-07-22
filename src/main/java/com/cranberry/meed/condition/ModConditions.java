package com.cranberry.meed.condition;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = "meed", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConditions {
    @SubscribeEvent
    public static void register(RegisterEvent event) {
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.RECIPE_SERIALIZERS)) {
            CraftingHelper.register(ModVersionRangeConditionSerializer.INSTANCE);
        }
    }
}