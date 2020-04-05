package com.github.lazoyoung.loottablefix

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object {
        internal val serverVersion = Bukkit.getServer().javaClass.`package`.name.split(".")[3]
    }

    override fun onEnable() {
        logger.info("Detected server version: $serverVersion")

        getCommand("loottable")?.setExecutor(LootTableCommand())
    }

}