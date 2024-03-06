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
        val location = player.location.add(0.0, 1.62-0.10000000149011612, 0.0)
        val velocity = location.direction.multiply(projectileType.initVelocity)
        val trajectoryLocations = mutableListOf<Location>()

        var hitResult: RayTraceResult? = null

        for (ticksPassed in 0..100) {
            // Apply drag and gravity
            velocity.multiply(0.99)
            velocity.y -= projectileType.gravAccel

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
        }

        return Trajectory(player, trajectoryLocations, hitResult)
    }
}