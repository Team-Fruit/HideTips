package net.teamfruit.hidetips.core;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import net.teamfruit.hidetips.asm.DescHelper;
import net.teamfruit.hidetips.asm.MethodMatcher;

public class ItemStackVisitor extends ClassVisitor {
	private static class GetTooltipMethodVisitor extends MethodVisitor {
		public GetTooltipMethodVisitor(final @Nullable MethodVisitor mv, final @Nonnull String baseOwner) {
			super(Opcodes.ASM5, mv);
		}

		@Override
		public void visitCode() {
			visitVarInsn(Opcodes.ALOAD, 0);
			visitVarInsn(Opcodes.ALOAD, 1);
			visitVarInsn(Opcodes.ILOAD, 2);
			visitMethodInsn(Opcodes.INVOKESTATIC, "net/teamfruit/hidetips/GetTooltipHook", "getTooltip", DescHelper.toDesc(List.class, "net/minecraft/item/ItemStack", "net/minecraft/entity/player/EntityPlayer", boolean.class), false);
			visitInsn(Opcodes.ARETURN);
			//Log.log.info("Patched getTooltip Method");
			super.visitCode();
		}
	}

	private final @Nonnull String targetOwner;
	private final @Nonnull MethodMatcher targetMethod;
	private final @Nonnull String targetDesc;

	public ItemStackVisitor(final @Nonnull String obfClassName, final @Nonnull ClassVisitor cv) {
		super(Opcodes.ASM5, cv);
		this.targetOwner = obfClassName;
		this.targetDesc = DescHelper.toDesc(List.class, "net/minecraft/entity/player/EntityPlayer", boolean.class);
		this.targetMethod = new MethodMatcher(this.targetOwner, this.targetDesc, ASMDeobfNames.ItemStackGetTooltip);
	}

	@Override
	public @Nullable MethodVisitor visitMethod(final int access, final @Nullable String name, final @Nullable String desc, final @Nullable String signature, final @Nullable String[] exceptions) {
		final MethodVisitor parent = super.visitMethod(access, name, desc, signature, exceptions);
		if (name!=null&&desc!=null)
			//Log.log.info(String.format("%s + %s", name, desc));
			if (this.targetMethod.match(name, desc))
				//Log.log.info("Matched getTooltip Method");
				return new GetTooltipMethodVisitor(parent, this.targetOwner);
		return parent;
	}
}