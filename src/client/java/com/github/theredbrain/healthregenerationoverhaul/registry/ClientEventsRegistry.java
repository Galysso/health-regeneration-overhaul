package com.github.theredbrain.healthregenerationoverhaul.registry;

import com.github.theredbrain.healthregenerationoverhaul.HealthRegenerationOverhaul;
import com.github.theredbrain.healthregenerationoverhaul.HealthRegenerationOverhaulClient;
import com.github.theredbrain.healthregenerationoverhaul.config.ClientConfig;
import com.github.theredbrain.healthregenerationoverhaul.entity.HealthRegeneratingEntity;
import com.github.theredbrain.resourcebarapi.ResourceBarAPI;
import com.github.theredbrain.resourcebarapi.ResourceBarAPIClient;
import me.fzzyhmstrs.fzzy_config.api.ConfigApi;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.ArrayList;

public class ClientEventsRegistry {
    // Health
	private static final String RESOURCE_BAR_IDENTIFIER_STRING = HealthRegenerationOverhaul.MOD_ID + ":health";
    private static final Identifier ICON_HEALTH_CONTAINER = Identifier.ofVanilla("hud/heart/container");
	private static final Identifier ICON_HEALTH_CONTAINER_HARDCORE = Identifier.ofVanilla("hud/heart/container_hardcore");
	private static final Identifier ICON_HEALTH_FULL = Identifier.ofVanilla("hud/heart/full");
	private static final Identifier ICON_HEALTH_HALF = Identifier.ofVanilla("hud/heart/half");
	private static final Identifier ICON_HEALTH_FULL_POISONED = Identifier.ofVanilla("hud/heart/poisoned_full");
	private static final Identifier ICON_HEALTH_HALF_POISONED = Identifier.ofVanilla("hud/heart/poisoned_half");
	private static final Identifier ICON_HEALTH_FULL_WITHERED = Identifier.ofVanilla("hud/heart/withered_full");
	private static final Identifier ICON_HEALTH_HALF_WITHERED = Identifier.ofVanilla("hud/heart/withered_half");
	private static final Identifier ICON_HEALTH_FULL_FROZEN = Identifier.ofVanilla("hud/heart/frozen_full");
	private static final Identifier ICON_HEALTH_HALF_FROZEN = Identifier.ofVanilla("hud/heart/frozen_half");
	private static final Identifier ICON_HEALTH_FULL_HARDCORE = Identifier.ofVanilla("hud/heart/hardcore_full");
	private static final Identifier ICON_HEALTH_HALF_HARDCORE = Identifier.ofVanilla("hud/heart/hardcore_half");
	private static final Identifier ICON_HEALTH_FULL_POISONED_HARDCORE = Identifier.ofVanilla("hud/heart/poisoned_hardcore_full");
	private static final Identifier ICON_HEALTH_HALF_POISONED_HARDCORE = Identifier.ofVanilla("hud/heart/poisoned_hardcore_half");
	private static final Identifier ICON_HEALTH_FULL_WITHERED_HARDCORE = Identifier.ofVanilla("hud/heart/withered_hardcore_full");
	private static final Identifier ICON_HEALTH_HALF_WITHERED_HARDCORE = Identifier.ofVanilla("hud/heart/withered_hardcore_half");
	private static final Identifier ICON_HEALTH_FULL_FROZEN_HARDCORE = Identifier.ofVanilla("hud/heart/frozen_hardcore_full");
	private static final Identifier ICON_HEALTH_HALF_FROZEN_HARDCORE = Identifier.ofVanilla("hud/heart/frozen_hardcore_half");

    // Absorption
    private static final String ABSORPTION_BAR_IDENTIFIER_STRING = HealthRegenerationOverhaul.MOD_ID + ":absorption";
    private static final Identifier ICON_ABSORPTION_CONTAINER = Identifier.of("healthregenerationoverhaul", "hud/absorption/container");
    private static final Identifier ICON_ABSORPTION_CONTAINER_HARDCORE = Identifier.of("healthregenerationoverhaul", "hud/absorption/container_hardcore");
    private static final Identifier ICON_ABSORPTION_FULL = Identifier.of("healthregenerationoverhaul", "hud/absorption/full");
    private static final Identifier ICON_ABSORPTION_HALF = Identifier.of("healthregenerationoverhaul", "hud/absorption/half");
    private static double prevAbsorptionLevel = -1.0;

