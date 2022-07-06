package de.daniel.trajectories.math

import de.daniel.trajectories.data.ProjectileType
import de.daniel.trajectories.data.Trajectory
import org.bukkit.FluidCollisionMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.util.RayTraceResult
import kotlin.math.pow

object TrajectoryCalculator {

    fun getTrajectory(player: Player, projectileType: ProjectileType): Trajectory {
        val location = player.eyeLocation.subtract(0.0, 0.1, 0.0)
        val velocity = location.direction.multiply(projectileType.initVelocity)
        val trajectoryLocations = mutableListOf<Location>()
        var ticksPassed = 0
        var hitResult: RayTraceResult? = null
        while (true) {
            // Apply drag and gravity
            velocity.multiply(0.99)
            velocity.y -= (projectileType.gravAccel * ((0.99).pow(ticksPassed + 1)))
            if (velocity.length() <= 0) break
            hitResult = location.world.rayTrace(
                location,
                velocity,
                velocity.length(),
                FluidCollisionMode.NEVER,
                true,
                0.0
            ) { it !is Projectile }
            if (hitResult?.hitBlock == null && (hitResult?.hitEntity == null || hitResult.hitEntity == player)) {
                location.add(velocity)
                if (player.eyeLocation.distance(location) > 5) trajectoryLocations.add(location.clone())
            } else {
                trajectoryLocations.add(hitResult.hitPosition.toLocation(player.world))
                break
            }
            ticksPassed++
        }
        return Trajectory(player, trajectoryLocations, hitResult)
    }
}