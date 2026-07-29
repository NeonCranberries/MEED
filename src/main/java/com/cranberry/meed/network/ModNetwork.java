package com.cranberry.meed.network;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModNetwork {

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ModNetwork::onRegisterPayloadHandlers);
    }

    private static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {

        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(
                DescriptionAuditRequestPacket.TYPE,
                DescriptionAuditRequestPacket.STREAM_CODEC,
                DescriptionAuditRequestPacket::handle
        );

    }

}