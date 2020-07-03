package io.github.boogiemonster1o1.enchantmentshud.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.Window;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Formatting;
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

    private StringBuilder enchantments;

    @Inject(method="method_2420",at=@At(value="INVOKE",target = "Lnet/minecraft/item/ItemStack;getName()Ljava/lang/String;"))
    public void enchantments(Window window,CallbackInfo ci){
        if (this.heldItemTooltipFade > 0 && this.heldItem != null && this.heldItem.hasEnchantments()) {
            ListTag enchantmentList = this.heldItem.getEnchantments();
            StringBuilder enchantments = new StringBuilder();
            for(int a = 0; a < enchantmentList.size();a++){
                String val = enchantmentList.getString(a);
                val = val.replace("{","");
                val = val.replace("}","");
                val = val.replace("s","");
                String[] arr = val.split(",");
                String[] level = arr[0].split(":");
                int enchantmentLevel = Integer.parseInt(level[1]);
                String[] enchantmentId = arr[1].split(":");
                String enchantmentName = I18n.translate(Enchantment.byRawId(Integer.parseInt(enchantmentId[1])).getTranslationKey());
                enchantments.append(enchantmentName);
                enchantments.append(" ");
                enchantments.append(enchantmentLevel);
                enchantments.append("   ");
            }
            this.enchantments = enchantments;
            System.out.println(enchantments.toString());
        }
    }

    @Inject(method="method_2420",at=@At(value="INVOKE",target = "Lcom/mojang/blaze3d/platform/GlStateManager;disableBlend()V"))
    public void displayEnchantments(Window window,CallbackInfo ci){
        int ii = (window.getScaledWidth() - this.getFontRenderer().getStringWidth(enchantments.toString())) / 2;
        int jj = window.getScaledHeight() - 59;
        if (!this.client.interactionManager.hasStatusBars()) {
            jj += 14;
        }

        int kk = (int)((float)this.heldItemTooltipFade * 256.0F / 10.0F);
        if (kk > 255) {
            kk = 255;
        }
        String finalText = Formatting.BLUE + enchantments.toString() + Formatting.RESET;
        System.out.println(finalText);
        this.getFontRenderer().drawWithShadow(finalText, (float)ii, (float)jj + 5, 16777215 + (kk << 24));
    }

    @Shadow
    public abstract TextRenderer getFontRenderer();
}
