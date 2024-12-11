import java.util.Scanner;

public class RubikCube {

    private CubePiece[][][] cube;

    public RubikCube() {
        // cube with 6 sides, each side has 3 rows and 3 columns
        cube = new CubePiece[6][3][3];

        cube[0] = new CubePiece[][] {
                { new CubePiece('B'), new CubePiece('B'), new CubePiece('B') },
                { new CubePiece('B'), new CubePiece('B'), new CubePiece('B') },
                { new CubePiece('B'), new CubePiece('B'), new CubePiece('B') }
        };
        cube[1] = new CubePiece[][] {
                { new CubePiece('R'), new CubePiece('R'), new CubePiece('R') },
                { new CubePiece('R'), new CubePiece('R'), new CubePiece('R') },
                { new CubePiece('R'), new CubePiece('R'), new CubePiece('R') }
        };
        cube[2] = new CubePiece[][] {
                { new CubePiece('G'), new CubePiece('G'), new CubePiece('G') },
                { new CubePiece('G'), new CubePiece('G'), new CubePiece('G') },
                { new CubePiece('G'), new CubePiece('G'), new CubePiece('G') }
        };

        cube[3] = new CubePiece[][] {
                { new CubePiece('Y'), new CubePiece('Y'), new CubePiece('Y') },
                { new CubePiece('Y'), new CubePiece('Y'), new CubePiece('Y') },
                { new CubePiece('Y'), new CubePiece('Y'), new CubePiece('Y') }
        };

        cube[4] = new CubePiece[][] {
                { new CubePiece('O'), new CubePiece('O'), new CubePiece('O') },
                { new CubePiece('O'), new CubePiece('O'), new CubePiece('O') },
                { new CubePiece('O'), new CubePiece('O'), new CubePiece('O') }
        };
        cube[5] = new CubePiece[][] {
                { new CubePiece('W'), new CubePiece('W'), new CubePiece('W') },
                { new CubePiece('W'), new CubePiece('W'), new CubePiece('W') },
                { new CubePiece('W'), new CubePiece('W'), new CubePiece('W') }
        };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RubikCube cube = new RubikCube();
        cube.display();

        while (true) {
            System.out.print("Command: ");
            String command = scanner.nextLine().toUpperCase();
            if (command.equals("EX")) {
                System.out.println("Exiting...");
                break;
            } else if (command.equals("RS")) {
                cube.shuffle();
                cube.display();
            } else if (command.matches("[ULRFB][+-]?")) {
                int side = "ULRFB".indexOf(command.charAt(0));
                int turns = 1;
                if (command.length() == 2) {
                    if (command.charAt(1) == '+') {
                        turns = 3;
                    } else if (command.charAt(1) == '-') {
                        turns = 1;
                    }
                }
                cube.rotate(side, turns);
                cube.display();
                if (cube.isSolved()) {
                    System.out.println("Solved the Rubik's Cube!");
                    break;
                }
            } else {
                System.out.println("Invalid command");
            }
        }

        scanner.close();
    }

    public void display() {

        for (int i = 0; i < 3; i++) {
            System.out.print("      ");
            for (int j = 0; j < 3; j++) {
                System.out.print(cube[0][i][j].getColor() + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 1; j < 5; j++) {
                for (int k = 0; k < 3; k++) {
                    System.out.print(cube[j][i][k].getColor() + " ");
                }

            }
            System.out.println();
        }

        for (int i = 0; i < 3; i++) {
            System.out.print("      ");
            for (int j = 0; j < 3; j++) {
                System.out.print(cube[5][i][j].getColor() + " ");
            }
            System.out.println();
        }
    }

    public boolean isSolved() {
        for (int i = 0; i < 6; i++) {
            char c = cube[i][0][0].getColor();

            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if (cube[i][j][k].getColor() != c) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void shuffle() {
        for (int i = 0; i < 20; i++) {
            int side = (int) (Math.random() * 6);
            int turns = (int) (Math.random() * 3) + 1;
            rotate(side, turns);
        }
    }

    public void rotate(int side, int turns) {
        while (turns-- > 0) {
            CubePiece[][] temp = new CubePiece[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    temp[i][j] = cube[side][2 - j][i];
                }
            }
            cube[side] = temp;

            CubePiece[] tempRow = new CubePiece[3];

            if (side == 0) {
                tempRow = cube[1][0];
                cube[1][0] = cube[2][0];
                cube[2][0] = cube[3][0];
                cube[3][0] = cube[4][0];
                cube[4][0] = tempRow;
            } else if (side == 1) {
                tempRow = cube[0][0];
                cube[0][0] = cube[4][2];
                cube[4][2] = cube[5][0];
                cube[5][0] = cube[2][0];
                cube[2][0] = tempRow;
            } else if (side == 2) {
                tempRow = cube[0][2];
                cube[0][2] = cube[4][0];
                cube[4][0] = cube[5][2];
                cube[5][2] = cube[1][0];
                cube[1][0] = tempRow;
            } else if (side == 3) {
                tempRow = cube[0][0];
                cube[0][0] = cube[1][2];
                cube[1][2] = cube[5][2];
                cube[5][2] = cube[3][0];
                cube[3][0] = tempRow;
            } else if (side == 4) {
                tempRow = cube[0][2];
                cube[0][2] = cube[2][2];
                cube[2][2] = cube[5][0];
                cube[5][0] = cube[3][2];
                cube[3][2] = tempRow;
            } else if (side == 5) {
                tempRow = cube[1][2];
                cube[1][2] = cube[2][2];
                cube[2][2] = cube[3][2];
                cube[3][2] = cube[4][2];
                cube[4][2] = tempRow;
            }
        }
    }
}