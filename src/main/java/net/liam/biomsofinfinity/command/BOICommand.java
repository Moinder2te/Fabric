package net.liam.biomsofinfinity.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.liam.biomsofinfinity.BiomsOfInfinityMod;
import net.liam.biomsofinfinity.config.BOIConfig;
import net.liam.biomsofinfinity.persistent.BossCooldownManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public final class BOICommand {
    private BOICommand() {}

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("boi")
            .requires(source -> source.hasPermissionLevel(2))
            .then(configNode())
            .then(bossNode())
        );
    }

    private static ArgumentBuilder<ServerCommandSource, ?> configNode() {
        return literal("config")
            .then(literal("reload").executes(BOICommand::reloadConfig));
    }

    private static ArgumentBuilder<ServerCommandSource, ?> bossNode() {
        return literal("boss")
            .then(literal("enable").executes(context -> toggleBosses(context, true)))
            .then(literal("disable").executes(context -> toggleBosses(context, false)))
            .then(literal("resetcooldowns").executes(BOICommand::resetCooldowns));
    }

    private static int reloadConfig(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        BiomsOfInfinityMod.reloadConfig();
        context.getSource().sendFeedback(() -> Text.literal("Reloaded Bioms of Infinity configuration."), true);
        return 1;
    }

    private static int toggleBosses(CommandContext<ServerCommandSource> context, boolean enable) throws CommandSyntaxException {
        BOIConfig config = BiomsOfInfinityMod.getConfig();
        config.bosses.allowBossSpawns = enable;
        BiomsOfInfinityMod.updateConfig(config);
        context.getSource().sendFeedback(() -> Text.literal("Boss altar activations " + (enable ? "enabled" : "disabled")), true);
        return enable ? 1 : 0;
    }

    private static int resetCooldowns(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        BossCooldownManager cooldowns = BossCooldownManager.get(context.getSource().getServer());
        cooldowns.reset();
        context.getSource().sendFeedback(() -> Text.literal("All boss cooldowns reset."), true);
        return 1;
    }
}
