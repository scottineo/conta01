package conta.controller;

import java.net.http.HttpResponse.BodySubscriber;
import java.util.ArrayList;

import javax.management.ValueExp;

import conta.model.Conta;
import conta.repositorio.ContaRepositorio;

public class ContaController implements ContaRepositorio {
	private ArrayList<Conta> listaContas = new ArrayList<>();
	int numero = 0;

	@Override
	public void procurarPorNumero(int numero) {
		var conta = buscarNaCollection(numero);

		if (conta != null) {
			conta.visualizar();
		} else {
			System.out.println("\nA Conta número: " + numero + " não foi encontrada!");
		}

	}

	@Override
	public void listarTodas() {
		for (var conta : listaContas) {
			conta.visualizar();
		}

	}

	@Override
	public void cadastrar(Conta conta) {
		listaContas.add(conta);
		System.out.println("\nA Conta número: " + conta.getNumero() + " foi criada com sucesso!");
	}

	@Override
	public void atualizar(Conta conta) {
		var buscaConta = buscarNaCollection(conta.getNumero());

		if (buscaConta != null) {
			listaContas.set(listaContas.indexOf(buscaConta), conta);
			System.out.println("\n Conta número: " + conta.getNumero() + " foi atualizada com sucesso!");
		} else {
			System.out.println("\n Conta número: " + conta.getNumero() + " não foi encontrada!");
		}

	}

	@Override
	public void deletar(int numero) {
		var conta = buscarNaCollection(numero);

		if (conta != null) {
			if (listaContas.remove(conta) == true) {
				System.out.println("\nA conta número: " + numero + " foi deletada com sucesso!");
			} else {
				System.out.println("\nA conta número: " + numero + " não foi encontrada.");
			}
		}

	}

	@Override
	public void sacar(int numero, float valor) {
		var conta = buscarNaCollection(numero);

		if (conta != null) {
			if (conta.sacar(valor) == true)
				System.out.println("\nO saque na Conta número: " + numero + " foi efetuaod com sucesso!");
		} else {
			System.out.println("\nA Conta número: " + numero + " não foi encontrada!");
		}
	}

	@Override
	public void depositar(int numero, float valor) {
		var conta = buscarNaCollection(numero);

		if (conta != null) {
			conta.depositar(valor);
			System.out.println("\nOdepósito na conta número: " + numero + " foi efetuado com sucesso!");
		} else {
			System.out.println(
					"\nA conta número: " + numero + " não foi encontrada ou a conta destino não é uma Conta Corrente!");
		}

	}

	@Override
	public void transferir(int numero, int numeroDestino, float valor) {
		var contaOrigem = buscarNaCollection(numero);
		var contaDestino = buscarNaCollection(numeroDestino);

		if (contaOrigem.sacar(valor) == true) {
			contaDestino.depositar(valor);
			System.out.println("\nA transferência foi efetuada com sucesso!");
		} else {
			System.out.println("\nA oonta de origem e/ou destino não foram encontradas!");
		}
	}

	public int gerarNumero() {
		return ++numero;
	}

	public Conta buscarNaCollection(int numero) {
		for (var conta : listaContas) {
			if (conta.getNumero() == numero) {
				return conta;
			}
		}
		return null;
	}

}
