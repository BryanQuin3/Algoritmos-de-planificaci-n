/**
 * The FCFS (First-Come, First-Served) class is a subclass of the AlgoritmoPlanificacion class.
 * It represents the FCFS scheduling algorithm for process execution.
 */
public class FCFS extends AlgoritmoPlanificacion {

    /**
     * Constructs a new FCFS object with the given array of processes.
     *
     * @param procesos an array of processes
     */
    public FCFS(Proceso[] procesos) {
        super(procesos);
    }

    /**
     * Executes the FCFS scheduling algorithm.
     * It calculates the finishing time for each process based on their arrival time and burst time.
     */
    @Override
    public void ejecutar() {
        int tiempoActual = 0;
        for (int i = 0; i < numeroProcesos; i++) {
            Proceso proceso = procesos[i];
            if (tiempoActual < proceso.tiempoLlegada) {
                tiempoActual = proceso.tiempoLlegada;
            }
            tiempoActual += proceso.rafagasRestantes;
            proceso.tiempoFinalizacion = tiempoActual;
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
            tiempoEsperaPromedio += procesos[i].tiempoFinalizacion - procesos[i].tiempoLlegada - procesos[i].rafagasRestantes;
        }
        return tiempoEsperaPromedio / numeroProcesos;
    }
    
}
