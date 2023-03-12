package me.window.lightapicommands;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.beykerykt.minecraft.lightapi.common.LightAPI;

import java.util.ArrayList;

@Route(name = "light")
@Permission("lightapi.set")
public class LightCommand {

    public void addToLights(Player player, LightObject lightObject) {
        LightAPICommands.INSTANCE.lights.putIfAbsent(player.getUniqueId(), new ArrayList<>());
        LightAPICommands.INSTANCE.lights.get(player.getUniqueId()).add(lightObject);
    }

    @Execute(required = 1)
    public void execute(Player player, @Arg @Name("light level") int level) {
        addToLights(player, new LightObject(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), player.getWorld().getName(),
                LightAPI.get().getLightLevel(player.getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())));
        LightAPI.get().setLightLevel(player.getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), level);
        player.sendMessage("Light source for location " + player.getLocation() + " has been set to " + level);
    }
    @Execute
    public void execute(Player player, @Arg Location location, @Arg @Name("light level") int level) {
        addToLights(player, new LightObject(location.getBlockX(), location.getBlockY(), location.getBlockZ(), player.getWorld().getName(),
                LightAPI.get().getLightLevel(player.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ())));
        LightAPI.get().setLightLevel(player.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), level);
        location.setWorld(player.getWorld());
        player.sendMessage("Light source for location " + location + " has been set to " + level);
    }

    @Execute(route = "undo")
    @Permission("light.undo")
    public void undo(Player player) {
        LightAPICommands inst = LightAPICommands.INSTANCE;
        inst.lights.putIfAbsent(player.getUniqueId(), new ArrayList<>());
        if(inst.lights.get(player.getUniqueId()).size() == 0) {
            player.sendMessage(Component.text("Nothing left to undo.", NamedTextColor.RED));
        } else {
            LightObject lightObject = inst.lights.get(player.getUniqueId()).remove(inst.lights.get(player.getUniqueId()).size() - 1);
            LightAPI.get().setLightLevel(lightObject.world, lightObject.x, lightObject.y, lightObject.z, lightObject.level);
            player.sendMessage(Component.text("Undid last available action.", NamedTextColor.GREEN));
        }
    }
}
