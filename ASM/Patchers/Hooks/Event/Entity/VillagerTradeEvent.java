/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.DragonAPI.ASM.Patchers.Hooks.Event.Entity;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import Reika.DragonAPI.ASM.Patchers.Patcher;
import Reika.DragonAPI.Libraries.Java.ReikaASMHelper;


public class VillagerTradeEvent extends Patcher {

	public VillagerTradeEvent() {
		super("net.minecraft.entity.passive.EntityVillager", "yv");
	}

	@Override
	protected void apply(ClassNode cn) {
		MethodNode m = ReikaASMHelper.getMethodByName(cn, "func_70933_a", "useRecipe", "(Lnet/minecraft/village/MerchantRecipe;)V");
		m.instructions.insert(new MethodInsnNode(Opcodes.INVOKESTATIC, "Reika/DragonAPI/Instantiable/Event/VillagerTradeEvent", "fire", "(Lnet/minecraft/entity/passive/EntityVillager;Lnet/minecraft/village/MerchantRecipe;)V", false));
		m.instructions.insert(new VarInsnNode(Opcodes.ALOAD, 1));
		m.instructions.insert(new VarInsnNode(Opcodes.ALOAD, 0));
	}

}
