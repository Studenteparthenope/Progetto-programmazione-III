package Factory_method;

//Interfaccia per la strategia di autenticazione
public interface AutenticazioneStrategy {
 boolean authenticate(String username, String password);
}