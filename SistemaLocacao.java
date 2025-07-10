package sistemalocacao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SistemaLocacao {

    private final Scanner entrada;
    // DAOs para interação com o banco de dados
    private final ClienteDAO clienteDAO;
    private final LocadorDAO locadorDAO;
    private final EspacoDAO espacoDAO;
    private final LocacaoDAO locacaoDAO;

    public SistemaLocacao() {
        this.entrada = new Scanner(System.in);
        // Inicializa os DAOs
        this.clienteDAO = new ClienteDAO();
        this.locadorDAO = new LocadorDAO();
        this.espacoDAO = new EspacoDAO();
        this.locacaoDAO = new LocacaoDAO();
    }

    private static boolean verificarConexao() {
        try (var conn = ConexaoBD.getConexao()) {
            // A instrução try-with-resources garante que a conexão seja fechada.
            // Se nenhuma exceção for lançada aqui, a conexão foi bem-sucedida.
            return true;
        } catch (SQLException e) {
            System.err.println("----------------------------------------------------");
            System.err.println("FALHA NA CONEXÃO COM O BANCO DE DADOS");
            System.err.println("----------------------------------------------------");
            System.err.println("Erro: " + e.getMessage());
            System.err.println("Por favor, verifique os seguintes pontos:");
            System.err.println("1. O serviço do MySQL (ou outro banco) está ativo?");
            System.err.println("2. As informações no arquivo 'ConexaoBD.java' (URL, usuário, senha) estão corretas?");
            return false;
        }
    }

    public static void main(String[] args) {
        //Verifica a conexão antes de iniciar o sistema.
        if (!verificarConexao()) {
            System.err.println("\nO sistema será encerrado devido à falha de conexão.");
            return; // Encerra a aplicação
        }

        System.out.println("Conexão com o banco de dados estabelecida com sucesso. Iniciando o sistema...");
        SistemaLocacao sistema = new SistemaLocacao();
        try {
            sistema.executar();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro fatal durante a execução do sistema. Causa: " + e.getMessage());
        }
    }

    public void executar() {
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerInt("Escolha uma opção: ");
            switch (opcao) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuLocadores();
                    break;
                case 3:
                    menuEspacos();
                    break;
                case 4:
                    menuLocacoes();
                    break;
                case 0:
                    System.out.println("Saindo do sistema. Até logo!");
                    break;
                default:
                    System.err.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
        entrada.close();
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n--- SISTEMA DE LOCAÇÃO DE ESPAÇOS ---");
        System.out.println("1. Gerenciar Clientes");
        System.out.println("2. Gerenciar Locadores");
        System.out.println("3. Gerenciar Espaços");
        System.out.println("4. Gerenciar Locações");
        System.out.println("0. Sair");
        System.out.println("------------------------------------");
    }

    // --- GERENCIAMENTO DE MENUS ---
    private void menuClientes() {
        int opcao;
        do {
            System.out.println("\n--- Menu Clientes ---");
            System.out.println("1. Cadastrar Novo Cliente");
            System.out.println("2. Listar Todos os Clientes");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Excluir Cliente");
            System.out.println("0. Voltar ao Menu Principal");
            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    atualizarCliente();
                    break;
                case 4:
                    excluirCliente();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void menuLocadores() {
        int opcao;
        do {
            System.out.println("\n--- Menu Locadores ---");
            System.out.println("1. Cadastrar Novo Locador");
            System.out.println("2. Listar Todos os Locadores");
            System.out.println("3. Atualizar Locador");
            System.out.println("4. Excluir Locador");
            System.out.println("0. Voltar ao Menu Principal");
            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarLocador();
                    break;
                case 2:
                    listarLocadores();
                    break;
                case 3:
                    atualizarLocador();
                    break;
                case 4:
                    excluirLocador();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void menuEspacos() {
        int opcao;
        do {
            System.out.println("\n--- Menu Espaços ---");
            System.out.println("1. Cadastrar Novo Espaço");
            System.out.println("2. Listar Todos os Espaços");
            System.out.println("0. Voltar ao Menu Principal");
            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarEspaco();
                    break;
                case 2:
                    listarEspacos();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void menuLocacoes() {
        int opcao;
        do {
            System.out.println("\n--- Menu Locações ---");
            System.out.println("1. Realizar Nova Locação");
            System.out.println("2. Listar Locações Ativas");
            System.out.println("3. Encerrar Locação");
            System.out.println("4. Listar Locações Encerradas");
            System.out.println("0. Voltar ao Menu Principal");
            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    realizarLocacao();
                    break;
                case 2:
                    listarLocacoesAtivas();
                    break;
                case 3:
                    encerrarLocacao();
                    break;
                case 4:
                    listarLocacoesEncerradas();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    // --- FUNCIONALIDADES DO CLIENTE ---
    private void cadastrarCliente() {
        System.out.println("\n--- Cadastro de Novo Cliente ---");
        try {
            String nome = lerStringNaoVazia("Nome: ");
            String email = lerEmail("Email: ");
            int idade = lerIdade("Idade: ");
            long telefone = lerTelefone("Telefone (10 ou 11 dígitos): ");
            long cpf = lerCpf("CPF (11 dígitos): ");
            String endereco = lerStringNaoVazia("Endereço: ");

            Cliente novoCliente = new Cliente(nome, email, idade, endereco, cpf, telefone);
            clienteDAO.salvar(novoCliente);
            System.out.println("\n>> Cliente '" + novoCliente.getNome() + "' cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao salvar cliente no banco de dados: " + e.getMessage());
        }
    }

    private void listarClientes() {
        System.out.println("\n--- Clientes Cadastrados ---");
        try {
            List<Cliente> clientes = clienteDAO.listarTodos();
            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado.");
                return;
            }
            for (Cliente c : clientes) {
                System.out.printf("ID: %d | Nome: %s | Email: %s | CPF: %d\n", c.getId(), c.getNome(), c.getEmail(), c.getCpf());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }
    }

    private void atualizarCliente() {
        System.out.println("\n--- Atualizar Cliente ---");
        try {
            listarClientes();
            int id = lerInt("Digite o ID do cliente que deseja atualizar: ");

            Optional<Cliente> clienteOpt = clienteDAO.buscarPorId(id);
            if (!clienteOpt.isPresent()) {
                System.err.println("Cliente com ID " + id + " não encontrado.");
                return;
            }

            Cliente cliente = clienteOpt.get();
            System.out.println("Digite os novos dados para o cliente '" + cliente.getNome() + "':");

            cliente.setNome(lerStringNaoVazia("Novo Nome: "));
            cliente.setEmail(lerEmail("Novo Email: "));
            cliente.setIdade(lerIdade("Nova Idade: "));
            cliente.setEndereco(lerStringNaoVazia("Novo Endereço: "));
            cliente.setTelefone(lerTelefone("Novo Telefone: "));

            clienteDAO.atualizar(cliente);
            System.out.println("\n>> Cliente atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    private void excluirCliente() {
        System.out.println("\n--- Excluir Cliente ---");
        try {
            listarClientes();
            int id = lerInt("Digite o ID do cliente que deseja excluir: ");

            String confirmacao = lerStringNaoVazia("Tem certeza que deseja excluir? (S/N): ");
            if (!confirmacao.equalsIgnoreCase("S")) {
                System.out.println("Operação cancelada.");
                return;
            }

            clienteDAO.excluir(id);
            System.out.println("\n>> Cliente com ID " + id + " excluído com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir cliente. Verifique se ele não está associado a uma locação. Erro: " + e.getMessage());
        }
    }

    // --- FUNCIONALIDADES DO LOCADOR ---
    private void cadastrarLocador() {
        System.out.println("\n--- Cadastro de Novo Locador ---");
        try {
            String nome = lerStringNaoVazia("Nome: ");
            String email = lerEmail("Email: ");
            int idade = lerIdade("Idade: ");
            long telefone = lerTelefone("Telefone (10 ou 11 dígitos): ");
            long cpf = lerCpf("CPF (11 dígitos): ");
            String endereco = lerStringNaoVazia("Endereço: ");
            String senha = lerStringNaoVazia("Senha: ");

            Locador novoLocador = new Locador(nome, email, idade, endereco, cpf, telefone, senha);
            locadorDAO.salvar(novoLocador);
            System.out.println("\n>> Locador '" + novoLocador.getNome() + "' cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Ocorreu um erro durante o cadastro: " + e.getMessage());
        }
    }

    private void listarLocadores() {
        System.out.println("\n--- Locadores Cadastrados ---");
        try {
            List<Locador> locadores = locadorDAO.listar();
            if (locadores.isEmpty()) {
                System.out.println("Nenhum locador cadastrado.");
                return;
            }
            for (Locador l : locadores) {
                System.out.printf("ID: %d | Nome: %s | Email: %s\n", l.getId(), l.getNome(), l.getEmail());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar locadores: " + e.getMessage());
        }
    }

    private void atualizarLocador() {
        System.out.println("\n--- Atualizar Locador ---");
        try {
            listarLocadores();
            int id = lerInt("Digite o ID do locador que deseja atualizar: ");

            Optional<Locador> locadorOpt = locadorDAO.buscarPorId(id);
            if (!locadorOpt.isPresent()) {
                System.err.println("Locador com ID " + id + " não encontrado.");
                return;
            }

            Locador locador = locadorOpt.get();
            System.out.println("Digite os novos dados para o locador '" + locador.getNome() + "':");

            locador.setNome(lerStringNaoVazia("Novo Nome: "));
            locador.setEmail(lerEmail("Novo Email: "));
            locador.setIdade(lerIdade("Nova Idade: "));
            locador.setEndereco(lerStringNaoVazia("Novo Endereço: "));
            locador.setTelefone(lerTelefone("Novo Telefone: "));
            locador.setSenha(lerStringNaoVazia("Nova Senha: "));

            locadorDAO.atualizar(locador);
            System.out.println("\n>> Locador atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar locador: " + e.getMessage());
        }
    }

    private void excluirLocador() {
        System.out.println("\n--- Excluir Locador ---");
        try {
            listarLocadores();
            int id = lerInt("Digite o ID do locador que deseja excluir: ");

            String confirmacao = lerStringNaoVazia("Tem certeza que deseja excluir? (S/N): ");
            if (!confirmacao.equalsIgnoreCase("S")) {
                System.out.println("Operação cancelada.");
                return;
            }

            locadorDAO.excluir(id);
            System.out.println("\n>> Locador com ID " + id + " excluído com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir locador. Verifique se ele não possui espaços cadastrados. Erro: " + e.getMessage());
        }
    }

    // --- FUNCIONALIDADES DE ESPAÇO ---
    private void cadastrarEspaco() {
        System.out.println("\n--- Cadastro de Novo Espaço ---");
        try {
            listarLocadores();
            int locadorId = lerInt("Digite o ID do Locador responsável: ");

            Optional<Locador> locadorOpt = locadorDAO.buscarPorId(locadorId);
            if (!locadorOpt.isPresent()) {
                System.err.println("Locador com ID " + locadorId + " não encontrado.");
                return;
            }

            String endereco = lerStringNaoVazia("Endereço do espaço: ");
            double valor = lerDouble("Valor do aluguel (R$): ");

            System.out.println("Qual o tipo de espaço? (SalaComercial, GalpaoIndustrial, LojaComercial, Estudio, PavilhaoEventos)");
            String tipo = lerStringNaoVazia("Tipo: ");

            Espaco novoEspaco = null;

            switch (tipo) {
                case "SalaComercial":
                    int nroPortas = lerInt("Número de portas: ");
                    int nroJanelas = lerInt("Número de janelas: ");
                    String seguranca = lerStringNaoVazia("Sistema de segurança: ");
                    novoEspaco = new SalaComercial(endereco, valor, locadorOpt.get(), seguranca, nroJanelas, nroPortas);
                    break;
                case "GalpaoIndustrial":
                    double areaGI = lerDouble("Área total (m²): ");
                    double altura = lerDouble("Altura do pé direito (m): ");
                    novoEspaco = new GalpaoIndustrial(endereco, valor, locadorOpt.get(), areaGI, altura);
                    break;
                case "LojaComercial":
                    double areaLC = lerDouble("Área total (m²): ");
                    novoEspaco = new LojaComercial(endereco, valor, locadorOpt.get(), areaLC);
                    break;
                case "Estudio":
                    double areaE = lerDouble("Área do estúdio (m²): ");
                    novoEspaco = new Estudio(endereco, valor, locadorOpt.get(), areaE);
                    break;
                case "PavilhaoEventos":
                    double areaPE = lerDouble("Área do pavilhão (m²): ");
                    novoEspaco = new PavilhaoEventos(endereco, valor, locadorOpt.get(), areaPE);
                    break;
                default:
                    System.err.println("Tipo de espaço desconhecido.");
                    return;
            }

            espacoDAO.salvar(novoEspaco);
            System.out.println("\n>> Espaço cadastrado com sucesso!");

        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao salvar o espaço: " + e.getMessage());
        }
    }

    private void listarEspacos() {
        System.out.println("\n--- Espaços Cadastrados ---");
        try {
            List<Espaco> espacos = espacoDAO.listarTodos();
            if (espacos.isEmpty()) {
                System.out.println("Nenhum espaço cadastrado.");
                return;
            }
            for (Espaco e : espacos) {
                System.out.println(e.toString());
                System.out.println("---------------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar espaços: " + e.getMessage());
        }
    }

    // --- FUNCIONALIDADES DE LOCAÇÃO ---
    private void realizarLocacao() {
        System.out.println("\n--- Realizar Nova Locação ---");
        try {
            System.out.println("Clientes disponíveis:");
            listarClientes();
            int clienteId = lerInt("Digite o ID do Cliente: ");

            System.out.println("\nEspaços disponíveis:");
            List<Espaco> espacosDisponiveis = espacoDAO.listarDisponiveis();

            if (espacosDisponiveis.isEmpty()) {
                System.out.println("Nenhum espaço disponível no momento.");
                return;
            }
            for (Espaco e : espacosDisponiveis) {
                System.out.println(e.toString());
                System.out.println("---------------------------------");
            }
            int espacoId = lerInt("Digite o ID do Espaço a ser alugado: ");

            Optional<Cliente> clienteOpt = clienteDAO.buscarPorId(clienteId);
            if (!clienteOpt.isPresent()) {
                System.err.println("Cliente com ID " + clienteId + " não encontrado.");
                return;
            }

            Optional<Espaco> espacoOpt = espacoDAO.buscarPorId(espacoId);
            if (!espacoOpt.isPresent() || !espacoOpt.get().isDisponivel()) {
                System.err.println("ID de espaço inválido ou espaço indisponível.");
                return;
            }

            Locacao novaLocacao = new Locacao(clienteOpt.get(), espacoOpt.get());
            locacaoDAO.salvar(novaLocacao);

            System.out.println("\n>> Locação realizada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao realizar locação: " + e.getMessage());
        }
    }

    private void listarLocacoesAtivas() {
        System.out.println("\n--- Locações Ativas ---");
        try {
            List<Locacao> locacoes = locacaoDAO.listarAtivas();
            if (locacoes.isEmpty()) {
                System.out.println("Nenhuma locação ativa encontrada.");
                return;
            }
            for (Locacao locacao : locacoes) {
                System.out.println(locacao.toString());
                System.out.println("---------------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar locações: " + e.getMessage());
        }
    }

    private void encerrarLocacao() {
        System.out.println("\n--- Encerrar uma Locação ---");
        listarLocacoesAtivas(); // Mostra as locações que podem ser encerradas

        try {
            int locacaoId = lerInt("Digite o ID da locação que deseja encerrar: ");

            String confirmacao = lerStringNaoVazia("Tem certeza que deseja encerrar esta locação? (S/N): ");
            if (!confirmacao.equalsIgnoreCase("S")) {
                System.out.println("Operação cancelada.");
                return;
            }

            locacaoDAO.encerrar(locacaoId);
            System.out.println("\n>> Locação com ID " + locacaoId + " encerrada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao encerrar locação: " + e.getMessage());
        }
    }

    private void listarLocacoesEncerradas() {
        System.out.println("\n--- Locações Encerradas ---");
        try {
            List<Locacao> locacoes = locacaoDAO.listarEncerradas();
            if (locacoes.isEmpty()) {
                System.out.println("Nenhuma locação encerrada encontrada.");
                return;
            }
            for (Locacao locacao : locacoes) {
                System.out.println(locacao.toString());
                System.out.println("---------------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar locações encerradas: " + e.getMessage());
        }
    }

    // --- MÉTODOS DE LEITURA E VALIDAÇÃO DE ENTRADA ---
    private String lerStringNaoVazia(String prompt) {
        String valor;
        while (true) {
            System.out.print(prompt);
            valor = entrada.nextLine().trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            System.err.println("Este campo não pode ser vazio.");
        }
    }

    private int lerInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("Entrada inválida. Por favor, digite um número inteiro.");
            }
        }
    }

    private double lerDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("Entrada inválida. Por favor, digite um número (ex: 123.45).");
            }
        }
    }

    private String lerEmail(String prompt) {
        String email;
        while (true) {
            email = lerStringNaoVazia(prompt);
            if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                return email;
            }
            System.err.println("Formato de e-mail inválido. Tente novamente.");
        }
    }

    private int lerIdade(String prompt) {
        int idade;
        while (true) {
            idade = lerInt(prompt);
            if (idade >= 18 && idade <= 103) {
                return idade;
            }
            System.err.println("A idade deve ser entre 18 e 103 anos.");
        }
    }

    private long lerTelefone(String prompt) {
        String telStr;
        while (true) {
            telStr = lerStringNaoVazia(prompt);
            if (telStr.matches("\\d{10,11}")) {
                return Long.parseLong(telStr);
            }
            System.err.println("O telefone deve conter 10 ou 11 dígitos numéricos.");
        }
    }

    private long lerCpf(String prompt) {
        String cpfStr;
        while (true) {
            cpfStr = lerStringNaoVazia(prompt);
            if (cpfStr.matches("\\d{11}")) {
                return Long.parseLong(cpfStr);
            }
            System.err.println("O CPF deve conter exatamente 11 dígitos numéricos.");
        }
    }
}
