package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestGameManager {

    @Test
    public void test1_validPlayers() {
        GameManager gm = new GameManager();
        String[][] playersOK = {
                {"1", "Sara", "Purple"},
                {"2", "Rui", "Blue"}
        };
        assertTrue(gm.createInitialBoard(playersOK, 10),
                "createInitialBoard() deveria retornar true para dados válidos");
    }

    @Test
    public void test2_onePlayerInvalid() {
        GameManager gm = new GameManager();
        String[][] onePlayer = {
                {"1", "Sara", "Purple"}
        };
        assertFalse(gm.createInitialBoard(onePlayer, 10),
                "Deveria retornar false com apenas um jogador");
    }

    @Test
    public void test3_wrongColor() {
        GameManager gm = new GameManager();
        String[][] wrongColor = {
                {"1", "Sara", "Orange"},
                {"2", "Rui", "Blue"}
        };
        assertFalse(gm.createInitialBoard(wrongColor, 10),
                "Deveria retornar false com cor inválida");
    }

    @Test
    public void test4_duplicatedID() {
        GameManager gm = new GameManager();
        String[][] duplicatedID = {
                {"1", "Sara", "Purple"},
                {"1", "Rui", "Blue"}
        };
        assertFalse(gm.createInitialBoard(duplicatedID, 10),
                "Deveria retornar false com IDs duplicados");
    }

    @Test
    public void test5_smallBoard() {
        GameManager gm = new GameManager();
        String[][] smallBoard = {
                {"1", "Sara", "Purple"},
                {"2", "Rui", "Blue"}
        };
        assertFalse(gm.createInitialBoard(smallBoard, 2),
                "Deveria retornar false com tabuleiro pequeno");
    }

    @Test
    public void test6_spacesAndLower() {
        GameManager gm = new GameManager();
        String[][] spacesAndLower = {
                {" 3 ", "  Ana  ", " blue "},
                {" 4 ", "  Pedro  ", " GREEN "}
        };
        assertTrue(gm.createInitialBoard(spacesAndLower, 6),
                "Deveria retornar true com espaços e minúsculas");
    }

    @Test
    public void test7_playersCreatedPositions() {
        GameManager gm = new GameManager();
        String[][] players = {
                {"1", "Ada Lovelace", "Purple"},
                {"2", "Alan Turing", "Green"},
                {"3", "Grace Hopper", "Blue"}
        };
        gm.createInitialBoard(players, 10);


    }
}
