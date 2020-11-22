package br.system;

import br.dao.*;
import br.model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ModoProfessor {

    public static final String RESET = "\u001B[0m";
    public static final String WHITE = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String BLACK = "\u001B[37m";

    public static void cadastroNotas() {

        Scanner input = new Scanner(System.in);

        NotaDAO notaDAO = new NotaDAO();
        CursoDAO cursoDAO = new CursoDAO();
        List<Curso> cursoList = cursoDAO.listar();
        List<Integer> codigos = new ArrayList<>();

        int codCurso;
        int codDisciplina;
        int codAluno;

        System.out.printf("%s= CURSOS%s\n", BLUE, BLACK);
        for (Curso c : cursoList) {
            codigos.add(c.getCodCurso());
            System.out.printf("%s[%02d] %s%s\n", BLUE, c.getCodCurso(), c.getNomeCurso(), RESET);
        }

        do {
            System.out.print("Selecione o curso: ");
            codCurso = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
            if (!(codigos.contains(codCurso)))
                System.out.printf("%s<!> Digite novamente%s\n", RED, RESET);
        } while (!(codigos.contains(codCurso)));

        codigos.clear();

        CursoDisciplinaDAO cursoDisciplinaDAO = new CursoDisciplinaDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        AlunoCursoDAO alunoCursoDAO = new AlunoCursoDAO();
        AlunoDAO alunoDAO = new AlunoDAO();

        List<CursoDisciplina> cursoDisciplinaList = cursoDisciplinaDAO.listar();
        List<AlunoCurso> alunoCursoList = alunoCursoDAO.listar();

        List<Disciplina> disciplinas = new ArrayList<>();
        List<Aluno> alunos = new ArrayList<>();

        for (CursoDisciplina c : cursoDisciplinaList) {
            if (c.getCodCurso() == codCurso) {
                int idDisciplina = c.getCodDisciplina();
                Disciplina disciplina = disciplinaDAO.buscarPorId(idDisciplina);
                disciplinas.add(disciplina);
            }
        }

        for (AlunoCurso a : alunoCursoList) {
            if (a.getCodCurso() == codCurso) {
                int idAluno = a.getCodAluno();
                Aluno aluno = alunoDAO.buscarPorId(idAluno);
                alunos.add(aluno);
            }
        }


        System.out.printf("%s= DISCIPLINA%s\n", BLUE, RESET);
        for (Disciplina d : disciplinas) {
            codigos.add(d.getCodDisciplina());
            System.out.printf("%s[%02d] %s %s\n", BLUE, d.getCodDisciplina(), d.getNome(), RESET);
        }

        do {
            System.out.print("Selecione a disciplina: ");
            codDisciplina = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
            if (!(codigos.contains(codDisciplina)))
                System.out.printf("%s<!> Digite novamente%s\n", RED, RESET);
        } while (!(codigos.contains(codDisciplina)));

        codigos.clear();

        System.out.printf("%s= ALUNOS%s\n", BLUE, RESET);
        for (Aluno a : alunos) {
            codigos.add(a.getCodAluno());
            System.out.printf("%s[%02d] %s %s\n", BLUE, a.getCodAluno(), a.getNome(), RESET);
        }

        do {
            System.out.print("Selecione o aluno: ");
            codAluno = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
            if (!(codigos.contains(codAluno)))
                System.out.printf("%s<!> Digite novamente%s\n", RED, RESET);
        } while (!(codigos.contains(codAluno)));

        System.out.printf("%sDigite a nota do aluno: %s", WHITE, RESET);
        double nota = Double.parseDouble(input.nextLine().replaceAll("[^\\d.,]+", ""));

        Nota cadastrarNota = new Nota(codAluno, codDisciplina, nota);
        notaDAO.salvar(cadastrarNota);

        System.out.printf("%s[!] Nota cadastrada%s\n", GREEN, RESET);

    }

    public static void consultarAlunos() {

        Scanner input = new Scanner(System.in);

        CursoDAO cursoDAO = new CursoDAO();
        List<Curso> cursoList = cursoDAO.listar();
        List<Integer> codigos = new ArrayList<>();
        int codCurso;

        System.out.printf("%s= CURSOS%s\n", BLUE, RESET);
        for (Curso c : cursoList) {
            codigos.add(c.getCodCurso());
            System.out.printf("%s[%02d] %s %s\n", BLUE, c.getCodCurso(), c.getNomeCurso(), RESET);
        }

        do {
            System.out.print("Selecione o curso: ");
            codCurso = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
            if (!(codigos.contains(codCurso)))
                System.out.printf("%s<!> Digite novamente%s\n", RED, RESET);
        } while (!(codigos.contains(codCurso)));

        AlunoCursoDAO alunoCursoDAO = new AlunoCursoDAO();
        AlunoDAO alunoDAO = new AlunoDAO();

        Curso curso = cursoDAO.buscarPorId(codCurso);
        List<AlunoCurso> alunoCursoList = alunoCursoDAO.listar();
        List<Integer> codAlunoList = new ArrayList<>();
        List<Aluno> alunoList = new ArrayList<>();

        for (AlunoCurso a : alunoCursoList) {
            if (a.getCodCurso() == codCurso) {
                codAlunoList.add(a.getCodAluno());
            }
        }

        for (int codAluno : codAlunoList) {
            alunoList.add(alunoDAO.buscarPorId(codAluno));
        }

        System.out.printf("%sLista de alunos do curso %s%s\n", CYAN, curso.getNomeCurso(), RESET);
        for (Aluno a : alunoList) {
            System.out.printf("%s- %04d, %s %s\n", CYAN, a.getCodAluno(), a.getNome(), RESET);
        }

    }

    public static void consultarAgendamento() {

        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
        List<Agendamento> agendamentoList = agendamentoDAO.listar();

        AlunoDAO alunoDAO = new AlunoDAO();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Date hoje = new Date();

        for (Agendamento a : agendamentoList) {

            Date horario = new Date();
            try {
                horario = dateFormat.parse(a.getHorario());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long diferenca = horario.getTime() - hoje.getTime();

            if (diferenca > 0) {

                Aluno aluno = alunoDAO.buscarPorId(a.getCodAluno());

                System.out.printf("%s= Agendamento %d\n" +
                        "  - Aluno: %s\n" +
                        "  - Horário: %s%s\n",
                        CYAN, a.getCodAgendamento(), aluno.getNome(), a.getHorario(), RESET);

            }

        }

    }

}
