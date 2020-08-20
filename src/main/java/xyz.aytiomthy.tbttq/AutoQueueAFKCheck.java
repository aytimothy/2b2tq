package xyz.aytiomthy.tbttq;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import org.apache.logging.log4j.Level;

import java.time.Duration;
import java.time.LocalDateTime;

public class AutoQueueAFKCheck {
    public static Boolean enabled;

    @SubscribeEvent
    public static void onChatReceived(ClientChatReceivedEvent e) {
        if (enabled) {
            switch (AutoQueueConfig.server.toLowerCase()) {
                case "2b2t.org":
                    // System.out.println(e.getMessage().getUnformattedText());
                    String message = e.getMessage().getUnformattedText();
                    if (message == "2b2t is full") {
                        break;
                    }
                    if (message.contains("Position in queue: ")) {
                        String[] splitMessage = message.split(" ");
                        if (splitMessage.length < 4)
                            break;
                        Integer queuePosition = Integer.parseInt(splitMessage[3]);
                        if (queuePosition >= AutoQueueConfig.afkCheckQueuePosition) {
                            enabled = false;
                            LocalDateTime kickTime = LocalDateTime.now().plusSeconds((long)AutoQueueConfig.afkTimeout);
                            Minecraft.getMinecraft().displayGuiScreen(new AutoQueueAFKCheckScreen(kickTime));
                        }
                    }
                    break;
                default:
                    enabled = false;
                    break;
            }
        }
    }

    // todo: Check if the player is there when a specific queue position is reached. Otherwise, disconnect (if this connection was initialized by AutoQueuer).
}
