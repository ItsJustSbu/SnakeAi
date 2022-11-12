import java.util.*;

public class Algorithm {

    int MaxRow = 50;
    int MaxCol = 50;
    Node[][] grid = new Node[MaxRow][MaxCol];
    Node goalInit = new Node(-1,-1);
    boolean goalreached = false;

    Node current,start,goal, tail,apple;
    //OpenList will contain the nodes that have a walkable path
    ArrayList<Node>openList = new ArrayList<>();
    //closedList will contain the nodes that cannot be walked, this includes obstacles
    ArrayList<Node>closedList = new ArrayList<>();
    //array contains the path
    LinkedList<Node>path = new LinkedList<>();
    LinkedList<Node> bfsPath = new LinkedList<>();
    ArrayList<Node>zombieSnakes = new ArrayList<>();

    public Algorithm(){

        for(int i = 0; i < MaxRow;i++) {
            for (int j = 0; j < MaxCol; j++) {
                grid[i][j] = new Node(i, j);
            }
        }

    }
    public void setZombieSnakes(String snake){
        String [] arr = snake.split(" ");
        int dummyX = -1;
        int dummyY = -1;
        for(String s : arr){
            String[] points = s.split(",");
            int y = Integer.parseInt(points[0]);
            int x = Integer.parseInt(points[1]);
            grid[x][y].isObstacle = true;
            closedList.add(grid[x][y]);
            zombieSnakes.add(grid[x][y]);


            if (x == dummyX){
                if(dummyY < y){
                    for (int i = dummyY; i < y;i++){
                        grid[x][i].isObstacle = true;
                        closedList.add(grid[x][i]);
                        zombieSnakes.add(grid[x][i]);
                    }
                }
                else{
                    for(int i = y; i < dummyY;i++){
                        grid[x][i].isObstacle = true;
                        closedList.add(grid[x][i]);
                        zombieSnakes.add(grid[x][i]);
                    }
                }
            } else if (y == dummyY){
                if (dummyX < x){
                    for(int i = dummyX;i <x;i++){
                        grid[i][y].isObstacle = true;
                        closedList.add(grid[i][y]);
                        zombieSnakes.add(grid[i][y]);
                    }
                }
                else{
                    for(int i = x; i < dummyX;i++){
                        grid[i][y].isObstacle = true;
                        closedList.add(grid[i][y]);
                        zombieSnakes.add(grid[i][y]);
                    }
                }
            }
            dummyX = x;
            dummyY = y;

        }
        for(Node n : zombieSnakes){
            zombieHead(n.row,n.col);
        }
    }

    //This method initializes the start
    public void addStart(int x, int y){
        start = new Node(x, y);
        grid[x][y] = start;
        openList.add(start);
    }
    //This method initializes the apple
    public void addApple(int x, int y){
        apple = new Node(x, y);
        grid[x][y] = apple;
    }
    //This method initializes the tail
    public void addTail(int x, int y){
        if(x+1 < MaxRow){
            tail = new Node(x, y);
            grid[x+1][y] = tail;
        }
        if(x-1 >=0){
            tail = new Node(x, y);
            grid[x-1][y] = tail;
        }




    }
    public void setGoal(Node node){
        goal = node;
    }
    //This method initializes the snake's head.
    // it also sets a field around the zombie's head so that the snake does not get near the head of the zombie
    public void zombieHead(int x, int y){
        Node zombie = new Node(x, y);
        grid[x][y] = zombie;
        grid[x][y].isObstacle = true;
        if (x-1 >= 0){
            grid[x-1][y] = new Node(x-1, y);
            grid[x-1][y].isObstacle = true;
            closedList.add(grid[x-1][y]);
        }
        if (x+1 < MaxRow){
            grid[x+1][y] = new Node(x+1, y);
            grid[x+1][y].isObstacle = true;
            closedList.add(grid[x+1][y]);
        }
        if(y-1 >=0){
            grid[x][y-1] = new Node(x, y-1);
            grid[x][y-1].isObstacle = true;
            closedList.add(grid[x][y-1]);
        }
        if(y+1 < MaxCol){
            grid[x][y+1] = new Node(x, y+1);
            grid[x][y+1].isObstacle = true;
            closedList.add(grid[x][y+1]);
        }
        if (x-1 >= 0 && y-1 >=0){
            grid[x-1][y-1] = new Node(x, y-1);
            grid[x-1][y-1].isObstacle = true;
            closedList.add(grid[x-1][y-1]);
        }
//        if (x+1 < MaxRow && y+1 < MaxCol){
//            grid[x+1][y+1] = new Node(x+1, y+1);
//            grid[x+1][y+1].isObstacle = true;
//            closedList.add(grid[x+1][y+1]);
//        }
    }

