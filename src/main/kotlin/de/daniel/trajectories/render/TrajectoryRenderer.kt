package de.daniel.trajectories.render

import de.daniel.trajectories.data.Trajectory
import de.daniel.trajectories.extensions.xNormal
import de.daniel.trajectories.extensions.yNormal
import net.axay.kspigot.runnables.async
import org.bukkit.Particle
import kotlin.math.cos
import kotlin.math.sin

object TrajectoryRenderer {

    fun renderTrajectory(
        trajectory: Trajectory,
        particleType: Particle = Particle.CRIT_MAGIC,
        drawCircle: Boolean = true,
        circleRadius: Double = 0.5,
        particleAmount: Int = 20
    ) {
        async {
            trajectory.locations.forEach {
                trajectory.player.spawnParticle(particleType, it, 1, 0.0, 0.0, 0.0, 0.0)
            }

            if (trajectory.locations.size < 2 || !drawCircle) return@async

            val lastLocation = trajectory.locations[trajectory.locations.size - 1]

            val hitBlockFace = trajectory.hitResult?.hitBlockFace ?: return@async

            for (i in 0 until particleAmount) {
                val angle = i * 2 * Math.PI / particleAmount

                val x = cos(angle) * circleRadius
                val y = sin(angle) * circleRadius

                val particleLocation = lastLocation.clone()
                    .add(hitBlockFace.xNormal().multiply(x))
                    .add(hitBlockFace.yNormal().multiply(y))

                trajectory.player
                    .spawnParticle(particleType, particleLocation, 1, 0.0, 0.0, 0.0, 0.0)
            }
        }
    }

}