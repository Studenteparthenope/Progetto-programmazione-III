package Memento_pattern;

import Strategy.Ricetta;
/*
public class Memento {
    private Ricetta ricetta;

    // Costruttore
    public Memento(Ricetta ricetta) {
        this.ricetta = ricetta;
    }

    // Metodo per ottenere la ricetta memorizzata nel Memento
    public Ricetta getRicetta() {
        return ricetta;
    }
}*/
import java.io.Serializable;

public class Memento implements Serializable {
    private static final long serialVersionUID = 1L;
    private Ricetta ricetta;

    // Costruttore
    public Memento(Ricetta ricetta) {
        this.ricetta = ricetta;
    }

    // Metodo per ottenere la ricetta memorizzata nel Memento
    public Ricetta getRicetta() {
        return ricetta;
    }
}