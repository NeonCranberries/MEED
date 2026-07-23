package com.cranberry.meed.condition;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModConditions {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS =
            DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, "meed");

    public static final Supplier<MapCodec<ModVersionRangeCondition>> MOD_VERSION_RANGE =
            CONDITION_CODECS.register("mod_version_range", () -> ModVersionRangeCondition.CODEC);
}