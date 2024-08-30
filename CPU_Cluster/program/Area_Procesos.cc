/** @file Area_Procesos.cc
    @brief Código de la clase Area_Procesos
*/
#include "Area_Procesos.hh"

Area_Procesos::Area_Procesos() {

}

int Area_Procesos::alta_prioridad (const string& id_pri) {
   map<string,Priority>::const_iterator it = area.find(id_pri); //Consultamos en el area de espera si existe la prioridad con el id id_pri.
   if (it != area.end()) return 1; //Si existe devolvemos error 1 (consultar en program.cc).
   Priority p;
   area.insert (make_pair(id_pri, p));
   return 0; //No hay error.
}

int Area_Procesos::baja_prioridad (const string& id_pri) {
    map<string,Priority>::const_iterator it = area.find(id_pri); //Consultamos en el area de espera si existe la prioridad con el id id_pri.
    if (it == area.end()) return 1; //Si no existe devolvemos error 1 (consultar en program.cc).
    if ((*it).second.procesos_pendientes()) return 2; //Si la prioridad tiene procesos pendientes. Si se da el caso, devuelve error 2.
    area.erase (it); 
    return 0;
}

int Area_Procesos::alta_proceso_espera (const Proceso& pro, const string& id_pri) {
    map<string,Priority>::iterator it = area.find(id_pri);
    if (it == area.end()) return 1;
    int x = (*it).second.existe_proceso(pro); //Consulta en la prioridad si existe un proceso con el mismo identificador.
    if (x < 0) return 2; //Si no existe, la función existe_proceso(pro) devuelve -1. En ese caso, devolvemos error 2.
    return 0;
}

void Area_Procesos::enviar_procesos_cluster(int n, Cluster &c) {
    map<string, Priority>::iterator it = area.begin();
    //Inv: n <= N y it apunta siempre a un elemento de area.
    while (it != area.end() and n != 0) { //En el momento que hemos recorrido todas las prioridades o que los n procesos han sido enviados, sale.
        (*it).second.enviar_procesos (n, c);
        ++it;
    }
}

void Area_Procesos::inicializar() {
    int n;
    cin >> n;
    while (n != 0) {
        string id_pri;
        cin >> id_pri;
        Priority p;  
        area.insert (make_pair (id_pri, p));
        --n;
    }
}

int Area_Procesos::imprimir_prioridad(const string& id_pri) {
    map<string,Priority>::iterator it = area.find(id_pri);
    if (it == area.end()) return 1;
    (*it).second.imprimir_p();
    (*it).second.imprimir_envios();
    return 0;
}

void Area_Procesos::imprimir_area_espera() {
    map<string, Priority>::const_iterator it = area.begin();
    while (it != area.end()) { 
        cout << (*it).first << endl;
        if ((*it).second.procesos_pendientes()) (*it).second.imprimir_p(); //Si la prioridad tiene procesos pendientes, los imprime.
        (*it).second.imprimir_envios(); //Siempre imprime cuantos procesos han sido aceptados por el cluster, y cuantos han sido rechazados.
        ++it;
    } 
}





