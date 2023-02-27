import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class QuizCardPlayer {
  private JFrame frame;
  private JTextArea display;
  private JTextArea answer;
  private ArrayList<QuizCard> cardList;
  private QuizCard current;
  private int currentIndex;
  private JButton nextButton;
  private boolean isShowAnswer;

  public static void main(String[] args) {
    QuizCardPlayer player = new QuizCardPlayer();
    player.setupGUI();
  }

  public void setupGUI() {
    frame = new JFrame("Quiz Card Player");
    JPanel mainPanel = new JPanel();
    Font largeFont = new Font("sanserif", Font.BOLD, 24);
    JScrollPane questionScroll = new JScrollPane(display);
    display = new JTextArea(10, 20);
    display.setFont(largeFont);

    questionScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    questionScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    nextButton = new JButton("Show Question");
    mainPanel.add(questionScroll);
    mainPanel.add(nextButton);

    nextButton.addActionListener(new nextButtonListener());

    JMenuBar menu = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem loadMenuItem = new JMenuItem("Load Card Set");
    loadMenuItem.addActionListener(new loadItemListener());
    fileMenu.add(loadMenuItem);
    menu.add(fileMenu);

    frame.setJMenuBar(menu);
    frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
    frame.setSize(640, 500);
    frame.setVisible(true);
  }

  public class nextButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (isShowAnswer) {
        display.setText(current.getAnswer());
        nextButton.setText("Next Card");
        isShowAnswer = false;
      } else {
        if (currentIndex < cardList.size()) {
          showNextCard();
        } else {
          display.setText("End of Quiz");
          nextButton.setEnabled(false);
        }
      }

    }
  }

  public class loadItemListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      JFileChooser osFiles = new JFileChooser();
      osFiles.showOpenDialog(frame);
      loadFile(osFiles.getSelectedFile());
    }
  }

  private void showNextCard() {
    current = cardList.get(currentIndex);
    currentIndex++;
    display.setText(current.getQuestion());
    nextButton.setText("Show Answer");
    isShowAnswer = true;
  }

  private void makeCard(String line) {
    String[] res = line.split("/");
    QuizCard card = new QuizCard(res[0], res[1]);
    cardList.add(card);
    System.out.println("Card added");
  }

  private void loadFile(File file) {
    cardList = new ArrayList<QuizCard>();

    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line = null;

      while ((line = reader.readLine()) != null) {
        makeCard(line);
      }

      reader.close();
    } catch (Exception e) {
      System.out.println("Couldn't read file");
      e.printStackTrace();
    }

    showNextCard();
  }
}
