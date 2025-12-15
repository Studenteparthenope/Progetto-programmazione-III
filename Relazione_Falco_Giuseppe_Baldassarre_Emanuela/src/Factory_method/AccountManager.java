package Factory_method;

import State_utente.Utente;  // Assicurati che Utente sia importato

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class AccountManager {
    private List<Utente> accounts;  // Usa Utente 
    private static final String FILE_NAME = "DbUtenti.txt";  // Nome del file di dati

    // Costruttore per inizializzare la lista degli account
    public AccountManager() {
        accounts = new ArrayList<>();
        loadAccountsFromFile();  // Carica gli account dal file all'avvio
    }

    // Aggiungi un nuovo account alla lista e salvalo nel file
    public void addAccount(String username, String password) {
        Utente newUser = new Utente(username, password);
        accounts.add(newUser);  // Aggiungi all'elenco in memoria
        saveAccountsToFile();   // Salva l'account nel file
    }

    // Verifica se le credenziali corrispondono a uno degli account
    public Utente authenticate(String username, String password) {
        for (Utente account : accounts) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return account;  // Restituisce l'utente se la verifica ha successo
            }
        }
        return null;  // Se non trovato, restituisce null
    }

    

    // Carica gli account dal file DbUtenti.txt
    private void loadAccountsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");  // Assumiamo che username e password siano separati da una virgola
                if (userData.length == 2) {
                    accounts.add(new Utente(userData[0], userData[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file: " + e.getMessage());
        }
    }

    // Salva gli account nel file DbUtenti.txt
    private void saveAccountsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Utente account : accounts) {
                bw.write(account.getUsername() + "," + account.getPassword());  // Scrivi username e password nel file
                bw.newLine();  // Aggiungi una nuova linea per ogni account
            }
        } catch (IOException e) {
            System.out.println("Errore nella scrittura del file: " + e.getMessage());
        }
    }
}
