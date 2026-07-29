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

/**
 * Checks every registered MobEffect for a description lang key following
 * the "effect.<namespace>.<path>.description" convention, grouped by mod.
 *
 * Only checked against whatever language the running client currently
 * has active - a mod that only ships an en_us description would show as
 * "missing" to a player running a different language. Checking en_us
 * specifically regardless of active language would need reading the raw
 * lang resource directly instead of the live Language instance - not
 * done here, flagged as a known limitation instead.
 */
public class DescriptionAuditor {

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
                continue; // fully covered - don't list this mod at all

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