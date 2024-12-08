package com.github.stefvanschie.inventoryframework.util.version;

import com.github.stefvanschie.inventoryframework.exception.UnsupportedVersionException;
import org.bukkit.Bukkit;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.EnumSet;

/**
 * The different supported NMS versions
 *
 * @since 0.8.0
 */
public enum Version {

    /**
     * Version 1.21.3
     *
     * @since 0.10.18
     */
    LATEST;

    /**
     * A collection of versions on which modern smithing tables are available.
     */
    private static final Collection<Version> MODERN_SMITHING_TABLE_VERSIONS = EnumSet.of(
            LATEST
    );

    /**
     * A collection of versions on which {@link InventoryView} is an interface.
     */
    @NotNull
    private static final Collection<@NotNull Version> INTERFACE_INVENTORY_VIEW = EnumSet.of(
            LATEST
    );

    /**
     * Checks whether the {@link InventoryView} class is an interface on this version.
     *
     * @return true if the class is an interface, false otherwise
     * @since 0.10.16
     */
    @Contract(pure = true)
    public boolean isInventoryViewInterface() {
        return INTERFACE_INVENTORY_VIEW.contains(this);
    }

    /**
     * Checks whether modern smithing tables exist on this version. Returns true if they do, otherwise false.
     *
     * @return true if modern smithing tables are available
     * @since 0.10.10
     */
    boolean existsModernSmithingTable() {
        return MODERN_SMITHING_TABLE_VERSIONS.contains(this);
    }

    /**
     * Gets the version currently being used. If the used version is not supported, an
     * {@link UnsupportedVersionException} will be thrown.
     *
     * @return the version of the current instance
     * @since 0.8.0
     */
    @NotNull
    @Contract(pure = true)
    public static Version getVersion() {
        String version = Bukkit.getBukkitVersion().split("-")[0];

        if (version.equals("1.21.4")) {
            return LATEST;
        }
        throw new UnsupportedVersionException("The server version provided is not supported");
    }
}
