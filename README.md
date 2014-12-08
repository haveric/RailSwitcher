#### RailSwitcher

### Allows you to turn rails or swap out different types of rails(powered, detector, activator, or normal) with each other by right clicking on rail with a rail or shears in hand.

#### Changelog:

#### Coming Soon
* Updated Metrics to handle old getOnlinePlayers (provides backward support for old versions of CraftBukkit)
* Add 1.8 items + doors to default block list
* Added support for data values in list files
** Examples:
** ACACIA_STAIRS (will affect all acacia stair data values)
** ACACIA_STAIRS:0 (affects only data value 0)
** ACACIA_STAIRS:0-3 (affects all data values from 0-3)
** ACACIA_STAIRS:0,1,2,3 (same as above, data values defined separately using commas)
** ACACIA_STAIRS:0-3,5,6 (Combine the two types for more customization
* Allow rails to attach to top of slabs, but not bottom.
* Allow rails to attach to top of stairs, but not bottom

#### Version 1.0.9 (8/11/2014)
* Updated Metrics to work with updated getOnlinePlayers
* Remove items from all non-creative GameModes and not just survival
* Added a warning for when older blocks are missing.
* Added null check for materials from default file.
* Log any materials that do not exist to the console.
* Added config.yml with configurable rotate tool
* New Permissions setup:
* railswitcher.switch: Sets all swap and rotate perms
*   railswitcher.swap: Allows swapping of rails.
*   railswitcher.rotate.tool: Allows rotating using the tool defined in config.yml
*   railswitcher.rotate.rail: Allows rotating using the same rail type.

#### Version 1.0.8 (1/17/2014)
* Fixed more power updating issues.
* Support for all protection and block logging plugins. (Hopefully)

#### Version 1.0.7 (1/12/2014)
* Swapping/Rotating rail will now update power correctly in most cases.

#### Version 1.0.6 (1/1/2014)
* Added 1.7 items to default block list (Backwards support for 1.6)

#### Version 1.0.5 (8/20/2013)
* Updated Metrics to R7
* Removed extra "[RailSwitcher]" being logged
* Added carpet to default block list
* Added CoreProtect and LogBlock support

#### Version 1.0.4 (4/13/2013)
* Removed extra TRAP_DOOR in default block list.
* Changed permission node "railswitcher.adjust" to "railswitcher.admin".
* Added "/rs" as an alias for "/railswitcher"
* Added "railswitcher perms" command. Op's and Admin's can use these to see the plugin's permissions.
* Added Towny support

#### Version 1.0.3 (3/17/2013)
* Added permission to plugin.yml
* Use plugin logger instead of Minecraft logger
* Added Activator Rails
* Updated placeable block list.
* Improved click placement when holding rails.
    * When using any rail, it now will get the block that you are LOOKING at.  This is not the same as the block that is TARGETED.
    * I still have no way to get the targeted block when using rails, but this is working much better than the previous method.
* Updated Metrics
* Moved defaultBlocks.txt and customBlocks.txt under the lists folder.
* Added "/railswitcher reload" to command to reload config files (currently just lists)
    * Can only use if you are an op or have permission "railswitcher.adjust"
* Since github dropped support for their version of downloads, I have set up a new folder structure under downloads:
    * release: always has the latest official version
    * dev: always has the most up to date version (could be the official or a development/test version)
    * old: storage of all old versions in case somebody wants to download an older version.

#### Version 1.0.2 (10/30/2012)
* Added WorldGuard support
* Adding Metrics support. To opt out, set "opt-out: true" in PluginMetrics/config.yml
* Removed the need for Vault, any permission will do.
* Partially fixed right clicking with rail to swap out rails (ex: holding a powered rail and swapping out a regular rail) or rotate them
    * Apparently it is a client bug that killed this functionality.  This helps, but will hit the closest one to you instead of the one you are aiming at.
    * For precision rotating, I suggest to stick with the shears. If swapping out rails, stand on the rail or to a side without any rails in the way.
* Added stairs, beacon, anvil, walls, and tripwire to default blocks list.

#### Version 1.0.1 (3/13/2012)
* Added default command /railswitcher
* Made Vault optional.  If not found, everyone can Switch rails.

#### Version 1.0 (3/2/2012)
* Initial release