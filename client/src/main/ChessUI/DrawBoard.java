package ChessUI;

import chess.*;

import java.io.PrintStream;

import static ChessUI.EscapeSequences.*;

public class DrawBoard{
    private static final int BOARD_SIZE = 8; // Adjust the board size as needed
    private static final int SQUARE_SIZE = 3; // Adjust the square size as needed
    public String[][] myPieces = new String[8][8]; //represent players and help us know where to put
    //I made the board white on top and black on bottom... good to know

    public static void printBoards() {
        Board myBoard = new Board();
        myBoard.resetBoard();
        drawChessboard(System.out, myBoard);
        System.out.println("\n");
        drawChessboardWhite(System.out, myBoard);
    }

    public DrawBoard() {

    }
    public static void drawChessboard(PrintStream out, ChessBoard board) {
        printColHeader(out);

        printRowHeader(out, 1);
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                drawSquare(out, row, col, (Board) board);

                if (col == BOARD_SIZE - 1) {
                    resetColors(out);
                    out.print(SET_BG_COLOR_WHITE);
                }
            }
            printRowHeader(out, row + 1);
            out.println(); // Move to the next row

            if (row != 7) {
                printRowHeader(out, row+2); //start next row...
            }
        }

        printColHeader(out);
        out.print(SET_BG_COLOR_WHITE);
    }

    public static void drawChessboardWhite(PrintStream out, ChessBoard board) {
        printColHeaderWhite(out);

        printRowHeader(out, 8);

        for (int row = 7; row >= 0; row--) {
            for (int col = 7; col >= 0; col--) {
                drawSquare(out, row, col, (Board) board);

                if (col == 0) {
                    resetColors(out);
                }
            }
            printRowHeader(out, row + 1);
            out.println(); // Move to the next row

            if (row != 0) {
                printRowHeader(out, row); //start next row...
            }
        }

        printColHeaderWhite(out);
        out.print(SET_BG_COLOR_WHITE);
    }

    private static void printColHeader(PrintStream out) {
        int squares = BOARD_SIZE + 2;
        setGray(out);

        for (int square = 0; square < squares; square++) {
            if (square == 1) {
                out.print(" a ");
            } else if (square == 2) {
                out.print(" b ");
            } else if (square == 3) {
                out.print(" c ");
            } else if (square == 4) {
                out.print(" d ");
            } else if (square == 5) {
                out.print(" e ");
            } else if (square == 6) {
                out.print(" f ");
            } else if (square == 7) {
                out.print(" g ");
            } else if (square == 8) {
                out.print(" h ");
            } else {
                out.print("   ");
            }
        }

        resetColors(out);
        out.println(); //print out that header
    }

    private static void printColHeaderWhite(PrintStream out) {
        int squares = BOARD_SIZE + 2;
        setGray(out);

        for (int square = 0; square < squares; square++) {
            if (square == 1) {
                out.print(" h ");
            } else if (square == 2) {
                out.print(" g ");
            } else if (square == 3) {
                out.print(" f ");
            } else if (square == 4) {
                out.print(" e ");
            } else if (square == 5) {
                out.print(" d ");
            } else if (square == 6) {
                out.print(" c ");
            } else if (square == 7) {
                out.print(" b ");
            } else if (square == 8) {
                out.print(" a ");
            } else {
                out.print("   ");
            }
        }

        resetColors(out);
        out.println(); //print out that header
    }

    private static void printRowHeader(PrintStream out, int row) {
        setGray(out);

        if (row == 1) {
            out.print(" 1 ");
        } else if (row == 2) {
            out.print(" 2 ");
        } else if (row == 3) {
            out.print(" 3 ");
        } else if (row == 4) {
            out.print(" 4 ");
        } else if (row == 5) {
            out.print(" 5 ");
        } else if (row == 6) {
            out.print(" 6 ");
        } else if (row == 7) {
            out.print(" 7 ");
        } else if (row == 8) {
            out.print(" 8 ");
        }

        resetColors(out);
    }

    private static void drawSquare(PrintStream out, int row, int col, Board board) {
        if ((row + col) % 2 == 0) {
            setWhite(out);
        } else {
            setBlack(out);
        }

        // Draw the pieces according to the matrix
        printPiece(out, row, col, board);
    }

    private static void printPiece(PrintStream out, int row, int col, Board board) {

        ChessPosition pos = new Position(row, col);

        if (board.getPiece(pos) == null) {
            out.print("   ");
            return;
        }

        //else, there's a piece there
        if (board.getPiece(pos).getTeamColor() == ChessGame.TeamColor.WHITE) {
            out.print(SET_TEXT_COLOR_BLUE);
        } else if (board.getPiece(pos).getTeamColor() == ChessGame.TeamColor.BLACK) {
            out.print(SET_TEXT_COLOR_RED);
        }

        ChessPiece.PieceType type = board.getPiece(pos).getPieceType();


        if (type == ChessPiece.PieceType.KING) {
            out.print(" K ");
        } else if (type == ChessPiece.PieceType.KNIGHT) {
            out.print(" N ");
        } else if (type == ChessPiece.PieceType.PAWN) {
            out.print(" P ");
        } else if (type == ChessPiece.PieceType.ROOK) {
            out.print(" R ");
        } else if (type == ChessPiece.PieceType.BISHOP) {
            out.print(" B ");
        } else if (type == ChessPiece.PieceType.QUEEN) {
            out.print(" Q ");
        }

        out.print(RESET_TEXT_COLOR);
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE); // Make white background
        out.print(SET_TEXT_COLOR_BLACK); // Make black text
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK); // Make black background
        out.print(SET_TEXT_COLOR_WHITE); // Make white text
    }

    private static void setGray(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void resetColors(PrintStream out) {
        out.print(RESET_BG_COLOR); // Reset the background color
        out.print(RESET_TEXT_COLOR); // reset the text color
    }

}
