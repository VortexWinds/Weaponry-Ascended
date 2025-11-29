# Weaponry Ascended

A comprehensive Minecraft mod for version 1.21.1 using NeoForge that introduces customizable weapons with unique traits based on the materials used to craft them.

## Overview

Weaponry Ascended enhances Minecraft's combat and crafting systems by adding:
- **New weapon varieties** beyond standard Minecraft tools
- **Customizable weapon system** where each piece can be modified with different items
- **Unique traits system** based on item combinations used in construction
- **Enhanced combat mechanics** with special effects and abilities

## Features

### 🔧 Weapon Customization System
- **Modular Weapons**: Each weapon can be customized with different components
- **Trait Application**: Apply special traits to weapons using specific materials
- **Strength Levels**: Traits can be enhanced to increase their effectiveness
- **Combination Mechanics**: Combine multiple traits for unique weapon builds

### 🎯 Weapon Traits
- **Fire Trait**: Adds fire damage and burning effects (Blaze Powder, Flint & Steel)
- **Frost Trait**: Adds cold damage and slows enemies (Ice, Snow Blocks)
- **Sharpness Trait**: Increases weapon damage (Sharpening Stones)
- **Speed Trait**: Increases attack speed (Lightweight Materials)
- **Durability Trait**: Reduces wear and increases longevity (Obsidian, Reinforced Components)
- **Toxic Trait**: Adds poison effects (Poisonous Materials)
- **Explosion Trait**: Adds explosive damage and knockback (TNT, Gunpowder)

### ⚒️ Crafting Stations
- **Customization Station**: For applying traits to weapons
- **Weapon Forge**: For crafting enhanced weapons
- **Material Processor**: For creating trait essence and components

### 📊 Configuration Options
- Enable/disable specific weapon systems
- Adjust maximum traits per weapon
- Configure trait strength multipliers
- Set crafting times and requirements

## Installation

### For Players
1. Install Minecraft 1.21.1
2. Download and install NeoForge for 1.21.1
3. Download the latest Weaponry Ascended mod JAR file
4. Place the JAR file in your `.minecraft/mods/` folder

### For Developers
1. Clone this repository
2. Run `./gradlew build` to compile the mod
3. Use `./gradlew runClient` to launch the development environment

## Development Structure

```
src/main/java/com/vortexwinds/weaponryascended/
├── WeaponryAscended.java          # Main mod class
├── Config.java                    # Configuration handling
├── items/
│   └── CustomSwordItem.java       # Base weapon implementation
├── weapons/
│   └── BaseWeaponItem.java        # Core weapon system
├── traits/
│   ├── WeaponTrait.java           # Base trait system
│   └── ConcreteTraits.java        # Specific trait implementations
└── registry/
    └── RegistrationHandler.java   # Item/block registration

src/main/resources/
├── META-INF/
│   └── mods.toml                  # Mod metadata
├── assets/weaponryascended/
│   ├── lang/
│   │   └── en_us.json            # Language file
│   ├── models/item/              # Item models
│   ├── textures/item/            # Item textures
│   └── sounds/                   # Sound effects
└── data/weaponryascended/
    ├── recipes/                   # Crafting recipes
    └── tags/items/                # Item tags
```

## Key Classes

### BaseWeaponItem
The foundation class for all custom weapons in the mod. Provides:
- Weapon trait management through NBT data
- Damage and speed modification system
- Trait application and effect handling
- Integration with Minecraft's item system

### WeaponTrait
Abstract base class for all weapon traits. Each trait:
- Has a unique ID, name, and description
- Can be applied at different strength levels
- Provides specific modifiers (damage, speed, durability)
- Can have special effects on hit
- May have compatibility restrictions with other traits

### RegistrationHandler
Manages all item, block, and creative tab registrations using NeoForge's Deferred Register system.

## Configuration

The mod includes comprehensive configuration options accessible through the Minecraft options menu:

### Weapon Settings
- `enable_custom_weapons`: Toggle custom weapon system
- `max_weapon_traits`: Maximum traits per weapon (1-20)
- `trait_strength_multiplier`: Global trait effect multiplier (0.1-5.0)

### Customization Settings
- `enable_tool_customization`: Toggle customization system
- `max_customization_items`: Maximum customization components (1-10)

### Crafting Settings
- `enable_custom_crafting`: Toggle custom crafting
- `crafting_time_ticks`: Crafting duration in ticks (20-2000)

## Building and Testing

### Build Commands
```bash
# Build for distribution
./gradlew build

# Run client for testing
./gradlew runClient

# Run server for testing
./gradlew runServer

# Clean build artifacts
./gradlew clean
```

### Development Setup
1. The mod uses NeoForge 53.2.17 for Minecraft 1.21.1
2. Gradle 8.4 is required for building
3. Java 17+ is required for development
4. IntelliJ IDEA or Eclipse recommended for development

## Contributing

### Code Style
- Follow Minecraft/NeoForge naming conventions
- Use proper Java documentation
- Include configuration options for new features
- Add language entries for all new content

### Adding New Traits
1. Extend the `WeaponTrait` class
2. Implement required methods (`applyEffect`, `getDamageModifier`, etc.)
3. Register the trait in the trait registry
4. Add language entries for the trait
5. Create corresponding trait items/recipes

### Adding New Weapons
1. Extend `BaseWeaponItem` or `CustomSwordItem`
2. Register the weapon in `RegistrationHandler`
3. Add language entries and textures
4. Create appropriate crafting recipes

## Roadmap

### Version 1.1
- [ ] Additional weapon types (Axes, Pickaxes, Shovels)
- [ ] More trait combinations and effects
- [ ] Customization GUI interface
- [ ] Weapon repair and maintenance system

### Version 1.2
- [ ] Advanced crafting stations
- [ ] Trait extraction and transfer
- [ ] Weapon sets and bonuses
- [ ] Integration with other combat mods

### Version 1.3
- [ ] Custom weapon models and animations
- [ ] Advanced trait interaction system
- [ ] Weapon progression and evolution
- [ ] Multiplayer synchronization

## License

This mod is licensed under the MIT License. See the LICENSE file for details.

## Support

For bug reports, feature requests, or general discussion:
- GitHub Issues: [Submit issues here](https://github.com/VortexWinds/Weaponry-Ascended/issues)
- Discord: [Join our community server]

## Credits

- **Lead Developer**: VortexWinds
- **Technical Support**: MiniMax Agent
- **Special Thanks**: NeoForge team, Minecraft modding community

---

**Note**: This mod is currently in early development. Features and APIs may change between versions. Beta testing and feedback are welcome!