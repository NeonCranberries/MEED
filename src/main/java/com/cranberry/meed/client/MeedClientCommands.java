package com.cranberry.meed.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

import java.util.List;

@EventBusSubscriber(modid = "meed", value = Dist.CLIENT)
public class MeedClientCommands {

    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {

        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
                Commands.literal("meed")
                        .then(Commands.literal("missingdescriptions")
                                .executes(MeedClientCommands::runMissingDescriptions)
                        )
        );
    }

    private static int runMissingDescriptions(CommandContext<CommandSourceStack> context) {

        if (Minecraft.getInstance().player == null)
            return 0;

        List<String> lines = DescriptionAuditor.buildReport();

        if (lines.isEmpty()) {
            Minecraft.getInstance().player.displayClientMessage(
                    Component.literal("[MEED] Every registered effect has a description"), false
            );
            return 1;
        }

        Minecraft.getInstance().player.displayClientMessage(
                Component.literal("[MEED] " + lines.size() + " mods missing effect descriptions"), false
        );

        for (String line : lines) {
            Minecraft.getInstance().player.displayClientMessage(
                    Component.literal("[MEED] " + line), false
            );
        }

        return 1;
    }
}