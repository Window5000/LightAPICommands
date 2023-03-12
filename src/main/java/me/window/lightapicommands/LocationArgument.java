package me.window.lightapicommands;

import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.MultilevelArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import panda.std.Result;

import java.util.Arrays;
import java.util.List;

// https://github.com/Rollczi/LiteCommands/blob/master/examples/bukkit/src/main/java/dev/rollczi/example/bukkit/argument/LocationArgument.java (Apache License 2.0)

@ArgumentName("x y z")
public class LocationArgument implements MultilevelArgument<Location> {

    @Override
    public Result<Location, Object> parseMultilevel(LiteInvocation invocation, String... arguments) {
        return Result.supplyThrowing(NumberFormatException.class, () -> {

            double x;
            double y;
            double z;


            if(arguments[0].contains("~") && invocation.sender().getHandle() instanceof Player player) {
                x = player.getLocation().getX();
                String str = arguments[0].replaceAll("~", "");
                if(!str.isBlank()) x += Double.parseDouble(str);
            }
            else x = Double.parseDouble(arguments[0]);

            if(arguments[1].contains("~") && invocation.sender().getHandle() instanceof Player player) {
                y = player.getLocation().getY();
                String str = arguments[1].replaceAll("~", "");
                if(!str.isBlank()) y += Double.parseDouble(str);
            }
            else y = Double.parseDouble(arguments[1]);

            if(arguments[2].contains("~") && invocation.sender().getHandle() instanceof Player player) {
                z = player.getLocation().getZ();
                String str = arguments[2].replaceAll("~", "");
                if(!str.isBlank()) z += Double.parseDouble(str);
            }
            else z = Double.parseDouble(arguments[2]);


            return new Location(null, x, y, z);
        }).mapErr(ex -> "&cError!");
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return Arrays.asList(
                Suggestion.multilevel("100", "100", "100"),
                Suggestion.multilevel("5", "5", "5"),
                Suggestion.multilevel("10", "35", "-10")
        );
    }

    @Override
    public boolean validate(LiteInvocation invocation, Suggestion suggestion) {
        for (String suggest : suggestion.multilevelList()) {
            if (!suggest.matches("-?[\\d.]+")) { // -? - optional negative, \\d - digit, . - dot
                return false;
            }
        }

        return true;
    }

    @Override
    public int countMultilevel() {
        return 3;
    }
}