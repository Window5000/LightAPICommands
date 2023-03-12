package me.window.lightapicommands;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.platform.LiteSender;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import ru.beykerykt.minecraft.lightapi.common.LightAPI;

@Route(name = "light")
@Permission("lightapi.set")
public class LightCommand {

    @Execute(required = 1)
    public void execute(Player player, @Arg @Name("light level") int level) {
        LightAPI.get().setLightLevel(player.getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), level);
        player.sendMessage("Light source for location " + player.getLocation() + " has been set to " + level);
    }
    @Execute
    public void execute(Player player, @Arg Location location, @Arg @Name("light level") int level) {
        LightAPI.get().setLightLevel(player.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), level);
        location.setWorld(player.getWorld());
        player.sendMessage("Light source for location " + location + " has been set to " + level);
    }
}
