public class SJFConDesalojo extends AlgoritmoPlanificacion {

    public SJFConDesalojo(Proceso[] procesos) {
        super(procesos);
    }

    private static Proceso[] copiarProcesos(Proceso[] procesosOriginales) {
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

    Proceso[] procesosOriginales = copiarProcesos(procesos);

    @Override
    public void ejecutar() {
        int tiempoActual = 0;
        Proceso procesoActual = null;
        while (true) {
            boolean todosTerminados = true;
            for (Proceso proceso : procesos) {
                if (!proceso.terminado) {
                    todosTerminados = false;
                    if (proceso.tiempoLlegada <= tiempoActual
                            && (procesoActual == null || proceso.rafagasRestantes < procesoActual.rafagasRestantes)) {
                        procesoActual = proceso;
                    }
                }
            }
            if (todosTerminados) {
                break;
            }
            if (procesoActual != null) {
                procesoActual.rafagasRestantes--;
                if (procesoActual.rafagasRestantes == 0) {
                    procesoActual.tiempoFinalizacion = tiempoActual + 1;
                    procesoActual.terminado = true;
                    // actualizar proceso original
                    for (Proceso proceso : procesosOriginales) {
                        if (proceso.nombre.equals(procesoActual.nombre)) {
                            proceso.tiempoFinalizacion = procesoActual.tiempoFinalizacion;
                            proceso.terminado = true;
                        }
                    }
                    procesoActual = null;
                }
            }
            tiempoActual++;
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
        for (Proceso proceso : procesosOriginales) {
            tiempoEsperaPromedio += proceso.tiempoFinalizacion - proceso.tiempoLlegada - proceso.rafagasRestantes;
        }
        return tiempoEsperaPromedio / numeroProcesos;
    }
}
