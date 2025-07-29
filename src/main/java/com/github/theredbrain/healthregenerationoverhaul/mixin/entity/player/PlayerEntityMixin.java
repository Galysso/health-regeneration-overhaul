package com.github.theredbrain.healthregenerationoverhaul.mixin.entity.player;

import com.github.theredbrain.healthregenerationoverhaul.HealthRegenerationOverhaul;
import com.github.theredbrain.healthregenerationoverhaul.entity.HealthRegeneratingEntity;
import com.google.common.collect.HashMultimap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements HealthRegeneratingEntity {

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setHealth(F)V", shift = At.Shift.AFTER))
	protected void healthregenerationoverhaul$applyDamage(DamageSource source, float amount, CallbackInfo ci) {
		this.healthregenerationoverhaul$resetTickCounters();
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void healthregenerationoverhaul$tick(CallbackInfo ci) {
		if (this.getServer() != null && this.getWorld().getGameRules().getBoolean(GameRules.NATURAL_REGENERATION)) {
			this.getAttributes().addTemporaryModifiers(getNaturalHealthRegenerationModifier());
		} else {
			this.getAttributes().removeModifiers(getNaturalHealthRegenerationModifier());
		}
	}

	@Unique
	private HashMultimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getNaturalHealthRegenerationModifier() {
		HashMultimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> hashMultimap = HashMultimap.create();
		hashMultimap.put(HealthRegenerationOverhaul.HEALTH_REGENERATION, new EntityAttributeModifier(HealthRegenerationOverhaul.identifier("natural_health_regeneration_modifier"), 1.0, EntityAttributeModifier.Operation.ADD_VALUE));
		return hashMultimap;
	}
}
