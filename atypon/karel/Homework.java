import stanford.karel.SuperKarel;

import java.util.*;


public class Homework extends SuperKarel {

    /* You fill the code here */
    private enum Direction{
        UP,RIGHT,BOTTOM,LEFT
    }
    private int X,Y;
    private int width,height;
    private int steps,beepers;
    private int current_direction;
    Homework() {}
    public void setCurrentDirection(int direction) {
        Direction arr[] = {Direction.UP, Direction.RIGHT,
                Direction.BOTTOM, Direction.LEFT};

        int x = current_direction;
        while (true){
            if (direction != x) {
                turnRight();
            } else {
                break;
            }
            x++;
            if (x == 4)
                x = 0;
        }
        current_direction = x;
    }
    public int getCurrentDirection()
    {
        return current_direction;
    }
    @Override
    public void run() {
        initialize();
        walkToMiddleX(width,Direction.LEFT.ordinal(),Direction.BOTTOM.ordinal());

        if(height==1&&width==2||height==2&&width==1||height==width&&width==1);
        else
        if(height%2==1&&width%2==1) {
            solveOddOdd();
        }else if(height%2==0&&width%2==0&&Math.min(height,width)>=3)
        {
            solveEvenEven();
        }else if(Math.min(height,width)>=3){
            solveOddEven();
        }
        else{
            solveEdge();
        }
        printResults();
    }

    public void printResults()
    {
        System.out.println("Number of Steps: "+steps);
        System.out.println("Number of Beepers: "+beepers);
    }
    @Override
    public void move()
    {   super.move();
        switch (getCurrentDirection())
        {
            case 0:Y++;break;
            case 1:X++;break;
            case 2:Y--;break;
            case 3:X--;break;
        }
        steps++;
    }
    public void initialize() {
        X = 1;
        Y = 1;
        width = 0;
        height = 0;
        steps =0;
        beepers = 0;
        current_direction = 1;
        setBeepersInBag(1000);
        while(!frontIsBlocked()||getCurrentDirection()!=Direction.UP.ordinal())
        {
            if(frontIsBlocked()) {
                setCurrentDirection(Direction.UP.ordinal());
                if(frontIsBlocked()) {
                    break;
                }
            }
            move();
        }
        height = Y;
        width = X;
        setCurrentDirection(Direction.LEFT.ordinal());
    }
    public void walkToMiddleX(int width,int start_direction,int end_direction)
    {
        setCurrentDirection(start_direction);
        while(X!=width/2+1&&!frontIsBlocked())
        {
            move();
        }
        setCurrentDirection(end_direction);
    }
    public void walkToMiddleY(int height,int start_direction,int end_direction)
    {
        setCurrentDirection(start_direction);
        while(Y!=height/2+1)
        {
            move();
        }
        setCurrentDirection(end_direction);
    }
    @Override
    public void turnRight() {
        super.turnRight();
        current_direction++;
        if(current_direction==4)
            current_direction =0;
    }
    @Override
    public void turnLeft() {
        super.turnLeft();
        current_direction--;
        if(current_direction==-1)
            current_direction =3;
    }

