package eu.endercentral.crazy_advancements.nms;

import eu.endercentral.crazy_advancements.CrazyAdvancementsAPI;
import eu.endercentral.crazy_advancements.nms.v1X.NMSBinding1X;
import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NMS {
    private static final INMSBinding binding = bind();

    public static INMSBinding get() {
        return binding;
    }

    public static String getNMSTag() {

        try {
            return Bukkit.getServer().getClass().getCanonicalName().split("\\Q.\\E")[3];
        } catch (Throwable e) {
            getLogger().severe("Failed to determine server nms version!");
            e.printStackTrace();
        }

        return "BUKKIT";
    }

    private static INMSBinding bind() {
        Logger logger = getLogger();
        String code = getNMSTag();
        logger.info("Locating NMS Binding for " + code);

        try {
            Class<?> clazz = Class.forName("eu.endercentral.crazy_advancements.nms."+code+".NMSBinding");
            try {
                Object b = clazz.getConstructor().newInstance();
                if (b instanceof INMSBinding binding) {
                    logger.info("Craftbukkit " + code + " <-> " + b.getClass().getSimpleName() + " Successfully Bound");
                    return binding;
                }
            } catch (Throwable e) {
                logger.log(Level.SEVERE, "Craftbukkit " + code + " <-> " + clazz.getSimpleName() + " Failed to Bind", e);
            }
        } catch (ClassNotFoundException|NoClassDefFoundError ignored) {}

        logger.info("Craftbukkit " + code + " <-> " + NMSBinding1X.class.getSimpleName() + " Successfully Bound");
        logger.warning("Note: Some features of Iris may not work the same since you are on an unsupported version of Minecraft.");
        logger.warning("Note: If this is a new version, expect an update soon.");

        return new NMSBinding1X();
    }

    private static Logger getLogger() {
        return Logger.getLogger(CrazyAdvancementsAPI.class.getSimpleName());
    }
}
