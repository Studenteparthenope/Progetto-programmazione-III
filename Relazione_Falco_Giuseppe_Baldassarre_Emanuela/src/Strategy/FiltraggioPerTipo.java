package Strategy;

public class FiltraggioPerTipo implements FiltraggioStrategy {
	private String tipo;

    public FiltraggioPerTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean filtra(Ricetta ricetta) {
        return ricetta.getTipo().equalsIgnoreCase(tipo);
    }
}
