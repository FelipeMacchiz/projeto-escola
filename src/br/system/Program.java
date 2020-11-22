package br.system;

import br.dao.AlunoDAO;
import br.model.Aluno;

import java.util.List;
import java.util.Scanner;

public class Program {

    public static final String RESET = "\u001B[0m";
    public static final String WHITE = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BLACK = "\u001B[37m";

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int modo;

        // escolha do modo
        System.out.printf("%s= MODOS\n" +
                        "[1] Aluno\n" +
                        "[2] Não Aluno\n" +
                        "[3] Professor\n" +
                        "[4] Admin%s\n",
                BLUE, RESET);
        do {
            System.out.print("Selecione o modo: ");
            modo = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
            if (modo < 1 || modo > 4)
                System.out.printf("%s<!> Modo inválido. Digite novamente%s\n", RED, RESET);
        } while (modo < 1 || modo > 4);

        switch (modo) {
            case 1 -> modoAluno();
            case 2 -> modoNaoAluno();
            case 3 -> modoProfessor();
            case 4 -> modoAdmin();
        }

    }

    public static void modoAluno() {

        Scanner input = new Scanner(System.in);
        AlunoDAO alunoDAO = new AlunoDAO();

        List<Aluno> alunoList = alunoDAO.listar();
        Aluno aluno;
        int codAluno;

        int confirmar;
        int servico;
        int continuar;

        do {
            do {
                System.out.printf("%sInforme a sua matrícula: %s", WHITE, RESET);
                codAluno = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
                if (codAluno < 1 || codAluno >= alunoList.size())
                    System.out.printf("%s<!> Matrícula inválida. Digite novamente%s\n", RED, RESET);
            } while (codAluno < 1 || codAluno >= alunoList.size());

            aluno = alunoDAO.buscarPorId(codAluno);
            System.out.printf("%sVocê é %s? [1] Confirmar [0] Cancelar%s\n", YELLOW, aluno.getNome(), RESET);
            do {
                System.out.print("Selecione: ");
                confirmar = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
                if (confirmar < 0 || confirmar > 1)
                    System.out.printf("%s<!> Digite novamente%s\n", RED, RESET);
            } while (confirmar < 0 || confirmar > 1);

        } while (confirmar != 1);

        do {
            System.out.printf("%s= SERVIÇOS E CONSULTAS\n" +
                    "[1] Consultar os cursos\n" +
                    "[2] Verificar notas\n" +
                    "[3] Reservar livros na biblioteca\n" +
                    "[4] Agendar uma reunião\n" +
                    "[5] Atualizar dados pessoais%s\n",
                    BLUE, RESET);
            do {
                System.out.print("Selecione o serviço: ");
                servico = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
                if (servico < 1 || servico > 5)
                    System.out.printf("%s<!> Serviço não encontrado. Digite novamente%s\n", RED, RESET);
            } while (servico < 1 || servico > 5);

            switch (servico) {
                case 1 -> ModoNaoAluno.consultaCursos();
                case 2 -> ModoAluno.consultaNotas(codAluno);
                case 3 -> ModoAluno.reservarLivros(codAluno);
                case 4 -> ModoAluno.agendamentoReuniao(codAluno);
                case 5 -> ModoAluno.atualizarDados(codAluno);
            }

            System.out.printf("%sDeseja realizar mais algum serviço? [1] Sim  [0] Não%s\n", YELLOW, RESET);
            do {
                System.out.print("Selecione: ");
                continuar = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
                if (continuar < 0 || continuar > 1)
                    System.out.printf("%s<!> Digite novamente%s\n", RED, RESET);
            } while (continuar < 0 || continuar > 1);

        } while (continuar == 1);

    }

    public static void modoNaoAluno() {

        Scanner input = new Scanner(System.in);

        int servico;
        int continuar;

        System.out.printf("%s-x- BEM VINDO! -x-%s\n", YELLOW, RESET);
        do {
            System.out.printf("%s= SERVIÇOS E CONSULTAS\n" +
                    "[1] Cadastro\n" +
                    "[2] Ver cursos%s\n",
                    BLUE, RESET);
            do {
                System.out.print("Selecione: ");
                servico = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
                if (servico < 1 || servico > 2)
                    System.out.printf("%s<!> Digite novamente.%s\n", RED, RESET);
            } while (servico < 1 || servico > 2);

            switch (servico) {
                case 1 -> ModoNaoAluno.cadastroAluno();
                case 2 -> ModoNaoAluno.consultaCursos();
            }

            System.out.printf("%sDeseja realizar mais algum serviço? [1] Sim  [0] Não%s\n", YELLOW, RESET);
            do {
                System.out.print("Selecione: ");
                continuar = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
                if (continuar < 0 || continuar > 1)
                    System.out.printf("%s<!> Digite novamente%s\n", RED, RESET);
            } while (continuar < 0 || continuar > 1);


        } while (continuar == 1);

    }

    public static void modoProfessor() {

        Scanner input = new Scanner(System.in);

        int servico;
        int continuar;

        do {
            System.out.printf("%s= SERVIÇOS E CONSULTAS\n" +
                    "[1] Cadastrar Notas\n" +
                    "[2] Ver alunos\n" +
                    "[3] Ver agendamentos marcados%s\n", BLUE, RESET);
            do {
                System.out.print("Selecione: ");
                servico = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
                if (servico < 1 || servico > 3)
                    System.out.printf("%s<!> Digite novamente.%s\n", RED, RESET);
            } while (servico < 1 || servico > 3);

            switch (servico) {
                case 1 -> ModoProfessor.cadastroNotas();
                case 2 -> ModoProfessor.consultarAlunos();
                case 3 -> ModoProfessor.consultarAgendamento();
            }

            System.out.printf("%sDeseja realizar mais algum serviço? [1] Sim  [0] Não%s\n", YELLOW, RESET);
            do {
                System.out.print("Selecione: ");
                continuar = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
                if (continuar < 0 || continuar > 1)
                    System.out.printf("%s<!> Digite novamente%s\n", RED, RESET);
            } while (continuar < 0 || continuar > 1);

        } while (continuar == 1);

    }

    public static void modoAdmin() {

        Scanner input = new Scanner(System.in);

        int servico;
        int modoEdicao;
        int continuar;

        do {
            System.out.printf("%s= SERVIÇOS\n" +
                    "[1] Editar Funcionários\n" +
                    "[2] Editar Cursos\n" +
                    "[3] Editar Disciplinas\n" +
                    "[4] Editar Livros%s\n", BLUE, RESET);

            do {
                System.out.print("Selecione: ");
                servico = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
                if (servico < 1 || servico > 4)
                    System.out.printf("%s<!> Digite novamente%s\n", RED, RESET);
            } while (servico < 1 || servico > 4);

            switch (servico) {
                case 1 -> {
                    modoEdicao = escolherModo();
                    switch (modoEdicao) {
                        case 1 -> ModoAdmin.cadastroFuncionario();
                        case 2 -> ModoAdmin.atualizarFuncionario();
                        case 3 -> ModoAdmin.deletarFuncionario();
                    }
                }
                case 2 -> {
                    modoEdicao = escolherModo();
                    switch (modoEdicao) {
                        case 1 -> ModoAdmin.cadastroCurso();
                        case 2 -> ModoAdmin.atualizarCurso();
                        case 3 -> ModoAdmin.deletarCurso();
                    }
                }
                case 3 -> {
                    modoEdicao = escolherModo();
                    switch (modoEdicao) {
                        case 1 -> ModoAdmin.cadastroDisciplina();
                        case 2 -> ModoAdmin.atualizarDisciplina();
                        case 3 -> ModoAdmin.deletarDisciplina();
                    }
                }
                case 4 -> {
                    modoEdicao = escolherModo();
                    switch (modoEdicao) {
                        case 1 -> ModoAdmin.cadastroLivro();
                        case 2 -> ModoAdmin.atualizarLivro();
                        case 3 -> ModoAdmin.deletarLivro();
                    }
                }
            }

            System.out.printf("%sDeseja realizar mais algum serviço? [1] Sim  [0] Não%s\n", YELLOW, RESET);
            do {
                System.out.print("Selecione: ");
                continuar = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
                if (continuar < 0 || continuar > 1)
                    System.out.printf("%s<!> Digite novamente%s\n", RED, RESET);
            } while (continuar < 0 || continuar > 1);

        } while (continuar == 1);

    }

    public static int escolherModo() {

        Scanner input = new Scanner(System.in);

        int modoEdicao;

        System.out.printf("%s= MODO DE EDIÇÃO\n" +
                "[1] Cadastrar\n" +
                "[2] Atualizar\n" +
                "[3] Deletar%s\n", BLUE, RESET);

        do {
            System.out.print("Selecione: ");
            modoEdicao = Integer.parseInt(input.nextLine().replaceAll("[^0-9]", ""));
            if (modoEdicao < 1 || modoEdicao > 3)
                System.out.printf("%s<!> Digite novamente%s\n", RED, RESET);
        } while (modoEdicao < 1 || modoEdicao > 3);

        return modoEdicao;

    }

}
