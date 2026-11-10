// AI-GENERATED: Codex | 2026-11-10T04:31:26.881Z
package com.gregtechceu.gtceu.integration.jade.provider;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IHasCircuitSlot;
import com.gregtechceu.gtceu.common.item.IntCircuitBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class ProgrammableCircuitProvider extends BlockInfoProvider<IHasCircuitSlot> {

    public ProgrammableCircuitProvider() {
        super(GTCEu.id("programmed_circuit_info"));
    }

    @Override
    protected @Nullable IHasCircuitSlot getCapability(Level level, BlockPos pos) {
        return MetaMachine.getMachine(level, pos) instanceof IHasCircuitSlot slot ? slot : null;
    }

    @Override
    protected void write(CompoundTag data, IHasCircuitSlot slot, BlockAccessor block) {
        if (!slot.isCircuitSlotEnabled() || slot.getCircuitInventory().getSlots() == 0) return;
        int configuration = IntCircuitBehaviour.getCircuitConfiguration(slot.getCircuitInventory().getStackInSlot(0).copy());
        if (configuration > 0) data.putInt("Configuration", configuration);
    }

    @Override
    protected void addTooltip(CompoundTag data, ITooltip tooltip, Player player, BlockAccessor block,
                              BlockEntity blockEntity, IPluginConfig config) {
        if (!data.contains("Configuration")) return;
        int configuration = data.getInt("Configuration");
        tooltip.add(tooltip.getElementHelper().smallItem(IntCircuitBehaviour.stack(configuration)));
        tooltip.append(Component.translatable("gtceu.jade.programmed_circuit", configuration));
    }
}
