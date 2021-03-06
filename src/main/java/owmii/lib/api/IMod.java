package owmii.lib.api;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Consumer;

public interface IMod {

    default void setup(FMLCommonSetupEvent event) {}

    default void client(FMLClientSetupEvent event) {}

    default void modEnqueue(InterModEnqueueEvent event) {}

    default void loadComplete(FMLLoadCompleteEvent event) {}

    default void aboutToStar(FMLServerAboutToStartEvent event) {}

    default void starting(FMLServerStartingEvent event) {
        registerCommands(event.getCommandDispatcher());
    }

    default void registerCommands(CommandDispatcher<CommandSource> cd) {}

    default void started(FMLServerStartedEvent event) {}

    default void stopped(FMLServerStoppedEvent event) {}

    default <T extends Event> void addModListener(Consumer<T> consumer) {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(consumer);
    }

    default <T extends Event> void addEventListener(Consumer<T> consumer) {
        MinecraftForge.EVENT_BUS.addListener(consumer);
    }

    default void loadListeners() {
        addModListener(this::setup);
        addModListener(this::client);
        addModListener(this::modEnqueue);
        addModListener(this::loadComplete);
        addEventListener(this::aboutToStar);
        addEventListener(this::starting);
        addEventListener(this::started);
        addEventListener(this::stopped);
    }
}
