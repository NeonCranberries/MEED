package com.cranberry.meed.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;

public record ModVersionRangeCondition(String modid, String versionRange) implements ICondition {

    public static final MapCodec<ModVersionRangeCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("modid").forGetter(ModVersionRangeCondition::modid),
            Codec.STRING.fieldOf("versionRange").forGetter(ModVersionRangeCondition::versionRange)
    ).apply(instance, ModVersionRangeCondition::new));

    @Override
    public boolean test(IContext context) {
        return ModList.get().getModContainerById(modid).map(container -> {
            try {
                ArtifactVersion current = container.getModInfo().getVersion();
                VersionRange range = VersionRange.createFromVersionSpec(versionRange);
                return range.containsVersion(current);
            } catch (InvalidVersionSpecificationException e) {
                return false;
            }
        }).orElse(false);
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}