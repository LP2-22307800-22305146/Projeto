package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameManager {

    // === TESTES UNITÁRIOS PARA createInitialBoard() ===

    @Test
    public void test1_validPlayers() {
        GameManager gm = new GameManager();
        String[][] playersOK = {
                {"1", "Sara", "Java", "Purple", "1"},
                {"2", "Rui", "Python", "Blue", "1"}
        };
        assertTrue(gm.createInitialBoard(playersOK, 10),
                "Deveria retornar true para dados válidos");
    }

    @Test
    public void test2_onePlayerInvalid() {
        GameManager gm = new GameManager();
        String[][] onePlayer = {
                {"1", "Sara", "Java", "Purple", "1"}
        };
        assertFalse(gm.createInitialBoard(onePlayer, 10),
                "Deveria retornar false com apenas um jogador");
    }

    @Test
    public void test3_wrongColor() {
        GameManager gm = new GameManager();
        String[][] wrongColor = {
                {"1", "Sara", "Java", "Orange", "1"},
                {"2", "Rui", "C++", "Blue", "1"}
        };
        assertFalse(gm.createInitialBoard(wrongColor, 10),
                "Deveria retornar false com cor inválida");
    }

    @Test
    public void test4_duplicatedID() {
        GameManager gm = new GameManager();
        String[][] duplicatedID = {
                {"1", "Sara", "Java", "Purple", "1"},
                {"1", "Rui", "Python", "Blue", "1"}
        };
        assertFalse(gm.createInitialBoard(duplicatedID, 10),
                "Deveria retornar false com IDs duplicados");
    }

    @Test
    public void test5_smallBoard() {
        GameManager gm = new GameManager();
        String[][] smallBoard = {
                {"1", "Sara", "Java", "Purple", "1"},
                {"2", "Rui", "Python", "Blue", "1"}
        };
        assertFalse(gm.createInitialBoard(smallBoard, 2),
                "Deveria retornar false com tabuleiro pequeno");
    }

    @Test
    public void test6_spacesAndLower() {
        GameManager gm = new GameManager();
        String[][] spacesAndLower = {
                {" 3 ", "  Ana  ", " Java ", " blue ", "1"},
                {" 4 ", "  Pedro  ", " C# ", " GREEN ", "1"}
        };
        assertTrue(gm.createInitialBoard(spacesAndLower, 6),
                "Deveria retornar true com espaços e letras minúsculas");
    }

    @Test
    public void test7_duplicateColor() {
        GameManager gm = new GameManager();
        String[][] duplicateColor = {
                {"1", "Sara", "Java", "Purple", "1"},
                {"2", "Rui", "Python", "Purple", "1"}
        };
        assertFalse(gm.createInitialBoard(duplicateColor, 10),
                "Deveria retornar false com cores duplicadas");
    }


}
