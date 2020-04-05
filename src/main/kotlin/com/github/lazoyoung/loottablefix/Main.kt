package com.github.lazoyoung.loottablefix

import org.bukkit.Bukkit
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object {
        internal val serverVersion = Bukkit.getServer().javaClass.`package`.name.split(".")[3]
        internal lateinit var lootTablePatch: LootTablePatch
    }

    override fun onEnable() {
        logger.info("Detected server version: $serverVersion")

        try {
            val className = "com.github.lazoyoung.loottablefix.internal.LootTablePatch_"
                    .plus(serverVersion)
            val clazz = Class.forName(className)
            val instance = clazz.newInstance() as LootTablePatch

            lootTablePatch = instance
            server.servicesManager.register(LootTablePatch::class.java, lootTablePatch, this, ServicePriority.Normal)
            logger.info("Registered patch: ${clazz.simpleName}")

            getCommand("loottable")?.setExecutor(LootTableCommand())
        } catch (e: Exception) {
            logger.warning("Plugin does not support this version!")
            pluginLoader.disablePlugin(this)
        }
    }

}