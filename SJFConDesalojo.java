import java.util.Comparator;
import java.util.PriorityQueue;

public class SJFConDesalojo extends AlgoritmoPlanificacion {

    private PriorityQueue<Proceso> colaListos;

    public SJFConDesalojo(Proceso[] procesos) {
        super(procesos);
        colaListos = new PriorityQueue<>(Comparator.comparingInt(p -> p.rafagasRestantes));
    }

    // copia del estado original de los procesos
    private Proceso[] copiaProcesos(Proceso[] procesosOriginales) {
        Proceso[] copia = new Proceso[procesosOriginales.length];
        for (int i = 0; i < procesosOriginales.length; i++) {
            copia[i] = new Proceso(
                    procesosOriginales[i].nombre,
                    procesosOriginales[i].tiempoLlegada,
                    procesosOriginales[i].rafagasRestantes,
                    procesosOriginales[i].prioridad);
        }
        return copia;
    }

    Proceso[] procesosOriginales = copiaProcesos(procesos);

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
            // Si hay procesos en la cola de listos
            if (!colaListos.isEmpty()) {
                // Obtener el próximo proceso con la ráfaga más corta
                Proceso proximoProceso = colaListos.poll();
                if (procesoActual != null && procesoActual != proximoProceso && procesoActual.rafagasRestantes > 0) {
                    // Desalojo
                    colaListos.add(procesoActual);
                }
                procesoActual = proximoProceso;
                procesoActual.rafagasRestantes--;
                // Ejecutar una ráfaga del proceso actual
                ejecutado = true;
                // Verificar si el proceso ha terminado
                if (procesoActual.rafagasRestantes == 0) {
                    procesoActual.tiempoFinalizacion = tiempoActual;
                    // actualizar el tiempo de finalización del proceso original
                    for (Proceso proceso : procesosOriginales) {
                        if (proceso.nombre.equals(procesoActual.nombre)) {
                            proceso.tiempoFinalizacion = tiempoActual;
                        }
                    }
                    procesosTerminados++;

                    System.out.println(procesosTerminados + ". " + procesoActual.nombre + " terminado en el tiempo "
                            + tiempoActual);
                }
            }

            // Incrementar el tiempo de espera para los procesos en la cola de listos
            for (Proceso p : colaListos) {
                p.tiempoEspera++;
            }
            tiempoActual++;
        }
        // actualizar el tiempo final de ejecución
        tiempoFinalizacion = tiempoActual;
    }

    @Override
    public double calcularTiempoEjecucionPromedio() {
        double tiempoEjecucionPromedio = 0;
        for (Proceso proceso : procesosOriginales) {
            tiempoEjecucionPromedio += proceso.tiempoFinalizacion - proceso.tiempoLlegada + 1;
        }
        return tiempoEjecucionPromedio / numeroProcesos;
    }

    @Override
    public double calcularTiempoEsperaPromedio() {
        double tiempoEsperaPromedio = 0;
        for (Proceso proceso : procesosOriginales) {
            tiempoEsperaPromedio += proceso.tiempoFinalizacion - proceso.tiempoLlegada - proceso.rafagasRestantes;
        }
        return tiempoEsperaPromedio / numeroProcesos;
    }

}
