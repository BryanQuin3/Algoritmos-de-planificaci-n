import java.util.Arrays;
import java.util.Comparator;

public class Prioridad extends AlgoritmoPlanificacion {

    public Prioridad(Proceso[] procesos) {
        super(procesos);
    }

    @Override
    public void ejecutar() {
        // Ordenar los procesos por prioridad (el menor tiene más prioridad)
        Arrays.sort(procesos, Comparator.comparingInt(p -> p.prioridad));
        
        // Ejecutar los procesos en el orden ordenado
        int tiempoActual = 0;
        for (int i = 0; i < numeroProcesos; i++) {
            Proceso proceso = procesos[i];
            if (tiempoActual < proceso.tiempoLlegada) {
                tiempoActual = proceso.tiempoLlegada;
            }
            tiempoActual += proceso.rafagasRestantes;
            proceso.tiempoFinalizacion = tiempoActual;
        }
        tiempoFinalizacion = tiempoActual;
    }

    @Override
    public double calcularTiempoEjecucionPromedio() {
        double tiempoEjecucionPromedio = 0;
        for (int i = 0; i < numeroProcesos; i++) {
            tiempoEjecucionPromedio += procesos[i].tiempoFinalizacion - procesos[i].tiempoLlegada;
        }
        return tiempoEjecucionPromedio / numeroProcesos;
    }

    @Override
    public double calcularTiempoEsperaPromedio() {
        double tiempoEsperaPromedio = 0;
        for (int i = 0; i < numeroProcesos; i++) {
            tiempoEsperaPromedio += procesos[i].tiempoFinalizacion - procesos[i].tiempoLlegada - procesos[i].rafagasRestantes;
        }
        return tiempoEsperaPromedio / numeroProcesos;
    }
}
