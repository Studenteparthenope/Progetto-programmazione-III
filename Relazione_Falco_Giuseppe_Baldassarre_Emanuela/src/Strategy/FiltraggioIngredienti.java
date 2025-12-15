package Strategy;

public class FiltraggioIngredienti implements FiltraggioStrategy {
    private String ing1;
    private String ing2;
    private String ing3;
    private String ing4;

    // Costruttore che riceve gli ingredienti
    public FiltraggioIngredienti(String ing1, String ing2, String ing3, String ing4) {
        super();
        this.ing1 = ing1;
        this.ing2 = ing2;
        this.ing3 = ing3;
        this.ing4 = ing4;
    }

    // Metodo che applica il filtro in base agli ingredienti
    @Override
    public boolean filtra(Ricetta ricetta) {
        return ricetta.getIngrediente1().equalsIgnoreCase(ing1) || 
               ricetta.getIngrediente2().equalsIgnoreCase(ing2) ||
               ricetta.getIngrediente3().equalsIgnoreCase(ing3) ||
               ricetta.getIngrediente4().equalsIgnoreCase(ing4);
    }
}
/*Unico metodo filtra: Ho combinato le diverse condizioni in un unico metodo, in modo che il filtro possa verificare se uno 
 * degli ingredienti della ricetta corrisponde ad almeno uno degli ingredienti forniti nel costruttore.
 */

/*Operatore logico ||: Ho usato l'operatore || (OR) per verificare se uno degli ingredienti della ricetta corrisponde 
 * a uno degli ingredienti passati nel costruttore. Puoi usare && (AND) se desideri che tutti gli ingredienti corrispondano.*/