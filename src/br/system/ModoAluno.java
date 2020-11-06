package br.system;

import br.dao.*;
import br.model.*;

import java.sql.Timestamp;
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
            System.out.println(c.getNomeCurso());
        }

        System.out.println("Deseja ver as disciplinas de determinado curso? (1. Sim  0. Não");
        do {
            verDisciplinas = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
        } while (verDisciplinas < 0 || verDisciplinas > 1);

        if (verDisciplinas == 1) {

            System.out.print("Selecione o curso que deseja visualizar de acordo com o índice (0. Cancelar): ");
            do {
                selecionarCurso = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
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

        /* * * *
         * sout *
         * * * */
        for (Nota n : notas) {
            System.out.println(n.getCodNota());
        }

    }

    public static void reservarLivros(int codAluno) {

        Scanner input = new Scanner(System.in);

        ReservaLivroDAO reservaLivroDAO = new ReservaLivroDAO();
        LivroDAO livroDAO = new LivroDAO();
        List<Livro> livroList = livroDAO.listar();

        int codLivro;

        /* * * *
         * sout *
         * * * */
        for (Livro l : livroList) {
            System.out.println(l.getNome());
        }

        System.out.println("Escolha um livro para reservar (0. Cancelar): ");
        do {
            codLivro = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
        } while (codLivro < 0 || codLivro > livroList.size());

        if (codLivro != 0) {

            Livro livro = livroDAO.buscarPorId(codLivro);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 7);

            Date dataDevolucao = calendar.getTime();

            ReservaLivro reserva = new ReservaLivro(codAluno, codLivro, date, dataDevolucao);
            reservaLivroDAO.salvar(reserva);

            // sout done
            System.out.printf("Reserva Feita! %nLivro: %s [%04d] %nData de Emprestimo: %s %nData de Devolução: %s", livro.getNome(), livro.getCodLivro(), reserva.getDataEmprestimo(), reserva.getDataDevolucao());

        }

    }

    public static void agendamentoReuniao(int codAluno) {

        Scanner input = new Scanner(System.in);
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

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, dias);


            String dataHorario = dateFormat.format(calendar.getTime());
        }

    }

}