    //This method sets the snakes onto the grid as well as joins the body from the edges of the snakes given as a string
    public void setSnakes(String snake){
        String [] arr = snake.split(" ");
        int dummyX = -1;
        int dummyY = -1;
        for(String s : arr){
            String[] points = s.split(",");
            int y = Integer.parseInt(points[0]);
            int x = Integer.parseInt(points[1]);
            grid[x][y].isObstacle = true;
            closedList.add(grid[x][y]);


            if (x == dummyX){
                if(dummyY < y){
                    for (int i = dummyY; i < y;i++){
                        grid[x][i].isObstacle = true;
                        closedList.add(grid[x][i]);
                    }
                }
                else{
                    for(int i = y; i < dummyY;i++){
                        grid[x][i].isObstacle = true;
                        closedList.add(grid[x][i]);
                    }
                }
            } else if (y == dummyY){
                if (dummyX < x){
                    for(int i = dummyX;i <x;i++){
                        grid[i][y].isObstacle = true;
                        closedList.add(grid[i][y]);
                    }
                }
                else{
                    for(int i = x; i < dummyX;i++){
                        grid[i][y].isObstacle = true;
                        closedList.add(grid[i][y]);
                    }
                }
            }
            dummyX = x;
            dummyY = y;

        }
    }

    public void calculateCost(Node node){
        node.gCost =(int) Math.abs(node.row - start.row) + Math.abs(node.col - start.col);
        node.hCost = (int) Math.abs(node.row - goal.row) + Math.abs(node.col - goal.col);
        node.fCost = node.gCost + node.hCost;
    }

    public void search2Cost(Node node){
        node.gCost =Math.abs(node.row - start.row) + Math.abs(node.col - start.col);
    }



    //This method performs the A* search which searches for the goal
    //The goal is either the apple or the snake's tail if it does not find a path
    public void search(){

        while (!openList.isEmpty()){

            openList.sort((n1,n2) -> Integer.compare(n1.getFcost(), n2.getFcost()));
            current = openList.get(0);
            openList.remove(current);
            current.checked = true;
            closedList.add(current);
            if (current.equals(goal)){
                System.out.println("goal is found");
                goalreached = true;
                TrackPath();
                break;

            }
            ArrayList<Node>children = new ArrayList<>();
            //child above
            if(current.row -1 > -1){
                children.add(grid[current.row-1][current.col]);
            }
            //child right
            if(current.col+1 < MaxCol){
                children.add(grid[current.row][current.col+1]);
            }
            //child down
            if(current.row+1 < MaxRow){
                children.add(grid[current.row+1][current.col]);
            }
            //child left
            if(current.col-1 >-1){
                children.add(grid[current.row][current.col-1]);
            }


            for(Node child : children){

                if (closedList.contains(child) || child.isObstacle){
                    child.checked = true;
                    continue;
                }
                calculateCost(child);
                if (openList.contains(child))
                {
                    openList.sort((n1,n2) -> Integer.compare(n1.getGcost(), n2.getGcost()));
                    if(openList.get(0).equals(child)){
                        child.parent = current;
                    }
                    else
                    {
                        continue;
                    }
                }else
                {
                    child.parent = current;
                    openList.add(child);
                }

            }

            if(openList.isEmpty())
            {
                System.out.println("log "+"Path not found");
                goal = tail;
                search2();
                break;

            }

        }

    }
    public void TrackPath(){
        path.clear();
        current = goal;
        path.add(current);
        while(current.parent !=start){
            current = current.parent;
            path.addFirst(current);
        }
    }
    public void search2(){
        // goal = tail;
        // path.clear();
        // openList.add(start);
        while (!openList.isEmpty()){

            openList.sort((n1,n2) -> Integer.compare(n1.getGcost(), n2.getGcost()));
            current = openList.get(0);
            openList.remove(current);
            current.checked = true;
            closedList.add(current);
            if (current.equals(goal)){
                System.out.println("goal is found");
                TrackPath();
                break;

            }
            ArrayList<Node>children = new ArrayList<>();
            //child above
            if(current.row -1 > -1){
                children.add(grid[current.row-1][current.col]);
            }
            //child right
            if(current.col+1 < MaxCol){
                children.add(grid[current.row][current.col+1]);
            }
            //child down
            if(current.row+1 < MaxRow){
                children.add(grid[current.row+1][current.col]);
            }
            //child left
            if(current.col-1 >-1){
                children.add(grid[current.row][current.col-1]);
            }


            for(Node child : children){

                if (closedList.contains(child) || child.isObstacle){
                    child.checked = true;
                    continue;
                }
                search2Cost(child);
                if (openList.contains(child))
                {
                    openList.sort((n1,n2) -> Integer.compare(n1.getGcost(), n2.getGcost()));
                    if(openList.get(0).equals(child)){
                        child.parent = current;
                    }
                    else
                    {
                        continue;
                    }
                }else
                {
                    child.parent = current;
                    openList.add(child);
                }

            }



        }
    }


    public void eat(){
        if (goalreached == true){
            search2();
            if (path.size() > 0){
                current = path.get(0);


                if(start.row == current.row){
                    if(start.col < current.col){
                        System.out.println(3);
                    }
                    else if(start.col > current.col){
                        System.out.println(2);
                    }
                }

                if(start.col == current.col){
                    if(start.row < current.row){
                        System.out.println(1);
                    }
                    if(start.row > current.row){
                        System.out.println(0);
                    }
                }
            }
        }
        else{
            search();
            if (path.size() > 0){
                current = path.get(0);


                if(start.row == current.row){
                    if(start.col < current.col){
                        System.out.println(3);
                    }
                    else if(start.col > current.col){
                        System.out.println(2);
                    }
                }

                if(start.col == current.col){
                    if(start.row < current.row){
                        System.out.println(1);
                    }
                    if(start.row > current.row){
                        System.out.println(0);
                    }
                }
            }
        }





    }


}
