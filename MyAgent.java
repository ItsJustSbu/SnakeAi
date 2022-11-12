import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import za.ac.wits.snake.DevelopmentAgent;

public class MyAgent extends DevelopmentAgent {

    public static void main(String args[]) {
        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String initString = br.readLine();
            String[] temp = initString.split(" ");
            int nSnakes = Integer.parseInt(temp[0]);

            while (true) {
                String line = br.readLine();
                if (line.contains("Game Over")) {
                    break;
                }
                Algorithm algorithm = new Algorithm();

                String apple1 = line;
                String[] apple = apple1.split(" ");
                algorithm.addApple(Integer.parseInt(apple[1]),Integer.parseInt(apple[0]));
                algorithm.setGoal(algorithm.apple);
                int headx =-1;
                int heady =-1;

                //do stuff with apples

                for (int zombie=0; zombie<6; zombie++) {
                    String zombieLine=br.readLine();
                    String[] zombiebody = zombieLine.split(" ");
                    String[] zombiehead = zombiebody[0].split(",");
                    algorithm.zombieHead(Integer.parseInt(zombiehead[1]),Integer.parseInt(zombiehead[0]));
                    algorithm.setZombieSnakes(zombieLine);

                }

                int mySnakeNum = Integer.parseInt(br.readLine());
                for (int i = 0; i < nSnakes; i++) {
                    String snakeLine = br.readLine();
                    String[] body = snakeLine.split(" ");
                    if(body[0].equals("dead")){
                        continue;
                    }else{
                        if (i == mySnakeNum) {
                            String snake = "";
                            //hey! That's me :)
                            String[] head = body[3].split(",");
                            String[] tail = body[body.length-1].split(",");
                            algorithm.addTail(Integer.parseInt(tail[1]),Integer.parseInt(tail[0]));
                            algorithm.addStart(Integer.parseInt(head[1]),Integer.parseInt(head[0]));

                            for (int j = 3; j < body.length; j++) {
                                snake+= " "+body[j];
                            }
                            algorithm.setSnakes(snake.trim());
                        }
                        //do stuff with other snakes
                        String[] head = body[3].split(",");
                        String snake = "";
                        for (int j = 3; j < body.length; j++) {
                            snake+= " "+body[j];
                        }

                        algorithm.setSnakes(snake.trim());
//                        algorithm.zombieHead(Integer.parseInt(head[1]),Integer.parseInt(head[0]));
                    }

                }

                algorithm.eat();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}