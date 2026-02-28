import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProdutoPerecivel extends Produto {

  /** Desconto para proximidade de validade: 25% */
  private static final double DESCONTO = 0.25;

  /** Prazo, em dias, para conceder o desconto por proximidade da validade */
  private static final int PRAZO_DESCONTO = 7;

  /**
   * Data de validade do produto. Não pode ser anterior à data da criação ou venda
   */
  private LocalDate dataDeValidade;

  /** Construtor completo. ... */
  public ProdutoPerecivel(String descricao, double precoCusto, double margemLucro, LocalDate validade) {
    super(descricao, precoCusto, margemLucro);
    if (validade.isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("O produto está vencido!");
    }
    dataDeValidade = validade;
  }

  /**
   * Retorna o valor de venda do produto, considerando seu preço de custo, margem
   * de lucro e ...
   */
  @Override
  public double valorDeVenda() {
    double desconto = 0d;
    int diasValidade = LocalDate.now().until(dataDeValidade).getDays();
    if (diasValidade <= PRAZO_DESCONTO) {
      desconto = DESCONTO;
    }
    return super.valorDeVenda() * (1 - desconto);
  }

  /**
   * Descrição em string do produto, contendo sua descrição, o valor de venda e
   * data de validade. ...
   */
  @Override
  public String toString() {
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    String dados = super.toString();
    dados += "\nVálido até " + formato.format(dataDeValidade);
    return dados;
  }

  /**
   * Gera uma linha de texto a partir dos dados do produto. Preço e margem de
   * lucro vão formatados com 2 casas decimais.
   * Data de validade vai no formato dd/mm/aaaa
   * 
   * @return Uma string no formato "2;
   *         descrição;preçoDeCusto;margemDeLucro;dataDeValidade"
   */
  @Override
  public String gerarDadosTexto() {
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return String.format(java.util.Locale.US, "2;%s;%.2f;%.2f;%s", getDescricao(), getPrecoCusto(),
        getMargemLucro(),
        formato.format(dataDeValidade));
  }
}
