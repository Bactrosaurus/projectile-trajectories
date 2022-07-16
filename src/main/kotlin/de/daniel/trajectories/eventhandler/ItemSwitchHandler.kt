package de.daniel.trajectories.eventhandler

import de.daniel.trajectories.data.ProjectileType
import de.daniel.trajectories.extensions.projectileType
import net.axay.kspigot.event.listen
import net.axay.kspigot.runnables.taskRunLater
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object ItemSwitchHandler {

    val playerProjectileMap = mutableMapOf<Player, ProjectileType>()

    private fun updatePlayerProjectileItem(player: Player) {
        taskRunLater(delay = 1) {
            val projectileType = player.projectileType()
            if (projectileType == null) {
                playerProjectileMap.remove(player)
                return@taskRunLater
            }
            playerProjectileMap[player] = projectileType
        }
    }

    fun register() {
        listen<PlayerJoinEvent> { updatePlayerProjectileItem(it.player) }
        listen<PlayerQuitEvent> { playerProjectileMap.remove(it.player) }
        listen<PlayerItemHeldEvent> { updatePlayerProjectileItem(it.player) }
        listen<PlayerDropItemEvent> { updatePlayerProjectileItem(it.player) }
        listen<InventoryClickEvent> {
            if (it.whoClicked.type != EntityType.PLAYER) return@listen
            updatePlayerProjectileItem(it.whoClicked as Player)
        }

        listen<InventoryDragEvent> {
            if (it.whoClicked.type != EntityType.PLAYER) return@listen
            updatePlayerProjectileItem(it.whoClicked as Player)
        }

        listen<ProjectileLaunchEvent> {
            if (it.entity.shooter !is Player) return@listen
            updatePlayerProjectileItem(it.entity.shooter as Player)
        }

        listen<EntityPickupItemEvent> {
            if (it.entityType != EntityType.PLAYER) return@listen
            updatePlayerProjectileItem(it.entity as Player)
        }
    }

}