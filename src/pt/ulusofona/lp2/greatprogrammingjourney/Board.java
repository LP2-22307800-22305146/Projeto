package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.HashMap;

public class Board {

    public int tamanho;                        // tamanho do tabuleiro
    public HashMap<Integer, Player> jogadores;       // jogadores por ID
    public int currentPlayerID;                  // ID do jogador atual
    public int turnos;                        // contador de turnos

    public Board() {
        this.jogadores = new HashMap<>();
        this.turnos = 0;
        this.tamanho = 0;
        this.currentPlayerID = -1;
    }

    // Getters
    public int getTamanho() {
        return tamanho;
    }

    public int getTurnos() {
        return turnos;
    }

    public HashMap<Integer, Player> getJogadores() {
        return jogadores;
    }

    public Board getBoard () {
        return this;
    }


    // Setters
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public void setCurrentPlayerID(int currentPlayerID) {
        this.currentPlayerID = currentPlayerID;
    }

    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }



    public void printBoard() {
        System.out.println("=== Estado atual do tabuleiro ===");
        System.out.println("Tamanho: " + tamanho);
        System.out.println("Turno: " + turnos);
        System.out.println("Jogadores:");
        for (Player p : jogadores.values()) {
            System.out.println(" - " + p);
        }
    }


}
