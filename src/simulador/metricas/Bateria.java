package simulador.metricas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import simulador.cpu.CPU;
import simulador.escalonador.Escalonador;
import simulador.escalonador.FCFS;
import simulador.escalonador.Prioridade;
import simulador.escalonador.RoundRobin;
import simulador.escalonador.SJF;
import simulador.gerador.GeradorDeProcessos;
import simulador.modelo.Processo;

/** Roda os 6 cenários no mesmo CSV e junta as métricas */
public class Bateria {

    public static List<Metricas> rodar(String csv) {
        List<Processo> base = GeradorDeProcessos.lerCsv(csv);

        List<Escalonador> escalonadores = List.of(
                new FCFS(), new SJF(), new Prioridade(),
                new RoundRobin(2), new RoundRobin(5), new RoundRobin(10));

        List<Metricas> resultados = new ArrayList<>();
        for (Escalonador e : escalonadores) {
            List<Processo> copias = new ArrayList<>();
            for (Processo p : base) copias.add(p.copia()); // cada algoritmo recebe cópias novas

            Metricas m = new CPU(e, copias, 0).executar(); // sleepMs = 0 para rodar rápido
            m.setNome(e.nome());
            resultados.add(m);
        }
        return resultados;
    }

    public static void rodarEImprimir(String csv, String saidaCsv) {
        List<Metricas> resultados = rodar(csv);
        Metricas.imprimirTabela(resultados);
        try {
            Metricas.salvarCsv(resultados, saidaCsv);
        } catch (IOException ex) {
            System.err.println("Não consegui salvar " + saidaCsv + ": " + ex.getMessage());
        }
    }
}