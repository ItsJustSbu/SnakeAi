# SnakeAi

This repository contains a snake ai game that is for my computer science 2: Analysis of Algorithms class 

This repository contains a snakeai.jar file that should be imported into the project file as well as the A-star algorithm as well as Dijkstra's algorithm that I have used

With this implementation I have managed to sit at position 43/225 students who have submitted their snakes on the game.


# Explanation of the algorithms
## A-Star Algorithm
in summary, A star is a path searching algorithm that finds the shortest path from a node to its goal using a heuristic. 
The heuristic used in my implementation is F = G + H where G is the manhattan distance between start node and the current node and H is the manhatten distance between the current node and the goal node

we search all the neighbours and measure the distances using the heuristic and store them into a priority queue where the priority queue prioritises the node with the smallest value and we mark the  neighbour as visited

This then allows us to find the shortest path between the snake and the apple(which is our goal)

However with A* there is no guarantee that we will find the shortest path, I therefore implemeneted Dijkstra's algorithm which guaruntees that we will always get the shortest path.

This algorithm is used when the A* algorithm fails(this happens when it takes too long to run or when it cannot find a path)

## Dijkstra's Algorithm
like A* this algorithm is a path searching algorithm that finds the shortest path from a node to its goal using a heuristic. 
The heuristic used is H, the manhattan distance between the current node and the goal node

the rest functions just as the above so I wont go into it.
