package com.cranberry.meed.network;

import com.cranberry.meed.client.DescriptionAuditor;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class DescriptionAuditRequestPacket {

    public static void encode(DescriptionAuditRequestPacket packet, FriendlyByteBuf buffer) {
        // no payload
    }

    public static DescriptionAuditRequestPacket decode(FriendlyByteBuf buffer) {
        return new DescriptionAuditRequestPacket();
    }

    public static void handle(DescriptionAuditRequestPacket packet, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {

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

        ctx.get().setPacketHandled(true);
    }

}