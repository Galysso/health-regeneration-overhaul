package com.github.theredbrain.healthregenerationoverhaul.config;

import com.github.theredbrain.healthregenerationoverhaul.HealthRegenerationOverhaul;
import com.github.theredbrain.resourcebarapi.ResourceBarAPI;
import me.fzzyhmstrs.fzzy_config.annotations.ConvertFrom;
import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedMap;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedColor;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.util.Identifier;

import java.util.HashMap;

@ConvertFrom(fileName = "client.json5", folder = "healthregenerationoverhaul")
public class ClientConfig extends Config {

	public ClientConfig() {
		super(HealthRegenerationOverhaul.identifier("client"));
	}

	public boolean enable_alternative_health_bar = true;
	public ResourceBarAPI.ResourceBarDisplay health_bar_display = ResourceBarAPI.ResourceBarDisplay.ICON;
	public boolean show_full_health_bar = true;

	public ResourceBarAPI.ResourceBarOrigin origin = ResourceBarAPI.ResourceBarOrigin.BOTTOM_MIDDLE;

	public ResourceBarAPI.ResourceBarFillDirection fill_direction = ResourceBarAPI.ResourceBarFillDirection.LEFT_TO_RIGHT;

	public IconBarSettings iconBarSettings = new IconBarSettings();

	public static class IconBarSettings extends ConfigSection {
		public ValidatedInt offset_x = new ValidatedInt(91);
		public ValidatedInt offset_y = new ValidatedInt(-49);
		public ValidatedInt max_icon_amount_per_bar = new ValidatedInt(10);
		public ValidatedBoolean reverse_stack_direction = new ValidatedBoolean(true);
	}

	public SmoothBarSettings smoothBarSettings = new SmoothBarSettings();

	public static class SmoothBarSettings extends ConfigSection {

	public PositionSettings positionSettings = new PositionSettings();

	public static class PositionSettings extends ConfigSection {
		public ValidatedMap<Integer, Integer> offsets_x = new ValidatedMap<>(new HashMap<>() {{
			put(0, -91);
		}}, new ValidatedInt(), new ValidatedInt());
		public ValidatedMap<Integer, Integer> offsets_y = new ValidatedMap<>(new HashMap<>() {{
			put(0, -39);
		}}, new ValidatedInt(), new ValidatedInt());
	}

	public boolean show_current_value_overlay = false;

	public TextureSettings textureSettings = new TextureSettings();

	public static class TextureSettings extends ConfigSection {
		public BackgroundTextureSettings backgroundTextureSettings = new BackgroundTextureSettings();

		@Translation(prefix = "healthregenerationoverhaul.client.texture_layer")
		public static class BackgroundTextureSettings extends ConfigSection {

			public ValidatedMap<Integer, Integer> texture_heights = new ValidatedMap<>(new HashMap<>() {{
				put(0, 5);
			}}, new ValidatedInt(), new ValidatedInt());
			public ValidatedMap<Integer, Integer> texture_widths = new ValidatedMap<>(new HashMap<>() {{
				put(0, 182);
			}}, new ValidatedInt(), new ValidatedInt());

			public ValidatedMap<Integer, Identifier> texture_ids = new ValidatedMap<>(new HashMap<>() {{
				put(0, Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_background.png"));
			}}, new ValidatedInt(), new ValidatedIdentifier());

		}

		public ProgressTextureSettings progressTextureSettings = new ProgressTextureSettings();

		@Translation(prefix = "healthregenerationoverhaul.client.texture_layer")
		public static class ProgressTextureSettings extends ConfigSection {
			public int offset_x = 0;
			public int offset_y = 0;

			public ValidatedMap<Integer, Integer> texture_heights = new ValidatedMap<>(new HashMap<>() {{
				put(0, 5);
			}}, new ValidatedInt(), new ValidatedInt());
			public ValidatedMap<Integer, Integer> texture_widths = new ValidatedMap<>(new HashMap<>() {{
				put(0, 182);
			}}, new ValidatedInt(), new ValidatedInt());

