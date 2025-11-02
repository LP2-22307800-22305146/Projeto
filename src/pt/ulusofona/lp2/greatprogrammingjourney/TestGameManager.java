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

    // TESTES PARA A LINGUAGENS GUARDADAS

    @Test
    public void testSemLinguagens() {
        GameManager gm = new GameManager();

        String[][] jogadores = {
                {"1", "Ada", "", "Green", "1"},
                {"2", "Turing", "", "Purple", "1"}
        };

        gm.createInitialBoard(jogadores, 6);

        String infoAda = gm.getProgrammerInfoAsStr(1);
        String infoTuring = gm.getProgrammerInfoAsStr(2);

        assertTrue(infoAda.contains(" |  | ") || infoAda.endsWith(" |  | Em Jogo"),
                "Quando não há linguagens, o campo deve ficar vazio");
        assertTrue(infoTuring.contains(" |  | ") || infoTuring.endsWith(" |  | Em Jogo"),
                "Jogadores sem linguagens devem mostrar campo vazio");
    }

    // TESTES DA moveCurrentPlayer

    @Test
    public void testMoveCurrentPlayerValido() {
        GameManager gm = new GameManager();
        String[][] players = {
                {"1", "Bruninho", "Python; PHP", "Blue", "1"},
                {"2", "Boss Baby", "Java", "Green", "1"}
        };

        assertTrue(gm.createInitialBoard(players, 10));

        boolean moved = gm.moveCurrentPlayer(3);
        assertTrue(moved, "Movimento de 3 casas deve ser permitido.");

        Player p = gm.getBoard().getJogadores().get(1);
        assertEquals(4, p.getPosicao(), "O jogador deve ir da posição 1 para a 4.");

        assertEquals(2, gm.getBoard(), "O turno deve passar para o jogador 2.");
    }

    /**
     * Movimento inválido: nrSpaces < 1.
     */
    @Test
    public void testMoveCurrentPlayerInvalidoAbaixo() {
        GameManager gm = new GameManager();
        String[][] players = {
                {"1", "Ana", "Python", "Purple", "1"},
                {"2", "João", "C", "Green", "1"}
        };

        assertTrue(gm.createInitialBoard(players, 10));

        boolean moved = gm.moveCurrentPlayer(0);
        assertFalse(moved, "Não deve permitir mover 0 casas.");

        Player p = gm.getBoard().getJogadores().get(1);
        assertEquals(1, p.getPosicao(), "O jogador deve permanecer na posição original.");
    }

    /**
     * Movimento inválido: nrSpaces > 6.
     */
    @Test
    public void testMoveCurrentPlayerInvalidoAcima() {
        GameManager gm = new GameManager();
        String[][] players = {
                {"1", "Maria", "Java", "Blue", "1"},
                {"2", "Pedro", "C#", "Brown", "1"}
        };

        assertTrue(gm.createInitialBoard(players, 10));

        boolean moved = gm.moveCurrentPlayer(7);
        assertFalse(moved, "Não deve permitir mover mais de 6 casas.");

        Player p = gm.getBoard().getJogadores().get(1);
        assertEquals(1, p.getPosicao(), "O jogador não deve ter-se movido.");
    }

    /**
     * REGRAS DE FRONTEIRA — Ricochete: jogador ultrapassa a meta.
     * Exemplo: meta = 100, jogador = 99, move 3 → vai para 98.
     */
    @Test
    public void testMoveCurrentPlayerRicochete() {
        GameManager gm = new GameManager();
        String[][] players = {
                {"1", "Lia", "Go; Kotlin", "Blue", "99"},
                {"2", "Rui", "C", "Green", "1"}
        };

        assertTrue(gm.createInitialBoard(players, 100));

        boolean moved = gm.moveCurrentPlayer(3);
        assertTrue(moved, "O movimento deve ser válido e aplicar ricochete.");

        Player p = gm.getBoard().getJogadores().get(1);
        assertEquals(98, p.getPosicao(), "Deve recuar 2 casas após ultrapassar a meta (100 → 102 → 98).");

        assertEquals(2, gm.getCurrentPlayerID(), "O turno deve passar para o jogador seguinte (ID 2).");
    }

    /**
     * Turnos circulares — quando o último jogador termina, o turno volta ao primeiro.
     */
    @Test
    public void testMoveCurrentPlayerCircularTurn() {
        GameManager gm = new GameManager();
        String[][] players = {
                {"1", "A", "Python", "Purple", "1"},
                {"2", "B", "C", "Green", "1"},
                {"3", "C", "Java", "Brown", "1"}
        };

        assertTrue(gm.createInitialBoard(players, 10));

        // jogador 1 → jogador 2
        gm.moveCurrentPlayer(1);
        assertEquals(2, gm.getCurrentPlayerID());

        // jogador 2 → jogador 3
        gm.moveCurrentPlayer(2);
        assertEquals(3, gm.getCurrentPlayerID());

        // jogador 3 → volta ao 1
        gm.moveCurrentPlayer(3);
        assertEquals(1, gm.getCurrentPlayerID());
    }

    /**
     * Verifica incremento do contador de turnos.
     */
    @Test
    public void testContadorDeTurnosIncrementa() {
        GameManager gm = new GameManager();
        String[][] players = {
                {"1", "Zé", "Python", "Blue", "1"},
                {"2", "Mia", "Java", "Green", "1"}
        };

        assertTrue(gm.createInitialBoard(players, 10));

        int turnosAntes = gm.getBoard().getTurnos();
        gm.moveCurrentPlayer(2);
        int turnosDepois = gm.getBoard().getTurnos();

        assertEquals(turnosAntes + 1, turnosDepois,
                "O contador de turnos deve incrementar em 1 após cada movimento válido.");
    }


}