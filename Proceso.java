class Proceso {
    String nombre;
    int tiempoLlegada;
    int rafagasRestantes;
    int prioridad;
    public int tiempoFinalizacion;
    public boolean terminado;
    public int tiempoEspera;

    public Proceso(String nombre, int tiempoLlegada, int rafagasRestantes, int prioridad) {
        this.nombre = nombre;
        this.tiempoLlegada = tiempoLlegada;
        this.rafagasRestantes = rafagasRestantes;
        this.prioridad = prioridad;
    }
    // metodo para resetear los valores de los procesos
    public void reset() {
        this.tiempoFinalizacion = 0;
        this.terminado = false;
        this.tiempoEspera = 0;
    }
}