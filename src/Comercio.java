import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Comercio {
  /** Para inclusão de novos produtos no vetor */
  static final int MAX_NOVOS_PRODUTOS = 10;

  /**
   * Nome do arquivo de dados. O arquivo deve estar localizado na raiz do projeto
   */
  static String nomeArquivoDados;

  /** Scanner para leitura do teclado */
  static Scanner teclado;

  /**
   * Vetor de produtos cadastrados. Sempre terá espaço para 10 novos produtos a
   * cada execução
   */
  static Produto[] produtosCadastrados;

  /** Quantidade produtos cadastrados atualmente no vetor */
  static int quantosProdutos;

  /** Gera um efeito de pausa na CLI. Espera por um enter para continuar */
  static void pausa() {
    System.out.println("Digite enter para continuar... ");
    teclado.nextLine();
  }

  /** Cabeçalho principal da CLI do sistema */
  static void cabecalho() {
    System.out.println("AEDII COMÉRCIO DE COISINHAS");
    System.out.println("===========================");
  }

  /**
   * Imprime o menu principal, lê a opção do usuário e a retorna (int).
   * Perceba que poderia haver uma melhor modularização com a criação de uma
   * classe Menu.
   * 
   * @return Um inteiro com a opção do usuário.
   */
  static int menu() {
    cabecalho();
    System.out.println("1 - Listar todos os produtos");
    System.out.println("2 - Procurar e listar um produto");
    System.out.println("3 - Cadastrar novo produto");
    System.out.println("0 - Sair");
    System.out.print("Digite sua opção: ");
    return Integer.parseInt(teclado.nextLine());
  }

  /**
   * Lê os dados de um arquivo texto e retorna um vetor de produtos. Arquivo no
   * formato
   * N (quantiade de produtos) <br/>
   * tipo; descrição;preçoDeCusto;margemDeLucro;[dataDeValidade] <br/>
   * Deve haver uma linha para cada um dos produtos. Retorna um vetor vazio em
   * caso de problemas com o arquivo.
   * 
   * @param nomeArquivoDados Nome do arquivo de dados a ser aberto.
   * @return Um vetor com os produtos carregados, ou vazio em caso de problemas de
   *         leitura.
   */
  static Produto[] lerProdutos(String nomeArquivoDados) {
    Produto[] vetorProdutos;
    try (Scanner scannerArquivo = new Scanner(new File(nomeArquivoDados), "UTF-8")) {
      if (scannerArquivo.hasNextLine()) {
        quantosProdutos = Integer.parseInt(scannerArquivo.nextLine().trim());
        vetorProdutos = new Produto[quantosProdutos + MAX_NOVOS_PRODUTOS];

        for (int i = 0; i < quantosProdutos; i++) {
          if (scannerArquivo.hasNextLine()) {
            vetorProdutos[i] = Produto.criarDoTexto(scannerArquivo.nextLine());
          }
        }
        return vetorProdutos;
      }
    } catch (FileNotFoundException e) {
      System.out.println("Arquivo de dados não encontrado. Iniciando projeto com vetor vazio.");
    } catch (Exception e) {
      System.out.println("Erro ao ler o arquivo: " + e.getMessage());
    }

    quantosProdutos = 0;
    return new Produto[MAX_NOVOS_PRODUTOS];
  }

  /** Lista todos os produtos cadastrados, numerados, um por linha */
  static void listarTodosOsProdutos() {
    System.out.println("\n--- Lista de Produtos ---");
    if (quantosProdutos == 0) {
      System.out.println("Nenhum produto cadastrado.");
    } else {
      for (int i = 0; i < quantosProdutos; i++) {
        System.out.println((i + 1) + ". " + produtosCadastrados[i].toString());
      }
    }
  }

  /**
   * Localiza um produto no vetor de cadastrados, a partir do nome (descrição), e
   * imprime seus dados.
   * A busca não é sensível ao caso. Em caso de não encontrar o produto, imprime
   * mensagem padrão
   */
  static void localizarProdutos() {
    System.out.print("Digite o nome (descrição) do produto a localizar: ");
    String busca = teclado.nextLine();

    // Criar um produto temporário apenas para utilizar o método equals
    Produto temp = new ProdutoNaoPerecivel(busca, 1.0);

    boolean encontrado = false;
    for (int i = 0; i < quantosProdutos; i++) {
      if (produtosCadastrados[i] != null && produtosCadastrados[i].equals(temp)) {
        System.out.println("Produto encontrado:");
        System.out.println(produtosCadastrados[i].toString());
        encontrado = true;
        break;
      }
    }

    if (!encontrado) {
      System.out.println("Produto não encontrado.");
    }
  }

  /**
   * Rotina de cadastro de um novo produto: pergunta ao usuário o tipo do produto,
   * lê os dados correspondentes,
   * cria o objeto adequado de acordo com o tipo, inclui no vetor. Este método
   * pode ser feito com um nível muito
   * melhor de modularização. As diversas fases da lógica poderiam ser
   * encapsuladas em outros métodos.
   * Uma sugestão de melhoria mais significativa poderia ser o uso de padrão
   * Factory Method para criação dos objetos.
   */
  static void cadastrarProduto() {
    System.out.println("\n--- Cadastro de Produto ---");

    if (quantosProdutos >= produtosCadastrados.length) {
      System.out.println("Capacidade máxima de produtos atingida. Não é possível cadastrar novos itens.");
      return;
    }

    try {
      System.out.print("Escolha o tipo de produto (1 - Não perecível, 2 - Perecível): ");
      int tipo = Integer.parseInt(teclado.nextLine());

      if (tipo != 1 && tipo != 2) {
        System.out.println("Tipo inválido. Operação cancelada.");
        return;
      }

      System.out.print("Descrição: ");
      String descricao = teclado.nextLine();

      System.out.print("Preço de custo: ");
      double preco = Double.parseDouble(teclado.nextLine().replace(",", "."));

      System.out.print("Margem de lucro (ex: 0.2 para 20%): ");
      double margem = Double.parseDouble(teclado.nextLine().replace(",", "."));

      Produto novo = null;
      if (tipo == 1) {
        novo = new ProdutoNaoPerecivel(descricao, preco, margem);
      } else {
        System.out.print("Data de validade (dd/MM/yyyy): ");
        String validadeStr = teclado.nextLine();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        java.time.LocalDate validade = java.time.LocalDate.parse(validadeStr, formatter);

        novo = new ProdutoPerecivel(descricao, preco, margem, validade);
      }

      produtosCadastrados[quantosProdutos] = novo;
      quantosProdutos++;
      System.out.println("Produto cadastrado com sucesso!");
    } catch (Exception e) {
      System.out.println("Erro durante o cadastro: verifique os dados informados.");
    }
  }

  /**
   * Salva os dados dos produtos cadastrados no arquivo csv informado. Sobrescreve
   * todo o conteúdo do arquivo.
   * 
   * @param nomeArquivo Nome do arquivo a ser gravado.
   */
  public static void salvarProdutos(String nomeArquivo) {
    try (PrintWriter writer = new PrintWriter(new File(nomeArquivo), "UTF-8")) {
      writer.println(quantosProdutos);
      for (int i = 0; i < quantosProdutos; i++) {
        if (produtosCadastrados[i] != null) {
          writer.println(produtosCadastrados[i].gerarDadosTexto());
        }
      }
      System.out.println("Dados salvos com sucesso.");
    } catch (Exception e) {
      System.out.println("Erro ao salvar os dados: " + e.getMessage());
    }
  }

  public static void main(String[] args) throws Exception {
    teclado = new Scanner(System.in, Charset.forName("ISO-8859-2"));
    nomeArquivoDados = "dadosProdutos.csv";
    produtosCadastrados = lerProdutos(nomeArquivoDados);
    System.out.println(produtosCadastrados.length);
    int opcao = -1;
    do {
      opcao = menu();
      switch (opcao) {
        case 1 -> listarTodosOsProdutos();
        case 2 -> localizarProdutos();
        case 3 -> cadastrarProduto();
      }
      pausa();
    } while (opcao != 0);

    salvarProdutos(nomeArquivoDados);
    teclado.close();
  }
}
