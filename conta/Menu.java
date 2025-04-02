package conta;

import conta.controller.ContaController;
import conta.model.ContaCorrente;
import conta.model.ContaPoupanca;
import conta.util.Cores;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
	public static void main(String[] args) {

		ContaController contas = new ContaController();

		int opcao, numero, agencia, tipo, aniversario, numeroDestino;
		String titular;
		float saldo, limite, valor;

		System.out.println("\nCriar Contas\n");

		ContaCorrente cc1 = new ContaCorrente(contas.gerarNumero(), 123, 1, "João da Silva", 1000f, 100.0f);
		contas.cadastrar(cc1);
		ContaCorrente cc2 = new ContaCorrente(contas.gerarNumero(), 124, 1, "Maria da Silva", 2000f, 100.0f);
		contas.cadastrar(cc2);
		ContaPoupanca cp1 = new ContaPoupanca(contas.gerarNumero(), 125, 2, "Mariana dos Santos", 4000f, 12);
		contas.cadastrar(cp1);
		ContaPoupanca cp2 = new ContaPoupanca(contas.gerarNumero(), 125, 2, "Juliana Ramos", 8000f, 15);
		contas.cadastrar(cp2);
		contas.listarTodas();

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println(Cores.ANSI_BLACK_BACKGROUND + Cores.TEXT_WHITE
					+ "************************************************");
			System.out.println("BANCO");
			System.out.println("************************************************");
			System.out.println("1 - Criar conta");
			System.out.println("2 - Listar todas as contas");
			System.out.println("3 - Buscar contas por número");
			System.out.println("4 - Atualizar dados da conta");
			System.out.println("5 - Excluir conta");
			System.out.println("6 - Sacar");
			System.out.println("7 - Depositar");
			System.out.println("8 - Transferir valores entre contas");
			System.out.println("9 - Sair");
			System.out.println("************************************************");
			System.out.print("Digite a opção desejada: ");

			try {
				opcao = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("\nDigite valores inteiros!");
				scanner.nextLine();
				opcao = 0;
			}

			if (opcao == 9) {
				System.out.println("Obrigado por usar o nosso sistema.");
				sobre();
				scanner.close();
				System.exit(0);
			}
			switch (opcao) {
			case 1 -> {
				System.out.println(Cores.TEXT_WHITE + "Criar conta");
				System.out.println("Digite o número da agência: ");
				agencia = scanner.nextInt();
				System.out.println("Digite o nome do títular: ");
				scanner.skip("\\R?");
				titular = scanner.nextLine();

				do {
					System.out.println("Digite o tipo da conta (1 - CC ou 2 - CP)");
					tipo = scanner.nextInt();
				} while (tipo < 1 && tipo > 2);

				System.out.println("Digite o saldo da conta (R$): ");
				saldo = scanner.nextFloat();

				switch (tipo) {
				case 1 -> {
					System.out.println("Digite o Limite de Crédito (R$): ");
					limite = scanner.nextFloat();
					contas.cadastrar(new ContaCorrente(contas.gerarNumero(), agencia, tipo, titular, saldo, limite));
				}
				case 2 -> {
					System.out.println("Digite o dia do Aniversário da Conta: ");
					aniversario = scanner.nextInt();
					contas.cadastrar(
							new ContaPoupanca(contas.gerarNumero(), agencia, tipo, titular, saldo, aniversario));
				}
				}

				keyPress();
				break;
			}
			case 2 -> {
				System.out.println(Cores.TEXT_WHITE + "Listar todas as contas");
				contas.listarTodas();
				keyPress();
				break;
			}
			case 3 -> {
				System.out.println(Cores.TEXT_WHITE + "Buscar contas por número");
				System.out.println("Digite o número da conta: ");
				numero = scanner.nextInt();

				contas.procurarPorNumero(numero);

				keyPress();
				break;
			}
			case 4 -> {
				System.out.println(Cores.TEXT_WHITE + "Atualizar dados da conta");

				System.out.println("Digite o número da conta: ");
				numero = scanner.nextInt();

				var buscaConta = contas.buscarNaCollection(numero);

				if (buscaConta != null) {
					tipo = buscaConta.getTipo();

					System.out.println("Digite o número da agência: ");
					agencia = scanner.nextInt();
					System.out.println("Digite o nome do Titular: ");
					scanner.skip("\\R?");
					titular = scanner.nextLine();

					System.out.println("Digite o saldo da conta (R$): ");
					saldo = scanner.nextFloat();

					switch (tipo) {
					case 1 -> {
						System.out.println("Digite o limite de crédito (R$): ");
						limite = scanner.nextFloat();

						contas.atualizar(new ContaCorrente(numero, agencia, tipo, titular, saldo, limite));
					}
					case 2 -> {
						System.out.println("Digite o dia do aniversário da conta: ");
						aniversario = scanner.nextInt();

						contas.atualizar(new ContaPoupanca(numero, agencia, tipo, titular, saldo, aniversario));
					}
					default -> {
						System.out.println("Tipo de conta inválida.");
					}
					}
				} else {
					System.out.println("A conta não foi encontrada.");
				}

				keyPress();
				break;
			}
			case 5 -> {
				System.out.println(Cores.TEXT_WHITE + "Excluir conta");
				System.out.println("Digite o número da conta: ");
				numero = scanner.nextInt();

				contas.deletar(numero);
				keyPress();
				break;
			}
			case 6 -> {
				System.out.println(Cores.TEXT_WHITE + "Sacar");

				System.out.println("Digite o número da Conta: ");
				numero = scanner.nextInt();

				do {
					System.out.println("Digite o valor do saque (R$): ");
					valor = scanner.nextFloat();
				} while (valor <= 0);

				contas.sacar(numero, valor);
				keyPress();
				break;
			}
			case 7 -> {
				System.out.println(Cores.TEXT_WHITE + "Depositar");

				System.out.println("Digite o número da Conta: ");
				numero = scanner.nextInt();

				do {
					System.out.println("Digite o valor do depósito (R$): ");
					valor = scanner.nextFloat();
				} while (valor <= 0);

				contas.depositar(numero, valor);

				keyPress();
				break;
			}
			case 8 -> {
				System.out.println(Cores.TEXT_WHITE + "Transferir valores entre contas");
				System.out.println("Digite o número da conta de origem: \n");
				numero = scanner.nextInt();
				System.out.println("Digite o número da conta de destino: \n");
				numeroDestino = scanner.nextInt();

				do {
					System.out.println("Digite o valor da transferência (R$): \n");
					valor = scanner.nextFloat();
				} while (valor <= 0);

				contas.transferir(numero, numeroDestino, valor);

				keyPress();
				break;
			}
			default -> {
				System.out.println(Cores.TEXT_RED_BOLD + "\nOpção inválida\n" + Cores.TEXT_RESET);
				keyPress();
				break;
			}

			}
		}
	}

	public static void sobre() {
		System.out.println("************************************************");
		System.out.println("Desenvolvido por: Luiz Henrique Machado");
		System.out.println("Generation Brasil - generation@generation.org");
		System.err.println("github.com/conteudoGeneration");
		System.out.println("************************************************");
	}

	public static void keyPress() {
		try {
			System.out.println(Cores.TEXT_RESET + "\n\nPressione ENTER para continuar");
			System.in.read();
		} catch (IOException e) {
			System.out.println("Você pressionou um tecla diferente de enter.");
		}
	}
}
