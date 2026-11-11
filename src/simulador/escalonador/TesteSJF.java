package simulador.escalonador;

import java.util.List;

import simulador.cpu.CPU;
import simulador.modelo.Processo;

public class TesteSJF {

    public static void main(String[] args){
        List<Processo> processos = List.of(
            new Processo(1 , 5, 2, 0),
            new Processo(2 , 3, 5, 1),
            new Processo(3 , 8, 1, 2),
            new Processo(4 , 2, 4, 3),
            new Processo(5 , 4, 3, 4)
        );

        CPU cpu = new CPU(new SJF(), processos, 0);
        cpu.executar();

        System.out.println("\nRESULTADO DO TESTE SJF");
        int somaEspera = 0;
        for(Processo p : processos){
            System.out.printf("P%d terminou em t=%d, espera=%d%n",
                p.getId(), p.getFim(), p.tempoEspera());
                somaEspera += p.tempoEspera();
        }
        System.out.printf("Espera média = %.1f%n", somaEspera/ (double) processos.size());
    }
}
