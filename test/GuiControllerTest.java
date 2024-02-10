import org.junit.Before;
import org.junit.Test;

import controller.GUIControllerImpl;

import static org.junit.Assert.assertTrue;


/**
 * GuiControllerTest is a test class for the GUIController.
 */
public class GuiControllerTest {

  private GUIControllerImpl controller;
  private MockGuiModel mockModel;
  private MockImageProcessingGui mockView;

  @Before
  public void setUp() {
    mockModel = new MockGuiModel(new StringBuilder(), 1);
    controller = new GUIControllerImpl(mockModel);
    mockView = new MockImageProcessingGui(controller);
    controller.setView(mockView);
    mockView.setLoadFilePath("res/train.jpg");
    controller.handleButtonClick("load");

  }

  @Test
  public void testHandleLoadButtonClick() {
    mockView.setLoadFilePath("res/train.jpg");
    controller.handleButtonClick("load");
    assertTrue(mockModel.isMethodCalled("load"));
    assertTrue(mockView.isDisplayImageMethodCalled());
  }

  @Test
  public void testHandleRedComponentButtonClick() {
    controller.handleButtonClick("redComponent");

    assertTrue(mockModel.isMethodCalled("red"));
    assertTrue(mockView.isDisplayImageMethodCalled());
  }

  @Test
  public void testHandleBlueComponentButtonClick() {
    controller.handleButtonClick("blueComponent");

    assertTrue(mockModel.isMethodCalled("blue"));
    assertTrue(mockView.isDisplayImageMethodCalled());
  }

  @Test
  public void testHandleGreenComponentButtonClick() {
    controller.handleButtonClick("greenComponent");

    assertTrue(mockModel.isMethodCalled("green"));
    assertTrue(mockView.isDisplayImageMethodCalled());
  }

  @Test
  public void testHandleBlurButtonClick() {
    controller.handleButtonClick("blur");

    assertTrue(mockModel.isMethodCalled("blur"));
    assertTrue(mockView.isDisplayImageMethodCalled());
  }

  @Test
  public void testHandleSharpenButtonClick() {
    controller.handleButtonClick("sharpen");

    assertTrue(mockModel.isMethodCalled("sharpen"));
    assertTrue(mockView.isDisplayImageMethodCalled());
  }

  @Test
  public void testHandleVerticalFlipButtonClick() {
    controller.handleButtonClick("verticalFlip");

    assertTrue(mockModel.isMethodCalled("verticalFlip"));
    assertTrue(mockView.isDisplayImageMethodCalled());
  }

  @Test
  public void testHandleHorizontalFlipButtonClick() {
    controller.handleButtonClick("horizontalFlip");

    assertTrue(mockModel.isMethodCalled("horizontalFlip"));
    assertTrue(mockView.isDisplayImageMethodCalled());
  }

  @Test
  public void testHandleLumaButtonClick() {
    controller.handleButtonClick("luma");

    assertTrue(mockModel.isMethodCalled("luma"));
    assertTrue(mockView.isDisplayImageMethodCalled());
  }

  @Test
  public void testHandleSepiaButtonClick() {
    controller.handleButtonClick("sepia");

    assertTrue(mockModel.isMethodCalled("sepia"));
    assertTrue(mockView.isDisplayImageMethodCalled());
  }

  @Test
  public void testHandleCompressionButtonClick() {
    mockView.setInput("50");
    controller.handleButtonClick("compression");

    assertTrue(mockModel.isMethodCalled("compress"));
    assertTrue(mockView.isDisplayImageMethodCalled());
  }

  @Test
  public void testHandleColorCorrectButtonClick() {
    controller.handleButtonClick("colorCorrect");

    assertTrue(mockModel.isMethodCalled("colorCorrect"));
    assertTrue(mockView.isDisplayImageMethodCalled());
  }

  @Test
  public void testHandleAdjustLevelsButtonClick() {
    mockView.setInput("0");
    mockView.setInput("128");
    mockView.setInput("255");
    controller.handleButtonClick("adjustLevels");

    assertTrue(mockModel.isMethodCalled("levelAdjustment"));
    assertTrue(mockView.isDisplayImageMethodCalled());
  }
}
