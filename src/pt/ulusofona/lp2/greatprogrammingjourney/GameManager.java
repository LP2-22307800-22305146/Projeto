package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {
    private HashMap<Integer, Player> jogadores = new HashMap<>(); // ->
    private int boardSize; //guarda valor do tabuliero
    private int currentPlayerID;
    private int count;

    //construtor vazio
    public GameManager() {

    }

    //
    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        if (playerInfo == null || playerInfo.length < 2 || playerInfo.length > 4) {
            return false;
        }

        jogadores.clear();

        ArrayList<String> coresValidas = new ArrayList<>();
        coresValidas.add("PURPLE");
        coresValidas.add("GREEN");
        coresValidas.add("BROWN");
        coresValidas.add("BLUE");

        if (worldSize < playerInfo.length * 2) {
            return false;
        }

        for (String[] info : playerInfo) {
            if (info.length < 3) {
                return false;
            }
            try {
                int id = Integer.parseInt(info[0].trim());
                String nome = info[1].trim();
                String cor = info[2].trim().toUpperCase();

                if (id <= 0 || jogadores.containsKey(id) || nome.isEmpty() || !coresValidas.contains(cor)) {
                    return false;
                }

                //Cria o jogador já na posição 1
                Player novo = new Player(id, nome, cor);
                novo.setPosicao(1); //garante que começa na casa 1
                jogadores.put(id, novo);

            } catch (Exception e) {
                return false;
            }
        }

        //guarda tamanho do tabuleiro
        this.boardSize = worldSize;

        //define o jogador atual (menor ID)
        currentPlayerID = Integer.MAX_VALUE;
        for (int id : jogadores.keySet()) {
            if (id < currentPlayerID) {
                currentPlayerID = id;
            }
        }

        //inicializa contador de turnos
        count = 0;

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
