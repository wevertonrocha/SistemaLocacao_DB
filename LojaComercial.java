package sistemalocacao;

public class LojaComercial extends Espaco {

    private double areaTotal;

    public LojaComercial(String endereco, double valor, Locador locador, double areaTotal) {
        super(endereco, valor, locador);
        this.areaTotal = areaTotal;
    }

    public LojaComercial() {
    }

    public double getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(double areaTotal) {
        this.areaTotal = areaTotal;
    }

    @Override
    public String getTipo() {
        return "LojaComercial";
    }
}
