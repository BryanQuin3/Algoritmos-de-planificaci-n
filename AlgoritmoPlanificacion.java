abstract class AlgoritmoPlanificacion {
    protected Proceso[] procesos;
    protected int numeroProcesos;
    protected int tiempoFinalizacion;

    public AlgoritmoPlanificacion(Proceso[] procesos) {
        this.procesos = procesos;
        this.numeroProcesos = procesos.length;
    }

    public abstract void ejecutar();
    public abstract double calcularTiempoEjecucionPromedio();
    public abstract double calcularTiempoEsperaPromedio();

    // Método para obtener el tiempo de finalización
    public int getTiempoFinalizacion() {
        return tiempoFinalizacion;
    }
}
