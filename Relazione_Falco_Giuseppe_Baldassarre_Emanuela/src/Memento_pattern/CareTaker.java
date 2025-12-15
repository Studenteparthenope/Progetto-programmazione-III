package Memento_pattern;

import Strategy.Ricetta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CareTaker implements Originator {

    private static final String FILE_PATH = "Ricette_salvate.txt";

    // Mappa per associare gli utenti alle loro ricette salvate
    private List<Memento> mementos = new ArrayList<>();
    private Ricetta ricettaCorrente;

    // Costruttore con parametro Ricetta (come prima)
    public CareTaker(Ricetta ricetta) {
        this.ricettaCorrente = ricetta;
    }

    // Costruttore vuoto che crea una Ricetta di default
    public CareTaker() {
        // Inizializza con una ricetta di default se non viene fornita una ricetta
        this.ricettaCorrente = new Ricetta(
            "Ricetta di default",  // Nome
            0,                     // Tempo di preparazione
            "Tipo",                // Tipo
            "Facile",              // Difficoltà
            "Nessuno",             // Regime alimentare
            "Ingrediente1",        // Ingrediente 1
            "Ingrediente2",        // Ingrediente 2
            "Ingrediente3",        // Ingrediente 3
            "Ingrediente4"         // Ingrediente 4
        );
    }

    // Aggiungi un Memento alla lista e salva nel file
    public void addMemento(Memento memento, String nomeUtente) {
        try {
            // Leggi tutto il file per verificare la presenza dell'utente
            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            boolean userFound = false;
            boolean written = false;

            // Leggi tutte le righe esistenti e memorizzale
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            // Aggiungi o aggiorna l'utente
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
            for (String existingLine : lines) {
                if (existingLine.equals(nomeUtente)) {
                    userFound = true;
                    if (!written) {
                        // Aggiungi la ricetta per questo utente
                        writer.write(existingLine);  // Scrivi il nome utente
                        writer.newLine();
                        writer.write(formatRicetta(memento.getRicetta()));  // Scrivi la ricetta
                        writer.newLine();
                        written = true;
                    } else {
                        writer.write(existingLine);  // Scrivi la riga precedente se l'utente è trovato
                        writer.newLine();
                    }
                } else {
                    writer.write(existingLine);  // Scrivi la riga esistente
                    writer.newLine();
                }
            }

            // Se l'utente non è stato trovato, aggiungilo alla fine del file
            if (!userFound && !written) {
                writer.write(nomeUtente);
                writer.newLine();
                writer.write(formatRicetta(memento.getRicetta()));  // Scrivi la ricetta
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carica tutte le ricette salvate da un determinato utente
    public List<Ricetta> getRicetteByUser(String nomeUtente) {
        List<Ricetta> ricetteSalvate = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean userFound = false;

            // Leggi tutte le righe del file
            while ((line = reader.readLine()) != null) {
                if (line.equals(nomeUtente)) {
                    userFound = true;
                    continue; // Salta il nome dell'utente
                }

                if (userFound && line.contains(",")) {
                    // Se l'utente è trovato, la riga è una ricetta
                    String[] parts = line.split(",");
                    if (parts.length == 9) {
                        Ricetta ricetta = new Ricetta(
                            parts[0],  // Nome
                            Integer.parseInt(parts[1]),  // Tempo
                            parts[2],  // Tipo
                            parts[3],  // Difficoltà
                            parts[4],  // Regime alimentare
                            parts[5],  // Ingrediente 1
                            parts[6],  // Ingrediente 2
                            parts[7],  // Ingrediente 3
                            parts[8]   // Ingrediente 4
                        );
                        ricetteSalvate.add(ricetta);
                    }
                } else if (userFound && !line.isEmpty()) {
                    // Se troviamo una nuova sezione per un altro utente, fermiamoci
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ricetteSalvate;
    }

    // Formatta la ricetta in formato stringa per il salvataggio
    private String formatRicetta(Ricetta ricetta) {
        return String.join(",", ricetta.getNome(),
            String.valueOf(ricetta.getTempi_di_preparazione()),
            ricetta.getTipo(),
            ricetta.getDifficolta(),
            ricetta.getRegime_alimentare(),
            ricetta.getIngrediente1(),
            ricetta.getIngrediente2(),
            ricetta.getIngrediente3(),
            ricetta.getIngrediente4());
    }

    // Metodo implementato da Originator per salvare lo stato
    @Override
    public Memento saveStateToMemento() {
        return new Memento(ricettaCorrente);
    }

    // Metodo implementato da Originator per ripristinare lo stato
    @Override
    public void restoreStateFromMemento(Memento memento) {
        if (memento != null) {
            this.ricettaCorrente = memento.getRicetta();
        }
    }

    // Metodo per ottenere la ricetta corrente
    public Ricetta getRicetta() {
        return ricettaCorrente;
    }

    @Override
    public String toString() {
        return ricettaCorrente.toString();
    }
}

