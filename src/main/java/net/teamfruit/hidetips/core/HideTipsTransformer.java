package net.teamfruit.hidetips.core;

import javax.annotation.Nullable;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import net.minecraft.launchwrapper.IClassTransformer;
import net.teamfruit.hidetips.Log;
import net.teamfruit.hidetips.asm.VisitorHelper;
import net.teamfruit.hidetips.asm.VisitorHelper.TransformProvider;

public class HideTipsTransformer implements IClassTransformer {
	@Override
	public @Nullable byte[] transform(final @Nullable String name, final @Nullable String transformedName, final @Nullable byte[] bytes) {
		if (bytes==null||name==null||transformedName==null)
			return bytes;

		if (transformedName.equals("net.minecraft.item.ItemStack"))
			return VisitorHelper.apply(bytes, name, new TransformProvider(ClassWriter.COMPUTE_FRAMES) {
				@Override
				public ClassVisitor createVisitor(final String name, final ClassVisitor cv) {
					Log.log.info(String.format("Patching ItemStack (class: %s)", name));
					return new ItemStackVisitor(name, cv);
				}
			});

		return bytes;
	}
}