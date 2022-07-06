package de.daniel.trajectories.data

import org.bukkit.Material

enum class ProjectileType(val initVelocity: Double, val gravAccel: Double) {
    /**
     * Types should match the name of Material enum in order to make getPlayerProjectileType() working
     */
    ENDER_PEARL(1.5, 0.03),
    SNOWBALL(1.5, 0.03),
    EGG(1.5, 0.03),
    BOW(3.0, 0.05),
    TRIDENT(3.0, 0.05),
    CROSSBOW(3.15, 0.05);

    companion object {
        fun getPlayerProjectileTypeFromMaterial(material: Material): ProjectileType? {
            return try {
                valueOf(material.name)
            } catch (exc: IllegalArgumentException) {
                null
            }
        }
    }
}