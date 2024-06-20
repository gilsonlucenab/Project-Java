import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        
        List<Funcionario> funcionarios = new ArrayList<>();
        
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 15), new BigDecimal("2500.00"), "Analista"));
        funcionarios.add(new Funcionario("Maria", LocalDate.of(1985, 8, 20), new BigDecimal("3500.00"), "Gerente"));
        funcionarios.add(new Funcionario("Pedro", LocalDate.of(1988, 10, 10), new BigDecimal("3000.00"), "Desenvolvedor"));

        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        System.out.println("Lista de Funcionários:");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        funcionarios.forEach(funcionario -> {
            System.out.println("Nome: " + funcionario.getNome() +
                               ", Data de Nascimento: " + funcionario.getDataNascimento().format(formatter) +
                               ", Salário: " + funcionario.getSalario().toString().replace('.', ',') +
                               ", Função: " + funcionario.getFuncao());
        });

        funcionarios.forEach(funcionario -> funcionario.aumentarSalario(BigDecimal.TEN));

        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
        funcionarios.forEach(funcionario -> {
            String funcao = funcionario.getFuncao();
            funcionariosPorFuncao.computeIfAbsent(funcao, k -> new ArrayList<>()).add(funcionario);
        });

        System.out.println("\nFuncionários Agrupados por Função:");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(f -> {
                System.out.println("Nome: " + f.getNome() +
                                   ", Data de Nascimento: " + f.getDataNascimento().format(formatter) +
                                   ", Salário: " + f.getSalario().toString().replace('.', ','));
            });
        });

        System.out.println("\nFuncionários que Fazem Aniversário no Mês 10:");
        funcionarios.stream()
                .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == 10)
                .forEach(funcionario -> {
                    System.out.println("Nome: " + funcionario.getNome() +
                                       ", Data de Nascimento: " + funcionario.getDataNascimento().format(formatter));
                });

        Funcionario maisVelho = funcionarios.stream()
                .max(Comparator.comparing(funcionario -> ChronoUnit.YEARS.between(funcionario.getDataNascimento(), LocalDate.now())))
                .orElse(null);
        if (maisVelho != null) {
            long idade = ChronoUnit.YEARS.between(maisVelho.getDataNascimento(), LocalDate.now());
            System.out.println("\nFuncionário mais Velho:");
            System.out.println("Nome: " + maisVelho.getNome() + ", Idade: " + idade);
        }

        System.out.println("\nLista de Funcionários Ordenada por Nome:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(funcionario -> {
                    System.out.println("Nome: " + funcionario.getNome() +
                                       ", Data de Nascimento: " + funcionario.getDataNascimento().format(formatter) +
                                       ", Salário: " + funcionario.getSalario().toString().replace('.', ',') +
                                       ", Função: " + funcionario.getFuncao());
                });

        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos Salários dos Funcionários: " + totalSalarios);

        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nSalários Mínimos Ganho por Funcionário:");
        funcionarios.forEach(funcionario -> {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(funcionario.getNome() + ": " + salariosMinimos);
        });
    }
}
