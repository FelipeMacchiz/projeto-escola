package br.system;

import java.util.*;

public class ModoNaoAluno {
	public static void cadastroAluno() {

		Scanner input = new Scanner(System.in);
		
		System.out.println("Informe o nome do aluno(a): ");
		String nome = input.nextLine();
		
		System.out.println("Informe o CPF do aluno(a): ");
		String cpf = input.nextLine();
		
		System.out.println("Informe o RG do aluno(a): ");
		String rg = input.nextLine();
		
		System.out.println("Informe a data de nascimento: ");
		int dia;
		int mes;
		int ano;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int anoHoje = calendar.get(Calendar.YEAR);

			do {
				System.out.println("DIA");
				dia = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
				if (dia > 31 || dia < 1) {
					System.out.println("Inválido");
				}
			} while(dia > 31 || dia < 1);

			do {
				System.out.println("MÊS");
				mes = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
				if (dia > 12 || dia < 1) {
					System.out.println("Inválido");
				}
			} while(mes > 12 || mes < 1);

			do {
				System.out.println("ANO");
				ano = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
				if (dia > anoHoje) {
					System.out.println("Inválido");
				}
			} while(ano > anoHoje);

			System.out.println("\n=========|O ALUNO FOI CADASTRADO COM SUCESSO, BEM VINDO A FACULDADE KONOHA|=========");
	}

	/*public static void consultaCursos() {

		Scanner input = new Scanner(System.in);

		CursoDAO cursoDAO = new CursoDAO();
		List<Curso> cursoList = cursoDAO.listar();

	}
		*/
}
	
	

