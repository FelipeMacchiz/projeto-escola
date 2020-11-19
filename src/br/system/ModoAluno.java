package br.system;

import br.dao.*;
import br.model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ModoAluno {

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void consultaCursos() {

        Scanner input = new Scanner(System.in);

        CursoDAO cursoDAO = new CursoDAO();
        List<Curso> cursoList = cursoDAO.listar();

        int verDisciplinas;
        int codCurso;

        System.out.printf("%s= CURSOS%s\n", CYAN, RESET);
        for (Curso c : cursoList) {
            System.out.printf("%s[%02d] %s %s\n", CYAN, c.getCodCurso(), c.getNomeCurso(), RESET);
        }

        System.out.printf("%sDeseja ver mais detalhes de determinado curso? [1] Sim  [0] Não%s%n", YELLOW, RESET);
        do {
            System.out.print("Selecione: ");
            verDisciplinas = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
            if (verDisciplinas < 0 || verDisciplinas > 1)
                System.out.printf("%s<!> Digite novamente%s\n", RED, RESET);
        } while (verDisciplinas < 0 || verDisciplinas > 1);

        if (verDisciplinas == 1) {

            do {
                System.out.print("Selecione o curso: ");
                codCurso = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
                if (codCurso < 0 || codCurso > cursoList.size())
                    System.out.printf("%s<!> Digite novamente%s\n", RED, RESET);
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

            System.out.printf("%n%s= NOME DO CURSO: %s\n" +
                    "DURAÇÃO: %s anos\n" +
                    "DISCIPLINAS: %s\n",
                    CYAN, curso.getNomeCurso(), curso.getDuracao(), RESET);
            for (Disciplina d : disciplinasCurso) {
                System.out.printf("%s- %s%s\n", CYAN, d.getNome(), RESET);
            }

        }

    }

    public static void consultaNotas(int codAluno) {

        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        NotaDAO notaDAO = new NotaDAO();

        List<Nota> notaList = notaDAO.listar();
        List<Nota> notas = new ArrayList<>();

        for (Nota n : notaList) {
            if (n.getCodAluno() == codAluno) {
                    notas.add(n);
            }
        }

        System.out.printf("%s= SUAS NOTAS%s\n", CYAN, RESET);
        for (Nota n : notas) {
            Disciplina d = disciplinaDAO.buscarPorId(n.getCodDisciplina());
            System.out.printf("%s- %s: %.1f %s\n", CYAN, d.getNome(), n.getNota(), RESET);
        }

    }

    public static void reservarLivros(int codAluno) {

        Scanner input = new Scanner(System.in);

        ReservaLivroDAO reservaLivroDAO = new ReservaLivroDAO();
        LivroDAO livroDAO = new LivroDAO();
        List<Livro> livroList = livroDAO.listar();

        int codLivro;

        System.out.printf("%s= LIVROS%s\n", BLUE, RESET);
        for (Livro l : livroList) {
            System.out.printf("%s[%02d] %s %s\n", BLUE, l.getCodLivro(), l.getNome(), RESET);
        }

        do {
            System.out.print("Escolha um livro para reservar: ");
            codLivro = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
            if (codLivro < 1 || codLivro > livroList.size())
                System.out.printf("%s<!> Código incorreto. Digite novamente%s\n", RED, RESET);
        } while (codLivro < 1 || codLivro > livroList.size());

        Livro livro = livroDAO.buscarPorId(codLivro);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);

        String dataEmprestimo = dateFormat.format(date);
        String dataDevolucao = dateFormat.format(calendar.getTime());

        ReservaLivro reserva = new ReservaLivro(codAluno, codLivro, dataEmprestimo, dataDevolucao);
        reservaLivroDAO.salvar(reserva);

        // sout done
        System.out.printf("%sReserva Feita! \n" +
                "Livro: %s [%04d] \n" +
                "Data de Emprestimo: %s \n" +
                "Data de Devolução: %s%s\n",
                CYAN, livro.getNome(), livro.getCodLivro(), reserva.getDataEmprestimo(), reserva.getDataDevolucao(), RESET);

    }

    public static void agendamentoReuniao(int codAluno) {

        Scanner input = new Scanner(System.in);
        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
        int data;
        int hora;
        int dias = 0;

        System.out.printf("%s= DATA DO ATENDIMENTO" +
                "[1] Amanhã\n" +
                "[2] Daqui uma semana\n" +
                "[3] Daqui duas semanas\n" +
                "[0] Cancelar%s\n",
                BLUE, RESET);
        do {
            System.out.print("Digite para quando deseja marcar o atendimento: ");
            data = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
            if (data < 0 || data > 3)
                System.out.printf("%s<!> Número inválido. Digite novamente%s\n", RED, RESET);
        } while (data < 0 || data > 3);

        if (data != 0) {
            switch (data) {
                case 1 -> dias = 1;
                case 2 -> dias = 7;
                case 3 -> dias = 14;

            }

            System.out.printf("%s= HORÁRIOS DISPONÍVEIS\n" +
                    "[1] Manhã\n" +
                    "[2] Tarde\n" +
                    "[3] Noite%s\n",
                    BLUE, RESET);
            do {
                System.out.print("Selecione um horário: ");
                hora = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
                if (hora < 1 || hora > 3)
                    System.out.printf("%s<!> Número inválido. Digite novamente%s\n", RED, RESET);
            } while (hora < 1 || hora > 3);

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, dias);
            switch (hora) {
                case 1 -> calendar.set(Calendar.HOUR_OF_DAY, 9);
                case 2 -> calendar.set(Calendar.HOUR_OF_DAY, 16);
                case 3 -> calendar.set(Calendar.HOUR_OF_DAY, 19);
            }
            calendar.set(Calendar.MINUTE, 0);

            String dataHorario = dateFormat.format(calendar.getTime());

            Agendamento agendamento = new Agendamento(codAluno, dataHorario);
            agendamentoDAO.salvar(agendamento);

            System.out.printf("%s= Agendamento marcado para %s!%s\n", CYAN, dataHorario, RESET);

        }

    }

    public static void atualizarDados(int codAluno) {

        Scanner input = new Scanner(System.in);
        AlunoDAO alunoDAO = new AlunoDAO();
        Aluno aluno = alunoDAO.buscarPorId(codAluno);

        System.out.printf("%s= Atualize os dados de %s (<!> Para manter os dados atuais, deixe em branco)%s\n",
                PURPLE, aluno.getNome(), RESET);
        System.out.printf("%s| Nome: %s %s\nNovo nome: ", YELLOW, aluno.getNome(), RESET);
        String nome = input.nextLine();
        if (nome.equals("")){
            nome = aluno.getNome();
        }

        System.out.printf("%s| RG: %s %s\nNovo RG: ",  YELLOW, aluno.getRg(), RESET);
        String rg = input.nextLine();
        if (rg.equals("")) {
            rg = aluno.getRg();
        }

        System.out.printf("%s| CPF: %s %s\nNovo CPF: ", YELLOW, aluno.getCpf(), RESET);
        String cpf = input.nextLine();
        if (cpf.equals("")) {
            cpf = aluno.getCpf();
        }

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String nasc = aluno.getNasc();
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

        System.out.printf("%s| Data de Nascimento: %s %s\n", YELLOW, aluno.getNasc(), RESET);
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

        Aluno alunoAtualizado = new Aluno(codAluno, nome, rg, cpf, nasc);
        alunoDAO.atualizar(alunoAtualizado);

        System.out.printf("%s= ALUNO ATUALIZADO! \n" +
                "Matrícula: %08d\n" +
                "Nome: %s\n" +
                "RG: %s\n" +
                "CPF: %s\n" +
                "Data de Nascimento: %s %s\n",
                CYAN, aluno.getCodAluno(), aluno.getNome(), aluno.getRg(), aluno.getCpf(), aluno.getNasc(), RESET);

    }

}
