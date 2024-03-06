package de.daniel.trajectories.extensions

import org.bukkit.block.BlockFace
import org.bukkit.util.Vector

fun BlockFace.xNormal() = when (this) {
    BlockFace.DOWN, BlockFace.UP -> Vector(1, 0, 0)
    BlockFace.EAST, BlockFace.WEST -> Vector(0, 0, 1)
    BlockFace.NORTH, BlockFace.SOUTH -> Vector(1, 0, 0)
    else -> Vector(1, 0, 0)
}

fun BlockFace.yNormal() = when (this) {
    BlockFace.DOWN, BlockFace.UP -> Vector(0, 0, 1)
    BlockFace.EAST, BlockFace.WEST -> Vector(0, 1, 0)
    BlockFace.NORTH, BlockFace.SOUTH -> Vector(0, 1, 0)
    else -> Vector(0, 0, 1)
}