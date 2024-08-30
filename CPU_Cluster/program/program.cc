/** @mainpage
    Este programa tiene la finalidad de simular el funcionamiento de procesadores interconectados. Principalmente, trata la gestión
    de procesos tanto activos como pendientes, y del funcionamiento eficiente del conjunto de procesadores.
    
    El programa principal se encuentra en el módulo program.cc. Atendiendo a los tipos de datos tratados, necesitaremos un módulo para
    representar, por una parte, el clúster, el cual contiene un conjunto de procesadores en los cuales se ejecutan y almacenan 
    los procesos activos, y, por otra parte, el área de procesos pendientes el cual contiene procesos de diferente prioridad a la 
    espera de poder pasar a ejecutarse en el clúster.
*/
/** @file program.cc

    @brief Programa principal

    Asumo que las operaciones leídas siempre cumplen el formato adecuado, 
    por lo cual no incluyo las comprobaciones correspondientes. 
    Para ciertas operaciones que puedan provocar determinados fallos en el programa, utilizo números enteros para
    poder diferenciar el tipo de error y notificarlo de manera correspondiente.
*/

#include "Cluster.hh"
#include "Area_Procesos.hh"
#include "Proceso.hh"
using namespace std;

int main() {
    Cluster c; //El programa crea un Cluster por defecto.
    Area_Procesos ap; //El programa crea un Cluster por defecto.
    c.configurar_cluster();
    ap.inicializar();
    string op; //Código de operación
    cin >> op;
    while (op != "fin") {
        if (op == "cc" or op == "configurar_cluster") {
            cout << '#' << op << endl;
            c.configurar_cluster();
        }
        else if (op == "mc" or op == "modificar_cluster") {
            string id_cpu;
            cin >> id_cpu;
            Cluster c2;
            c2.configurar_cluster();
            cout << '#' << op << ' ' << id_cpu << endl;
            int error = c.modificar_cluster(id_cpu, c2);
            if(error == 1) cout << "error: no existe procesador" << endl;
            if(error == 2) cout << "error: procesador con procesos" << endl;
            if(error == 3) cout << "error: procesador con auxiliares" << endl;
        }
        else if (op == "ap" or op == "alta_prioridad") {
            string id_priority;
            cin >> id_priority;
            cout << '#' << op << ' ' << id_priority << endl;    
            int error = ap.alta_prioridad(id_priority);
            if (error == 1) cout << "error: ya existe prioridad" << endl; 
        }
        else if (op == "bp" or op == "baja_prioridad") {
            string id_priority;
            cin >> id_priority;
            cout << '#' << op << ' ' << id_priority << endl;
            int error = ap.baja_prioridad(id_priority);
            if (error == 1) cout << "error: no existe prioridad" << endl;
            if (error == 2) cout << "error: prioridad con procesos" << endl;
        }
        else if (op == "ape" or op == "alta_proceso_espera") {
            string id_priority;
            cin >> id_priority;
            Proceso pro;
            pro.leer();
            cout << '#' << op << ' ' << id_priority << " " << pro.consultar_id() << endl;
            int error = ap.alta_proceso_espera(pro, id_priority);
            if (error == 1) cout << "error: no existe prioridad" << endl;
            if (error == 2) cout << "error: ya existe proceso" << endl;
        }
        else if (op == "app" or op == "alta_proceso_procesador") {
            string id_cpu;
            cin >> id_cpu;
            Proceso pro;
            pro.leer(); 
            cout << '#' << op << ' ' << id_cpu << " " << pro.consultar_id() << endl; 
            int error = c.alta_proceso_procesador(id_cpu, pro);
            if (error == 1) cout << "error: no existe procesador" << endl;
            if (error == 2) cout << "error: ya existe proceso" << endl;
            if (error == 3) cout << "error: no cabe proceso" << endl; 
        }
        else if (op == "bpp" or op == "baja_proceso_procesador") {
            string id_cpu;
            cin >> id_cpu;
            int id_proceso;
            cin >> id_proceso;
            cout << '#' << op << ' ' << id_cpu << ' ' << id_proceso << endl;   
            int error = c.baja_proceso_procesador(id_cpu, id_proceso);
            if (error == 1) cout << "error: no existe procesador" << endl;
            if (error == 2) cout << "error: no existe proceso" << endl; 
        }
        else if (op == "epc" or op == "enviar_procesos_cluster") {
            int n;
            cin >> n;
            cout << '#' << op << ' ' << n << endl;
            ap.enviar_procesos_cluster(n, c);
        } 
        else if (op == "at" or op == "avanzar_tiempo") {
            int t;
            cin >> t;
            cout << '#' << op << ' ' << t << endl;
            c.avanzar_tiempo(t);
        }
        else if (op == "at" or op == "avanzar_tiempo") {
            int t;
            cin >> t;
            cout << '#' << op << endl;
            c.avanzar_tiempo(t);
        } 
        else if (op == "ipri" or op == "imprimir_prioridad") {
            string id_priority;
            cin >> id_priority;
            cout << '#' << op << ' ' << id_priority << endl;
            int error = ap.imprimir_prioridad(id_priority);
            if (error == 1) cout << "error: no existe prioridad" << endl;
        }
        else if (op == "iae" or op == "imprimir_area_espera") {
            cout << '#' << op << endl;
            ap.imprimir_area_espera();
        }
        else if (op == "ipro" or op == "imprimir_procesador") {
            string id_cpu;
            cin >> id_cpu;
            cout << '#' << op << ' ' << id_cpu << endl;
            int error = c.imprimir_procesador(id_cpu);
            if (error == 1) cout << "error: no existe procesador" << endl;
        } 
        else if (op == "ipc" or op == "imprimir_procesadores_cluster") {
            cout << '#' << op << endl;
            c.imprimir_procesadores_cluster();
        }
        else if (op == "iec" or op == "imprimir_estructura_cluster") {
            cout << '#' << op << endl;
            c.imprimir_estructura_cluster();
        }
        else if (op == "cmp" or op == "compactar_memoria_procesador") {
            string id_cpu;
            cin >> id_cpu;
            cout << '#' << op << ' ' << id_cpu << endl;
            int error = c.compactar_memoria_procesador(id_cpu);
            if (error == 1) cout << "error: no existe procesador" << endl;
        }
        else if (op == "cmc" or op == "compactar_memoria_cluster") {
            cout << '#' << op << endl;
            c.compactar_memoria_cluster();
        }
        cin >> op;
    }
}