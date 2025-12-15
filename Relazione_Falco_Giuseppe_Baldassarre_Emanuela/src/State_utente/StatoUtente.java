package State_utente;

public interface StatoUtente {
    void login(Utente utente);
    void logout(Utente utente);
    void accediAreaPrivata(Utente utente);
}