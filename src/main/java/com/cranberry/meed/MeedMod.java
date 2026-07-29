package com.cranberry.meed;

import com.cranberry.meed.condition.ModConditions;
import com.cranberry.meed.network.ModNetwork;
import com.cranberry.meed.server.MeedCommands;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(MeedMod.MODID)
public class MeedMod {

    public static final String MODID = "meed";

    public MeedMod(IEventBus modEventBus, ModContainer container) {

        ModConditions.CONDITION_CODECS.register(modEventBus);

        ModNetwork.register(modEventBus);
        MeedCommands.register();

    }
}