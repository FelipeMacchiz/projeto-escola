BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "notas" (
	"codNotas"	INTEGER NOT NULL,
	"codAluno"	INTEGER NOT NULL,
	"notas"	REAL NOT NULL,
	PRIMARY KEY("codNotas" AUTOINCREMENT),
	FOREIGN KEY("codAluno") REFERENCES "aluno"("codCurso")
);
CREATE TABLE IF NOT EXISTS "alunoCurso" (
	"codAlunoCurso"	INTEGER NOT NULL,
	"codAluno"	INTEGER NOT NULL,
	"codCurso"	INTEGER NOT NULL,
	PRIMARY KEY("codAlunoCurso" AUTOINCREMENT),
	FOREIGN KEY("codAluno") REFERENCES "aluno"("codCurso"),
	FOREIGN KEY("codCurso") REFERENCES "curso"("codCurso")
);
CREATE TABLE IF NOT EXISTS "funcionario" (
	"codFuncionario"	INTEGER NOT NULL,
	"nome"	TEXT NOT NULL,
	"CPF"	TEXT NOT NULL,
	"RG"	TEXT NOT NULL,
	"nasc"	date NOT NULL,
	PRIMARY KEY("codFuncionario" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "aluno" (
	"codAluno"	INTEGER NOT NULL,
	"nome"	TEXT NOT NULL,
	"RG"	TEXT NOT NULL,
	"CPF"	TEXT NOT NULL,
	"nasc"	DATE NOT NULL,
	PRIMARY KEY("codAluno" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "curso" (
	"codCurso"	INTEGER NOT NULL,
	"nomeCurso"	TEXT,
	"duracao"	intEGER,
	PRIMARY KEY("codCurso" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "departamento" (
	"codDepartamento"	INTEGER NOT NULL,
	"descricao"	TEXT NOT NULL,
	PRIMARY KEY("codDepartamento" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "depFuncionario" (
	"codDepFuncionario"	INTEGER NOT NULL,
	"codFuncionario"	INTEGER NOT NULL,
	"codDepartamento"	INTEGER NOT NULL,
	PRIMARY KEY("codDepFuncionario" AUTOINCREMENT),
	FOREIGN KEY("codDepartamento") REFERENCES "departamento"("codDepartamento"),
	FOREIGN KEY("codFuncionario") REFERENCES "funcionario"("codFuncionario")
);
CREATE TABLE IF NOT EXISTS "servico" (
	"codServico"	INTEGER NOT NULL,
	"nome"	TEXT NOT NULL,
	PRIMARY KEY("codServico" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "depServico" (
	"codDepServico"	INTEGER NOT NULL,
	"codDepartamento"	INTEGER NOT NULL,
	"codServico"	INTEGER NOT NULL,
	PRIMARY KEY("codDepServico" AUTOINCREMENT),
	FOREIGN KEY("codServico") REFERENCES "servico"("codServico"),
	FOREIGN KEY("codDepartamento") REFERENCES "departamento"("codDepartamento")
);
CREATE TABLE IF NOT EXISTS "agendamento" (
	"codAgendamento"	INTEGER NOT NULL,
	"codServico"	INTEGER NOT NULL,
	"codAluno"	INTEGER NOT NULL,
	"horario"	datetime NOT NULL,
	PRIMARY KEY("codAgendamento" AUTOINCREMENT),
	FOREIGN KEY("codServico") REFERENCES "servico"("codServico"),
	FOREIGN KEY("codAluno") REFERENCES "aluno"("codAluno")
);
CREATE TABLE IF NOT EXISTS "livro" (
	"codLivro"	INTEGER NOT NULL,
	"nome"	TEXT NOT NULL,
	PRIMARY KEY("codLivro" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "reservaLivros" (
	"codReserva"	INTEGER NOT NULL,
	"codAluno"	INTEGER NOT NULL,
	"codLivro"	INTEGER NOT NULL,
	"dataEmprestimo"	DATE NOT NULL,
	"dataEntrega"	DATE NOT NULL,
	PRIMARY KEY("codReserva" AUTOINCREMENT),
	FOREIGN KEY("codAluno") REFERENCES "aluno"("codAluno"),
	FOREIGN KEY("codLivro") REFERENCES "livro"("codLivro")
);
CREATE TABLE IF NOT EXISTS "boleto" (
	"codBoleto"	INTEGER,
	"codAluno"	INTEGER NOT NULL,
	"dataVencimento"	DATE NOT NULL,
	"valor"	REAL NOT NULL,
	"desconto"	REAL,
	"valorPago"	REAL,
	PRIMARY KEY("codBoleto" AUTOINCREMENT),
	FOREIGN KEY("codAluno") REFERENCES "aluno"("codAluno")
);
CREATE TABLE IF NOT EXISTS "disciplina" (
	"codDisciplina"	INTEGER NOT NULL,
	"nome"	TEXT NOT NULL,
	PRIMARY KEY("codDisciplina" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "cursoDisciplina" (
	"codCursoDisciplina"	INTEGER NOT NULL,
	"codCurso"	INTEGER NOT NULL,
	"codDisciplina"	INTEGER NOT NULL,
	PRIMARY KEY("codCursoDisciplina" AUTOINCREMENT),
	FOREIGN KEY("codCurso") REFERENCES "curso"("codCurso"),
	FOREIGN KEY("codDisciplina") REFERENCES "disciplina"
);
COMMIT;
