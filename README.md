# BossBar Plugin for Folia 1.21.8

A customizable boss bar plugin for Minecraft servers running Folia 1.21.8. This plugin allows server administrators to create and manage multiple boss bars with custom titles, colors (including hex colors), overlays, and visibility settings.

## Features

- Create multiple custom boss bars
- Support for standard Minecraft boss bar colors (RED, BLUE, GREEN, YELLOW, PURPLE, PINK, WHITE)
- TOML configuration format for easy editing
- MiniMessage support for rich text formatting
- `/mbossbar reload` command to reload configuration without restarting the server
- Folia-compatible (multi-threaded server implementation)

## Installation

1. Download the compiled JAR file or build from source
2. Place the JAR file in your server's `plugins` folder
3. Restart the server to generate the default configuration
4. Edit `plugins/BossBar/settings.toml` to customize your boss bars
5. Use `/mbossbar reload` to apply configuration changes

## Configuration

The plugin uses a TOML configuration file located at `plugins/BossBar/settings.toml`. Here's an example configuration:

```toml
# BossBar Settings File
# Using TOML format

[bossbars]
  [bossbars.example]
  title = "<rainbow>Example Boss Bar</rainbow>"
  color = "RED"
  overlay = "PROGRESS" # PROGRESS, NOTCHED_6, NOTCHED_10, NOTCHED_12, NOTCHED_20
  progress = 1.0
  visible = true
  # Players who should see this boss bar
  # Leave empty for all players, or specify player names
  players = []
  
  [bossbars.welcome]
  title = "<gradient:#FFFF00:#FFAA00>Welcome to the Server!</gradient>"
  color = "GREEN"
  overlay = "NOTCHED_20"
  progress = 0.75
  visible = true
  players = []
```

### Configuration Options

- `title`: The text displayed on the boss bar. Supports MiniMessage formatting.
- `color`: The color of the boss bar. Can be one of the standard Minecraft boss bar colors:
  - RED
  - BLUE
  - GREEN
  - YELLOW
  - PURPLE
  - PINK
  - WHITE
- `overlay`: The style of the boss bar. Options are:
  - PROGRESS
  - NOTCHED_6
  - NOTCHED_10
  - NOTCHED_12
  - NOTCHED_20
- `progress`: The fill percentage of the boss bar (0.0 to 1.0).
- `visible`: Whether the boss bar is initially visible.
- `players`: A list of player names who should see this boss bar. Leave empty for all players.

## Commands

- `/mbossbar reload` - Reloads the configuration file

## Permissions

- `bossbar.admin` - Allows use of the `/mbossbar` command (default: OPs)

## Building from Source

1. Clone the repository
2. Run `mvn clean package` to build the JAR file
3. The compiled JAR will be located in the `target` directory

## MiniMessage Formatting

The plugin supports MiniMessage formatting in boss bar titles. Some examples:

- `<red>Red Text</red>`
- `<rainbow>Rainbow Text</rainbow>`
- `<gradient:#FF0000:#00FF00>Gradient Text</gradient>`
- `<bold>Bold Text</bold>`

See the [MiniMessage documentation](https://docs.adventure.kyori.net/minimessage/format.html) for more formatting options.