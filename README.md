# Dungeon Crawl (sprint 1)

## Story

[Roguelikes](https://en.wikipedia.org/wiki/Roguelike) are one of the oldest
types of video games, the earliest ones were made in the 70s, they were inspired
a lot by tabletop RPGs. Roguelikes have the following in common usually:

- They are tile-based.
- The game is divided into turns, e.g. you make one action, then the other
  entities (monsters, allies, etc. controlled by the CPU) make one.
- Usually the task is to explore a labyrinth and retrieve some treasure from its
  bottom.
- They feature permadeath: if you die its game over, you need to start from the
  beginning again.
- Are heavily using procedural generation: Levels, monster placement, items,..
  are randomized, so the game does not get boring.

Your task will be to create a roguelike! You can deviate from the rules above,
the important bit is that it should be fun!

## What are you going to learn?

- Get more practice in OOP
- Design patterns: layer separation (All of the game logic, i.e., player
  movement, game rules, and so on), is in the `logic` package, completely
  independent of user interface code. In principle, you could implement a
  completely different interface e.g. terminal, web, Virtual Reality, etc. for
  the same logic code.)

## Tasks

1. Understand the existing code, classes and tests so you can make changes. You should do this before planning everything else. It will help you understand what is going on.
    - Student has a class diagram in a digialized format which 
- contains enums, classes, interfaces with all fields, methods
- show connections between classes: inheritance, aggregation, composition
- show multiplicity of connections (1..1, 1..*, *..*)

2. Create a game logic which restricts the movement of the player so s/he cannot run into walls and monsters.
    - The hero is not able to move into walls.
    - The hero is not able to move into monsters.

3. There are items lying around the dungeon. They are visible in the GUI.
    - There are at least 2 item types, for instance a key, and a sword.
    - There can be one item in a map square.
    - A player can stand on the same square as an item.
    - The item has to be displayed on screen (unless somebody stands on the same square)

4. Create a feature that allows the hero to pick up an item.
    - There is a "Pick up" button on the right side of screen.
    - After the player clicks the button, the item the hero is standing on should be gone from map.

5. Show picked up items in the inventory list.
    - There is an `Inventory` list on the screen.
    - After the hero picks up an item, it should appear in inventory.

6. Make the hero to able to attack monsters by moving into them.
    - Attacking a monster removes 5 health points. If health of a monster goes below 0, it dies and disappears.
    - Create a feature where the hero attack a monster, and it doesn't die, it also attacks the hore at the same time (but is a bit weaker, and only removes 2 health).
    - Having a weapon should increase your attack strength.
    - Different monsters have different health and attack strength.

7. Create doors in the dungeon that open by using keys.
    - There are two new square types, closed door, and open door.
    - The hero cannot pass through a closed door, unless it has a key in his/her inventory. Then it becomes an open door.

8. Create three different monster types with different behaviors.
    - There are at least three different monster types with different behaviors.
    - One type of monster does not move at all.
    - One type of monster moves randomly. It cannot go trough walls or open doors.

9. [OPTIONAL] Create a more sophisticated movement AI.
    - One type of monster moves around randomly and teleports to a random free square every few turns.
    - A custom movement logic is implemented (like Ghosts that can move trough walls, monster that chases the player, etc.)

10. Create maps that have more varied scenery. Take a look at the tile sheet (tiles.png). Get inspired!
    - At least three more tiles are used. These can be more monsters, items, or background. At least one of them has to be not purely decorative, but have some effect on gameplay.

11. [OPTIONAL] Allow the player to set a name for my character. This name will also function as a cheat code!
    - There is a `Name` label and text field on the screen.
    - If the name given is one of the game developers' name, the player can walk through walls.

12. [OPTIONAL] Make the game sound fun by implementing audio effects, when player or enemies do stuff
    - There is a footstep sound, that plays, whenever the player takes a step
    - There is an attack sound, whenever player and/or an enemy attacks someone This sound might vary depending on the weapon (sword, axe, arrow)
    - Enemies such as skeletons or ghosts play characteristic sounds randomly every few seconds
    - Add some background music to your game!

