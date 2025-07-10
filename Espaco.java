package sistemalocacao;

public abstract class Espaco {

    private int id;
    protected String endereco;
    protected double valor;
    protected boolean disponivel;
    protected Locador locador;

    public Espaco(String endereco, double valor, Locador locador) {
        this.setEndereco(endereco);
        this.setValor(valor);
        this.setLocador(locador);
        this.setDisponivel(true);
    }

    public Espaco() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void ocupar() {
        this.setDisponivel(false);
    }

    public void liberar() {
        this.setDisponivel(true);
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Locador getLocador() {
        return locador;
    }

    public void setLocador(Locador locador) {
        this.locador = locador;
    }

    public abstract String getTipo();

    @Override
    public String toString() {
        String nomeLocador = (getLocador() != null) ? getLocador().getNome() : "N/A";
        return getTipo() + " (ID: " + id + ")"
                + "\n  - Endereço: " + getEndereco()
                + "\n  - Valor: R$" + getValor()
                + "\n  - Status: " + (isDisponivel() ? "Disponível" : "Indisponível")
                + "\n  - Locador: " + nomeLocador;
    }
}
