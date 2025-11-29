# Weaponry-Ascended

Weaponry-Ascended is a Minecraft mod for Minecraft version 1.21.1 on the latest Neoforge. This mod adds new varieties of weapons in addition to the normal weapons and tools in Minecraft and allows you to customize each piece of the tool with different types of items to give it unique traits based on what items are used to build it.

## New Weapons Added

This update significantly expands the weapon arsenal with a comprehensive collection of medieval and fantasy weapons:

### Two-Handed Weapons (Offhand Slot Disabled)

#### Greatsword
- **Damage:** 9.0
- **Speed:** -3.0
- **Features:** Extended reach, sweeping attacks, heavy durability consumption
- **Special:** Can hit multiple enemies in a cone

#### Scythe
- **Damage:** 7.0
- **Speed:** -2.8
- **Features:** Excellent against plants/wood, bleeding effects
- **Special:** Bonus damage to organic entities

#### Great Hammer
- **Damage:** 11.0
- **Speed:** -3.5
- **Features:** Massive impact damage, strong knockback
- **Special:** Effective against stone and ore blocks

#### Morningstar
- **Damage:** 10.0
- **Speed:** -3.2
- **Features:** Armor penetration, stagger effects
- **Special:** Critical hits against heavily armored targets

#### Battle Axe
- **Damage:** 8.0
- **Speed:** -3.1
- **Features:** Excellent wood cutting, shield breaking
- **Special:** Double speed on wood materials

#### Bow
- **Type:** Ranged weapon
- **Draw Speed:** 30 ticks
- **Features:** Standard bow functionality with trait support
- **Special:** Can be enhanced with weapon traits

#### Crossbow
- **Type:** Ranged weapon
- **Draw Speed:** 40 ticks
- **Features:** Higher damage and velocity than bow
- **Special:** Can fire bolts or arrows

#### Staff
- **Damage:** 5.0
- **Speed:** -2.0
- **Features:** Magic-based attacks, long reach
- **Special:** Supports different magic types (lightning, frost, fire, arcane)

### One-Handed Weapons (Can be Dual-Wielded or Used with Offhand)

#### War Glaive
- **Damage:** 7.5
- **Speed:** -2.6
- **Features:** Disarming attacks, armor cutting
- **Special:** 40% chance to disarm opponents

#### Hammer
- **Damage:** 8.5
- **Speed:** -2.8
- **Features:** Single-target devastation, stagger effects
- **Special:** Extra damage against armored targets

#### Mace
- **Damage:** 7.0
- **Speed:** -2.5
- **Features:** Armor breaking, shield destruction
- **Special:** 30% chance to break shields

#### Axe
- **Damage:** 6.5
- **Speed:** -2.3
- **Features:** Wood cutting specialist
- **Special:** 50% faster on wood materials

#### Dagger
- **Damage:** 3.5
- **Speed:** -1.6
- **Features:** High critical hit chance, backstab bonus
- **Special:** 40% crit chance, 1.5x damage from behind

#### Throwing Weapon
- **Damage:** 4.0
- **Speed:** -1.5
- **Features:** Can be thrown, retrievable variant available
- **Special:** Different behavior when thrown vs melee

#### Wand
- **Damage:** 4.0
- **Speed:** -1.8
- **Features:** Magic precision, various spell types
- **Special:** Supports healing, shadow, earth, and wind magic

### Defensive Items (Offhand Only)

#### Tome
- **Defense:** 2.0
- **Features:** Magical defensive spells
- **Special Types:** Healing, protection, mana, clarity magic
- **Uses:** Offhand spellcasting

#### Shield
- **Defense:** 5.0
- **Durability:** 200
- **Features:** Standard blocking with enhanced properties
- **Special:** Can be fortified or reflective

#### Relic
- **Defense:** 3.0
- **Features:** Powerful magical abilities
- **Special Types:** Life steal, warding, fury, wisdom
- **Activation:**消耗Durability for powerful effects

#### Totem
- **Defense:** 4.0
- **Durability:** 50
- **Features:** Area effect abilities, cooldown-based
- **Special Types:** Life, death, protection, warrior buffs
- **Activation:** 30-second cooldown

## Features

### Weapon Customization System
- Each weapon can be customized with up to 20 different traits
- Traits affect weapon damage, speed, special abilities, and appearance
- Material-based trait system with 7 core traits:
  - Fire: Sets targets on fire
  - Frost: Applies slowness effects
  - Sharpness: Increases attack damage
  - Speed: Increases attack speed
  - Durability: Reduces durability loss
  - Toxic: Applies poison effects
  - Explosion: Creates explosions on hit

### Two-Handed vs One-Handed Mechanics
- **Two-handed weapons:** Disable offhand slot, provide increased damage and reach
- **One-handed weapons:** Allow dual-wielding or offhand defensive items
- **Ranged weapons:** Require ammunition, support traits

### Defensive Item System
- **Offhand restriction:** All defensive items can only be used in offhand
- **Active abilities:** Some items have activated abilities (relics, totems)
- **Passive effects:** Other items provide constant defensive bonuses

## Installation

1. Ensure you have Minecraft 1.21.1 installed
2. Install NeoForge version 53.2.17 or later
3. Download the latest version of Weaponry-Ascended
4. Place the mod file in your `.minecraft/mods` directory
5. Launch Minecraft with the configured profile

## Configuration

The mod includes comprehensive configuration options:
- Enable/disable specific weapon types
- Adjust maximum trait limits
- Modify crafting times and costs
- Configure weapon damage and speed multipliers

## Development Setup

1. Clone the repository
2. Import the project into your IDE
3. Run `./gradlew runClient` to launch the development environment
4. The mod will be loaded in a test instance of Minecraft

## Contributing

We welcome contributions! Please read our contributing guidelines and submit pull requests for any new features, bug fixes, or improvements.

## License

This mod is released under the MIT License.

## Changelog

### Version 1.1.0
- Added 21 new weapon types
- Implemented two-handed weapon mechanics
- Added defensive item system with 4 item types
- Expanded trait system support
- Added ranged weapon support (bows and crossbows)
- Implemented magical weapon types (staves and wands)
- Added weapon throwing mechanics
- Enhanced creative tab organization

### Version 1.0.0
- Initial release
- Basic weapon customization system
- 7 core weapon traits
- Configuration framework
- Creative tab integration