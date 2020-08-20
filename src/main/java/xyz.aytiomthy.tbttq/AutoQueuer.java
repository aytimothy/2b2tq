package xyz.aytiomthy.tbttq;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;

import java.time.LocalDateTime;

public class AutoQueuer {
    public static void Check(GuiScreen returnScreen) {
        if (!AutoQueueConfig.enabled)
            return;

        if (LocalDateTime.now().isAfter(AutoQueueConfig.timestamp)) {
            AutoQueueConfig.enabled = false;
            AutoQueueConfig.SaveToDisk();

            Minecraft minecraft = Minecraft.getMinecraft();
            ServerData serverEntry = new ServerData("Auto-Connect Server", AutoQueueConfig.server, false);
            minecraft.setServerData(serverEntry);
            net.minecraftforge.fml.client.FMLClientHandler.instance().connectToServer(returnScreen, serverEntry);

            // AutoQueueAFKCheck.enabled = AutoQueueConfig.enableAFKTimeout;
        }
    }
}
