# Java Tower Defense Game

A Java-based Tower Defense game developed as a university project during my second year of Bachelor's degree (L2). This game was created as part of the "Programmation Objet" (Object-Oriented Programming) course.

## Project Overview

This game is a simplified version of a classic tower defense game where players must strategically place various types of defensive towers to prevent enemies from reaching their castle. The game features:

- A grid-based map with defined paths for enemies
- Multiple types of towers with different abilities
- Various enemy types with different characteristics
- Resource management (gold) for building and upgrading towers
- Wave-based enemy spawning system

## Features

### Tower Types
- **Archer Tower**: Basic tower with medium range and damage
- **Cannon Tower**: Deals area damage with explosive projectiles
- **Freeze Tower**: Slows down enemies with ice projectiles
- **Poison Tower**: Damages enemies over time with poison effects

### Enemy Types
- **Basic Monsters**: Standard enemies with balanced stats
- **Fast Monsters**: Quicker but weaker enemies
- **Boss Monsters**: Powerful enemies with high health

### Gameplay Mechanics
- Strategic tower placement on a fixed map
- Tower upgrading system
- Resource management (gold earned from defeating enemies)
- Wave-based progression
- Win/loss conditions based on castle health

## Technologies Used

- **Language**: Java
- **Graphics**: StdDraw library for rendering
- **Architecture**: Object-oriented design with inheritance and polymorphism

## Project Structure

- `src/`: Source code with the game implementation
  - `Monster.java`: Abstract class for enemy units
  - `Tower.java`: Abstract class for defensive structures
  - `Projectile.java`: Base class for tower projectiles
  - `World.java`: Main game logic and state management
  - Various specialized implementations of monsters and towers
- `images/`: Game assets (sprites for towers, enemies, projectiles, and terrain)
- `bin/`: Compiled class files

## How to Run

1. Ensure you have Java installed on your system
2. Navigate to the project directory
3. Compile the Java files if needed
4. Run the Boot class with:
   ```
   java -cp bin warcraftTD.Boot
   ```

## Learning Outcomes

This project demonstrates proficiency in:
- Object-oriented programming principles
- Inheritance and polymorphism
- Game development concepts (game loop, collision detection)
- Entity management and interactions
- Basic graphics rendering

## Original Documentation

The original README (in French) is preserved as README.md.fr for historical reference.

---

*Note: This project was developed for educational purposes during my university studies and showcases my early programming skills and understanding of object-oriented concepts.*
