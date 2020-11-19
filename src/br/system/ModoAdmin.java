package br.system;

import br.dao.*;
import br.model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class ModoAdmin {

	/*
	- cadastroFuncionario - nome, cpf, rg, nasc
	- cadastroCurso
	- cadastroDisciplina
	- cadastroLivro
	- cadastroServico
	*/

	public static final String RESET = "\u001B[0m";
	public static final String BLACK = "\u001B[30m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";


	public static void cadastroFuncionario() {

		Scanner input = new Scanner(System.in);

		System.out.printf("%s= CADASTRO DO FUNCIONÁRIO%s\n", BLUE, RESET);

		System.out.print("- Nome: ");
		String nome = input.nextLine();

		System.out.print("- CPF: ");
		String CPF = input.nextLine();

		System.out.print("- RG: ");
		String RG = input.nextLine();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int anoHoje = calendar.get(Calendar.YEAR);
		int dia;
		int mes;
		int ano;

		System.out.printf("%s- Data de nascimento%s", YELLOW, RESET);
		do {
			System.out.print("Dia: ");
			dia = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (dia < 1 || dia > 31) {
				System.out.printf("%s<!> Dia do mês invalido. Digite novamente%s\n", RED, RESET);
			}
		} while (dia < 1 || dia > 31);

		do {
			System.out.println("Mês: ");
			mes = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (mes < 1 || mes > 12) {
				System.out.printf("%s<!> Mês invalido. Digite novamente%s\n", RED, RESET);
			}
		} while (mes < 1 || mes > 12);

		do {
			System.out.println("Ano: ");
			ano = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (ano > anoHoje) {
				System.out.printf("%s<!> Ano invalido. Digite novamente%s\n", RED, RESET);
			}
		} while (ano > anoHoje);

		DepartamentoDAO departamentoDAO = new DepartamentoDAO();
		List<Departamento> departamentoList = departamentoDAO.listar();

		System.out.printf("%s= DEPARTAMENTO%s\n", BLUE, RESET);
		for (Departamento d : departamentoList) {
			System.out.printf("%s[%2d] %s %s\n", BLUE, d.getCodDepartamento(), d.getDescricao(), RESET);
		}

		int codDepartamento;
		do {
			System.out.print("Digite o código do departamento: ");
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
		Funcionario funcionario = new Funcionario(nome, RG, CPF, nasc);
		funcionarioDAO.salvar(funcionario);

		DepFuncionarioDAO depFuncionarioDAO = new DepFuncionarioDAO();
		depFuncionarioDAO.salvar(new DepFuncionario(codDepartamento));

		System.out.printf("%sCadastro concluído com sucesso!%s\n", CYAN, RESET);

	}

	public static void cadastroCurso() {

		Scanner input = new Scanner(System.in);

		System.out.println("----|BEM VINDO AO CADASTRO DO CURSO|----");

		System.out.println("Informe o nome do curso");
		String nomeCurso = input.nextLine();

		System.out.println("Qual a duração desse curso (em horas)");
		int duracao = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));

		CursoDAO cursoDAO = new CursoDAO();
		Curso curso = new Curso(nomeCurso, duracao);
		cursoDAO.salvar(curso);

	}

	public static void cadastroDisciplina(){

		Scanner input = new Scanner(System.in);

		System.out.println("----|BEM VINDO AO CADASTRO DA DISCIPLINA|----");

		System.out.println("Informe o nome da disciplina");
		String nome = input.nextLine();

		CursoDAO cursoDAO = new CursoDAO();
		List<Curso> cursoList = cursoDAO.listar();
		for (Curso c : cursoList) {
			System.out.printf("[%2d] %s %n", c.getCodCurso(), c.getNomeCurso());
		}

		System.out.println("----|ESCOLHA SEU CURSO|----");
		int cod = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));

		DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
		Disciplina disciplina = new Disciplina();
		disciplinaDAO.salvar(disciplina);

		CursoDisciplinaDAO cursoDisciplinaDAO = new CursoDisciplinaDAO();
		CursoDisciplina cursoDisciplina = new CursoDisciplina(cod);
		cursoDisciplinaDAO.salvar(cursoDisciplina);

	}

	public static void cadastroLivro(){

		Scanner input = new Scanner(System.in);

		System.out.println("----|BEM VINDO A AREÁ DE CADASTRO DO LIVRO|----");

		System.out.println("Informe o nome do livro");
		String nome = input.nextLine();

		LivroDAO livroDAO = new LivroDAO();
		Livro livro = new Livro(nome);
		livroDAO.salvar(livro);

	}


	public static void atualizarFuncionario() {

		Scanner input = new Scanner(System.in);
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		Funcionario funcionario = funcionarioDAO.buscarPorId(1);

		System.out.printf("%s= Atualize os dados de %s (<!> Para manter os dados atuais, deixe em branco)%s\n",
				PURPLE, funcionario.getNome(), RESET);
		System.out.printf("%s| Nome: %s %s\nNovo nome: ", YELLOW, funcionario.getNome(), RESET);
		String nome = input.nextLine();
		if (nome.equals("")){
			nome = funcionario.getNome();
		}

		System.out.printf("%s| RG: %s %s\nNovo RG: ",  YELLOW, funcionario.getRg(), RESET);
		String rg = input.nextLine();
		if (rg.equals("")) {
			rg = funcionario.getRg();
		}

		System.out.printf("%s| CPF: %s %s\nNovo CPF: ", YELLOW, funcionario.getCpf(), RESET);
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
			System.out.print("- Dia: ");
			dia = input.nextLine().replaceAll("[^0-9]", "");
			if (dia.equals("")) {
				dia = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			}
		} while (Integer.parseInt(dia) < 1 || Integer.parseInt(dia) > 31);

		String mes;
		do {
			System.out.print("- Mês: ");
			mes = input.nextLine().replaceAll("[^0-9]", "");
			if (mes.equals("")) {
				mes = String.valueOf(calendar.get(Calendar.MONTH) + 1);
			}
		} while (Integer.parseInt(mes) < 1 || Integer.parseInt(mes) > 12);

		String ano;
		do {
			System.out.print("- Ano: ");
			ano = input.nextLine().replaceAll("[^0-9]", "");
			if (ano.equals("")) {
				ano = String.valueOf(calendar.get(Calendar.YEAR));
			}
		} while (Integer.parseInt(ano) > hoje.get(Calendar.YEAR));

		nasc = String.format("%s/%s/%s", dia, mes, ano);

		Funcionario funcionarioAtualizado = new Funcionario(1, nome, rg, cpf, nasc);
		funcionarioDAO.atualizar(funcionarioAtualizado);

		System.out.printf("%s= ALUNO ATUALIZADO! \n" +
						"Matrícula: %08d\n" +
						"Nome: %s\n" +
						"RG: %s\n" +
						"CPF: %s\n" +
						"Data de Nascimento: %s %s\n",
				CYAN, funcionario.getCodFuncionario(), funcionario.getNome(), funcionario.getRg(), funcionario.getCpf(), funcionario.getNasc(), RESET);

	}

	public static void atualizarCurso() {

	}

	public static void atualizarDisciplina() {

	}

	public static void atualizarLivro() {

	}


	public static void deletarFuncionario() {

	}

	public static void deletarCurso() {

	}

	public static void deletarDisciplina() {

	}

	public static void deletarLivro() {

	}

}