    public void fillBeepersLineToXY(int x, int y, int direction)
    {
        setCurrentDirection(direction);
        while(X!=x||Y!=y)
        {
            if(!beepersPresent()) {
                putBeeper();
            }
            move();
            beepers++;
        }
        if(!beepersPresent()) {
            putBeeper();
            beepers++;
        }
    }
    public void solveOddOdd()
    {
        fillBeepersLineToXY(width / 2 + 1, height / 2 + 1, Direction.BOTTOM.ordinal());
        fillBeepersLineToXY(width, height / 2 + 1, Direction.RIGHT.ordinal());
        walkToMiddleX(width,Direction.LEFT.ordinal(),Direction.BOTTOM.ordinal());
        fillBeepersLineToXY(1, height / 2 + 1, Direction.LEFT.ordinal());
        walkToMiddleX(width,Direction.RIGHT.ordinal(),Direction.BOTTOM.ordinal());
        fillBeepersLineToXY(width / 2 + 1, 1, Direction.BOTTOM.ordinal());
    }
    public void solveOddEven()
    {
        walkToMiddleY(height,Direction.BOTTOM.ordinal(),Direction.RIGHT.ordinal());
        if(height%2==0){
            fillBeepersLineToXY(width/2+1,height,Direction.UP.ordinal());
            fillBeepersLineToXY(width/2+1,1,Direction.BOTTOM.ordinal());
            walkToMiddleY(height,Direction.UP.ordinal(),Direction.RIGHT.ordinal());

        }
        else {
            fillBeepersLineToXY(width,height/2+1,Direction.RIGHT.ordinal());
            fillBeepersLineToXY(1,height/2+1,Direction.LEFT.ordinal());
            walkToMiddleX(width-1,Direction.RIGHT.ordinal(),Direction.UP.ordinal());
        }
        fillDottedCircleBeepers(X,Y, Collections.emptyList());
        if(width==3){move();putBeeper();
            setCurrentDirection(Direction.LEFT.ordinal());move();move();putBeeper();}
        if(height==3) {
            move();
            putBeeper();
            setCurrentDirection(Direction.BOTTOM.ordinal());
            move();
            move();
            putBeeper();
        }
    }
    public void fillDottedCircleBeepers(int x, int y, List<Integer>points)
    {
        move();
        int i=0;
        while(X!=x||Y!=y)
        {
            for(int z=0;z<points.size();z+=2)
            {
                if(points.get(z)==X&&points.get(z+1)==Y)
                {
                    putBeeper();
                    beepers++;
                    turnLeft();
                    i=  1;
                }
            }
            if(frontIsBlocked()) {
                if(i%2==1&&!beepersPresent()){
                    putBeeper();
                    beepers++;
                }
                turnRight();
                i++;
                move();
                turnRight();
            }
            if(i%2==1&&!beepersPresent()){
                putBeeper();
                beepers++;
            }
            i++;
            move();}

        if(!beepersPresent()){
            putBeeper();
            beepers++;
        }
    }
    public void solveEvenEven()
    {
        walkToMiddleY(height,Direction.BOTTOM.ordinal(),Direction.BOTTOM.ordinal());
        setCurrentDirection(Direction.RIGHT.ordinal());
        fillDottedCircleBeepers(X,Y,List.of(X,Y,X,Y-1,X-1,Y,X-1,Y-1));
    }
    public void solveEdge() {
        setCurrentDirection(Direction.RIGHT.ordinal());
        while(!frontIsBlocked())move();
        if (height > width) {
            int i = 1;
            int number_champers = 0;
            while ((X != width || Y != 1) && number_champers != 4) {

                setCurrentDirection(Direction.BOTTOM.ordinal());
                move();
                setCurrentDirection(Direction.LEFT.ordinal());
                if (i % 2 == 1) {
                    putBeeper();
                    beepers++;
                    number_champers++;
                }
                while (!frontIsBlocked() && i % 2 == 1) {
                    move();
                    putBeeper();
                    beepers++;
                }
                setCurrentDirection(Direction.RIGHT.ordinal());
                while (!frontIsBlocked() && i % 2 == 1) {
                    move();
                }
                i++;
            }
            if (X!=width||Y!=1){
                int a = X, b = Y;
                setCurrentDirection(Direction.BOTTOM.ordinal());
                fillBeepersLineToXY(width, 1, Direction.BOTTOM.ordinal());
                setCurrentDirection(Direction.LEFT.ordinal());
                if (!frontIsBlocked()) {
                    move();
                    setCurrentDirection(Direction.UP.ordinal());
                    fillBeepersLineToXY(a - 1, b, Direction.UP.ordinal());
                }
            }

        }
        else
        {
            int i = 1;
            int number_champers=0;
            setCurrentDirection(Direction.BOTTOM.ordinal());
            while(!frontIsBlocked())move();
            while((X!=1||Y!=1)&&number_champers!=4)
            {
                setCurrentDirection(Direction.LEFT.ordinal());
                move();
                setCurrentDirection(Direction.UP.ordinal());
                if(i%2==1) {
                    putBeeper();
                    beepers++;
                    number_champers++;
                }
                while(!frontIsBlocked()&&i%2==1){
                    move();
                    putBeeper();
                    beepers++;
                }
                setCurrentDirection(Direction.BOTTOM.ordinal());
                while(!frontIsBlocked()&&i%2==1){
                    move();
                }
                i++;
            }

            if (X!=1||Y!=1) {
                int a = X, b = Y;
                fillBeepersLineToXY(1, 1, Direction.LEFT.ordinal());

                setCurrentDirection(Direction.UP.ordinal());
                if (!frontIsBlocked()) {
                    move();
                    setCurrentDirection(Direction.RIGHT.ordinal());
                    fillBeepersLineToXY(a, b + 1, Direction.RIGHT.ordinal());
                }
            }
        }
    }

}
