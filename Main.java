import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Crear procesos de ejemplo
        Proceso[] procesosOriginales = {
            new Proceso("P1", 0, 3, 1),
            new Proceso("P2", 1, 4, 5),
            new Proceso("P3", 2, 5, 6),
            new Proceso("P4", 3, 2, 2),
            new Proceso("P5", 4, 6, 7),
            new Proceso("P6", 4, 3, 3),
            new Proceso("P7", 4, 3, 4),
        };

        // Menú
        while (true) {
            Proceso[] procesos = copiarProcesos(procesosOriginales); // Copiar procesos originales

            System.out.println("\nMenú:");
            System.out.println("1. FCFS");
            System.out.println("2. SJF sin desalojo");
            System.out.println("3. SJF con desalojo");
            System.out.println("4. Prioridad");
            System.out.println("5. RR");
            System.out.println("6. HRRN");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            AlgoritmoPlanificacion algoritmo;

            switch (opcion) {
                case 1:
                    algoritmo = new FCFS(procesos);
                    break;
                case 2:
                    algoritmo = new SJF(procesos);
                    break;
                case 3:
                    algoritmo = new SJFConDesalojo(procesos);
                    break;
                case 4:
                    algoritmo = new Prioridad(procesos);
                    break;
                case 5:
                    System.out.print("Ingrese el valor de Q para RR: ");
                    int quantumRR = scanner.nextInt();
                    algoritmo = new RR(procesos, quantumRR);
                    break;
                case 6:
                    algoritmo = new HRRN(procesos);
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
                    continue;
            }

            ejecutarAlgoritmo(algoritmo);
        }
    }

    private static Proceso[] copiarProcesos(Proceso[] procesosOriginales) {
        Proceso[] copia = new Proceso[procesosOriginales.length];
        for (int i = 0; i < procesosOriginales.length; i++) {
            copia[i] = new Proceso(
                procesosOriginales[i].nombre,
                procesosOriginales[i].tiempoLlegada,
                procesosOriginales[i].rafagasRestantes,
                procesosOriginales[i].prioridad
            );
        }
        return copia;
    }

    private static void ejecutarAlgoritmo(AlgoritmoPlanificacion algoritmo) {
        // Imprimir los datos de cada proceso
        System.out.println("\nDatos de los procesos:");
        System.out.println("Proceso    | Tiempo de llegada | Ráfagas | Prioridad");
        System.out.println("------------------------------------------------------");
        for (Proceso proceso : algoritmo.procesos) {
            System.out.printf("%-10s | %-18d | %-7d | %-8d%n", proceso.nombre, proceso.tiempoLlegada, proceso.rafagasRestantes, proceso.prioridad);
        }
        algoritmo.ejecutar();
        System.out.println("Tiempo de finalización: " + algoritmo.getTiempoFinalizacion());
        System.out.println("Tiempo de ejecución promedio: " + algoritmo.calcularTiempoEjecucionPromedio());
        System.out.println("Tiempo de espera promedio: " + algoritmo.calcularTiempoEsperaPromedio());
    }
}
