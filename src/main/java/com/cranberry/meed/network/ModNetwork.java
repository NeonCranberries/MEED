package com.cranberry.meed.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class ModNetwork {

    private static final String VERSION = "1";

    public static final SimpleChannel CHANNEL =
            NetworkRegistry.newSimpleChannel(
                    new ResourceLocation("meed", "network"),
                    () -> VERSION,
                    VERSION::equals,
                    VERSION::equals
            );

    public static void register() {

        int id = 0;

        CHANNEL.registerMessage(
                id++,
                DescriptionAuditRequestPacket.class,
                DescriptionAuditRequestPacket::encode,
                DescriptionAuditRequestPacket::decode,
                DescriptionAuditRequestPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );

    }

}