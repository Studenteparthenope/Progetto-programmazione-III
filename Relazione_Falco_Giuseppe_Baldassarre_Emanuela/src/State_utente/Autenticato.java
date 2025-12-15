package State_utente;

public class Autenticato implements StatoUtente {

    @Override
    public void login(Utente utente) {
        System.out.println("L'utente è già autenticato.");
    }

    @Override
    public void logout(Utente utente) {
        System.out.println("Utente disconnesso con successo.");
        utente.setStato(new NonAutenticato());
    }

    @Override
    public void accediAreaPrivata(Utente utente) {
        System.out.println("Accesso all'area privata consentito.");
    }
}

