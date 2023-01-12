
//code written by Hugh Dolliff dolli028
import java.util.Random;
public class MyMaze{
    Cell[][] maze;
    int startRow;
    int endRow;

    public MyMaze(int rows, int cols, int startRow, int endRow) {
        this.maze = new Cell[rows][cols];
        this.startRow = startRow;
        this.endRow = endRow;
        if (cols<rows){
            for (int i = 0; i<maze[0].length;i++){
                for(int j = 0;j<maze.length;j++){
                    maze[j][i]=new Cell();
                }
            }
        }else{
            for (int i = 0; i<maze.length; i++){
                for (int j = 0; j<maze[0].length;j++){
                    maze[i][j] = new Cell();
                }
            }
        }
    }
    private static boolean checkBounds(MyMaze newMaze, int j,int d){//helper function to determine if the coordinates j and d are in the maze and if it has been visited
        if(j>-1 && j<newMaze.maze.length&&d>-1&&d<newMaze.maze[0].length&&newMaze.maze[j][d]!=null&& !newMaze.maze[j][d].getVisited()){
            return true;
        }else{return false; }
    }
    private static boolean hasNeighbor(MyMaze newMaze, int [] f){//uses checked bounds function to make sure the neighbors are within the maze and not visited
        int j = f[0],d= f[1];
        if(checkBounds(newMaze,j-1,d)||checkBounds(newMaze,j+1,d)||checkBounds(newMaze,j,d-1)||checkBounds(newMaze,j,d+1)){
            return true;
        }else{return false;}
    }
    private static int[] getRandomNeighbor(MyMaze newMaze, int[] h){
        while(hasNeighbor(newMaze,h)){//uses  has neighbor to make sure there is a space available and while their is it randomly picks one
            int random = (int)Math.floor(Math.random()*4);//returns a random number between 0-4 but not including 4
            if (random == 0){//when random is 0
                if(checkBounds(newMaze,h[0]-1,h[1])){//checks bounds of the place above the starting point
                    newMaze.maze[h[0]-1][h[1]].setBottom(false);//if its there it takes the wall out between the starting point and below neighbor
                    return new int []{h[0]-1,h[1]};//returns the location of the neighbor
                }
            }
            if (random == 1){//when random is 1
                if(checkBounds(newMaze,h[0],h[1]+1)){//checks bounds of the neighbor to the right
                    newMaze.maze[h[0]][h[1]].setRight(false);//if its there we delete the wall between the starting point and the neighbor
                    return new int[]{h[0],h[1]+1};//returns the location of the neighbor
                }
            }
            if(random == 2){ //when random is 2
                if(checkBounds(newMaze,h[0]+1,h[1])){//checks bounds of the neighbor that's below the starting point
                    newMaze.maze[h[0]][h[1]].setBottom(false);//if its there we delete the wall between the starting point and the neighbor
                    return new int[]{h[0]+1,h[1]};//returns the location of the neighbor
                }
            }if(random==3){//when random is 3
                if (checkBounds(newMaze,h[0],h[1]-1)){//checks the bounds of the neighbor that's to the left of the starting point
                newMaze.maze[h[0]][h[1]-1].setRight(false);//if its there delete the wall between starting point and neighbor
                return new int []{h[0],h[1]-1};//returns the location of neighbor
                }
            }

        }
        return new int[] {-1,-1};
    }

    /* TODO: Create a new maze using the algorithm found in the writeup. */
    public static MyMaze makeMaze(int rows, int cols, int startRow, int endRow) {
        MyMaze newMaze = new MyMaze(rows,cols,startRow,endRow);//creates a new MyMaze
        Stack1Gen<int[]> stack = new Stack1Gen<>();//creates a new stack called stack
        stack.push(new int []{startRow, 0});// adds the start of the maze to the stack
        newMaze.maze[startRow][0].setVisited(true);//sets the position to visited
        while(!stack.isEmpty()) {
            int[] top = stack.top();//variable for the top of the stack
            int[] randomNeighbor = getRandomNeighbor(newMaze, top);// variable for the random neighbor value
            if (randomNeighbor[0] == -1) stack.pop();//if the point has no unvisited neighbors pop it off the stack
            else {
                stack.push(randomNeighbor);//otherwise add the random neighbor to the stack
                newMaze.maze[randomNeighbor[0]][randomNeighbor[1]].setVisited(true);//set position of random neighbor to visited
            }
        }
            newMaze.maze[endRow][newMaze.maze[0].length-1].setRight(false);//sets the endrow to not have a right
            for(int i  = 0; i<rows; i++){//loop for the rows
                for(int j = 0; j<cols;j++){//loop for the columns
                    newMaze.maze[i][j].setVisited(false);//sets everything to false
                }
            }
        return newMaze;
    }

