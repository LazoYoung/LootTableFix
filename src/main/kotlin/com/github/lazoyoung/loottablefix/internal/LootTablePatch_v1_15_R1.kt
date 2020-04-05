package com.github.lazoyoung.loottablefix.internal

import com.github.lazoyoung.loottablefix.LootTablePatch
import net.minecraft.server.v1_15_R1.BlockPosition
import net.minecraft.server.v1_15_R1.DamageSource
import net.minecraft.server.v1_15_R1.LootContextParameters
import net.minecraft.server.v1_15_R1.LootTableInfo
import org.bukkit.craftbukkit.v1_15_R1.CraftLootTable
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftHumanEntity
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftInventory
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.loot.LootContext
import org.bukkit.loot.LootTable
import java.util.*

class LootTablePatch_v1_15_R1 : LootTablePatch {

    override fun populateLoot(lootTable: LootTable, context: LootContext): List<ItemStack> {
        val nmsContext = convertContext(lootTable, context)
        val nmsItems = getHandle(lootTable).populateLoot(nmsContext)
        val bukkitItems = ArrayList<ItemStack>(nmsItems.size)
        val iter = nmsItems.iterator()

        while (iter.hasNext()) {
            val nmsItem = iter.next()

            if (!nmsItem.isEmpty) {
                bukkitItems.add(CraftItemStack.asBukkitCopy(nmsItem))
            }
        }

        return bukkitItems
    }

    override fun fillInventory(inventory: Inventory, lootTable: LootTable, context: LootContext) {
        val nmsContext = convertContext(lootTable, context)
        val craftInventory = inventory as CraftInventory
        val handle = craftInventory.inventory

        getHandle(lootTable).fillInventory(handle, nmsContext)
    }

    private fun convertContext(lootTable: LootTable, context: LootContext): LootTableInfo {
        val loc = context.location
        val handle = (loc.world as CraftWorld).handle
        val builder = LootTableInfo.Builder(handle)
        val x = context.location.blockX
        val y = context.location.blockY
        val z = context.location.blockZ

        if (getHandle(lootTable) != net.minecraft.server.v1_15_R1.LootTable.EMPTY) {
            if (context.lootedEntity != null) {
                val nmsLootedEntity = (context.lootedEntity as CraftEntity?)!!.handle
                builder.set(LootContextParameters.THIS_ENTITY, nmsLootedEntity)
                builder.set(LootContextParameters.DAMAGE_SOURCE, DamageSource.GENERIC)
            }
            if (context.killer != null) {
                val nmsKiller = (context.killer as CraftHumanEntity?)!!.handle
                builder.set(LootContextParameters.KILLER_ENTITY, nmsKiller)
                builder.set(LootContextParameters.DAMAGE_SOURCE, DamageSource.playerAttack(nmsKiller))
            }

            builder.set(LootContextParameters.POSITION, BlockPosition(x, y, z))
        }

        return builder.build(getHandle(lootTable).lootContextParameterSet)
    }

    private fun getHandle(lootTable: LootTable): net.minecraft.server.v1_15_R1.LootTable {
        return (lootTable as CraftLootTable).handle
    }

}