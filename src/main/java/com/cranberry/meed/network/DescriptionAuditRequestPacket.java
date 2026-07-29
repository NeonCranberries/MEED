package com.cranberry.meed.network;

import com.cranberry.meed.client.DescriptionAuditor;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

// server -> client, no reply needed - no payload data at all (StreamCodec.unit)
public record DescriptionAuditRequestPacket() implements CustomPacketPayload {

    public static final Type<DescriptionAuditRequestPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("meed", "description_audit_request"));

    public static final StreamCodec<ByteBuf, DescriptionAuditRequestPacket> STREAM_CODEC =
            StreamCodec.unit(new DescriptionAuditRequestPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(DescriptionAuditRequestPacket payload, IPayloadContext context) {

        context.enqueueWork(() -> {

            if (Minecraft.getInstance().player == null)
                return;

            List<String> lines = DescriptionAuditor.buildReport();

            if (lines.isEmpty()) {

                Minecraft.getInstance().player.displayClientMessage(
                        Component.literal(
                                "[MEED] Every registered effect has a description"
                        ),
                        false
                );

                return;
            }

            Minecraft.getInstance().player.displayClientMessage(
                    Component.literal(
                            "[MEED] " + lines.size() + " mods missing effect descriptions"
                    ),
                    false
            );

            for (String line : lines) {
                Minecraft.getInstance().player.displayClientMessage(
                        Component.literal("[MEED] " + line),
                        false
                );
            }

        });

    }

}