package com.github.lazoyoung.loottablefix

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.loot.LootContext
import org.bukkit.loot.LootTable

abstract class LootTableFix {

    companion object {
        fun get(lootTable: LootTable): LootTableFix? {
            return try {
                val className = "com.github.lazoyoung.loottablefix.internal.LootTableFix_".plus(Main.serverVersion)
                val clazz = Class.forName(className)

                clazz.constructors[0].newInstance(lootTable) as LootTableFix
            } catch (e: Exception) {
                null
            }
        }
    }

    abstract fun populateLoot(context: LootContext): List<ItemStack>

    abstract fun fillInventory(inventory: Inventory, context: LootContext)

}