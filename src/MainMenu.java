//pre: MainMenu is called from Main
//post:
// Displays the main menu with buttons

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//pre: Main Menu is called
//post:
// Displays the main menu with buttons
public class MainMenu {
    JFrame window;
    Container con;
    JPanel titleNamePanel, startButtonPanel;
    JLabel titleNameLabel;
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 90);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    JButton OnePButton;
    JButton TwoPButton;
    JButton HelpButton;
    JButton QuitButton;

    MyFrame frame;
    HowToPlay howtoplay;

    public boolean twoPlayer;


    //pre: none
    //post:
    // Calls upon the MainMenu method
    public static void main(String[] args) {
        new MainMenu();
    }

    //pre: none
    //post:
    //This method displays the Main Menu
    public MainMenu() {

        window = new JFrame();
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.DARK_GRAY);
        window.setLayout(null);
        window.setVisible(true);
        con = window.getContentPane();

        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(300, 350, 200, 205);
        startButtonPanel.setBackground(Color.white);

        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(100, 100, 600, 150);
        titleNamePanel.setBackground(Color.black);
        titleNameLabel = new JLabel("GOMOKU");
        titleNameLabel.setForeground(Color.white);
        titleNameLabel.setFont(titleFont);

        OnePButton = new JButton("1-Player");
        OnePButton.setBackground(Color.black);
        OnePButton.setForeground(Color.white);
        OnePButton.setFont(normalFont);

        con.setVisible(true);

        TwoPButton = new JButton("2-Player");
        TwoPButton.setBackground(Color.black);
        TwoPButton.setForeground(Color.white);
        TwoPButton.setFont(normalFont);

        con.setVisible(true);

        QuitButton = new JButton("Quit");
        QuitButton.setBackground(Color.black);
        QuitButton.setForeground(Color.white);
        QuitButton.setFont(normalFont);

        con.setVisible(true);

        HelpButton = new JButton("How to Play");
        HelpButton.setBackground(Color.black);
        HelpButton.setForeground(Color.white);
        HelpButton.setFont(normalFont);

        con.setVisible(true);

        titleNamePanel.add(titleNameLabel);
        startButtonPanel.add(OnePButton);
        startButtonPanel.add(TwoPButton);
        startButtonPanel.add(HelpButton);
        startButtonPanel.add(QuitButton);
        con.add(titleNamePanel);
        con.add(startButtonPanel);

        OnePButton.addActionListener (buttonListener);
        TwoPButton.addActionListener (buttonListener);
        HelpButton.addActionListener (buttonListener);
        QuitButton.addActionListener (buttonListener);

        con.revalidate();

    }


    MybuttonListener buttonListener =new MybuttonListener();

    //pre: none
    //post:
    // Button Listener to make the Main Menu buttons work
    class MybuttonListener  implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            Sound.ClickButton();
            window.dispose();

            if(evt.getSource()==OnePButton){
                frame = new MyFrame(false); //Put Oneplayer here
                frame.setVisible(true);
            }
            if(evt.getSource()==TwoPButton){
                frame = new MyFrame(true); //Put Twoplayer here
                frame.setVisible(true);
            }
            if(evt.getSource()==QuitButton){ //Quits game if this button is pressed
                System.exit(0);
            }
            if(evt.getSource()==HelpButton){
                howtoplay = new HowToPlay(); //Put help page here
            }

        }
    }
}








