package controller;

import java.io.IOException;

import model.Model;

/**
 * Controller interface in the MVC design pattern responsible for handling user input, invoking
 * corresponding methods in the Model, and updating the View accordingly
 * with an appropriate message.
 */
public interface Controller {

  /**
   * The execute method accepts inputs from user either through the keyboard or in the form of
   * a predefined script , parses the input and call the respective method in the model
   * to perform an operation if the command is valid else it displays a invalid command
   * message to use or throws an error.
   *
   * @param model The model that provides data and context for the action.
   * @throws IOException If an I/O error occurs during the execution.
   */
  void execute(Model model, String[] args) throws IOException;
}
