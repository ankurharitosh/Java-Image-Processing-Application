package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


import controller.GuiController;

/**
 * GUI for image processing operations, allows user to graphically interact with the application.
 */
public class ImageProcessingGUI extends JFrame implements View {

  private JLabel imageLabel;
  private JButton loadButton;
  private JButton saveButton;
  private JButton redButton;
  private JButton greenButton;
  private JButton originalButton;
  private JButton blurButton;
  private JButton sharpenButton;
  private JButton verticalFlip;
  private JButton horizontalFlip;
  private JButton blueButton;
  private JButton lumaButton;
  private JButton sepiaButton;
  private JButton compressionButton;
  private JButton colorCorrectButton;
  private JButton adjustLevelsButton;
  private JLabel histogramLabel;
  private JToggleButton splitToggleButton;
  private BufferedImage loadedImage;
  private final GuiController controller;
  private JPanel leftPanel;
  private JPanel southPanel;
  private JPanel rightPanel;

  /**
   * Constructor for the GUI View of the application. Takes controller as an argument.
   *
   * @param controller the controller for the GUI.
   */
  public ImageProcessingGUI(GuiController controller) {
    this.controller = controller;
    initComponents();
    addComponents();
    addListeners();
    this.controller.setView(this);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void initComponents() {
    southPanel = new JPanel(new BorderLayout());
    leftPanel = new JPanel(new GridLayout(14, 1));
    rightPanel = new JPanel(new BorderLayout());
    southPanel.setLayout(new GridLayout(1, 2));
    imageLabel = new JLabel();
    histogramLabel = new JLabel();
    histogramLabel.setPreferredSize(new Dimension(300, 300));
    loadButton = new JButton("Load Image");
    saveButton = new JButton("Save Image");

    originalButton = new JButton("Original");

    redButton = new JButton("Red Component");
    greenButton = new JButton("Green Component");
    blueButton = new JButton("Blue Component");

    verticalFlip = new JButton("Vertical Flip");
    horizontalFlip = new JButton("Horizontal Flip");

    blurButton = new JButton("Blur");
    sharpenButton = new JButton("Sharpen");

    lumaButton = new JButton("Greyscale");
    sepiaButton = new JButton("Sepia");
    compressionButton = new JButton("Compression Artifacts");
    colorCorrectButton = new JButton("Color Correction");
    adjustLevelsButton = new JButton("Adjust Levels");
    splitToggleButton = new JToggleButton("Image Split Toggle");

    imageLabel.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        displayImage(loadedImage);
      }
    });
  }

  private void addComponents() {
    Container container = getContentPane();
    container.setLayout(new BorderLayout());

    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    container.add(imageScrollPane, BorderLayout.CENTER);

    southPanel.add(loadButton);
    southPanel.add(saveButton);
    container.add(southPanel, BorderLayout.SOUTH);
    container.add(rightPanel, BorderLayout.EAST);

    JScrollPane histogramScrollPane = new JScrollPane(histogramLabel);
    rightPanel.add(histogramScrollPane, BorderLayout.CENTER);

    leftPanel.add(originalButton);
    leftPanel.add(redButton);
    leftPanel.add(greenButton);
    leftPanel.add(blueButton);
    leftPanel.add(verticalFlip);
    leftPanel.add(horizontalFlip);
    leftPanel.add(blurButton);
    leftPanel.add(sharpenButton);
    leftPanel.add(lumaButton);
    leftPanel.add(sepiaButton);
    leftPanel.add(compressionButton);
    leftPanel.add(colorCorrectButton);
    leftPanel.add(adjustLevelsButton);
    leftPanel.add(splitToggleButton);
    container.add(leftPanel, BorderLayout.WEST);
  }

  private void addListeners() {
    loadButton.addActionListener(e -> controller.handleButtonClick("load"));
    saveButton.addActionListener(e -> controller.handleButtonClick("save"));

    originalButton.addActionListener(e -> controller.handleButtonClick("original"));

    redButton.addActionListener(e -> controller.handleButtonClick("redComponent"));
    blueButton.addActionListener(e -> controller.handleButtonClick("blueComponent"));
    greenButton.addActionListener(e -> controller.handleButtonClick("greenComponent"));

    verticalFlip.addActionListener(e -> controller.handleButtonClick("verticalFlip"));
    horizontalFlip.addActionListener(e -> controller.handleButtonClick("horizontalFlip"));

    blurButton.addActionListener(e -> controller.handleButtonClick("blur"));
    sharpenButton.addActionListener(e -> controller.handleButtonClick("sharpen"));

    lumaButton.addActionListener(e -> controller.handleButtonClick("luma"));
    sepiaButton.addActionListener(e -> controller.handleButtonClick("sepia"));
    compressionButton.addActionListener(e -> controller.handleButtonClick("compression"));
    colorCorrectButton.addActionListener(e -> controller.handleButtonClick("colorCorrect"));
    adjustLevelsButton.addActionListener(e -> controller.handleButtonClick("adjustLevels"));
    splitToggleButton.addActionListener(e -> controller.handleButtonClick("splitToggle"));

  }

  /**
   * Displays the given image on the GUI.
   *
   * @param image BufferedImage to be displayed.
   */
  public void displayImage(BufferedImage image) {
    if (image != null) {
      ImageIcon imageIcon = new ImageIcon(image);
      imageLabel.setIcon(imageIcon);
      imageLabel.revalidate();
      imageLabel.repaint();
      loadedImage = image;
      updateHistogram();
    }
  }

  private void updateHistogram() {
    BufferedImage newHistogramImage = controller.getHistogramImage();

    if (newHistogramImage != null) {
      ImageIcon histogramIcon = new ImageIcon(newHistogramImage);
      histogramLabel.setIcon(histogramIcon);
      histogramLabel.revalidate();
      histogramLabel.repaint();
    }
  }

  /**
   * Handles the load button click event.
   *
   * @return File selected by the user through file chooser.
   */
  public File handleLoadButtonClick() {
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files",
        "jpg", "png", "ppm", "jpeg");
    fileChooser.setFileFilter(filter);
    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile();
    }
    return null;
  }

  /**
   * Shows a file chooser dialog for the save button.
   *
   * @return File selected by the user for saving.
   */
  public File showSaveButtonFileChooser() {
    if (loadedImage != null) {
      JFileChooser fileChooser = new JFileChooser();
      int result = fileChooser.showSaveDialog(this);
      if (result == JFileChooser.APPROVE_OPTION) {
        return fileChooser.getSelectedFile();
      }
    }
    return null;
  }

  /**
   * Gets user input within a specified range using a slider.
   *
   * @param inputMessage Message to be displayed to the user.
   * @return User input as a String.
   */
  public String getInput(String inputMessage) {
    return JOptionPane.showInputDialog(inputMessage);
  }

  /**
   * Displays an error message dialog.
   *
   * @param inputMessage Error message to be displayed.
   */
  public void showErrorMessage(String inputMessage) {
    JOptionPane.showMessageDialog(this, inputMessage, "Error",
        JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Displays a warning message dialog.
   *
   * @param inputMessage Warning message to be displayed.
   */
  public void showWarningMessage(String inputMessage) {
    JOptionPane.showMessageDialog(this, inputMessage, "Warning",
        JOptionPane.WARNING_MESSAGE);
  }

  /**
   * Displays a general message dialog.
   *
   * @param inputMessage Message to be displayed.
   */
  public void showMessage(String inputMessage) {
    JOptionPane.showMessageDialog(this, inputMessage, "Message",
        JOptionPane.PLAIN_MESSAGE);
  }
}