    /* TODO: Print a representation of the maze to the terminal */
    public void printMaze() {
        System.out.print(" ");
        for (int i =0;i<maze[0].length;i++){//this is for printing the top row
            System.out.print("|---");//prints |-- until the last column
            if(i==maze[0].length-1){
                System.out.print("|");//prints | for the last column
                System.out.println();
            }


        }
        for (int g = 0; g<maze.length;g++){
            for (int f = 0; f<maze[0].length;f++){
                if(f==0){//this is the far left side of the maze
                    if(g==startRow){
                        System.out.print("  ");//for the far left wall of the maze
                    } else{
                        System.out.print(" |");//for the starting spot of the maze or entrance
                    }
                }

                if(maze[g][f].getVisited()) System.out.print(" * ");//prints when the spot has been visited
                if(maze[g][f].getVisited()==false) System.out.print("   ");//prints when the spot has not been visited
                if(f==maze[0].length-1){//this is the far right side of the maze
                    if(g!=endRow){//for the far right wall of the maze
                        System.out.print("|");
                    }else{
                        System.out.print("");//for the right ending spot or exit to the maze
                    }
                }
                if(f==maze[0].length-1){//starting a new line
                    System.out.println();
                }
                if (maze[g][f].getRight()==true&& f!=maze[0].length-1) System.out.print("|");//for walls in the maze

                else{System.out.print(" ");}//for empty spaces in maze where there is no wall
            }
            for ( int j= 0; j<maze[0].length; j++){
                if(g == maze.length-1){
                    System.out.print("|---");//for the bottom
                }else{
                    if(maze[g][j].getBottom())System.out.print("|---");//for walls separating above and below cells
                    else System.out.print("|   ");//for left walls
                }
                if(j==maze[0].length-1){
                    System.out.println("|");//for far right wall
                }

            }
        }
    }

    /* TODO: Solve the maze using the algorithm found in the writeup. */
    public void solveMaze() {
        Q2Gen<int[]> queue = new Q2Gen<>();//creates a new queue
        queue.add(new int[]{startRow,0});//adds the starting spot to the queue
        while(queue.length()!=0){//while the queue is not empty
            int[] t = queue.remove();//set t to what ever is removed from the queue
            maze[t[0]][t[1]].setVisited(true);//set the position to visited
            if(t[0]==endRow &&t[1]==maze[0].length-1)return;//for the end of the maze
            int r = t[0],c = t[1];
            if(checkBounds(this,r+1,c)==true&& maze[r][c].getBottom()==false)//for the space below
                queue.add(new int []{(r+1),c});//adds it to the queue
            if(checkBounds(this,r-1,c)==true &&maze[(r-1)][c].getBottom()==false)//for the position above
                queue.add(new int []{(r-1),c});//adds it to the queue
            if(checkBounds(this,r,c+1)==true&&maze[r][c].getRight()==false)//for the position to the right
                queue.add(new int []{r,(c+1)});//adds it to the queue
            if(checkBounds(this,r,c-1)==true&&maze[r][c-1].getRight()==false)//for the position to the left
                queue.add(new int []{r,(c-1)});//adds it to the queue
        }//all the if statements above use check bounds to make sure the new point is within the maze then checks to see it a wall is between the two spaces
    }

    public static void main(String[] args){
        /* Any testing can be put in this main function */
        MyMaze newMaze = MyMaze.makeMaze(4,20,1,2);
        newMaze.printMaze();
        //MyMaze newMaze2 = MyMaze.makeMaze(10,10,7,2);
        //newMaze2.printMaze();
        newMaze.solveMaze();
        newMaze.printMaze();
        //newMaze2.solveMaze();
        //newMaze2.printMaze();

    }
}
