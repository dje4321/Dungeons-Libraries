package com.infamous.dungeons_libraries.network;

import com.infamous.dungeons_libraries.capabilities.elite.EliteMob;
import com.infamous.dungeons_libraries.capabilities.elite.EliteMobHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EliteMobMessage {
    private int entityId;
    private boolean isElite;
    private ResourceLocation texture;

    public EliteMobMessage(int entityId, boolean isElite, ResourceLocation texture) {
        this.entityId = entityId;
        this.isElite = isElite;
        this.texture = texture;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeBoolean(isElite);
        buffer.writeResourceLocation(texture);
    }

    public static EliteMobMessage decode(FriendlyByteBuf buffer) {
        int entityId = buffer.readInt();
        boolean isElite = buffer.readBoolean();
        ResourceLocation texture = buffer.readResourceLocation();

        return new EliteMobMessage(entityId, isElite, texture);
    }

    public static boolean onPacketReceived(EliteMobMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                Entity entity = Minecraft.getInstance().player.level.getEntity(message.entityId);
                if (entity instanceof LivingEntity) {
                    EliteMob cap = EliteMobHelper.getEliteMobCapability(entity);
                    cap.setElite(message.isElite);
                    cap.setTexture(message.texture);
                    if(cap.isElite()) {
                        entity.refreshDimensions();
                    }
                }
            });
        }
        return true;
    }
}
