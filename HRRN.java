import java.util.Comparator;
import java.util.PriorityQueue;

public class HRRN extends AlgoritmoPlanificacion {

    public HRRN(Proceso[] procesos) {
        super(procesos);
    }

    @Override
    public void ejecutar() {
        PriorityQueue<Proceso> colaListos = new PriorityQueue<>(Comparator.comparingDouble(this::calcularRatioRespuesta).reversed());
        int tiempoActual = 0;
    
        while (!colaListos.isEmpty() || hayProcesosPendientes()) {
            // Agregar procesos a la cola de listos que han llegado
            for (Proceso proceso : procesos) {
                if (!proceso.terminado && proceso.tiempoLlegada <= tiempoActual) {
                    colaListos.add(proceso);
                }
            }
    
            if (!colaListos.isEmpty()) {
                Proceso procesoActual = colaListos.poll();
                int tiempoEjecutado = Math.min(procesoActual.rafagasRestantes, 1); // Ejecutar una ráfaga
    
                tiempoActual += tiempoEjecutado;
                procesoActual.rafagasRestantes -= tiempoEjecutado;
    
                if (procesoActual.rafagasRestantes == 0) {
                    procesoActual.tiempoFinalizacion = tiempoActual;
                    procesoActual.terminado = true;
                }
            } else {
                // No hay procesos listos, avanzar en el tiempo
                tiempoActual++; 
            }
    
            // Actualizamos el tiempo final de la ejecución en cada iteración
            tiempoFinalizacion = tiempoActual;
        }
    }
    
    private double calcularRatioRespuesta(Proceso proceso) {
        // Calculamos el ratio de respuesta para un proceso
        return (tiempoFinalizacion - proceso.tiempoLlegada + proceso.rafagasRestantes) / (double) proceso.rafagasRestantes;
    }

    @Override
    public double calcularTiempoEjecucionPromedio() {
        double tiempoEjecucionPromedio = 0;
        for (Proceso proceso : procesos) {
            tiempoEjecucionPromedio += proceso.tiempoFinalizacion - proceso.tiempoLlegada;
        }
        return tiempoEjecucionPromedio / numeroProcesos;
    }

    @Override
    public double calcularTiempoEsperaPromedio() {
        double tiempoEsperaPromedio = 0;
        for (Proceso proceso : procesos) {
            tiempoEsperaPromedio += proceso.tiempoFinalizacion - proceso.tiempoLlegada - proceso.rafagasRestantes;
        }
        return tiempoEsperaPromedio / numeroProcesos;
    }

    private boolean hayProcesosPendientes() {
        for (Proceso proceso : procesos) {
            if (!proceso.terminado) {
                return true;
            }
        }
        return false;
    }
}
