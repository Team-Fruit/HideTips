package net.teamfruit.hidetips;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GetTooltipHook {
	public static List<String> getTooltip(final ItemStack itemstack, final EntityPlayer player, final boolean f3h) {
		Log.log.info("Tooltip Aborted");
		final List<String> list = new ArrayList<String>();
		list.add("Tooltip Aborted");
		return list;
	}
}
