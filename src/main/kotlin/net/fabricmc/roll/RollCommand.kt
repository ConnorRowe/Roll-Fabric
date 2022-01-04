package net.fabricmc.roll

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.roll.config.RollConfig
import net.minecraft.network.MessageType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Style
import net.minecraft.text.TextColor
import net.minecraft.text.TranslatableText
import kotlin.random.Random

object RollCommand : Command<Any?> {

    lateinit var config: RollConfig

    override fun run(context: CommandContext<Any?>?): Int {
        return 0
    }

    fun register(dispatcher: CommandDispatcher<ServerCommandSource?>, config: RollConfig) {

        this.config = config

        // Fast roll 1 - 100
        dispatcher.register(CommandManager.literal("roll").executes(this::roll))

        // Roll 1 - max var
        dispatcher.register(
            CommandManager.literal("roll").then(
                CommandManager.argument("max", IntegerArgumentType.integer())
                    .executes { ctx: CommandContext<ServerCommandSource?> ->
                        rollRange(
                            ctx,
                            1,
                            IntegerArgumentType.getInteger(ctx, "max")
                        )
                    })
        )

        // Roll min var - max var
        dispatcher.register(
            CommandManager.literal("roll").then(
                CommandManager.argument("min", IntegerArgumentType.integer())
                    .then(
                        CommandManager.argument("max", IntegerArgumentType.integer())
                            .executes { ctx: CommandContext<ServerCommandSource?> ->
                                rollRange(
                                    ctx,
                                    IntegerArgumentType.getInteger(ctx, "min"),
                                    IntegerArgumentType.getInteger(ctx, "max")
                                )
                            })
            )
        )
    }

    private fun roll(ctx: CommandContext<ServerCommandSource?>): Int {
        return rollRange(ctx, 1, 100)
    }

    private fun rollRange(ctx: CommandContext<ServerCommandSource?>, min: Int, max: Int): Int {
        if (ctx.source?.entity is ServerPlayerEntity) {
            // Gets command source
            val serverCmdSrc: ServerCommandSource = ctx.source as ServerCommandSource
            // Gets player
            val player: ServerPlayerEntity = serverCmdSrc.entity as ServerPlayerEntity
            // Rolls the dice & builds the message
            val msg = TranslatableText("menu.roll.roll_msg_rolls",
                player.displayName.shallowCopy().setStyle(Style.EMPTY.withColor(TextColor.parse(config.player_rgb)).withBold(config.player_bold).withItalic(config.player_italic)),
                TranslatableText("menu.roll.roll_msg_num", Random.nextInt(min, max + 1),
                TranslatableText("menu.roll.roll_msg_range", min, max).setStyle(Style.EMPTY.withColor(TextColor.parse(config.range_rgb)).withBold(config.range_bold).withItalic(config.range_italic)))
                .setStyle(Style.EMPTY.withColor(TextColor.parse(config.roll_num_rgb)).withBold(config.roll_num_bold).withItalic(config.roll_num_italic)))
                .setStyle(Style.EMPTY.withColor(TextColor.parse(config.rolls_rgb)).withBold(config.rolls_bold).withItalic(config.rolls_italic))

            // Gets iterator for all players
            val playerIterator = serverCmdSrc.server.playerManager.playerList.iterator()

            // Sends the message to everyone
            for (p in playerIterator) {
                p.sendMessage(msg, MessageType.CHAT, player.uuid)
            }
        }
        return 1
    }
}