package de.daniel.trajectories.data

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.util.RayTraceResult

data class Trajectory(val player: Player, val locations: List<Location>, val hitResult: RayTraceResult?)