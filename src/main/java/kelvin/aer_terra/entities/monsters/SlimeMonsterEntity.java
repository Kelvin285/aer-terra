package kelvin.aer_terra.entities.monsters;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class SlimeMonsterEntity extends SlimeEntity {

	public boolean stick = false;
	
	public SlimeMonsterEntity(EntityType<? extends SlimeMonsterEntity> entityType, World world) {
		super(entityType, world);
		this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
		this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(5);
	}
	
	@Override
	protected void setSize(int size, boolean heal) {
		
	}
	
	@Override
	public int getSize() {
		return 1;
	}
	
	@Override
	public boolean canTarget(LivingEntity entity) {
		return entity == this.getAttacker();
	}
	
	@Override
	public void remove(RemovalReason reason) {
		this.setRemoved(reason);
		if (reason == RemovalReason.KILLED) {
			this.emitGameEvent(GameEvent.ENTITY_KILLED);
		}
	}
	
	public boolean isCollidable() {
		return !stick;
	}
	
	@Override
	public void tick() {
		super.tick();
		this.noClip = false;
		

		if (this.getTarget() != null) {
			
			if (this.getTarget().getEyePos().distanceTo(this.getPos()) <= 0.5F || stick) {
				if ((getTarget().isSprinting() || !getTarget().isOnGround()) && random.nextInt(10) == 0) {
					stick = false;
					this.jump();
				} else {
					stick = true;
					//this.move(MovementType.SELF, new Vec3d(getTarget().getX() - getX(), getTarget().getY() - getY(), getTarget().getZ() - getZ()));
					this.noClip = true;
					this.setPos(getTarget().getX(), getTarget().getEyeY() - 0.5f, getTarget().getZ());
					this.tryAttack(getTarget());
					
					//this.setPos(MathHelper.lerp(0.5f, getX(), getTarget().getX()), MathHelper.lerp(0.5f, getY(), getTarget().getEyeY() + 1.5f), MathHelper.lerp(0.5f, getZ(), getTarget().getZ()));
				
				}
			}
			if (!this.getTarget().isAlive()) {
				stick = false;
			}
			if (this.getPos().distanceTo(getTarget().getPos()) <= 0.5F) {
				this.tryAttack(getTarget());
			}
		} else {
			stick = false;
		}
	}

}
