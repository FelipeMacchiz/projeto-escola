package br.system;

import br.dao.*;
import br.model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public static void consultarAluno() {

        Scanner input = new Scanner(System.in);


        CursoDAO cursoDAO = new CursoDAO();
        List<Curso> cursoList = cursoDAO.listar();

        int codCurso;

        for (Curso c : cursoList) {
            System.out.println(c.getNomeCurso());
        }

        System.out.println("Selecione o Curso: (0. Cancelar): ");
        do {
            codCurso = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
        } while (codCurso < 0 || codCurso> cursoList.size());

        if (codCurso !=0){

            AlunoCursoDAO alunoCursoDAO = new AlunoCursoDAO();
            AlunoDAO alunoDAO = new AlunoDAO();
            List<AlunoCurso> alunoCursoList = alunoCursoDAO.listar();
            List<Integer> codAlunoList =new ArrayList<>();
            List<Aluno> alunoList = new ArrayList<>();

            for (AlunoCurso a : alunoCursoList) {
                if (a.getCodCurso()==codCurso){
                    codAlunoList.add(a.getCodAluno());
                }
            }

            for (int codAluno:codAlunoList) {
                alunoList.add(alunoDAO.buscarPorId(codAluno));
            }

            for (Aluno a: alunoList) {
                System.out.printf("- %03d, %s %n",a.getCodAluno(),a.getNome());
            }
        }
    }
    public static void CosultaAgendamento () {

        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
        List<Agendamento> agendamentosList = agendamentoDAO.listar();
        AlunoDAO alunoDAO = new AlunoDAO();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date hoje = new Date();

        for (Agendamento a: agendamentosList) {
            Date horario = new Date();
            try {
                horario = dateFormat.parse(a.getHorario());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long diferenca = horario.getTime()-hoje.getTime();
            if (diferenca > 0){
                Aluno aluno = alunoDAO.buscarPorId(a.getCodAluno());
                System.out.printf("Agendamento %d\naluno: %s\n horario:%s\n",a.getCodAgendamento(),aluno.getNome(),a.getHorario());
            }
        }
    }


}
