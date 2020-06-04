package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Represents a Controller for marble solitaire: handle user moves by executing them using the
 * implemented model; display the resulting game state is some form.
 */
public interface MarbleSolitaireController {

  /**
   * Executes a single game of marble solitaire given a {@link MarbleSolitaireModel}. When the game
   * over, the {@code playGame} method ends. Displays an error in console for any exception thrown.
   *
   * @param m a non-null {@link MarbleSolitaireModel}
   */
  void playGame(MarbleSolitaireModel m);
}
