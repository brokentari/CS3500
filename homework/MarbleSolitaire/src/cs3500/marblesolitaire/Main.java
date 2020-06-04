package cs3500.marblesolitaire;

import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelImpl;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModelImpl;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModelImpl;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


/**
 * Main method for the {@link MarbleSolitaireControllerImpl}.
 */
public class Main {

  /**
   * Creates the controller for the a {@link MarbleSolitaireModel}, where the user can choose the
   * type of board and any specific.
   *
   * @param args specifies the type of board (triangular, european, english) and any customization
   *             with {@code -size} <i>armThickness</i> and {@code -hole} <i>R C</i>
   */
  public static void main(String[] args) {
    Map<String, Integer> parameters = new HashMap<>();

    setDefaultParameters(args[0], parameters);

    for (int i = 1; i < args.length; i++) {
      if (args[i].contains("-")) {
        switch (args[i]) {
          case "-size":
            parameters.replace("size", Integer.parseInt(args[i + 1]));
            break;
          case "-hole":
            parameters.replace("row", Integer.parseInt(args[i + 1]) - 1);
            parameters.replace("col", Integer.parseInt(args[i + 2]) - 1);
            break;
          default:
            System.out.print("unknown parameter");
            break;
        }
      }
    }

    MarbleSolitaireModel model = createModel(args[0], parameters);

    new MarbleSolitaireControllerImpl(new InputStreamReader(System.in),
        System.out).playGame(model);
  }

  private static void setDefaultParameters(String boardType, Map<String, Integer> parameters) {
    switch (boardType) {
      case "english":
      case "european": {
        parameters.put("size", 3);
        parameters.put("row", 3);
        parameters.put("col", 3);
        break;
      }
      case "triangular": {
        parameters.put("size", 5);
        parameters.put("row", 0);
        parameters.put("col", 0);
        break;
      }
      default:
        throw new IllegalArgumentException("unknown board type");
    }
  }

  private static MarbleSolitaireModel createModel(String boardType,
      Map<String, Integer> parameters) {
    try {
      switch (boardType) {
        case "english":
          return new MarbleSolitaireModelImpl(parameters.get("size"),
              parameters.get("row"), parameters.get("col"));
        case "european":
          return new EuropeanSolitaireModelImpl(parameters.get("size"),
              parameters.get("row"), parameters.get("col"));
        case "triangular":
          return new TriangleSolitaireModelImpl(parameters.get("size"),
              parameters.get("row"), parameters.get("col"));
        default:
          System.out.println("Unknown board type");
      }
    } catch (IllegalArgumentException e) {
      System.out.print("Invalid board parameters");
    }

    return null;
  }
}

