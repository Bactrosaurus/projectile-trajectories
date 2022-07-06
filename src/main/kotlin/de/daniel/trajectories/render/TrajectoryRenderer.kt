package de.daniel.trajectories.render

import de.daniel.trajectories.data.Trajectory
import net.axay.kspigot.runnables.async
import org.bukkit.Particle
import org.bukkit.block.BlockFace
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin

object TrajectoryRenderer {

    fun renderTrajectory(
        trajectory: Trajectory,
        particleType: Particle = Particle.CRIT_MAGIC,
        drawCircle: Boolean = true,
        circleRadius: Double = 1.0
    ) {
        async {
            trajectory.locations.forEach {
                trajectory.player.spawnParticle(particleType, it, 1, 0.0, 0.0, 0.0, 0.0)
            }
            if (trajectory.locations.size < 2 || !drawCircle) return@async
            val particleAmount = 30
            for (i in 0 until particleAmount) {
                val angle = i * (2 * Math.PI) / particleAmount
                val x = cos(angle) * circleRadius
                val y = sin(angle) * circleRadius
                val hitBlockFace = trajectory.hitResult?.hitBlockFace ?: return@async
                var xOrthogonal: Vector
                var yOrthogonal: Vector
                when (hitBlockFace) {
                    BlockFace.DOWN, BlockFace.UP -> {
                        xOrthogonal = Vector(1, 0, 0)
                        yOrthogonal = Vector(0, 0, 1)
                    }
                    BlockFace.WEST, BlockFace.EAST -> {
                        xOrthogonal = Vector(0, 0, 1)
                        yOrthogonal = Vector(0, 1, 0)
                    }
                    BlockFace.NORTH, BlockFace.SOUTH -> {
                        xOrthogonal = Vector(1, 0, 0)
                        yOrthogonal = Vector(0, 1, 0)
                    }
                    else -> {
                        xOrthogonal = Vector(1, 0, 0)
                        yOrthogonal = Vector(0, 0, 1)
                    }
                }
                val particleLocation = trajectory.locations[trajectory.locations.size - 2].clone()
                    .add(xOrthogonal.multiply(x)).add(yOrthogonal.multiply(y))
                trajectory.player
                    .spawnParticle(particleType, particleLocation, 1, 0.0, 0.0, 0.0, 0.0)
            }
        }
    }

}