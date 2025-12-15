package Strategy;

public class FiltraggioperTempo implements FiltraggioStrategy {
	private int tempo;

    // Costruttore che riceve il tempo da filtrare
    public FiltraggioperTempo(int tempo) {
        this.tempo = tempo;
    }

    // Metodo che applica il filtro sulla ricetta in base al tempo di preparazione
    @Override
    public boolean filtra(Ricetta ricetta) {
        return ricetta.getTempi_di_preparazione() <= tempo;
    }
}