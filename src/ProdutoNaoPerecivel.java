public class ProdutoNaoPerecivel extends Produto {

  /** Construtor completo. Causa exceção em caso de valores inválidos ... */
  public ProdutoNaoPerecivel(String descricao, double precoCusto, double margemLucro) {
    super(descricao, precoCusto, margemLucro);
  }

  /**
   * Construtor com margem de lucro padrão (20%). Causa exceção em caso de valores
   * inválidos ...
   */
  public ProdutoNaoPerecivel(String descricao, double precoCusto) {
    super(descricao, precoCusto);
  }

  /**
   * Retorna o valor de venda do produto, considerando seu preço de custo e margem
   * de lucro ...
   */
  @Override
  public double valorDeVenda() {
    return super.valorDeVenda();
  }

  /**
   * Gera uma linha de texto a partir dos dados do produto. Preço e margem de
   * lucro vão formatados com 2 casas decimais.
   * 
   * @return Uma string no formato "1; descrição;preçoDeCusto;margemDeLucro"
   */
  @Override
  public String gerarDadosTexto() {
    return String.format(java.util.Locale.US, "1;%s;%.2f;%.2f", getDescricao(), getPrecoCusto(),
        getMargemLucro());
  }
}
