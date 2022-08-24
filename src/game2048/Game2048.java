package game2048;

import java.util.Random;

public class Game2048 {

    private int SIZE;
    private int score;
    private int map[][];

    public Game2048() {
        SIZE = 4;
        score = 0;
        map = new int[SIZE][SIZE];
        initMap();
    }

    public void initMap() {
        Random rd = new Random();
        boolean check = true;

        int x[], y[];
        x = new int[2];
        y = new int[2];
        score = 0;

        x[0] = (Math.abs(rd.nextInt()) % SIZE);
        y[0] = (Math.abs(rd.nextInt()) % SIZE);

        int val1 = (Math.abs(rd.nextInt()) % 2);
        int val2 = (Math.abs(rd.nextInt()) % 2);

        do {
            int rx = (Math.abs(rd.nextInt()) % SIZE);
            int ry = (Math.abs(rd.nextInt()) % SIZE);
            if (rx != x[0] || ry != y[0]) {
                x[1] = rx;
                y[1] = ry;
                check = false;
            }
//            System.out.println(x[0] + " " + y[0] + " " + rx + " " + ry);
        } while (check);
        

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = 0;
            }
        }
        map[x[0]][y[0]] = 2 + 2 * val1;
        map[x[1]][y[1]] = 2 + 2 * val2;

    }

    public void printMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%5d  ", map[i][j]);
            }
            System.out.println("");
        }
    }

    public void createNum() {
        Random rd = new Random();
        boolean check = false;
        while (!check) {
            int x = (Math.abs(rd.nextInt()) % SIZE);
            int y = (Math.abs(rd.nextInt()) % SIZE);
            int value = Math.abs(rd.nextInt()) % 2;
            if (map[x][y] == 0) {
                map[x][y] = 2 + 2 * value;
                check = true;
            }
        }
    }

    public int[][] initPrevMap() {
        int[][] prevMap = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                prevMap[i][j] = map[i][j];
            }
        }
        return prevMap;
    }

    public boolean isArrSame(int[][] prevMap) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (prevMap[i][j] != map[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void gomTop() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE - 1; j++) {
                if (map[j][i] == map[j + 1][i]) {
                    map[j][i] += map[j + 1][i];
                    score += map[j][i];
                    for (int k = j + 1; k < SIZE - 1; k++) {
                        map[k][i] = map[k + 1][i];
                    }
                    map[SIZE - 1][i] = 0;
                }
            }
        }
    }

    public void gomBottom() {
        for (int i = SIZE - 1; i > 0; i--) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == map[i - 1][j]) {
                    map[i][j] += map[i - 1][j];
                    score += map[i][j];
                    for (int k = i - 1; k > 0; k--) {
                        map[k][j] = map[k - 1][j];
                    }
                    map[0][j] = 0;
                }
            }
        }
    }

    public void gomLeft() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE - 1; j++) {
                if (map[i][j] == map[i][j + 1]) {
                    map[i][j] += map[i][j + 1];
                    score += map[i][j];
                    for (int k = j + 1; k < SIZE - 1; k++) {
                        map[i][k] = map[i][k + 1];
                    }
                    map[i][SIZE - 1] = 0;
                }
            }
        }
    }

    public void gomRight() {
        for (int i = SIZE - 1; i >= 0; i--) {
            for (int j = SIZE - 1; j > 0; j--) {
                if (map[i][j] == map[i][j - 1]) {
                    map[i][j] += map[i][j - 1];
                    score += map[i][j];
                    for (int k = j - 1; k > 0; k--) {
                        map[i][k] = map[i][k - 1];
                    }
                    map[i][0] = 0;
                }
            }
        }
    }

    public boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isGameOver() {
        boolean check = true;
        if (isFull()) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE - 1; j++) {
                    if (map[i][j] == map[i][j + 1]) {
                        check = false;
                    }
                }
            }
            if (check) {
                for (int i = 0; i < SIZE - 1; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        if (map[i][j] == map[i + 1][j]) {
                            check = false;
                        }
                    }
                }
            }
            if (check) {
                return true;
            }
        }
        return false;
    }

    public void keyUp() {
        if (!isFull()) {
            for (int lap = 0; lap < SIZE - 2; lap++) {
                for (int i = 0; i < SIZE; i++) { //lap nay doi len duoc 1 so
                    for (int j = 0; j < SIZE; j++) {
                        if (map[i][j] == 0) {
                            for (int k = i; k < SIZE - 1; k++) {
                                if (map[k + 1][j] > 0) {
                                    map[k][j] = map[k + 1][j];
                                    map[k + 1][j] = 0;
                                }
                            }
                        }
                    }
                }
            }
        }
        gomTop();
    }

    public void keyDown() {
        if (!isFull()) {
            for (int lap = 0; lap < SIZE - 2; lap++) {
                for (int i = SIZE - 1; i >= 0; i--) {
                    for (int j = 0; j < SIZE; j++) {
                        if (map[i][j] == 0) {
                            for (int k = i; k > 0; k--) {
                                if (map[k - 1][j] > 0) {
                                    map[k][j] = map[k - 1][j];
                                    map[k - 1][j] = 0;
                                }
                            }
                        }
                    }
                }
            }
        }
        gomBottom();
    }

    public void keyLeft() {
        if (!isFull()) {
            for (int lap = 0; lap < SIZE - 2; lap++) {
                for (int j = 0; j < SIZE; j++) {
                    for (int i = 0; i < SIZE; i++) {
                        if (map[i][j] == 0) {
                            for (int k = j; k < SIZE - 1; k++) {
                                if (map[i][k + 1] > 0) {
                                    map[i][k] = map[i][k + 1];
                                    map[i][k + 1] = 0;
                                }
                            }
                        }
                    }
                }
            }
        }
        gomLeft();
    }

    public void keyRight() {
        if (!isFull()) {
            for (int lap = 0; lap < SIZE - 2; lap++) {
                for (int j = SIZE - 1; j >= 0; j--) {
                    for (int i = 0; i < SIZE; i++) {
                        if (map[i][j] == 0) {
                            for (int k = j; k > 0; k--) {
                                if (map[i][k - 1] > 0) {
                                    map[i][k] = map[i][k - 1];
                                    map[i][k - 1] = 0;
                                }
                            }
                        }
                    }
                }
            }
        }
        gomRight();
    }

    public int getSIZE() {
        return SIZE;
    }

    public void setSIZE(int SIZE) {
        this.SIZE = SIZE;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

//    public static void main(String[] args) {
////        Game2048 g2048 = new Game2048();
////        
////        g2048.initMap();
////        g2048.printMap();
////        System.out.println(log2((int) Math.pow(2, 9)));
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        Font[] fonts = ge.getAllFonts();
//        
//        for (Font font : fonts) {
//            System.out.print(font.getFontName() + " : ");
//            System.out.println(font.getFamily());
//        }
//    }
}
