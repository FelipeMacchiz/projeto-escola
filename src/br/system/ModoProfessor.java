package br.system;

import br.dao.*;
import br.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ModoProfessor {

    public static void cadastroNotas() {

        Scanner input = new Scanner(System.in);

        NotaDAO notaDAO = new NotaDAO();
        CursoDAO cursoDAO = new CursoDAO();
        List<Curso> cursoList = cursoDAO.listar();

        int codCurso;

        for (Curso c : cursoList) {
            System.out.println(c.getNomeCurso());
        }

        System.out.println("Selecione o curso (0. Cancelar): ");
        do {
            codCurso = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
        } while (codCurso < 0 || codCurso > cursoList.size());

        if (codCurso != 0) {

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

            for (Disciplina d : disciplinas) {
                System.out.printf("[%02d] %s %n", d.getCodDisciplina(), d.getNome());
            }

            System.out.println("Selecione a disciplina (0. Cancelar)");
            int codDisciplina = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));

            if (codDisciplina != 0) {

                for (Aluno a : alunos) {
                    System.out.printf("[%02d] %s %n", a.getCodAluno(), a.getNome());
                }

                System.out.println("Selecione o aluno (0. Cancelar)");
                int codAluno = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));

                if (codAluno != 0) {

                    System.out.print("Digite a nota do aluno: ");
                    double nota = Double.parseDouble(input.nextLine().replaceAll("[^0-9]", ""));

                    Nota cadastrarNota = new Nota(codAluno, codDisciplina, nota);
                    notaDAO.salvar(cadastrarNota);

                }

            }

        }

    }

    public static void consultarAlunos() {

        Scanner input = new Scanner(System.in);

        CursoDAO cursoDAO = new CursoDAO();
        List<Curso> cursoList = cursoDAO.listar();
        int codCurso;

        for (Curso c : cursoList) {
            System.out.printf("[%2d] %s %n", c.getCodCurso(), c.getNomeCurso());
        }
        System.out.println("[00] Cancelar");

        do {
            System.out.print("Selecione o curso: ");
            codCurso = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
        } while (codCurso < 0 || codCurso > cursoList.size());

        if (codCurso != 0) {

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

            System.out.println("Lista de alunos do curso " + curso.getNomeCurso());
            for (Aluno a : alunoList) {
                System.out.printf("- %03d, %s %n", a.getCodAluno(), a.getNome());
            }

        }

    }

}
