[README.md](https://github.com/user-attachments/files/33880764/README.md)
# Human vs Monsters Arena

A turn-based battle game built with **Java Swing**. Two players chooses a human character and a monster, then takes turns attacking until one character's health reaches zero.

## Features

- **Graphical user interface (GUI):** Built with Java Swing.
- **Character selection:** Choose a name and class for both the human and the monster.
- **Health bars:** Displays each character's current HP out of 100.
- **Turn-based combat:** The human acts first, followed by the monster, alternating turns.
- **Basic attacks:** Each character class has its own attack and damage value.
- **Special attacks:** Special attacks become available after accumulating 3 basic-attack stacks.
- **Battle log:** Displays attacks, damage, round changes, and the winner.
- **Victory detection:** The battle ends when either character is defeated.
- **Replay support:** Press **Start** to initialize a new match.

## Character Classes

Every character starts with **100 HP**.

### Human Classes

| Class | Basic Attack | Basic Damage | Special Attack | Special Damage |
|---|---|---:|---|---:|
| Warrior | Mighty Sword | 10 | Shield Bash | 25 |
| Mage | Fireball | 12 | Lightning Storm | 30 |
| Archer | Quick Shot | 9 | Piercing Arrow | 28 |

### Monster Classes

| Class | Basic Attack | Basic Damage | Special Attack | Special Damage |
|---|---|---:|---|---:|
| Siren | Vicious Scratch | 8 | Siren Song | 26 |
| Werewolf | Aggressive Bite | 11 | Full Moon Rage | 27 |
| Slime | Slam | 7 | Acidic Sludge | 24 |

## Battle Rules

1. Enter a name for the human and the monster.
2. Select one human class and one monster class.
3. Click **Start** to begin the match. The human takes the first turn.
4. On a character's turn, choose **Basic Attack** or an available **Special Attack**.
5. Each basic attack adds 1 stack to that character's special charge.
6. A special attack requires at least 3 stacks and consumes 3 stacks when used.
7. Characters take turns until one reaches 0 HP.
8. The remaining character wins the match.

> **Note:** The GUI disables a character's buttons when it is not their turn and disables the Special Attack button until they have enough stacks.

## Data Structures and Design

This project demonstrates object-oriented programming and queue-based Abstract Data Types (ADTs).

- **`Queue<GameCharacter> turnQueue`** — In `BattleSystem`, keeps track of whose turn it is. The character at the front acts next; after an action, that character moves to the back.
- **`Queue<BattleAction> actionQueue`** — In `BattleSystem`, stores actions waiting to be executed. The current GUI flow enqueues one action and processes the queue immediately.
- **`GameCharacter`** — Abstract base class containing shared character state and behavior, including name, health, damage handling, and special-charge tracking.
- **Inheritance and polymorphism** — `Warrior`, `Mage`, `Archer`, `Siren`, `Werewolf`, and `Slime` implement their own basic and special attacks.
- **`BattleAction`** — Represents and executes one basic or special attack.
- **`GUI`** — Builds the Swing interface, handles user input, and refreshes the displayed game state.

Both queues are backed by Java's `LinkedList` implementation.

## Requirements

- Java Development Kit (**JDK 8 or later**)
- An IDE such as IntelliJ IDEA, Eclipse, or NetBeans, or a terminal with Java tools installed

The program uses standard Java libraries; no external dependencies are required.

## How to Run

### Option 1: Using an IDE

1. Create or open a Java project.
2. Create the package `GameCharacter`.
3. Put the supplied code in a file named `Main.java` inside that package.
4. Make sure the file begins with:

   ```java
   package GameCharacter;
   ```

5. Run the `Main` class.

### Option 2: Using the Terminal

From the directory containing the `GameCharacter` folder, compile the source:

```bash
javac GameCharacter/Main.java
```

Then run the program:

```bash
java GameCharacter.Main
```

If your source file is in a different directory, adjust the commands to match your project structure.

## Project Structure

```text
ProjectFolder/
└── GameCharacter/
    └── Main.java
```

All classes are included in `Main.java` in the provided version. Only `Main` is declared `public`; the other classes use package-private visibility.
