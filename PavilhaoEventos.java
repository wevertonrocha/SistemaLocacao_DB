package sistemalocacao;

public class PavilhaoEventos extends Espaco {

    private double areaEvento;

    public PavilhaoEventos(String endereco, double valor, Locador locador, double areaEvento) {
        super(endereco, valor, locador);
        this.areaEvento = areaEvento;
    }

    public PavilhaoEventos() {
    }

    public double getAreaEvento() {
        return areaEvento;
    }

    public void setAreaEvento(double areaEvento) {
        this.areaEvento = areaEvento;
    }

    @Override
    public String getTipo() {
        return "PavilhaoEventos";
    }
}
