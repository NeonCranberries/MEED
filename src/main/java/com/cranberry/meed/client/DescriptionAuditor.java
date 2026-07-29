package com.cranberry.meed.client;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.locale.Language;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DescriptionAuditor {

    // only checks against whatever language is currently active, not en_us
    // specifically - a mod that only has an en_us description would show
    // as "missing" on a non-English client
    public static List<String> buildReport() {

        Map<String, List<ResourceLocation>> byMod = new TreeMap<>();

        for (MobEffect effect : BuiltInRegistries.MOB_EFFECT) {

            ResourceLocation id = BuiltInRegistries.MOB_EFFECT.getKey(effect);

            if (id == null)
                continue;

            byMod.computeIfAbsent(id.getNamespace(), k -> new ArrayList<>()).add(id);

        }

        Language language = Language.getInstance();

        List<String> lines = new ArrayList<>();

        for (Map.Entry<String, List<ResourceLocation>> entry : byMod.entrySet()) {

            String modId = entry.getKey();
            List<ResourceLocation> effects = entry.getValue();

            List<ResourceLocation> missing = new ArrayList<>();

            for (ResourceLocation effectID : effects) {

                String descKey = "effect." + effectID.getNamespace() + "." + effectID.getPath() + ".description";

                if (!language.has(descKey)) {
                    missing.add(effectID);
                }

            }

            if (missing.isEmpty())
                continue;

            if (missing.size() == effects.size()) {

                lines.add(modId + ": all " + effects.size() + " effect(s)");

            } else {

                String missingIds = missing.stream()
                        .map(ResourceLocation::toString)
                        .collect(Collectors.joining(", "));

                lines.add(
                        modId + ": " + missing.size() + "/" + effects.size()
                                + " missing descriptions - " + missingIds
                );

            }

        }

        return lines;
    }

}