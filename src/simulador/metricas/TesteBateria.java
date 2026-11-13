package simulador.metricas;

public class TesteBateria {
    public static void main(String[] args) {
        String csv = args.length > 0 ? args[0] : "cenarios/cenario_fixo.csv";
        Bateria.rodarEImprimir(csv, "resultados/resultados.csv");
    }
}