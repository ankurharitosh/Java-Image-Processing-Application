package controller;

import controller.commands.CompressionHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import controller.commands.BlurHelper;
import controller.commands.BrightenHelper;
import controller.commands.ColorComponentHelper;
import controller.commands.ColorCorrectHelper;
import controller.commands.FlipImageHelper;
import controller.commands.HistogramHelper;
import controller.commands.IOHelper;
import controller.commands.ImageChannelsHelper;
import controller.commands.LevelAdjustmentHelper;
import controller.commands.RGBCombineHelper;
import controller.commands.RGBSplitHelper;
import controller.commands.SepiaHelper;
import controller.commands.SharpenHelper;
import model.Model;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */

public class ControllerImpl implements Controller {

  private final Readable in;
  private final Appendable out;
  private Scanner scan;

  /**
   * Constructs a ControllerImpl with the specified input and output streams.
   *
   * @param in  The input stream for reading user commands.
   * @param out The output stream for displaying results.
   */
  public ControllerImpl(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  /**
   * Executes actions based on user commands read from the input stream.
   *
   * @param model The model used to perform actions based on user commands.
   * @throws IOException If an I/O error occurs during execution.
   */
  public void execute(Model model, String[] args) throws IOException {
    if (args.length > 1 && args[0].equalsIgnoreCase("-file")) {
      try {
        scan = new Scanner(new File(args[1]));
      } catch (FileNotFoundException e) {
        throw new IOException("Error: Script file not found.");
      }
    } else if (args.length > 0 && args[0].equalsIgnoreCase("-text")) {
      scan = new Scanner(this.in);
    }
    executeCommands(model);
  }

  private void executeCommands(Model model) throws IOException {

    Map<String, Function<Scanner, CommandDesign>> knownCommands = new HashMap<>();
    knownCommands.put("load", s -> new IOHelper("load", s.next(), s.next()));
    knownCommands.put("save", (Scanner s) -> {
      return new IOHelper("save", s.next(), s.next());
    });
    knownCommands.put("red-component", (Scanner s) -> {
      return new ColorComponentHelper("red-component", s.next(), s.next());
    });
    knownCommands.put("green-component", (Scanner s) -> {
      return new ColorComponentHelper("green-component", s.next(), s.next());
    });
    knownCommands.put("blue-component", (Scanner s) -> {
      return new ColorComponentHelper("blue-component", s.next(), s.next());
    });
    knownCommands.put("value-component", (Scanner s) -> {
      String imageName = s.next();
      String destImageName = s.next();
      double percentage = 100;
      if (s.hasNext("split")) {
        s.next();
        percentage = s.nextDouble();
      }
      return new ImageChannelsHelper("value-component", imageName, destImageName, percentage);
    });
    knownCommands.put("luma-component", (Scanner s) -> {
      String imageName = s.next();
      String destImageName = s.next();
      double percentage = 100;
      if (s.hasNext("split")) {
        s.next();
        percentage = s.nextDouble();
      }
      return new ImageChannelsHelper("luma-component", imageName, destImageName, percentage);
    });
    knownCommands.put("intensity-component", (Scanner s) -> {
      String imageName = s.next();
      String destImageName = s.next();
      double percentage = 100;
      if (s.hasNext("split")) {
        s.next();
        percentage = s.nextDouble();
      }
      return new ImageChannelsHelper("intensity-component", imageName, destImageName, percentage);
    });
    knownCommands.put("horizontal-flip", (Scanner s) -> {
      return new FlipImageHelper("horizontal-flip", s.next(), s.next());
    });
    knownCommands.put("vertical-flip", (Scanner s) -> {
      return new FlipImageHelper("vertical-flip", s.next(), s.next());
    });
    knownCommands.put("brighten", (Scanner s) -> {
      return new BrightenHelper(s.nextInt(), s.next(), s.next());
    });
    knownCommands.put("rgb-split", (Scanner s) -> {
      return new RGBSplitHelper(s.next(), s.next(), s.next(), s.next());
    });
    knownCommands.put("rgb-combine", (Scanner s) -> {
      return new RGBCombineHelper(s.next(), s.next(), s.next(), s.next());
    });
    knownCommands.put("blur", (Scanner s) -> {
      String imageName = s.next();
      String destImageName = s.next();
      double percentage = 100;
      if (s.hasNext("split")) {
        s.next();
        percentage = s.nextDouble();
      }
      return new BlurHelper(imageName, destImageName, percentage);
    });
    knownCommands.put("sharpen", (Scanner s) -> {
      String imageName = s.next();
      String destImageName = s.next();
      double percentage = 100;
      if (s.hasNext("split")) {
        s.next();
        percentage = s.nextDouble();
      }
      return new SharpenHelper(imageName, destImageName, percentage);
    });
    knownCommands.put("sepia", (Scanner s) -> {
      String imageName = s.next();
      String destImageName = s.next();
      double percentage = 100;
      if (s.hasNext("split")) {
        s.next();
        percentage = s.nextDouble();
      }
      return new SepiaHelper(imageName, destImageName, percentage);
    });
    knownCommands.put("compress", (Scanner s) -> {
      return new CompressionHelper(s.nextDouble(), s.next(), s.next());
    });
    knownCommands.put("histogram", (Scanner s) -> {
      return new HistogramHelper(s.next(), s.next());
    });
    knownCommands.put("color-correct", (Scanner s) -> {
      String imageName = s.next();
      String destImageName = s.next();
      double percentage = 100;
      if (s.hasNext("split")) {
        s.next();
        percentage = s.nextDouble();
      }
      return new ColorCorrectHelper(imageName, destImageName, percentage);
    });
    knownCommands.put("levels-adjust", (Scanner s) -> {
      int shadow = s.nextInt();
      int mid = s.nextInt();
      int highlight = s.nextInt();
      String imageName = s.next();
      String destImageName = s.next();
      double percentage = 100;
      if (s.hasNext("split")) {
        s.next();
        percentage = s.nextDouble();
      }
      return new LevelAdjustmentHelper(shadow, mid, highlight, imageName, destImageName,
          percentage);
    });

    while (scan.hasNext()) {
      CommandDesign c;
      String command = scan.next();
      if (command.startsWith("#")) {
        scan.nextLine();
        continue;
      }
      if (command.equals("run")) {
        String script_file = scan.next();
        scan = new Scanner(new File(script_file));
        continue;
      }
      System.out.println(command);

      if (command.equalsIgnoreCase("q") || command.equalsIgnoreCase("quit")) {
        return;
      }
      Function<Scanner, CommandDesign> cmd = knownCommands.getOrDefault(command, null);
      if (cmd == null) {
        this.out.append("Invalid Input \n");
        return;
      } else {
        c = cmd.apply(scan);
        c.execute(model);
      }
    }
  }
}