			@Translation(prefix = "healthregenerationoverhaul.client.texture_layer", negate = true)
			public ValidatedMap<Integer, Identifier> progress_decrease_animation_texture_ids = new ValidatedMap<>(new HashMap<>() {{
				put(0, Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_progress_decrease_animation.png"));
			}}, new ValidatedInt(), new ValidatedIdentifier());

			@Translation(prefix = "healthregenerationoverhaul.client.texture_layer", negate = true)
			public ValidatedMap<Integer, Identifier> progress_increase_animation_texture_ids = new ValidatedMap<>(new HashMap<>() {{
				put(0, Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_progress_increase_animation.png"));
			}}, new ValidatedInt(), new ValidatedIdentifier());

			@Translation(prefix = "healthregenerationoverhaul.client.texture_layer", negate = true)
			public ValidatedMap<Integer, Identifier> progress_increase_value_texture_ids = new ValidatedMap<>(new HashMap<>() {{
				put(0, Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_progress_increase_value.png"));
			}}, new ValidatedInt(), new ValidatedIdentifier());

			@Translation(prefix = "healthregenerationoverhaul.client.texture_layer", negate = true)
			public ValidatedMap<Integer, Identifier> progress_texture_ids = new ValidatedMap<>(new HashMap<>() {{
				put(0, Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_progress.png"));
			}}, new ValidatedInt(), new ValidatedIdentifier());

		}

		public ReservedTextureSettings reservedTextureSettings = new ReservedTextureSettings();

		@Translation(prefix = "healthregenerationoverhaul.client.texture_layer")
		public static class ReservedTextureSettings extends ConfigSection {
			public int offset_x = 0;
			public int offset_y = 0;

			public ValidatedMap<Integer, Integer> texture_heights = new ValidatedMap<>(new HashMap<>() {{
				put(0, 5);
			}}, new ValidatedInt(), new ValidatedInt());
			public ValidatedMap<Integer, Integer> texture_widths = new ValidatedMap<>(new HashMap<>() {{
				put(0, 182);
			}}, new ValidatedInt(), new ValidatedInt());

			public ValidatedMap<Integer, Identifier> texture_ids = new ValidatedMap<>(new HashMap<>() {{
				put(0, Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_reserved.png"));
			}}, new ValidatedInt(), new ValidatedIdentifier());

		}

		public OverlayTextureSettings overlayTextureSettings = new OverlayTextureSettings();

		@Translation(prefix = "healthregenerationoverhaul.client.texture_layer")
		public static class OverlayTextureSettings extends ConfigSection {
			@Translation(prefix = "healthregenerationoverhaul.client.texture_layer", negate = true)
			public int offset_x = -2;
			@Translation(prefix = "healthregenerationoverhaul.client.texture_layer", negate = true)
			public int offset_y = 0;

			public ValidatedMap<Integer, Integer> texture_heights = new ValidatedMap<>(new HashMap<>() {{
				put(0, 5);
			}}, new ValidatedInt(), new ValidatedInt());
			public ValidatedMap<Integer, Integer> texture_widths = new ValidatedMap<>(new HashMap<>() {{
				put(0, 5);
			}}, new ValidatedInt(), new ValidatedInt());

			public ValidatedMap<Integer, Identifier> texture_ids = new ValidatedMap<>(new HashMap<>() {{
				put(0, Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_health_overlay.png"));
			}}, new ValidatedInt(), new ValidatedIdentifier());

		}
	}

	public boolean show_icon = false;

	public IconTextureSettings iconTextureSettings = new IconTextureSettings();

	@Translation(prefix = "healthregenerationoverhaul.client.texture_layer")
	public static class IconTextureSettings extends ConfigSection {
		@Translation(prefix = "healthregenerationoverhaul.client.texture_layer", negate = true)
		public int offset_x = 0;
		@Translation(prefix = "healthregenerationoverhaul.client.texture_layer", negate = true)
		public int offset_y = 0;

		public ValidatedMap<Integer, Integer> texture_heights = new ValidatedMap<>(new HashMap<>() {{
			put(0, 0);
		}}, new ValidatedInt(), new ValidatedInt());
		public ValidatedMap<Integer, Integer> texture_widths = new ValidatedMap<>(new HashMap<>() {{
			put(0, 0);
		}}, new ValidatedInt(), new ValidatedInt());

		public ValidatedMap<Integer, Identifier> texture_ids = new ValidatedMap<>(new HashMap<>() {
		}, new ValidatedInt(), new ValidatedIdentifier());
	}

	public boolean enable_smooth_animation = true;

	public AnimationsSettings animationSettings = new AnimationsSettings();

	public static class AnimationsSettings extends ConfigSection {
		public int animation_interval = 1;
		public boolean max_value_change_is_animated = false;
	}
}

	public NumberSettings numberSettings = new NumberSettings();

	public static class NumberSettings extends ConfigSection {
		public boolean show_number = false;
		public boolean show_max_value = false;
		public boolean show_when_health_full = true;
		public int offset_x = 0;
		public int offset_y = -40;
		public ValidatedColor color = new ValidatedColor(150, 150, 150);
	}

    // Active/désactive la barre d’absorption alternative
    public boolean enable_alternative_absorption_bar = true;

    // Affichage de la barre d’absorption (ICON ou SMOOTH), indépendant de la santé
    public ResourceBarAPI.ResourceBarDisplay absorption_bar_display = ResourceBarAPI.ResourceBarDisplay.SMOOTH;

    // Afficher la barre d’absorption même lorsqu’elle est “pleine”
    public boolean show_full_absorption_bar = true;

    // Direction de remplissage indépendante
    public ResourceBarAPI.ResourceBarFillDirection absorption_fill_direction = ResourceBarAPI.ResourceBarFillDirection.LEFT_TO_RIGHT;

    // Section icônes pour l’absorption (offsets par défaut: juste en dessous de la barre de coeurs icônes)
    public IconBarSettings absorptionIconSettings = new IconBarSettings() {{
        offset_x = new ValidatedInt(91);
        offset_y = new ValidatedInt(-39); // 10px sous la barre de santé icônes
        max_icon_amount_per_bar = new ValidatedInt(10);
        reverse_stack_direction = new ValidatedBoolean(true);
    }};

    // Section lisse pour l’absorption
    public SmoothBarSettings absorptionSmoothSettings = new SmoothBarSettings() {{
        // Position: légèrement décalée par rapport à la barre de santé lisse
        positionSettings = new PositionSettings() {{
            offsets_x = new ValidatedMap<>(new HashMap<>() {{
                put(0, -91);
            }}, new ValidatedInt(), new ValidatedInt());
            offsets_y = new ValidatedMap<>(new HashMap<>() {{
                put(0, -33); // un peu plus haut/bas que la santé (selon ton HUD)
            }}, new ValidatedInt(), new ValidatedInt());
        }};

        // On peut afficher un overlay si désiré (par défaut false)
        show_current_value_overlay = false;

        // Textures dédiées à l’absorption (à fournir dans ton pack)
        textureSettings = new TextureSettings() {{
            backgroundTextureSettings = new BackgroundTextureSettings() {{
                texture_heights = new ValidatedMap<>(new HashMap<>() {{
                    put(0, 5);
                }}, new ValidatedInt(), new ValidatedInt());
                texture_widths = new ValidatedMap<>(new HashMap<>() {{
                    put(0, 182);
                }}, new ValidatedInt(), new ValidatedInt());
                texture_ids = new ValidatedMap<>(new HashMap<>() {{
                    put(0, Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_background.png"));
                }}, new ValidatedInt(), new ValidatedIdentifier());
            }};
            progressTextureSettings = new ProgressTextureSettings() {{
                offset_x = 0;
                offset_y = 0;
                texture_heights = new ValidatedMap<>(new HashMap<>() {{
                    put(0, 5);
                }}, new ValidatedInt(), new ValidatedInt());
                texture_widths = new ValidatedMap<>(new HashMap<>() {{
                    put(0, 182);
                }}, new ValidatedInt(), new ValidatedInt());
                progress_decrease_animation_texture_ids = new ValidatedMap<>(new HashMap<>() {{
                    put(0, Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_progress_decrease_animation.png"));
                }}, new ValidatedInt(), new ValidatedIdentifier());
                progress_increase_animation_texture_ids = new ValidatedMap<>(new HashMap<>() {{
                    put(0, Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_progress_increase_animation.png"));
                }}, new ValidatedInt(), new ValidatedIdentifier());
                progress_increase_value_texture_ids = new ValidatedMap<>(new HashMap<>() {{
                    put(0, Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_progress_increase_value.png"));
                }}, new ValidatedInt(), new ValidatedIdentifier());
                progress_texture_ids = new ValidatedMap<>(new HashMap<>() {{
                    put(0, Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_progress.png"));
                }}, new ValidatedInt(), new ValidatedIdentifier());
            }};
            reservedTextureSettings = new ReservedTextureSettings() {{
                offset_x = 0;
                offset_y = 0;
                texture_heights = new ValidatedMap<>(new HashMap<>() {{
                    put(0, 5);
                }}, new ValidatedInt(), new ValidatedInt());
                texture_widths = new ValidatedMap<>(new HashMap<>() {{
                    put(0, 182);
                }}, new ValidatedInt(), new ValidatedInt());
                texture_ids = new ValidatedMap<>(new HashMap<>() {{
                    put(0, Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_reserved.png"));
                }}, new ValidatedInt(), new ValidatedIdentifier());
            }};
            overlayTextureSettings = new OverlayTextureSettings() {{
                offset_x = -2;
                offset_y = 0;
                texture_heights = new ValidatedMap<>(new HashMap<>() {{
                    put(0, 5);
                }}, new ValidatedInt(), new ValidatedInt());
                texture_widths = new ValidatedMap<>(new HashMap<>() {{
                    put(0, 5);
                }}, new ValidatedInt(), new ValidatedInt());
                texture_ids = new ValidatedMap<>(new HashMap<>() {{
                    put(0, Identifier.of("healthregenerationoverhaul", "textures/gui/sprites/hud/horizontal_absorption_overlay.png"));
                }}, new ValidatedInt(), new ValidatedIdentifier());
            }};
        }};

        show_icon = false; // par défaut pas d'icône par-dessus la barre lisse

        iconTextureSettings = new IconTextureSettings() {{
            offset_x = 0;
            offset_y = 0;
            texture_heights = new ValidatedMap<>(new HashMap<>() {{
                put(0, 0);
            }}, new ValidatedInt(), new ValidatedInt());
            texture_widths = new ValidatedMap<>(new HashMap<>() {{
                put(0, 0);
            }}, new ValidatedInt(), new ValidatedInt());
            texture_ids = new ValidatedMap<>(new HashMap<>() {{
                // Laisse vide par défaut
            }}, new ValidatedInt(), new ValidatedIdentifier());
        }};

        enable_smooth_animation = true;
        animationSettings = new AnimationsSettings() {{
            animation_interval = 1;
            max_value_change_is_animated = false;
        }};
    }};

    // Nombre pour l’absorption (par défaut: jaune doux)
    public NumberSettings absorptionNumberSettings = new NumberSettings() {{
        show_number = false;
        show_max_value = false;
        // champ existant "show_when_health_full" n’est pas utilisé par l’absorption
        offset_x = 0;
        offset_y = -34;
        color = new ValidatedColor(255, 200, 0);
    }};
}