
public class SJF extends AlgoritmoPlanificacion {

    public SJF(Proceso[] procesos) {
        super(procesos);
    }

    @Override
    public void ejecutar() {
        int tiempoActual = 0;
        int procesosCompletados = 0;
        while (procesosCompletados < numeroProcesos) {
            Proceso procesoAEjecutar = null;
            for (Proceso proceso : procesos) {
                if (!proceso.terminado && proceso.tiempoLlegada <= tiempoActual
                        && (procesoAEjecutar == null || proceso.rafagasRestantes < procesoAEjecutar.rafagasRestantes)) {
                    procesoAEjecutar = proceso;
                }
            }
            if (procesoAEjecutar != null) {
                tiempoActual += procesoAEjecutar.rafagasRestantes;
                procesoAEjecutar.tiempoFinalizacion = tiempoActual;
                procesoAEjecutar.terminado = true;
                procesosCompletados++;
            } else {
                tiempoActual++;
            }
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
            tiempoEsperaPromedio += procesos[i].tiempoFinalizacion - procesos[i].tiempoLlegada
                    - procesos[i].rafagasRestantes;
        }
        return tiempoEsperaPromedio / numeroProcesos;
    }
}
