//pre: if How to Play button is clicked from the Main Menu
//post:
// Displays the rules on how to play Gomoku

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//pre: if how to play button is clicked
//post:
//Sets up fonts, windows, labels, containers, and frames used in this class
public class HowToPlay {
    JFrame window;
    Container con;
    JPanel titleNamePanel, GoBackPanel, TextPanel;
    JLabel titleNameLabel, TextLabel, TextLabel2, TextLabel3, TextLabel4, TextLabel5, TextLabelEmpty;
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 50);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    Font textFont = new Font("Times New Roman", Font.PLAIN, 20);
    JButton GoBackButton;

    MainMenu frame;

    //pre: if how to play button is clicked
    //post:
    //Calls upon the how to play method
    public static void main(String[] args) {
        new HowToPlay();
    }

    //pre: none
    //post:
    //This method displays the how to play screen which includes all the instructions on how to play
    public HowToPlay() {

        window = new JFrame();
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.DARK_GRAY);
        window.setLayout(null);
        window.setVisible(true);
        con = window.getContentPane();

        GoBackPanel = new JPanel();
        GoBackPanel.setBounds(300, 450, 200, 60);
        GoBackPanel.setBackground(Color.white);

        titleNamePanel = new JPanel(); //Set up title panel
        titleNamePanel.setBounds(75, 50, 650, 75);
        titleNamePanel.setBackground(Color.black);
        titleNameLabel = new JLabel("How To Play"); //Label it
        titleNameLabel.setForeground(Color.white);
        titleNameLabel.setFont(titleFont);

        TextPanel = new JPanel(); //Setup text panel with How to Play instructions
        TextPanel.setBounds(75, 150, 650, 275);
        TextPanel.setBackground(Color.black);
        TextLabel = new JLabel ("Gomoku, also known as Wu-zi-qi, is an ancient Chinese game where two players");
        TextLabel2 = new JLabel("take turns placing Black and White colored stones onto a grid. The first to");
        TextLabel3 = new JLabel("place five of the same color in a row, column, or diagonal wins the game.");
        TextLabel4 = new JLabel("1-Player Mode: Play against the computer in a game of Gomoku!");
        TextLabel5 = new JLabel("2-Player Mode: Play against another player locally, alternating turns!");
        TextLabelEmpty = new JLabel("This is intentionally left empty.");
        TextLabel.setForeground(Color.white);
        TextLabel2.setForeground(Color.white);
        TextLabel3.setForeground(Color.white);
        TextLabel4.setForeground(Color.white);
        TextLabel5.setForeground(Color.white);
        TextLabelEmpty.setForeground(Color.black); //This is black to keep it invisible, it's meant to be a space
        TextLabel.setFont(textFont);
        TextLabel2.setFont(textFont);
        TextLabel3.setFont(textFont);
        TextLabel4.setFont(textFont);
        TextLabel5.setFont(textFont);
        TextLabelEmpty.setFont(textFont);

        GoBackButton = new JButton("Go back"); //Return to the Main Menu
        GoBackButton.setBackground(Color.black);
        GoBackButton.setForeground(Color.white);
        GoBackButton.setFont(normalFont);

        con.setVisible(true);

        titleNamePanel.add(titleNameLabel);
        TextPanel.add(TextLabel);
        TextPanel.add(TextLabel2);
        TextPanel.add(TextLabel3);
        TextPanel.add(TextLabelEmpty);
        TextPanel.add(TextLabelEmpty);
        TextPanel.add(TextLabel4);
        TextPanel.add(TextLabel5);

        GoBackPanel.add(GoBackButton);

        con.add(titleNamePanel);
        con.add(GoBackPanel);
        con.add(TextPanel);

        GoBackButton.addActionListener (buttonListener);

        con.revalidate();

    }

    HowToPlay.MybuttonListener buttonListener =new HowToPlay.MybuttonListener();

    //pre: none
    //post:
    //This tells the buttons what to do
    class MybuttonListener  implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            window.dispose();

            if(evt.getSource()==GoBackButton){
                frame = new MainMenu(); //Put MainMenu here
            }

        }
    }
}

