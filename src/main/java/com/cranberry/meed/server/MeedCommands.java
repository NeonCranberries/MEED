package com.cranberry.meed.server;

import com.cranberry.meed.MeedLogger;
import com.cranberry.meed.network.DescriptionAuditRequestPacket;
import com.cranberry.meed.network.ModNetwork;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;

/**
 * Registered from MeedMod's constructor via
 * MinecraftForge.EVENT_BUS.register(MeedCommands.class) - all handlers
 * here are static so a Class registration (not an instance) is enough.
 * Future subcommands are just more .then(...) branches and more
 * private static run... methods.
 */
public class MeedCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {

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

        ModNetwork.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> target),
                new DescriptionAuditRequestPacket()
        );

        return 1;
    }

    /**
     * Lang data only exists client-side, so this needs a real client to
     * run against: the player who ran the command if one did, otherwise
     * the first connected player as a console/RCON fallback, otherwise
     * fails with an explanation.
     */
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