package cs3500.marblesolitaire.model.hw02;

class Util {

  private static boolean isBetween(int value, int min, int max) {
    return ((value > min) && (value < max));
  }

  protected static boolean doesCoordExistInBoard(int armThickness, int row, int col) {
    return (isBetween(col, armThickness - 2, armThickness * 2 - 1) &&
        isBetween(row, -1, armThickness * 3 - 2))
        || (isBetween(row, armThickness - 2, armThickness * 2 - 1) &&
        isBetween(col, -1, armThickness * 3 - 2));
  }

  protected static String isItIllegalMove(MarbleSolitaireModelImpl game, int fromRow, int fromCol,
      int toRow, int toCol) {

    if (!Util.doesCoordExistInBoard(game.armThickness, fromRow, fromCol)
        || game.board[fromRow][fromCol] == '_') {
      return "Cannot move a non-existing marble";
    }
    if (!Util.doesCoordExistInBoard(game.armThickness, toRow, toCol)) {
      return "Cannot move to a non-existing position";
    }

    if (game.board[toRow][toCol] == 'O') {
      return "Cannot move to an occupied position";
    }

    if ((fromCol != toCol && fromRow != toRow)) {
      return "Can only move horizontally or vertically";
    }

    if (!(Math.abs(toCol - fromCol) == 2 || Math.abs(toRow - fromRow) == 2)) {
      return "Cannot move to a space that is not exactly two positions away";
    }

    if (game.board[fromRow + ((toRow - fromRow) / 2)]
        [fromCol + ((toCol - fromCol) / 2)] != 'O') {
      return "Must jump over a marble";
    }

    return null;
  }
}