    public static void initializeClientEvents() {
		HudRenderCallback.EVENT.register((matrixStack, delta) -> {
			MinecraftClient minecraftClient = MinecraftClient.getInstance();
			PlayerEntity playerEntity = minecraftClient.player;
			ClientConfig clientConfig = HealthRegenerationOverhaulClient.CLIENT_CONFIG;

            if (playerEntity != null
                    && !minecraftClient.options.hudHidden
                    && (clientConfig.enable_alternative_health_bar || clientConfig.enable_alternative_absorption_bar)) {
				double health = playerEntity.getHealth();
				double maxHealth = playerEntity.getMaxHealth();
				double unreservedHealth = MathHelper.ceil(((HealthRegeneratingEntity) playerEntity).healthregenerationoverhaul$getUnreservedHealth());
                double absorption = playerEntity.getAbsorptionAmount();
                if (prevAbsorptionLevel < 0.0) {
                    prevAbsorptionLevel = absorption;
                }
                int regeneratedAbsorption = (int)Math.ceil(Math.max(0.0, absorption - prevAbsorptionLevel));
                prevAbsorptionLevel = absorption;

				if (!playerEntity.isCreative() && maxHealth > 0) {

					MutablePair<Integer, Integer> originPos = ResourceBarAPIClient.getOriginPos(matrixStack, clientConfig.origin);

					if (clientConfig.health_bar_display == ResourceBarAPI.ResourceBarDisplay.ICON && (health < maxHealth || clientConfig.show_full_health_bar)) {

						Identifier containerId;
						Identifier fullId;
						Identifier halfId;

						if (playerEntity.getWorld().getLevelProperties().isHardcore()) {
							containerId = ICON_HEALTH_CONTAINER_HARDCORE;
							if (playerEntity.hasStatusEffect(StatusEffects.POISON)) {
								fullId = ICON_HEALTH_FULL_POISONED_HARDCORE;
								halfId = ICON_HEALTH_HALF_POISONED_HARDCORE;
							} else if (playerEntity.hasStatusEffect(StatusEffects.WITHER)) {
								fullId = ICON_HEALTH_FULL_WITHERED_HARDCORE;
								halfId = ICON_HEALTH_HALF_WITHERED_HARDCORE;
							} else if (playerEntity.isFrozen()) {
								fullId = ICON_HEALTH_FULL_FROZEN_HARDCORE;
								halfId = ICON_HEALTH_HALF_FROZEN_HARDCORE;
							} else {
								fullId = ICON_HEALTH_FULL_HARDCORE;
								halfId = ICON_HEALTH_HALF_HARDCORE;
							}
						} else {
							containerId = ICON_HEALTH_CONTAINER;
							if (playerEntity.hasStatusEffect(StatusEffects.POISON)) {
								fullId = ICON_HEALTH_FULL_POISONED;
								halfId = ICON_HEALTH_HALF_POISONED;
							} else if (playerEntity.hasStatusEffect(StatusEffects.WITHER)) {
								fullId = ICON_HEALTH_FULL_WITHERED;
								halfId = ICON_HEALTH_HALF_WITHERED;
							} else if (playerEntity.isFrozen()) {
								fullId = ICON_HEALTH_FULL_FROZEN;
								halfId = ICON_HEALTH_HALF_FROZEN;
							} else {
								fullId = ICON_HEALTH_FULL;
								halfId = ICON_HEALTH_HALF;
							}
						}

						ResourceBarAPIClient.drawIconResourceBar(
								minecraftClient,
								matrixStack,
								RESOURCE_BAR_IDENTIFIER_STRING,
								health,
								maxHealth,
								containerId,
								fullId,
								halfId,
								new ArrayList<>(),
								new ArrayList<>(),// TODO reserved health, absorption
								originPos.getLeft(),
								originPos.getRight(),
								clientConfig.iconBarSettings.offset_x.get(),
								clientConfig.iconBarSettings.offset_y.get(),
								clientConfig.fill_direction,
								clientConfig.iconBarSettings.reverse_stack_direction.get(),
								clientConfig.iconBarSettings.max_icon_amount_per_bar.get()
						);
					} else if (clientConfig.health_bar_display == ResourceBarAPI.ResourceBarDisplay.SMOOTH && (health < maxHealth || clientConfig.show_full_health_bar)) {
					ResourceBarAPIClient.drawSmoothResourceBar(
							minecraftClient,
							matrixStack,
							RESOURCE_BAR_IDENTIFIER_STRING,
							new double[]{
									-1,
									-1,
									0,
									-91,
									-45,
									5,
									182,
									5,
									182,
									5,
									182,
									5,
									5,
									0,
									0
							},
							new Identifier[]{
									Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_background.png"),
									Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_progress_decrease_animation.png"),
									Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_progress_increase_animation.png"),
									Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_progress_increase_value.png"),
									Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_progress.png"),
									Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_reserved.png"),
									Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_overlay.png"),
									null
							},
							health,
							maxHealth,
							MathHelper.ceil(((HealthRegeneratingEntity) playerEntity).healthregenerationoverhaul$getRegeneratedHealth()),
							unreservedHealth,
							originPos.getLeft(),
							originPos.getRight(),
							clientConfig.smoothBarSettings.positionSettings.offsets_x,
							clientConfig.smoothBarSettings.positionSettings.offsets_y,
							0,
							0,
							clientConfig.fill_direction,
							clientConfig.smoothBarSettings.textureSettings.backgroundTextureSettings.texture_heights,
							clientConfig.smoothBarSettings.textureSettings.backgroundTextureSettings.texture_widths,
							clientConfig.smoothBarSettings.textureSettings.backgroundTextureSettings.texture_ids,
							clientConfig.smoothBarSettings.textureSettings.progressTextureSettings.offset_x,
							clientConfig.smoothBarSettings.textureSettings.progressTextureSettings.offset_y,
							clientConfig.smoothBarSettings.textureSettings.progressTextureSettings.texture_heights,
							clientConfig.smoothBarSettings.textureSettings.progressTextureSettings.texture_widths,
							clientConfig.smoothBarSettings.textureSettings.progressTextureSettings.progress_decrease_animation_texture_ids,
							clientConfig.smoothBarSettings.textureSettings.progressTextureSettings.progress_increase_animation_texture_ids,
							clientConfig.smoothBarSettings.textureSettings.progressTextureSettings.progress_increase_value_texture_ids,
							clientConfig.smoothBarSettings.textureSettings.progressTextureSettings.progress_texture_ids,
							clientConfig.smoothBarSettings.textureSettings.reservedTextureSettings.offset_x,
							clientConfig.smoothBarSettings.textureSettings.reservedTextureSettings.offset_y,
							clientConfig.smoothBarSettings.textureSettings.reservedTextureSettings.texture_heights,
							clientConfig.smoothBarSettings.textureSettings.reservedTextureSettings.texture_widths,
							clientConfig.smoothBarSettings.textureSettings.reservedTextureSettings.texture_ids,
							clientConfig.smoothBarSettings.show_current_value_overlay,
							clientConfig.smoothBarSettings.textureSettings.overlayTextureSettings.offset_x,
							clientConfig.smoothBarSettings.textureSettings.overlayTextureSettings.offset_y,
							clientConfig.smoothBarSettings.textureSettings.overlayTextureSettings.texture_heights,
							clientConfig.smoothBarSettings.textureSettings.overlayTextureSettings.texture_widths,
							clientConfig.smoothBarSettings.textureSettings.overlayTextureSettings.texture_ids,
							clientConfig.smoothBarSettings.show_icon,
							clientConfig.smoothBarSettings.iconTextureSettings.offset_x,
							clientConfig.smoothBarSettings.iconTextureSettings.offset_y,
							clientConfig.smoothBarSettings.iconTextureSettings.texture_heights,
							clientConfig.smoothBarSettings.iconTextureSettings.texture_widths,
							clientConfig.smoothBarSettings.iconTextureSettings.texture_ids,
							clientConfig.smoothBarSettings.enable_smooth_animation,
							clientConfig.smoothBarSettings.animationSettings.animation_interval,
							clientConfig.smoothBarSettings.animationSettings.max_value_change_is_animated
					);
					}
					if (clientConfig.numberSettings.show_number && (health < maxHealth || clientConfig.show_full_health_bar)) {
						ResourceBarAPIClient.drawResourceNumber(
								minecraftClient,
								minecraftClient.textRenderer,
								matrixStack,
								RESOURCE_BAR_IDENTIFIER_STRING,
								health,
								maxHealth,
								unreservedHealth,
								originPos.getLeft(),
								originPos.getRight(),
								clientConfig.numberSettings.show_max_value,
								clientConfig.numberSettings.offset_x,
								clientConfig.numberSettings.offset_y,
								clientConfig.numberSettings.color.toInt()
						);
					}
                    if (clientConfig.enable_alternative_absorption_bar
                            && (absorption > 0.0 || clientConfig.show_full_absorption_bar)) {
                        double absorptionMaxForBar = maxHealth;

                        if (prevAbsorptionLevel < 0.0) {
                            prevAbsorptionLevel = absorption;
                        }
                        int deltaAbsorption = (int) Math.ceil(Math.max(0.0, absorption - prevAbsorptionLevel));
                        prevAbsorptionLevel = absorption;

                        if (clientConfig.absorption_bar_display == ResourceBarAPI.ResourceBarDisplay.ICON
                                && (absorption < absorptionMaxForBar || clientConfig.show_full_absorption_bar)) {

                            Identifier containerAbs;
                            Identifier fullAbs;
                            Identifier halfAbs;

                            if (playerEntity.getWorld().getLevelProperties().isHardcore()) {
                                containerAbs = ICON_ABSORPTION_CONTAINER_HARDCORE;
                            } else {
                                containerAbs = ICON_ABSORPTION_CONTAINER;
                            }
                            fullAbs = ICON_ABSORPTION_FULL;
                            halfAbs = ICON_ABSORPTION_HALF;

                            ResourceBarAPIClient.drawIconResourceBar(
                                    minecraftClient,
                                    matrixStack,
                                    ABSORPTION_BAR_IDENTIFIER_STRING,
                                    absorption,
                                    absorptionMaxForBar,
                                    containerAbs,
                                    fullAbs,
                                    halfAbs,
                                    new ArrayList<>(),
                                    new ArrayList<>(), // pas de "reserved" sur absorption
                                    originPos.getLeft(),
                                    originPos.getRight(),
                                    clientConfig.absorptionIconSettings.offset_x.get(),
                                    clientConfig.absorptionIconSettings.offset_y.get(),
                                    clientConfig.absorption_fill_direction,
                                    clientConfig.absorptionIconSettings.reverse_stack_direction.get(),
                                    clientConfig.absorptionIconSettings.max_icon_amount_per_bar.get()
                            );
                        }
                        else if (clientConfig.absorption_bar_display == ResourceBarAPI.ResourceBarDisplay.SMOOTH
                                && (absorption < absorptionMaxForBar || clientConfig.show_full_absorption_bar)) {

                            ResourceBarAPIClient.drawSmoothResourceBar(
                                    minecraftClient,
                                    matrixStack,
                                    ABSORPTION_BAR_IDENTIFIER_STRING,
                                    new double[]{
                                            -1, -1, 0, -91, -45, 5, 182, 5, 182, 5, 182, 5, 5, 0, 0
                                    },
                                    new Identifier[]{
                                            Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_background.png"),
                                            Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_progress_decrease_animation.png"),
                                            Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_progress_increase_animation.png"),
                                            Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_progress_increase_value.png"),
                                            Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_progress.png"),
                                            Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_reserved.png"),
                                            Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_overlay.png"),
                                            null
                                    },
                                    absorption,                // current
                                    absorptionMaxForBar,       // max (indexé sur la vie)
                                    regeneratedAbsorption,     // <<< clé: delta positif pour l’animation d’augmentation
                                    absorption,                // unreserved (pas de "reserved" réel pour l’absorption)
                                    originPos.getLeft(),
                                    originPos.getRight(),
                                    clientConfig.absorptionSmoothSettings.positionSettings.offsets_x,
                                    clientConfig.absorptionSmoothSettings.positionSettings.offsets_y,
                                    0,
                                    0,
                                    clientConfig.absorption_fill_direction,
                                    clientConfig.absorptionSmoothSettings.textureSettings.backgroundTextureSettings.texture_heights,
                                    clientConfig.absorptionSmoothSettings.textureSettings.backgroundTextureSettings.texture_widths,
                                    clientConfig.absorptionSmoothSettings.textureSettings.backgroundTextureSettings.texture_ids,
                                    clientConfig.absorptionSmoothSettings.textureSettings.progressTextureSettings.offset_x,
                                    clientConfig.absorptionSmoothSettings.textureSettings.progressTextureSettings.offset_y,
                                    clientConfig.absorptionSmoothSettings.textureSettings.progressTextureSettings.texture_heights,
                                    clientConfig.absorptionSmoothSettings.textureSettings.progressTextureSettings.texture_widths,
                                    clientConfig.absorptionSmoothSettings.textureSettings.progressTextureSettings.progress_decrease_animation_texture_ids,
                                    clientConfig.absorptionSmoothSettings.textureSettings.progressTextureSettings.progress_increase_animation_texture_ids,
                                    clientConfig.absorptionSmoothSettings.textureSettings.progressTextureSettings.progress_increase_value_texture_ids,
                                    clientConfig.absorptionSmoothSettings.textureSettings.progressTextureSettings.progress_texture_ids,
                                    clientConfig.absorptionSmoothSettings.textureSettings.reservedTextureSettings.offset_x,
                                    clientConfig.absorptionSmoothSettings.textureSettings.reservedTextureSettings.offset_y,
                                    clientConfig.absorptionSmoothSettings.textureSettings.reservedTextureSettings.texture_heights,
                                    clientConfig.absorptionSmoothSettings.textureSettings.reservedTextureSettings.texture_widths,
                                    clientConfig.absorptionSmoothSettings.textureSettings.reservedTextureSettings.texture_ids,
                                    clientConfig.absorptionSmoothSettings.show_current_value_overlay,
                                    clientConfig.absorptionSmoothSettings.textureSettings.overlayTextureSettings.offset_x,
                                    clientConfig.absorptionSmoothSettings.textureSettings.overlayTextureSettings.offset_y,
                                    clientConfig.absorptionSmoothSettings.textureSettings.overlayTextureSettings.texture_heights,
                                    clientConfig.absorptionSmoothSettings.textureSettings.overlayTextureSettings.texture_widths,
                                    clientConfig.absorptionSmoothSettings.textureSettings.overlayTextureSettings.texture_ids,
                                    clientConfig.absorptionSmoothSettings.show_icon,
                                    clientConfig.absorptionSmoothSettings.iconTextureSettings.offset_x,
                                    clientConfig.absorptionSmoothSettings.iconTextureSettings.offset_y,
                                    clientConfig.absorptionSmoothSettings.iconTextureSettings.texture_heights,
                                    clientConfig.absorptionSmoothSettings.iconTextureSettings.texture_widths,
                                    clientConfig.absorptionSmoothSettings.iconTextureSettings.texture_ids,
                                    clientConfig.absorptionSmoothSettings.enable_smooth_animation,
                                    clientConfig.absorptionSmoothSettings.animationSettings.animation_interval,
                                    clientConfig.absorptionSmoothSettings.animationSettings.max_value_change_is_animated
                            );
                        }

                        if (clientConfig.absorptionNumberSettings.show_number
                                && (absorption < absorptionMaxForBar || clientConfig.show_full_absorption_bar)) {

                            ResourceBarAPIClient.drawResourceNumber(
                                    minecraftClient,
                                    minecraftClient.textRenderer,
                                    matrixStack,
                                    ABSORPTION_BAR_IDENTIFIER_STRING,
                                    absorption,
                                    absorptionMaxForBar,
                                    absorption, // non-réservé (tout absorption)
                                    originPos.getLeft(),
                                    originPos.getRight(),
                                    clientConfig.absorptionNumberSettings.show_max_value,
                                    clientConfig.absorptionNumberSettings.offset_x,
                                    clientConfig.absorptionNumberSettings.offset_y,
                                    clientConfig.absorptionNumberSettings.color.toInt()
                            );
                        }
                    }
				}
			}
		});
		ConfigApi.event().onUpdateClient((identifier, config) -> {
			if (identifier.equals(Identifier.of(HealthRegenerationOverhaul.MOD_ID, "client"))) {
				ResourceBarAPIClient.clearCache(
						RESOURCE_BAR_IDENTIFIER_STRING,
						new double[]{
								-1,
								-1,
								0,
								-91,
								-45,
								5,
								182,
								5,
								182,
								5,
								182,
								5,
								5,
								0,
								0
						},
						new Identifier[]{
								Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_background.png"),
								Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_progress_decrease_animation.png"),
								Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_progress_increase_animation.png"),
								Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_progress_increase_value.png"),
								Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_progress.png"),
								Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_reserved.png"),
								Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_overlay.png"),
								null
						}
				);
                ResourceBarAPIClient.clearCache(
                        ABSORPTION_BAR_IDENTIFIER_STRING,
                        new double[]{
                                -1,
                                -1,
                                0,
                                -91,
                                -45,
                                5,
                                182,
                                5,
                                182,
                                5,
                                182,
                                5,
                                5,
                                0,
                                0
                        },
                        new Identifier[]{
                                Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_background.png"),
                                Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_progress_decrease_animation.png"),
                                Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_progress_increase_animation.png"),
                                Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_progress_increase_value.png"),
                                Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_progress.png"),
                                Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_reserved.png"),
                                Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_overlay.png"),
                                null
                        }
                );
			}
		});
	}
}
