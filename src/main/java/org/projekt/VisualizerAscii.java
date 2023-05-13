package org.projekt;

public class VisualizerAscii implements VisualizerInterface {
    private Board board;
    public VisualizerAscii(Board board){
        this.board = board;
    }
    private StringPair getCellLabels(Cell cell){
        if (cell instanceof BlankCell){
            return new StringPair(".",".");
        } else if (cell instanceof ValueCell) {
            return new StringPair(""+cell.getValue(), "");
        } else if (cell instanceof SummingCell) {
            return new StringPair(""+((SummingCell)cell).getDownTargetValue(), ""+((SummingCell)cell).getRightTargetValue());
        }
        throw new IllegalStateException("Nieznany typ komórki" + cell.getClass());
    }
    @Override
    public void display() {
        for (int row = 0; row<board.getHeight(); row++){
            drawLine(row);
        }
    }

    public void drawLine(int line) {
        String l1 = "";
        String l2 = "";
        String l3 = "";
        for(int col = 0; col < board.getWidth(); col++){
            StringPair pair = getCellLabels(board.get(col, line));
            String l1Formatted = String.format("%1$5s", pair.s1);
            String l2Formatted = String.format("%1$5s", pair.s2);
            l1 += "+-----";
            l2 += "|"+l1Formatted;
            l3 += "|"+l2Formatted;
        }
        System.out.println(l1);
        System.out.println(l2);
        System.out.println(l3);
    }


}
