//By: Jefferson Chen and Kishan Muhundhan
//Date: June 23, 2021
//Purpose: This program runs the game Gomoku, an ancient Chinese board game. The instructions of the game
//can be found under the "How to Play" tab in the Main Menu

//Preconditions: Main Menu must call upon this class
//Postconditions: The game Gomoku will be opened
// Displays the board and runs the main game where pieces are placed and a winner is decided

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyFrame extends JFrame implements MouseListener {

    // Variables Section
    // Save Coordinates
    int x;
    int y;
    int x1;
    int y1;//creating integer variables
    public int winflag =0;
    public boolean twoPlayers=true;
    public boolean win=false; // creating and initializing boolean variables

    public int c;
    //Black Pieces Numbers
    //White Pieces Numbers

    //1 = Black Move, 2 = White Move
    //By default, black moves first in Gomoku

    int flag=1;

    //canPlay defines if game is over
    //true = game is ongoing, false = game is over
    boolean canPlay=true;

    //Saves positions
    //'0' means empty grid, '1' means black occupied, '2' means white occupied
    int [][]allChess = new int[19][19];

    //Total piece numbers
    int chessSum=0;

    //Sets up and defines the background image
    BufferedImage bgImage;

    {
        try { //try to set and read the bakground wood image
            bgImage = ImageIO.read(new File(".//src//wood.jpg//"));
        } catch (IOException e) {
            e.printStackTrace();  //otherwise just trace it back
        }
    }


    //Buttons and JPanel
    JButton restart=new JButton("Main Menu"); //Go back to Main Menu
    JButton exit=new JButton("Quit"); //Exit
    JPanel south=new JPanel();

    //Variables Section Ends

    //pre: none
    //post:
    //MyFrame class sets up the background and certain variables and buttons
    public MyFrame(boolean twoPlayers) {
        this.twoPlayers = twoPlayers;
        this.setTitle("Gomoku"); // Title, "Gomoku"
        setSize(630,700); //set size of window
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//closes window when x is clicked

        addMouseListener(this);//Add listener

        south.setLayout(new FlowLayout(FlowLayout.LEFT,60,30)); //set layout of buttons to flow layout

        south.add(restart);
        south.add(exit);//add exit and restart buttons

        //Initialize Listeners
        MybuttonListener buttonListener =new MybuttonListener();
        //Add Listeners
        restart.addActionListener(buttonListener);
        exit.addActionListener(buttonListener);
        //Add the listeners to the south of the panel
        this.add(south,BorderLayout.SOUTH);

        setVisible(true);
    }

    //pre: none
    //post:
    //Paint Class, draws the GUI
    public void paint(Graphics g) {
        int column,row;

        super.paint(g);
        g.drawImage(bgImage,38,58,this);
        // Setup board

        for(column=58;column<600 ;column=column+30){ //loop to draw all column lines
            g.drawLine(38,column,578,column);
        }
        for(row=38;row<600;row=row+30) { //loop to draw every row line
            g.drawLine(row, 58, row, 598);
        }
        //Construct nine black markers on board
        g.fillOval(123, 143, 10, 10);
        g.fillOval(483, 143, 10, 10);
        g.fillOval(123, 503, 10, 10);
        g.fillOval(303, 323, 10, 10);
        g.fillOval(483, 503, 10, 10);
        g.fillOval(123, 323, 10, 10);
        g.fillOval(483, 323, 10, 10);
        g.fillOval(303, 143, 10, 10);
        g.fillOval(303, 503, 10, 10);

        for(int i=0;i<allChess.length;i++) { //while the rows are not full
            for(int j=0;j<allChess.length;++j) { //while the columns are not full


                // Black Move
                if(allChess[i][j]==1) { //if piece placed was black
                    int tempX=i*30+38;// Left border to grid
                    int tempY=j*30+58;// Right border to grid
                    g.setColor(Color.black);
                    g.fillOval(tempX-13,tempY-13,25,25);
                }

                // White Move
                if(allChess[i][j]==2) { //if piece placed was white
                    int tempX=i*30+38; // Left border to grid
                    int tempY=j*30+58; // Right border to grid
                    g.setColor(Color.white);
                    g.fillOval(tempX-13,tempY-13,25,25);
                }
            }
        }
        //Construct a red border around the last placed piece for clarity reasons
        if(chessSum>0) { //if at least one piece has been placed
            g.setColor(Color.red); //set colour to red
            g.drawRect(x*30+38-13, y*30+58-13, 25,25); //draw a red rectangle
        }
        if(twoPlayers) { //if two players mode was selected, increase the number of pieces placed
            chessSum++;
        }
        else{ //other wise add 2 to account for the AI's movements
            chessSum = chessSum +2;
        }
        System.out.println("Total Pieces Placed: "+(chessSum-1)); //print the amount of pieces placed so far
    }


    //pre: the mouse is clicked after multiplayer/singleplayer is decided and the game has not been won
    //post: places a piece at the appropriate position for the player(s) or AI
    //this method places pieces at the appropriate position
    public void mouseClicked(MouseEvent e) {

        x=e.getX();
        y=e.getY();

        if(canPlay) { //if game is in progress

            if(x>=38&&x<=588&&y>=58&&y<=620) { //if chosen piece location is not outside the borders

                x=(x-25)/30; //find the exact x piece coordinate
                y=(y-45)/30; //find the exact y piece coordinate
                if(allChess[x][y]==0){ //Can only place piece if that spot is currently unoccupied by another piece

                    if(flag==1) {
                        allChess[x][y]=1;//'1' represents a black piece
                        Sound.PlacePiece();
                        repaint();
                        this.checkFive();//Check if a line of 5 has been made

                        flag=2; //set flag

                        if(!twoPlayers && !win) {
                            AI(); //AI is called
                            Sound.PlacePiece();
                            repaint();
                            flag = 2; //Ensures flag is 2, so the win goes to white if a line of 5 is made
                            this.checkFive(); //Checks win
                            flag = 1; //Black move
                        }
                    }


                    else {
                        allChess[x][y]=2;//'2' represents a white piece
                        Sound.PlacePiece();
                        this.checkFive();//Check if a line of 5 has been made

                        flag=1; //Resets flag
                    }
                    this.repaint(); //repaint the board
                }
            }
        }

    }

    //pre: if player chose single player and black (the player) placed a piece
    //post: the AI decides a location for a piece to be placed
    //This method controls the Artificial intelligence, which checks for possible locations to place white pieces
    public void AI(){

        if (twoPlayers==false){ //if the one player button was chosen

            if (x>15 || x<4 || y<4 || y>15) { //if the piece is placed too far outside the grid
                c=2;
                check(4); //calls upon various check methods to look for different combinations of pieces
                check(3);
                check(2);
                if (flag==2){ //if it is AI turn and above is not possible, place a random piece
                    randomPiece();
                }

            }
            c=1; //calls upon various check methods to look for different combinations of pieces
            check(4);//black

            check(3);//black
            c=2;

            check(4);//white
            c=1;

            check(2);//black
            c=2;

            check(3);//white

            check(2);//white



            if (flag==2){ //if it is AI turn
                randomPiece(); //random piece if other conditions fail to meet
            }
        }
    }

    //pre: if black has already placed their piece, the game is set to singleplayer, and the AI resorts to this method
    //post:
    //Randomizes a location on the board and places a piece
    public void randomPiece(){
        do {
            x= (int)(Math.random() * 19); //create a random x coordinate on grid
            y= (int)(Math.random() * 19); //create a random y coordinate on grid
        }
        while(allChess[x][y] != 0); //while there is already a piece taking up the location
        allChess[x][y]=2; //place a white piece

        checkFive(); //check to see if 5 in a row
        flag=1; //set flag to 1 to indicate black turn

    }

    //pre: if the AI resorts to this method and int check is given a value
    //post:
    //checks for rows, columns or diagonals of pieces of a specified length and places a piece accordingly
    public void check(int check){

        int color=allChess[x][y];

        int count=1; //initialize the count variable to 1

        //Check rows to the right
        for(int i=1;i<check;i++) {
            if(x>= (20-check)) //if piece is outside range
                break;
            if(color==allChess[x+i][y]) //if there is already a piece at this location
                count++;


            if (count>=check && allChess[x][y]==c && flag == 2){ //if the correct coloured piece is at this location and it is white turn
                if (allChess[x+check][y] == 0){ //if the spot is free
                    if((x+check)<0){ //if the piece will place outside the board
                        randomPiece();
                    }
                    else{
                        allChess[x+check][y]=2; //place a white piece at specific location
                        checkWin(count); //check to see if there is a winner
                        flag=1;
                    }
                }

                else if (allChess[x-(5-check)][y] == 0) { //if spot is free
                    if ((x-(5-check))<0 || (x-(5-check))>19)  //if the piece will place outside the board
                        randomPiece();
                    else{
                        allChess[x-(5-check)][y]=2; //place a  white piece at specific location
                        checkWin(count); //check to see if there is a winner
                        flag=1;
                    }
                }

                else
                    randomPiece();
            }
        }
        count=1;

        //Check rows to the left
        for(int i=1;i<check;i++) { //keep checking to the left of a placed piece
            if(x<= (check-2))
                break;

            if(color==allChess[x-i][y]) //if the correct coloured piece is in the specific location
                count++;

            if (count>=check && allChess[x][y]==c && flag == 2){ //if correct coloured piece is at location and it is correct player's turn

                if (allChess[x-check][y] == 0 ){ //if spot is free
                    if ((x-check)<0 || (x-check)>19) //if spot is outside of board
                        randomPiece();
                    else {
                        allChess[x-check][y]=2; //place a white piece at specific location
                        checkWin(count); //check to see if there is a winner
                        flag=1;
                    }
                }

                else if (allChess[x+(5-check)][y] == 0){ //if spot is free
                    if ((x+(5-check))<0 || (x+(5-check))>19) //if spot is outside of board
                        randomPiece();
                    else{
                        allChess[x+(5-check)][y]=2; //place white piece at specific location
                        checkWin(count); //check to see if there is a winner
                        flag=1;
                    }
                }
                else{
                    randomPiece();
                }
            }

        }
        count=1;


        //Check columns downwards
        for(int i=1;i<check;i++) {//check every spot below the placed piece
            if(y>=(20-check))//if spot being checked is outside of board
                break;
            if(color==allChess[x][y+i]) {//if there is a piece of the correct colour at specified spot
                count++;
            }

            if (count>=check && allChess[x][y]==c && flag == 2){ //if specified spot has a piece of correct colour and it is the correct colour's turn
                if (allChess[x][y+check] == 0){ //if the spot is free
                    allChess[x][y+check]=2; //place a white piece at specified location
                    checkWin(count); //check if there is a winner
                    flag=1;
                }
                else if (allChess[x][y-(5-check)] == 0){ //if the specified spot is free
                    allChess[x][y-(5-check)]=2; //place a white piece
                    checkWin(count); //check if there is a winner
                    flag=1;
                }

                else
                    randomPiece();
            }


        }
        count=1;

        //Check columns upwards
        for(int i=1;i<check;i++) { //check ever spot above the last placed piece
            if(y<=(check-2)) //if spot being checked is outside the board
                break;
            if(color==allChess[x][y-i]) {
                count++;
            }

            if (count>=check && allChess[x][y]==c && flag == 2){ //if there is a piece that is the right colour and it is the right player's turn
                if (allChess[x][y-check] == 0){ //if spot is free
                    allChess[x][y-check]=2; //place a white piece at specified spot
                    checkWin(count); //check if there is a winner
                    flag=1;
                }
                else if (allChess[x][y+(5-check)] == 0){ //if specified spot is free
                    allChess[x][y+(5-check)]=2; //place a white piece
                    checkWin(count); //check if there is a winner
                    flag=1;
                }

                else
                    randomPiece(); //place a random piece
            }
        }
        count=1;

        //Check diagonals right and up
        for(int i=1;i<check;i++) { //check each spot diagonal to the right and up
            if(y<=(check-2)||x>=(20-check)) //if spot being checked is outside board
                break;
            if(color==allChess[x+i][y-i]) { //if there is a piece of the correct colour
                count++;
            }

            if (count>=check && allChess[x][y]==c && flag == 2){ //check if black just went and it is white's turn
                if (allChess[x+check][y-check] == 0){ //if the spot is free
                    allChess[x+check][y-check]=2; //place a white piece at specified location
                    checkWin(count); //check if there is a winner
                    flag=1;
                }
                else if (allChess[x-(5-check)][y+(5-check)] == 0){ //if specified spot is free
                    allChess[x-(5-check)][y+(5-check)]=2; //place a white piece at specified spot
                    checkWin(count); //check if there is a winner
                    flag=1;
                }
                else
                    randomPiece(); //place a random piece
            }
        }
        count=1;

        //Check diagonals left and down
        for(int i=1;i<check;i++) { //check every spot diagonal to the left and down
            if(x<=(check-2)||y>=(20-check)) //if spot being checked is outside board
                break;

            if(color==allChess[x-i][y+i]) { //if there is a piece of the correct colour at the specified spot
                count++;
            }

            if (count>=check && allChess[x][y]==c && flag == 2){ //if black just played and it is white's turn
                if (allChess[x-check][y+check] == 0){ //if specified spot is free
                    allChess[x-check][y+check]=2; //place a white piece
                    checkWin(count); //check if there is a winner
                    flag=1;
                }
                else if (allChess[x+(5-check)][y-(5-check)] == 0){ //check if the spot is free
                    allChess[x+(5-check)][y-(5-check)]=2; //place a white piece at specified spot
                    checkWin(count); //check if there is a winner
                    flag=1;
                }

                else
                    randomPiece(); //place a random piece
            }
        }
        count=1;

        //Check diagonals left and up
        for(int i=1;i<check;i++) { //check every spot diagonal to the left and up
            if(x<=(check-2)||y<=(check-2)) //if spot being checked is outside the board
                break;
            if(color==allChess[x-i][y-i]) { //if there is a piece of specific colour at the specified location
                count++;
            }

            if (count>=check && allChess[x][y]==c && flag == 2){ //if black just played and if it is white's turn
                if (allChess[x-check][y-check] == 0){ //if spot is free
                    allChess[x-check][y-check]=2; //place a white piece at specified location
                    checkWin(count); //check if there is a winner
                    flag=1;
                }
                else if(allChess[x+(5-check)][y+(5-check)] == 0){ //if spot is free
                    allChess[x+(5-check)][y+(5-check)]=2; //place a white piece
                    checkWin(count); //check if there is a winner
                    flag=1;
                }

                else
                    randomPiece(); //place a random piece
            }
        }
        count=1;

        //Check diagonals right and down
        for(int i=1;i<check;i++) { //check each spot diagonal to the right and down
            if(y>=(20-check)||x>=(20-check)) //if spot being checked is in board
                break;
            if(color==allChess[x+i][y+i]) { //if there is a piece of specific colour at the specified spot
                count++;
            }

            if (count>=check && allChess[x][y]==c && flag == 2){ //if black just played and it is white's turn
                if (allChess[x+check][y+check] == 0){ //if spot is free
                    allChess[x+check][y+check]=2; //place a white piece
                    checkWin(count); //check if there is a winner
                    flag=1;
                }
                else if (allChess[x-(5-check)][y-(5-check)] == 0){ //if spot is free
                    allChess[x-(5-check)][y-(5-check)]=2; //place a white piece
                    checkWin(count);//check if there is a winner
                    flag=1;
                }

                else
                    randomPiece(); //place a random piece
            }
        }
    }

    //pre: if a turn ended(a player or AI just made a move)
    //post:
    //This method checks if Five pieces are connected after a move has been made
    public  void checkFive() {
        int checkX; //create integer variables
        int checkY;
        int countRow, countColumn,CountDiagonalUp, CountDiagonalDown;

        for (checkX = 0; checkX <= 18; checkX++) { //while the x value is within the board
            for(checkY=0; checkY<=18;checkY++) { //while the y value is within the board
                //Saves the color of a certain piece
                if(allChess[checkX][checkY]!=0) { //if a piece is placed on specified spot
                    int color = allChess[checkX][checkY];
                    //Counts the pieces
                    countRow = 1;

                    //Check rows (to the right)
                    for (int i = 1; i < 5; i++) {
                        if (checkX >= 15) //if past the 15th row
                            break;
                        if (color == allChess[checkX + i][checkY]) { //if there is a piece to the right of specified one
                            countRow++;
                        }
                        checkWin(countRow); //check if there is a winner
                    }

                    //Check columns (downwards)
                    countColumn = 1;
                    for (int i = 1; i < 5; i++) { //check each spot downwards
                        if (checkY >= 15) //if piece is past the 15 column
                            break;
                        if (color == allChess[checkX][checkY + i]) { //if specified piece is the correct colour
                            countColumn++;
                        }
                        checkWin(countColumn); //check if there is a winner
                    }


                    //Check diagonals left and down
                    CountDiagonalDown = 1;
                    for (int i = 1; i < 5; i++) { //check every spot until 5 diagonally left and down
                        if (checkX <= 3 || checkY >= 15) //check between 3rd rwo and 15th column
                            break;

                        if (color == allChess[checkX - i][checkY + i]) { //if specified piece is the correct colour
                            CountDiagonalDown++;
                        }
                        checkWin(CountDiagonalDown); //check if there is a winner
                    }

                    //Check diagonals left and up
                    CountDiagonalUp =1;
                    for (int i = 1; i < 5; i++) { //check every spot until 5 diagonally left and up
                        if (checkX <= 3 || checkY <= 3) //check between 3rd rwo and 3rd column
                            break;
                        if (color == allChess[checkX - i][checkY - i]) { //if specified piece is the correct colour
                            CountDiagonalUp++;
                        }
                        checkWin(CountDiagonalUp); //check if there is a winner
                    }

                }
            }
        }
    }

    //pre: when the mouse has been entered
    //post: checks the coordinates of the mouse input
    //
    public void mouseEntered(MouseEvent e) {
        x1=e.getX();
        y1=e.getY();
        if(x1>=38&&x1<=588&&y1>=58&&y1<=620) { //if the mouse has entered a location within accepted range
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    //pre: when the mouse exits a component
    //post: none (this method exists to avoid an error)
    //
    public void mouseExited(MouseEvent arg0) {

    }

    //pre: when the mouse has been pressed down
    //post: none (this method exists to avoid an error)
    //
    public void mousePressed(MouseEvent arg0) {
    }

    //pre: when the mouse has been released
    //post: none (this method exists to avoid an error)
    //

    public void mouseReleased(MouseEvent e) {
    }

    //pre: if there are 5 or more pieces in a row
    //post:
    //This method checks if the game is won and displays a victory screen and victory jingle
    public void checkWin(int count) {
        if (count>=5 && winflag ==0){ //if there is 5 or more pieces in a row
            if (flag == 1) { //if it is black's turn
                Sound.BlackWinJingle(); //play the winner sound
                win=true;
                JOptionPane.showMessageDialog(this, "Black Wins!!!!!!"); //display winner message
                winflag = winflag +1;
            }
            if (flag == 2) { //if it is white's turn
                Sound.WhiteWinJingle(); //play the winner sound
                win=true;
                JOptionPane.showMessageDialog(this, "White Wins!!!!!!"); //display winner message
                winflag = winflag +1;
            }
            canPlay = false;//End the game so no more pieces can be placed
        }
    }



    //pre: if the main menu button is clicked
    //post:
    //This method ends the game and opens main menu
    public void restartGame(){
        for(int i=0;i<allChess.length;i++) { //for every row
            for(int j=0;j<allChess.length;j++) //for every column
                allChess[i][j] = 0; //clear the spot
        }
        Sound.ClickButton(); //play a button sound
        dispose(); //dispose the old window
        new MainMenu(); //open a new main menu


        flag=1;// Default to black move first
        canPlay=true;
    }

    //pre: none
    //post:
    //This method implements listeners to the Main Menu and Exit buttons so they are functional
    class MybuttonListener  implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==restart) { //if restart button was chosen
                restartGame();
            }

            if(e.getSource()==exit) { //if exit button was chosen
                Sound.ClickButton(); //play button click sound
                System.exit(0);
            }
        }
    }
}



