package sistemalocacao;

import java.time.LocalDate;

public class Locacao {

    private int id;
    private Cliente cliente;
    private Espaco espaco;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public Locacao(Cliente cliente, Espaco espaco) {
        this.cliente = cliente;
        this.espaco = espaco;
        this.dataInicio = LocalDate.now();
        this.dataFim = null; // Uma nova locação não tem data de fim
        this.espaco.ocupar();
    }

    // Construtor vazio para uso pelo DAO
    public Locacao() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Espaco getEspaco() {
        return espaco;
    }

    public void setEspaco(Espaco espaco) {
        this.espaco = espaco;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    @Override
    public String toString() {
        String status = (dataFim == null)
                ? "Status: Ativa"
                : "Status: Encerrada em " + dataFim;

        return "Locação (ID: " + id + ")"
                + "\n  - Cliente: " + cliente.getNome() + " (CPF: " + cliente.getCpf() + ")"
                + "\n  - Espaço: " + espaco.getTipo() + " em " + espaco.getEndereco()
                + "\n  - Data de Início: " + dataInicio
                + "\n  - " + status;
    }
}
