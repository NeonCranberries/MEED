package com.cranberry.meed.server;

import com.cranberry.meed.MeedLogger;
import com.cranberry.meed.network.DescriptionAuditRequestPacket;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class MeedCommands {

    public static void register() {
        NeoForge.EVENT_BUS.addListener(MeedCommands::onRegisterCommands);
    }

    private static void onRegisterCommands(RegisterCommandsEvent event) {

        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
                Commands.literal("meed")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("missingdescriptions")
                                .executes(MeedCommands::runMissingDescriptions)
                        )
        );

    }

    private static int runMissingDescriptions(CommandContext<CommandSourceStack> context) {

        CommandSourceStack source = context.getSource();

        ServerPlayer target = resolveTargetPlayer(source);

        if (target == null)
            return 0;

        MeedLogger.info("/meed missingdescriptions run by " + source.getTextName());

        PacketDistributor.sendToPlayer(target, new DescriptionAuditRequestPacket());

        return 1;
    }

    // lang data only exists client-side, so fall back to the first
    // connected player if run from console
    private static ServerPlayer resolveTargetPlayer(CommandSourceStack source) {

        if (source.getEntity() instanceof ServerPlayer player) {
            return player;
        }

        List<ServerPlayer> players = source.getServer().getPlayerList().getPlayers();

        if (players.isEmpty()) {

            source.sendFailure(Component.literal(
                    "[MEED] No players are connected - this needs a client to run against."
            ));

            return null;
        }

        ServerPlayer target = players.get(0);

        source.sendSuccess(
                () -> Component.literal(
                        "[MEED] Run from console - checking via "
                                + target.getGameProfile().getName()
                                + ", results will appear in their chat."
                ),
                true
        );

        return target;
    }

}