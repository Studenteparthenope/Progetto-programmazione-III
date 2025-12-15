package Factory_method;
import State_utente.Utente;  // Importa Utente dal package State_utente
// Strategia di autenticazione tramite username e password
public class PasswordAutenticazioneStrategy implements AutenticazioneStrategy {
    private AccountManager accountManager;

    public PasswordAutenticazioneStrategy(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public boolean authenticate(String username, String password) {
        Utente account = accountManager.authenticate(username, password);  // Usa Utente al posto di Autentichacion
        return account != null;  // Se account non è null, l'autenticazione è riuscita
    }
}