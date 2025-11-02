package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GameManager {

    private Board board; // agora o tabuleiro é um objeto!

    //construtor vazio
    public GameManager() {

        this.board = new Board();

    }

    //
    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {

        // Validação inicial do array
        if (playerInfo == null || playerInfo.length < 2 || playerInfo.length > 4) {
            return false;
        }

        board.jogadores.clear();

        // Validação do tamanho mínimo do tabuleiro
        if (worldSize < playerInfo.length * 2) {
            return false;
        }

        // Conjunto auxiliar para evitar cores duplicadas
        Set<String> coresUsadas = new HashSet<>();

        // Processar cada jogador
        for (String[] info : playerInfo) {

            // Cada jogador precisa de 5 campos: ID, Nome, Linguagens, Cor, Posição
            if (info == null || info.length < 3 || info[0] == null || info[1] == null || info[2] == null) {
                return false;
            }


            // Validar campos não nulos
            for (String campo : info) {
                if (campo == null) return false;
            }


            try {
                int id = Integer.parseInt(info[0].trim());
                String nome = info[1].trim();
                String linguagens = info[2].trim();
                String cor = info[3].trim();
                String posicao = info[4].trim();

                // Validar ID
                if (id <= 0 || board.jogadores.containsKey(id)) {
                    return false;
                }


                // Validar nome
                if (nome.isEmpty()) {
                    return false;
                }

                // Validar linguagens (não pode ser vazio)
                if (linguagens.isEmpty()) {
                    return false;
                }

                //aceitar cores com letras minúsculas ou espaços
                if (!cor.equalsIgnoreCase("Purple") &&
                        !cor.equalsIgnoreCase("Green") &&
                        !cor.equalsIgnoreCase("Brown") &&
                        !cor.equalsIgnoreCase("Blue")) {
                    return false;
                }

                // Impedir cores duplicadas
                if (!coresUsadas.add(cor.toLowerCase())) {
                    return false; // cor já escolhida por outro jogador
                }

                // Validar posição (não pode ser vazia)
                if (posicao.isEmpty()) {
                    return false;
                }


                //criar jogador (posição 1 já definida no construtor)
                Player novo = new Player(id, nome, cor);
                board.jogadores.put(id, novo);

            } catch (Exception e) {
                return false;
            }
        }
        //guardar tamanho
        board.setTamanho(worldSize);

        // Definir jogador atual (menor ID)
        int current = Integer.MAX_VALUE;
        for (int id : board.getJogadores().keySet()) {
            if (id < current) {
                current = id;
            }
        }
        board.setCurrentPlayerID(current);

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
        String[] info = new String[5];

        info[0] = String.valueOf(p.getId());               // ID
        info[1] = p.getNome();                             // Nome
        info[2] = p.linguagensComoString();                // Linguagens separadas por ";"
        info[3] = p.getCor().toUpperCase();                // Cor em maiúsculas
        info[4] = String.valueOf(p.getPosicao());          // Posição atual

        return info;
    }

    public String getProgrammerInfoAsStr(int id) {
        //verifica se o jogador existe
        if (!board.getJogadores().containsKey(id)) {
            return null;
        }

        //vai buscar o jogador ao mapa
        Player p = board.getJogadores().get(id);

        //ordenar as linguagens favoritas de forma manual
        ArrayList<String> linguagens = p.getLinguagensFavoritas();

        //troca se estiver fora de ordem (alfabética)
        for (int i = 0; i < linguagens.size(); i++) {
            for (int j = i + 1; j < linguagens.size(); j++) {
                String lang1 = linguagens.get(i);
                String lang2 = linguagens.get(j);

                //compara letra a letra
                if (lang1.length() > 0 && lang2.length() > 0) {
                    char c1 = lang1.charAt(0);
                    char c2 = lang2.charAt(0);
                    if (c1 > c2) {
                        //troca de posição
                        linguagens.set(i, lang2);
                        linguagens.set(j, lang1);
                    }
                }
            }
        }

        //juntar todas as linguagens numa String separadas por "; "
        String linguagensStr = "";
        for (int i = 0; i < linguagens.size(); i++) {
            linguagensStr = linguagensStr + linguagens.get(i);
            if (i < linguagens.size() - 1) {
                linguagensStr = linguagensStr + "; ";
            }
        }

        //determinar o estado do jogador
        String estado;
        if (p.isDerrotado()) {
            estado = "Derrotado";
        } else {
            estado = "Em Jogo";
        }

        //string final
        String resultado = p.getId() + " | " + p.getNome() + " | " +
                p.getPosicao() + " | " + linguagensStr + " | " + estado;

        return resultado;
    }


    public String[] getSlotInfo(int position){
        return null;
    }


    public int getCurrentPlayerID(){
        return 0;
    }


    public boolean moveCurrentPlayer(int nrSpaces){
        return false;
    }


    public boolean gameIsOver(){
        return false;
    }


    public ArrayList<String> getGameResults(){
        return null;
    }


    public JPanel getAuthorsPanel(){
        return null;
    }


    public HashMap<String, String> customizeBoard(){
        return new HashMap<>();
    }


    //git

    // "olá"
    // mubdo
}