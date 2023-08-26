package GameOfLife;

public class Cell {

    /**
     * Função responsável por fazer o jogo correr, criando uma borda nova a partir da anterior, e a substituindo; possibilitando a mudança de gerações.
     * @param board a borda que vai ser parâmetro para seguir as regras da vida.
     */
    public void running(int[][] board) {
        int[][] newBoard = new int[board.length][board[0].length];

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                int cellIsAlive = checkNeighbor(board, row, column);
                newBoard[row][column] = checkIsAlive(board, row, column, cellIsAlive);
            }
        }

        for (int row = 0; row < board.length; row++) {
            System.arraycopy(newBoard[row], 0, board[row], 0, board[row].length);
        }
    }

    /**
     * Função responsável por checar as condições dos vizinhos, contando as células vivas e as retornando para que o jogo possa ser atualizado nas próximas gerações.
     * @param board borda.
     * @param row linha.
     * @param column coluna.
     * @return retorna a contagem das células vivas.
     */
    public int checkNeighbor(int[][] board, int row, int column){

        int cellIsAlive = 0;

        for(int r = -1; r <= 1; r++){
            for(int c = -1; c <= 1; c++){

                int neighRow = row + r; // pegando as coordenadas da row
                int neighColumn = column + c; // pegando as coordenadas da column

                if(neighRow >= 0 && neighRow < board.length && neighColumn >= 0 && neighColumn < board[0].length){ // verifica se está dentro da vizinhança
                    int neighborhood = board[neighRow][neighColumn];
                    if (neighborhood == 1 && !(r == 0 && c == 0)){ // checa se a celula está viva e se não é a própria célula
                        cellIsAlive++;
                    }
                }
            }
        }
        return cellIsAlive;
    }

    /**
     * Função responsável por aplicar as regras da vida, para verificar se uma célula vive ou não.
     * @param board tela.
     * @param row linha.
     * @param column coluna.
     * @param cellIsAlive células vivas.
     * @return retorna a nova tela com as novas condições das células.
     */
    public int checkIsAlive(int[][] board, int row, int column, int cellIsAlive) {
        if (board[row][column] == 1 && (cellIsAlive < 2 || cellIsAlive > 3)) {
            return 0;
        } else if (board[row][column] == 0 && cellIsAlive == 3) {
            return 1;
        }
        return board[row][column];
    }
}

