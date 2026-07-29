package com.cranberry.meed;

import com.cranberry.meed.network.ModNetwork;
import com.cranberry.meed.server.MeedCommands;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(MeedMod.MODID)
public class MeedMod {

    public static final String MODID = "meed";

    public MeedMod() {

        ModNetwork.register();

        MinecraftForge.EVENT_BUS.register(MeedCommands.class);

    }
}