package Strategy;

public class FiltraggioPerDifficolta implements FiltraggioStrategy {
	 private String difficolta;

	    public FiltraggioPerDifficolta(String difficolta) {
	        this.difficolta = difficolta;
	    }

	    @Override
	    public boolean filtra(Ricetta ricetta) {
	        return ricetta.getDifficolta().equalsIgnoreCase(difficolta);
    }
}
