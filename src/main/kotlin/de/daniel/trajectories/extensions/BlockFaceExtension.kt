package de.daniel.trajectories.extensions

import org.bukkit.block.BlockFace
import org.bukkit.util.Vector

val xOrthogonal = Vector(1, 0, 0)
val yOrthogonal = Vector(0, 1, 0)
val zOrthogonal = Vector(0, 0, 1)

fun BlockFace.xNormal() = when (this) {
    BlockFace.DOWN, BlockFace.UP -> xOrthogonal
    BlockFace.EAST, BlockFace.WEST -> zOrthogonal
    BlockFace.NORTH, BlockFace.SOUTH -> xOrthogonal
    else -> xOrthogonal
}

fun BlockFace.yNormal() = when (this) {
    BlockFace.DOWN, BlockFace.UP -> zOrthogonal
    BlockFace.EAST, BlockFace.WEST -> yOrthogonal
    BlockFace.NORTH, BlockFace.SOUTH -> yOrthogonal
    else -> zOrthogonal
}