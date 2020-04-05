package com.github.lazoyoung.loottablefix

import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.loot.LootContext

class LootTableCommand : CommandExecutor {

    @Suppress("DEPRECATION")
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Players can execute this command.")
            return true
        }

        if (!sender.hasPermission(command.permission!!)) {
            sender.sendMessage(command.permissionMessage!!)
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("/loottable (namespace) (key)")
            return true
        }

        if (args.size < 2) {
            return false
        }

        val context = LootContext.Builder(sender.location).build()
        val namespacedKey = NamespacedKey(args[0], args[1])
        val name = namespacedKey.toString()
        val lootTable = Bukkit.getLootTable(namespacedKey)
        val items: List<ItemStack>

        if (lootTable == null) {
            sender.sendMessage("$name not found.")
            return true
        }

        items = Main.lootTablePatch.populateLoot(lootTable, context)

        if (items.isEmpty()) {
            sender.sendMessage("$name not found.")
            return true
        }

        sender.inventory.addItem(*items.toTypedArray())
        sender.sendMessage("Loot \'$name\' is filled into your inventory.")
        return true
    }

}