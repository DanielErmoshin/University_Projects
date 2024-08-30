/** @file Proceso.cc
    @brief Código de la clase Proceso
*/
#include "Proceso.hh"


Proceso::Proceso() {
    id_proceso = 0;
    space = 0;
    t_ej = 0;
}

int Proceso::avanzar_t(int t) {
    t_ej -= t;
    if (t_ej <= 0) return -1;
    else return 0;
}

int Proceso::consultar_id() const{
    return id_proceso;
}

int Proceso::consultar_space() const{
    return space;
}

void Proceso::leer() {
    cin >> id_proceso >> space >> t_ej;
}

void Proceso::escribir() const {
    cout << id_proceso << ' ' << space << ' ' << t_ej << endl;
}