13. Add the possibility to add more levels.
    - There are at least two levels.
    - There is a square type "stairs down". Entering this square moves the player to a different map.

14. Implement bigger levels than the game window.
    - Levels are larger than the game window (for example 3 screens wide and 3 screens tall).
    - When the player moves the player character stays in the center of the view.
    
    ## General requirements
    
    None
    
    ## Hints
    
    - Start with the smaller tasks, and then move into the more difficult ones
    - Before making any changes make sure you understand the whole starting code
    - Open the project in IntelliJ IDEA. This is a Maven project, so you will need to
    open `pom.xml`. The project is using JavaFX, use the `javafx` maven plugin to
    build and run the program. Build: `mvn javafx:compile`, run: `mvn javafx:run`.
    - You don't need to dwelve into JavaFX's technicalities much, most of the GUI is ready
    
    ## Background materials
    
    - <i class="far fa-book-open"></i> [RogueBasin, a wiki with lots of resources on Roguelike creation](http://roguebasin.com/index.php?title=Articles)
    - <i class="far fa-exclamation"></i> [Basics of OOP](project/curriculum/materials/pages/oop/basics-of-object-oriented-programming.md)
    - <i class="far fa-exclamation"></i> [UML diagrams](project/curriculum/materials/pages/general/uml-unified-modeling-language.md)
    - <i class="far fa-exclamation"></i> [How to design classes](project/curriculum/materials/pages/java/how-to-design-classes.md)
    - <i class="far fa-book-open"></i> [JavaFX](https://en.wikipedia.org/wiki/JavaFX)
    - <i class="far fa-book-open"></i> [JavaFX Tutorial](http://tutorials.jenkov.com/javafx/index.html)


# Dungeon Crawl (sprint 2)

## Story

Last week you created a pretty good [Roguelike](https://en.wikipedia.org/wiki/Roguelike) game. It already has some features but the players have no opportunity to save their games. It can be annoying especially when you have to leave the game aside suddenly.

The gamer community bragging for saving functionality and some other new interesting ideas like:

- sharing game with each other
- maps of different sizes
- player tracking camera movement

The management is handing out a **prioritized list** of new user stories that should be appended to the unfinished stories from last week in your product backlog. Try to estimate these new stories as well, and based on the estimations pick the stories your team can finish in this sprint.

> Using database for saving game state feature is a business critical item which overrides every other priority now!

Let's continue this entertaining project, and make our players happier!

## What are you going to learn?

- Serialization of objects
- Communicating with database
- Writing unit tests for your classes
- Design pattern: **Data Access Object**

## Tasks

1. Create a new sprint from the existing backlog. Last week you had a long list of stories, a few new stories this week.
    - The new items are added to the backlog
    - The team has created a new sprint plan based upon the unified backlog
    - The mandatory "Saving game" backlog item is in Sprint 2 and planned in detail

2. As you will work in a new repository but you need the code from the previous sprint, add the `dungeon-crawl-2` repository as a new remote to the previous sprint's repository, then pull (merge) and push your changes into it.
    - There is a merge commit in the project's repository that contains code from the previous sprint

3. Allow the user to save the current state of the game in a database. Extend the given schema if needed.
    - The application uses PostgreSQL database with the given schema: `schema_ddl.sql`
    - The application respects the `PSQL_USER_NAME`, `PSQL_PASSWORD`, `PSQL_DB_NAME` environment variables
    - Students has an Entity Relationship diagram (connections between classes, 1-1, 1-many...) in a digitalized format.
    - When the user hits `Ctrl+s`, a modal window pops up with one text input field (labelled `Name`) and two buttons, `Save` and `Cancel`.
- When the user clicks on `Save`, the game saves the current state (current map, player's position and content of inventory) in the database
  - If the given name is new then it saves the state
  - If the given username already exist in the db the system shows a dialogbox with a question: `Would you like to overwrite the already existing state?`
    - Choosing `Yes`: the already existing state is updated and all modal window closes
    - Choosing `No`: the dialog closes and the name input field content on the saving dialog is selected again
  - In case of clicking on `Cancel` the saving process terminates without any further action
- The modal window is automatically closed after the operation
    - Already discovered maps are also saved in DB.
    - There is a `Load` menu which brings up a modal window, showing the previously saved states with their names as a selectable list. Choosing an element loads the selected game state with the proper map, position and inventory.

4. Allow the user to export (serialize) his game state into a text file, and load (deserialize) the game from the exported file.
    - There is a menu item with a label `Export game` which triggers the export mechanism
    - The exporting process asks the user for the location and the name of the exported file. The file is created in the defined directory using the given name as a JSON file. eg. `<my-fantastic-game>.json`
    - The file stores every necessary game state information in JSON format.
    - There is a menu button labeled `Import` for importing a previously saved game.
- The system shows a file browser to select an exported file
  - If the chosen file isn't in proper format, the application shows a dialog window (OK, Cancel) with the following message: `IMPORT ERROR! Unfortunately the given file is in wrong format. Please try another one!`
    - If the user clicks on `OK` button then the window closes without any further action
    - If the user click on `Cancel` all dialog and modal window closes
  - In case the file is in the required format, the game loads the state, and navigates on the map to the point where the user left the game with its inventory

5. The customer seeks for quality assurance and wants to see that your code is covered by unit tests. It is important that beyond positive test cases also cover negative scenarios.
    - Every unit test method is well arranged and follows the `arrange`-`act`-`assert` structure
    - Unit test classes and methods follow these naming conventions consistently:
- classes: `<The name of the tested class>Test`
- methods: `<the name of the tested method>_<expected input / tested state>_<expected behavior>`
    - Every test class has at least one negative test case (and more if it's plausible)
    - Code coverage of self-created business logic classes is above 90%

## General requirements

None

## Hints

- Break the backlog items into smaller tasks so that you can work in parallel
- The given DB schema is only an example. Probably you need to alter is according to the requirements. For instance it doesn't contain any info about inventory or discovered maps by the player
- Write as many unit tests as possible to cover your business logic
- If a method takes a reference type parameter there should be test for getting `null` as an argument. It is called negative test cases.
- You can read easily an environment variable's value: `System.getenv("VAR_NAME");`
- You can import the sample data file into `psql` with the `\i` command or run it via the Database tool in IntelliJ.
- In IntelliJ language injections let you work with pieces of code in other languages embedded in your code. When you inject a language (such as PostgreSQL) into a string literal, you get comprehensive code assistance for editing that literal.
- Do you remember how to set environment variables for your run configuration? [here](https://www.jetbrains.com/help/objc/add-environment-variables-and-program-arguments.html)
- For serialization you need to add necessary dependency to your `pom.xml` and reload the maven project

## Background materials

- <i class="far fa-exclamation"></i> [Software testing](project/curriculum/materials/pages/general/software-testing.md)
- <i class="far fa-book-open"></i> [Positive or negative](https://stackoverflow.com/questions/8162423)
- <i class="far fa-exclamation"></i> [How to design classes](project/curriculum/materials/pages/java/how-to-design-classes.md)
- <i class="far fa-exclamation"></i> [Introduction to JDBC](project/curriculum/materials/competencies/java-intermediate/java-db-access.md.html)
- <i class="far fa-exclamation"></i> [JDBC basics](https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html)
- <i class="far fa-exclamation"></i> [DAO pattern in Java](https://www.baeldung.com/java-dao-pattern)
- <i class="far fa-exclamation"></i> [Serialization in Java](project/curriculum/materials/pages/java/serialization-in-java.md)
- <i class="far fa-exclamation"></i> [Compare two popular serialization frameworks](https://www.baeldung.com/jackson-vs-gson)
- [1-Bit Pack by Kenney](https://kenney.nl/assets/bit-pack)
