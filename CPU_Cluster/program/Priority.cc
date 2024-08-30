/** @file Priority.cc
    @brief Código de la clase Priority
*/
#include "Priority.hh"

Priority::Priority() {
    enviados = 0;
    rechazados = 0;
}

void Priority::enviar_procesos(int &n, Cluster& c) {
    list<Proceso>::iterator it = p_pendientes.begin();
    int i = p_pendientes.size(); //De esta manera, recorremos todos los procesos de la prioridad sin repetir los que hayan sido rechazados.
    //Inv: i >= 0 y n >= 0.
    while (i != 0 and n != 0) {
        int x = c.enviar_proceso_procesador (*it);
        if (x < 0) { 
            p_pendientes.insert(p_pendientes.end(), *it); //Añadimos el proceso al final de la lista (como si fuera nuevo).
            it = p_pendientes.erase(it); //Lo borramos.
            ++rechazados;
        }
        else {
            --n;
            ++enviados;
            it = p_pendientes.erase(it); //Lo borramos ya que ha sido enviado a Cluster.
        }
        --i;
    } 
}

int Priority::existe_proceso (const Proceso &pro) {
    list<Proceso>::const_iterator it = p_pendientes.begin();
    //Inv: it siempre apunta a un elemento de p_pendientes
    while (it != p_pendientes.end()) {
        if ((*it).consultar_id() == pro.consultar_id()) return -1; //Si existe un proceso con ese id, retorna -1.
        ++it;
    }
    p_pendientes.insert (p_pendientes.end(), pro);
    return 0;
}

bool Priority::procesos_pendientes() const {
    if (p_pendientes.empty()) return false;
    return true;
}

void Priority::imprimir_envios() const {
    cout << enviados << ' ' << rechazados << endl;
}

void Priority::imprimir_p() const {
    list<Proceso>::const_iterator it = p_pendientes.begin();
    while (it != p_pendientes.end()) {
        (*it).escribir();
        ++it;
    }
}