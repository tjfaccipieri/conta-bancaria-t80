package contaBancariaGen;

import java.io.IOException;
import java.nio.file.spi.FileSystemProvider;
import java.util.InputMismatchException;
import java.util.Scanner;

import contaBancariaGen.controller.ContaController;
import contaBancariaGen.model.ContaCorrente;
import contaBancariaGen.model.ContaPoupanca;
import contaBancariaGen.utils.Cores;


public class Menu {

	public static void main(String[] args) {
		
		// Instancias necessárias
		Scanner leia = new Scanner(System.in);
		ContaController contas = new ContaController();
		
		//Variaveis de controle do projeto
		int opcao, numero, agencia, tipo, aniversario, numeroDestino;
		//Aqui vai vir o nome do titular da conta
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
		
		//laço de repetição para o menu
		while(true) {
			System.out.println(Cores.TEXT_WHITE_BOLD_BRIGHT + "*****************************************************");
			System.out.println("                                                     ");
			System.out.println("                BANCO DO BRAZIL COM Z                ");
			System.out.println("                                                     ");
			System.out.println("*****************************************************");
			System.out.println("                                                     ");
			System.out.println("            1 - Criar Conta                          ");
			System.out.println("            2 - Listar todas as Contas               ");
			System.out.println("            3 - Buscar Conta por Numero              ");
			System.out.println("            4 - Atualizar Dados da Conta             ");
			System.out.println("            5 - Apagar Conta                         ");
			System.out.println("            6 - Sacar                                ");
			System.out.println("            7 - Depositar                            ");
			System.out.println("            8 - Transferir valores entre Contas      ");
			System.out.println("            9 - Sair                                 ");
			System.out.println("                                                     ");
			System.out.println("*****************************************************");
			System.out.println("Entre com a opção desejada:                          ");
			System.out.println("                                                     " + Cores.TEXT_RESET);
			
			//tratativa de exception da opção
			try {
				opcao = leia.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("\nDigite um numero do menu");
				leia.nextLine();
				opcao = 0;
			}
			
			//laço de decisão da opção -> saida do sistema
			if(opcao == 9) {
				System.out.println("\nBanco do Brazil com Z - O seu Futuro começa aqui!");
				sobre();
				leia.close();
				System.exit(0);
			}
			
			//laço de decisão da opção -> opções válidas
			switch(opcao) {
				case 1:
					System.out.println(Cores.TEXT_WHITE_BOLD_BRIGHT + "Criar nova conta\n\n");
					System.out.println("Digite o número da Agência: ");
					agencia = leia.nextInt();
					System.out.println("Digite o nome do Titular: ");
					leia.skip("\\R?"); //faz a mesma função que o leia.nextLine(); vazio
					titular = leia.nextLine();
					
					do {
						System.out.println("Digite o tipo da conta: 1 - Conta Corrente / 2 - Conta Poupança");
						tipo = leia.nextInt();
					} while(tipo < 1 && tipo > 2);
					
					System.out.println("Digite o saldo da conta (R$): ");
					saldo = leia.nextFloat();
					
					switch(tipo) {
						case 1 -> {
							System.out.println("Digite o limite de crédito da conta: ");
							limite = leia.nextFloat();
							contas.cadastrar(new ContaCorrente(contas.gerarNumero(), agencia, tipo, titular, saldo, limite));
						}
						case 2 -> {
							System.out.println("Digite o dia do aniversário da conta: ");
							aniversario = leia.nextInt();
							contas.cadastrar(new ContaPoupanca(contas.gerarNumero(), agencia, tipo, titular, saldo, aniversario));
						}
					}
					
					keyPress();
					break;
				case 2:
					System.out.println("Listar todas as contas\n\n");
					contas.listarTodas();
					keyPress();
					break;
				case 3:
					System.out.println("Consultar dados da conta - Por número\n\n");
					System.out.println("Digite o número da conta: ");
					numero = leia.nextInt();
					contas.procurarPorNumero(numero);
					keyPress();
					break;
				case 4:
					System.out.println("Atualizar dados da conta\n\n");
					System.out.println("Digite o número da conta que será atualizada: ");
					numero = leia.nextInt();
					var buscaConta = contas.buscarNaCollection(numero);
					
					if(buscaConta != null) {
						tipo = buscaConta.getTipo();
						System.out.println("Digite o número da agencia: ");
						agencia = leia.nextInt();
						
						System.out.println("Digite o nome do titular da conta: ");
						leia.skip("\\R?");
						titular = leia.nextLine();
						
						System.out.println("Digite o saldo da conta: ");
						saldo = leia.nextFloat();
						
						switch(tipo) {
							case 1 -> {
								System.out.println("Digite o limite de crédito da conta: ");
								limite = leia.nextFloat();
								contas.atualizar(new ContaCorrente(contas.gerarNumero(), agencia, tipo, titular, saldo, limite));
							}
							case 2 -> {
								System.out.println("Digite o dia do aniversário da conta: ");
								aniversario = leia.nextInt();
								contas.atualizar(new ContaPoupanca(contas.gerarNumero(), agencia, tipo, titular, saldo, aniversario));
							}
							default -> {
								System.out.println("Tipo de conta inválido");
							}
						}
						
					} else {
						System.out.println("A Conta não foi encontrada!");
					}
					
					keyPress();
					break;
				case 5:
					System.out.println("Apagar conta\n\n");
					System.out.println("Digite o número da conta para apagar: ");
					numero = leia.nextInt();
					contas.deletar(numero);
					
					keyPress();
					break;
				case 6:
					System.out.println("Saque\n\n");
					System.out.println("Digite o numero da conta: ");
					numero = leia.nextInt();
					
					do {
						System.out.println("Digite o valor do saque (R$): ");
						valor = leia.nextFloat();
					} while(valor <= 10);
					
					contas.sacar(numero, valor);
					
					keyPress();
					break;
				case 7:
					System.out.println("Depósito\n\n");
					System.out.println("Digite o número da conta: ");
					numero = leia.nextInt();
					
					do {
						System.out.println("Digite o valor (R$) do depósito: ");
						valor = leia.nextFloat();
					} while (valor <= 0);
					
					contas.depositar(numero, valor);
					
					keyPress();
					break;
				case 8:
					System.out.println("Transferência entre contas\n\n");
					System.out.println("Digite o número da conta de Origem: ");
					numero = leia.nextInt();
					
					System.out.println("Digite o número da conta de Destino: ");
					numeroDestino = leia.nextInt();
					
					do {
						System.out.println("Digite o valor da Transferência (R$): ");
						valor = leia.nextFloat();
					} while (valor <= 0);
					
					contas.transferir(numero, numeroDestino, valor);
					
					keyPress();
					break;
				default:
					System.out.println(Cores.TEXT_RED_BOLD_BRIGHT + "\nOpção Invalida\n\n" + Cores.TEXT_RESET);
					keyPress();
					break;
			}
		}
	}
	
	public static void sobre() {
		System.out.println("\n*********************************************************");
		System.out.println("Projeto Desenvolvido por: ");
		System.out.println("Thiago Faccipieri - thiago.faccipieri@gmail.com");
		System.out.println("github.com/tjfaccipieri");
		System.out.println("*********************************************************");
	}
	
	public static void keyPress() {
		try {
			System.out.println("\n\nPrecione Enter para continuar...");
			System.in.read();
		} catch (IOException e) {
			System.out.println("Você precionou uma tecla diferente de Enter!");
		}
	}

}
