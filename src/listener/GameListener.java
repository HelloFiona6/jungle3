package listener;

import model.ChessboardPoint;
import view.CellComponent;
import view.ChessComponent;

public interface GameListener {

    // click an empty cell
    void onPlayerClickCell(ChessboardPoint point, CellComponent component);

    // click a cell with a chess
    void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component);


}
