package com.dtteam.dynamictrees.client.tint;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.util.ARGB;

/**
 * Vanilla {@link net.minecraft.client.color.block.BlockColors} lookup used by falling-leaf particles and mods
 * that tint fractures from {@code getTintSource(state, index)}.
 * <p>
 * Tints are ARGB in 26.2, so colors are made opaque; a plain RGB value would render fully transparent.
 */
public record FunctionalBlockTintSource(int fallback, WorldColor color) implements BlockTintSource {

    @FunctionalInterface
    public interface WorldColor {
        int color(BlockState state, BlockAndTintGetter level, BlockPos pos);
    }

    @Override
    public int color(BlockState state) {
        return ARGB.opaque(fallback);
    }

    @Override
    public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
        if (level == null || pos == null) {
            return ARGB.opaque(fallback);
        }
        return ARGB.opaque(color.color(state, level, pos));
    }
}
