package sistemalocacao;

public class Estudio extends Espaco {

    private double areaEstudio;

    public Estudio(String endereco, double valor, Locador locador, double areaEstudio) {
        super(endereco, valor, locador);
        this.areaEstudio = areaEstudio;
    }

    public Estudio() {
    }

    public double getAreaEstudio() {
        return areaEstudio;
    }

    public void setAreaEstudio(double areaEstudio) {
        this.areaEstudio = areaEstudio;
    }

    @Override
    public String getTipo() {
        return "Estudio";
    }
}
