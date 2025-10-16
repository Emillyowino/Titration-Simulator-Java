## Author
Emilly Owino

# Java Acid–Base Titration Simulator

Simulates an acid-base titration in Java, calculates pH at each step, detects the equivalence point, and graphs the titration curve using Swing.

## Features
- Takes acid/base concentrations and volumes as input.
- Calculates moles and pH for each addition.
- Detects and prints the equivalence point.
- Displays a live titration curve graph.

## Files
- `Chemistry.java` → Main program, handles user input.
- `TitrationSimulator.java` → Performs calculations of moles, pH, and equivalence point.
- `TitrationGraph.java` → Generates the graph of pH vs. volume using Swing.

## Screenshot
![Titration Curve Screenshot](graph_screenshot.png)

## How to Run
```bash
javac src/*.java
java src/Chemistry
