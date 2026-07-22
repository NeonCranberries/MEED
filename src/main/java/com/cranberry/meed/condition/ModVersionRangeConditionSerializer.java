package com.cranberry.meed.condition;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class ModVersionRangeConditionSerializer implements IConditionSerializer<ModVersionRangeCondition> {
    public static final ModVersionRangeConditionSerializer INSTANCE = new ModVersionRangeConditionSerializer();
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("meed", "mod_version_range");
    @Override
    public void write(JsonObject json, ModVersionRangeCondition value) {
        json.addProperty("modid", value.getModid());
        json.addProperty("versionRange", value.getVersionRange());
    }

    @Override
    public ModVersionRangeCondition read(JsonObject json) {
        String modid = GsonHelper.getAsString(json, "modid");
        String versionRange = GsonHelper.getAsString(json, "versionRange");
        return new ModVersionRangeCondition(modid, versionRange);
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }
}