package GameOfLife;

import java.io.PrintStream;
import java.util.Random;

public class Main {

    private static final PrintStream PRINT = System.out;
    private static final Random RANDOM = new Random();
    private static final int WIDTH = 16;
    private static final int HEIGHT = 9;
    private static final int GENERATION = 30;
    private static final int SPEED = 1000;

    /**
     * Printa a tela para o usuário.
     * @param board tela.
     */

    public static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                PRINT.print(cell + " ");
            }
            PRINT.println();
        }
    }

    /**
     * Escolhe a geração a ser passada pelo terminado, ou retorna um valor padrão.
     * @param args argumentos para passar pelo CLI.
     * @return retorna a geração dada, ou o valor padrão.
     */

    public static int choiceGeneration(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("g=")) {
                return Integer.parseInt(arg.substring(2));
            }
        }
        return GENERATION;  // Valor padrão
    }

    /**
     * Escolhe a velocidade a ser passada pelo terminado, ou retorna um valor padrão.
     * @param args argumentos para passar pelo CLI.
     * @return retorna a velocidade dada, ou o valor padrão.
     */

    public static int choiceSpeed(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("s=")) {
                return Integer.parseInt(arg.substring(2));
            }
        }
        return SPEED;  // Valor padrão
    }

    /**
     * Desenha a tela das populações.
     * @param populationRows o valor atribuído à população.
     * @return retorna o valor da população na tela.
     */

    public static int[][] parsePopulation(String[] populationRows) {
        int height = populationRows.length;
        int width = populationRows[0].length();
        int[][] screen = new int[height][width];

        for (int r = 0; r < height; r++) {
            String row = populationRows[r]; // linha da população inicial
            for (int c = 0; c < width; c++) {
                screen[r][c] = Character.getNumericValue(row.charAt(c)); //pega o caractere atual da coluna na linha, e transforma os valores da população em números
            }
        }

        return screen;
    }

    /**
     * Gera a primeira tela. Separa os atributos das populações pela # e retorna este valor, caso contrário, cai na condição da população aleatória.
     * @param args argumentos da população.
     * @return retorna a população criada com o comando, ou a população criada aleatóriamente com a função de gerar matriz.
     */

    public static int[][] initializeScreen(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("p=")) {
                String populationArg = arg.substring(2); //pega o valor depois do parâmetro=.
                String[] populationRows = populationArg.split("#");
                return parsePopulation(populationRows);
            }
        }
        return generateRandomMatrix(WIDTH, HEIGHT);
    }

    /**
     * Gera uma matriz aleatória.
     * @param rows linhas da matriz.
     * @param columns colunas da matriz.
     * @return retorna a matriz aleatória.
     */

    public static int[][] generateRandomMatrix(int rows, int columns) {
        int[][] matrix = new int[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                matrix[row][column] = RANDOM.nextInt(2);
            }
        }
        return matrix;
    }

    /**
     * Verifica se os valores passados no terminal são validos. se forem, são exibidos, senão, mostra o erro a partir de suas respectivas labels.
     * @param label valor printado no terminal indicando se é válido ou não.
     * @param argValue tipo de argumento que está sendo passado.
     */

    public static void printValue(String label, String argValue) {
        try {
            if (argValue == null) {
                PRINT.println(label + "[Not Present]");
            } else if (label.equals("s=") || label.equals("p=")) {
                PRINT.println(label + "[" + argValue + "]");
            } else {
                int value = Integer.parseInt(argValue);
                PRINT.println(label + "[" + value + "]");
            }
        } catch (NumberFormatException error) {
            PRINT.println(label + "[Invalid]");
        }
    }

    /**
     * Condiciona a saída do usuário dentro do terminal, fazendo com que elas sejam as mesmas que são usadas como referência do outuputLabels.
     * @param args argumentos passados pelo cli.
     */

    public static void output(String[] args) {

        String[] outputLabels = {
                "w=", "h=", "g=",
                "s=", "p=",
        };

        String[] argValues = new String[outputLabels.length];

        for (String value : args) {
            value = value.trim();
            for (int i = 0; i < outputLabels.length; i++) {
                if (value.startsWith(outputLabels[i])) {
                    argValues[i] = value.substring(outputLabels[i].length());
                }
            }
        }

        for (int i = 0; i < outputLabels.length; i++) {
            printValue(outputLabels[i], argValues[i]);
        }
    }

    /**
     * Função main onde o jogo corre, a partir das funções criadas anteriormentes.
     * @param args parâmetros que vão ser recebidos do terminal CLI.
     */

    public static void main(String[] args) {
        output(args);

        int[][] screen = initializeScreen(args);
        int generation = choiceGeneration(args);
        int speed = choiceSpeed(args);

        PRINT.println("Initial State:");
        printBoard(screen);

        Cell cell = new Cell();
        for (int gen = 1; gen <= generation; gen++) {
            cell.running(screen);
            PRINT.println("Generation " + gen + ":");
            printBoard(screen);
            try {
                Thread.sleep(speed);
            } catch (InterruptedException error) {
                Thread.currentThread().interrupt(); // trata o erro sem exibir nada no console
            }
        }
    }
}