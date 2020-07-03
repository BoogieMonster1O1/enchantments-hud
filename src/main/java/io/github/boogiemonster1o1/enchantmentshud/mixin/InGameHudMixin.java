package io.github.boogiemonster1o1.enchantmentshud.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.Window;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListTag;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow @Final private MinecraftClient client;
    @Shadow private ItemStack heldItem;
    @Shadow private int heldItemTooltipFade;

    @Inject(method="method_2420",at=@At(value="INVOKE",target = "Lnet/minecraft/item/ItemStack;getName()Ljava/lang/String;"))
    public void enchantments(Window window,CallbackInfo ci){
        if (this.heldItemTooltipFade > 0 && this.heldItem != null && this.heldItem.hasEnchantments()) {
            ListTag enchantments = this.heldItem.getEnchantments();
            System.out.println(enchantments.get(0));

//            String string = this.heldItem.getName();
//            if (this.heldItem.hasCustomName()) {
//                string = Formatting.ITALIC + string;
//            }
//
//            int i = (window.getScaledWidth() - this.getFontRenderer().getStringWidth(string)) / 2;
//            int j = window.getScaledHeight() - 59;
//            if (!this.client.interactionManager.hasStatusBars()) {
//                j += 14;
//            }
//
//            int k = (int)((float)this.heldItemTooltipFade * 256.0F / 10.0F);
//            if (k > 255) {
//                k = 255;
//            }
//
//            if (k > 0) {
//                GlStateManager.pushMatrix();
//                GlStateManager.enableBlend();
//                GlStateManager.blendFuncSeparate(770, 771, 1, 0);
//                this.getFontRenderer().drawWithShadow(string, (float)i, (float)j, 16777215 + (k << 24));
//                GlStateManager.disableBlend();
//                GlStateManager.popMatrix();
//            }
        }
    }

    @Shadow
    public abstract TextRenderer getFontRenderer();
}
