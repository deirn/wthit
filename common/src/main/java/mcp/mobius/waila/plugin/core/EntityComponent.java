package mcp.mobius.waila.plugin.core;

import java.util.List;

import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.IDrawableText;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITaggableList;
import mcp.mobius.waila.config.WailaConfig;
import mcp.mobius.waila.util.ModIdentification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum EntityComponent implements IEntityComponentProvider {

    INSTANCE;

    @Override
    public void appendHead(List<Text> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        Entity entity = accessor.getEntity();
        WailaConfig.ConfigFormatting formatting = Waila.config.get().getFormatting();
        ((ITaggableList<Identifier, Text>) tooltip).setTag(BlockComponent.OBJECT_NAME_TAG, new LiteralText(String.format(formatting.getEntityName(), entity.getDisplayName().getString())));
        if (config.get(WailaCore.CONFIG_SHOW_REGISTRY))
            ((ITaggableList<Identifier, Text>) tooltip).setTag(BlockComponent.REGISTRY_NAME_TAG, new LiteralText(String.format(formatting.getRegistryName(), Registry.ENTITY_TYPE.getId(entity.getType()))));
    }

    @Override
    public void appendBody(List<Text> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (config.get(WailaCore.CONFIG_SHOW_ENTITY_HEALTH) && accessor.getEntity() instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) accessor.getEntity();
            float health = living.getHealth();
            float maxHealth = living.getMaxHealth();

            if (living.getMaxHealth() > Waila.config.get().getGeneral().getMaxHealthForRender())
                tooltip.add(new TranslatableText("tooltip.waila.health", String.format("%.2f", health), String.format("%.2f", maxHealth)));
            else {
                NbtCompound healthData = new NbtCompound();
                healthData.putFloat("health", health / 2.0F);
                healthData.putFloat("max", maxHealth / 2.0F);
                tooltip.add(IDrawableText.of(WailaCore.RENDER_ENTITY_HEALTH, healthData));
            }
        }
    }

    @Override
    public void appendTail(List<Text> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (config.get(WailaCore.CONFIG_SHOW_MOD_NAME))
            ((ITaggableList<Identifier, Text>) tooltip).setTag(BlockComponent.MOD_NAME_TAG, new LiteralText(String.format(Waila.config.get().getFormatting().getModName(), ModIdentification.getModInfo(accessor.getEntity()).getName())));
    }

}