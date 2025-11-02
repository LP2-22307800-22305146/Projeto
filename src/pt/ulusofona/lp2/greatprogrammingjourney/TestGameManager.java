package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameManager {

    // === TESTES UNITÁRIOS PARA createInitialBoard() ===

    // TESTES PARA O NÚMERO VÁLIDO DE JOGADORES
    @Test
    public void validPlayers() {
        GameManager gm = new GameManager();
        String[][] playersOK = {
                {"1", "Sara", "Java", "Purple", "1"},
                {"2", "Rui", "Python", "Blue", "1"}
        };
        assertTrue(gm.createInitialBoard(playersOK, 10),
                "Deveria retornar true para dados válidos");
    }

    @Test
    public void onePlayerInvalid() {
        GameManager gm = new GameManager();
        String[][] onePlayer = {
                {"1", "Sara", "Java", "Purple", "1"}
        };
        assertFalse(gm.createInitialBoard(onePlayer, 10),
                "Deveria retornar false com apenas um jogador");
    }

    @Test
    public void mais4Jogadores() {
        GameManager gm = new GameManager();
        String[][] mais4Jogadores = {
                {"1", "Sara", "Java", "Purple", "1"},
                {"2", "Rui", "Python", "Blue", "1"},
                {"3", "Núria", "C", "Green", "2"},
                {"4", "Ana", "Python", "Brown", "4"},
                {"5", "Vasco", "Java", "Purple", "3"}
        };
        assertFalse(gm.createInitialBoard(mais4Jogadores, 10),
                "Deveria retornar false com apenas um jogador");
    }

    // TESTE DAS CORES
    @Test
    public void wrongColor() {
        GameManager gm = new GameManager();
        String[][] wrongColor = {
                {"1", "Sara", "Java", "Orange", "1"},
                {"2", "Rui", "C++", "Blue", "1"}
        };
        assertFalse(gm.createInitialBoard(wrongColor, 10),
                "Deveria retornar false com cor inválida");
    }

    @Test
    public  void corDuplicada () {
        GameManager gm = new GameManager();
        String[][] corDuplicada = {
                {"1", "Sara", "Java", "Blue", "1"},
                {"2", "Rui", "C++", "Blue", "1"}
        };
        assertFalse(gm.createInitialBoard(corDuplicada, 10),
                "Deveria retornar false com cor duplicada");
    }

    @Test
    public  void corInvalida () {
        GameManager gm = new GameManager();
        String[][] corInvalida = {
                {"1", "Sara", "Java", "PURPLE", "1"},
                {"2", "Rui", "C++", "blue", "1"}
        };
        assertFalse(gm.createInitialBoard(corInvalida, 10),
                "Deveria retornar false com cor duplicada");
    }


    // TESTES DOS IDS
    @Test
    public void duplicatedID() {
        GameManager gm = new GameManager();
        String[][] duplicatedID = {
                {"1", "Sara", "Java", "Purple", "1"},
                {"1", "Rui", "Python", "Blue", "1"}
        };
        assertFalse(gm.createInitialBoard(duplicatedID, 10),
                "Deveria retornar false com IDs duplicados");
    }

    @Test
    public void idInvalido() {
        GameManager gm = new GameManager();
        String[][] idInavlido = {
                {"-2", "Sara", "Java", "Purple", "1"},
                {"1", "Rui", "Python", "Blue", "1"}
        };
        assertFalse(gm.createInitialBoard(idInavlido, 10),
                "Deveria retornar false com IDs negativo");
    }

    // TESTE PARA OS NOMES

    @Test
    public void nomeNull() {
        GameManager gm = new GameManager();
        String[][] nomeNull = {
                {"1", null, "Java", "Purple", "1"},
                {"1", "Rui", "Python", "Blue", "1"}
        };
        assertFalse(gm.createInitialBoard(nomeNull, 10),
                "Deveria retornar false com nome null");
    }

    @Test
    public void nomeVazio() {
        GameManager gm = new GameManager();
        String[][] nomeVazio = {
                {"1", "", "Java", "Purple", "1"},
                {"1", "Rui", "Python", "Blue", "1"}
        };
        assertFalse(gm.createInitialBoard(nomeVazio, 10),
                "Deveria retornar false com IDs duplicados");
    }


    // TESTE PARA O TAMANHO DO BOARD

    @Test
    public void smallBoard() {
        GameManager gm = new GameManager();
        String[][] smallBoard = {
                {"1", "Sara", "Java", "Purple", "1"},
                {"2", "Rui", "Python", "Blue", "1"}
        };
        assertFalse(gm.createInitialBoard(smallBoard, 2),
                "Deveria retornar false com tabuleiro pequeno");
    }

    @Test
    public void spacesAndLower() {
        GameManager gm = new GameManager();
        String[][] spacesAndLower = {
                {" 3 ", "  Ana  ", " Java ", " blue ", "1"},
                {" 4 ", "  Pedro  ", " C# ", " GREEN ", "1"}
        };
        assertFalse(gm.createInitialBoard(spacesAndLower, 6),
                "Deveria retornar false com espaços ou letras minúsculas");

    }



}
