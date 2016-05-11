//(c) 2015 John Freeman
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.Iterator;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class menuGUI extends JFrame{

  //Specifications about how the GUI is displayed
  private static final String TITLE = "Jose Really Hates Puppies";
  private static final int WIDTH = 500;
  private static final int HEIGHT = 600;

  //Container to hold everything that needs to be displayed
  private Container content;

  private PlayerNode node;

  private JLabel info;

  private JButton startButton;
  private JButton randButton;
  private JButton refreshButton;
  private JButton logoutButton;

  private JTextArea games;
  private Highlighter highlighter;
  private HighlightPainter painter;

  String user;
  String port;
  String master;

  //Constructor
  public menuGUI(String user, String port, String master){
    this.user = user;
    this.port = port;
    this.master = "137.165.8.91";

    //Sets dimensions of the window
    setTitle(TITLE);
    setSize(WIDTH, HEIGHT);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    //Initialize Content Pane
    content = getContentPane();
    content.setLayout(new BorderLayout());

    info = new JLabel("<html>UserName: "+user+"<br>Port: "+port+"<br>Master: "+master+"</html>");
    content.add(info, BorderLayout.NORTH);

    Container buttons = new Container();
    buttons.setLayout(new GridLayout(4,1));

    startButton = new JButton("Start Game");
    startButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        startGame();
      }
    });
    buttons.add(startButton);

    randButton = new JButton("Random Game");
    randButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        connectRandomGame();
      }
    });
    buttons.add(randButton);

    refreshButton = new JButton("Refresh");
    refreshButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        refresh();
      }
    });
    buttons.add(refreshButton);

    logoutButton = new JButton("Logout");
    logoutButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        logout();
      }
    });
    buttons.add(logoutButton);

    content.add(buttons, BorderLayout.EAST);

    JScrollPane scrollPane;

    games = new JTextArea(20, 25);
    scrollPane = new JScrollPane(games);
    games.setEditable(false);
    content.add(scrollPane, BorderLayout.WEST);

    highlighter = games.getHighlighter();
    painter = new DefaultHighlighter.DefaultHighlightPainter(Color.lightGray);

    //set window visible
    setVisible(true);
  }

  private void startGame() {
    node = new PlayerNode(user, port);
    node.startGame();
    new tictacGUI(node);
    System.out.println("In menu Start -" + node.getHosts());
    // Host stuff for a game
  }

  private void connectRandomGame() {
    node = new PlayerNode(user, port, master);
    System.out.println("In menu Random 1-" + node.getHosts());
    if (node.connectToRandom()){
      System.out.println("In menu Random 2-" + node.getHosts());
      // Get the gameboard to display (set in connectToRandom)
      new tictacGUI(node);
      /*while(node.inGame()){
        if(!node.isPlaying()){
          node.waitForTurn();
        }else{
          //node.playTurn();
        }
	}*/
    }
  }

  private void refresh() {
    games.setText("");
    node.refresh();
    games.setText(node.getHosts().toString());
  }

  private void logout() {
    System.exit(1);
    node.logout();
  }

}
