# Final Destination Group Project

Main Contributors:
Jahren Ramborger, Myroon Galil

## Overview
This project is a grid-based zombie simulation written in Java.  
The simulation models interactions between humans, zombies, soldiers, and generals on a tile-based map.

The system demonstrates object-oriented design, simulation logic, and GUI rendering using Java Swing.

---

## Features
- Tile-based map system:
  - Grass
  - Water
  - Safe Zone
  - Wall
  - Deep Water
- Multiple unit types:
    - Humans
    - Zombies
    - Soldiers
    - Generals
- Movement behaviors:
    - Random wandering
    - Target chasing
- Collision and interaction system
- Real-time GUI visualization
- Custom map loading from `.txt` files

---
## Entity Behavior Rules

Each entity type follows specific movement and interaction rules within the simulation:

### Humans
- Move randomly using a wandering behavior
- Avoid obstacles such as walls and deep water
- Can be converted into zombies upon contact with a zombie

### Zombies
- Actively seek the nearest target:
    - Humans
    - Soldiers
    - Generals
- Move toward targets using directional chasing logic
- Convert humans, soldiers, and generals into zombies upon contact
- Can drown when standing in water tiles

### Soldiers
- Hunt zombies by targeting the nearest one
- Move using chasing behavior similar to zombies
- Eliminate zombies upon contact instead of being converted
- Can move through water tiles

### Generals
- Seek out humans and move toward them
- Function as a higher-level human unit
- Can be converted into zombies if caught
- Can move through water tiles

---

### Movement Rules
- All entities:
    - Cannot move outside the map boundaries
    - Cannot enter blocked tiles (e.g., walls, deep water)
    - Cannot move into occupied tiles

- Movement is processed in steps:
    - Entities choose a direction
    - The simulation checks if the move is valid (`canEnter`)
    - If valid → move occurs
    - If invalid → entity stays or chooses a new direction

---

### Tile Interaction Rules
- **Grass (`.`)**: Normal movement allowed
- **Water (`A`)**:
    - All entities can enter
    - Zombies will drown if they remain in water
- **Deep Water (`D`)**:
    - No entity can enter
- **Wall (`W`)**:
    - Completely blocks movement
- **Safe Zone (`C`)**:
    - Blocks zombies
    - Allows humans, soldiers, and generals

---
## How to Run

1. Run the program
2. The MiniGUI will appear:
    - Choose a map file from the `maps` file OR
    - Enter simulation values manually for randomized spawns

3. The simulation window will open when clicking submit

---

## Making Map File Format

It must be made in square format, no blank space. With characters representing either entities
  or tiles. Tile is defaulted to grass for any position where entity spawns

Characters for Tiles

| Symbol | Tile Type      |
|--------|----------------|
| `.`    | Grass          |
| `A`    | Water          |
| `D`    | Deep Water     |
| `W`    | Wall           |
| `C`    | Safe Zone      |

Character for Entities

| Symbol | Entity         |
|--------|----------------|
| `H`    | Human          |
| `Z`    | Zombie         |
| `S`    | Soldier        |
| `G`    | General        |

---
