package com.github.lazoyoung.loottablefix

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.loot.LootContext
import org.bukkit.loot.LootTable

interface LootTablePatch {

    fun populateLoot(lootTable: LootTable, context: LootContext): List<ItemStack>

    fun fillInventory(inventory: Inventory, lootTable: LootTable, context: LootContext)

}