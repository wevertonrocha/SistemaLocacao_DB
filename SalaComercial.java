package sistemalocacao;

public class SalaComercial extends Espaco {

    private String sistemaSeguranca;
    private int numeroDeJanelas;
    private int numeroDePortas;

    public SalaComercial(String endereco, double valor, Locador locador, String sistemaSeguranca, int numeroDeJanelas, int numeroDePortas) {
        super(endereco, valor, locador);
        this.sistemaSeguranca = sistemaSeguranca;
        this.numeroDeJanelas = numeroDeJanelas;
        this.numeroDePortas = numeroDePortas;
    }

    public SalaComercial() {
    }

    public String getSistemaSeguranca() {
        return sistemaSeguranca;
    }

    public void setSistemaSeguranca(String sistemaSeguranca) {
        this.sistemaSeguranca = sistemaSeguranca;
    }

    public int getNumeroDeJanelas() {
        return numeroDeJanelas;
    }

    public void setNumeroDeJanelas(int numeroDeJanelas) {
        this.numeroDeJanelas = numeroDeJanelas;
    }

    public int getNumeroDePortas() {
        return numeroDePortas;
    }

    public void setNumeroDePortas(int numeroDePortas) {
        this.numeroDePortas = numeroDePortas;
    }

    @Override
    public String getTipo() {
        return "SalaComercial";
    }
}
