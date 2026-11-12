// AI-GENERATED: Codex | 2026-11-12T17:56:05Z
package com.gregtechceu.gtceu.core.mixins.jei;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;

import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.ingredients.IIngredientRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "com.lowdragmc.lowdraglib.jei.ModularUIRecipeCategory$2", remap = false)
public abstract class LDLibRecipeSlotTooltipMixin implements IIngredientRenderer<Object> {

    // Newer JEI calls the player-aware overload. LDLib supplies its tooltip through the older overload.
    public void getTooltip(ITooltipBuilder tooltip, Object ingredient, Player player, TooltipFlag flag) {
        getTooltip(tooltip, ingredient, flag);
    }
}
