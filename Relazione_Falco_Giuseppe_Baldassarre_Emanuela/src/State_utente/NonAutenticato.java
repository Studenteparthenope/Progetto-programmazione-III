package State_utente;

public class NonAutenticato implements StatoUtente {

    @Override
    public void login(Utente utente) {
        System.out.println("Utente autenticato con successo.");
        utente.setStato(new Autenticato()); // Cambia lo stato dell'utente a "Autenticato"
    }

    @Override
    public void logout(Utente utente) {
        System.out.println("L'utente è già disconnesso.");
    }

    @Override
    public void accediAreaPrivata(Utente utente) {
        System.out.println("Devi essere autenticato per accedere all'area privata.");
    }
}
