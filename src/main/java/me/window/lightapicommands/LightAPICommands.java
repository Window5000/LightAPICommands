package me.window.lightapicommands;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class LightAPICommands extends JavaPlugin {

    private LiteCommands<CommandSender> liteCommands;

    public static LightAPICommands INSTANCE;

    public HashMap<UUID, ArrayList<LightObject>> lights = new HashMap<>();

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.liteCommands = LiteBukkitFactory.builder(this.getServer(), "lightapi-commands")
                .command(LightCommand.class)
                .argumentMultilevel(Location.class, new LocationArgument())
                .contextualBind(Player.class, new BukkitOnlyPlayerContextual<>("&cThis command can only be executed by players!"))
                .register();
    }

    @Override
    public void onDisable() {
        this.liteCommands.getPlatform().unregisterAll();
    }
}
