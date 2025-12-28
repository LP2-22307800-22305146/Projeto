package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {

    private int tamanho;                        // tamanho do tabuleiro
    private HashMap<Integer, Player> jogadores;       // jogadores por ID
    private int currentPlayerID;                  // ID do jogador atual
    private int turnos;     // contador de turnos
    private HashMap<Integer, Abismo> abismos = new HashMap<>();
    private HashMap<Integer, Ferramenta> ferramentas = new HashMap<>();
    private int ultimoValorDado = 0;
    private boolean esperaReacao = false;
    private int ultimoJogadorMovido;

    public Board() {
        this.jogadores = new HashMap<>();
        this.turnos = 0;
        this.tamanho = 0;
        this.currentPlayerID = 0;
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

    public Board getBoard() {
        return this;
    }

    public HashMap<Integer, Abismo> getAbismos() {
        return abismos;
    }

    public HashMap<Integer, Ferramenta> getFerramentas() {
        return ferramentas;
    }

    public boolean getEsperaReacao() {
        return esperaReacao;
    }

    public int getUltimoValorDado() {
        return ultimoValorDado;
    }

    public int getCurrentPlayerID() {
        return currentPlayerID;
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

    public void setUltimoValorDado(int valor) {
        this.ultimoValorDado = valor;
    }

    public void setEsperaReacao(boolean reacao) {
        esperaReacao = reacao;
    }

    // MÉTODOS

    public void avancaProximoJogador() {
        if (jogadores == null || jogadores.isEmpty()) {
            return;
        }

        ArrayList<Integer> ids = new ArrayList<>(jogadores.keySet());
        ids.sort(Integer::compareTo);

        int atual = currentPlayerID;

        // segurança extra
        if (!ids.contains(atual)) {
            currentPlayerID = ids.get(0);
            return;
        }

        int proximo = ids.get((ids.indexOf(atual) + 1) % ids.size());
        currentPlayerID = proximo;
    }

    public void printBoard() {
        System.out.println("=== Estado atual do tabuleiro ===");
        System.out.println("Tamanho: " + tamanho);
        System.out.println("Turno: " + turnos);
        System.out.println("Jogadores:");
        for (Player p : jogadores.values()) {
            System.out.println(" - " + p);
        }
        System.out.println("Abismos:");
        for (Abismo a : abismos.values()) {
            System.out.println(" - " + a);
        }
        System.out.println("Ferramentas:");
        for (Ferramenta f : ferramentas.values()) {
            System.out.println(" - " + f);
        }
    }


    public void setUltimoJogadorMovido(int id) {
        this.ultimoJogadorMovido = id;
    }

    public int getUltimoJogadorMovido() {
        return ultimoJogadorMovido;
    }
}