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
            ListTag enchantmentList = this.heldItem.getEnchantments();
            System.out.println(enchantmentList.getString(0));
            StringBuilder enchantments = new StringBuilder();
            for(int a = 0; a < enchantmentList.size();a++){
                String val = enchantmentList.getString(a);
                val = val.replace("{","");
                val = val.replace("}","");
                val = val.replace("s","");
                String[] arr = new String[2];
                arr = val.split(",");
                String[] level = arr[0].split(":");
                int enchantmentlevel = Integer.parseInt(level[1]);
                String[] enchantmentId = arr[1].split(":");
                String enchantmentName = I18n.translate(Enchantment.byRawId(Integer.parseInt(enchantmentId[1])).getTranslationKey());
                enchantments.append(enchantmentName);
                enchantments.append(" ");
                enchantments.append(enchantmentlevel);
            }

            System.out.println(enchantments);

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
