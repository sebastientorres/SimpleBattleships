package game;

/**
 * Created by st on 18/07/14.
 * For the game.gui
 */

import javax.swing.*;
import java.awt.*;


public class gui {

    public static String userMessage(char selector) {

        /*
        Message types \ prefixes to String name:
            - m --    message
            - w --    warning
            - r --    results
            - i --    instructions
            - t --    title
         */


        // case 0
        String mWelcome = new String("A pretty simple game, choose coordinates x and y, where the values are between 1" +
                " and 25 inclusive,with a comma ',' in between, for example: 23,2");

        // case 1
        String iQuit = new String("Enter 'q' to quit the game.");

        // case 2
        String iEnterCoords = new String("Enter coordinates as 'x,y'");

        // case 3
        String rMissed = "Missed...";


        String message = new String();

        switch (selector) {
            case '0':
                message = mWelcome;
                break;
            case '1':
                message = iQuit;
                break;
            case '2':
                message = iEnterCoords;
                break;
            case '3':
                message = rMissed;
                break;
        }

        return message;
    }

        /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Simple Battleships");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setSize(600, 200);

        JPanel panel = new JPanel(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();

        JLabel label = new JLabel(userMessage('0'));
        label.setVisible(true);
        //frame.getContentPane().add(label);

        panel.add(label);
        panel.setVisible(true);

        //Display the window.
        //frame.pack();
        frame.setVisible(true);
        frame.setContentPane(panel);
    }

    public static void initGui() {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
