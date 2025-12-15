package Strategy;

public class FiltraggioPerRegimeAlimentare implements FiltraggioStrategy {
    private String regime;

    // Costruttore che riceve il regime alimentare
    public FiltraggioPerRegimeAlimentare(String regime) {
        this.regime = regime;
    }

    // Metodo che applica il filtro in base al regime alimentare
    @Override
    public boolean filtra(Ricetta ricetta) {
        return ricetta.getRegime_alimentare().equalsIgnoreCase(regime);
    }
}
