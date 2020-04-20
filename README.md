# LootTableFix
This is a Paper plugin to satisfy my personal need which is to have a working LootTable.  
LootTable API is mostly broken due to severe flaw inside CraftBukkit layer.  
This plugin simply access net.minecraft.server just like CraftBukkit does and provides a proper API solution.

## Example of API usage
```java
// The player who receives the loot.
Player player = Bukkit.getPlayer("SkyDoesMinecraft");

// Retrieve LootTablePatch
ServicesManager services = Bukkit.getServicesManager();

if (!services.isProvidedFor(LootTablePatch.class)) {
    Bukkit.getLogger().warning("LootTableFix plugin is required!");
    return;
}

LootTablePatch patch = services.getRegistration(LootTablePatch.class).getProvider();
LootContext context = LootContext.Builder(player.getLocation());
LootTable lootTable = Bukkit.getLootTable("minecraft", "chests/end_city_treasure");

patch.fillInventory(player.getInventory(), lootTable, context);
```

## Download
Install the latest version of binary into server's plugin folder.  
Jar binary is available in the [Releases](https://github.com/LazoYoung/LootTableFix/releases) page.

## Link to your project
Follow these steps to hook this project by your favorite build tool.  

First, you need to install CraftBukkit into your local Maven repository with
[Spigot BuildTools](https://www.spigotmc.org/threads/buildtools-updates-information.42865/).  
Subsequent steps vary depending on what build tool you use: Maven or Gradle

### 1. Maven
Add repository and dependency to pom.xml in your project:
```xml
<repositories>
    <repository>
        <id>github</id>
        <name>LootTableFix Package</name>
        <url>https://maven.pkg.github.com/LazoYoung/LootTableFix</url>
        <releases><enabled>true</enabled></releases>
        <snapshots><enabled>true</enabled></snapshots>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.lazoyoung</groupId>
    <artifactId>LootTableFix</artifactId>
    <version>1.0.1</version>
</dependency>
```
Now you need to make your Github `personal access token`.
[Learn how to make one](https://help.github.com/en/github/authenticating-to-github/creating-a-personal-access-token-for-the-command-line).

Next, append the following in global Maven settings: `USER_HOME\.m2\settings.xml`  
> `GITHUB_USERNAME` and `YOUR_ACCESS_TOKEN` is your credential info.
```xml
<servers>
    <server>
        <id>github</id>
        <username>GITHUB_USERNAME</username>
        <password>YOUR_ACCESS_TOKEN</password>
    </server>
</servers>
```
That's basically it! Run `maven install` to install the package.

### 2. Gradle
Add repository and dependency to build.gradle in your project:
```groovy
repositories {
    mavenLocal()
    maven {
        name "LootTableFix Package"
        url "https://maven.pkg.github.com/LazoYoung/LootTableFix"
        credentials {
            username = getProperty("github.username")
            password = getProperty("github.token")
        }
    }
}
dependencies {
    compileOnly "com.github.lazoyoung:LootTableFix:1.0.1"
}
```
Now you need to make your Github `personal access token`.
[Learn how to make one](https://help.github.com/en/github/authenticating-to-github/creating-a-personal-access-token-for-the-command-line).

Next, append the following in global Gradle settings: `USER_HOME\.gradle\gradle.properties`  
> `GITHUB_USERNAME` and `YOUR_ACCESS_TOKEN` is your credential info.
```groovy
github.username=GITHUB_USERNAME
github.token=YOUR_ACCESS_TOKEN
```
That's basically it! Re-import your Gradle project to install the package.
