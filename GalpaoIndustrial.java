package sistemalocacao;

public class GalpaoIndustrial extends Espaco {

    private double areaTotal;
    private double altura;

    public GalpaoIndustrial(String endereco, double valor, Locador locador, double areaTotal, double altura) {
        super(endereco, valor, locador);
        this.areaTotal = areaTotal;
        this.altura = altura;
    }

    public GalpaoIndustrial() {
    }

    public double getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(double areaTotal) {
        this.areaTotal = areaTotal;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    @Override
    public String getTipo() {
        return "GalpaoIndustrial";
    }
}
