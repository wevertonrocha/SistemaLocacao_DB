package sistemalocacao;

public class Cliente {

    private int id;
    private String nome;
    private String email;
    private int idade;
    private String endereco;
    private long cpf;
    private long telefone;

    public Cliente(String nome, String email, int idade, String endereco, long cpf, long telefone) {
        this.setNome(nome);
        this.setEmail(email);
        this.setIdade(idade);
        this.setEndereco(endereco);
        this.setCpf(cpf);
        this.setTelefone(telefone);
    }

    public Cliente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio.");
        }
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Formato de e-mail inválido.");
        }
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade < 18 || idade > 103) {
            throw new IllegalArgumentException("A idade deve ser entre 18 e 103 anos.");
        }
        this.idade = idade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("O endereço não pode ser vazio.");
        }
        this.endereco = endereco;
    }

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        if (!String.valueOf(cpf).matches("\\d{11}")) {
            throw new IllegalArgumentException("O CPF deve conter exatamente 11 dígitos numéricos.");
        }
        this.cpf = cpf;
    }

    public long getTelefone() {
        return telefone;
    }

    public void setTelefone(long telefone) {
        if (!String.valueOf(telefone).matches("\\d{10,11}")) {
            throw new IllegalArgumentException("O telefone deve conter 10 ou 11 dígitos numéricos.");
        }
        this.telefone = telefone;
    }
}
