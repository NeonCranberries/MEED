package com.cranberry.meed;

import net.minecraftforge.fml.common.Mod;

@Mod(MeedMod.MODID)
public class MeedMod {

    public static final String MODID = "meed";

    public MeedMod() {
        // nothing needed here anymore — client commands self-register
        // via @Mod.EventBusSubscriber
    }
}