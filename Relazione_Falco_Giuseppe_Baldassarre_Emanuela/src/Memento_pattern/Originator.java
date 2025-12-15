package Memento_pattern;

import Strategy.Ricetta;

public interface Originator {
    // Crea un Memento per memorizzare lo stato corrente
    Memento saveStateToMemento();
    
    // Ripristina lo stato da un Memento
    void restoreStateFromMemento(Memento memento);
}
