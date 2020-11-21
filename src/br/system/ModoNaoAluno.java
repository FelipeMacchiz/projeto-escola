package br.system;

import br.dao.*;
import br.model.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ModoNaoAluno {

	public static final String RESET = "\u001B[0m";
	public static final String BLACK = "\u001B[30m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";

	public static void cadastroAluno() {


		Scanner input = new Scanner(System.in);

		List<Integer> codigos = new ArrayList<>();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int anoHoje = calendar.get(Calendar.YEAR);

		int dia;
		int mes;
		int ano;
		int codCurso;

		System.out.printf("%s= CADASTRO NOVO ALUNO%s\n", YELLOW, RESET);
		System.out.print("Digite o seu nome: ");
		String nome = input.nextLine();

		System.out.print("Digite o seu CPF: ");
		String cpf = input.nextLine();

		System.out.print("Digite o seu RG: ");
		String rg = input.nextLine();

		System.out.printf("%sInforme a data de nascimento: %s\n", YELLOW, RESET);

		do {
			System.out.print("- Dia: ");
			dia = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (dia < 1 || dia > 31)
				System.out.printf("%s<!> Dia inválido. Digite novamente%s\n", RED, RESET);
		} while (dia < 1 || dia > 31);

		do {
			System.out.print("- Mês: ");
			mes = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (mes < 1 || mes > 12)
				System.out.printf("%s<!> Mês inválido. Digite novamente%s\n", RED, RESET);
		} while (mes < 1 || mes > 12);

		do {
			System.out.print("- Ano: ");
			ano = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (ano > anoHoje)
				System.out.printf("%s<!> Ano inválido. Digite novamente%s\n", RED, RESET);
		} while (ano > anoHoje);

		String nasc = String.format("%s/%s/%s", dia, mes, ano);
		Aluno aluno = new Aluno(nome, rg, cpf, nasc);
		AlunoDAO alunoDAO = new AlunoDAO();
		alunoDAO.salvar(aluno);

		CursoDAO cursoDAO = new CursoDAO();
		List<Curso> cursoList = cursoDAO.listar();

		System.out.printf("%s= CURSOS%s\n", BLUE, RESET);
		for (Curso c : cursoList) {
			codigos.add(c.getCodCurso());
			System.out.printf("%s[%02d] %s%s\n", BLUE, c.getCodCurso(), c.getNomeCurso(), RESET);
		}

		do {
			System.out.print("Escolha um Curso: ");
			codCurso = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (!(codigos.contains(codCurso)))
				System.out.printf("%s<!> Digite novamente%s\n", RED, RESET);
		} while (!(codigos.contains(codCurso)));

		AlunoCursoDAO alunoCursoDAO = new AlunoCursoDAO();
		AlunoCurso alunoCurso = new AlunoCurso(codCurso);
		alunoCursoDAO.salvar(alunoCurso);

		System.out.printf("%s[!] Aluno cadastrado!%s\n", GREEN, RESET);
		System.out.printf("%s= DADOS\n" +
				"- Matrícula: %04d\n" +
				"- Nome: %s\n" +
				"- CPF: %s\n" +
				"- RG: %s\n" +
				"- Data de Nascimento: %s%s\n",
				CYAN, aluno.getCodAluno(), aluno.getNome(), aluno.getCpf(), aluno.getRg(), aluno.getNasc(), RESET);

	}

	public static void consultaCursos() {

		Scanner input = new Scanner(System.in);

		CursoDAO cursoDAO = new CursoDAO();
		List<Curso> cursoList = cursoDAO.listar();
		List<Integer> codigos = new ArrayList<>();

		int codCurso;

		//Listar os Cursos//

		System.out.printf("%s= CURSOS%s\n", BLUE, RESET);
		for (Curso c : cursoList) {
			codigos.add(c.getCodCurso());
			System.out.printf("%s[%02d]%s%s\n", BLUE, c.getCodCurso(), c.getNomeCurso(), RESET);
		}

		//Selecionar o curso//

		do {
			System.out.print("Selecione o curso: ");
			codCurso = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (!(codigos.contains(codCurso)))
				System.out.printf("%s<!> Digite novamente%s\n", RED, RESET);
		} while (!(codigos.contains(codCurso)));

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

		System.out.printf("%n%s= NOME DO CURSO: %s\n" +
						"DURAÇÃO: %s anos\n" +
						"DISCIPLINAS: %s\n",
				CYAN, curso.getNomeCurso(), curso.getDuracao(), RESET);
		for (Disciplina d : disciplinasCurso) {
			System.out.printf("%s- %s%s\n", CYAN, d.getNome(), RESET);
		}

	}
}