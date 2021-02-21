package me.drex.customtab.mixin;

import me.drex.customtab.util.PlayerList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.scoreboard.ScoreboardState;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ScoreboardState.class)
public class ScoreboardStateMixin {

    @Inject(method = "readNbt", at = @At(value = "HEAD"))
    public void getTabData(CompoundTag tag, CallbackInfoReturnable<ScoreboardState> cir) {
        if (tag.contains("tab")) {
            CompoundTag tab = tag.getCompound("tab");
            PlayerList.header = Text.Serializer.fromJson(tab.getString("header"));
            PlayerList.footer = Text.Serializer.fromJson(tab.getString("footer"));
        }
    }

    @Inject(method = "writeNbt", at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void addTabData(CompoundTag tag, CallbackInfoReturnable<CompoundTag> cir) {
        CompoundTag tab = new CompoundTag();
        tab.putString("header", Text.Serializer.toJson(PlayerList.header));
        tab.putString("footer", Text.Serializer.toJson(PlayerList.footer));
        tag.put("tab", tab);
    }

}
