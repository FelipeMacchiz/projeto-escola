package br.system;

import br.dao.CursoDAO;
import br.dao.CursoDisciplinaDAO;
import br.dao.DisciplinaDAO;
import br.model.Aluno;
import br.model.Curso;
import br.model.CursoDisciplina;
import br.model.Disciplina;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateNasc = new Date();
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
		} while (dia > 31 || dia < 1);

		do {
			System.out.println("MÊS");
			mes = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (mes > 12 || mes < 1) {
				System.out.println("Inválido");
			}
		} while (mes > 12 || mes < 1);

		do {
			System.out.println("ANO");
			ano = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (ano > anoHoje) {
				System.out.println("Inválido");
			}
		} while (ano > anoHoje);

		String nasc = String.format("%s/%s/%s", dia, mes, ano);
		Aluno aluno = new Aluno(nome,rg,cpf,nasc);

		System.out.println("\n=========|O ALUNO FOI CADASTRADO COM SUCESSO, BEM VINDO A FACULDADE KONOHA|=========");



	}

	public static void consultaCursos() {

		Scanner input = new Scanner(System.in);

		CursoDAO cursoDAO = new CursoDAO();
		List<Curso> cursoList = cursoDAO.listar();

		}
	}

	

