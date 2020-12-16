package me.drex.customtab.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.drex.customtab.mixin.PlayerListHeaderS2CPacketMixin;
import me.drex.customtab.util.PlayerList;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;

public class TabCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> tab = LiteralArgumentBuilder.literal("tab");
        {
            RequiredArgumentBuilder<ServerCommandSource, Text> text = CommandManager.argument("message", TextArgumentType.text());
            text.executes(TabCommand::updateHeader);
            LiteralArgumentBuilder<ServerCommandSource> header = LiteralArgumentBuilder.literal("header");
            header.then(text);
            tab.then(header);
        }
        {
            RequiredArgumentBuilder<ServerCommandSource, Text> text = CommandManager.argument("message", TextArgumentType.text());
            text.executes(TabCommand::updateFooter);
            LiteralArgumentBuilder<ServerCommandSource> footer = LiteralArgumentBuilder.literal("footer");
            footer.then(text);
            tab.then(footer);
        }
        dispatcher.register(tab);
    }

    private static int updateHeader(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        Text text = Texts.parse(ctx.getSource(), TextArgumentType.getTextArgument(ctx, "message"), ctx.getSource().getPlayer(), 0);
        PlayerList.header = text;
        PlayerList.update();
        ctx.getSource().sendFeedback(new LiteralText("Tab header has been set to: ").formatted(Formatting.WHITE).append(text), false);
        return 1;
    }

    private static int updateFooter(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        Text text = Texts.parse(ctx.getSource(), TextArgumentType.getTextArgument(ctx, "message"), ctx.getSource().getPlayer(), 0);
        PlayerList.footer = text;
        PlayerList.update();
        ctx.getSource().sendFeedback(new LiteralText("Tab footer has been set to: ").formatted(Formatting.WHITE).append(text), false);
        return 1;
    }

}
