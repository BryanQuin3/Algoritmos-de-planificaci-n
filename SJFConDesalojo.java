import java.util.Comparator;
import java.util.PriorityQueue;

public class SJFConDesalojo extends AlgoritmoPlanificacion {

    private PriorityQueue<Proceso> colaListos;

    public SJFConDesalojo(Proceso[] procesos) {
        super(procesos);
        colaListos = new PriorityQueue<>(Comparator.comparingInt(p -> p.rafagasRestantes));
    }

    @Override
    public void ejecutar() {
        int tiempoActual = 0;
        int procesosTerminados = 0;
        Proceso procesoActual = null;
    
        while (procesosTerminados < numeroProcesos) {
            // Variable para controlar si se ejecutó algún proceso en esta iteración
            boolean ejecutado = false;
    
            // Agregar los procesos que han llegado a la cola de listos
            for (int i = 0; i < numeroProcesos; i++) {
                if (procesos[i].tiempoLlegada <= tiempoActual && procesos[i].rafagasRestantes > 0) {
                    colaListos.add(procesos[i]);
                }
            }
            // Incrementar el tiempo de espera para todos los procesos en la cola de listos
            for (Proceso p : colaListos) {
                p.tiempoEspera++;
            }
    
            // Si hay procesos en la cola de listos
            if (!colaListos.isEmpty()) {
                // Obtener el próximo proceso con la ráfaga más corta
                Proceso proximoProceso = colaListos.poll();
                if (procesoActual != null && procesoActual != proximoProceso && procesoActual.rafagasRestantes > 0) {
                    // Desalojo
                    colaListos.add(procesoActual);
                }
                procesoActual = proximoProceso;
                // Ejecutar una ráfaga del proceso actual
                procesoActual.rafagasRestantes--;
                tiempoActual++;
                ejecutado = true;
    
                // Verificar si el proceso ha terminado
                if (procesoActual.rafagasRestantes == 0) {
                    procesoActual.tiempoFinalizacion = tiempoActual;
                    procesosTerminados++;
                    
                   System.out.println(procesosTerminados + ". " + procesoActual.nombre + " terminado en el tiempo " + tiempoActual);
                }
            } else {
                // No hay procesos listos, avanzar en el tiempo
                tiempoActual++;
            }
    
            // Verificar si se ejecutó algún proceso en esta iteración
            if (!ejecutado && procesoActual != null) {
                // Si no se ejecutó ningún proceso y hay uno en ejecución, incrementar su tiempo de espera
                procesoActual.tiempoEspera++;
            }
        }
        // actualizar el tiempo final de ejecución
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
        // Calcular el tiempo de espera de cada proceso
        procesos[i].tiempoEspera = procesos[i].tiempoFinalizacion - procesos[i].tiempoLlegada - procesos[i].rafagasRestantes;
        tiempoEsperaPromedio += procesos[i].tiempoEspera;
    }
    return tiempoEsperaPromedio / numeroProcesos;
}

}
