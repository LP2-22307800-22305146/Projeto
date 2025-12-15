package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
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
                        if (id < 0 || id > 9) {
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
        // posição fora do tabuleiro
        if (position < 1 || position > board.getTamanho()) {
            return null;
        }

        // --- [0] IDs dos jogadores ---
        StringBuilder ids = new StringBuilder();
        for (Player p : board.getJogadores().values()) {
            if (p.getPosicao() == position) {
                if (ids.length() > 0) {
                    ids.append(",");
                }
                ids.append(p.getId());
            }
        }

        // --- [1] Descrição ---
        String descricao = "";
        String tipoEId = "";

        // verificar abismos
        if (board.getAbismos().containsKey(position)) {
            Abismo a = board.getAbismos().get(position);
            descricao = switch (a.getId()) {
                case 0 -> "Erro de sintaxe";
                case 1 -> "Erro de Lógica";
                case 2 -> "Exception";
                case 3 -> "FileNotFoundException";
                case 4 -> "Crash";
                case 5 -> "Código Duplicado";
                case 6 -> "Efeitos Secundários";
                case 7 -> "Blue Screen of Death";
                case 8 -> "Ciclo Infinito";
                case 9 -> "Segmentation Fault";
                default -> "Desconhecido";
            };
            tipoEId = "A:" + a.getId();
        }

        // verificar ferramentas (só se não houver abismo)
        else if (board.getFerramentas().containsKey(position)) {
            Ferramenta f = board.getFerramentas().get(position);
            descricao = f.getNome();
            tipoEId = "T:" + f.getId();
        }

        // --- Retornar sempre 3 elementos ---
        return new String[]{
                ids.toString(),
                descricao,
                tipoEId
        };
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
        if (jogadorAtual == null || jogadorAtual.isDerrotado() || jogadorAtual.isPreso()) {
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


        // se o jogador chegou ao fim, o jogo termina
        if (novaPosicao == tamanho) {
            return true;
        }

        // passar o turno para o próximo jogador
        ArrayList<Integer> idsOrdenados = new ArrayList<>(board.getJogadores().keySet());
        idsOrdenados.sort(Integer::compareTo);
        int proximo = idsOrdenados.get((idsOrdenados.indexOf(idAtual) + 1) % idsOrdenados.size());
        board.setCurrentPlayerID(proximo);

        return true;
    }


    public String reactToAbyssOrTool() {

        // Lista dos IDs dos jogadores
        List<Integer> ids = new ArrayList<>(board.getJogadores().keySet());
        Collections.sort(ids);

        int idParaReagir;
        int atual = board.getCurrentPlayerID();

        // se for o início do jogo ou existir apenas um jogador
        if (board.getTurnos() == 0 || ids.size() == 1) {
            idParaReagir = atual;
        } else {
            // o jogador que reagirá é o que acabou de jogar (anterior)
            int indexAtual = ids.indexOf(atual);
            idParaReagir = (indexAtual == 0) ? ids.get(ids.size() - 1) : ids.get(indexAtual - 1);
        }

        // para saber onde está o joador
        Player jogador = board.getJogadores().get(idParaReagir);
        int posicao = jogador.getPosicao();

        // verificar se há uma ferramenta na casa atual
        if (board.getFerramentas().containsKey(posicao)) {
            Ferramenta f = board.getFerramentas().get(posicao);
            if (!jogador.temFerramenta(f)) { // o jogador não tem ferramenta
                jogador.adicionarFerramenta(f);
                board.getFerramentas().remove(posicao);
                board.setTurnos(board.getTurnos() + 1);
                return jogador.getNome() + " encontrou a ferramenta " + f.getNome() + "!";
            } else {
                board.setTurnos(board.getTurnos() + 1);
                return jogador.getNome() + " já tinha a ferramenta " + f.getNome() + ".";
            }
        }

        // verificar se há um abismo na casa atual
        if (board.getAbismos().containsKey(posicao)) {
            Abismo a = board.getAbismos().get(posicao);

            // se o jogador tiver ferramenta, o abismo é anulado
            if (jogador.temFerramentaQueAnula(a)) { // o id da ferramenta é igual ao id do abismo
                jogador.usarFerramentaContra(a);
                board.setTurnos(board.getTurnos() + 1);
                return jogador.getNome() + " evitou o abismo " + a.getNome() + "!";
            }

            int novaPos = jogador.getPosicao();

            switch (a.getId()) {
                case 0:
                    // Erro de Sintaxe → recua 1 casa
                    novaPos = Math.max(1, novaPos - 1);
                    break;

                case 1:
                    // Erro de Lógica → recua N casas, N = floor(valor do dado / 2)
                    int dado = board.getUltimoValorDado();
                    int n = (int) Math.floor(dado / 2.0);
                    novaPos = Math.max(1, novaPos - n);
                    break;

                case 2:
                    // Exception → recua 2 casas
                    novaPos = Math.max(1, novaPos - 2);
                    break;

                case 3:
                    // FileNotFoundException → recua 3 casas
                    novaPos = Math.max(1, novaPos - 3);
                    break;

                case 4:
                    // Crash → volta à primeira casa
                    novaPos = 1;
                    break;

                case 5:
                    // Código Duplicado → volta para a posição anterior
                    novaPos = jogador.getPosicaoAnterior();
                    break;

                case 6:
                    // Efeitos Secundários → volta à posição de há dois turnos
                    novaPos = jogador.getPosicaoHaDoisTurnos();
                    break;

                case 7:
                    // Blue Screen of Death → o jogador é derrotado
                    jogador.setDerrotado(true);
                    board.setTurnos(board.getTurnos() + 1);
                    return jogador.getNome() + " sofreu uma Blue Screen of Death e foi derrotado!";

                case 8:
                    // Ciclo Infinito → o jogador fica preso, mas o jogo continua
                    jogador.setPreso(true);
                    board.setTurnos(board.getTurnos() + 1);
                    return jogador.getNome() + " ficou preso num ciclo infinito!";

                case 9:
                    // Segmentation Fault → todos os jogadores na mesma casa recuam 3 casas,
                    // apenas se houver dois ou mais jogadores nessa posição
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
                    break;
            }

            // atualizar posição e turno
            jogador.setPosicao(novaPos);
            board.setTurnos(board.getTurnos() + 1);
            return jogador.getNome() + " caiu no abismo " + a.getNome() + " e foi parar à casa " + novaPos + "!";
        }

        // casa vazia, apenas incrementa turno
        board.setTurnos(board.getTurnos() + 1);
        return null;
    }






    public boolean gameIsOver() {
        //se o tabuleiro não tem jogadores, o jogo não pode ter terminado
        if (board.getJogadores().isEmpty()) {
            return false;
        }


        int meta = board.getTamanho(); //ultima posição do tabuleiro

        //percorre todos os jogadores e verifica se alguém chegou à meta
        for (Player p : board.getJogadores().values()) {
            if (p.getPosicao() == meta) {
                return true; //o jogo termina imediatamente
            }
        }

        //ninguém chegou ainda
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
        restantes.sort((p1, p2) -> Integer.compare(p2.getPosicao(), p1.getPosicao()));

        for (Player p : restantes) {
            resultados.add(p.getNome() + " " + p.getPosicao());
        }

        return resultados;
    }

    public void loadGame(File file) throws InvalidFileException, FileNotFoundException {

    }

    public boolean saveGame(File file) {

        return true;

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


}