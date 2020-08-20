package xyz.aytiomthy.tbttq;

import akka.io.UdpConnected;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.time.LocalDateTime;

public class AutoQueueAFKCheckScreen extends GuiScreen {
    public LocalDateTime timeout;
    public GuiButton notAFKButton;

    public AutoQueueAFKCheckScreen (LocalDateTime timeout) {
        this.timeout = timeout;
    }

    @Override
    public void updateScreen() {
        if(LocalDateTime.now().isAfter(timeout)) {
            // todo: Disconnect from Minecraft Server
            Minecraft.getMinecraft().getConnection().getNetworkManager();
        }
    }
}
