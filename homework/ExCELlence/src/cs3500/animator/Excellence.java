package cs3500.animator;

import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.excellence.hw05.model.ExcellenceAnimator;
import cs3500.excellence.hw05.model.ExcellenceAnimatorImpl.Builder;
import cs3500.excellence.hw05.view.AnimationView.VisualAnimationView;
import cs3500.excellence.hw05.view.IView;
import cs3500.excellence.hw05.view.IViewModel;
import cs3500.excellence.hw05.view.SVGAnimationView.SVGAnimationView;
import cs3500.excellence.hw05.view.TextView.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public final class Excellence {

  public static void main(String[] args) throws FileNotFoundException {
    StringBuilder filePath = new StringBuilder();
    String viewType = "";
    int animationSpeed = 1;

    for (int i = 0; i < args.length; i++) {
      if (args[i].contains("-")) {
        switch (args[i]) {
          case "-in":
            String fileName = args[i + 1];
            filePath.append(System.getProperty("user.dir"));
            filePath.append("/src/cs3500/animator/" + fileName);
            break;
          case "-view":
            viewType = args[i + 1];
            break;
          case "-out":
            break;
          case "-speed":
            animationSpeed = Integer.parseInt(args[i + 1]);
            break;
          default:
            break;
        }
      }
    }

    System.out.print(filePath);

    File fileIn = new File(filePath.toString());

    BufferedReader br = new BufferedReader(new FileReader(fileIn));
    AnimationBuilder<ExcellenceAnimator> builder = new Builder();

    ExcellenceAnimator animation = AnimationReader.parseFile(br, builder);
    IView visual = createView(viewType, animation.returnViewModel(), animationSpeed);

    visual.makeVisible();
  }

  private static IView createView(String viewType, IViewModel viewModel, int animationSpeed) {
    switch (viewType) {
      case "visual":
        return new VisualAnimationView(viewModel,animationSpeed);
      case "text":
        return new TextView(viewModel, animationSpeed);
      case "svg":
        break;
      default:
        break;
    }

    return null;
  }
}
