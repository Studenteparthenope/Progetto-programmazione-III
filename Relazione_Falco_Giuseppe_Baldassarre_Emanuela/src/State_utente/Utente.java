package State_utente;

public class Utente {
    private StatoUtente stato;
    private String username;
    private String password;

    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
        this.stato = new NonAutenticato();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public StatoUtente getStato() {
        return stato;
    }

    public void setStato(StatoUtente stato) {
        this.stato = stato;
    }

    public void login() {
        stato.login(this);
    }

    public void logout() {
        stato.logout(this);
    }

    public void accediAreaPrivata() {
        stato.accediAreaPrivata(this);
    }
}
