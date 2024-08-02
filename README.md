# Live Football World Cup Score Board

## Overview
This library provides functionality for live management of the World Cup scoreboard, as required in the task description. It supports operations to start new matches, update scores, finish matches, and get a summary of ongoing matches.

## Features
- **Start a new match**: Initializes a match with a 0-0 score.
- **Update the score of an ongoing match**: Allows updating the score of an ongoing match.
- **Finish an ongoing match**: Removes a match from the scoreboard.
- **Get a summary of ongoing matches**: Provides a summary of ongoing matches, ordered by total score and start time.

## Usage

### Starting a Match
```java
ScoreBoard scoreBoard = ScoreBoard.getInstance();
scoreBoard.startMatch("Mexico", "Canada");
```

### Updating a Score
```java
scoreBoard.updateScore("Mexico", "Canada", 0, 5);
```

### Finishing a Match
```java
scoreBoard.finishMatch("Mexico", "Canada");
```

### Getting the Summary
```java
List<Match> summary = scoreBoard.getSummary();
for (Match match : summary) {
        System.out.println(match);
}
```

## Additional informations
### Team names
Validation of team names should be added. I assumed that no one would try to enter a team name other than the first letter in upper case and the rest in lower case. The simplest solution would be to change all team names to upper or lower case and compare them when trying to start a match, update or end a match. I think this should be done by an additional class that would additionally check that the team name itself is correct. Currently XYZ is also a correct name.

### Singleton ScoreBoard class
I decided not to make the ScoreBoard class a singleton. Given that it is supposed to display all the matches currently being played it could be implemented that way, but we might want to display men's and women's matches at the same time. In this situation, two instances of the class would be needed.

### Match class
The Match class currently only has team name and score information. If more data is going to be added to the Match class, the creation of the object would need to be moved to a separate class that would be responsible for this. In this case, it would also be possible to move the validation of the data that the Match class accepts to a separate class which would lead to more readable code. The current case is so small that the introduction of an additional number of classes could only introduce additional complexity in understanding the solution.