package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameManager {

    //  TESTES UNITÁRIOS PARA createInitialBoard()

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
    // Movimento inválido: nrSpaces < 1.

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


    // Movimento inválido: nrSpaces > 6.

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


    // REGRAS DE FRONTEIRA — Ricochete: jogador ultrapassa a meta.
    // Exemplo: meta = 100, jogador = 99, move 3 → vai para 98.

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


    // Turnos circulares — quando o último jogador termina, o turno volta ao primeiro.

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

    // Verifica incremento do contador de turnos.

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


    // TESTES DA gameIsOver()
    @Test
    public void testGameIsOverTrue() {
        GameManager gm = new GameManager();
        String[][] players = {
                {"1", "Ana", "Java", "Blue", "9"},
                {"2", "Bruno", "C", "Green", "10"} // este atingiu a meta
        };

        assertTrue(gm.createInitialBoard(players, 10));

        // O jogo deve terminar imediatamente porque Bruno está na meta
        assertTrue(gm.gameIsOver(), "O jogo deve terminar quando um jogador atinge a última casa.");
    }

    @Test
    public void testGameIsOverFalse() {
        GameManager gm = new GameManager();
        String[][] players = {
                {"1", "Ana", "Java", "Blue", "5"},
                {"2", "Bruno", "C", "Green", "9"}
        };

        assertTrue(gm.createInitialBoard(players, 10));

        // Nenhum jogador atingiu a meta
        assertFalse(gm.gameIsOver(), "O jogo ainda não deve ter terminado.");
    }

    // TESTES DA CONTAGEM DE TURNOS
    @Test
    void testContagemDeTurnosIncluiJogadaFinal() {
        GameManager gm = new GameManager();

        //criar tabuleiro pequeno para testar rapidamente
        String[][] jogadores = {
                {"1", "Alice", "Java;Python", "Blue"},
                {"2", "Bob", "C;PHP", "Green"}
        };
        gm.createInitialBoard(jogadores, 10); // tabuleiro de 10 casas

        // turno inicial deve ser 0
        assertEquals(0, gm.getBoard().getTurnos(), "O contador deve começar em 0.");

        // jogador 1 move 3 casas → turno +1
        gm.moveCurrentPlayer(3);
        assertEquals(1, gm.getBoard().getTurnos(), "Após 1 movimento, deve haver 1 turno.");

        // jogador 2 move 2 casas → turno +1
        gm.moveCurrentPlayer(2);
        assertEquals(2, gm.getBoard().getTurnos(), "Após 2 movimentos, deve haver 2 turnos.");

        // jogador 1 move para a meta (posição final = 10)
        gm.getBoard().getJogadores().get(1).setPosicao(9); // manualmente antes da jogada
        gm.moveCurrentPlayer(1); // este movimento vence o jogo

        // turno deve ser incrementado mesmo na jogada final
        assertEquals(3, gm.getBoard().getTurnos(), "A jogada vencedora também conta como turno.");

        // o jogo deve ter terminado
        assertTrue(gm.gameIsOver(), "O jogo deve terminar quando o jogador chega à meta.");
    }

    //PARTE 2

    // TESTE da getProgrammersInfo
    @Test
    public void testGetProgrammersInfo_FormatacaoCorreta() {
        // Arrange (preparar o cenário)
        GameManager gm = new GameManager();

        // Cria tabuleiro com dois jogadores
        String[][] jogadores = {
                {"1", "Bruninho", "Java", "Blue"},
                {"2", "Raquelita", "Python", "Green"}
        };
        gm.createInitialBoard(jogadores, 10);

        // Adiciona ferramentas simuladas (apenas se o Player tiver método addFerramenta)
        Player p1 = gm.getBoard().getJogadores().get(1);
        Player p2 = gm.getBoard().getJogadores().get(2);

        // Vamos supor que Ferramenta tem construtor Ferramenta(String nome)
        p1.getFerramentas().add(new Ferramenta(4,"IDE"));
        p1.getFerramentas().add(new Ferramenta(0,"Herança"));

        // Nenhuma ferramenta para p2 → deve aparecer "No tools"

        // Act (executar)
        String resultado = gm.getProgrammersInfo();

        // Assert (verificar)
        String esperado = "Bruninho : IDE;Herança | Raquelita : No tools";

        assertEquals(esperado, resultado,
                "A string retornada deve conter os nomes e ferramentas formatados corretamente.");
    }

    // TESTE da createInitialBoard
    // ---------- TESTES DE ABISMOS E FERRAMENTAS ----------

    @Test
    public void testAbismoValido() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "Ana", "Java", "Blue", "1"},
                {"2", "Bruno", "Python", "Green", "1"}
        };

        String[][] abyssesAndTools = {
                {"0", "Abyss", "3"} // Erro de Sintaxe na posição 3
        };

        assertTrue(gm.createInitialBoard(players, 10, abyssesAndTools),
                "Deve retornar true com abismo válido");
        assertEquals(1, gm.getBoard().getAbismos().size(),
                "Deve existir 1 abismo no tabuleiro");
    }

    @Test
    public void testFerramentaValida() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "Ana", "Java", "Blue", "1"},
                {"2", "Bruno", "Python", "Green", "1"}
        };

        String[][] abyssesAndTools = {
                {"4", "Tool", "5"} // Ferramenta IDE na posição 5
        };

        assertTrue(gm.createInitialBoard(players, 10, abyssesAndTools),
                "Deve retornar true com ferramenta válida");
        assertEquals(1, gm.getBoard().getFerramentas().size(),
                "Deve existir 1 ferramenta no tabuleiro");
    }

    @Test
    public void testAbismoInvalido() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "Ana", "Java", "Blue", "1"},
                {"2", "Bruno", "Python", "Green", "1"}
        };

        String[][] abyssesAndTools = {
                {"15", "Abyss", "3"} // ID inválido (maior que 9)
        };

        assertFalse(gm.createInitialBoard(players, 10, abyssesAndTools),
                "Deve retornar false se o ID do abismo for inválido");
    }

    @Test
    public void testFerramentaInvalida() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "Ana", "Java", "Blue", "1"},
                {"2", "Bruno", "Python", "Green", "1"}
        };

        String[][] abyssesAndTools = {
                {"9", "Tool", "8"} // ID inválido (>5)
        };

        assertFalse(gm.createInitialBoard(players, 10, abyssesAndTools),
                "Deve retornar false se o ID da ferramenta for inválido");
    }

    @Test
    public void testPosicaoInvalidaDeAbismoOuFerramenta() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "Ana", "Java", "Blue", "1"},
                {"2", "Bruno", "Python", "Green", "1"}
        };

        String[][] abyssesAndTools = {
                {"1", "Abyss", "0"}, // posição inválida
                {"3", "Tool", "15"}  // posição fora do limite
        };

        assertFalse(gm.createInitialBoard(players, 10, abyssesAndTools),
                "Deve retornar false para posições inválidas");
    }

    @Test
    public void testTipoInvalido() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "Ana", "Java", "Blue", "1"},
                {"2", "Bruno", "Python", "Green", "1"}
        };

        String[][] abyssesAndTools = {
                {"2", "Banana", "4"} // tipo inválido
        };

        assertFalse(gm.createInitialBoard(players, 10, abyssesAndTools),
                "Deve retornar false se o tipo não for Abyss ou Tool");
    }


    @Test
    public void testCreateInitialBoard_ComDadosInvalidos() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "Neo", "Python", "Purple"}
        };

        int worldSize = 10;

        // Abismo inválido (id fora do intervalo)
        String[][] abyssesAndTools = {
                {"15", "Abyss", "3"}
        };

        boolean result = gm.createInitialBoard(players, worldSize, abyssesAndTools);
        System.out.println("createInitialBoard() com abismo inválido → " + result);
        assertFalse(result, "Deve retornar false com id de abismo inválido");
    }

    @Test
    public void testGetSlotInfo_ComAbismo() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "Ana", "Java", "Blue"},
                {"2", "Bruno", "Python", "Green"}
        };

        // Abismo válido: Erro de Sintaxe (id=0), posição=3
        String[][] abyssesAndTools = {
                {"0", "Abyss", "3"}
        };

        boolean result = gm.createInitialBoard(players, 10, abyssesAndTools);
        assertTrue(result, "createInitialBoard deve retornar true com abismo válido");

        String[] slot = gm.getSlotInfo(3);
        assertNotNull(slot, "getSlotInfo não deve retornar null");
        assertEquals(3, slot.length, "getSlotInfo deve retornar array com 3 elementos");

        assertEquals("", slot[0], "Não deve haver jogadores nesta casa");
        assertEquals("Erro de sintaxe", slot[1], "Descrição deve ser 'Erro de sintaxe'");
        assertEquals("A:0", slot[2], "Tipo+ID deve ser 'A:0'");
    }

    @Test
    public void testGetSlotInfo_ComFerramenta() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "Ana", "Java", "Blue"},
                {"2", "Bruno", "Python", "Green"}
        };

        // Ferramenta válida: IDE (id=4), posição=5
        String[][] abyssesAndTools = {
                {"4", "Tool", "5"}
        };

        boolean result = gm.createInitialBoard(players, 10, abyssesAndTools);
        assertTrue(result, "createInitialBoard deve retornar true com ferramenta válida");

        String[] slot = gm.getSlotInfo(5);
        assertNotNull(slot, "getSlotInfo não deve retornar null");
        assertEquals(3, slot.length, "getSlotInfo deve retornar array com 3 elementos");

        assertEquals("", slot[0], "Sem jogadores nesta casa");
        assertEquals("IDE", slot[1], "Descrição deve ser 'IDE'");
        assertEquals("T:4", slot[2], "Tipo+ID deve ser 'T:4'");
    }


    @Test
    public void testGetSlotInfo_CasaVazia() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "Ana", "Java", "Blue"},
                {"2", "Bruno", "Python", "Green"}
        };

        String[][] abyssesAndTools = {};

        boolean result = gm.createInitialBoard(players, 10, abyssesAndTools);
        assertTrue(result, "createInitialBoard deve retornar true");

        // Casa vazia (sem jogador nem objeto)
        String[] slot = gm.getSlotInfo(7);
        assertNotNull(slot, "getSlotInfo não deve retornar null");
        assertEquals(3, slot.length, "getSlotInfo deve retornar array com 3 elementos");

        assertEquals("", slot[0], "Sem jogadores");
        assertEquals("", slot[1], "Sem descrição");
        assertEquals("", slot[2], "Sem tipo/id");
    }


    //Abismo e uma Ferramenta na mesma casa
    @Test
    public void testAbismoEFerramentaNaMesmaPosicao() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "Ana", "Java", "Blue"},
                {"2", "Bruno", "Python", "Green"}
        };

        // Um abismo e uma ferramenta na mesma posição 5
        String[][] abyssesAndTools = {
                {"0", "Abyss", "5"},
                {"3", "Tool", "5"}
        };

        boolean result = gm.createInitialBoard(players, 10, abyssesAndTools);
        System.out.println("Abismo + Ferramenta na mesma posição → " + result);
        assertFalse(result, "createInitialBoard deve retornar false quando há um Abismo e uma Ferramenta na mesma posição");
    }


}
