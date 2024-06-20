import java.math.BigDecimal;
import java.time.LocalDate;

public class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void aumentarSalario(BigDecimal percentual) {
        this.salario = this.salario.add(this.salario.multiply(percentual.divide(BigDecimal.valueOf(100))));
    }

    @Override
    public String toString() {
        return String.format("Nome: %s, Data de Nascimento: %s, Salário: %.2f, Função: %s",
                getNome(), getDataNascimento(), salario, funcao);
    }
}
