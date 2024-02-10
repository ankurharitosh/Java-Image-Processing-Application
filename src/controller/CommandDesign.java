package controller;

import java.io.IOException;

import model.Model;

/**
 * An interface for CommandDesign classes that perform actions based on a given model.
 */
public interface CommandDesign {

  /**
   * The execute operation for CommandDesign classes that perform actions based on a given model.
   */
  void execute(Model model) throws IOException;

}
