package com.tac.guns.client.settings;

import net.minecraft.client.Options;
import net.minecraft.client.gui.components.SliderButton;
import net.minecraft.client.ProgressOption;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;
import java.util.Optional;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class GunOptionSlider extends SliderButton
{
    private final ProgressOption option;

    public GunOptionSlider(Options settings, int x, int y, int width, int height, ProgressOption option, List<FormattedCharSequence> tooltip)
    {
        super(settings, x, y, width, height, option, tooltip);
        this.option = option;
    }

    @Override
    protected void applyValue()
    {
        this.option.set(this.options, this.option.toValue(this.value));
    }
}
