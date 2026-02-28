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
}
