package me.drex.customtab.util;

import me.drex.customtab.Mod;
import me.drex.customtab.mixin.PlayerListHeaderS2CPacketMixin;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class PlayerList {

    public static Text header = new LiteralText("");
    public static Text footer = new LiteralText("");

    public static void update() {
        for (ServerPlayerEntity player : Mod.server.getPlayerManager().getPlayerList()) {
            update(player);
        }
    }

    public static void update(ServerPlayerEntity player) {
        if (player.networkHandler == null) return;
        PlayerListHeaderS2CPacketMixin packet = (PlayerListHeaderS2CPacketMixin) new PlayerListHeaderS2CPacket();
        packet.setHeader(header);
        packet.setFooter(footer);
        player.networkHandler.sendPacket(packet);
    }

}
