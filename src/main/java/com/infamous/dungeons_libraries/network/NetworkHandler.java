package com.infamous.dungeons_libraries.network;

import com.infamous.dungeons_libraries.DungeonsLibraries;
import com.infamous.dungeons_libraries.integration.curios.client.message.CuriosArtifactStartMessage;
import com.infamous.dungeons_libraries.integration.curios.client.message.CuriosArtifactStopMessage;
import com.infamous.dungeons_libraries.network.gearconfig.ArmorGearConfigSyncPacket;
import com.infamous.dungeons_libraries.network.gearconfig.BowGearConfigSyncPacket;
import com.infamous.dungeons_libraries.network.gearconfig.CrossbowGearConfigSyncPacket;
import com.infamous.dungeons_libraries.network.gearconfig.MeleeGearConfigSyncPacket;
import com.infamous.dungeons_libraries.network.materials.ArmorMaterialSyncPacket;
import com.infamous.dungeons_libraries.network.materials.WeaponMaterialSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
            new ResourceLocation(DungeonsLibraries.MODID, "network"))
            .clientAcceptedVersions("1"::equals)
            .serverAcceptedVersions("1"::equals)
            .networkProtocolVersion(() -> "1")
            .simpleChannel();

    protected static int PACKET_COUNTER = 0;

    public NetworkHandler() {
    }

    public static void init() {
        INSTANCE.messageBuilder(UpdateSoulsMessage.class, incrementAndGetPacketCounter())
                .encoder(UpdateSoulsMessage::encode).decoder(UpdateSoulsMessage::decode)
                .consumer(UpdateSoulsMessage.UpdateSoulsHandler::handle)
                .add();
        INSTANCE.messageBuilder(ArmorGearConfigSyncPacket.class, incrementAndGetPacketCounter())
                .encoder(ArmorGearConfigSyncPacket::encode).decoder(ArmorGearConfigSyncPacket::decode)
                .consumer(ArmorGearConfigSyncPacket::onPacketReceived)
                .add();
        INSTANCE.messageBuilder(MeleeGearConfigSyncPacket.class, incrementAndGetPacketCounter())
                .encoder(MeleeGearConfigSyncPacket::encode).decoder(MeleeGearConfigSyncPacket::decode)
                .consumer(MeleeGearConfigSyncPacket::onPacketReceived)
                .add();
        INSTANCE.messageBuilder(BowGearConfigSyncPacket.class, incrementAndGetPacketCounter())
                .encoder(BowGearConfigSyncPacket::encode).decoder(BowGearConfigSyncPacket::decode)
                .consumer(BowGearConfigSyncPacket::onPacketReceived)
                .add();
        INSTANCE.messageBuilder(CrossbowGearConfigSyncPacket.class, incrementAndGetPacketCounter())
                .encoder(CrossbowGearConfigSyncPacket::encode).decoder(CrossbowGearConfigSyncPacket::decode)
                .consumer(CrossbowGearConfigSyncPacket::onPacketReceived)
                .add();
        INSTANCE.messageBuilder(ArmorMaterialSyncPacket.class, incrementAndGetPacketCounter())
                .encoder(ArmorMaterialSyncPacket::encode).decoder(ArmorMaterialSyncPacket::decode)
                .consumer(ArmorMaterialSyncPacket::onPacketReceived)
                .add();
        INSTANCE.messageBuilder(WeaponMaterialSyncPacket.class, incrementAndGetPacketCounter())
                .encoder(WeaponMaterialSyncPacket::encode).decoder(WeaponMaterialSyncPacket::decode)
                .consumer(WeaponMaterialSyncPacket::onPacketReceived)
                .add();
        INSTANCE.messageBuilder(CuriosArtifactStartMessage.class, incrementAndGetPacketCounter())
                .encoder(CuriosArtifactStartMessage::encode).decoder(CuriosArtifactStartMessage::decode)
                .consumer(CuriosArtifactStartMessage.CuriosArtifactHandler::handle)
                .add();
        INSTANCE.messageBuilder(CuriosArtifactStopMessage.class, incrementAndGetPacketCounter())
                .encoder(CuriosArtifactStopMessage::encode).decoder(CuriosArtifactStopMessage::decode)
                .consumer(CuriosArtifactStopMessage.CuriosArtifactHandler::handle)
                .add();
    }

    public static int incrementAndGetPacketCounter() {
        return PACKET_COUNTER++;
    }
}
