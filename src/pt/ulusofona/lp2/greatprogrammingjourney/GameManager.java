package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {
    private HashMap<Integer, Player> jogadores = new HashMap<>(); // ->
    private int boardSize; //guarda valor do tabuliero

    //construtor vazio
    public GameManager() {

    }

    //
    public boolean createInitialBoard(String[][] playerInfo, int worldSize){
        //ve se os jogadores sao entre 2 e 4
        if (playerInfo.length < 2 || playerInfo.length > 4)
            return false;
        //limpa jogo anterior para evitar erros
        jogadores.clear();

        //cores validas
        ArrayList<String> coresValidas = new ArrayList<>();
        coresValidas.add("Purple");
        coresValidas.add("Green");
        coresValidas.add("Brown");
        coresValidas.add("Blue");

        //tamanho do tabuleiro
        if (worldSize < playerInfo.length * 2) {
            return false;
        }

        //percorre cada jogadoor
        for (String[] info : playerInfo) {
            try {
                //info[0] = id, info[1] = nome, info[2] = cor
                int id = Integer.parseInt(info[0]);
                String nome = info[1].trim();
                String cor = info[2].trim();

                //ID positivo
                if (id <= 0) {
                    return false;
                }

                //ID já existe
                if (jogadores.containsKey(id)) {
                    return false;
                }

                //nome não vazio
                if (nome.isEmpty()) {
                    return false;
                }

                //cor
                if (!coresValidas.contains(cor)) {
                    return false;
                }

                //Criar jogador e guardar no mapa
                Player novo = new Player(id, nome, cor);
                jogadores.put(id, novo);

            } catch (Exception e) {
                //Se der erro (ID não é número), falha a criação
                return false;
            }
        }
        //guardar valor do tabuleiro para os proximos metodos
        this.boardSize = worldSize;
        //todas as validações passarem
        return true;


    }

    public String getImagePng(int nrSquare) {
        //valida intervalo
        if (nrSquare < 1 || nrSquare > boardSize) {
            return null;
        }

        //ultima casa (meta)
        if (nrSquare == boardSize) {
            return "glory.png";
        }

        return null;
    }

    public String[] getProgrammerInfo(int id){
        return null;
    }

    public String getProgrammerInfoAsStr(int id){
        return "";
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
