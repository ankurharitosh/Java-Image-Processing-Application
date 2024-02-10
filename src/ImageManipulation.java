import controller.GUIControllerImpl;
import java.io.IOException;
import java.io.InputStreamReader;

import controller.Controller;
import controller.ControllerImpl;
import controller.GuiController;
import model.Model;
import model.ModelGUI;
import model.ModelGUIImpl;
import model.ModelImpl;
import view.ImageProcessingGUI;
import view.View;

/**
 * The entry point for the image manipulation application. Reads user commands and delegates
 * execution to the controller.
 */
public class ImageManipulation {

  /**
   * Entry point for the application. Reads user commands and executes actions based on those
   * commands.
   *
   * @param args Command-line arguments (not used).
   */
  public static void main(String[] args) {
    if (args.length > 0) {
      Model model = new ModelImpl();
      Controller controller = new ControllerImpl(new InputStreamReader(System.in), System.out);
      try {
        controller.execute(model, args);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      ModelGUI model = new ModelGUIImpl();
      GuiController guiController = new GUIControllerImpl(model);
      View view = new ImageProcessingGUI(guiController);
      guiController.setView(view);
    }
  }
}
