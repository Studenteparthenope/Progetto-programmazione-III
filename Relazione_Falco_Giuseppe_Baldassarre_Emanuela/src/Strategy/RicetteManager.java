package Strategy;

import java.io.BufferedReader;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Memento_pattern.*;


public class RicetteManager {
	public static class Main {
	    public static void main(String[] args) {
	        // Il nome utente viene passato come primo argomento
	        String nomeUtente = args.length > 0 ? args[0] : null;

	        Scanner scanner = new Scanner(System.in);
	        List<Ricetta> ricette = new ArrayList<>();
	        
	        // Carica le ricette dal file
	        try {
	            BufferedReader reader = new BufferedReader(new FileReader("ricette.txt"));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                // Supponiamo che i dati delle ricette siano separati da virgola
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
	                    ricette.add(ricetta);
	                }
	            }
	            reader.close();
	        } catch (IOException e) {
	            System.out.println("Errore nella lettura del file: " + e.getMessage());
	        }

	        // Se le ricette sono state caricate con successo, le stampiamo
	        System.out.println("Ricette caricate dal file:");
	        for (Ricetta ricetta : ricette) {
	            System.out.println(ricetta);
	        }

	        // Ora continua con il codice di filtraggio come prima
	        List<FiltraggioStrategy> filtri = new ArrayList<>();
	        boolean continuaRicerca = true;

	        // Creo l'oggetto CareTaker per gestire la lista dei mementi
	        CareTaker careTaker = new CareTaker();

	        // Continua a chiedere all'utente se vuole aggiungere filtri
	        while (continuaRicerca) {
	            System.out.println("Scegli un tipo di filtraggio:");
	            System.out.println("1. Filtraggio per tempo");
	            System.out.println("2. Filtraggio per tipo");
	            System.out.println("3. Filtraggio per difficoltà");
	            System.out.println("4. Filtraggio per regime alimentare");
	            System.out.println("5. Filtraggio per ingrediente");
	            System.out.println("6. Filtraggio per nome della ricetta");

	            int filtro = scanner.nextInt();
	            scanner.nextLine();  // Consuma la newline

	            switch (filtro) {
	                case 1:
	                    System.out.println("Inserisci il numero di minuti di preparazione massimo: ");
	                    int minutiCottura = scanner.nextInt();
	                    scanner.nextLine();  // Consuma la newline
	                    filtri.add(new FiltraggioperTempo(minutiCottura));
	                    break;

	                case 2:
	                    System.out.println("Inserisci il tipo di ricetta (es. 'Primo', 'Secondo','Antipasto','Dolce'): ");
	                    String tipo = scanner.nextLine();
	                    filtri.add(new FiltraggioPerTipo(tipo));
	                    break;

	                case 3:
	                    System.out.println("Inserisci la difficoltà (es. 'Facile', 'Media', 'Difficile'): ");
	                    String difficolta = scanner.nextLine();
	                    filtri.add(new FiltraggioPerDifficolta(difficolta));
	                    break;

	                case 4:
	                    System.out.println("Inserisci il regime alimentare (es. 'Vegetariano', 'Vegano', 'Carnivoro'): ");
	                    String regimeAlimentare = scanner.nextLine();
	                    filtri.add(new FiltraggioPerRegimeAlimentare(regimeAlimentare));
	                    break;
	                case 5:
	                    System.out.println("Inserisci 4 ingredienti che hai e vedi se qualche ricerca ne richiede alcuni di essi (es. 'Pomodorini', 'Limone', 'Rucola fresca'): ");
	                    String ingrediente1 = scanner.nextLine();
	                    String ingrediente2 = scanner.nextLine();
	                    String ingrediente3 = scanner.nextLine();
	                    String ingrediente4 = scanner.nextLine();
	                    filtri.add(new FiltraggioIngredienti(ingrediente1, ingrediente2, ingrediente3, ingrediente4));
	                    break;
	                case 6:
	                    System.out.println("Inserisci il nome della ricerca che stai cercando fra le seguenti che sono presenti nel sistema:");
	                    System.out.println("Spaghettata\nPollo al limone\nHummus di ceci\nPasta con zucchine e parmigiano\nInvoltini di prosciutto crudo con formaggio cremoso\nPalline di cacao e cocco\nInsalata di quinoa e avocado\nRisotto ai funghi porcini\nPollo al curry con riso basmati\nSpaghetti alle vongole\nTorta di carote e noci\nBurgers di lenticchie e quinoa");


	                    String Nome = scanner.nextLine();
	                    filtri.add(new FiltraggioNome(Nome));
	                    break;

	                default:
	                    System.out.println("Scelta non valida.");
	                    break;
	            }

	            // Chiedi se l'utente vuole aggiungere un altro filtro
	            System.out.println("Vuoi aggiungere un altro filtro? (s/n)");
	            String risposta = scanner.nextLine();
	            if (risposta.equalsIgnoreCase("n")) {
	                continuaRicerca = false; // Termina la ricerca
	            }
	        }

	     // Esegui il filtraggio delle ricette con tutti i filtri applicati
	        System.out.println("\nRisultati dopo i filtri applicati:");
	        boolean found = false;
	        for (Ricetta ricetta : ricette) {
	            boolean passaFiltri = true;

	            // Applica tutti i filtri
	            for (FiltraggioStrategy filtro : filtri) {
	                if (!filtro.filtra(ricetta)) {
	                    passaFiltri = false;
	                    break;
	                }
	            }

	            // Se la ricetta passa tutti i filtri, la visualizziamo
	            if (passaFiltri) {
	                System.out.println(ricetta);
	                found = true;

	                // Domanda se l'utente vuole salvare la ricetta
	                System.out.println("\nVuoi salvare la ricetta? (s/n)");
	                String risposta4 = scanner.nextLine();
	                if (risposta4.equalsIgnoreCase("s")) {
	                    // Verifica se la ricetta è già stata salvata
	                    List<Ricetta> ricetteSalvate = careTaker.getRicetteByUser(nomeUtente);
	                    boolean ricettaGiaSalvata = false;

	                    // Controlla se la ricetta è già nelle ricette salvate dell'utente
	                    for (Ricetta salvata : ricetteSalvate) {
	                        if (salvata.getNome().equals(ricetta.getNome())) {
	                            ricettaGiaSalvata = true;
	                            break;
	                        }
	                    }

	                    if (ricettaGiaSalvata) {
	                        System.out.println("La ricetta " + ricetta.getNome() + " è già stata salvata.");
	                    } else {
	                        // Salva la ricetta nel Memento
	                        Memento memento = new Memento(ricetta);
	                        // Aggiungi la ricetta al CareTaker associandola all'utente
	                        careTaker.addMemento(memento, nomeUtente); // Usa il nome utente passato come parametro
	                        
	                        System.out.println("Ricetta salvata per l'utente " + nomeUtente + "!");
	                    }
	                }
	            }
	        }
	        if (!found) {
	            System.out.println("NO RECIPE FOUND");
	        }
	        System.out.println("\nTorniamo al menu principale\n");
	        continuaRicerca = false;	
	    }    
	    }
}