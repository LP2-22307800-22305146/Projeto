package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class GameManager {

    private Board board; //agora o tabuleiro é um objeto!

    //construtor vazio
    public GameManager() {

        this.board = new Board();

    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        if (gameIsOver()) {
            return false;
        }

        //Validação inicial do array
        if (playerInfo == null || playerInfo.length < 2 || playerInfo.length > 4) {
            return false;
        }

        board.getJogadores().clear();

        //Validação do tamanho mínimo do tabuleiro
        if (worldSize < playerInfo.length * 2) {
            return false;
        }

        //Conjunto auxiliar para evitar cores duplicadas
        Set<Integer> idsUsados = new HashSet<>();
        Set<String> coresUsadas = new HashSet<>();

        // Processar cada jogador
        for (String[] info : playerInfo) {

            //Pode haver 3 ou 5 campos — aceitar ambos
            if (info == null || info.length < 3) {
                return false;
            }


            try {

                //id
                if (info[0] == null || info[0].trim().isEmpty()) {

                    return false;

                }

                int id = Integer.parseInt(info[0].trim());

                if (id <= 0 || !idsUsados.add(id)) {

                    return false;

                }

                //nome
                if (info[1] == null || info[1].trim().isEmpty()) {
                    return false;
                }
                String nome = info[1].trim();

                //linguagens
                String linguagensStr = "";
                String cor = "";
                int posicao = 1;

                if (info.length == 3) {
                    // formato: [id, nome, cor]
                    if (info[2] == null || info[2].trim().isEmpty()) {
                        return false;
                    }
                    cor = info[2].trim();
                } else if (info.length == 4) {
                    // formato: [id, nome, linguagens, cor]
                    if (info[2] == null) {
                        return false; //linguagens pode estar vazia, mas não null
                    }
                    linguagensStr = info[2].trim();
                    if (info[3] == null || info[3].trim().isEmpty()) {
                        return false;
                    }
                    cor = info[3].trim();
                } else if (info.length >= 5) {
                    // formato: [id, nome, linguagens, cor, posicao]

                    if (info[2] == null) {
                        return false;
                    }
                    linguagensStr = info[2].trim();

                    if (info[3] == null || info[3].trim().isEmpty()) {
                        return false;
                    }

                    cor = info[3].trim();

                    if (info[4] != null && !info[4].trim().isEmpty()) {
                        posicao = Integer.parseInt(info[4].trim());
                    }
                }

                //cor
                // deve ser uma das quatro, exatamente igual (case-sensitive)
                if (!cor.equals("Purple") && !cor.equals("Green") &&
                        !cor.equals("Brown") && !cor.equals("Blue")) {
                    return false;
                }

                // não pode repetir cor
                if (!coresUsadas.add(cor)) {
                    return false;
                }

                //criar jogador
                Player novo = new Player(id, nome, cor);
                novo.setPosicao(posicao);

                //linguagens
                // pode estar vazia, mas nunca null
                if (!linguagensStr.isEmpty()) {

                    String[] linguas = linguagensStr.split("\\s*;\\s*");
                    for (String l : linguas) {

                        if (l != null && !l.trim().isEmpty()) {
                            novo.adicionarLinguagem(l.trim());
                        }

                    }
                }

                // adicionar ao tabuleiro
                board.getJogadores().put(id, novo);

            } catch (Exception e) {
                return false;
            }
        }

        //guardar tamanho
        board.setTamanho(worldSize);

        // Definir jogador inicial (menor ID)
        int menorID = Integer.MAX_VALUE;
        for (int id : board.getJogadores().keySet()) {
            if (id < menorID) {
                menorID = id;
            }
        }
        board.setCurrentPlayerID(menorID);

        // Reiniciar contador de turnos
        board.setTurnos(0);

        return true;
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools) {
        if (!createInitialBoard(playerInfo,worldSize)) {
            System.out.println("Falhou na criação dos jogadores da Parte 1");
            return false;
        }
        System.out.println("Parte 1 (jogadores) criada com sucesso!");

        // ===============================
        // Processar Abismos e Ferramentas
        // ===============================
        if (abyssesAndTools != null) {

            // Limpa listas anteriores, se existirem
            board.getAbismos().clear();
            board.getFerramentas().clear();

            for (String[] linha : abyssesAndTools) {
                System.out.println("Linha recebida (createInitialBoard): " + Arrays.toString(linha));
                try {
                    // Validação básica
                    if (linha == null || linha.length != 3) {
                        return false;
                    }


                    int tipo = Integer.parseInt(linha[0].trim());; // ← tipo agora é String
                    int id = Integer.parseInt(linha[1].trim());
                    int posicao = Integer.parseInt(linha[2].trim());

// verificar posição
                    // Posição válida
                    if (posicao < 1 || posicao > worldSize) {
                        return false;
                    }

                    // Evitar sobreposição
                    if (board.getAbismos().containsKey(posicao) || board.getFerramentas().containsKey(posicao)) {
                        return false;
                    }

                    // Verificar tipo
                    if (tipo == 0) {
                        if ((id < 0 || id > 9)&& id != 20) {
                            return false;
                        }
                        board.getAbismos().put(posicao, new Abismo(id, posicao));
                    } else if (tipo == 1) {
                        if (id < 0 || id > 5) {
                            return false;
                        }

                        String nomeFerramenta = switch (id) {
                            case 0 -> "Herança";
                            case 1 -> "Programação Funcional";
                            case 2 -> "Testes Unitários";
                            case 3 -> "Tratamento de Excepções";
                            case 4 -> "IDE";
                            case 5 -> "Ajuda Do Professor";
                            default -> "Ferramenta Desconhecida";
                        };

                        board.getFerramentas().put(posicao, new Ferramenta(id, nomeFerramenta));
                    } else {
                        return false;
                    }

                } catch (Exception e) {
                    System.out.println("Exceção detectada: " + e.getMessage());
                    e.printStackTrace();
                    return false;
                }
            }
        }


        //guardar tamanho
        board.setTamanho(worldSize);

        // Definir jogador inicial (menor ID)
        int menorID = Integer.MAX_VALUE;
        for (int id : board.getJogadores().keySet()) {
            if (id < menorID) {
                menorID = id;
            }
        }
        board.setCurrentPlayerID(menorID);

        // Reiniciar contador de turnos
        board.setTurnos(0);

        return true;

    }

    public String getImagePng(int nrSquare) {
        //valida intervalo
        if (nrSquare < 1 || nrSquare > board.getTamanho()) {
            return null;
        }

        //ultima casa (meta)
        if (nrSquare == board.getTamanho()) {
            return "glory.png";
        }

        return null;
    }

    public String[] getProgrammerInfo(int id) {
        if (!board.getJogadores().containsKey(id)) {
            return null;
        }

        Player p = board.getJogadores().get(id);
        String[] info = new String[7];

        info[0] = String.valueOf(p.getId());               // ID
        info[1] = p.getNome();                             // Nome
        info[2] = p.linguagensComoString();                // Linguagens separadas por ";"
        info[3] = p.getCor();
        info[4] = String.valueOf(p.getPosicao());          // Posição atual

        //ferramentas
        if (p.getFerramentas() != null && !p.getFerramentas().isEmpty()) {
            ArrayList<String> nomesFerramentas = new ArrayList<>();
            for (Ferramenta f : p.getFerramentas()) {
                nomesFerramentas.add(f.getNome());
            }
            info[5] = String.join(";", nomesFerramentas);
        } else {
            info[5] = "No tools";
        }

        //estado do jogador
        if (p.isDerrotado()) {
            info[6] = "Derrotado";
        } else if (p.isPreso()) {
            info[6] = "Preso";
        } else {
            info[6] = "Em Jogo";
        }
        return info;
    }

    public String getProgrammerInfoAsStr(int id) {

        if (!board.getJogadores().containsKey(id)) {

            return null;

        }

        Player p = board.getJogadores().get(id);

        // ferramentas (se não existir lista em Player, assume "No tools")
        String ferramentasStr = "No tools";
        if (p.getFerramentas() != null && !p.getFerramentas().isEmpty()) {
            ArrayList<String> nomes = new ArrayList<>();
            for (Ferramenta f : p.getFerramentas()) {
                nomes.add(f.getNome());
            }
            ferramentasStr = String.join(";", nomes);
        }

        // criar cópia das linguagens e ordenar alfabeticamente
        ArrayList<String> linguagensOrdenadas = new ArrayList<>(p.getLinguagensFavoritas());
        linguagensOrdenadas.sort(String::compareToIgnoreCase);

        // juntar com "; "
        String linguagensStr = String.join("; ", linguagensOrdenadas);

        // determinar estado
        String estado;
        if (p.isDerrotado()) {
            estado = "Derrotado";
        } else if (p.isPreso()) {
            estado = "Preso";
        } else {
            estado = "Em Jogo";
        }

        // construir string final
        return p.getId() + " | " + p.getNome() + " | " +
                p.getPosicao() + " | " + ferramentasStr + " | " +
                linguagensStr + " | " + estado;
    }

    public String getProgrammersInfo() {

        // Se não houver jogadores, retorna string vazia
        if (board.getJogadores().isEmpty()) {
            return "";
        }

        String resultado = "";

        // Percorrer todos os jogadores
        for (Player p : board.getJogadores().values()) {

            // Ignorar jogadores derrotados
            if (p.isDerrotado()) {
                continue;
            }

            // Começar o segmento com o nome do jogador
            resultado += p.getNome() + " : ";

            // Obter as ferramentas (por agora usamos linguagensFavoritas)
            ArrayList<Ferramenta> ferramentas = p.getFerramentas();

            if (ferramentas.isEmpty()) {
                resultado += "No tools";
            } else {
                // Converte cada ferramenta em texto (usando o toString() ou um método getNome())
                ArrayList<String> nomesFerramentas = new ArrayList<>();

                for (Ferramenta f : ferramentas) {
                    nomesFerramentas.add(f.toString());  // ou f.getNome(), se tiver esse método
                }

                resultado += String.join(";", nomesFerramentas);
            }

            // Adiciona separador entre jogadores
            resultado += " | ";
        }

        // Remover o último " | " se existir
        if (resultado.endsWith(" | ")) {
            resultado = resultado.substring(0, resultado.length() - 3);
        }

        return resultado;
    }

    public String[] getSlotInfo(int position) {

        // posição inválida
        if (position < 1 || position > board.getTamanho()) {
            return null;
        }

        // jogadores na casa
        List<Integer> ids = new ArrayList<>();
        for (Player p : board.getJogadores().values()) {
            if (p.getPosicao() == position) {
                ids.add(p.getId());
            }
        }
        Collections.sort(ids);

        String playersStr = ids.isEmpty()
                ? ""
                : ids.stream()
                .map(String::valueOf)
                .collect(java.util.stream.Collectors.joining(","));

        String description = "";
        String type = "";

        if (board.getFerramentas().containsKey(position)) {
            Ferramenta f = board.getFerramentas().get(position);
            description = f.getNome();
            type = "T:" + f.getId();
        } else if (board.getAbismos().containsKey(position)) {
            Abismo a = board.getAbismos().get(position);
            description = a.getNome();
            type = "A:" + a.getId();
        }

        return new String[]{playersStr, description, type};
    }

    public int getCurrentPlayerID() {
        //se o tabuleiro ainda não tem jogadores
        if (board.getJogadores() == null || board.getJogadores().size() == 0) {
            return -1;
        }

        //se já existe jogador atual guardado, devolve-o
        if (board.getCurrentPlayerID() > 0) {
            return board.getCurrentPlayerID();
        }

        // Caso contrário, encontra o jogador de menor ID
        int menorID = -1;

        // Cria uma lista com todos os jogadores (mais simples de percorrer)
        ArrayList<Player> listaJogadores = new ArrayList<>(board.getJogadores().values());

        for (int i = 0; i < listaJogadores.size(); i++) {
            int id = listaJogadores.get(i).getId();

            if (menorID == -1 || id < menorID) {
                menorID = id;
            }
        }

        // Guarda o resultado para próximas chamadas
        board.setCurrentPlayerID(menorID);

        // Devolve o ID do jogador atual
        return menorID;
    }

    public boolean moveCurrentPlayer(int nrSpaces) {
        // valida se o valor do dado é entre 1 e 6
        if (nrSpaces < 1 || nrSpaces > 6) {
            return false;
        }

        // guardar o valor do dado para uso posterior em abismos (ex: Erro de Lógica)
        board.setUltimoValorDado(nrSpaces);

        int idAtual = board.getCurrentPlayerID();
        Player jogadorAtual = board.getJogadores().get(idAtual);

        // verificar se o jogador existe e está ativo
        if (jogadorAtual == null || jogadorAtual.isDerrotado()) {
            return false;
        }

        if (jogadorAtual.isPreso()) {
            jogadorAtual.setPreso(false);
            board.setTurnos(board.getTurnos() + 1);
            if (!gameIsOver()) {
                avancarTurno();
            }
            return false;
        }

        // Restrição de Linguagem
        String primeiraLinguagem = jogadorAtual.primeiraLinguagem();

        // Verificar que tipo de linguagem é e aplica a restrição
        if (primeiraLinguagem.equals("Assembly") && nrSpaces >= 3) {
            return false;
        } else if (primeiraLinguagem.equals("C") && nrSpaces >= 4) {
            return false;
        }

        // atualizar histórico antes de alterar a posição
        jogadorAtual.atualizarHistorico();

        int tamanho = board.getTamanho();
        int novaPosicao = jogadorAtual.getPosicao() + nrSpaces;

        // se ultrapassar o final do tabuleiro, faz ricochete
        if (novaPosicao > tamanho) {
            int excesso = novaPosicao - tamanho;
            novaPosicao = tamanho - excesso;
        }

        // aplicar a nova posição
        jogadorAtual.setPosicao(novaPosicao);

        jogadorAtual.incrementJogadas();

        // se o jogador chegou ao fim, o jogo termina
        if (novaPosicao == tamanho) {
            board.setCurrentPlayerID(idAtual); // passar o vencedor para o "current player"
            return true;
        }

        // passar o turno para o próximo jogador
        /*ArrayList<Integer> idsOrdenados = new ArrayList<>(board.getJogadores().keySet());
        idsOrdenados.sort(Integer::compareTo);
        int proximo = idsOrdenados.get((idsOrdenados.indexOf(idAtual) + 1) % idsOrdenados.size());
        board.setCurrentPlayerID(proximo);*/

        return true;
    }

    public String reactToAbyssOrTool() {

        int idParaReagir = board.getCurrentPlayerID();
        Player jogador = board.getJogadores().get(idParaReagir);

        if (jogador == null) {
            board.setTurnos(board.getTurnos() + 1);
            if (!gameIsOver()) {
                avancarTurno();
            }
            return null; // só aqui (situação anormal)
        }

        int posicao = jogador.getPosicao();

        // ferramenta tem prioridade
        if (board.getFerramentas().containsKey(posicao)) {
            Ferramenta f = board.getFerramentas().get(posicao);

            String msg;
            if (f == null) {
                msg = "Nada aconteceu.";
            } else if (!jogador.temFerramenta(f)) {
                jogador.adicionarFerramenta(f);
                msg = jogador.getNome() + " encontrou a ferramenta " + f.getNome() + "!";
            } else {
                msg = jogador.getNome() + " já tinha a ferramenta " + f.getNome() + ".";
            }

            board.setTurnos(board.getTurnos() + 1);
            if (!gameIsOver()) {
                avancarTurno();
            }
            return msg;
        }

        // abismo
        if (board.getAbismos().containsKey(posicao)) {
            Abismo a = board.getAbismos().get(posicao);

            if (a == null) {
                board.setTurnos(board.getTurnos() + 1);
                if (!gameIsOver()) {
                    avancarTurno();
                }
                return "Nada aconteceu.";
            }

            int aid = a.getId();

            if (jogador.temFerramentaQueAnula(a, board.getTurnos())) {
                jogador.usarFerramentaContra(a);
                String msg = jogador.getNome() + " evitou o abismo " + a.getNome() + "!";
                board.setTurnos(board.getTurnos() + 1);
                if (!gameIsOver()) {
                    avancarTurno();
                }
                return msg;
            }

            int novaPos = jogador.getPosicao();

            switch (aid) {
                case 0 -> novaPos = Math.max(1, novaPos - 1);
                case 1 -> {
                    int dado = board.getUltimoValorDado();
                    int n = (int) Math.floor(dado / 2.0);
                    novaPos = Math.max(1, novaPos - n);
                }
                case 2 -> novaPos = Math.max(1, novaPos - 2);
                case 3 -> novaPos = Math.max(1, novaPos - 3);
                case 4 -> novaPos = 1;
                case 5 -> novaPos = jogador.getPosicaoAnterior();
                case 6 -> novaPos = jogador.getPosicaoHaDoisTurnos();

                case 7 -> {
                    jogador.setDerrotado(true);
                    String msg = jogador.getNome() + " sofreu uma Blue Screen of Death!";
                    board.setTurnos(board.getTurnos() + 1);
                    if (!gameIsOver()) {
                        avancarTurno();
                    }
                    return msg;
                }

                case 8 -> {
                    jogador.setPreso(true);
                    String msg = jogador.getNome() + " ficou preso num Ciclo Infinito!";
                    board.setTurnos(board.getTurnos() + 1);
                    if (!gameIsOver()) {
                        avancarTurno();
                    }
                    return msg;
                }

                case 9 -> {
                    long count = board.getJogadores().values().stream()
                            .filter(p -> p.getPosicao() == posicao)
                            .count();

                    if (count > 1) {
                        for (Player p : board.getJogadores().values()) {
                            if (p.getPosicao() == posicao) {
                                p.setPosicao(Math.max(1, p.getPosicao() - 3));
                            }
                        }
                    }

                    String msg = jogador.getNome() + " caiu no abismo " + a.getNome() + "!";
                    board.setTurnos(board.getTurnos() + 1);
                    if (!gameIsOver()) {
                        avancarTurno();
                    }
                    return msg;
                }

                case 20 -> {
                    if (jogador.getJogadas() < 4) {
                        boolean temAjuda = jogador.temFerramenta("Ajuda Do Professor");
                        if (temAjuda) {
                            jogador.usarFerramenta("Ajuda Do Professor");
                            String msg = jogador.getNome() + " evitou o abismo LLM com Ajuda do Professor!";
                            board.setTurnos(board.getTurnos() + 1);
                            if (!gameIsOver()) {
                                avancarTurno();
                            }
                            return msg;
                        }

                        jogador.setPosicao(jogador.getPosicaoAnterior());
                        String msg = jogador.getNome() + " recuou devido ao abismo LLM!";
                        board.setTurnos(board.getTurnos() + 1);
                        if (!gameIsOver()) {
                            avancarTurno();
                        }
                        return msg;
                    }

                    int avancar = board.getUltimoValorDado();
                    int novaPosLLM = jogador.getPosicao() + avancar;
                    if (novaPosLLM > board.getTamanho()) {
                        int excesso = novaPosLLM - board.getTamanho();
                        novaPosLLM = board.getTamanho() - excesso;
                    }

                    jogador.setPosicao(novaPosLLM);
                    if (novaPosLLM == board.getTamanho()) {
                        board.setCurrentPlayerID(jogador.getId());
                    }

                    String msg = jogador.getNome() + " beneficiou do LLM e avançou " + avancar + " casas!";
                    board.setTurnos(board.getTurnos() + 1);
                    if (!gameIsOver()) {
                        avancarTurno();
                    }
                    return msg;
                }
            }

            jogador.setPosicao(novaPos);

            String msg = jogador.getNome() + " caiu no abismo " + a.getNome() +
                    " e foi parar à casa " + novaPos + "!";

            board.setTurnos(board.getTurnos() + 1);
            if (!gameIsOver()) {
                avancarTurno();
            }
            return msg;
        }

        // casa vazia -> null (regra dos testes)
        board.setTurnos(board.getTurnos() + 1);
        if (!gameIsOver()) {
            avancarTurno();
        }
        return null;
    }

    private boolean nenhumJogadorPodeJogar() {
        for (Player p : board.getJogadores().values()) {
            if (!p.isDerrotado() && !p.isPreso()) {
                return false; // há pelo menos um jogador ativo
            }
        }

        return true; // todos estão presos ou derrotados
    }

    public boolean gameIsOver() {
        if (board.getJogadores().isEmpty()) {
            return false;
        }

        int meta = board.getTamanho();
        int winnerId = -1;

        for (Player p : board.getJogadores().values()) {
            if (p.getPosicao() == meta) {
                if (winnerId == -1 || p.getId() < winnerId) {
                    winnerId = p.getId(); // menor ID vence
                }
            }
        }

        if (winnerId != -1) {
            board.setCurrentPlayerID(winnerId);
            return true;
        }

        if (nenhumJogadorPodeJogar()) {

            int bestPos = -1;
            int bestId = -1;

            for (Player p : board.getJogadores().values()) {
                if (p.getPosicao() > bestPos) {
                    bestPos = p.getPosicao();
                    bestId = p.getId();
                } else if (p.getPosicao() == bestPos) {
                    if (bestId == -1 || p.getId() < bestId) {
                        bestId = p.getId();
                    }
                }
            }

            board.setCurrentPlayerID(bestId);
            return true;
        }

        return false;
    }




    public ArrayList<String> getGameResults() {
        ArrayList<String> resultados = new ArrayList<>();

        //caso o jogo ainda não tenha terminado, retorna lista vazia
        if (!gameIsOver() || board.getJogadores().isEmpty()) {
            return resultados;
        }

        resultados.add("THE GREAT PROGRAMMING JOURNEY");
        resultados.add("");

        //nr total de turnos
        resultados.add("NR. DE TURNOS");


        int turnos = board.getTurnos();
        //garantimos que a jogada da vitória é contada
        if (gameIsOver()) {
            turnos++;
        }
        resultados.add(String.valueOf(turnos));
        resultados.add(""); // linha vazia

        if (nenhumJogadorPodeJogar()) {
            resultados.add("O jogo terminou empatado.");
            resultados.add("");
            resultados.add("Participantes:");

            ArrayList<Player> lista = new ArrayList<>(board.getJogadores().values());

            // ordenar por posição desc; se empatar, por nome asc
            lista.sort((p1, p2) -> {
                int cmp = Integer.compare(p2.getPosicao(), p1.getPosicao());
                if (cmp != 0) return cmp;
                return p1.getNome().compareToIgnoreCase(p2.getNome());
            });

            for (Player p : lista) {
                String estado;
                if (p.isPreso()) {
                    estado = "Ciclo Infinito";
                } else if (p.isDerrotado()) {
                    estado = "Blue Screen of Death";
                } else {
                    estado = "Em Jogo";
                }

                resultados.add(p.getNome() + " : " + p.getPosicao() + " : " + estado);
            }

            return resultados;
        }

        //vencedor
        resultados.add("VENCEDOR");
        Player vencedor = null;
        int meta = board.getTamanho();

        for (Player p : board.getJogadores().values()) {
            if (p.getPosicao() == meta) {
                vencedor = p;
                break;
            }
        }

        if (vencedor == null) {
            return resultados; //ainda não há vencedor —> lista vazia
        }

        resultados.add(vencedor.getNome());
        resultados.add("");

        //Restantes jogadores (por proximidade à meta)
        resultados.add("RESTANTES");
        ArrayList<Player> restantes = new ArrayList<>(board.getJogadores().values());
        restantes.remove(vencedor);

        //ordena por posição (maior → mais próximo da meta)
        restantes.sort((p1, p2) -> {
            int cmp = Integer.compare(p2.getPosicao(), p1.getPosicao());
            if (cmp == 0) {
                return p1.getNome().compareToIgnoreCase(p2.getNome()); // ordem alfabética se empatados
            }
            return cmp;
        });

        for (Player p : restantes) {
            resultados.add(p.getNome() + " " + p.getPosicao());
        }

        return resultados;
    }

    public void loadGame(File file) throws InvalidFileException, FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException("Ficheiro não encontrado: " + file.getName());
        }

        try {
            board = new Board();
            Scanner sc = new Scanner(file);
            String secao = "";

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                // Identificar cabeçalhos principais
                if (line.startsWith("BOARD_SIZE=")) {
                    board.setTamanho(Integer.parseInt(line.split("=")[1]));
                    continue;
                }

                if (line.startsWith("TURN=")) {
                    board.setTurnos(Integer.parseInt(line.split("=")[1]));
                    continue;
                }

                if (line.startsWith("CURRENT_PLAYER=")) {
                    board.setCurrentPlayerID(Integer.parseInt(line.split("=")[1]));
                    continue;
                }

                // Detetar secções
                if (line.equals("[PLAYERS]") || line.equals("[ABISMS]") || line.equals("[TOOLS]")) {
                    secao = line;
                    continue;
                }

                // --- PLAYERS ---
                if (secao.equals("[PLAYERS]")) {
                    String[] p = line.split(";");
                    Player jogador = new Player(Integer.parseInt(p[0]), p[1], p[2]);

                    // Linguagens
                    if (!p[3].isEmpty()) {
                        for (String l : p[3].split(",")) {
                            jogador.adicionarLinguagem(l);
                        }
                    }

                    // Ferramentas
                    if (!p[4].isEmpty()) {
                        for (String fNome : p[4].split(",")) {
                            Ferramenta f = new Ferramenta(0, fNome);
                            jogador.adicionarFerramenta(f);
                        }
                    }

                    jogador.setPosicao(Integer.parseInt(p[5]));
                    if (p[6].equals("DERROTADO")) {
                        jogador.setDerrotado(true);
                    }

                    board.getJogadores().put(jogador.getId(), jogador);
                }

                // --- ABISMS ---
                else if (secao.equals("[ABISMS]")) {
                    String[] a = line.split(";");
                    int id = Integer.parseInt(a[0]);
                    int pos = Integer.parseInt(a[1]);
                    board.getAbismos().put(pos, new Abismo(id, pos));
                }

                // --- TOOLS ---
                else if (secao.equals("[TOOLS]")) {
                    String[] f = line.split(";");
                    int id = Integer.parseInt(f[0]);
                    int pos = Integer.parseInt(f[1]);

                    String nome = switch (id) {
                        case 0 -> "Herança";
                        case 1 -> "Programação Funcional";
                        case 2 -> "Testes Unitários";
                        case 3 -> "Tratamento de Excepções";
                        case 4 -> "IDE";
                        case 5 -> "Ajuda Do Professor";
                        default -> "Ferramenta Desconhecida";
                    };

                    board.getFerramentas().put(pos, new Ferramenta(id, nome));
                }
            }

            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidFileException("Erro a carregar o jogo: " + e.getMessage());
        }
    }

    public boolean saveGame(File file) {
        try {
            PrintWriter pw = new PrintWriter(file);

            // Cabeçalho
            pw.println("BOARD_SIZE=" + board.getTamanho());
            pw.println("TURN=" + board.getTurnos());
            pw.println("CURRENT_PLAYER=" + board.getCurrentPlayerID());

            // --- Guardar jogadores ---
            pw.println("[PLAYERS]");
            for (Player p : board.getJogadores().values()) {
                pw.print(p.getId() + ";" + p.getNome() + ";" + p.getCor() + ";");

                // linguagens
                pw.print(String.join(",", p.getLinguagensFavoritas()) + ";");

                // ferramentas
                ArrayList<String> nomesFerramentas = new ArrayList<>();
                for (Ferramenta f : p.getFerramentas()) {
                    nomesFerramentas.add(f.getNome());
                }
                pw.print(String.join(",", nomesFerramentas) + ";");

                // posição e estado
                pw.print(p.getPosicao() + ";");
                pw.print(p.isDerrotado() ? "DERROTADO" : "EM_JOGO");
                pw.println();
            }

            // --- Guardar abismos ---
            pw.println("[ABISMS]");
            for (Map.Entry<Integer, Abismo> e : board.getAbismos().entrySet()) {
                Abismo a = e.getValue();
                pw.println(a.getId() + ";" + e.getKey());
            }

            // --- Guardar ferramentas ---
            pw.println("[TOOLS]");
            for (Map.Entry<Integer, Ferramenta> e : board.getFerramentas().entrySet()) {
                Ferramenta f = e.getValue();
                pw.println(f.getId() + ";" + e.getKey());
            }

            pw.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public JPanel getAuthorsPanel(){
        //painel
        JPanel painel = new JPanel();
        painel.setSize(300, 300);

        //cria o label
        JLabel autor = new JLabel("<html><center>"
                + "Nome: Sara Isabel | Núria Fernandes<br>"
                + "Número: a22307800 | 22305146<br>"
                + "Disciplina: Linguagens de Programação II"
                + "</center></html>");

        //centraliza o texto
        autor.setHorizontalAlignment(SwingConstants.CENTER);

        //adiciona o label ao painel
        painel.add(autor);

        //devolve o painel para o visualizador mostrar
        return painel;
    }

    public HashMap<String, String> customizeBoard(){
        return new HashMap<>();
    }

    public Board getBoard() {
        return board;
    }

    private void avancarTurno() {
        ArrayList<Integer> idsOrdenados = new ArrayList<>(board.getJogadores().keySet());
        idsOrdenados.sort(Integer::compareTo);

        int atual = board.getCurrentPlayerID();
        int idx = idsOrdenados.indexOf(atual);

        // se o atual não estiver na lista, mete o menor ID não derrotado
        if (idx < 0) {
            for (int id : idsOrdenados) {
                Player p = board.getJogadores().get(id);
                if (p != null && !p.isDerrotado()) {
                    board.setCurrentPlayerID(id);
                    return;
                }
            }
            return;
        }

        for (int i = 1; i <= idsOrdenados.size(); i++) {
            int cand = idsOrdenados.get((idx + i) % idsOrdenados.size());
            Player p = board.getJogadores().get(cand);

            // só salta DERROTADOS (presos continuam na rotação)
            if (p != null && !p.isDerrotado()) {
                board.setCurrentPlayerID(cand);
                return;
            }
        }
    }

}