import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class QuizCardBuilder {
  private JTextArea question;
  private JTextArea answer;
  private ArrayList<QuizCard> cardList;
  private JFrame frame;

  public void setupGUI() {
    frame = new JFrame("Quiz Card Builder");
    JPanel mainPanel = new JPanel();

    Font largeFont = new Font("sanserif", Font.BOLD, 24);

    question = new JTextArea(6, 20);
    question.setLineWrap(true);
    question.setWrapStyleWord(false);
    question.setFont(largeFont);

    JScrollPane scrollPane = new JScrollPane(answer);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    answer = new JTextArea(6, 20);
    answer.setLineWrap(true);
    answer.setWrapStyleWord(false);
    answer.setFont(largeFont);

    JButton nextButton = new JButton("Next Card");

    cardList = new ArrayList<QuizCard>();

    JLabel questionLabel = new JLabel("Question");
    JLabel answerLabel = new JLabel("Answer");

    mainPanel.add(questionLabel);
    mainPanel.add(question);
    mainPanel.add(answerLabel);
    mainPanel.add(answer);
    mainPanel.add(nextButton);

    JMenuBar menu = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem newMenuItem = new JMenuItem("New");
    JMenuItem saveItem = new JMenuItem("Save");

    newMenuItem.addActionListener(new newMenuListener());
    saveItem.addActionListener(new saveItemListener());

    fileMenu.add(newMenuItem);
    fileMenu.add(saveItem);
    menu.add(fileMenu);
    frame.setJMenuBar(menu);

    frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
    frame.setSize(500, 600);
    frame.setVisible(true);

  }

  public class newMenuListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      cardList.clear();
      clearCard();
    }
  }

  public class saveItemListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      QuizCard card = new QuizCard(question.getText(), answer.getText());
      cardList.add(card);

      JFileChooser saveOptions = new JFileChooser();
      saveOptions.showSaveDialog(frame);
      saveFile(saveOptions.getSelectedFile());
    }
  }

  public class nextCardListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      QuizCard card = new QuizCard(question.getText(), answer.getText());
      cardList.add(card);
      clearCard();
    }
  }

  private void clearCard() {
    question.setText("");
    answer.setText("");
    question.requestFocus();
  }

  public void saveFile(File file) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));

      for (QuizCard card : cardList) {
        writer.write(card.getQuestion() + "/");
        writer.write(card.getAnswer() + "\n");
      }

      writer.close();
    } catch (IOException e) {
      System.out.println("Couldn't save all cards");
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    QuizCardBuilder builder = new QuizCardBuilder();
    builder.setupGUI();
  }
}
