package de.daniel.trajectories.extensions

import de.daniel.trajectories.data.ProjectileType
import org.bukkit.entity.Player

fun Player.projectileType() = ProjectileType.getPlayerProjectileTypeFromMaterial(inventory.itemInMainHand.type)