public class Node{

    Node parent;
    int col;
    int row;
    boolean open;
    int fCost;
    int gCost;
    int hCost;
    boolean isObstacle;
    boolean start;
    boolean goal;
    boolean checked;

    public Node(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getFcost(){
        return fCost;
    }
    public int getGcost(){
        return gCost;
    }



}