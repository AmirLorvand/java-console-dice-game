# Java Console Dice Game

This project is a console-based dice game developed in **Java**. It was originally created as a university coursework project and demonstrates core programming concepts such as arrays, loops, conditionals, methods, user input handling, random number generation, and basic game state management.

The game allows two players to take turns rolling dice, selecting scoring categories, and building scores over multiple rounds.

---

## Project Timeline

* **Originally completed:** 2024
* **Refactored and published on GitHub:** 2026
* **Context:** Java programming coursework project

This repository has been cleaned and documented for portfolio purposes.

---

## Project Status

This is an early-stage coursework project. The core dice rolling, scoring, category selection, and two-player turn structure are implemented, but the project is not presented as a fully polished production game.

It is included in this portfolio to show my progression in Java programming, console-based application design, and basic game logic.

---

## Features

* Two-player console-based gameplay
* Dice rolling using Java’s `Random` class
* Turn-based structure
* Score table for both players
* Multiple scoring categories:

  * Ones
  * Twos
  * Threes
  * Fours
  * Fives
  * Sixes
  * Sequence
* Category selection by user input
* Basic winner calculation
* Console score display after rounds

---

## Technologies Used

* Java
* Console input/output
* Arrays
* Methods
* Random number generation
* Basic game logic
* Makefile

---

## Project Structure

```text
.
├── src/
│   └── dice/
│       └── game/
│           └── DiceGame.java
├── Makefile
├── .gitignore
└── README.md
```

---

## How to Compile

Use the Makefile:

```bash
make
```

This compiles the Java source code into the `out/` directory.

---

## How to Run

```bash
make run
```

Or run manually after compilation:

```bash
java -cp out dice.game.DiceGame
```

---

## How to Clean Build Files

```bash
make clean
```

This removes the generated `out/` directory.

---

## Example Gameplay Flow

The game runs in the terminal. During each turn, the player can roll dice, select a scoring category, and continue or forfeit the turn.

Example categories include:

```text
Ones
Twos
Threes
Fours
Fives
Sixes
Sequence
```

At the end of the game, the program calculates the total score and announces the winning player.

---

## What I Learned

Through this coursework project, I practised:

* Writing Java methods to separate program logic
* Using arrays to store players, categories, dice values, and scores
* Handling user input through `Scanner`
* Generating random dice rolls
* Implementing basic scoring logic
* Managing turn-based game flow
* Displaying formatted output in the console
* Preparing an older coursework project for GitHub presentation

---

## Future Improvements

Possible improvements include:

* Improving input validation to handle invalid user entries safely
* Refactoring the program into multiple classes such as `Player`, `Dice`, `ScoreTable`, and `Game`
* Adding unit tests
* Improving the sequence scoring logic
* Adding clearer instructions during gameplay
* Handling draw/tie situations between players
* Adding a graphical user interface
* Saving game results to a file

---

## Author

**Amir Lorvand**

Computer Science graduate with an interest in software development, artificial intelligence, machine learning, and data-driven systems.
