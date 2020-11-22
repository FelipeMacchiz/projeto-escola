package br.system;

import br.dao.*;
import br.model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ModoAdmin {

	public static final String RESET = "\u001B[0m";
	public static final String WHITE = "\u001B[30m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";

	public static void cadastroFuncionario() {

		Scanner input = new Scanner(System.in);

		System.out.printf("%s= CADASTRO DO FUNCIONÁRIO%s\n", BLUE, RESET);

		System.out.printf("%sNome: %s", WHITE, RESET);
		String nome = input.nextLine();

		System.out.printf("%sCPF: %s", WHITE, RESET);
		String cpf = input.nextLine();

		System.out.printf("%sRG: %s", WHITE, RESET);
		String rg = input.nextLine();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int anoHoje = calendar.get(Calendar.YEAR);
		int dia;
		int mes;
		int ano;

		System.out.printf("%s- Data de nascimento%s\n", YELLOW, RESET);
		do {
			System.out.printf("%sDia: %s", WHITE, RESET);
			dia = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (dia < 1 || dia > 31) {
				System.out.printf("%s<!> Dia do mês invalido. Digite novamente%s\n", RED, RESET);
			}
		} while (dia < 1 || dia > 31);

		do {
			System.out.printf("%sMês: %s", WHITE, RESET);
			mes = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (mes < 1 || mes > 12) {
				System.out.printf("%s<!> Mês invalido. Digite novamente%s\n", RED, RESET);
			}
		} while (mes < 1 || mes > 12);

		do {
			System.out.printf("%sAno: %s", WHITE, RESET);
			ano = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (ano > anoHoje) {
				System.out.printf("%s<!> Ano invalido. Digite novamente%s\n", RED, RESET);
			}
		} while (ano > anoHoje);

		DepartamentoDAO departamentoDAO = new DepartamentoDAO();
		List<Departamento> departamentoList = departamentoDAO.listar();

		System.out.printf("%s= DEPARTAMENTO%s\n", BLUE, RESET);
		for (Departamento d : departamentoList) {
			System.out.printf("%s[%02d] %s %s\n", BLUE, d.getCodDepartamento(), d.getDescricao(), RESET);
		}

		int codDepartamento;
		do {
			System.out.printf("%sDigite o código do departamento: %s", WHITE, RESET);
			codDepartamento =  Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if ( codDepartamento < 0 || codDepartamento > departamentoList.size())
				System.out.printf("%s<!> Ano invalido. Digite novamente%s\n", RED, RESET);
		} while (codDepartamento < 0 || codDepartamento > departamentoList.size());

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		Calendar instance = Calendar.getInstance();
		instance.setTime(new Date());
		instance.set(Calendar.DATE, dia);
		instance.set(Calendar.MONTH, mes);
		instance.set(Calendar.YEAR, ano);

		String nasc = dateFormat.format(instance.getTime());

		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		Funcionario funcionario = new Funcionario(nome, rg, cpf, nasc);
		funcionarioDAO.salvar(funcionario);

		DepFuncionarioDAO depFuncionarioDAO = new DepFuncionarioDAO();
		depFuncionarioDAO.salvar(new DepFuncionario(codDepartamento));

		System.out.printf("%s[!] Funcionário cadastrado%s\n", GREEN, RESET);

	}

	public static void cadastroCurso() {

		Scanner input = new Scanner(System.in);
		int duracao;

		System.out.printf("%s= CADASTRO CURSO%s\n", YELLOW, RESET);

		System.out.printf("%sInforme o nome do curso: %s", WHITE, RESET);
		String nomeCurso = input.nextLine();

		do {
			System.out.printf("%sQual a duração em anos desse curso: %s", WHITE, RESET);
			duracao = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (duracao < 1)
				System.out.printf("%s<!> O número deve ser um número inteiro maior que 0. Digite novamente%s\n", RED, RESET);
		} while (duracao < 1);

		CursoDAO cursoDAO = new CursoDAO();
		Curso curso = new Curso(nomeCurso, duracao);
		cursoDAO.salvar(curso);

		System.out.printf("%s[!] Curso criado%s\n", GREEN, RESET);

	}

	public static void cadastroDisciplina() {

		Scanner input = new Scanner(System.in);

		CursoDAO cursoDAO = new CursoDAO();
		List<Curso> cursoList = cursoDAO.listar();
		List<Integer> codigos = new ArrayList<>();

		int codCurso;

		System.out.printf("%s= CADASTRO DISCIPLINA%s\n", YELLOW, RESET);

		System.out.printf("%sInforme o nome da disciplina: %s", WHITE, RESET);
		String nome = input.nextLine();

		System.out.printf("%s= CURSOS%s\n", BLUE, RESET);
		for (Curso c : cursoList) {
			codigos.add(c.getCodCurso());
			System.out.printf("%s[%02d] %s%s\n", BLUE, c.getCodCurso(), c.getNomeCurso(), RESET);
		}

		do {
			System.out.printf("%sEscolha o curso: %s", WHITE, RESET);
			codCurso = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (!(codigos.contains(codCurso)))
				System.out.printf("%s<!> Digite novamente%s\n", RED, RESET);
		} while (!(codigos.contains(codCurso)));

		DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
		Disciplina disciplina = new Disciplina(nome);
		disciplinaDAO.salvar(disciplina);

		CursoDisciplinaDAO cursoDisciplinaDAO = new CursoDisciplinaDAO();
		CursoDisciplina cursoDisciplina = new CursoDisciplina(codCurso);
		cursoDisciplinaDAO.salvar(cursoDisciplina);

		System.out.printf("%s[!] Disciplina criada%s\n", GREEN, RESET);

	}

	public static void cadastroLivro() {

		Scanner input = new Scanner(System.in);

		System.out.printf("%s= CADASTRO LIVRO%s\n", YELLOW, RESET);

		System.out.printf("%sInforme o nome do livro: %s", WHITE, RESET);
		String nome = input.nextLine();

		LivroDAO livroDAO = new LivroDAO();
		Livro livro = new Livro(nome);
		livroDAO.salvar(livro);

		System.out.printf("%s[!] Livro cadastrado%s\n", GREEN, RESET);

	}


	public static void atualizarFuncionario() {

		Scanner input = new Scanner(System.in);

		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		List<Funcionario> funcionarioList = funcionarioDAO.listar();
		List<Integer> codigos = new ArrayList<>();

		Funcionario funcionario;
		int codFuncionario;

		System.out.printf("%s= LISTA FUNCIONÁRIOS%s\n", BLUE, RESET);
		for (Funcionario f : funcionarioList) {
			codigos.add(f.getCodFuncionario());
			System.out.printf("%s[%02d] %s%s\n", BLUE, f.getCodFuncionario(), f.getNome(), RESET);
		}

		do {
			System.out.print("Selecione o funcionário: ");
			codFuncionario = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (!(codigos.contains(codFuncionario)))
				System.out.printf("%s<!> Código inválido.%s\n", RED, RESET);
		} while (!(codigos.contains(codFuncionario)));

		funcionario = funcionarioDAO.buscarPorId(codFuncionario);

		System.out.printf("%s= Atualize os dados de %s (<!> Para manter os dados atuais, deixe em branco)%s\n",
				PURPLE, funcionario.getNome(), RESET);
		System.out.printf("%s| Nome: %s %s\n%sNovo nome: %s", YELLOW, funcionario.getNome(), RESET, WHITE, RESET);
		String nome = input.nextLine();
		if (nome.equals("")){
			nome = funcionario.getNome();
		}

		System.out.printf("%s| RG: %s %s\n%sNovo RG: %s",  YELLOW, funcionario.getRg(), RESET, WHITE, RESET);
		String rg = input.nextLine();
		if (rg.equals("")) {
			rg = funcionario.getRg();
		}

		System.out.printf("%s| CPF: %s %s\n%sNovo CPF: %s", YELLOW, funcionario.getCpf(), RESET, WHITE, RESET);
		String cpf = input.nextLine();
		if (cpf.equals("")) {
			cpf = funcionario.getCpf();
		}

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String nasc = funcionario.getNasc();
		Date dateNasc = new Date();
		try {
			dateNasc = dateFormat.parse(nasc);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateNasc);

		Calendar hoje = Calendar.getInstance();
		hoje.setTime(new Date());

		System.out.printf("%s| Data de Nascimento: %s %s\n", YELLOW, funcionario.getNasc(), RESET);
		String dia;
		do {
			System.out.printf("%s- Dia: %s", WHITE, RESET);
			dia = input.nextLine().replaceAll("[^0-9]", "");
			if (dia.equals("")) {
				dia = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			}
		} while (Integer.parseInt(dia) < 1 || Integer.parseInt(dia) > 31);

		String mes;
		do {
			System.out.printf("%s- Mês: %s", WHITE, RESET);
			mes = input.nextLine().replaceAll("[^0-9]", "");
			if (mes.equals("")) {
				mes = String.valueOf(calendar.get(Calendar.MONTH) + 1);
			}
		} while (Integer.parseInt(mes) < 1 || Integer.parseInt(mes) > 12);

		String ano;
		do {
			System.out.printf("%s- Ano: %s", WHITE, RESET);
			ano = input.nextLine().replaceAll("[^0-9]", "");
			if (ano.equals("")) {
				ano = String.valueOf(calendar.get(Calendar.YEAR));
			}
		} while (Integer.parseInt(ano) > hoje.get(Calendar.YEAR));

		nasc = String.format("%s/%s/%s", dia, mes, ano);

		Funcionario funcionarioAtualizado = new Funcionario(codFuncionario, nome, rg, cpf, nasc);
		funcionarioDAO.atualizar(funcionarioAtualizado);

		System.out.printf("%s= FUNCIONÁRIO ATUALIZADO! \n" +
				"ID: %04d\n" +
				"Nome: %s\n" +
				"RG: %s\n" +
				"CPF: %s\n" +
				"Data de Nascimento: %s %s\n",
				CYAN, funcionario.getCodFuncionario(), funcionario.getNome(), funcionario.getRg(), funcionario.getCpf(), funcionario.getNasc(), RESET);

	}

	public static void atualizarCurso() {

		Scanner input = new Scanner(System.in);

		CursoDAO cursoDAO = new CursoDAO();
		List<Curso> cursoList = cursoDAO.listar();
		List<Integer> codigos = new ArrayList<>();

		Curso curso;
		int codCurso;

		System.out.printf("%s= CURSOS%s\n", BLUE, RESET);
		for (Curso c : cursoList) {
			codigos.add(c.getCodCurso());
			System.out.printf("%s[%02d] %s%s\n", BLUE, c.getCodCurso(), c.getNomeCurso(), RESET);
		}

		do {
			System.out.print("Selecione o curso: ");
			codCurso = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (!(codigos.contains(codCurso)))
				System.out.printf("%s<!> Código inválido.%s\n", RED, RESET);
		} while (!(codigos.contains(codCurso)));

		curso = cursoDAO.buscarPorId(codCurso);

		System.out.printf("%s= Atualize os dados de %s (<!> Para manter os dados atuais, deixe em branco)%s\n",
				PURPLE,curso.getNomeCurso(), RESET);

		System.out.printf("%s| Nome do Curso: %s %s\n%sNovo nome: %s", YELLOW, curso.getNomeCurso(), RESET, WHITE, RESET);
		String nomeCurso = input.nextLine();
		if (nomeCurso.equals("")){
			nomeCurso = curso.getNomeCurso();
		}

		System.out.printf("%s| Duração do Curso: %d anos%s\n%sNova duração: %s", YELLOW, curso.getDuracao(), RESET, WHITE, RESET);
		String duracao = input.nextLine().replaceAll("[^0-9]", "");
		if (nomeCurso.equals("")){
			duracao = String.valueOf(curso.getDuracao());
		}

		Curso cursoAtualizado = new Curso(codCurso, nomeCurso, Integer.parseInt(duracao));
		cursoDAO.atualizar(cursoAtualizado);

		System.out.printf("%s= CURSO ATUALIZADO\n" +
				"Código do Curso: %02d\n" +
				"Nome: %s\n" +
				"Duração: %d anos%s\n",
				CYAN, cursoAtualizado.getCodCurso(), cursoAtualizado.getNomeCurso(), cursoAtualizado.getDuracao(), RESET);

	}

	public static void atualizarDisciplina() {

		Scanner input = new Scanner(System.in);

		CursoDAO cursoDAO = new CursoDAO();
		DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
		CursoDisciplinaDAO cursoDisciplinaDAO = new CursoDisciplinaDAO();

		List<Curso> cursoList = cursoDAO.listar();
		List<CursoDisciplina> cursoDisciplinaList = cursoDisciplinaDAO.listar();

		List<Integer> codCursoList = new ArrayList<>();
		List<Integer> codigos = new ArrayList<>();

		int codDisciplina;

		System.out.printf("%s= CURSOS E DISCIPLINAS%s\n", BLUE, RESET);

		for (Curso c : cursoList) {
			if (!(codCursoList.contains(c.getCodCurso()))) {
				codCursoList.add(c.getCodCurso());
			}
		}


		for (CursoDisciplina cd : cursoDisciplinaList) {

			codigos.add(cd.getCodDisciplina());

			Curso c = cursoDAO.buscarPorId(cd.getCodCurso());
			Disciplina d = disciplinaDAO.buscarPorId(cd.getCodDisciplina());
			System.out.printf(" %s[%02d] %s (%s) %s\n", BLUE, d.getCodDisciplina(), d.getNome(), c.getNomeCurso(), RESET);

		}

		do {
			System.out.print("Selecione a disciplina: ");
			codDisciplina = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (!(codigos.contains(codDisciplina)))
				System.out.printf("%s<!> Código inválido. Digite novamente%s\n", RED, RESET);
		} while (!(codigos.contains(codDisciplina)));

		Disciplina disciplina = disciplinaDAO.buscarPorId(codDisciplina);
		CursoDisciplina cursoDisciplina = cursoDisciplinaDAO.buscarPorId(codDisciplina);
		Curso curso = cursoDAO.buscarPorId(cursoDisciplina.getCodCurso());

		System.out.printf("%s= Atualize os dados de %s (<!> Para manter os dados atuais, deixe em branco)%s\n",
				PURPLE, disciplina.getNome(), RESET);

		System.out.printf("%s| Nome da Disciplina: %s %s\n%sNovo nome: %s", YELLOW, disciplina.getNome(), RESET, WHITE, RESET);
		String nome = input.nextLine();
		if (nome.equals("")){
			nome = disciplina.getNome();
		}

		System.out.printf("%s| Curso: [%02d] %s %s\n%sNovo curso: %s", YELLOW, curso.getCodCurso(), curso.getNomeCurso(), RESET, WHITE, RESET);
		String codCurso = input.nextLine().replaceAll("[^0-9]", "");
		if (codCurso.equals("")){
			codCurso = String.valueOf(cursoDisciplina.getCodCurso());
		}

		Disciplina disciplinaAtualizada = new Disciplina(codDisciplina, nome);
		CursoDisciplina cdAtualizada = new CursoDisciplina(codDisciplina, Integer.parseInt(codCurso));
		Curso cursoAtualizado = cursoDAO.buscarPorId(Integer.parseInt(codCurso));

		disciplinaDAO.atualizar(disciplinaAtualizada);
		cursoDisciplinaDAO.atualizar(cdAtualizada);

		System.out.printf("%s= DISCIPLINA ATUALIZADA\n" +
				"Código: %02d\n" +
				"Nome: %s\n" +
				"Curso: [%02d] %s %s\n",
				CYAN, disciplinaAtualizada.getCodDisciplina(), disciplinaAtualizada.getNome(), cursoAtualizado.getCodCurso(), cursoAtualizado.getNomeCurso(), RESET);

	}

	public static void atualizarLivro() {

		Scanner input = new Scanner(System.in);

		LivroDAO livroDAO = new LivroDAO();
		List<Livro> livroList = livroDAO.listar();
		List<Integer> codigos = new ArrayList<>();

		int codLivro;

		System.out.printf("%s= LIVROS%s\n", BLUE, RESET);
		for (Livro l : livroList) {
			codigos.add(l.getCodLivro());
			System.out.printf("%s[%02d] %s%s\n", BLUE, l.getCodLivro(), l.getNome(), RESET);
		}

		do {
			System.out.print("Selecione o livro: ");
			codLivro = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (!(codigos.contains(codLivro)))
				System.out.printf("%s<!> Código inválido. Digite novamente%s\n", RED, RESET);
		} while (!(codigos.contains(codLivro)));

		Livro livro = livroDAO.buscarPorId(codLivro);

		System.out.printf("%s= Atualize os dados de %s (<!> Para manter os dados atuais, deixe em branco)%s\n",
				PURPLE, livro.getNome(), RESET);

		System.out.printf("%s| Nome do Livro: %s %s\n%sNovo nome: %s", YELLOW, livro.getNome(), RESET, WHITE, RESET);
		String nome = input.nextLine();
		if (nome.equals("")){
			nome = livro.getNome();
		}

		Livro livroAtualizado = new Livro(codLivro, nome);
		livroDAO.atualizar(livroAtualizado);

		System.out.printf("%s= LIVRO ATUALIZADO\n" +
				"Código: %02d\n" +
				"Nome: %s%s\n",
				CYAN, livroAtualizado.getCodLivro(), livroAtualizado.getNome(), RESET);

	}


	public static void deletarFuncionario() {

		Scanner input = new Scanner(System.in);

		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		List<Funcionario> funcionarioList = funcionarioDAO.listar();
		List<Integer> codigos = new ArrayList<>();

		int codFuncionario;

		System.out.printf("%s= LISTA FUNCIONÁRIOS%s\n", BLUE, RESET);
		for (Funcionario f : funcionarioList) {
			codigos.add(f.getCodFuncionario());
			System.out.printf("%s[%02d] %s%s\n", BLUE, f.getCodFuncionario(), f.getNome(), RESET);
		}

		do {
			System.out.print("Selecione o funcionário: ");
			codFuncionario = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (!(codigos.contains(codFuncionario)))
				System.out.printf("%s<!> Código inválido.%s\n", RED, RESET);
		} while (!(codigos.contains(codFuncionario)));

		funcionarioDAO.apagar(codFuncionario);
		System.out.printf("%s[!] Funcionário deletado%s\n", PURPLE, RESET);

	}

	public static void deletarCurso() {

		Scanner input = new Scanner(System.in);

		CursoDAO cursoDAO = new CursoDAO();
		List<Curso> cursoList = cursoDAO.listar();
		List<Integer> codigos = new ArrayList<>();

		int codCurso;

		System.out.printf("%s= CURSOS%s\n", BLUE, RESET);
		for (Curso c : cursoList) {
			codigos.add(c.getCodCurso());
			System.out.printf("%s[%02d] %s%s\n", BLUE, c.getCodCurso(), c.getNomeCurso(), RESET);
		}

		do {
			System.out.print("Selecione o curso: ");
			codCurso = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (!(codigos.contains(codCurso)))
				System.out.printf("%s<!> Código inválido.%s\n", RED, RESET);
		} while (!(codigos.contains(codCurso)));

		cursoDAO.apagar(codCurso);
		System.out.printf("%s[!] Curso deletado%s\n", PURPLE, RESET);

	}

	public static void deletarDisciplina() {

		Scanner input = new Scanner(System.in);

		CursoDAO cursoDAO = new CursoDAO();
		DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
		CursoDisciplinaDAO cursoDisciplinaDAO = new CursoDisciplinaDAO();

		List<Curso> cursoList = cursoDAO.listar();
		List<CursoDisciplina> cursoDisciplinaList = cursoDisciplinaDAO.listar();

		List<Integer> codCursoList = new ArrayList<>();
		List<Integer> codDisciplinaList = new ArrayList<>();
		List<Integer> codigos = new ArrayList<>();

		int codDisciplina;

		System.out.printf("%s= CURSOS E DISCIPLINAS%s", BLUE, RESET);

		for (Curso c : cursoList) {
			if (!(codCursoList.contains(c.getCodCurso()))) {
				codCursoList.add(c.getCodCurso());
			}
		}

		for (CursoDisciplina cd : cursoDisciplinaList) {

			codigos.add(cd.getCodDisciplina());
			int x = 0;

			for (int codCurso : codCursoList) {
				if (codCurso == cd.getCodCurso()) {
					codDisciplinaList.add(cd.getCodDisciplina());
					x = 1;
				}
			}

			if (x == 1) {
				Curso c = cursoDAO.buscarPorId(cd.getCodCurso());
				System.out.printf("%s- %s%s\n", BLUE, c.getNomeCurso(), RESET);

				for (int cod : codDisciplinaList) {
					Disciplina d = disciplinaDAO.buscarPorId(cod);
					System.out.printf("  %s[%02d] %s%s\n", BLUE, d.getCodDisciplina(), d.getNome(), RESET);
				}
			}

		}

		do {
			System.out.print("Selecione a disciplina: ");
			codDisciplina = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (!(codigos.contains(codDisciplina)))
				System.out.printf("%s<!> Código inválido. Digite novamente%s\n", RED, RESET);
		} while (!(codigos.contains(codDisciplina)));

		disciplinaDAO.apagar(codDisciplina);
		cursoDisciplinaDAO.apagar(codDisciplina);

	}

	public static void deletarLivro() {

		Scanner input = new Scanner(System.in);

		LivroDAO livroDAO = new LivroDAO();
		List<Livro> livroList = livroDAO.listar();
		List<Integer> codigos = new ArrayList<>();

		int codLivro;

		System.out.printf("%s= LIVROS%s\n", BLUE, RESET);
		for (Livro l : livroList) {
			codigos.add(l.getCodLivro());
			System.out.printf("%s[%02d] %s%s\n", BLUE, l.getCodLivro(), l.getNome(), RESET);
		}

		do {
			System.out.print("Selecione o livro: ");
			codLivro = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (!(codigos.contains(codLivro)))
				System.out.printf("%s<!> Código inválido. Digite novamente%s\n", RED, RESET);
		} while (!(codigos.contains(codLivro)));

		livroDAO.apagar(codLivro);

	}

}