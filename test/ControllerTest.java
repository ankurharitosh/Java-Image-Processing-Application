import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import controller.Controller;
import controller.ControllerImpl;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the ControllerTest class.
 */
public class ControllerTest {

  private String[] args;

  @Before
  public void setUp() {
    args = new String[]{};
  }

  @Test
  public void testRunScript() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("run script-train");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals(this.getRunOutput(), log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testLoad() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load res/train.jpg trainImage");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: trainImage\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testSave() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load res/train.jpg trainImage\nsave images/train.jpg trainImage");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: trainImage\nInput: trainImage\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testRedComponent() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("red-component trainImage train-red");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: trainImage train-red\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testBlueComponent() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("blue-component trainImage train-blue");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: trainImage train-blue\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testGreenComponent() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("green-component trainImage train-green");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: trainImage train-green\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testValueComponent() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("value-component trainImage train-greyscale");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: trainImage train-greyscale 100.0\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testLumaComponent() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("luma-component trainImage train-luma");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: trainImage train-luma 100.0\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testIntensityComponent() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("intensity-component trainImage train-intensity");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: trainImage train-intensity 100.0\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testBrighten() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("brighten 10 trainImage train-brighter");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: 10 trainImage train-brighter\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testVerticalFlip() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("vertical-flip trainImage train-vertical");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: trainImage train-vertical\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testHorizontalFlip() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("horizontal-flip train-vertical train-vertical-horizontal");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: train-vertical train-vertical-horizontal\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testRGBSplit() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("rgb-split trainImage train-red train-green train-blue");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: trainImage train-red train-green train-blue\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testRGBCombine() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("rgb-combine trainImage train-red train-green train-blue");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: trainImage train-red train-green train-blue\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testBlurComponent() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("blur trainImage train-blur");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: trainImage train-blur 100.0\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testSharpenComponent() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("sharpen trainImage train-sharpen");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: trainImage train-sharpen 100.0\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testSepiaComponent() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("sepia trainImage train-sepia");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: trainImage train-sepia 100.0\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testCompressComponent() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("compress 99 trainImage trainCompressed");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: 99.0 trainImage trainCompressed\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testHistogramComponent() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("histogram trainImage histogramTrain");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: trainImage histogramTrain\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testColorCorrectComponent() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("color-correct galaxy galaxyColorCorrect");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: galaxy galaxyColorCorrect 100.0\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testLevelAdjustComponent() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("levels-adjust 0 128 255 image-name dest-image-name");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Input: 0 128 255 image-name dest-image-name 100.0\n",
        log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testDefaultSwitch() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Do blur image");
    Controller controller = new ControllerImpl(in, out);
    StringBuilder log = new StringBuilder(); //log for mock model
    controller.execute(new MockModel(log, 123), args);
    assertEquals("Invalid Input \n",
        out.toString()); //inputs reached the model correctly
  }

  private String getRunOutput() {
    return "Input: trainImage\n"
        + "Input: red-component trainImage train-red-component\n"
        + "Input: blue-component trainImage train-blue-component\n"
        + "Input: green-component trainImage train-green-component\n"
        + "Input: luma-component trainImage train-luma-component 100.0\n"
        + "Input: intensity-component trainImage train-intensity-component 100.0\n"
        + "Input: trainImage train-blur 100.0\n"
        + "Input: trainImage train-sharpen 100.0\n"
        + "Input: trainImage train-sepia 100.0\n"
        + "Input: 10 trainImage train-brighter\n"
        + "Input: vertical-flip trainImage train-vertical\n"
        + "Input: horizontal-flip train-vertical train-vertical-horizontal\n"
        + "Input: value-component trainImage train-greyscale 100.0\n"
        + "Input: train-brighter\n"
        + "Input: train-greyscale\n"
        + "Input: trainImage\n"
        + "Input: trainImage train-red train-green train-blue\n"
        + "Input: 50 train-red train-red\n"
        + "Input: train-red-tint train-red train-green train-blue\n"
        + "Input: train-red-tint\n";
  }

}
