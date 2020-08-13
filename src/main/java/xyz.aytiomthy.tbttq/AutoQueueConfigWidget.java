package xyz.aytiomthy.tbttq;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.time.Duration;
import java.time.LocalDateTime;

/*
    Handles the widget in the multiplayer screen.
 */
@Mod.EventBusSubscriber(modid = "tbttq", value = Side.CLIENT)
public class AutoQueueConfigWidget {
    @SubscribeEvent
    public static void open(InitGuiEvent.Post e) {
        if (e.getGui() instanceof GuiMultiplayer) {
            GuiScreen screen = e.getGui();
            e.getButtonList().add(new GuiButton(19000, screen.width - 200, 5, 100, 20, "Auto-Queue"));
        }
    }

    @SubscribeEvent
    public static void draw(DrawScreenEvent.Post e) {
        if (e.getGui() instanceof GuiMultiplayer) {
            GuiScreen screen = e.getGui();
            FontRenderer fontRenderer = screen.mc.fontRenderer;
            if (AutoQueueConfig.enabled) {
                Duration duration = Duration.between(LocalDateTime.now(), AutoQueueConfig.timestamp);
                String timerText = DurationFormatUtils.formatDuration(duration.toMillis(), "H:mm:ss", true);
                screen.drawString(fontRenderer, timerText, screen.width - 90, 10, 0xFFFFFFFF);
            }
            else {
                screen.drawString(fontRenderer, "Idle...", screen.width - 90, 10, 0xFFFFFFFF);
            }
        }
        if (e.getGui() instanceof GuiMultiplayer || e.getGui() instanceof GuiMainMenu) {
            AutoQueueAFKCheck.enabled = false;
            AutoQueuer.Check(e.getGui());
        }
    }

    @SubscribeEvent
    public static void action(ActionPerformedEvent.Post e) {
        if ((e.getGui() instanceof GuiMultiplayer) && e.getButton().id == 19000) {
            Minecraft.getMinecraft().displayGuiScreen(new AutoQueueConfigScreen(Minecraft.getMinecraft().currentScreen));
        }
    }
}
