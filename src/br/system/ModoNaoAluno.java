package br.system;

import br.dao.*;
import br.model.*;

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
		Aluno aluno = new Aluno(nome, rg, cpf, nasc);
		AlunoDAO alunoDAO = new AlunoDAO();
		alunoDAO.salvar(aluno);

		CursoDAO cursoDAO = new CursoDAO();
		List<Curso> cursoList = cursoDAO.listar();

		for (Curso c : cursoList) {
			System.out.printf("[%02d] %s %n", c.getCodCurso(), c.getNomeCurso());
		}

		int codCurso;
		do {
			System.out.println("Escolha um Curso: ");
			codCurso = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (codCurso < 1 || codCurso > cursoList.size()) {
				System.out.println("INVÁLIDO");
			}
		} while (codCurso < 1 || codCurso > cursoList.size());

		AlunoCursoDAO alunoCursoDAO = new AlunoCursoDAO();
		AlunoCurso alunoCurso = new AlunoCurso(codCurso);
		alunoCursoDAO.salvar(alunoCurso);

		System.out.println("\n=========|O ALUNO FOI CADASTRADO COM SUCESSO, BEM VINDO A FACULDADE KONOHA|=========");


	}

	public static void consultaCursos() {

		Scanner input = new Scanner(System.in);

		CursoDAO cursoDAO = new CursoDAO();
		List<Curso> cursoList = cursoDAO.listar();

		int verDisciplinas;
		int codCurso;

		//Listarr os Cursos//

		System.out.printf("CURSO %n");
		for (Curso c : cursoList) {
			System.out.printf("[%02d]%s%n", c.getCodCurso(), c.getNomeCurso());
		}

		//------------------//

		//Ver detalhes do curso//

		System.out.printf("Deseja ver mais detalhes do curso? [1] Sim  [0] Não %n");
		do {
			System.out.print("Selecione uma das opções: ");
			verDisciplinas = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (verDisciplinas < 0 || verDisciplinas > 1)
				System.out.printf("ERRO: Digite novamente por favor %n");
		} while (verDisciplinas < 0 || verDisciplinas > 1);

		//------------------//

		//Selecionar o curso//

		if (verDisciplinas == 1) {

			do {
				System.out.print("Selecione o curso: ");
				codCurso = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
				if (codCurso < 0 || codCurso > cursoList.size())
					System.out.printf("ERRO: Digite novamente por favor %n");
			} while (codCurso < 0 || codCurso > cursoList.size());

			CursoDisciplinaDAO cursoDisciplinaDAO = new CursoDisciplinaDAO();
			DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

			Curso curso = cursoDAO.buscarPorId(codCurso);

			List<CursoDisciplina> cursoDisciplinaList = cursoDisciplinaDAO.listar();

			List<Disciplina> disciplinasCurso = new ArrayList<>();

			for (CursoDisciplina c : cursoDisciplinaList) {

				if (c.getCodCurso() == codCurso) {
					Disciplina disciplina = disciplinaDAO.buscarPorId(c.getCodDisciplina());
					disciplinasCurso.add(disciplina);
				}

			}

			//------------------//

			//Informações do Curso//

			System.out.printf("%nNOME DO CURSO:%s %nDURAÇÃO:%s anos %nDISCIPLINAS: ", curso.getNomeCurso(), curso.getDuracao());
			for (Disciplina d : disciplinasCurso) {
				System.out.printf("%s%n", d.getNome());
			}

			//------------------//
		}

	}
}