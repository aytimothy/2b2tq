package xyz.aytiomthy.tbttq;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;

@Mod(modid = tbttq.MODID, name = tbttq.NAME, version = tbttq.VERSION)
public class tbttq
{
    public static final String MODID = "tbttq";
    public static final String NAME = "aytimothy's 2b2t Auto-Queue Mod";
    public static final String VERSION = "1.0";

    public static AutoQueueConfig config;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.CLIENT) {
            config = new AutoQueueConfig();
            config.LoadFromDisk();

            if (AutoQueueConfig.enabled && LocalDateTime.now().isAfter(AutoQueueConfig.timestamp)) {
                AutoQueueConfig.enabled = false;
                AutoQueueConfig.SaveToDisk();
            }
        }
        else {
            config = null;
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }
}
