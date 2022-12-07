# Alunos
* Gabriel Faustino Lima da Rocha
* Isaac Neves Farias
* Joao Vitor Siqueira Resende
* Joao Alexandre de Souza Ferreira
* Lucas Kazuo Mimura
* Marcos Alves de Oliveira

# Projeto ADA

Olá, alunos!

Neste repositório vamos desenvolver um projeto em que vamos colocar em prática tudo o que estamos vendo nas aulas deste módulo!

Vamos carregar um arquivo CSV (que está na pasta resources) e processar seus dados com as seguintes condições:

1. O pagamento terá acréscimo para cada mês de atraso
    1. Crédito: 3% para cada mês
    2. Débito: 1% para cada mês
    3. Boleto: 5% para cada mês

2. Pagamentos sem vencimento em pix ou cartão fidelidade terão um desconto de 0.5% para cada dia adiantado.

3. O resultado deste processamento deverá ser salvo em arquivos csv separados por tipos de pagamento, com o seguinte formato:
   >PAGAMENTOS_{TIPO_PAGAMENTO}_{DATA_DO_PROCESSAMENTO_YYYY-MM-DD}.csv

Por exemplo: `PAGAMENTOS_PIX_2022-12-04.csv`

Façam um fork deste projeto, que deverá ser feito em grupo, com cada integrante realizando seu commit, para que sua avaliação individual seja possível

Com o projeto concluído, enviem o link para o Class até o dia 7/12.

Boa sorte!
