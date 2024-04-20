import java.util.LinkedList;
import java.util.Queue;

public class RR extends AlgoritmoPlanificacion {

    private int quantum;

    public RR(Proceso[] procesos, int quantum) {
        super(procesos);
        this.quantum = quantum;
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
        Queue<Proceso> colaListos = new LinkedList<>();
        int tiempoActual = 0;

        while (true) {
            boolean todosTerminados = true;

            // Agregar procesos a la cola de listos que han llegado
            for (Proceso proceso : procesos) {
                if (!proceso.terminado && proceso.tiempoLlegada <= tiempoActual && !colaListos.contains(proceso)) {
                    colaListos.add(proceso);
                    todosTerminados = false;
                }
            }

            if (todosTerminados && colaListos.isEmpty()) {
                // Todos los procesos han terminado y no hay procesos en cola de listos
                break;
            }

            if (!colaListos.isEmpty()) {
                Proceso procesoActual = colaListos.poll();
                int tiempoRestante = procesoActual.rafagasRestantes;

                // Ejecutar el proceso durante el quantum o hasta que termine
                int tiempoEjecutado = Math.min(quantum, tiempoRestante);
                tiempoActual += tiempoEjecutado;
                procesoActual.rafagasRestantes -= tiempoEjecutado;

                // Si el proceso no ha terminado, volver a agregarlo a la cola de listos
                if (procesoActual.rafagasRestantes > 0) {
                    colaListos.add(procesoActual);
                } else {
                    procesoActual.tiempoFinalizacion = tiempoActual;
                    // actualizar el tiempo de finalización del proceso original
                    for (Proceso proceso : procesosOriginales) {
                        if (proceso.nombre.equals(procesoActual.nombre)) {
                            proceso.tiempoFinalizacion = tiempoActual;
                        }
                    }
                    procesoActual.terminado = true;
                }
            } else {
                // No hay procesos listos, avanzar en el tiempo
                tiempoActual++;
            }
        }
        // Actualizamos el tiempo final de la ejecución
        tiempoFinalizacion = tiempoActual;
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
        for (Proceso proceso : procesosOriginales) {
            tiempoEsperaPromedio += proceso.tiempoFinalizacion - proceso.tiempoLlegada - proceso.rafagasRestantes;
            System.out.println(
                    proceso.tiempoFinalizacion + " - " + proceso.tiempoLlegada + " - " + proceso.rafagasRestantes
                            + " = " + (proceso.tiempoFinalizacion - proceso.tiempoLlegada - proceso.rafagasRestantes));
        }
        return tiempoEsperaPromedio / numeroProcesos;
    }
}
