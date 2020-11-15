package br.system;

import br.dao.*;
import br.model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ModoAluno {

    public static void consultaCursos() {

        Scanner input = new Scanner(System.in);

        CursoDAO cursoDAO = new CursoDAO();
        List<Curso> cursoList = cursoDAO.listar();

        int verDisciplinas;
        int selecionarCurso;

        /* * * *
        * sout *
        * * * */
        for (Curso c : cursoList) {
            System.out.printf("[%02d] %s %n", c.getCodCurso(), c.getNomeCurso());
        }

        do {
            System.out.print("Deseja ver as disciplinas de determinado curso (1. Sim  0. Não): ");
            verDisciplinas = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
            if (verDisciplinas < 0 || verDisciplinas > 1)
                System.out.println("Digite novamente.");
        } while (verDisciplinas < 0 || verDisciplinas > 1);

        if (verDisciplinas == 1) {

            do {
                System.out.print("Selecione o curso que deseja visualizar de acordo com o índice (0. Cancelar): ");
                selecionarCurso = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
                if (selecionarCurso < 0 || selecionarCurso > cursoList.size())
                    System.out.println("Digite novamente.");
            } while (selecionarCurso < 0 || selecionarCurso > cursoList.size());

            if (selecionarCurso != 0) {

                CursoDisciplinaDAO cursoDisciplinaDAO = new CursoDisciplinaDAO();
                DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

                List<CursoDisciplina> cursoDisciplinaList = cursoDisciplinaDAO.listar();

                List<Disciplina> disciplinasCurso = new ArrayList<>();

                for (CursoDisciplina c : cursoDisciplinaList) {

                    if (c.getCodCurso() == selecionarCurso) {
                        Disciplina disciplina = disciplinaDAO.buscarPorId(c.getCodDisciplina());
                        disciplinasCurso.add(disciplina);
                    }

                }

                /* * * *
                 * sout *
                 * * * */
                for (Disciplina d : disciplinasCurso) {
                    System.out.println(d.getNome());
                }

            }

        }

    }

    public static void consultaNotas(int codAluno) {

        AlunoCursoDAO alunoCursoDAO = new AlunoCursoDAO();
        CursoDisciplinaDAO cursoDisciplinaDAO = new CursoDisciplinaDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        NotaDAO notaDAO = new NotaDAO();

        List<AlunoCurso> alunoCursoList = alunoCursoDAO.listar();
        List<CursoDisciplina> cursoDisciplinaList = cursoDisciplinaDAO.listar();
        List<Nota> notaList = notaDAO.listar();

        List<Disciplina> disciplinas = new ArrayList<>();
        List<Nota> notas = new ArrayList<>();
        int codCurso = 0;

        for (AlunoCurso a : alunoCursoList) {
            if (a.getCodAluno() == codAluno) {
                codCurso = a.getCodCurso();
                break;
            }
        }

        for (CursoDisciplina c : cursoDisciplinaList) {
            if (c.getCodCurso() == codCurso) {
                Disciplina disciplina = disciplinaDAO.buscarPorId(c.getCodDisciplina());
                disciplinas.add(disciplina);
            }
        }

        for (Nota n : notaList) {
            for (Disciplina d : disciplinas) {
                if (n.getCodAluno() == codAluno) {
                    notas.add(n);
                }
            }
        }

        // sout done
        for (Nota n : notas) {
            System.out.printf("- %.1f = %d %n", n.getNota(), n.getCodDisciplina());
        }

    }

    public static void reservarLivros(int codAluno) {

        Scanner input = new Scanner(System.in);

        ReservaLivroDAO reservaLivroDAO = new ReservaLivroDAO();
        LivroDAO livroDAO = new LivroDAO();
        List<Livro> livroList = livroDAO.listar();

        int codLivro;

        // sout done
        for (Livro l : livroList) {
            System.out.printf("[%02d] %s %n", l.getCodLivro(), l.getNome());
        }

        System.out.println("Escolha um livro para reservar (0. Cancelar): ");
        do {
            codLivro = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
        } while (codLivro < 0 || codLivro > livroList.size());

        if (codLivro != 0) {

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
            System.out.printf("Reserva Feita! %nLivro: %s [%04d] %nData de Emprestimo: %s %nData de Devolução: %s", livro.getNome(), livro.getCodLivro(), reserva.getDataEmprestimo(), reserva.getDataDevolucao());

        }

    }

    public static void agendamentoReuniao(int codAluno) {

        Scanner input = new Scanner(System.in);
        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
        int data;
        int hora;
        int dias = 0;

        System.out.println("Para que dia deseja marcar o atendimento? ");
        System.out.println("[1] Amanhã\n[2] Daqui uma semana\n[3] Daqui duas semanas\n[0] Cancelar");
        do {
            data = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
        } while (data < 0 || data > 3);

        if (data != 0) {
            switch (data) {
                case 1 -> dias = 1;
                case 2 -> dias = 7;
                case 3 -> dias = 14;

            }

            System.out.println("Qual horário?");
            System.out.println("[1] Manhã\n[2] Tarde\n[3] Noite");
            do {
                hora = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
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

            Agendamento agendamento = new Agendamento(1, codAluno, dataHorario);
            agendamentoDAO.salvar(agendamento);

            System.out.printf("Agendamento marcado para %s!%n", dataHorario);

        }

    }

    public static void atualizarDados(int codAluno) {

        Scanner input = new Scanner(System.in);
        AlunoDAO alunoDAO = new AlunoDAO();
        Aluno aluno = alunoDAO.buscarPorId(codAluno);

        System.out.printf("Atualize os dados de %s (<!> Para manter os dados atuais, deixe em branco)%n", aluno.getNome());
        System.out.printf("| Nome: %s %nNovo nome: ", aluno.getNome());
        String nome = input.nextLine();
        if (nome.equals("")){
            nome = aluno.getNome();
        }

        System.out.printf("| RG: %s %nNovo RG: ", aluno.getRg());
        String rg = input.nextLine();
        if (rg.equals("")) {
            rg = aluno.getRg();
        }

        System.out.printf("| CPF: %s %nNovo CPF: ", aluno.getCpf());
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

        System.out.printf("| Data de Nascimento: %s %n", aluno.getNasc());
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

        System.out.printf("-x- ALUNO ATUALIZADO! -x-%n" +
                "Matrícula: %08d%n" +
                "Nome: %s%n" +
                "RG: %s%n" +
                "CPF: %s%n" +
                "Data de Nascimento: %s%n",
                aluno.getCodAluno(), aluno.getNome(), aluno.getRg(), aluno.getCpf(), aluno.getNasc());

    }

}
