package Strategy;
// aggiungi import per l'osservatore

import java.util.ArrayList;

import java.util.List;
import java.io.Serializable;

public class Ricetta implements Serializable {
    private static final long serialVersionUID = 1L;
    private String Nome;
    private int tempi_di_preparazione;
    private String tipo;
    private String difficolta;
    private String regime_alimentare;
    private String ingrediente1;
    private String ingrediente2;
    private String ingrediente3;
    private String ingrediente4;

  

    

    // Costruttore e getters
    public Ricetta(String Nome, int tempi_di_preparazione, String tipo, String difficolta, String regime_alimentare, String ingrediente1, String ingrediente2, String ingrediente3, String ingrediente4) {
        this.Nome = Nome;
        this.tempi_di_preparazione = tempi_di_preparazione;
        this.tipo = tipo;
        this.difficolta = difficolta;
        this.regime_alimentare = regime_alimentare;
        this.ingrediente1 = ingrediente1;
        this.ingrediente2 = ingrediente2;
        this.ingrediente3 = ingrediente3;
        this.ingrediente4 = ingrediente4;
    }

    public String getNome() {
        return Nome;
    }

    public int getTempi_di_preparazione() {
        return tempi_di_preparazione;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDifficolta() {
        return difficolta;
    }

    public String getRegime_alimentare() {
        return regime_alimentare;
    }

    public String getIngrediente1() {
        return ingrediente1;
    }

    public String getIngrediente2() {
        return ingrediente2;
    }

    public String getIngrediente3() {
        return ingrediente3;
    }

    public String getIngrediente4() {
        return ingrediente4;
    }



    @Override
    public String toString() {
        return "Ricetta{" +
                "Nome della ricetta='" + Nome + '\'' +
                ", tempi_di_preparazione=" + tempi_di_preparazione +
                ", tipo='" + tipo + '\'' +
                ", difficolta='" + difficolta + '\'' +
                ", regime_alimentare='" + regime_alimentare + '\'' +
                ", Primo ingrediente='" + ingrediente1 + '\'' +
                ", Secondo ingrediente='" + ingrediente2 + '\'' +
                ", Terzo ingrediente='" + ingrediente3 + '\'' +
                ", Quarto ingrediente='" + ingrediente4 + '\'' +
                '}';
    }

    // Metodo per calcolare la media delle valutazioni
   /* public double calcolaMediaValutazioni() {
        if (valutazioni.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for (int valutazione : valutazioni) {
            sum += valutazione;
        }
        return sum / valutazioni.size();
    }*/
} 