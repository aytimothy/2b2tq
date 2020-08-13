package xyz.aytiomthy.tbttq;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class AutoQueueConfig {
    /*
        AutoQUeueConfig - Data Container Class
        Houses the data for the auto-queuer.
     */
    public static String server;
    public static LocalDateTime timestamp;
    public static Boolean enabled;
    public static Integer afkCheckQueuePosition;
    public static Integer afkTimeout;
    public static Boolean enableAFKTimeout;

    private static Configuration config = null;

    private static Property _server;
    private static Property _enabled;
    private static Property _timestamp;
    private static Property _enableAFKTimeout;
    private static Property _afkTimeout;
    private static Property _afkCheckQueuePosition;

    public static final String CATEGORY_NAME_GENERAL = "general";

    public static void LoadFromDisk() {
        File configFile = new File(Minecraft.getMinecraft().mcDataDir, "/config/AutoQueue.cfg");
        config = new Configuration(configFile);
        config.load();

        _server = config.get(CATEGORY_NAME_GENERAL, "server", "2b2t.org", "Server to connect to.");
        _enabled = config.get(CATEGORY_NAME_GENERAL, "enabled", false, "Whether or not it should attempt to connect.");
        _timestamp = config.get(CATEGORY_NAME_GENERAL, "triggerTimestamp", LocalDateTime.now().toString(), "Timestamp at which to start connecting.");
        _enableAFKTimeout = config.get(CATEGORY_NAME_GENERAL, "enableAFKTimeout", true, "Whether or not to check whether the user is asleep or not.");
        _afkTimeout = config.get(CATEGORY_NAME_GENERAL, "afkTimeout", 600, "How many seconds to wait before AFK timing out?", 1, Integer.MAX_VALUE);
        _afkCheckQueuePosition = config.get("afkCheckPosition", CATEGORY_NAME_GENERAL, 60, "At what queue position should we check for AFK?", 1, Integer.MAX_VALUE);

        server = _server.getString();
        enabled = _enabled.getBoolean();
        try {
            timestamp = LocalDateTime.parse(_timestamp.getString());
        }
        catch (Exception ex) {
            timestamp = LocalDateTime.now().plusHours(6);
        }
        enableAFKTimeout = _enableAFKTimeout.getBoolean();
        afkTimeout = _afkTimeout.getInt();
        afkCheckQueuePosition = _afkCheckQueuePosition.getInt();

        config.save();
    }

    public static void SaveToDisk() {
        _server.set(server);
        _enabled.set(enabled);
        _timestamp.set(timestamp.toString());
        _enableAFKTimeout.set(enableAFKTimeout);
        _afkTimeout.set(afkTimeout);
        _afkCheckQueuePosition.set(afkCheckQueuePosition);

        config.save();
    }
}
