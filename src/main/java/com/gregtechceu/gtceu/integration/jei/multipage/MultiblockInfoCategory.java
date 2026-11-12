package com.gregtechceu.gtceu.integration.jei.multipage;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.data.machines.GTMultiMachines;

import com.lowdragmc.lowdraglib.gui.ingredient.IRecipeIngredientSlot;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.jei.ModularUIRecipeCategory;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.gui.inputs.RecipeSlotUnderMouse;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.gui.widgets.ISlottedRecipeWidget;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MultiblockInfoCategory extends ModularUIRecipeCategory<MultiblockInfoWrapper> {

    public final static RecipeType<MultiblockInfoWrapper> RECIPE_TYPE = new RecipeType<>(GTCEu.id("multiblock_info"),
            MultiblockInfoWrapper.class);
    private final IDrawable background;
    private final IDrawable icon;

    public MultiblockInfoCategory(IJeiHelpers helpers) {
        IGuiHelper guiHelper = helpers.getGuiHelper();
        this.background = guiHelper.createBlankDrawable(160, 160);
        this.icon = helpers.getGuiHelper().createDrawableItemStack(GTMultiMachines.ELECTRIC_BLAST_FURNACE.asStack());
    }

    public static void registerRecipes(IRecipeRegistration registry) {
        registry.addRecipes(RECIPE_TYPE, GTRegistries.MACHINES.values().stream()
                .filter(MultiblockMachineDefinition.class::isInstance)
                .map(MultiblockMachineDefinition.class::cast)
                .filter(MultiblockMachineDefinition::isRenderXEIPreview)
                .map(MultiblockInfoWrapper::new)
                .toList());
    }

    // AI-GENERATED (modified): Codex | 2026-11-12T01:40:26Z
    @Override
    public void createRecipeExtras(@NotNull IRecipeExtrasBuilder builder, @NotNull MultiblockInfoWrapper recipe,
                                   @NotNull IFocusGroup focuses) {
        super.createRecipeExtras(builder, recipe, focuses);
        var previewSlots = bindPreviewSlots(builder.getRecipeSlots().getSlots(),
                recipe.modularUI.getFlatWidgetCollection());
        class ProxyRecipeWidget implements ISlottedRecipeWidget {

            private final ScreenPosition position = new ScreenPosition(0, 0);

            @Override
            public Optional<RecipeSlotUnderMouse> getSlotUnderMouse(double mouseX, double mouseY) {
                var panel = recipe.getWidget();
                var pos = panel.getSelfPosition();
                var size = panel.getSize();
                boolean inParent = Widget.isMouseOver(pos.x, pos.y, size.width, size.height, mouseX, mouseY);
                if (!inParent) return Optional.empty();
                for (Widget widget : recipe.modularUI.getFlatWidgetCollection()) {
                    var slot = previewSlots.get(widget);
                    if (slot == null || !widget.isVisible()) continue;
                    slot.setPosition(widget.getPositionX(), widget.getPositionY());
                    if (slot.isMouseOver(mouseX, mouseY)) {
                        return Optional.of(new RecipeSlotUnderMouse(slot, 0, 0));
                    }
                }
                return Optional.empty();
            }

            @Override
            public ScreenPosition getPosition() {
                return position;
            }
        }

        builder.addSlottedWidget(new ProxyRecipeWidget(), List.copyOf(previewSlots.values()));
    }

    // AI-GENERATED (added): Codex | 2026-11-12T01:40:26Z
    private static Map<Widget, IRecipeSlotDrawable> bindPreviewSlots(List<IRecipeSlotDrawable> slots,
                                                                    List<Widget> widgets) {
        Map<Widget, IRecipeSlotDrawable> bindings = new IdentityHashMap<>();
        for (var slot : slots) {
            String name = slot.getSlotName().orElse("");
            if (!name.startsWith("slot_")) continue;
            try {
                // LDLib names slots using the widget index at layout creation. The preview can later rebuild its widgets.
                int index = Integer.parseInt(name, 5, name.length(), 10);
                if (index >= 0 && index < widgets.size() && widgets.get(index) instanceof IRecipeIngredientSlot) {
                    bindings.putIfAbsent(widgets.get(index), slot);
                }
            } catch (NumberFormatException ignored) {
                // A slot without a valid LDLib index has no preview widget to bind.
            }
        }
        return bindings;
    }

    @Override
    public @Nullable ResourceLocation getRegistryName(@NotNull MultiblockInfoWrapper recipe) {
        return recipe.definition.getId();
    }

    @Override
    @NotNull
    public RecipeType<MultiblockInfoWrapper> getRecipeType() {
        return RECIPE_TYPE;
    }

    @NotNull
    @Override
    public Component getTitle() {
        return Component.translatable("gtceu.jei.multiblock_info");
    }

    @NotNull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @NotNull
    @Override
    public IDrawable getIcon() {
        return icon;
    }
}
