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

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {

        // Validação inicial do array
        if (playerInfo == null || playerInfo.length < 2 || playerInfo.length > 4) {
            return false;
        }

        board.getJogadores().clear();

        // Validação do tamanho mínimo do tabuleiro
        if (worldSize < playerInfo.length * 2) {
            return false;
        }

        // Conjunto auxiliar para evitar cores duplicadas
        Set<Integer> idsUsados = new HashSet<>();
        Set<String> coresUsadas = new HashSet<>();

        // Processar cada jogador
        for (String[] info : playerInfo) {

            // Pode haver 3 ou 5 campos — aceitar ambos
            if (info == null || info.length < 3) {
                return false;
            }


            try {
                int id = Integer.parseInt(info[0].trim());

                if (info[1] == null || info[1].trim().isEmpty()) {
                    return false;
                }
                String nome = info[1].trim();

                String cor = info[info.length >= 4 ? 3 : 2].trim();
                // aceita formato [id, nome, linguagens, cor, posicao] ou [id, nome, cor]

                // Validar ID
                if (id <= 0 || !idsUsados.add(id)) {
                    return false;
                }


                // Validar nome
                if (nome.isEmpty()) {
                    return false;
                }


                // Cor válida
                if (!cor.equals("Purple") &&
                        !cor.equals("Green") &&
                        !cor.equals("Brown") &&
                        !cor.equals("Blue")) {
                    return false;
                }

                // Impedir cores duplicadas
                if (!coresUsadas.add(cor)) {
                    return false; // cor já escolhida por outro jogador
                }


                //criar jogador (posição 1 já definida no construtor)
                Player novo = new Player(id, nome, cor);
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