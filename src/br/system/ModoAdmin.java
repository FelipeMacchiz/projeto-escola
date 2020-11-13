package br.system;

import br.dao.DepFuncionarioDAO;
import br.dao.DepartamentoDAO;
import br.dao.FuncionarioDAO;
import br.model.DepFuncionario;
import br.model.Departamento;
import br.model.Funcionario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
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


	public static void cadastroFuncionario() {

		Scanner input = new Scanner(System.in);

		System.out.println("----| BEM VINDO A AREA DE CADASTRO DO FUNCIONÁRIO |----");

		System.out.println("Digite o nome do seu funcionário :)");
		String nome = input.nextLine();

		System.out.println("Digite o CPF do seu funcionário ><");
		String CPF = input.nextLine();

		System.out.println("Digite o RG do seu funcionário -_-");
		String RG = input.nextLine();

		System.out.println("Digite a data de nascimento do seu funcionário ;)");
		int dia;
		int mes;
		int ano;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int anoHoje = calendar.get(Calendar.YEAR);

		do {
			System.out.println("DIA");
			dia = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", "")) ;
			if (dia > 30 || dia < 1){
				System.out.println("Número invalido");
			}
		}while(dia > 30 || dia < 1);

		do {
			System.out.println("MÈS");
			mes = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (mes < 1 || mes > 12){
				System.out.println("Número invalido");
			}
		}while(mes < 1 || mes > 12);

		do {
			System.out.println("ANO");
			ano = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
			if (ano > anoHoje){
				System.out.println("Número invalido");
			}
		}while( ano > anoHoje);

		DepartamentoDAO departamentoDAO = new DepartamentoDAO();
		List<Departamento> departamentoList = departamentoDAO.listar();

		System.out.println("----|SELECIONE O DEPARTAMENTO DO SEU FUNCIONÁRIO|----");

		for (Departamento d : departamentoList) {
			System.out.printf("[%2d] %s %n", d.getCodDepartamento(), d.getDescricao());
		}
		int cod;
		do {
			System.out.println("Digite o código da area");
			cod = input.nextInt();
		}while( cod < 0 || cod > departamentoList.size());


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
		depFuncionarioDAO.salvar(new DepFuncionario(cod));

		System.out.println("PROCESSO CONCLUÍDO COM SUCESSO");
	}

}
