package Factory_method;

import Memento_pattern.*;
import Strategy.*;
import State_utente.Utente;
import java.util.List;
import java.util.Scanner;

public class LoginManager {

    private AccountManager accountManager;
    private CareTaker careTaker;  // Aggiungi CareTaker per gestire le ricette

    public LoginManager(AccountManager accountManager, CareTaker careTaker) {
        this.accountManager = accountManager;
        this.careTaker = careTaker;  // Inizializza CareTaker
    }
    public void start() {
        Scanner scanner = new Scanner(System.in);
        Utente utente = null;

        while (true) {
            if (utente == null) {
                System.out.println("Benvenuto nel programma! Scegli un'opzione:");
                System.out.println("1. Effettua il login");
                System.out.println("2. Iscriviti");
                System.out.println("3. Esci");

                int scelta = scanner.nextInt();
                scanner.nextLine();

                switch (scelta) {
                    case 1:
                        utente = handleLogin(scanner);
                        break;
                    case 2:
                        handleRegistration(scanner);
                        break;
                    case 3:
                        System.out.println("Uscita dal programma...");
                        scanner.close();
                        return;
                }
            } else {
                boolean continuaInAreaPrivata = true;
                while (continuaInAreaPrivata) {
                    System.out.println("Ora puoi lavorare nell'area privata.");
                    System.out.println("Scegli un'opzione:");
                    System.out.println("1. Logout");
                    System.out.println("2. Vedi ricette salvate");
                    System.out.println("3. Cerca ricette");

                    int sceltaAreaPrivata = scanner.nextInt();
                    scanner.nextLine();

                    switch (sceltaAreaPrivata) {
                        case 1:
                            System.out.println("Disconnessione in corso...");
                            utente = null;
                            System.out.println("Logout avvenuto con successo.");
                            continuaInAreaPrivata = false;
                            break;
                        case 2:
                            showSavedRecipes(utente);
                            break;
                        case 3:
                        
                            // Avvia la ricerca di ricette
                            System.out.println("Benvenuto nel sistema di filtraggio delle ricette!");
                            if (utente != null) {
                                String nome_utente = utente.getUsername();  // Recupera il nome dell'utente
                                RicetteManager.Main.main(new String[]{nome_utente});  // Passa il nome utente a RicetteManager
                            }
                            break;
                        default:
                            System.out.println("Scelta non valida. Riprova.");
                    }
                }
            }
        }
    }

    private Utente handleLogin(Scanner scanner) {
        System.out.print("Inserisci username: ");
        String username = scanner.nextLine();
        System.out.print("Inserisci password: ");
        String password = scanner.nextLine();

        // Autenticazione
        Utente utente = accountManager.authenticate(username, password);
        if (utente != null) {
            System.out.println("Accesso riuscito!");
            return utente;
        } else {
            System.out.println("Credenziali errate.");
            return null;
        }
    }

    private void handleRegistration(Scanner scanner) {
        System.out.print("Inserisci nuovo username: ");
        String newUsername = scanner.nextLine();
        System.out.print("Inserisci nuova password: ");
        String newPassword = scanner.nextLine();
        accountManager.addAccount(newUsername, newPassword);
        System.out.println("Nuovo utente aggiunto!");
    }

    private void showSavedRecipes(Utente utente) {
        List<Ricetta> ricetteSalvate = careTaker.getRicetteByUser(utente.getUsername());
        if (ricetteSalvate.isEmpty()) {
            System.out.println("Nessuna ricetta salvata.");
        } else {
            for (Ricetta ricetta : ricetteSalvate) {
                System.out.println(ricetta);
            }
        }
    }

    private void searchRecipes(Utente utente) {
        // Logica per cercare ricette
        System.out.println("Cerca ricette...");
    }

    public static void main(String[] args) {
        AccountManager accountManager = new AccountManager();
        CareTaker careTaker = new CareTaker();  // Crea un CareTaker per gestire le ricette
        LoginManager loginManager = new LoginManager(accountManager, careTaker);
        loginManager.start();  // Avvia il flusso dell'applicazione
    }
}
