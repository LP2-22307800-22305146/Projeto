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

        if (worldSize < playerInfo.length * 2) {
            return false;
        }

        for (String[] info : playerInfo) {
            if (info == null || info.length < 3 || info[0] == null || info[1] == null || info[2] == null) {
                return false;
            }

            try {
                int id = Integer.parseInt(info[0].trim());
                String nome = info[1].trim();
                String cor = info[2].trim();

                if (id <= 0 || jogadores.containsKey(id) || nome.isEmpty()) {
                    return false;
                }

            //aceitar cores com letras minúsculas ou espaços
                if (!cor.equalsIgnoreCase("Purple") &&
                        !cor.equalsIgnoreCase("Green") &&
                        !cor.equalsIgnoreCase("Brown") &&
                        !cor.equalsIgnoreCase("Blue")) {
                    return false;
                }


                //criar jogador (posição 1 já definida no construtor)
                Player novo = new Player(id, nome, cor);
                jogadores.put(id, novo);

            } catch (Exception e) {
                return false;
            }
        }
        //guardar tamanho
        this.boardSize = worldSize;

        //definir jogador atual (menor ID)
        currentPlayerID = Integer.MAX_VALUE;
        for (int id : jogadores.keySet()) {
            if (id < currentPlayerID) {
                currentPlayerID = id;
            }
        }
        //inicializar contador de turnos
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
