package GUI;

import Factory_method.AccountManager;
import Factory_method.LoginManager;
import Memento_pattern.CareTaker;
import Memento_pattern.Memento;
import State_utente.Utente;
import Strategy.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private AccountManager accountManager;
    private LoginManager loginManager;
    private CareTaker careTaker;
    private Utente currentUser;

    public MainWindow() {
        // Impostazioni di base della finestra
        setTitle("Sistema di Autenticazione e Ricette");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        accountManager = new AccountManager();
        loginManager = new LoginManager(accountManager, null);
        careTaker = new CareTaker(); // Inizializza CareTaker

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Aggiungi i pannelli
        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createMenuPanel(), "menu");

        add(mainPanel);
    }

    // Pannello di login
    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 1));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Registrati");

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(registerButton);

        // Aggiungi listener per il login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                currentUser = accountManager.authenticate(username, password);

                if (currentUser != null) {
                    // Se l'autenticazione è corretta, passa al menu principale
                    JOptionPane.showMessageDialog(MainWindow.this, "Login effettuato con successo!");
                    cardLayout.show(mainPanel, "menu");
                } else {
                    JOptionPane.showMessageDialog(MainWindow.this, "Credenziali errate.");
                }
            }
        });

        // Aggiungi listener per la registrazione
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                accountManager.addAccount(username, password);
                JOptionPane.showMessageDialog(MainWindow.this, "Registrazione completata! Ora puoi effettuare il login.");
            }
        });

        return loginPanel;
    }

    // Pannello del menu principale (dopo il login)
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1));

        JButton logoutButton = new JButton("Logout");
        JButton saveRecipeButton = new JButton("Vedi ricette salvate");
        JButton searchRecipeButton = new JButton("Cerca ricette");

        menuPanel.add(logoutButton);
        menuPanel.add(saveRecipeButton);
        menuPanel.add(searchRecipeButton);

        // Aggiungi listener per il logout
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentUser = null;
                JOptionPane.showMessageDialog(MainWindow.this, "Logout effettuato!");
                cardLayout.show(mainPanel, "login");
            }
        });

        // Aggiungi listener per "Vedi ricette salvate"
        saveRecipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser != null) {
                    // Recupera le ricette salvate per l'utente
                    List<Ricetta> savedRecipes = careTaker.getRicetteByUser(currentUser.getUsername());

                    if (savedRecipes.isEmpty()) {
                        JOptionPane.showMessageDialog(MainWindow.this, "Nessuna ricetta salvata.");
                    } else {
                        StringBuilder recipesList = new StringBuilder();
                        for (Ricetta recipe : savedRecipes) {
                            recipesList.append(recipe.toString()).append("\n");
                        }
                        JOptionPane.showMessageDialog(MainWindow.this, recipesList.toString(), "Le tue ricette salvate", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(MainWindow.this, "Devi essere loggato per vedere le ricette salvate.");
                }
            }
        });

        // Aggiungi listener per la ricerca delle ricette
        searchRecipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFilterMenu();
            }
        });

        return menuPanel;
    }

    // Funzione per visualizzare il menu dei filtri
    private void showFilterMenu() {
        // Menu a tendina con i tipi di filtro disponibili
        String[] filterOptions = {
            "Filtra per Tipo", 
            "Filtra per Nome",  // Aggiungi il filtro per nome
            "Filtra per Tempo", 
            "Filtra per Difficoltà", 
            "Filtra per Categoria", 
            "Filtra per Ingredienti"
        };

        JComboBox<String> filterComboBox = new JComboBox<>(filterOptions);
        int option = JOptionPane.showConfirmDialog(this, filterComboBox, "Scegli un tipo di filtro", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String selectedFilter = (String) filterComboBox.getSelectedItem();
            applyFilter(selectedFilter);
        }
    }

    // Funzione per applicare il filtro selezionato
    private void applyFilter(String selectedFilter) {
        List<FiltraggioStrategy> filtri = new ArrayList<>();

        switch (selectedFilter) {
            case "Filtra per Tipo":
                String[] types = {"Primo", "Secondo", "Antipasto", "Dolce"};
                String type = (String) JOptionPane.showInputDialog(this, "Seleziona il tipo di ricetta", "Filtra per Tipo", JOptionPane.QUESTION_MESSAGE, null, types, types[0]);
                filtri.add(new FiltraggioPerTipo(type));
                break;

            case "Filtra per Nome":
                String name = JOptionPane.showInputDialog(this, "Inserisci il nome della ricetta:");
                filtri.add(new FiltraggioNome(name));  // Usa FiltraggioNome
                break;

            case "Filtra per Tempo":
                String timeInput = JOptionPane.showInputDialog(this, "Inserisci il tempo massimo di preparazione (in minuti):");
                int time = Integer.parseInt(timeInput);
                filtri.add(new FiltraggioperTempo(time));
                break;

            case "Filtra per Difficoltà":
                String difficulty = JOptionPane.showInputDialog(this, "Inserisci la difficoltà (facile, media, difficile):");
                filtri.add(new FiltraggioPerDifficolta(difficulty));
                break;

            case "Filtra per Categoria":
                String category = JOptionPane.showInputDialog(this, "Inserisci la categoria (Vegetariano, Vegano, Carne, etc.):");
                filtri.add(new FiltraggioPerRegimeAlimentare(category));
                break;

            case "Filtra per Ingredienti":
                String ingredientsInput = JOptionPane.showInputDialog(this, "Inserisci fino a 4 ingredienti separati da una virgola:");
                String[] ingredients = ingredientsInput.split(",");
                if (ingredients.length == 4) {
                    filtri.add(new FiltraggioIngredienti(ingredients[0].trim(), ingredients[1].trim(), ingredients[2].trim(), ingredients[3].trim()));
                } else {
                    JOptionPane.showMessageDialog(this, "Devi inserire esattamente 4 ingredienti separati da una virgola.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }

        // Carica e filtra le ricette
        List<Ricetta> ricette = loadRecipesFromFile("ricette.txt");
        StringBuilder filteredRecipes = new StringBuilder();
        boolean found = false;

        for (Ricetta ricetta : ricette) {
            boolean isValid = true;
            for (FiltraggioStrategy filtro : filtri) {
                if (!filtro.filtra(ricetta)) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                filteredRecipes.append(ricetta.toString()).append("\n");
                found = true;
            }
        }

        if (found) {
            // Mostra le ricette trovate
            int option = JOptionPane.showConfirmDialog(this, filteredRecipes.toString(), "Ricette Trovate", JOptionPane.YES_NO_OPTION);
            
            // Se l'utente vuole salvare la ricetta
            if (option == JOptionPane.YES_OPTION) {
                // Chiedi quale ricetta salvare (puoi personalizzare questa parte per selezionare la ricetta giusta)
                String recipeToSave = JOptionPane.showInputDialog(this, "Inserisci il nome della ricetta da salvare:");

                // Verifica se la ricetta è già stata salvata per l'utente
                List<Ricetta> savedRecipes = careTaker.getRicetteByUser(currentUser.getUsername());
                boolean alreadySaved = false;
                for (Ricetta savedRecipe : savedRecipes) {
                    if (savedRecipe.getNome().equalsIgnoreCase(recipeToSave)) {
                        alreadySaved = true;
                        break;
                    }
                }

                if (alreadySaved) {
                    JOptionPane.showMessageDialog(this, "Questa ricetta è già stata salvata!", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    for (Ricetta ricetta : ricette) {
                        if (ricetta.getNome().equalsIgnoreCase(recipeToSave)) {
                            Memento memento = new Memento(ricetta);
                            careTaker.addMemento(memento, currentUser.getUsername());
                            JOptionPane.showMessageDialog(this, "Ricetta salvata con successo!");
                            break;
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nessuna ricetta trovata.", "Ricette Trovate", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Funzione per caricare le ricette dal file
    private List<Ricetta> loadRecipesFromFile(String filePath) {
        List<Ricetta> ricette = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 9) {
                    Ricetta ricetta = new Ricetta(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3], parts[4],
                            parts[5], parts[6], parts[7], parts[8]);
                    ricette.add(ricetta);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ricette;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}
