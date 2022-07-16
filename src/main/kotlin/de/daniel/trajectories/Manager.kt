package de.daniel.trajectories

import de.daniel.trajectories.eventhandler.ItemSwitchHandler
import de.daniel.trajectories.math.TrajectoryCalculator
import de.daniel.trajectories.render.TrajectoryRenderer
import net.axay.kspigot.main.KSpigot
import net.axay.kspigot.runnables.task

class InternalMainClass : KSpigot() {

    companion object {
        lateinit var INSTANCE: InternalMainClass; private set
    }

    override fun load() {
        INSTANCE = this
    }

    override fun startup() {
        ItemSwitchHandler.register()

        task(period = 1) {
            ItemSwitchHandler.playerProjectileMap.keys.forEach {
                val projectileType = ItemSwitchHandler.playerProjectileMap[it] ?: return@forEach
                val trajectory = TrajectoryCalculator.getTrajectory(it, projectileType)

                TrajectoryRenderer.renderTrajectory(trajectory)
            }
        }
    }

}

@Suppress("unused")
val Manager by lazy { InternalMainClass.INSTANCE }

