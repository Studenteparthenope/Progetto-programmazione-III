package Strategy;

public class FiltraggioNome implements FiltraggioStrategy {
private String nome;

public FiltraggioNome(String nome) {
	super();
	this.nome = nome;
}
// Metodo che applica il filtro sulla ricetta in base al nome
@Override
public boolean filtra(Ricetta ricetta) {
	return ricetta.getNome().equalsIgnoreCase(nome);
}
}