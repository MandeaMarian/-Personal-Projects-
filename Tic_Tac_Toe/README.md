# Tic-Tac-Toe

![Tic-Tac-Toe Demo](https://i.imgur.com/your-image.gif) This project is a Java-based Tic-Tac-Toe game featuring a clean graphical interface and multiple AI opponents with varying levels of difficulty. It serves as a practical demonstration of object-oriented programming, game development principles, and AI decision-making algorithms.

## Table of Contents
1.  [Project Overview](#project-overview)
2.  [Game Features](#game-features)
3.  [Game Modes](#game-modes)
    -   [Play with Friend](#play-with-friend)
    -   [Easy Bot](#easy-bot)
    -   [Hard Bot](#hard-bot)
    -   [Impossible Bot](#impossible-bot)
4.  [AI Algorithms](#ai-algorithms)
    -   [RandomBot](#randombot)
    -   [SmartBot](#smartbot)
    -   [ImpossibleBot](#impossiblebot)
5.  [Technical Implementation](#technical-implementation)
6.  [How to Run](#how-to-run)

---

## Project Overview
This Java implementation of Tic-Tac-Toe features multiple game modes and AI opponents with varying difficulty levels. The project demonstrates object-oriented programming principles and AI decision-making algorithms in game development.

---

## Game Features
-   **Modern graphical interface** with Java Swing
-   **Four distinct game modes**
-   **Three AI difficulty levels**
-   **Responsive design** that works on different screen sizes
-   **Game rules and instructions**
-   **Visual feedback** for wins and draws

---

## Game Modes

### Play with Friend
-   Classic two-player mode.
-   Players alternate turns between **X** and **O**.
-   First to get 3 in a row wins.
-   Visual indicators for winning moves.
-   Yellow background for draws.

### Easy Bot
-   **Opponent:** `RandomBot`
-   Makes completely random valid moves.
-   Perfect for beginners learning the game.
-   No strategic decision-making.
-   Wins only if the player makes significant mistakes.

### Hard Bot
-   **Opponent:** `SmartBot`
-   Implements basic game strategy:
    -   Wins when possible.
    -   Blocks opponent wins.
    -   Prefers center and corners.
-   Provides a moderate challenge.
-   Beatable with optimal play.

### Impossible Bot
-   **Opponent:** `ImpossibleBot`
-   Uses a perfect Tic-Tac-Toe strategy:
    -   **Never loses** (either wins or forces a draw).
    -   Creates forks (multiple winning opportunities).
    -   Blocks opponent forks.
    -   Implements opening book moves.
-   Maximum difficulty level.
-   Requires perfect play to achieve a draw.

---

## AI Algorithms

### RandomBot
**Strategy:** Pure randomness.

**Behavior:**
-   Compiles a list of empty cells.
-   Selects one at random.
-   No consideration for the game state.
-   **Weakness:** Easily beaten.

```java
public int makeMove(JButton[] buttons) {
    ArrayList<Integer> emptyCells = new ArrayList<>();
    for (int i = 0; i < buttons.length; i++) {
        if (buttons[i].getText().isEmpty()) {
            emptyCells.add(i);
        }
    }
    return emptyCells.isEmpty() ? -1 : emptyCells.get(random.nextInt(emptyCells.size()));
}
```

###SmartBot
**Strategy:** Basic tactical play.

**Behavior:**
- Wins if possible.
- Blocks opponent wins.
- Takes the center if available.
- Makes a random move otherwise.
- Improvement: Adds basic strategy over RandomBot.

```java
public int makeMove(JButton[] buttons) {
    // 1. Check for win
    int winningMove = findWinningMove(buttons, "O");
    if (winningMove != -1) return winningMove;
    
    // 2. Block opponent
    int blockingMove = findWinningMove(buttons, "X");
    if (blockingMove != -1) return blockingMove;
    
    // 3. Take center
    if (buttons[4].getText().isEmpty()) return 4;
    
    // 4. Take random move
    ArrayList<Integer> emptyCells = new ArrayList<>();
    // ... random selection logic
}
```

###ImpossibleBot
**Strategy:** Perfect Tic-Tac-Toe algorithm (based on the Minimax algorithm principles).

**Key Features:**
- Fork creation and detection.
- Opening book moves.
- Forced draw prevention.
- Pattern recognition for optimal moves.

**Behavior:**
- Always wins if the opportunity exists.
- Forces a draw against an optimal player.
- Uses all known Tic-Tac-Toe strategies to remain unbeatable.

```java
public int makeMove(JButton[] buttons) {
    // 1. Check for immediate win
    int winningMove = findWinningMove(buttons, "O");
    if (winningMove != -1) return winningMove;

    // 2. Block opponent's win
    int blockingMove = findWinningMove(buttons, "X");
    if (blockingMove != -1) return blockingMove;

    // 3. Create fork opportunities
    int forkMove = createFork(buttons);
    if (forkMove != -1) return forkMove;

    // 4. Block opponent's fork
    int blockForkMove = blockPotentialFork(buttons);
    if (blockForkMove != -1) return blockForkMove;

    // 5. Take center
    if (buttons[4].getText().isEmpty()) return 4;

    // 6. Take opposite corner
    int oppositeCorner = takeOppositeCorner(buttons);
    if (oppositeCorner != -1) return oppositeCorner;

    // 7. Take empty corner
    int oppositeCorner = takeOppositeCorner(buttons);
    if (oppositeCorner != -1) return oppositeCorner;

    // 8. Take empty side
    return getEmptySide(buttons)

}
```

---

## Technical Implementation

**Class Structure**
- Main.java: Entry point for the application.
- MainMenu.java: Handles the main menu and game mode selection.
- GameFrame.java: Manages the game interface and flow.
- BotStrategy.java: Interface for all AI implementations.
- RandomBot.java, SmartBot.java, ImpossibleBot.java: Concrete AI implementations.

**Key Methods**
- initializeGameButtons(): Sets up the 3x3 grid.
- checkWin(): Determines if a player has won.
- resetGame(): Handles game restarts.
- makeMove() (in bot classes): Contains the AI decision logic.

---

## How to Run
1. Ensure Java JDK is installed on your system (JDK 8 or later).

2. Clone the repository:
```bash
git clone [https://github.com/MandeaMarian/-Personal-Projects-.git](https://github.com/MandeaMarian/-Personal-Projects-)
```
3. Navigate to the project directory:
```bash
cd your-repo
```
4. Compile all .java files:
```bash
javac *.java
```
5. Run the main class:
```bash
java Main
```
**Controls**
- Click any empty cell to make a move.
- Click "Play Again" to reset the current game.
- Click "Exit to Menu" to return to the mode selection screen.

**Dependencies**
- Java SE Development Kit (JDK) 8 or later.
- No external libraries required.

This project demonstrates fundamental AI concepts and provides an engaging way to explore game theory and decision-making algorithms in a classic game setting.
