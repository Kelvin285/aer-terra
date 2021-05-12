package kelvin.trewrite.entities.models.trees;
import kelvin.trewrite.entities.models.CubePart;
import kelvin.trewrite.entities.models.CustomModel;
import kelvin.trewrite.entities.trees.BasicTreeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

@Environment(EnvType.CLIENT)
public class BasicTreeModel extends CustomModel<BasicTreeEntity> {
	private final CubePart bb_main;
	private final CubePart cube_r1;
	private final CubePart cube_r2;
	private final CubePart cube_r3;
	private final CubePart cube_r4;
	private final CubePart cube_r5;
	private final CubePart cube_r6;
	private final CubePart cube_r7;
	private final CubePart cube_r8;
	private final CubePart cube_r9;
	private final CubePart cube_r10;
	private final CubePart cube_r11;
	private final CubePart cube_r12;
	private final CubePart cube_r13;
	private final CubePart cube_r14;
	private final CubePart cube_r15;
	private final CubePart cube_r16;
	private final CubePart cube_r17;
	private final CubePart cube_r18;
	private final CubePart cube_r19;
	private final CubePart cube_r20;
	private final CubePart cube_r21;
	private final CubePart cube_r22;
	private final CubePart cube_r23;
	private final CubePart cube_r24;
	private final CubePart cube_r25;
	private final CubePart cube_r26;
	private final CubePart cube_r27;
	private final CubePart cube_r28;
	private final CubePart cube_r29;
	private final CubePart cube_r30;
	private final CubePart cube_r31;
	private final CubePart cube_r32;
	private final CubePart cube_r33;
	private final CubePart cube_r34;
	private final CubePart cube_r35;
	private final CubePart cube_r36;
	private final CubePart cube_r37;
	private final CubePart cube_r38;
	private final CubePart cube_r39;
	private final CubePart cube_r40;
	private final CubePart cube_r41;
	private final CubePart cube_r42;
	private final CubePart cube_r43;
	private final CubePart cube_r44;
	private final CubePart cube_r45;
	private final CubePart cube_r46;
	private final CubePart cube_r47;
	private final CubePart cube_r48;
	private final CubePart cube_r49;
	private final CubePart cube_r50;
	private final CubePart cube_r51;
	private final CubePart cube_r52;
	private final CubePart cube_r53;
	private final CubePart cube_r54;
	private final CubePart cube_r55;
	private final CubePart cube_r56;
	private final CubePart cube_r57;
	private final CubePart cube_r58;
	private final CubePart cube_r59;
	private final CubePart cube_r60;
	private final CubePart cube_r61;
	private final CubePart cube_r62;
	private final CubePart cube_r63;
	private final CubePart cube_r64;
	private final CubePart cube_r65;
	private final CubePart cube_r66;
	private final CubePart Trunk_r1;
	private final CubePart Trunk_r2;
	private final CubePart Trunk_r3;
	private final CubePart Trunk_r4;
	private final CubePart Trunk_r5;
	private final CubePart Trunk_r6;
	private final CubePart Trunk_r7;
	private final CubePart Trunk_r8;
	private final CubePart Trunk_r9;
	private final CubePart Trunk_r10;
	private final CubePart Trunk_r11;
	
	
	public BasicTreeModel(int textureWidth, int textureHeight) {
		super(textureWidth, textureHeight);

		bb_main = new CubePart(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.setTextureOffset(0, 0).addBox(-15.0F, -64.0F, -14.0F, 25.0F, 64.0F, 25.0F, 0.0F, false);

		cube_r1 = new CubePart(this);
		cube_r1.setRotationPoint(4.0F, -131.0F, -26.0F);
		bb_main.addChild(cube_r1);
		setRotationAngle(cube_r1, 2.9683F, 0.5973F, 0.2856F);
		cube_r1.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r2 = new CubePart(this);
		cube_r2.setRotationPoint(-2.0F, -164.0F, -8.0F);
		bb_main.addChild(cube_r2);
		setRotationAngle(cube_r2, -2.3004F, -0.7737F, 1.4642F);
		cube_r2.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r3 = new CubePart(this);
		cube_r3.setRotationPoint(-2.0F, -164.0F, -8.0F);
		bb_main.addChild(cube_r3);
		setRotationAngle(cube_r3, -0.6518F, -0.4971F, -0.6651F);
		cube_r3.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r4 = new CubePart(this);
		cube_r4.setRotationPoint(-2.0F, -164.0F, -8.0F);
		bb_main.addChild(cube_r4);
		setRotationAngle(cube_r4, -2.2226F, -0.4971F, -0.6651F);
		cube_r4.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r5 = new CubePart(this);
		cube_r5.setRotationPoint(-15.0F, -155.0F, 26.0F);
		bb_main.addChild(cube_r5);
		setRotationAngle(cube_r5, -2.3004F, -0.7737F, 1.4642F);
		cube_r5.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r6 = new CubePart(this);
		cube_r6.setRotationPoint(-15.0F, -155.0F, 26.0F);
		bb_main.addChild(cube_r6);
		setRotationAngle(cube_r6, -0.6518F, -0.4971F, -0.6651F);
		cube_r6.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r7 = new CubePart(this);
		cube_r7.setRotationPoint(-15.0F, -155.0F, 26.0F);
		bb_main.addChild(cube_r7);
		setRotationAngle(cube_r7, -2.2226F, -0.4971F, -0.6651F);
		cube_r7.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r8 = new CubePart(this);
		cube_r8.setRotationPoint(-24.0F, -155.0F, -2.0F);
		bb_main.addChild(cube_r8);
		setRotationAngle(cube_r8, -2.3004F, -0.7737F, 1.4642F);
		cube_r8.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r9 = new CubePart(this);
		cube_r9.setRotationPoint(-24.0F, -155.0F, -2.0F);
		bb_main.addChild(cube_r9);
		setRotationAngle(cube_r9, -0.6518F, -0.4971F, -0.6651F);
		cube_r9.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r10 = new CubePart(this);
		cube_r10.setRotationPoint(-24.0F, -155.0F, -2.0F);
		bb_main.addChild(cube_r10);
		setRotationAngle(cube_r10, -2.2226F, -0.4971F, -0.6651F);
		cube_r10.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r11 = new CubePart(this);
		cube_r11.setRotationPoint(24.0F, -111.0F, -2.0F);
		bb_main.addChild(cube_r11);
		setRotationAngle(cube_r11, -2.3004F, -0.7737F, 1.4642F);
		cube_r11.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r12 = new CubePart(this);
		cube_r12.setRotationPoint(24.0F, -111.0F, -2.0F);
		bb_main.addChild(cube_r12);
		setRotationAngle(cube_r12, -0.6518F, -0.4971F, -0.6651F);
		cube_r12.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r13 = new CubePart(this);
		cube_r13.setRotationPoint(24.0F, -111.0F, -2.0F);
		bb_main.addChild(cube_r13);
		setRotationAngle(cube_r13, -2.2226F, -0.4971F, -0.6651F);
		cube_r13.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r14 = new CubePart(this);
		cube_r14.setRotationPoint(36.0F, -120.0F, -2.0F);
		bb_main.addChild(cube_r14);
		setRotationAngle(cube_r14, -2.3004F, -0.7737F, 1.4642F);
		cube_r14.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r15 = new CubePart(this);
		cube_r15.setRotationPoint(36.0F, -120.0F, -2.0F);
		bb_main.addChild(cube_r15);
		setRotationAngle(cube_r15, -0.6518F, -0.4971F, -0.6651F);
		cube_r15.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r16 = new CubePart(this);
		cube_r16.setRotationPoint(36.0F, -120.0F, -2.0F);
		bb_main.addChild(cube_r16);
		setRotationAngle(cube_r16, -2.2226F, -0.4971F, -0.6651F);
		cube_r16.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r17 = new CubePart(this);
		cube_r17.setRotationPoint(19.0F, -125.0F, 39.0F);
		bb_main.addChild(cube_r17);
		setRotationAngle(cube_r17, -2.3004F, -0.7737F, 1.4642F);
		cube_r17.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r18 = new CubePart(this);
		cube_r18.setRotationPoint(19.0F, -125.0F, 39.0F);
		bb_main.addChild(cube_r18);
		setRotationAngle(cube_r18, -0.6518F, -0.4971F, -0.6651F);
		cube_r18.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r19 = new CubePart(this);
		cube_r19.setRotationPoint(19.0F, -125.0F, 39.0F);
		bb_main.addChild(cube_r19);
		setRotationAngle(cube_r19, -2.2226F, -0.4971F, -0.6651F);
		cube_r19.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r20 = new CubePart(this);
		cube_r20.setRotationPoint(-19.0F, -125.0F, 39.0F);
		bb_main.addChild(cube_r20);
		setRotationAngle(cube_r20, -2.3004F, -0.7737F, 1.4642F);
		cube_r20.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r21 = new CubePart(this);
		cube_r21.setRotationPoint(-19.0F, -125.0F, 39.0F);
		bb_main.addChild(cube_r21);
		setRotationAngle(cube_r21, -0.6518F, -0.4971F, -0.6651F);
		cube_r21.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r22 = new CubePart(this);
		cube_r22.setRotationPoint(-19.0F, -125.0F, 39.0F);
		bb_main.addChild(cube_r22);
		setRotationAngle(cube_r22, -2.2226F, -0.4971F, -0.6651F);
		cube_r22.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r23 = new CubePart(this);
		cube_r23.setRotationPoint(-19.0F, -125.0F, -36.0F);
		bb_main.addChild(cube_r23);
		setRotationAngle(cube_r23, -2.4073F, 0.4003F, 1.4881F);
		cube_r23.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r24 = new CubePart(this);
		cube_r24.setRotationPoint(-19.0F, -125.0F, -36.0F);
		bb_main.addChild(cube_r24);
		setRotationAngle(cube_r24, -2.1341F, -0.7527F, 0.3249F);
		cube_r24.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r25 = new CubePart(this);
		cube_r25.setRotationPoint(-19.0F, -125.0F, -36.0F);
		bb_main.addChild(cube_r25);
		setRotationAngle(cube_r25, 2.5783F, -0.7527F, 0.3249F);
		cube_r25.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r26 = new CubePart(this);
		cube_r26.setRotationPoint(16.0F, -118.0F, -40.0F);
		bb_main.addChild(cube_r26);
		setRotationAngle(cube_r26, -2.4073F, 0.4003F, 1.4881F);
		cube_r26.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r27 = new CubePart(this);
		cube_r27.setRotationPoint(16.0F, -118.0F, -40.0F);
		bb_main.addChild(cube_r27);
		setRotationAngle(cube_r27, -2.1341F, -0.7527F, 0.3249F);
		cube_r27.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r28 = new CubePart(this);
		cube_r28.setRotationPoint(16.0F, -118.0F, -40.0F);
		bb_main.addChild(cube_r28);
		setRotationAngle(cube_r28, 2.5783F, -0.7527F, 0.3249F);
		cube_r28.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r29 = new CubePart(this);
		cube_r29.setRotationPoint(-29.0F, -131.0F, -20.0F);
		bb_main.addChild(cube_r29);
		setRotationAngle(cube_r29, -0.9664F, 0.1431F, 1.9546F);
		cube_r29.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r30 = new CubePart(this);
		cube_r30.setRotationPoint(-29.0F, -131.0F, -20.0F);
		bb_main.addChild(cube_r30);
		setRotationAngle(cube_r30, -1.7441F, 0.5973F, 0.2856F);
		cube_r30.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r31 = new CubePart(this);
		cube_r31.setRotationPoint(-29.0F, -131.0F, -20.0F);
		bb_main.addChild(cube_r31);
		setRotationAngle(cube_r31, 2.9683F, 0.5973F, 0.2856F);
		cube_r31.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r32 = new CubePart(this);
		cube_r32.setRotationPoint(-15.0F, -131.0F, -3.0F);
		bb_main.addChild(cube_r32);
		setRotationAngle(cube_r32, -0.9664F, 0.1431F, 1.9546F);
		cube_r32.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r33 = new CubePart(this);
		cube_r33.setRotationPoint(-15.0F, -131.0F, -3.0F);
		bb_main.addChild(cube_r33);
		setRotationAngle(cube_r33, -1.7441F, 0.5973F, 0.2856F);
		cube_r33.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r34 = new CubePart(this);
		cube_r34.setRotationPoint(-15.0F, -131.0F, -3.0F);
		bb_main.addChild(cube_r34);
		setRotationAngle(cube_r34, 2.9683F, 0.5973F, 0.2856F);
		cube_r34.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r35 = new CubePart(this);
		cube_r35.setRotationPoint(4.0F, -131.0F, -26.0F);
		bb_main.addChild(cube_r35);
		setRotationAngle(cube_r35, -0.9664F, 0.1431F, 1.9546F);
		cube_r35.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r36 = new CubePart(this);
		cube_r36.setRotationPoint(4.0F, -131.0F, -26.0F);
		bb_main.addChild(cube_r36);
		setRotationAngle(cube_r36, -1.7441F, 0.5973F, 0.2856F);
		cube_r36.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r37 = new CubePart(this);
		cube_r37.setRotationPoint(29.0F, -131.0F, -14.0F);
		bb_main.addChild(cube_r37);
		setRotationAngle(cube_r37, 2.9683F, 0.5973F, 0.2856F);
		cube_r37.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r38 = new CubePart(this);
		cube_r38.setRotationPoint(29.0F, -131.0F, -14.0F);
		bb_main.addChild(cube_r38);
		setRotationAngle(cube_r38, -0.9664F, 0.1431F, 1.9546F);
		cube_r38.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r39 = new CubePart(this);
		cube_r39.setRotationPoint(29.0F, -131.0F, -14.0F);
		bb_main.addChild(cube_r39);
		setRotationAngle(cube_r39, -1.7441F, 0.5973F, 0.2856F);
		cube_r39.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r40 = new CubePart(this);
		cube_r40.setRotationPoint(29.0F, -131.0F, 15.0F);
		bb_main.addChild(cube_r40);
		setRotationAngle(cube_r40, 2.9683F, 0.5973F, 0.2856F);
		cube_r40.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r41 = new CubePart(this);
		cube_r41.setRotationPoint(29.0F, -131.0F, 15.0F);
		bb_main.addChild(cube_r41);
		setRotationAngle(cube_r41, -0.9664F, 0.1431F, 1.9546F);
		cube_r41.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r42 = new CubePart(this);
		cube_r42.setRotationPoint(29.0F, -131.0F, 15.0F);
		bb_main.addChild(cube_r42);
		setRotationAngle(cube_r42, -1.7441F, 0.5973F, 0.2856F);
		cube_r42.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r43 = new CubePart(this);
		cube_r43.setRotationPoint(17.0F, -142.0F, -5.0F);
		bb_main.addChild(cube_r43);
		setRotationAngle(cube_r43, 2.9683F, 0.5973F, 0.2856F);
		cube_r43.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r44 = new CubePart(this);
		cube_r44.setRotationPoint(17.0F, -142.0F, -5.0F);
		bb_main.addChild(cube_r44);
		setRotationAngle(cube_r44, -0.9664F, 0.1431F, 1.9546F);
		cube_r44.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r45 = new CubePart(this);
		cube_r45.setRotationPoint(17.0F, -142.0F, -5.0F);
		bb_main.addChild(cube_r45);
		setRotationAngle(cube_r45, -1.7441F, 0.5973F, 0.2856F);
		cube_r45.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r46 = new CubePart(this);
		cube_r46.setRotationPoint(-1.0F, -157.0F, -5.0F);
		bb_main.addChild(cube_r46);
		setRotationAngle(cube_r46, -2.2253F, 0.0F, 0.6545F);
		cube_r46.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r47 = new CubePart(this);
		cube_r47.setRotationPoint(-1.0F, -157.0F, -5.0F);
		bb_main.addChild(cube_r47);
		setRotationAngle(cube_r47, -1.5708F, -0.9163F, 2.2253F);
		cube_r47.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r48 = new CubePart(this);
		cube_r48.setRotationPoint(-1.0F, -157.0F, -5.0F);
		bb_main.addChild(cube_r48);
		setRotationAngle(cube_r48, -0.6545F, 0.0F, 0.6545F);
		cube_r48.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r49 = new CubePart(this);
		cube_r49.setRotationPoint(-1.0F, -157.0F, 20.0F);
		bb_main.addChild(cube_r49);
		setRotationAngle(cube_r49, -2.2253F, 0.0F, 0.6545F);
		cube_r49.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r50 = new CubePart(this);
		cube_r50.setRotationPoint(-1.0F, -157.0F, 20.0F);
		bb_main.addChild(cube_r50);
		setRotationAngle(cube_r50, -1.5708F, -0.9163F, 2.2253F);
		cube_r50.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r51 = new CubePart(this);
		cube_r51.setRotationPoint(-1.0F, -157.0F, 20.0F);
		bb_main.addChild(cube_r51);
		setRotationAngle(cube_r51, -0.6545F, 0.0F, 0.6545F);
		cube_r51.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r52 = new CubePart(this);
		cube_r52.setRotationPoint(-1.0F, -142.0F, 36.0F);
		bb_main.addChild(cube_r52);
		setRotationAngle(cube_r52, -2.2253F, 0.0F, 0.6545F);
		cube_r52.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r53 = new CubePart(this);
		cube_r53.setRotationPoint(-1.0F, -142.0F, 36.0F);
		bb_main.addChild(cube_r53);
		setRotationAngle(cube_r53, -1.5708F, -0.9163F, 2.2253F);
		cube_r53.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r54 = new CubePart(this);
		cube_r54.setRotationPoint(-1.0F, -142.0F, 36.0F);
		bb_main.addChild(cube_r54);
		setRotationAngle(cube_r54, -0.6545F, 0.0F, 0.6545F);
		cube_r54.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r55 = new CubePart(this);
		cube_r55.setRotationPoint(18.0F, -142.0F, 22.0F);
		bb_main.addChild(cube_r55);
		setRotationAngle(cube_r55, -2.2253F, 0.0F, 0.6545F);
		cube_r55.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r56 = new CubePart(this);
		cube_r56.setRotationPoint(18.0F, -142.0F, 22.0F);
		bb_main.addChild(cube_r56);
		setRotationAngle(cube_r56, -1.5708F, -0.9163F, 2.2253F);
		cube_r56.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r57 = new CubePart(this);
		cube_r57.setRotationPoint(18.0F, -142.0F, 22.0F);
		bb_main.addChild(cube_r57);
		setRotationAngle(cube_r57, -0.6545F, 0.0F, 0.6545F);
		cube_r57.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r58 = new CubePart(this);
		cube_r58.setRotationPoint(-10.0F, -142.0F, -24.0F);
		bb_main.addChild(cube_r58);
		setRotationAngle(cube_r58, -2.2253F, 0.0F, 0.6545F);
		cube_r58.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r59 = new CubePart(this);
		cube_r59.setRotationPoint(-10.0F, -142.0F, -24.0F);
		bb_main.addChild(cube_r59);
		setRotationAngle(cube_r59, -1.5708F, -0.9163F, 2.2253F);
		cube_r59.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r60 = new CubePart(this);
		cube_r60.setRotationPoint(-10.0F, -142.0F, -24.0F);
		bb_main.addChild(cube_r60);
		setRotationAngle(cube_r60, -0.6545F, 0.0F, 0.6545F);
		cube_r60.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r61 = new CubePart(this);
		cube_r61.setRotationPoint(-23.0F, -133.0F, 16.0F);
		bb_main.addChild(cube_r61);
		setRotationAngle(cube_r61, -2.2253F, 0.0F, 0.6545F);
		cube_r61.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r62 = new CubePart(this);
		cube_r62.setRotationPoint(-23.0F, -133.0F, 16.0F);
		bb_main.addChild(cube_r62);
		setRotationAngle(cube_r62, -1.5708F, -0.9163F, 2.2253F);
		cube_r62.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r63 = new CubePart(this);
		cube_r63.setRotationPoint(-23.0F, -133.0F, 16.0F);
		bb_main.addChild(cube_r63);
		setRotationAngle(cube_r63, -0.6545F, 0.0F, 0.6545F);
		cube_r63.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r64 = new CubePart(this);
		cube_r64.setRotationPoint(-3.0F, -87.0F, 42.0F);
		bb_main.addChild(cube_r64);
		setRotationAngle(cube_r64, -1.5708F, -0.9163F, 2.2253F);
		cube_r64.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r65 = new CubePart(this);
		cube_r65.setRotationPoint(-3.0F, -87.0F, 42.0F);
		bb_main.addChild(cube_r65);
		setRotationAngle(cube_r65, -2.2253F, 0.0F, 0.6545F);
		cube_r65.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		cube_r66 = new CubePart(this);
		cube_r66.setRotationPoint(-3.0F, -87.0F, 42.0F);
		bb_main.addChild(cube_r66);
		setRotationAngle(cube_r66, -0.6545F, 0.0F, 0.6545F);
		cube_r66.setTextureOffset(64, 126).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, 0.0F, false);

		Trunk_r1 = new CubePart(this);
		Trunk_r1.setRotationPoint(11.0F, -100.0F, -23.0F);
		bb_main.addChild(Trunk_r1);
		setRotationAngle(Trunk_r1, -0.1745F, 0.0F, 0.0F);
		Trunk_r1.setTextureOffset(0, 25).addBox(-16.0F, -47.0F, 11.0F, 16.0F, 47.0F, 16.0F, 0.0F, false);

		Trunk_r2 = new CubePart(this);
		Trunk_r2.setRotationPoint(15.0F, -115.0F, -19.0F);
		bb_main.addChild(Trunk_r2);
		setRotationAngle(Trunk_r2, -0.3054F, 0.0F, 0.4363F);
		Trunk_r2.setTextureOffset(0, 25).addBox(-6.0F, -21.0F, 22.0F, 7.0F, 21.0F, 6.0F, 0.0F, false);

		Trunk_r3 = new CubePart(this);
		Trunk_r3.setRotationPoint(10.0F, -96.0F, -26.0F);
		bb_main.addChild(Trunk_r3);
		setRotationAngle(Trunk_r3, 0.829F, 0.0F, 0.0F);
		Trunk_r3.setTextureOffset(0, 25).addBox(-6.0F, -21.0F, 22.0F, 7.0F, 21.0F, 6.0F, 0.0F, false);

		Trunk_r4 = new CubePart(this);
		Trunk_r4.setRotationPoint(-13.0F, -119.0F, -19.0F);
		bb_main.addChild(Trunk_r4);
		setRotationAngle(Trunk_r4, -0.4363F, 0.0F, -1.0472F);
		Trunk_r4.setTextureOffset(0, 25).addBox(-6.0F, -21.0F, 22.0F, 7.0F, 21.0F, 6.0F, 0.0F, false);

		Trunk_r5 = new CubePart(this);
		Trunk_r5.setRotationPoint(1.0F, -115.0F, -28.0F);
		bb_main.addChild(Trunk_r5);
		setRotationAngle(Trunk_r5, 0.3491F, 0.0F, -0.7854F);
		Trunk_r5.setTextureOffset(0, 25).addBox(-6.0F, -21.0F, 22.0F, 7.0F, 21.0F, 6.0F, 0.0F, false);

		Trunk_r6 = new CubePart(this);
		Trunk_r6.setRotationPoint(0.0F, -83.0F, -12.0F);
		bb_main.addChild(Trunk_r6);
		setRotationAngle(Trunk_r6, -1.0036F, 0.0F, 0.0F);
		Trunk_r6.setTextureOffset(0, 25).addBox(-6.0F, -21.0F, 22.0F, 7.0F, 21.0F, 6.0F, 0.0F, false);
		Trunk_r6.setTextureOffset(0, 25).addBox(-5.0F, -47.0F, 23.0F, 5.0F, 47.0F, 4.0F, 0.0F, false);

		Trunk_r7 = new CubePart(this);
		Trunk_r7.setRotationPoint(-1.6F, -57.0F, -2.9F);
		bb_main.addChild(Trunk_r7);
		setRotationAngle(Trunk_r7, 0.0F, 0.0F, 0.0873F);
		Trunk_r7.setTextureOffset(0, 0).addBox(-13.0F, -64.0F, -10.0F, 23.0F, 64.0F, 23.0F, 0.0F, false);

		Trunk_r8 = new CubePart(this);
		Trunk_r8.setRotationPoint(20.0F, 7.0F, -20.0F);
		bb_main.addChild(Trunk_r8);
		setRotationAngle(Trunk_r8, 0.0F, 0.0F, -0.1745F);
		Trunk_r8.setTextureOffset(0, 25).addBox(-16.0F, -47.0F, 11.0F, 16.0F, 38.0F, 16.0F, 0.0F, false);

		Trunk_r9 = new CubePart(this);
		Trunk_r9.setRotationPoint(-8.0F, 7.0F, -20.0F);
		bb_main.addChild(Trunk_r9);
		setRotationAngle(Trunk_r9, 0.0F, 0.0F, 0.1309F);
		Trunk_r9.setTextureOffset(0, 25).addBox(-16.0F, -47.0F, 11.0F, 16.0F, 41.0F, 16.0F, 0.0F, false);

		Trunk_r10 = new CubePart(this);
		Trunk_r10.setRotationPoint(7.0F, 6.0F, -38.0F);
		bb_main.addChild(Trunk_r10);
		setRotationAngle(Trunk_r10, -0.1745F, 0.0F, 0.0F);
		Trunk_r10.setTextureOffset(0, 25).addBox(-16.0F, -47.0F, 11.0F, 16.0F, 37.0F, 16.0F, 0.0F, false);

		Trunk_r11 = new CubePart(this);
		Trunk_r11.setRotationPoint(7.0F, 7.0F, -6.0F);
		bb_main.addChild(Trunk_r11);
		setRotationAngle(Trunk_r11, 0.1309F, 0.0F, 0.0F);
		Trunk_r11.setTextureOffset(0, 25).addBox(-16.0F, -47.0F, 11.0F, 16.0F, 42.0F, 16.0F, 0.0F, false);
		
		this.getTexturedModelData();
	}

	public void setRotationAngle(CubePart CubePart, float x, float y, float z) {
		CubePart.rotateAngleX = x;
		CubePart.rotateAngleY = y;
		CubePart.rotateAngleZ = z;
	}
}