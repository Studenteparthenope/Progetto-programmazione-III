package Factory_method;

import javax.swing.*;

import javax.swing.event.*;
import java.awt.event.*;

import Memento_pattern.CareTaker;
import Memento_pattern.Memento;
import Strategy.Ricetta;
import Strategy.RicetteManager;
import State_utente.Utente;
import State_utente.Autenticato;
import State_utente.NonAutenticato;
import java.util.*;
import java.util.Scanner;
import java.util.InputMismatchException;

public class AutenticazioneFactory {

    public static AutenticazioneStrategy getAutenticazioneMethod(String tipo, AccountManager accountManager) {
        if ("password".equals(tipo)) {
            return new PasswordAutenticazioneStrategy(accountManager);
        }
        return null;
    }
  
    public static void main(String[] args) {
        AccountManager accountManager = new AccountManager();

        // Crea un CareTaker senza passare la ricetta
        CareTaker careTaker = new CareTaker();  // Usa il costruttore di default senza passare una Ricetta

        // Crea il LoginManager passando l'accountManager e il careTaker
        LoginManager loginManager = new LoginManager(accountManager, careTaker);
        loginManager.start();  // Avvia il flusso di login
    }
    }


