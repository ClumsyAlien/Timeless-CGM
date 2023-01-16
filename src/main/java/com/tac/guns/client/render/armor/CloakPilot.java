package com.tac.guns.client.render.armor;// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

// Made with Blockbench 4.5.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class CloakPilot<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	//public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "cloakpilot"), "main");
	private final ModelPart Head;
	private final ModelPart Body;
	private final ModelPart Left_Arm;
	private final ModelPart Right_Arm;
	private final ModelPart Right_leg;
	private final ModelPart Left_Leg;

	public CloakPilot(ModelPart root) {
		this.Head = root.getChild("Head");
		this.Body = root.getChild("Body");
		this.Left_Arm = root.getChild("Left_Arm");
		this.Right_Arm = root.getChild("Right_Arm");
		this.Right_leg = root.getChild("Right_leg");
		this.Left_Leg = root.getChild("Left_Leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone9 = Head.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0345F, 4.5273F, -0.0698F, 0.0F, 0.0F));

		PartDefinition cube_r1 = bone9.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(88, 79).addBox(-2.5F, -3.75F, 0.269F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3229F, 0.0F, 0.0F));

		PartDefinition cube_r2 = bone9.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(117, 35).addBox(-1.0F, -4.0F, -0.5F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.2177F, 0.1064F, 0.4698F, 0.3498F, -0.3878F, -0.1371F));

		PartDefinition cube_r3 = bone9.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 44).addBox(-3.2807F, -4.75F, 3.0922F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0463F, -0.1586F, 0.474F, 0.8526F, -1.1358F, -0.804F));

		PartDefinition cube_r4 = bone9.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(60, 117).addBox(-1.0F, -4.0F, -0.5F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.2177F, 0.1064F, 0.4698F, 0.3498F, 0.3878F, 0.1371F));

		PartDefinition cube_r5 = bone9.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(48, 35).addBox(1.2807F, -4.75F, 3.0922F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0463F, -0.1586F, 0.474F, 0.8526F, 1.1358F, 0.804F));

		PartDefinition bone6 = Head.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(1.202F, -4.0884F, 4.0897F));

		PartDefinition cube_r6 = bone6.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(129, 59).addBox(0.5F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.3323F, 1.8628F, -0.4331F, 1.5708F, 0.8029F, 1.5708F));

		PartDefinition cube_r7 = bone6.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(105, 14).addBox(-5.5F, -2.0F, -0.5F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.3323F, 4.3188F, -7.0368F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r8 = bone6.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(42, 124).addBox(-5.1302F, -1.0F, -1.9602F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.702F, 1.8957F, 0.5929F, 0.1396F, 0.0F, 0.0F));

		PartDefinition cube_r9 = bone6.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(121, 130).addBox(-3.6627F, -1.0F, 2.5924F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.702F, 1.8957F, 0.5929F, 0.1929F, -0.7586F, -0.1336F));

		PartDefinition cube_r10 = bone6.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(70, 119).addBox(-4.1022F, -1.0F, 0.7797F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.702F, 1.8957F, 0.5929F, 0.1438F, -0.2419F, -0.0347F));

		PartDefinition cube_r11 = bone6.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(63, 129).addBox(-2.5F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9282F, 1.8628F, -0.4331F, 1.5708F, -0.8029F, -1.5708F));

		PartDefinition cube_r12 = bone6.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(107, 102).addBox(-2.5F, -1.0F, -0.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0198F, 3.3188F, -8.3298F, 0.0F, 0.9774F, 0.0F));

		PartDefinition cube_r13 = bone6.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(30, 117).addBox(-2.5F, -1.0F, -0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.7696F, 3.3188F, -9.9247F, 0.0F, 0.5585F, 0.0F));

		PartDefinition cube_r14 = bone6.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(117, 66).addBox(0.5F, -1.0F, -0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3655F, 3.3188F, -9.9247F, 0.0F, -0.5585F, 0.0F));

		PartDefinition cube_r15 = bone6.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(108, 53).addBox(-0.5F, -1.0F, -0.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.6157F, 3.3188F, -8.3298F, 0.0F, -0.9774F, 0.0F));

		PartDefinition cube_r16 = bone6.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(105, 85).addBox(0.5F, -2.0F, -0.5F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9282F, 4.3188F, -7.0368F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r17 = bone6.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(65, 125).addBox(4.1302F, -1.0F, -1.9602F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(109, 74).addBox(-2.5F, -1.0F, 0.4602F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.702F, 1.8957F, 0.5929F, 0.1396F, 0.0F, 0.0F));

		PartDefinition cube_r18 = bone6.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(10, 131).addBox(2.6627F, -1.0F, 2.5924F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.702F, 1.8957F, 0.5929F, 0.1929F, 0.7586F, 0.1336F));

		PartDefinition cube_r19 = bone6.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(119, 74).addBox(1.1022F, -1.0F, 0.7797F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.702F, 1.8957F, 0.5929F, 0.1438F, 0.2419F, 0.0347F));

		PartDefinition bone = Head.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(62, 112).addBox(-1.5F, -4.8125F, -6.375F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.375F, -0.3125F));

		PartDefinition cube_r20 = bone.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(40, 110).addBox(-1.5F, -2.5F, -0.5F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.4196F, -2.3125F, -5.0888F, 0.0F, 0.6283F, 0.0F));

		PartDefinition cube_r21 = bone.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(31, 112).addBox(-1.5F, -2.5F, -0.5F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.4196F, -2.3125F, -5.0888F, 0.0F, -0.6283F, 0.0F));

		PartDefinition bone4 = bone.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(126, 10).addBox(-1.5F, -2.2299F, -3.1137F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.125F))
				.texOffs(120, 4).addBox(-1.5F, -2.346F, -2.2299F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offset(-0.0172F, 1.9174F, -3.6988F));

		PartDefinition cube_r22 = bone4.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(120, 97).addBox(-1.5F, -1.5F, -2.0625F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r23 = bone4.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(96, 125).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(0.0F, -1.1049F, -2.3549F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r24 = bone4.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(58, 0).addBox(-0.4219F, -1.0F, -0.4375F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.6423F, -1.7299F, -1.6741F, 0.0F, -1.0996F, 0.0F));

		PartDefinition cube_r25 = bone4.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(28, 51).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.7157F, -1.7299F, -1.7075F, 0.0F, -0.4363F, 0.0F));

		PartDefinition cube_r26 = bone4.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(86, 125).addBox(-0.5781F, -1.0F, -0.4375F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.6767F, -1.7299F, -1.6741F, 0.0F, 1.0996F, 0.0F));

		PartDefinition cube_r27 = bone4.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(92, 125).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, -1.7299F, -1.7075F, 0.0F, 0.4363F, 0.0F));

		PartDefinition cube_r28 = bone4.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(125, 114).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(0.0F, -2.3549F, -2.3549F, -0.7854F, 0.0F, 0.0F));

		PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(2.7036F, -5.1679F, -2.8664F));

		PartDefinition cube_r29 = bone2.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(129, 2).addBox(-0.5F, 0.7071F, -0.4844F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.8295F, 2.2822F, -2.1496F, 0.0F, 0.8029F, 0.0F));

		PartDefinition cube_r30 = bone2.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(42, 128).addBox(-0.8536F, 0.5607F, -0.4844F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(-6.7396F, 2.1054F, -2.0628F, -0.632F, 0.5336F, -0.9637F));

		PartDefinition cube_r31 = bone2.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(8, 119).addBox(-0.7929F, -1.0F, -0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(-6.7036F, 2.9804F, -2.1F, 0.0F, 0.8029F, 0.0F));

		PartDefinition cube_r32 = bone2.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(17, 128).addBox(0.5607F, -0.8536F, -0.4687F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(-6.7508F, 2.8554F, -2.0736F, -0.632F, 0.5336F, -0.9637F));

		PartDefinition cube_r33 = bone2.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(42, 131).addBox(-0.5F, -1.7071F, -0.4688F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.8407F, 2.6786F, -2.1605F, 0.0F, 0.8029F, 0.0F));

		PartDefinition cube_r34 = bone2.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(117, 18).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-8.1291F, 4.7736F, 4.8605F, 1.5708F, 0.6458F, 1.5708F));

		PartDefinition cube_r35 = bone2.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(101, 71).addBox(-3.5F, -1.0F, -0.5F, 5.0F, 2.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-8.1291F, 5.5233F, 1.3569F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r36 = bone2.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(94, 35).addBox(-3.5F, -2.0F, -0.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-8.3166F, 4.8983F, 1.3569F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r37 = bone2.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(96, 47).addBox(-1.5F, -2.3125F, -0.5F, 3.0F, 4.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-7.5685F, 4.8983F, -0.8955F, 0.0F, 0.9294F, 0.0F));

		PartDefinition cube_r38 = bone2.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(109, 57).addBox(-2.0F, -1.5F, -0.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-6.1988F, 1.1109F, 8.0101F, 2.8355F, 0.1856F, 3.0996F));

		PartDefinition cube_r39 = bone2.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(36, 93).addBox(-2.0F, -1.5F, -0.5F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-6.0202F, 2.3937F, 7.3466F, -2.5052F, 0.1856F, 3.0996F));

		PartDefinition cube_r40 = bone2.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(129, 27).addBox(0.0F, -1.5F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-7.8193F, 1.1105F, 7.9264F, 2.377F, 1.1287F, 2.4436F));

		PartDefinition cube_r41 = bone2.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(0, 119).addBox(-1.0F, -1.5F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-7.767F, 4.4671F, 6.0746F, 1.902F, 1.2653F, 1.4038F));

		PartDefinition cube_r42 = bone2.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(16, 117).addBox(-1.0F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-8.4118F, 1.9302F, 5.252F, 1.3959F, 1.2653F, 1.4038F));

		PartDefinition cube_r43 = bone2.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(107, 0).addBox(-3.0F, -1.5F, -0.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-8.1727F, 2.4715F, 0.6894F, 0.0F, 1.5184F, 0.0F));

		PartDefinition cube_r44 = bone2.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(125, 0).addBox(-0.75F, -1.5F, -0.4375F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-7.8317F, 2.4715F, -0.84F, 0.0F, 1.0297F, 0.0F));

		PartDefinition cube_r45 = bone2.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(129, 80).addBox(-0.5F, 0.7071F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.411F, 2.2822F, -2.1388F, 0.0F, -0.8029F, 0.0F));

		PartDefinition cube_r46 = bone2.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(37, 129).addBox(-0.1464F, 0.5607F, -0.4844F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(1.3323F, 2.1054F, -2.0628F, -0.632F, -0.5336F, 0.9637F));

		PartDefinition cube_r47 = bone2.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(120, 10).addBox(-1.2071F, -1.0F, -0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(1.2963F, 2.9804F, -2.1F, 0.0F, -0.8029F, 0.0F));

		PartDefinition cube_r48 = bone2.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(129, 77).addBox(-1.5607F, -0.8536F, -0.4844F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(1.3323F, 2.8554F, -2.0628F, -0.632F, -0.5336F, 0.9637F));

		PartDefinition cube_r49 = bone2.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(131, 65).addBox(-0.5F, -1.7071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.411F, 2.6786F, -2.1388F, 0.0F, -0.8029F, 0.0F));

		PartDefinition cube_r50 = bone2.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(80, 117).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(2.7219F, 4.7736F, 4.8605F, 1.5708F, -0.6458F, -1.5708F));

		PartDefinition cube_r51 = bone2.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(102, 62).addBox(-1.5F, -1.0F, -0.5F, 5.0F, 2.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(2.7219F, 5.5233F, 1.3569F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r52 = bone2.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(95, 0).addBox(-1.5F, -2.0F, -0.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(2.9094F, 4.8983F, 1.3569F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r53 = bone2.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(98, 53).addBox(-1.5F, -2.3125F, -0.5F, 3.0F, 4.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(2.1612F, 4.8983F, -0.8955F, 0.0F, -0.9294F, 0.0F));

		PartDefinition cube_r54 = bone2.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(111, 9).addBox(-2.0F, -1.5F, -0.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(0.7916F, 1.1109F, 8.0101F, 2.8355F, -0.1856F, -3.0996F));

		PartDefinition cube_r55 = bone2.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(94, 30).addBox(-2.0F, -1.5F, -0.5F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(0.613F, 2.3937F, 7.3466F, -2.5052F, -0.1856F, -3.0996F));

		PartDefinition cube_r56 = bone2.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(74, 129).addBox(-2.0F, -1.5F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(2.412F, 1.1105F, 7.9264F, 2.377F, -1.1287F, -2.4436F));

		PartDefinition cube_r57 = bone2.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(119, 62).addBox(-2.0F, -1.5F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(2.3597F, 4.4671F, 6.0746F, 1.902F, -1.2653F, -1.4038F));

		PartDefinition cube_r58 = bone2.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(88, 117).addBox(-2.0F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(3.0045F, 1.9302F, 5.252F, 1.3959F, -1.2653F, -1.4038F));

		PartDefinition cube_r59 = bone2.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(123, 90).addBox(-1.5F, -0.5F, -0.6875F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-8.4354F, 2.0965F, 0.9259F, 1.0317F, 1.3673F, 1.0317F));

		PartDefinition cube_r60 = bone2.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(123, 112).addBox(-1.5F, -0.5F, -0.6875F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(3.0282F, 2.0965F, 0.9259F, 1.0317F, -1.3673F, -1.0317F));

		PartDefinition cube_r61 = bone2.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(62, 108).addBox(-1.0F, -1.5F, -0.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(2.7654F, 2.4715F, 0.6894F, 0.0F, -1.5184F, 0.0F));

		PartDefinition cube_r62 = bone2.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(0, 126).addBox(-1.25F, -1.5F, -0.4375F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(2.4245F, 2.4715F, -0.84F, 0.0F, -1.0297F, 0.0F));

		PartDefinition bone5 = bone.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(119, 120).addBox(-3.5F, -5.6875F, -6.75F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, -0.1875F));

		PartDefinition cube_r63 = bone5.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(80, 129).addBox(-1.0F, -1.5625F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -3.75F, -5.9375F, 0.0524F, 0.0F, 0.0F));

		PartDefinition cube_r64 = bone5.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(119, 57).addBox(-1.5F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0636F, -4.6875F, -3.7156F, 0.0F, 1.2872F, 0.0F));

		PartDefinition cube_r65 = bone5.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(38, 119).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5695F, -4.1875F, -5.5855F, 0.0F, 0.5061F, 0.0F));

		PartDefinition cube_r66 = bone5.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(119, 22).addBox(-1.5F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.4585F, -6.0076F, -5.1756F, -0.5524F, 0.5253F, -0.2998F));

		PartDefinition cube_r67 = bone5.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(117, 130).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.6898F, -6.1515F, -3.3712F, -1.1033F, 0.5635F, -0.8136F));

		PartDefinition cube_r68 = bone5.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(52, 129).addBox(-0.5F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.1194F, -5.097F, -3.3883F, -0.9068F, 0.9564F, -0.807F));

		PartDefinition cube_r69 = bone5.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(119, 14).addBox(-1.5F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.3335F, -7.1116F, -4.2058F, -0.9816F, 0.4329F, -0.5605F));

		PartDefinition cube_r70 = bone5.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(97, 130).addBox(-0.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4112F, -5.5293F, -2.5486F, -1.3868F, 0.3188F, -1.035F));

		PartDefinition cube_r71 = bone5.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(124, 69).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.7071F, -7.6405F, -3.359F, -1.3057F, 0.2562F, -0.7511F));

		PartDefinition cube_r72 = bone5.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(124, 46).addBox(-1.5F, 0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5481F, -5.375F, -3.7724F, 0.0F, 0.9774F, 0.0F));

		PartDefinition cube_r73 = bone5.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(124, 17).addBox(-1.5F, -0.375F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.2994F, -4.8518F, -3.6046F, -0.925F, 0.9774F, 0.0F));

		PartDefinition cube_r74 = bone5.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(14, 131).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6898F, -6.1515F, -3.3712F, -1.1033F, -0.5635F, 0.8136F));

		PartDefinition cube_r75 = bone5.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(15, 121).addBox(-1.5F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3335F, -7.1116F, -4.2058F, -0.9816F, -0.4329F, 0.5605F));

		PartDefinition cube_r76 = bone5.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(119, 102).addBox(-1.5F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0636F, -4.6875F, -3.7156F, 0.0F, -1.2872F, 0.0F));

		PartDefinition cube_r77 = bone5.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(125, 19).addBox(-1.5F, -0.375F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2994F, -4.8518F, -3.6046F, -0.925F, -0.9774F, 0.0F));

		PartDefinition cube_r78 = bone5.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(125, 67).addBox(-1.5F, 0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5481F, -5.375F, -3.7724F, 0.0F, -0.9774F, 0.0F));

		PartDefinition cube_r79 = bone5.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(111, 120).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5695F, -4.1875F, -5.5855F, 0.0F, -0.5061F, 0.0F));

		PartDefinition cube_r80 = bone5.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(119, 85).addBox(-2.0F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -6.3515F, -5.8505F, -0.4712F, 0.0F, 0.0F));

		PartDefinition cube_r81 = bone5.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(129, 74).addBox(-1.5F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.1194F, -5.097F, -3.3883F, -0.9068F, -0.9564F, 0.807F));

		PartDefinition cube_r82 = bone5.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(30, 121).addBox(-1.5F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4585F, -6.0076F, -5.1756F, -0.5524F, -0.5253F, 0.2998F));

		PartDefinition cube_r83 = bone5.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(103, 130).addBox(-1.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.4112F, -5.5293F, -2.5486F, -1.3868F, -0.3188F, 1.035F));

		PartDefinition cube_r84 = bone5.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(125, 72).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7071F, -7.6405F, -3.359F, -1.3057F, -0.2562F, 0.7511F));

		PartDefinition cube_r85 = bone5.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(116, 30).addBox(-2.5F, 0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -8.8524F, -2.753F, -1.2043F, 0.0F, 0.0F));

		PartDefinition cube_r86 = bone5.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(121, 25).addBox(-2.5F, -0.5F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -8.0762F, -4.3819F, -0.8552F, 0.0F, 0.0F));

		PartDefinition bone3 = Head.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(9, 37).addBox(-2.0F, -11.0625F, -4.5625F, 4.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, 0.875F));

		PartDefinition cube_r87 = bone3.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(48, 35).addBox(-1.5F, -0.5F, -2.8125F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.817F, -10.3795F, -1.75F, 0.0F, 0.0F, -1.0472F));

		PartDefinition cube_r88 = bone3.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(27, 49).addBox(-0.5F, -0.5F, -2.8125F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.817F, -10.3795F, -1.75F, 0.0F, 0.0F, 1.0472F));

		PartDefinition cube_r89 = bone3.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(123, 118).addBox(-1.0F, 1.5F, 0.9375F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -6.5662F, 3.3988F, -0.1047F, 0.0F, 0.0F));

		PartDefinition cube_r90 = bone3.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(90, 41).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.1351F, 3.7103F, 0.192F, 0.0F, 0.0F));

		PartDefinition cube_r91 = bone3.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(80, 110).addBox(-2.0F, -1.1875F, -0.8906F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(111, 95).addBox(3.0625F, -1.1875F, -0.8906F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5313F, -7.1008F, 2.0749F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r92 = bone3.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(74, 125).addBox(-3.0F, -0.9375F, 0.1094F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(80, 125).addBox(5.0625F, -0.9375F, 0.1094F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5313F, -7.7924F, 1.7877F, -1.0821F, 0.0F, 0.0F));

		PartDefinition cube_r93 = bone3.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(48, 110).addBox(-3.0F, -0.9375F, 0.1094F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(19, 112).addBox(4.125F, -0.9375F, 0.1094F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0625F, -8.7924F, 1.7877F, -1.0821F, 0.0F, 0.0F));

		PartDefinition cube_r94 = bone3.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(110, 4).addBox(-2.0F, -1.1875F, -0.8906F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(112, 32).addBox(2.125F, -1.1875F, -0.8906F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0625F, -8.1008F, 2.0749F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r95 = bone3.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(109, 79).addBox(-2.0F, -1.1875F, -0.8906F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(112, 41).addBox(0.5F, -1.1875F, -0.8906F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, -8.6636F, 2.0761F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r96 = bone3.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(76, 19).addBox(-2.0F, -1.1875F, -0.8906F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(8, 81).addBox(0.5F, -1.1875F, -0.8906F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, -8.3549F, 2.0377F, -1.0821F, 0.0F, 0.0F));

		PartDefinition cube_r97 = bone3.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(50, 82).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.2251F, 3.3723F, -0.8552F, 0.0F, 0.0F));

		PartDefinition cube_r98 = bone3.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(0, 61).addBox(-1.0F, -0.875F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.9653F, -5.5168F, -1.1875F, 0.0F, 0.0F, 0.1222F));

		PartDefinition cube_r99 = bone3.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(59, 24).addBox(-1.0F, -1.875F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8534F, -5.7829F, -1.1875F, 0.0F, 0.0F, -0.733F));

		PartDefinition cube_r100 = bone3.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(42, 43).addBox(-3.0F, -1.5F, -3.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0518F, -8.2708F, -1.1875F, 0.0F, 0.0F, -1.1126F));

		PartDefinition cube_r101 = bone3.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(30, 57).addBox(-0.7656F, -1.9219F, -3.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.4121F, -8.4528F, -1.1875F, 0.0F, 0.0F, -0.4625F));

		PartDefinition cube_r102 = bone3.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(62, 47).addBox(-1.0F, -1.875F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8534F, -5.7829F, -1.1875F, 0.0F, 0.0F, 0.733F));

		PartDefinition cube_r103 = bone3.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(56, 63).addBox(-1.0F, -0.875F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.9653F, -5.5168F, -1.1875F, 0.0F, 0.0F, -0.1222F));

		PartDefinition cube_r104 = bone3.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(46, 57).addBox(-1.2344F, -1.9219F, -3.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.4121F, -8.4528F, -1.1875F, 0.0F, 0.0F, 0.4625F));

		PartDefinition cube_r105 = bone3.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(0, 45).addBox(-1.0F, -1.5F, -3.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0518F, -8.2708F, -1.1875F, 0.0F, 0.0F, 1.1126F));

		PartDefinition bone7 = Head.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r106 = bone7.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(117, 113).addBox(-1.0F, 0.2473F, -1.1924F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(0.0F, -7.1223F, 6.0674F, -1.3832F, 0.0F, 0.0F));

		PartDefinition cube_r107 = bone7.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(109, 130).addBox(-0.5F, -1.974F, -0.6357F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.125F))
				.texOffs(31, 131).addBox(-0.5F, -0.1615F, -0.6357F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -7.1223F, 6.0674F, -1.453F, 0.0F, 0.0F));

		PartDefinition cube_r108 = bone7.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(90, 125).addBox(-0.5F, 0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 130).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-5.375F, -9.4955F, -1.8726F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r109 = bone7.addOrReplaceChild("cube_r109", CubeListBuilder.create().texOffs(84, 125).addBox(-0.5F, 0.8125F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(49, 115).addBox(-0.5F, -0.6875F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-5.375F, -9.4955F, 1.1274F, -0.2637F, 0.0796F, -0.7931F));

		PartDefinition cube_r110 = bone7.addOrReplaceChild("cube_r110", CubeListBuilder.create().texOffs(78, 125).addBox(-0.75F, 0.625F, 0.0625F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(12, 104).addBox(-0.75F, -0.625F, 0.0625F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-4.625F, -9.4955F, 3.6899F, -0.6133F, 0.2227F, -0.8519F));

		PartDefinition cube_r111 = bone7.addOrReplaceChild("cube_r111", CubeListBuilder.create().texOffs(131, 0).addBox(-0.25F, 0.625F, 0.0625F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(47, 130).addBox(-0.25F, -0.625F, 0.0625F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(4.625F, -9.4955F, 3.6899F, -0.6133F, -0.2227F, 0.8519F));

		PartDefinition cube_r112 = bone7.addOrReplaceChild("cube_r112", CubeListBuilder.create().texOffs(18, 131).addBox(-0.5F, 0.8125F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(58, 130).addBox(-0.5F, -0.6875F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(5.375F, -9.4955F, 1.1274F, -0.2637F, -0.0796F, 0.7931F));

		PartDefinition cube_r113 = bone7.addOrReplaceChild("cube_r113", CubeListBuilder.create().texOffs(27, 131).addBox(-0.5F, 0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(69, 130).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(5.375F, -9.4955F, -1.8726F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r114 = bone7.addOrReplaceChild("cube_r114", CubeListBuilder.create().texOffs(113, 130).addBox(-0.5F, -1.7368F, -0.6061F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(0.0F, -11.3348F, 3.4467F, -0.5934F, 0.0F, 0.0F));

		PartDefinition cube_r115 = bone7.addOrReplaceChild("cube_r115", CubeListBuilder.create().texOffs(131, 38).addBox(-0.5F, 0.1209F, -0.6537F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -11.3348F, 3.4467F, -0.4189F, 0.0F, 0.0F));

		PartDefinition cube_r116 = bone7.addOrReplaceChild("cube_r116", CubeListBuilder.create().texOffs(118, 70).addBox(-1.0F, 0.5223F, -1.2592F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(0.0F, -11.3348F, 3.4467F, -0.2443F, 0.0F, 0.0F));

		PartDefinition bone43 = Head.addOrReplaceChild("bone43", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r117 = bone43.addOrReplaceChild("cube_r117", CubeListBuilder.create().texOffs(78, 86).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(0.9993F, -7.2836F, -7.0773F, -0.392F, 0.1574F, 0.7226F));

		PartDefinition cube_r118 = bone43.addOrReplaceChild("cube_r118", CubeListBuilder.create().texOffs(61, 81).addBox(-0.6875F, -0.6875F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(0.875F, -8.5F, -6.5625F, -0.4128F, -0.6134F, -0.6785F));

		PartDefinition cube_r119 = bone43.addOrReplaceChild("cube_r119", CubeListBuilder.create().texOffs(74, 6).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(1.9368F, -2.5336F, -6.2023F, 0.0F, -0.5367F, 0.0F));

		PartDefinition cube_r120 = bone43.addOrReplaceChild("cube_r120", CubeListBuilder.create().texOffs(30, 61).addBox(-0.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(5.7493F, -3.0336F, -2.9523F, 0.0F, -1.5184F, 0.0F));

		PartDefinition cube_r121 = bone43.addOrReplaceChild("cube_r121", CubeListBuilder.create().texOffs(45, 29).addBox(-3.5625F, -1.5F, 0.3125F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(7.1987F, -0.4571F, -2.3071F, 0.0F, -0.9294F, 0.0F));

		PartDefinition cube_r122 = bone43.addOrReplaceChild("cube_r122", CubeListBuilder.create().texOffs(38, 49).addBox(2.5625F, -1.5F, 0.3125F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(-7.1987F, -0.4571F, -2.3071F, 0.0F, 0.9294F, 0.0F));

		PartDefinition cube_r123 = bone43.addOrReplaceChild("cube_r123", CubeListBuilder.create().texOffs(44, 35).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(6.0517F, -5.2211F, 1.7338F, 1.4112F, -1.2352F, -1.42F));

		PartDefinition cube_r124 = bone43.addOrReplaceChild("cube_r124", CubeListBuilder.create().texOffs(36, 25).addBox(-0.5F, -0.375F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(5.9763F, -5.7966F, 2.9346F, 2.2414F, -1.1428F, -2.3059F));

		PartDefinition cube_r125 = bone43.addOrReplaceChild("cube_r125", CubeListBuilder.create().texOffs(29, 48).addBox(-0.5F, -0.375F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-5.9763F, -5.7966F, 2.9346F, 2.2414F, 1.1428F, 2.3059F));

		PartDefinition cube_r126 = bone43.addOrReplaceChild("cube_r126", CubeListBuilder.create().texOffs(14, 56).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-6.0517F, -5.2211F, 1.7338F, 1.4112F, 1.2352F, 1.42F));

		PartDefinition cube_r127 = bone43.addOrReplaceChild("cube_r127", CubeListBuilder.create().texOffs(80, 63).addBox(-1.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-5.7493F, -3.0336F, -2.9523F, 0.0F, 1.5184F, 0.0F));

		PartDefinition cube_r128 = bone43.addOrReplaceChild("cube_r128", CubeListBuilder.create().texOffs(20, 74).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(-1.9368F, -2.5336F, -6.2023F, 0.0F, 0.5367F, 0.0F));

		PartDefinition cube_r129 = bone43.addOrReplaceChild("cube_r129", CubeListBuilder.create().texOffs(89, 30).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(-0.9993F, -7.2836F, -7.0773F, -0.392F, -0.1574F, -0.7226F));

		PartDefinition cube_r130 = bone43.addOrReplaceChild("cube_r130", CubeListBuilder.create().texOffs(93, 11).addBox(-1.3125F, -0.6875F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(-0.875F, -8.5F, -6.5625F, -0.4128F, 0.6134F, 0.6785F));

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0625F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone27 = Body.addOrReplaceChild("bone27", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 3.0F, -5.3125F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.375F))
				.texOffs(36, 23).addBox(-2.0F, 1.25F, -5.3125F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(11, 66).addBox(-4.25F, -0.5F, -5.8125F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0625F))
				.texOffs(7, 104).addBox(6.5F, -0.5F, -5.1875F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.5F))
				.texOffs(40, 7).addBox(-1.5F, 6.0F, -4.1875F, 7.0F, 3.0F, 4.0F, new CubeDeformation(0.375F))
				.texOffs(66, 118).addBox(-0.5625F, -2.5F, -0.875F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0625F))
				.texOffs(16, 29).addBox(3.5625F, -2.5F, -0.875F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0625F))
				.texOffs(38, 103).addBox(0.5F, -2.5F, -1.5F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.375F))
				.texOffs(74, 54).addBox(-1.0F, -2.5F, -1.75F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.375F))
				.texOffs(59, 35).addBox(-1.5F, -2.5F, -2.375F, 7.0F, 6.0F, 1.0F, new CubeDeformation(0.375F)), PartPose.offset(-2.0F, 2.9375F, 3.3125F));

		PartDefinition cube_r131 = bone27.addOrReplaceChild("cube_r131", CubeListBuilder.create().texOffs(128, 41).addBox(0.591F, 0.591F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1875F)), PartPose.offsetAndRotation(4.5625F, 1.0625F, -0.0625F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r132 = bone27.addOrReplaceChild("cube_r132", CubeListBuilder.create().texOffs(128, 33).addBox(-2.591F, -2.591F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1875F)), PartPose.offsetAndRotation(4.5625F, 1.5F, -0.0625F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r133 = bone27.addOrReplaceChild("cube_r133", CubeListBuilder.create().texOffs(5, 129).addBox(0.591F, -2.591F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1875F)), PartPose.offsetAndRotation(-0.5625F, 1.5F, -0.0625F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r134 = bone27.addOrReplaceChild("cube_r134", CubeListBuilder.create().texOffs(33, 128).addBox(-0.5F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(3.4375F, 2.8125F, 0.3125F, 0.0698F, 0.0F, 1.5708F));

		PartDefinition cube_r135 = bone27.addOrReplaceChild("cube_r135", CubeListBuilder.create().texOffs(128, 126).addBox(-1.5F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(0.5625F, 2.8125F, 0.3125F, 0.0698F, 0.0F, -1.5708F));

		PartDefinition cube_r136 = bone27.addOrReplaceChild("cube_r136", CubeListBuilder.create().texOffs(129, 24).addBox(-2.591F, 0.591F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1875F)), PartPose.offsetAndRotation(-0.5625F, 1.0625F, -0.0625F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r137 = bone27.addOrReplaceChild("cube_r137", CubeListBuilder.create().texOffs(74, 61).addBox(-1.5F, 0.0299F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(56, 47).addBox(-2.0F, -0.9701F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 8.0951F, -6.0F, -0.1571F, 0.0F, 0.0F));

		PartDefinition bone29 = Body.addOrReplaceChild("bone29", CubeListBuilder.create().texOffs(105, 118).addBox(-1.0F, -2.9219F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(124, 52).addBox(-1.0F, 0.7656F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.125F))
				.texOffs(130, 88).addBox(-1.0F, -1.9219F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.125F))
				.texOffs(130, 53).addBox(-1.0F, -0.4219F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(-0.3605F, 2.828F, -4.2795F, 0.0F, 0.0F, 1.4704F));

		PartDefinition bone8 = Body.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(40, 14).addBox(-3.0F, 1.1875F, -2.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(67, 0).addBox(-3.5F, 2.1875F, -2.0F, 7.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(78, 6).addBox(-3.0F, 2.1875F, -2.8125F, 6.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.375F, -1.0F));

		PartDefinition cube_r138 = bone8.addOrReplaceChild("cube_r138", CubeListBuilder.create().texOffs(89, 22).addBox(0.5F, -2.125F, -0.6563F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(71, 104).addBox(0.5F, -2.125F, -0.7188F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-0.375F, 6.25F, -4.0313F, 0.0F, -0.1222F, 0.0F));

		PartDefinition cube_r139 = bone8.addOrReplaceChild("cube_r139", CubeListBuilder.create().texOffs(89, 67).addBox(0.5F, -2.25F, -0.6563F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(46, 105).addBox(0.5F, -2.25F, -0.7188F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-3.5F, 6.25F, -4.0313F, 0.0F, 0.1047F, 0.0F));

		PartDefinition cube_r140 = bone8.addOrReplaceChild("cube_r140", CubeListBuilder.create().texOffs(88, 52).addBox(-1.5F, -2.25F, -0.9687F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(104, 44).addBox(-1.5F, -2.25F, -1.0312F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(3.4012F, 7.0625F, -2.3544F, 0.0F, -1.117F, 0.0F));

		PartDefinition cube_r141 = bone8.addOrReplaceChild("cube_r141", CubeListBuilder.create().texOffs(81, 105).addBox(-1.5F, -2.125F, -1.0312F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0625F))
				.texOffs(89, 86).addBox(-1.5F, -2.125F, -0.9687F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.3387F, 6.25F, -2.4169F, 0.0F, 1.117F, 0.0F));

		PartDefinition bone10 = Body.addOrReplaceChild("bone10", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition cube_r142 = bone10.addOrReplaceChild("cube_r142", CubeListBuilder.create().texOffs(61, 86).addBox(-0.5F, -3.5F, -1.5F, 1.0F, 7.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-5.5313F, 9.625F, -0.1875F, 3.1008F, 0.2168F, -2.8936F));

		PartDefinition cube_r143 = bone10.addOrReplaceChild("cube_r143", CubeListBuilder.create().texOffs(28, 86).addBox(-0.5F, -3.5F, -1.5F, 1.0F, 7.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-4.7656F, 9.875F, 1.375F, 2.9683F, -0.1417F, -3.0159F));

		PartDefinition cube_r144 = bone10.addOrReplaceChild("cube_r144", CubeListBuilder.create().texOffs(73, 85).addBox(-0.5F, -3.5F, -1.5F, 1.0F, 7.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-5.6875F, 8.9844F, -1.875F, -2.8788F, 0.2478F, -2.8427F));

		PartDefinition cube_r145 = bone10.addOrReplaceChild("cube_r145", CubeListBuilder.create().texOffs(81, 86).addBox(-0.5F, -3.5F, -1.5F, 1.0F, 7.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(4.7656F, 9.75F, 1.625F, 0.2628F, -0.1808F, -0.1512F));

		PartDefinition cube_r146 = bone10.addOrReplaceChild("cube_r146", CubeListBuilder.create().texOffs(49, 87).addBox(-0.5F, -3.5F, -1.5F, 1.0F, 7.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(5.5313F, 9.75F, 0.0625F, 0.1244F, 0.0469F, -0.2527F));

		PartDefinition cube_r147 = bone10.addOrReplaceChild("cube_r147", CubeListBuilder.create().texOffs(0, 89).addBox(-0.5F, -3.5F, -1.5F, 1.0F, 7.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(5.6875F, 9.1094F, -1.5625F, -0.2634F, 0.2726F, -0.2766F));

		PartDefinition bone28 = Body.addOrReplaceChild("bone28", CubeListBuilder.create().texOffs(83, 0).addBox(-1.5F, 5.959F, 0.3794F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.125F))
				.texOffs(0, 78).addBox(-1.0F, 0.6465F, 0.8794F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(123, 80).addBox(-1.5F, 0.2715F, 1.3794F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(121, 83).addBox(-1.5F, -0.4785F, 1.3794F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0625F))
				.texOffs(121, 100).addBox(-1.5F, -1.1427F, 1.3794F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(122, 44).addBox(-1.5F, -0.4356F, 2.0865F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(122, 8).addBox(-1.5F, -0.4356F, 0.6723F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(56, 24).addBox(-1.0F, 2.6465F, 0.8794F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(10.4531F, -6.0372F, -0.4419F, 0.1745F, -0.003F, 0.7853F));

		PartDefinition cube_r148 = bone28.addOrReplaceChild("cube_r148", CubeListBuilder.create().texOffs(104, 30).addBox(-1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -1.9785F, -6.0581F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r149 = bone28.addOrReplaceChild("cube_r149", CubeListBuilder.create().texOffs(121, 116).addBox(-1.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.4356F, 2.3794F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r150 = bone28.addOrReplaceChild("cube_r150", CubeListBuilder.create().texOffs(121, 110).addBox(-1.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.4356F, 1.3794F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r151 = bone28.addOrReplaceChild("cube_r151", CubeListBuilder.create().texOffs(122, 88).addBox(-1.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.5644F, 1.3794F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r152 = bone28.addOrReplaceChild("cube_r152", CubeListBuilder.create().texOffs(123, 65).addBox(-1.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.5644F, 2.3794F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r153 = bone28.addOrReplaceChild("cube_r153", CubeListBuilder.create().texOffs(10, 61).addBox(-1.0F, -2.25F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(72, 109).addBox(-1.5F, 2.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.125F))
				.texOffs(82, 61).addBox(-1.5F, 5.3125F, -5.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1875F))
				.texOffs(23, 77).addBox(-1.5F, -3.5F, -3.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(-0.1875F))
				.texOffs(24, 37).addBox(-1.5F, -3.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(0.5F, -0.666F, 1.8794F, -1.7017F, 0.0F, 0.0F));

		PartDefinition cube_r154 = bone28.addOrReplaceChild("cube_r154", CubeListBuilder.create().texOffs(99, 115).addBox(-0.5F, -2.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(-0.1875F))
				.texOffs(115, 107).addBox(1.875F, -2.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(-1.1875F, -2.2916F, -2.4399F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r155 = bone28.addOrReplaceChild("cube_r155", CubeListBuilder.create().texOffs(72, 95).addBox(-0.4375F, 0.75F, -1.875F, 1.0F, 5.0F, 3.0F, new CubeDeformation(-0.1875F))
				.texOffs(82, 113).addBox(2.375F, 1.6875F, -2.3125F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F))
				.texOffs(95, 94).addBox(1.9375F, 0.75F, -1.875F, 1.0F, 5.0F, 3.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(-1.25F, -1.1228F, -1.5907F, -1.7017F, 0.0F, 0.0F));

		PartDefinition cube_r156 = bone28.addOrReplaceChild("cube_r156", CubeListBuilder.create().texOffs(85, 94).addBox(-0.5F, -1.5F, -3.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(-0.1875F))
				.texOffs(95, 11).addBox(1.875F, -1.5F, -3.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(-1.1875F, -3.0723F, -3.2142F, -0.1309F, 0.0F, 0.0F));

		PartDefinition cube_r157 = bone28.addOrReplaceChild("cube_r157", CubeListBuilder.create().texOffs(0, 115).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, -2.0352F, 5.8264F, 2.6005F, 0.0F, 0.0F));

		PartDefinition cube_r158 = bone28.addOrReplaceChild("cube_r158", CubeListBuilder.create().texOffs(91, 113).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, -1.9911F, 6.5548F, 2.1118F, 0.0F, 0.0F));

		PartDefinition cube_r159 = bone28.addOrReplaceChild("cube_r159", CubeListBuilder.create().texOffs(8, 115).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, 0.3626F, 7.1855F, 1.5533F, 0.0F, 0.0F));

		PartDefinition cube_r160 = bone28.addOrReplaceChild("cube_r160", CubeListBuilder.create().texOffs(36, 115).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, 3.0199F, 7.3946F, 1.7453F, 0.0F, 0.0F));

		PartDefinition cube_r161 = bone28.addOrReplaceChild("cube_r161", CubeListBuilder.create().texOffs(44, 115).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, 5.5007F, 7.2211F, 1.2566F, 0.0F, 0.0F));

		PartDefinition cube_r162 = bone28.addOrReplaceChild("cube_r162", CubeListBuilder.create().texOffs(72, 115).addBox(-0.5F, -0.375F, -0.5781F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, 7.5215F, 4.8794F, 0.4712F, 0.0F, 0.0F));

		PartDefinition cube_r163 = bone28.addOrReplaceChild("cube_r163", CubeListBuilder.create().texOffs(58, 114).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(0.0F, 7.5215F, 4.8794F, 0.7156F, 0.0F, 0.0F));

		PartDefinition bone31 = bone28.addOrReplaceChild("bone31", CubeListBuilder.create().texOffs(88, 102).addBox(-2.0F, -1.7071F, -0.7071F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(66, 86).addBox(-3.5F, -1.5352F, -0.7071F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F))
				.texOffs(112, 44).addBox(-3.5F, -1.0049F, -0.1768F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F))
				.texOffs(111, 60).addBox(-3.5F, -0.4746F, -0.7071F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F))
				.texOffs(54, 104).addBox(-3.5F, -1.0049F, -1.2374F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F))
				.texOffs(112, 77).addBox(-3.5F, -1.0058F, -0.7071F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.0625F))
				.texOffs(101, 4).addBox(-2.0F, -1.0F, -1.4142F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(79, 104).addBox(-2.0F, -0.2929F, -0.7071F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 105).addBox(-2.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -0.9785F, -6.351F));

		PartDefinition cube_r164 = bone31.addOrReplaceChild("cube_r164", CubeListBuilder.create().texOffs(106, 36).addBox(-1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.2929F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r165 = bone31.addOrReplaceChild("cube_r165", CubeListBuilder.create().texOffs(103, 96).addBox(-1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -0.7071F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r166 = bone31.addOrReplaceChild("cube_r166", CubeListBuilder.create().texOffs(111, 12).addBox(-1.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-2.5F, -0.1299F, -0.5821F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r167 = bone31.addOrReplaceChild("cube_r167", CubeListBuilder.create().texOffs(111, 83).addBox(-1.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-2.5F, -0.1299F, 0.1679F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r168 = bone31.addOrReplaceChild("cube_r168", CubeListBuilder.create().texOffs(54, 87).addBox(-1.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-2.5F, -0.8799F, 0.1679F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r169 = bone31.addOrReplaceChild("cube_r169", CubeListBuilder.create().texOffs(77, 96).addBox(-1.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-2.5F, -0.8799F, -0.5821F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r170 = bone31.addOrReplaceChild("cube_r170", CubeListBuilder.create().texOffs(102, 44).addBox(-1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -1.0F, -0.7071F, 0.7854F, 0.0F, 0.0F));

		PartDefinition bone30 = bone28.addOrReplaceChild("bone30", CubeListBuilder.create().texOffs(121, 78).addBox(-2.0F, 28.6982F, -18.4982F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.125F))
				.texOffs(32, 23).addBox(-2.0F, 30.466F, -18.4982F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.125F))
				.texOffs(121, 28).addBox(-2.0F, 29.5821F, -19.382F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.125F))
				.texOffs(113, 7).addBox(-2.5F, 29.5821F, -18.4982F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.1875F))
				.texOffs(22, 103).addBox(-2.0F, 29.5821F, -17.6143F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offset(0.5F, -30.0606F, 22.7525F));

		PartDefinition cube_r171 = bone30.addOrReplaceChild("cube_r171", CubeListBuilder.create().texOffs(64, 33).addBox(-1.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(-1.0F, 30.7071F, -18.6232F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r172 = bone30.addOrReplaceChild("cube_r172", CubeListBuilder.create().texOffs(82, 67).addBox(-1.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(-1.0F, 30.7071F, -17.3732F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r173 = bone30.addOrReplaceChild("cube_r173", CubeListBuilder.create().texOffs(97, 39).addBox(-1.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(-1.0F, 29.4571F, -17.3732F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r174 = bone30.addOrReplaceChild("cube_r174", CubeListBuilder.create().texOffs(121, 60).addBox(-1.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(-1.0F, 29.4571F, -18.6232F, 0.7854F, 0.0F, 0.0F));

		PartDefinition bone32 = bone28.addOrReplaceChild("bone32", CubeListBuilder.create().texOffs(90, 60).addBox(-1.5492F, -0.9041F, -3.2912F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.5F))
				.texOffs(130, 8).addBox(-1.0492F, -0.4041F, -3.5412F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.125F))
				.texOffs(109, 65).addBox(-0.5492F, -1.9041F, -5.6662F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(58, 0).addBox(-0.5492F, 0.2834F, -9.6662F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(53, 93).addBox(-0.5492F, -0.9666F, -9.6662F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0492F, -1.573F, -6.5228F, 1.0036F, 0.0F, 0.0F));

		PartDefinition cube_r175 = bone32.addOrReplaceChild("cube_r175", CubeListBuilder.create().texOffs(56, 120).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 118).addBox(-0.5F, -1.5F, -2.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3286F, -0.3277F, -3.1662F, 0.0F, 0.0F, -0.2443F));

		PartDefinition cube_r176 = bone32.addOrReplaceChild("cube_r176", CubeListBuilder.create().texOffs(109, 112).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(2.9374F, 0.9352F, 2.082F, 0.3744F, -0.8532F, 0.1149F));

		PartDefinition cube_r177 = bone32.addOrReplaceChild("cube_r177", CubeListBuilder.create().texOffs(114, 62).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(2.5041F, 1.5412F, 2.3971F, -0.3063F, -0.8532F, 0.1149F));

		PartDefinition cube_r178 = bone32.addOrReplaceChild("cube_r178", CubeListBuilder.create().texOffs(127, 38).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(3.73F, 1.5295F, 1.1292F, 0.2437F, -0.0832F, 0.3821F));

		PartDefinition cube_r179 = bone32.addOrReplaceChild("cube_r179", CubeListBuilder.create().texOffs(67, 114).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(3.9821F, 1.4288F, -1.2251F, -0.402F, -0.0832F, 0.3821F));

		PartDefinition cube_r180 = bone32.addOrReplaceChild("cube_r180", CubeListBuilder.create().texOffs(114, 85).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(3.5892F, 0.4206F, -3.1489F, -0.5236F, 0.6763F, 0.0F));

		PartDefinition cube_r181 = bone32.addOrReplaceChild("cube_r181", CubeListBuilder.create().texOffs(114, 103).addBox(-0.8594F, -0.5F, 0.9844F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.125F))
				.texOffs(58, 127).addBox(-0.8594F, -0.5F, -0.7656F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(0.8258F, -0.2166F, -7.1662F, 0.0F, 0.6763F, 0.0F));

		PartDefinition cube_r182 = bone32.addOrReplaceChild("cube_r182", CubeListBuilder.create().texOffs(65, 92).addBox(-0.5F, -1.5F, -3.75F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4269F, 0.6098F, -5.9162F, 0.0F, 0.0F, 0.2443F));

		PartDefinition cube_r183 = bone32.addOrReplaceChild("cube_r183", CubeListBuilder.create().texOffs(22, 92).addBox(-0.5F, -1.5F, -3.75F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3286F, 0.6098F, -5.9162F, 0.0F, 0.0F, -0.2443F));

		PartDefinition cube_r184 = bone32.addOrReplaceChild("cube_r184", CubeListBuilder.create().texOffs(21, 98).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0492F, -0.5093F, -6.8083F, 0.2967F, 0.0F, 0.0F));

		PartDefinition cube_r185 = bone32.addOrReplaceChild("cube_r185", CubeListBuilder.create().texOffs(50, 118).addBox(-0.5F, -1.5F, -2.75F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(109, 123).addBox(-0.5F, -1.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4269F, -0.3277F, -2.9162F, 0.0F, 0.0F, 0.2443F));

		PartDefinition bone34 = bone32.addOrReplaceChild("bone34", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.8624F, -0.0217F, -10.1238F, 0.0F, 0.0F, 0.2443F));

		PartDefinition cube_r186 = bone34.addOrReplaceChild("cube_r186", CubeListBuilder.create().texOffs(111, 118).addBox(-0.3419F, -0.4944F, -0.0048F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2967F, 0.0F));

		PartDefinition cube_r187 = bone34.addOrReplaceChild("cube_r187", CubeListBuilder.create().texOffs(6, 126).addBox(-1.5037F, -1.8694F, -1.0791F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.8389F, 1.375F, 0.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition bone33 = bone32.addOrReplaceChild("bone33", CubeListBuilder.create(), PartPose.offsetAndRotation(0.8578F, -0.0217F, -10.1238F, 0.0F, 0.0F, -0.2443F));

		PartDefinition cube_r188 = bone33.addOrReplaceChild("cube_r188", CubeListBuilder.create().texOffs(105, 88).addBox(-0.6581F, -0.4944F, -0.0048F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2967F, 0.0F));

		PartDefinition cube_r189 = bone33.addOrReplaceChild("cube_r189", CubeListBuilder.create().texOffs(126, 5).addBox(0.5037F, -1.8694F, -1.0791F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-0.8389F, 1.375F, 0.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition bone35 = bone32.addOrReplaceChild("bone35", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0235F, 1.3533F, -10.1238F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r190 = bone35.addOrReplaceChild("cube_r190", CubeListBuilder.create().texOffs(69, 127).addBox(-0.7537F, -0.4944F, -1.4541F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition cube_r191 = bone35.addOrReplaceChild("cube_r191", CubeListBuilder.create().texOffs(114, 66).addBox(-0.3419F, -0.4944F, -0.0048F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2967F, 0.0F));

		PartDefinition JS = Body.addOrReplaceChild("JS", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0625F, -6.75F));

		PartDefinition cube_r192 = JS.addOrReplaceChild("cube_r192", CubeListBuilder.create().texOffs(40, 57).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8921F, 12.0876F, 10.7278F, -0.1585F, -0.0735F, -0.4305F));

		PartDefinition cube_r193 = JS.addOrReplaceChild("cube_r193", CubeListBuilder.create().texOffs(105, 112).addBox(-0.5F, -2.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.741F, 12.5435F, 12.1846F, -0.3779F, -0.1389F, -0.3356F));

		PartDefinition cube_r194 = JS.addOrReplaceChild("cube_r194", CubeListBuilder.create().texOffs(54, 114).addBox(-0.5F, -2.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.741F, 12.5435F, 12.1846F, -0.3779F, 0.1389F, 0.3356F));

		PartDefinition cube_r195 = JS.addOrReplaceChild("cube_r195", CubeListBuilder.create().texOffs(56, 57).addBox(-3.0F, -0.5F, -0.5F, 7.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 10.9237F, 12.3289F, -0.4014F, 0.0F, 0.0F));

		PartDefinition cube_r196 = JS.addOrReplaceChild("cube_r196", CubeListBuilder.create().texOffs(56, 43).addBox(-3.0F, -0.5F, -2.5F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 11.4281F, 10.7836F, 1.3526F, 0.0F, 0.0F));

		PartDefinition cube_r197 = JS.addOrReplaceChild("cube_r197", CubeListBuilder.create().texOffs(78, 12).addBox(-3.0F, -0.5F, -0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 10.8009F, 11.1101F, 0.829F, 0.0F, 0.0F));

		PartDefinition cube_r198 = JS.addOrReplaceChild("cube_r198", CubeListBuilder.create().texOffs(0, 68).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8921F, 12.0876F, 10.7278F, -0.1585F, 0.0735F, 0.4305F));

		PartDefinition cube_r199 = JS.addOrReplaceChild("cube_r199", CubeListBuilder.create().texOffs(57, 70).addBox(-3.0F, -0.5F, 0.5F, 7.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 10.5F, 10.5F, -0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r200 = JS.addOrReplaceChild("cube_r200", CubeListBuilder.create().texOffs(75, 35).addBox(-1.6025F, -1.2813F, -2.375F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-1.8975F, 13.7782F, 14.5757F, -1.0821F, 0.0F, 0.0F));

		PartDefinition cube_r201 = JS.addOrReplaceChild("cube_r201", CubeListBuilder.create().texOffs(14, 45).addBox(-0.9652F, -1.0667F, -2.375F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-3.1466F, 13.8371F, 14.465F, -1.0347F, 0.4049F, 0.2299F));

		PartDefinition cube_r202 = JS.addOrReplaceChild("cube_r202", CubeListBuilder.create().texOffs(47, 57).addBox(-0.0348F, -1.0667F, -2.375F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(3.1466F, 13.8371F, 14.465F, -1.0347F, -0.4049F, -0.2299F));

		PartDefinition cube_r203 = JS.addOrReplaceChild("cube_r203", CubeListBuilder.create().texOffs(31, 82).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(3.1123F, 13.9173F, 15.1721F, -0.6997F, -0.6427F, -0.4094F));

		PartDefinition cube_r204 = JS.addOrReplaceChild("cube_r204", CubeListBuilder.create().texOffs(44, 120).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(3.0313F, 13.7413F, 15.1778F, -0.9441F, -0.6427F, -0.4094F));

		PartDefinition cube_r205 = JS.addOrReplaceChild("cube_r205", CubeListBuilder.create().texOffs(83, 29).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-3.1123F, 13.9173F, 15.1721F, -0.6997F, 0.6427F, 0.4094F));

		PartDefinition cube_r206 = JS.addOrReplaceChild("cube_r206", CubeListBuilder.create().texOffs(122, 93).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-3.0313F, 13.7413F, 15.1778F, -0.9441F, 0.6427F, 0.4094F));

		PartDefinition cube_r207 = JS.addOrReplaceChild("cube_r207", CubeListBuilder.create().texOffs(0, 41).addBox(-1.1025F, -1.2813F, -0.25F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-1.8975F, 12.8953F, 14.1062F, -1.0821F, 0.0F, 0.0F));

		PartDefinition cube_r208 = JS.addOrReplaceChild("cube_r208", CubeListBuilder.create().texOffs(59, 31).addBox(-4.0F, -0.5F, 0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(0.5F, 10.7583F, 12.9742F, -0.4887F, 0.0F, 0.0F));

		PartDefinition cube_r209 = JS.addOrReplaceChild("cube_r209", CubeListBuilder.create().texOffs(87, 18).addBox(-2.5F, -0.5F, -1.5F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(0.0F, 10.8527F, 12.9288F, -0.2793F, 0.0F, 0.0F));

		PartDefinition bone37 = JS.addOrReplaceChild("bone37", CubeListBuilder.create().texOffs(112, 70).addBox(-1.7159F, 0.2217F, -0.3663F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.0625F))
				.texOffs(101, 121).addBox(-1.6534F, 0.2842F, -2.3038F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(106, 88).addBox(-0.0277F, -1.6576F, -2.6131F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4411F, 14.3295F, 13.6131F, -0.2705F, 0.0F, 0.0F));

		PartDefinition cube_r210 = bone37.addOrReplaceChild("cube_r210", CubeListBuilder.create().texOffs(86, 129).addBox(-1.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1523F, 0.1671F, 1.5966F, -0.847F, -0.9094F, -0.609F));

		PartDefinition cube_r211 = bone37.addOrReplaceChild("cube_r211", CubeListBuilder.create().texOffs(129, 116).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9723F, 0.5491F, 1.9679F, -1.1519F, 0.0F, 0.0F));

		PartDefinition cube_r212 = bone37.addOrReplaceChild("cube_r212", CubeListBuilder.create().texOffs(99, 91).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1012F, -0.4298F, 1.1727F, -0.6893F, 0.4121F, 0.4524F));

		PartDefinition cube_r213 = bone37.addOrReplaceChild("cube_r213", CubeListBuilder.create().texOffs(127, 129).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9723F, -0.2143F, 1.6644F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r214 = bone37.addOrReplaceChild("cube_r214", CubeListBuilder.create().texOffs(72, 79).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4921F, -0.3034F, 0.3672F, -0.3743F, 0.1217F, 0.2998F));

		PartDefinition cube_r215 = bone37.addOrReplaceChild("cube_r215", CubeListBuilder.create().texOffs(99, 88).addBox(-1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2083F, -0.4566F, 0.4307F, -0.2537F, 0.3031F, 0.8555F));

		PartDefinition cube_r216 = bone37.addOrReplaceChild("cube_r216", CubeListBuilder.create().texOffs(130, 5).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9723F, -0.813F, 1.1195F, -0.3927F, 0.0F, 0.0F));

		PartDefinition cube_r217 = bone37.addOrReplaceChild("cube_r217", CubeListBuilder.create().texOffs(100, 79).addBox(0.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5601F, -0.0587F, -2.1341F, 1.3378F, 0.5981F, -0.1328F));

		PartDefinition cube_r218 = bone37.addOrReplaceChild("cube_r218", CubeListBuilder.create().texOffs(92, 129).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9723F, -0.1509F, -2.6084F, 1.3788F, 0.0F, 0.0F));

		PartDefinition cube_r219 = bone37.addOrReplaceChild("cube_r219", CubeListBuilder.create().texOffs(129, 110).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9723F, -1.002F, -2.3482F, 1.1694F, 0.0F, 0.0F));

		PartDefinition cube_r220 = bone37.addOrReplaceChild("cube_r220", CubeListBuilder.create().texOffs(106, 53).addBox(-0.5F, -0.4375F, -0.375F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9512F, -1.8293F, 0.0032F, -0.192F, 0.0F, -0.1222F));

		PartDefinition cube_r221 = bone37.addOrReplaceChild("cube_r221", CubeListBuilder.create().texOffs(30, 107).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1875F)), PartPose.offsetAndRotation(0.9723F, -1.6576F, -1.4881F, 0.1571F, 0.0F, -0.1222F));

		PartDefinition cube_r222 = bone37.addOrReplaceChild("cube_r222", CubeListBuilder.create().texOffs(38, 126).addBox(-0.5F, -0.5F, -2.125F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(2.2184F, 0.7217F, 2.368F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r223 = bone37.addOrReplaceChild("cube_r223", CubeListBuilder.create().texOffs(114, 14).addBox(-1.0F, -0.5F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(0.2945F, 0.7217F, 1.487F, 0.0F, 0.6807F, 0.0F));

		PartDefinition cube_r224 = bone37.addOrReplaceChild("cube_r224", CubeListBuilder.create().texOffs(108, 27).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6132F, 0.7842F, -2.7993F, 0.0F, -1.3788F, 0.0F));

		PartDefinition cube_r225 = bone37.addOrReplaceChild("cube_r225", CubeListBuilder.create().texOffs(68, 103).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9809F, 0.7842F, -2.5223F, 0.0F, -0.4712F, 0.0F));

		PartDefinition cube_r226 = bone37.addOrReplaceChild("cube_r226", CubeListBuilder.create().texOffs(106, 49).addBox(-2.25F, -0.5F, -2.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2927F, -1.1576F, -0.6131F, 0.0F, 0.0F, -1.2392F));

		PartDefinition cube_r227 = bone37.addOrReplaceChild("cube_r227", CubeListBuilder.create().texOffs(24, 114).addBox(-0.625F, -0.5F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0481F, -1.033F, -0.6131F, 0.0F, 0.0F, -0.6109F));

		PartDefinition bone38 = bone37.addOrReplaceChild("bone38", CubeListBuilder.create().texOffs(123, 36).addBox(-0.8515F, -3.1424F, 1.2692F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.0625F))
				.texOffs(122, 40).addBox(-0.8515F, -3.1424F, -2.3825F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.0625F))
				.texOffs(127, 62).addBox(-0.8515F, -0.3924F, 0.9879F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(14, 45).addBox(-2.3515F, 0.4201F, -2.5121F, 5.0F, 1.0F, 5.0F, new CubeDeformation(-0.5F))
				.texOffs(127, 107).addBox(-0.8515F, -0.3924F, -2.1334F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(127, 120).addBox(-0.8515F, -0.7674F, 1.0817F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1875F))
				.texOffs(122, 127).addBox(-0.8515F, -0.7674F, -2.2164F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(0.5113F, 2.9379F, 0.524F, 0.6192F, -0.1427F, 0.2794F));

		PartDefinition cube_r228 = bone38.addOrReplaceChild("cube_r228", CubeListBuilder.create().texOffs(69, 88).addBox(-1.0625F, -0.8438F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(107, 65).addBox(-0.5F, -0.6563F, -0.5625F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1875F))
				.texOffs(84, 43).addBox(0.0625F, -0.8438F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.0617F, 0.1389F, -1.2776F, 0.0F, -2.3562F, 0.0F));

		PartDefinition cube_r229 = bone38.addOrReplaceChild("cube_r229", CubeListBuilder.create().texOffs(41, 98).addBox(-1.0625F, -0.8438F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(12, 110).addBox(-0.5F, -0.6563F, -0.5625F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1875F))
				.texOffs(57, 89).addBox(0.0625F, -0.8438F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.0617F, 0.1389F, 1.1429F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r230 = bone38.addOrReplaceChild("cube_r230", CubeListBuilder.create().texOffs(129, 51).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(-1.1034F, 0.0451F, 1.5118F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r231 = bone38.addOrReplaceChild("cube_r231", CubeListBuilder.create().texOffs(129, 83).addBox(0.6386F, -0.5F, 0.5837F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offset(-1.701F, 0.0451F, 0.8594F));

		PartDefinition cube_r232 = bone38.addOrReplaceChild("cube_r232", CubeListBuilder.create().texOffs(54, 99).addBox(-0.9571F, -0.5F, 0.4571F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.403F, -0.2049F, -0.2549F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r233 = bone38.addOrReplaceChild("cube_r233", CubeListBuilder.create().texOffs(20, 110).addBox(-1.3946F, -0.25F, 0.4571F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(1.2704F, -0.2674F, -0.2107F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r234 = bone38.addOrReplaceChild("cube_r234", CubeListBuilder.create().texOffs(82, 99).addBox(-1.9571F, -0.5F, 0.4571F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.3146F, -0.2049F, -0.1665F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r235 = bone38.addOrReplaceChild("cube_r235", CubeListBuilder.create().texOffs(27, 128).addBox(0.4268F, -0.5F, -0.2197F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(2.0779F, -0.2674F, -1.4942F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r236 = bone38.addOrReplaceChild("cube_r236", CubeListBuilder.create().texOffs(11, 128).addBox(0.4268F, -0.5F, -0.2197F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(-1.2202F, -0.2674F, -1.4942F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r237 = bone38.addOrReplaceChild("cube_r237", CubeListBuilder.create().texOffs(127, 48).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(1.3146F, 0.2326F, -1.2334F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r238 = bone38.addOrReplaceChild("cube_r238", CubeListBuilder.create().texOffs(116, 127).addBox(0.4268F, -0.5F, -0.2197F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.1318F, 0.1076F, -1.4995F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r239 = bone38.addOrReplaceChild("cube_r239", CubeListBuilder.create().texOffs(110, 127).addBox(-1.8536F, -0.5F, -0.0429F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.2354F, 0.1076F, -0.2495F, -3.1416F, -0.7854F, 3.1416F));

		PartDefinition cube_r240 = bone38.addOrReplaceChild("cube_r240", CubeListBuilder.create().texOffs(104, 127).addBox(-0.0429F, -0.5F, 0.4571F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.2521F, 0.1076F, 0.1772F, -3.1416F, 0.7854F, 3.1416F));

		PartDefinition cube_r241 = bone38.addOrReplaceChild("cube_r241", CubeListBuilder.create().texOffs(98, 127).addBox(0.4268F, -0.5F, -0.2197F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.9895F, 0.1076F, -1.4995F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r242 = bone38.addOrReplaceChild("cube_r242", CubeListBuilder.create().texOffs(127, 85).addBox(-1.9571F, -0.5F, 0.4571F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.2521F, 0.1076F, -0.3228F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r243 = bone38.addOrReplaceChild("cube_r243", CubeListBuilder.create().texOffs(127, 56).addBox(-0.5F, -0.5F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.3086F, 0.1076F, 0.6772F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r244 = bone38.addOrReplaceChild("cube_r244", CubeListBuilder.create().texOffs(38, 122).addBox(-0.0429F, -1.5F, 0.4571F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(1.4396F, -1.6424F, 0.0058F, -3.1416F, 0.7854F, 3.1416F));

		PartDefinition cube_r245 = bone38.addOrReplaceChild("cube_r245", CubeListBuilder.create().texOffs(122, 32).addBox(-1.9571F, -1.5F, 0.4571F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(1.4396F, -1.6424F, -0.1192F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r246 = bone38.addOrReplaceChild("cube_r246", CubeListBuilder.create().texOffs(70, 122).addBox(-1.8536F, -1.5F, -0.0429F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(-1.4229F, -1.6424F, -0.4209F, -3.1416F, -0.7854F, 3.1416F));

		PartDefinition cube_r247 = bone38.addOrReplaceChild("cube_r247", CubeListBuilder.create().texOffs(6, 122).addBox(0.4268F, -1.5F, -0.2197F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(2.2547F, -1.6424F, -1.4834F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r248 = bone38.addOrReplaceChild("cube_r248", CubeListBuilder.create().texOffs(55, 122).addBox(0.4268F, -1.5F, -0.2197F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(-1.397F, -1.6424F, -1.4834F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r249 = bone38.addOrReplaceChild("cube_r249", CubeListBuilder.create().texOffs(22, 123).addBox(-0.5F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(-1.4961F, -1.6424F, 0.8808F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone39 = JS.addOrReplaceChild("bone39", CubeListBuilder.create().texOffs(95, 13).addBox(0.7159F, 0.2217F, -0.3663F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.0625F))
				.texOffs(45, 85).addBox(0.6534F, 0.2842F, -2.3038F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(26, 57).addBox(-1.9723F, -1.6576F, -2.6131F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.4411F, 14.3295F, 13.6131F, -0.2705F, 0.0F, 0.0F));

		PartDefinition cube_r250 = bone39.addOrReplaceChild("cube_r250", CubeListBuilder.create().texOffs(88, 100).addBox(-0.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1523F, 0.1671F, 1.5966F, -0.847F, 0.9094F, 0.609F));

		PartDefinition cube_r251 = bone39.addOrReplaceChild("cube_r251", CubeListBuilder.create().texOffs(96, 103).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9723F, 0.5491F, 1.9679F, -1.1519F, 0.0F, 0.0F));

		PartDefinition cube_r252 = bone39.addOrReplaceChild("cube_r252", CubeListBuilder.create().texOffs(53, 72).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1012F, -0.4298F, 1.1727F, -0.6893F, -0.4121F, -0.4524F));

		PartDefinition cube_r253 = bone39.addOrReplaceChild("cube_r253", CubeListBuilder.create().texOffs(99, 113).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9723F, -0.2143F, 1.6644F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r254 = bone39.addOrReplaceChild("cube_r254", CubeListBuilder.create().texOffs(48, 77).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4921F, -0.3034F, 0.3672F, -0.3743F, -0.1217F, -0.2998F));

		PartDefinition cube_r255 = bone39.addOrReplaceChild("cube_r255", CubeListBuilder.create().texOffs(87, 41).addBox(0.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2083F, -0.4566F, 0.4307F, -0.2537F, -0.3031F, -0.8555F));

		PartDefinition cube_r256 = bone39.addOrReplaceChild("cube_r256", CubeListBuilder.create().texOffs(13, 115).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9723F, -0.813F, 1.1195F, -0.3927F, 0.0F, 0.0F));

		PartDefinition cube_r257 = bone39.addOrReplaceChild("cube_r257", CubeListBuilder.create().texOffs(89, 32).addBox(-1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5601F, -0.0587F, -2.1341F, 1.3378F, -0.5981F, 0.1328F));

		PartDefinition cube_r258 = bone39.addOrReplaceChild("cube_r258", CubeListBuilder.create().texOffs(127, 15).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9723F, -0.1509F, -2.6084F, 1.3788F, 0.0F, 0.0F));

		PartDefinition cube_r259 = bone39.addOrReplaceChild("cube_r259", CubeListBuilder.create().texOffs(127, 123).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9723F, -1.002F, -2.3482F, 1.1694F, 0.0F, 0.0F));

		PartDefinition cube_r260 = bone39.addOrReplaceChild("cube_r260", CubeListBuilder.create().texOffs(94, 47).addBox(-0.5F, -0.4375F, -0.375F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9512F, -1.8293F, 0.0032F, -0.192F, 0.0F, 0.1222F));

		PartDefinition cube_r261 = bone39.addOrReplaceChild("cube_r261", CubeListBuilder.create().texOffs(94, 65).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1875F)), PartPose.offsetAndRotation(-0.9723F, -1.6576F, -1.4881F, 0.1571F, 0.0F, 0.1222F));

		PartDefinition cube_r262 = bone39.addOrReplaceChild("cube_r262", CubeListBuilder.create().texOffs(125, 75).addBox(-0.5F, -0.5F, -2.125F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(-2.2184F, 0.7217F, 2.368F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r263 = bone39.addOrReplaceChild("cube_r263", CubeListBuilder.create().texOffs(113, 70).addBox(0.0F, -0.5F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(-0.2945F, 0.7217F, 1.487F, 0.0F, -0.6807F, 0.0F));

		PartDefinition cube_r264 = bone39.addOrReplaceChild("cube_r264", CubeListBuilder.create().texOffs(32, 107).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6132F, 0.7842F, -2.7993F, 0.0F, 1.3788F, 0.0F));

		PartDefinition cube_r265 = bone39.addOrReplaceChild("cube_r265", CubeListBuilder.create().texOffs(31, 79).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9809F, 0.7842F, -2.5223F, 0.0F, 0.4712F, 0.0F));

		PartDefinition cube_r266 = bone39.addOrReplaceChild("cube_r266", CubeListBuilder.create().texOffs(105, 32).addBox(0.25F, -0.5F, -2.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2927F, -1.1576F, -0.6131F, 0.0F, 0.0F, 1.2392F));

		PartDefinition cube_r267 = bone39.addOrReplaceChild("cube_r267", CubeListBuilder.create().texOffs(77, 113).addBox(-0.375F, -0.5F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0481F, -1.033F, -0.6131F, 0.0F, 0.0F, 0.6109F));

		PartDefinition bone40 = bone39.addOrReplaceChild("bone40", CubeListBuilder.create().texOffs(0, 122).addBox(-1.1485F, -3.1424F, 1.2692F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.0625F))
				.texOffs(121, 106).addBox(-1.1485F, -3.1424F, -2.3825F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.0625F))
				.texOffs(127, 30).addBox(-1.1485F, -0.3924F, 0.9879F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(44, 29).addBox(-2.6485F, 0.4201F, -2.5121F, 5.0F, 1.0F, 5.0F, new CubeDeformation(-0.5F))
				.texOffs(126, 104).addBox(-1.1485F, -0.3924F, -2.1334F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(41, 115).addBox(-1.1485F, -0.7674F, 1.0817F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1875F))
				.texOffs(5, 115).addBox(-1.1485F, -0.7674F, -2.2164F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(-0.5113F, 2.9379F, 0.524F, 0.6192F, 0.1427F, -0.2794F));

		PartDefinition cube_r268 = bone40.addOrReplaceChild("cube_r268", CubeListBuilder.create().texOffs(0, 54).addBox(0.0625F, -0.8438F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(85, 27).addBox(-0.5F, -0.6563F, -0.5625F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1875F))
				.texOffs(25, 66).addBox(-1.0625F, -0.8438F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.0617F, 0.1389F, -1.2776F, 0.0F, 2.3562F, 0.0F));

		PartDefinition cube_r269 = bone40.addOrReplaceChild("cube_r269", CubeListBuilder.create().texOffs(7, 71).addBox(0.0625F, -0.8438F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(18, 86).addBox(-0.5F, -0.6563F, -0.5625F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1875F))
				.texOffs(33, 72).addBox(-1.0625F, -0.8438F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.0617F, 0.1389F, 1.1429F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r270 = bone40.addOrReplaceChild("cube_r270", CubeListBuilder.create().texOffs(96, 77).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(1.1034F, 0.0451F, 1.5118F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r271 = bone40.addOrReplaceChild("cube_r271", CubeListBuilder.create().texOffs(97, 86).addBox(-2.6386F, -0.5F, 0.5837F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offset(1.701F, 0.0451F, 0.8594F));

		PartDefinition cube_r272 = bone40.addOrReplaceChild("cube_r272", CubeListBuilder.create().texOffs(41, 72).addBox(-0.0429F, -0.5F, 0.4571F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.403F, -0.2049F, -0.2549F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r273 = bone40.addOrReplaceChild("cube_r273", CubeListBuilder.create().texOffs(86, 86).addBox(0.3946F, -0.25F, 0.4571F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(-1.2704F, -0.2674F, -0.2107F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r274 = bone40.addOrReplaceChild("cube_r274", CubeListBuilder.create().texOffs(72, 48).addBox(0.9571F, -0.5F, 0.4571F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.3146F, -0.2049F, -0.1665F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r275 = bone40.addOrReplaceChild("cube_r275", CubeListBuilder.create().texOffs(82, 24).addBox(-2.4268F, -0.5F, -0.2197F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(-2.0779F, -0.2674F, -1.4942F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r276 = bone40.addOrReplaceChild("cube_r276", CubeListBuilder.create().texOffs(22, 89).addBox(-2.4268F, -0.5F, -0.2197F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(1.2202F, -0.2674F, -1.4942F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r277 = bone40.addOrReplaceChild("cube_r277", CubeListBuilder.create().texOffs(91, 94).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(-1.3146F, 0.2326F, -1.2334F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r278 = bone40.addOrReplaceChild("cube_r278", CubeListBuilder.create().texOffs(54, 126).addBox(-2.4268F, -0.5F, -0.2197F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.1318F, 0.1076F, -1.4995F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r279 = bone40.addOrReplaceChild("cube_r279", CubeListBuilder.create().texOffs(126, 92).addBox(-0.1464F, -0.5F, -0.0429F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.2354F, 0.1076F, -0.2495F, -3.1416F, 0.7854F, -3.1416F));

		PartDefinition cube_r280 = bone40.addOrReplaceChild("cube_r280", CubeListBuilder.create().texOffs(127, 12).addBox(-1.9571F, -0.5F, 0.4571F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.2521F, 0.1076F, 0.1772F, -3.1416F, -0.7854F, -3.1416F));

		PartDefinition cube_r281 = bone40.addOrReplaceChild("cube_r281", CubeListBuilder.create().texOffs(21, 127).addBox(-2.4268F, -0.5F, -0.2197F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.9895F, 0.1076F, -1.4995F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r282 = bone40.addOrReplaceChild("cube_r282", CubeListBuilder.create().texOffs(127, 21).addBox(-0.0429F, -0.5F, 0.4571F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.2521F, 0.1076F, -0.3228F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r283 = bone40.addOrReplaceChild("cube_r283", CubeListBuilder.create().texOffs(47, 127).addBox(-1.5F, -0.5F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.3086F, 0.1076F, 0.6772F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r284 = bone40.addOrReplaceChild("cube_r284", CubeListBuilder.create().texOffs(62, 49).addBox(-1.9571F, -1.5F, 0.4571F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(-1.4396F, -1.6424F, 0.0058F, -3.1416F, -0.7854F, -3.1416F));

		PartDefinition cube_r285 = bone40.addOrReplaceChild("cube_r285", CubeListBuilder.create().texOffs(121, 48).addBox(-0.0429F, -1.5F, 0.4571F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(-1.4396F, -1.6424F, -0.1192F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r286 = bone40.addOrReplaceChild("cube_r286", CubeListBuilder.create().texOffs(77, 121).addBox(-0.1464F, -1.5F, -0.0429F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(1.4229F, -1.6424F, -0.4209F, -3.1416F, 0.7854F, -3.1416F));

		PartDefinition cube_r287 = bone40.addOrReplaceChild("cube_r287", CubeListBuilder.create().texOffs(83, 121).addBox(-2.4268F, -1.5F, -0.2197F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(-2.2547F, -1.6424F, -1.4834F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r288 = bone40.addOrReplaceChild("cube_r288", CubeListBuilder.create().texOffs(89, 121).addBox(-2.4268F, -1.5F, -0.2197F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(1.397F, -1.6424F, -1.4834F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r289 = bone40.addOrReplaceChild("cube_r289", CubeListBuilder.create().texOffs(95, 121).addBox(-1.5F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(1.4961F, -1.6424F, 0.8808F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone36 = JS.addOrReplaceChild("bone36", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r290 = bone36.addOrReplaceChild("cube_r290", CubeListBuilder.create().texOffs(20, 16).addBox(-1.5F, -0.375F, -1.5F, 5.0F, 1.0F, 3.0F, new CubeDeformation(-0.0625F)), PartPose.offsetAndRotation(-1.0F, 14.3693F, 14.4572F, -0.1222F, 0.0F, 0.0F));

		PartDefinition cube_r291 = bone36.addOrReplaceChild("cube_r291", CubeListBuilder.create().texOffs(45, 52).addBox(-2.0F, -0.9063F, -2.5625F, 7.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 15.0781F, 14.7188F, -0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r292 = bone36.addOrReplaceChild("cube_r292", CubeListBuilder.create().texOffs(40, 0).addBox(-2.5F, -1.5F, -2.5F, 7.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 16.3424F, 11.919F, -1.501F, 0.0F, 0.0F));

		PartDefinition cube_r293 = bone36.addOrReplaceChild("cube_r293", CubeListBuilder.create().texOffs(52, 14).addBox(-2.5F, -1.5F, -1.5F, 7.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 16.3589F, 12.7398F, -0.733F, 0.0F, 0.0F));

		PartDefinition cube_r294 = bone36.addOrReplaceChild("cube_r294", CubeListBuilder.create().texOffs(0, 54).addBox(-2.5F, -1.5F, -2.5F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.7018F, 12.0753F, -1.501F, 0.0F, 0.0F));

		PartDefinition cube_r295 = bone36.addOrReplaceChild("cube_r295", CubeListBuilder.create().texOffs(82, 75).addBox(-1.5F, -1.0F, -1.25F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.1875F))
				.texOffs(69, 24).addBox(-2.0F, -1.5F, -1.5F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 16.7182F, 12.8961F, -0.733F, 0.0F, 0.0F));

		PartDefinition cube_r296 = bone36.addOrReplaceChild("cube_r296", CubeListBuilder.create().texOffs(12, 25).addBox(0.625F, -0.2813F, -2.4375F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.125F))
				.texOffs(20, 66).addBox(-2.625F, -0.2813F, -2.4375F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.125F))
				.texOffs(62, 8).addBox(-3.0F, -1.9063F, -2.5625F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 15.4375F, 14.875F, -0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r297 = bone36.addOrReplaceChild("cube_r297", CubeListBuilder.create().texOffs(20, 0).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.625F, 15.762F, 15.241F, -0.2778F, 0.3367F, -0.0939F));

		PartDefinition cube_r298 = bone36.addOrReplaceChild("cube_r298", CubeListBuilder.create().texOffs(59, 27).addBox(-0.7937F, -0.5F, -1.6299F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0111F, 15.3896F, 16.9537F, 0.0264F, 0.9755F, -0.4765F));

		PartDefinition cube_r299 = bone36.addOrReplaceChild("cube_r299", CubeListBuilder.create().texOffs(58, 8).addBox(-0.7937F, -0.5F, -0.3701F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0111F, 15.3896F, 16.9537F, 3.0875F, 1.2929F, 2.5912F));

		PartDefinition cube_r300 = bone36.addOrReplaceChild("cube_r300", CubeListBuilder.create().texOffs(31, 80).addBox(-1.0625F, 0.0625F, -4.25F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.2937F, 9.3397F, 9.8348F, -0.4973F, 0.721F, -0.3441F));

		PartDefinition cube_r301 = bone36.addOrReplaceChild("cube_r301", CubeListBuilder.create().texOffs(40, 57).addBox(-1.0625F, 0.0625F, -4.25F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(82, 29).addBox(-9.6222F, 0.0625F, -4.25F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.8424F, 11.0687F, 14.3389F, -0.3665F, 0.0F, 0.0F));

		PartDefinition cube_r302 = bone36.addOrReplaceChild("cube_r302", CubeListBuilder.create().texOffs(48, 77).addBox(-1.0625F, 0.0625F, -3.25F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(96, 81).addBox(-9.6222F, 0.0625F, -3.25F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.8424F, 13.8076F, 17.196F, -0.8552F, 0.0F, 0.0F));

		PartDefinition cube_r303 = bone36.addOrReplaceChild("cube_r303", CubeListBuilder.create().texOffs(81, 80).addBox(0.0625F, 0.0625F, -4.25F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.2937F, 9.3397F, 9.8348F, -0.4973F, -0.721F, 0.3441F));

		PartDefinition cube_r304 = bone36.addOrReplaceChild("cube_r304", CubeListBuilder.create().texOffs(69, 14).addBox(-0.2063F, -0.5F, -0.3701F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0111F, 15.3896F, 16.9537F, 3.0875F, -1.2929F, -2.5912F));

		PartDefinition cube_r305 = bone36.addOrReplaceChild("cube_r305", CubeListBuilder.create().texOffs(73, 19).addBox(-0.2063F, -0.5F, -1.6299F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0111F, 15.3896F, 16.9537F, 0.0264F, -0.9755F, 0.4765F));

		PartDefinition cube_r306 = bone36.addOrReplaceChild("cube_r306", CubeListBuilder.create().texOffs(36, 0).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.625F, 15.762F, 15.241F, -0.2778F, -0.3367F, 0.0939F));

		PartDefinition bone11 = Body.addOrReplaceChild("bone11", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r307 = bone11.addOrReplaceChild("cube_r307", CubeListBuilder.create().texOffs(8, 86).addBox(-2.0F, -2.1875F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.2246F, -0.4945F, 5.8213F, -0.134F, -0.3666F, 0.1114F));

		PartDefinition cube_r308 = bone11.addOrReplaceChild("cube_r308", CubeListBuilder.create().texOffs(75, 69).addBox(-1.5F, -1.5F, -2.375F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.8301F, 0.8114F, 4.9892F, -0.3879F, 1.0253F, 0.3992F));

		PartDefinition cube_r309 = bone11.addOrReplaceChild("cube_r309", CubeListBuilder.create().texOffs(101, 9).addBox(-2.9375F, -1.8125F, -1.4375F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.7131F, 2.2379F, 4.8132F, 0.2731F, -0.11F, 0.0076F));

		PartDefinition cube_r310 = bone11.addOrReplaceChild("cube_r310", CubeListBuilder.create().texOffs(32, 16).addBox(-4.25F, -1.3125F, -2.1875F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0876F, 0.5068F, 3.9483F, 0.244F, 0.2039F, -0.0886F));

		PartDefinition cube_r311 = bone11.addOrReplaceChild("cube_r311", CubeListBuilder.create().texOffs(66, 63).addBox(-4.0F, -0.25F, -1.0F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4722F, -2.75F, 5.9375F, 0.1552F, 0.1249F, -0.1423F));

		PartDefinition cube_r312 = bone11.addOrReplaceChild("cube_r312", CubeListBuilder.create().texOffs(64, 79).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.8125F, -1.375F, 2.8125F, -0.0049F, 0.0679F, 0.1859F));

		PartDefinition cube_r313 = bone11.addOrReplaceChild("cube_r313", CubeListBuilder.create().texOffs(14, 110).addBox(-0.8125F, -1.0F, -0.125F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.625F, 0.6875F, -3.0F, 0.027F, 0.7799F, 0.1576F));

		PartDefinition cube_r314 = bone11.addOrReplaceChild("cube_r314", CubeListBuilder.create().texOffs(61, 103).addBox(-1.0F, -0.1875F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.1875F, -1.3125F, -1.125F, 0.1625F, -0.3769F, 0.0847F));

		PartDefinition cube_r315 = bone11.addOrReplaceChild("cube_r315", CubeListBuilder.create().texOffs(103, 4).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.25F, -2.3125F, 1.0F, 0.1557F, 0.244F, 0.1829F));

		PartDefinition cube_r316 = bone11.addOrReplaceChild("cube_r316", CubeListBuilder.create().texOffs(99, 103).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -1.4375F, -0.125F, 0.1516F, -0.0888F, -0.1585F));

		PartDefinition cube_r317 = bone11.addOrReplaceChild("cube_r317", CubeListBuilder.create().texOffs(115, 116).addBox(0.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.9375F, 0.875F, -3.0F, 0.027F, -0.7799F, -0.1576F));

		PartDefinition cube_r318 = bone11.addOrReplaceChild("cube_r318", CubeListBuilder.create().texOffs(107, 106).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.625F, 1.25F, -3.4375F, -0.1047F, 0.0F, 0.2618F));

		PartDefinition cube_r319 = bone11.addOrReplaceChild("cube_r319", CubeListBuilder.create().texOffs(56, 19).addBox(-4.0F, -0.875F, -1.6875F, 7.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.8125F, -3.625F, -0.2821F, 0.1328F, -0.1173F));

		PartDefinition bone19 = bone11.addOrReplaceChild("bone19", CubeListBuilder.create(), PartPose.offset(-4.1756F, 3.6503F, 4.2635F));

		PartDefinition cube_r320 = bone19.addOrReplaceChild("cube_r320", CubeListBuilder.create().texOffs(61, 98).addBox(-1.9375F, -1.125F, 0.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5245F, -2.2626F, -1.1927F, -0.4458F, 0.1119F, -0.2639F));

		PartDefinition cube_r321 = bone19.addOrReplaceChild("cube_r321", CubeListBuilder.create().texOffs(19, 81).addBox(-0.6875F, -0.875F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1875F)), PartPose.offsetAndRotation(10.7739F, -3.181F, 0.5005F, 0.3095F, -0.2268F, -0.2187F));

		PartDefinition cube_r322 = bone19.addOrReplaceChild("cube_r322", CubeListBuilder.create().texOffs(109, 18).addBox(-0.5625F, -1.5F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.3512F, -3.0558F, 3.3465F, 0.3072F, 0.3279F, 0.1187F));

		PartDefinition cube_r323 = bone19.addOrReplaceChild("cube_r323", CubeListBuilder.create().texOffs(99, 108).addBox(-3.0F, -1.75F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.2094F, -4.9862F, 1.5424F, 0.2233F, -0.8642F, -0.0601F));

		PartDefinition cube_r324 = bone19.addOrReplaceChild("cube_r324", CubeListBuilder.create().texOffs(8, 93).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.1811F, -4.6513F, 2.9145F, 0.2958F, -0.1899F, -0.0406F));

		PartDefinition cube_r325 = bone19.addOrReplaceChild("cube_r325", CubeListBuilder.create().texOffs(109, 36).addBox(-0.6875F, -1.625F, -0.5625F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0763F, -5.2653F, 2.3511F, 0.3008F, 0.2613F, 0.0968F));

		PartDefinition cube_r326 = bone19.addOrReplaceChild("cube_r326", CubeListBuilder.create().texOffs(6, 110).addBox(-1.0F, -1.5F, -1.125F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4375F, 0.0F, 0.0293F, -0.2147F, 0.0045F));

		PartDefinition Left_Arm = partdefinition.addOrReplaceChild("Left_Arm", CubeListBuilder.create().texOffs(32, 33).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0625F))
				.texOffs(72, 48).addBox(-1.0F, 5.75F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.1875F))
				.texOffs(95, 71).addBox(2.25F, 7.5938F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.1875F))
				.texOffs(111, 123).addBox(3.0F, 7.5F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(105, 123).addBox(2.875F, 7.875F, -1.9375F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1875F))
				.texOffs(61, 123).addBox(2.875F, 7.875F, -0.0625F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1875F))
				.texOffs(72, 43).addBox(-1.0F, 1.75F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.1875F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition cube_r327 = Left_Arm.addOrReplaceChild("cube_r327", CubeListBuilder.create().texOffs(129, 36).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1875F)), PartPose.offsetAndRotation(0.4527F, 6.25F, -1.6736F, 0.0F, -0.3316F, 0.0F));

		PartDefinition bone12 = Left_Arm.addOrReplaceChild("bone12", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition cube_r328 = bone12.addOrReplaceChild("cube_r328", CubeListBuilder.create().texOffs(56, 106).addBox(-0.5F, -3.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(2.9375F, 5.0F, 1.1294F, 0.0F, -0.144F, 0.0F));

		PartDefinition cube_r329 = bone12.addOrReplaceChild("cube_r329", CubeListBuilder.create().texOffs(0, 107).addBox(-0.5F, -3.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(2.9375F, 5.0F, -1.1294F, 0.0F, 0.144F, 0.0F));

		PartDefinition cube_r330 = bone12.addOrReplaceChild("cube_r330", CubeListBuilder.create().texOffs(10, 124).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.125F))
				.texOffs(123, 123).addBox(-0.5F, 1.0625F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.125F))
				.texOffs(117, 123).addBox(-0.5F, 3.125F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(3.625F, 3.0F, 1.0938F, 0.0F, -0.2182F, 0.0F));

		PartDefinition cube_r331 = bone12.addOrReplaceChild("cube_r331", CubeListBuilder.create().texOffs(16, 124).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.125F))
				.texOffs(28, 124).addBox(-0.5F, -3.0625F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.125F))
				.texOffs(34, 124).addBox(-0.5F, -5.125F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(3.625F, 7.125F, -1.0938F, 0.0F, 0.2182F, 0.0F));

		PartDefinition bone13 = Left_Arm.addOrReplaceChild("bone13", CubeListBuilder.create(), PartPose.offset(3.125F, -1.3125F, -3.0F));

		PartDefinition cube_r332 = bone13.addOrReplaceChild("cube_r332", CubeListBuilder.create().texOffs(36, 29).addBox(-2.0F, -2.5F, -2.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4732F, 0.516F, 3.6747F, 0.1062F, -0.5171F, -0.1595F));

		PartDefinition cube_r333 = bone13.addOrReplaceChild("cube_r333", CubeListBuilder.create().texOffs(16, 103).addBox(-1.0F, -2.5F, -1.1875F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5893F, 0.7035F, 3.0497F, -0.0337F, 0.0977F, -0.3416F));

		PartDefinition cube_r334 = bone13.addOrReplaceChild("cube_r334", CubeListBuilder.create().texOffs(93, 4).addBox(-1.6875F, -1.0F, -0.4375F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5625F, -0.3125F, 4.4375F, 0.0971F, -0.0293F, -0.127F));

		PartDefinition cube_r335 = bone13.addOrReplaceChild("cube_r335", CubeListBuilder.create().texOffs(46, 97).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, -0.4375F, 4.5625F, 0.123F, -0.6263F, -0.3017F));

		PartDefinition cube_r336 = bone13.addOrReplaceChild("cube_r336", CubeListBuilder.create().texOffs(91, 107).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5625F, -0.0625F, 1.5625F, -0.1745F, 0.3142F, -0.2618F));

		PartDefinition bone42 = bone13.addOrReplaceChild("bone42", CubeListBuilder.create(), PartPose.offset(-0.75F, -0.4375F, 4.5625F));

		PartDefinition cube_r337 = bone42.addOrReplaceChild("cube_r337", CubeListBuilder.create().texOffs(85, 113).addBox(-1.9375F, -2.0F, -1.5625F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5138F, 0.8811F, -3.53F, -0.1521F, 0.4324F, -0.1832F));

		PartDefinition cube_r338 = bone42.addOrReplaceChild("cube_r338", CubeListBuilder.create().texOffs(105, 96).addBox(0.0938F, -1.4375F, -2.125F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 0.0F, 0.0F, 0.1458F, -0.353F, -0.3787F));

		PartDefinition Right_Arm = partdefinition.addOrReplaceChild("Right_Arm", CubeListBuilder.create().texOffs(0, 25).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0625F))
				.texOffs(14, 57).addBox(-3.0F, 4.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.1875F))
				.texOffs(41, 72).addBox(-3.0F, 4.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(76, 75).addBox(-3.0F, 3.0F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.3125F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition bone14 = Right_Arm.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(16, 88).addBox(-4.0F, -1.75F, -2.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.125F))
				.texOffs(39, 85).addBox(-3.5F, -2.25F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.125F))
				.texOffs(41, 77).addBox(-3.5F, -1.75F, -2.5F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.125F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone17 = Right_Arm.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(117, 80).addBox(-1.2529F, -1.0026F, -1.8282F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-3.6875F, 8.3125F, -0.8958F, -0.0521F, 0.0468F, -0.1073F));

		PartDefinition cube_r339 = bone17.addOrReplaceChild("cube_r339", CubeListBuilder.create().texOffs(102, 79).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(0.0003F, -0.5026F, 0.6719F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r340 = bone17.addOrReplaceChild("cube_r340", CubeListBuilder.create().texOffs(89, 102).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, -0.4993F, -0.1405F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r341 = bone17.addOrReplaceChild("cube_r341", CubeListBuilder.create().texOffs(128, 101).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(-0.0014F, -0.4863F, 2.1098F, 0.0F, 0.0F, 1.5708F));

		PartDefinition bone15 = Right_Arm.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(38, 79).addBox(-1.25F, -1.0015F, -1.828F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-3.8125F, 6.375F, -1.0208F, -0.0349F, 0.0F, 0.0F));

		PartDefinition cube_r342 = bone15.addOrReplaceChild("cube_r342", CubeListBuilder.create().texOffs(102, 39).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(0.0F, -0.5015F, 0.672F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r343 = bone15.addOrReplaceChild("cube_r343", CubeListBuilder.create().texOffs(102, 74).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, -0.4993F, -0.1405F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r344 = bone15.addOrReplaceChild("cube_r344", CubeListBuilder.create().texOffs(128, 98).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1875F)), PartPose.offsetAndRotation(0.0F, -0.4906F, 2.1097F, 0.0F, 0.0F, 1.5708F));

		PartDefinition bone16 = Right_Arm.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(128, 95).addBox(-0.9351F, -0.9916F, 1.1252F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1875F))
				.texOffs(28, 102).addBox(-0.9375F, -1.0F, -2.125F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.125F))
				.texOffs(7, 68).addBox(-1.1875F, -0.5F, -2.3125F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.125F))
				.texOffs(0, 102).addBox(-0.9381F, -1.0021F, -1.3125F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(-3.875F, 4.0804F, -0.4567F, 0.0096F, 0.0336F, 1.8502F));

		PartDefinition bone18 = Right_Arm.addOrReplaceChild("bone18", CubeListBuilder.create(), PartPose.offset(-0.5625F, -1.625F, 1.4375F));

		PartDefinition cube_r345 = bone18.addOrReplaceChild("cube_r345", CubeListBuilder.create().texOffs(24, 107).addBox(-1.0F, -1.0F, -1.1875F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.579F, 0.1073F, -3.5069F, -0.4997F, -1.1624F, 0.6201F));

		PartDefinition cube_r346 = bone18.addOrReplaceChild("cube_r346", CubeListBuilder.create().texOffs(29, 96).addBox(-1.0F, -2.3281F, -2.25F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.1518F, 1.016F, -1.3878F, 0.1015F, 0.0502F, 0.2695F));

		PartDefinition cube_r347 = bone18.addOrReplaceChild("cube_r347", CubeListBuilder.create().texOffs(16, 51).addBox(-1.625F, -2.625F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0893F, 0.8285F, -0.7628F, 0.1062F, 0.5171F, 0.1595F));

		PartDefinition cube_r348 = bone18.addOrReplaceChild("cube_r348", CubeListBuilder.create().texOffs(32, 72).addBox(-1.0F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6856F, -0.1671F, 0.7062F, 2.1087F, 1.2813F, 2.3684F));

		PartDefinition cube_r349 = bone18.addOrReplaceChild("cube_r349", CubeListBuilder.create().texOffs(15, 96).addBox(-1.375F, -1.5F, -1.75F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6455F, 0.4036F, -3.3001F, 2.743F, -1.3547F, -2.6806F));

		PartDefinition bone41 = bone18.addOrReplaceChild("bone41", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, 0.0F));

		PartDefinition cube_r350 = bone41.addOrReplaceChild("cube_r350", CubeListBuilder.create().texOffs(93, 113).addBox(-0.5625F, -1.0F, -1.4375F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.1812F, 0.479F, -4.3202F, -0.1696F, 0.1988F, 0.437F));

		PartDefinition cube_r351 = bone41.addOrReplaceChild("cube_r351", CubeListBuilder.create().texOffs(37, 66).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7603F, -0.461F, -3.2049F, -0.0293F, -0.0772F, 0.3514F));

		PartDefinition cube_r352 = bone41.addOrReplaceChild("cube_r352", CubeListBuilder.create().texOffs(113, 98).addBox(-1.0F, -0.9375F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.8382F, -0.9825F, -0.1126F, 0.3727F, 0.3733F, 0.4412F));

		PartDefinition Right_leg = partdefinition.addOrReplaceChild("Right_leg", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0625F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition cube_r353 = Right_leg.addOrReplaceChild("cube_r353", CubeListBuilder.create().texOffs(0, 61).addBox(-0.5F, -2.0F, -1.625F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-3.25F, 1.875F, -0.5F, 0.0F, -0.1047F, 0.0F));

		PartDefinition cube_r354 = Right_leg.addOrReplaceChild("cube_r354", CubeListBuilder.create().texOffs(111, 112).addBox(-0.5F, -2.0F, -0.375F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0625F)), PartPose.offsetAndRotation(-3.25F, 1.875F, 0.5F, 0.0F, 0.1047F, 0.0F));

		PartDefinition bone20 = Right_leg.addOrReplaceChild("bone20", CubeListBuilder.create().texOffs(117, 0).addBox(-1.5F, 4.5625F, -2.6875F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.125F))
				.texOffs(116, 91).addBox(-1.5F, 4.5625F, -3.0625F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 59).addBox(-2.0F, 6.4375F, 0.1875F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.125F))
				.texOffs(99, 68).addBox(-2.0F, 4.6875F, 0.1875F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.125F))
				.texOffs(54, 75).addBox(-2.0F, 4.5625F, -1.9375F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.1875F)), PartPose.offset(0.0F, -0.3125F, 0.0F));

		PartDefinition cube_r355 = bone20.addOrReplaceChild("cube_r355", CubeListBuilder.create().texOffs(121, 123).addBox(-0.3246F, -1.5F, -0.6617F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(115, 123).addBox(-0.3246F, 0.5F, -0.6617F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4437F, 6.0625F, -2.1186F, 0.0F, 0.8552F, 0.0F));

		PartDefinition cube_r356 = bone20.addOrReplaceChild("cube_r356", CubeListBuilder.create().texOffs(0, 25).addBox(-0.6801F, -3.5F, -0.3786F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(-1.5368F, 8.0625F, -2.1463F, 0.0F, 0.8552F, 0.0F));

		PartDefinition cube_r357 = bone20.addOrReplaceChild("cube_r357", CubeListBuilder.create().texOffs(40, 7).addBox(-0.3199F, -3.5F, -0.3786F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(1.5368F, 8.0625F, -2.1463F, 0.0F, -0.8552F, 0.0F));

		PartDefinition cube_r358 = bone20.addOrReplaceChild("cube_r358", CubeListBuilder.create().texOffs(14, 124).addBox(-0.6754F, -1.5F, -0.6617F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(32, 124).addBox(-0.6754F, -3.5F, -0.6617F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4437F, 8.0625F, -2.1186F, 0.0F, -0.8552F, 0.0F));

		PartDefinition bone21 = Right_leg.addOrReplaceChild("bone21", CubeListBuilder.create().texOffs(0, 69).addBox(-2.9375F, 0.0F, -2.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(21, 72).addBox(-2.0F, 0.5625F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(72, 14).addBox(-2.0F, 2.4375F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(82, 100).addBox(-2.0625F, 0.5625F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.375F))
				.texOffs(100, 91).addBox(-2.0625F, 2.4375F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.375F))
				.texOffs(99, 86).addBox(0.8125F, 0.5625F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.3125F))
				.texOffs(99, 98).addBox(0.8125F, 2.4375F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.3125F)), PartPose.offset(0.0F, -0.125F, 0.0F));

		PartDefinition bone22 = Right_leg.addOrReplaceChild("bone22", CubeListBuilder.create().texOffs(84, 14).addBox(-1.5F, 7.3125F, -2.5F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.1875F))
				.texOffs(114, 46).addBox(-1.5F, 8.3125F, -2.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.375F))
				.texOffs(97, 21).addBox(-1.5F, 8.3125F, -1.0625F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.3125F))
				.texOffs(41, 66).addBox(-1.5F, 8.875F, -2.5F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.125F)), PartPose.offset(-0.5F, 1.125F, 0.5F));

		PartDefinition cube_r359 = bone22.addOrReplaceChild("cube_r359", CubeListBuilder.create().texOffs(99, 27).addBox(-2.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.5F, 9.875F, 0.625F, 0.2094F, 0.0F, 0.0F));

		PartDefinition cube_r360 = bone22.addOrReplaceChild("cube_r360", CubeListBuilder.create().texOffs(33, 86).addBox(-6.0F, -1.0F, -0.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.4375F)), PartPose.offsetAndRotation(4.5F, 9.0625F, 1.25F, -0.0698F, 0.0F, 0.0F));

		PartDefinition cube_r361 = bone22.addOrReplaceChild("cube_r361", CubeListBuilder.create().texOffs(109, 23).addBox(-6.0F, -1.0F, -0.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.3125F)), PartPose.offsetAndRotation(4.5F, 8.0625F, 1.2969F, -0.1222F, 0.0F, 0.0F));

		PartDefinition cube_r362 = bone22.addOrReplaceChild("cube_r362", CubeListBuilder.create().texOffs(53, 66).addBox(-1.5F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 7.625F, -2.375F, 0.1745F, 0.0F, 0.0F));

		PartDefinition bone23 = Right_leg.addOrReplaceChild("bone23", CubeListBuilder.create().texOffs(118, 52).addBox(-1.0F, -2.9219F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(49, 123).addBox(-1.0F, 0.7656F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.125F))
				.texOffs(130, 44).addBox(-1.0F, -1.9219F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.125F))
				.texOffs(22, 130).addBox(-1.0F, -0.4219F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(-2.798F, 7.8905F, 0.2205F, -1.5708F, -0.7854F, -1.5708F));

		PartDefinition Left_Leg = partdefinition.addOrReplaceChild("Left_Leg", CubeListBuilder.create().texOffs(20, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0625F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition bone25 = Left_Leg.addOrReplaceChild("bone25", CubeListBuilder.create().texOffs(71, 38).addBox(-2.0F, 0.5625F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(68, 70).addBox(1.9375F, 0.0F, -2.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(84, 44).addBox(2.25F, 0.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(71, 29).addBox(-2.0F, 2.4375F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(76, 99).addBox(1.0625F, 0.5625F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.375F))
				.texOffs(54, 99).addBox(1.0625F, 2.4375F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.375F))
				.texOffs(8, 99).addBox(-1.8125F, 0.5625F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.3125F))
				.texOffs(35, 98).addBox(-1.8125F, 2.4375F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.3125F)), PartPose.offset(0.0F, -0.125F, 0.0F));

		PartDefinition bone26 = Left_Leg.addOrReplaceChild("bone26", CubeListBuilder.create().texOffs(83, 37).addBox(-2.5F, 7.3125F, -2.5F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.1875F))
				.texOffs(113, 89).addBox(-2.5F, 8.3125F, -2.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.375F))
				.texOffs(97, 65).addBox(-2.5F, 8.3125F, -1.0625F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.3125F))
				.texOffs(25, 66).addBox(-2.5F, 8.875F, -2.5F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.125F)), PartPose.offset(0.5F, 1.125F, 0.5F));

		PartDefinition cube_r363 = bone26.addOrReplaceChild("cube_r363", CubeListBuilder.create().texOffs(99, 24).addBox(-2.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-0.5F, 9.875F, 0.625F, 0.2094F, 0.0F, 0.0F));

		PartDefinition cube_r364 = bone26.addOrReplaceChild("cube_r364", CubeListBuilder.create().texOffs(38, 52).addBox(2.0F, -1.0F, -0.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.4375F)), PartPose.offsetAndRotation(-4.5F, 9.0625F, 1.25F, -0.0698F, 0.0F, 0.0F));

		PartDefinition cube_r365 = bone26.addOrReplaceChild("cube_r365", CubeListBuilder.create().texOffs(106, 92).addBox(2.0F, -1.0F, -0.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.3125F)), PartPose.offsetAndRotation(-4.5F, 8.0625F, 1.2969F, -0.1222F, 0.0F, 0.0F));

		PartDefinition cube_r366 = bone26.addOrReplaceChild("cube_r366", CubeListBuilder.create().texOffs(65, 54).addBox(-1.5F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 7.625F, -2.375F, 0.1745F, 0.0F, 0.0F));

		PartDefinition bone24 = Left_Leg.addOrReplaceChild("bone24", CubeListBuilder.create().texOffs(113, 48).addBox(-1.5F, 4.5625F, -2.6875F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.125F))
				.texOffs(113, 26).addBox(-1.5F, 4.5625F, -3.0625F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 17).addBox(-2.0F, 6.4375F, 0.1875F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.125F))
				.texOffs(0, 99).addBox(-2.0F, 4.6875F, 0.1875F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.125F))
				.texOffs(9, 75).addBox(-2.0F, 4.5625F, -1.9375F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.1875F)), PartPose.offset(0.0F, -0.3125F, 0.0F));

		PartDefinition cube_r367 = bone24.addOrReplaceChild("cube_r367", CubeListBuilder.create().texOffs(0, 0).addBox(-0.3199F, -3.5F, -0.3786F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(1.5368F, 8.0625F, -2.1463F, 0.0F, -0.8552F, 0.0F));

		PartDefinition cube_r368 = bone24.addOrReplaceChild("cube_r368", CubeListBuilder.create().texOffs(0, 16).addBox(-0.6801F, -3.5F, -0.3786F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(-1.5368F, 8.0625F, -2.1463F, 0.0F, 0.8552F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Left_Arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Right_Arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Left_Leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}