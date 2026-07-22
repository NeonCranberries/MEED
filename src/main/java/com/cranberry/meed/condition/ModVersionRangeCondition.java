package com.cranberry.meed.condition;

import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.fml.ModList;
import net.minecraft.resources.ResourceLocation;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;

public class ModVersionRangeCondition implements ICondition {
    private final String modid;
    private final String versionRange;

    public ModVersionRangeCondition(String modid, String versionRange) {
        this.modid = modid;
        this.versionRange = versionRange;
    }

    public String getModid() { return modid; }
    public String getVersionRange() { return versionRange; }

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
    public ResourceLocation getID() {
        return ModVersionRangeConditionSerializer.ID;
    }

    @Override
    public String toString() {
        return "mod_version_range(\"" + modid + "\", \"" + versionRange + "\")";
    }
}