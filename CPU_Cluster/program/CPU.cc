/** @file CPU.cc
    @brief Código de la clase CPU
*/
#include "CPU.hh"

CPU::CPU() {
    capacidad = 0;
    memoria_ocupada = 0;
}

int CPU::alta_proceso (const Proceso &pro) {
    if (capacidad < pro.consultar_space() or (memoria_ocupada + pro.consultar_space()) > capacidad) return 3;
    map<int, set<int>>::iterator it = gap.lower_bound(pro.consultar_space()); //Encontramos el hueco más ajustado al tamaño de pro
    if (it == gap.end()) return 3;
    int s_gap = (*it).first; //tamaño hueco
    int pos_gap = (*(*it).second.begin()); //primera posición que ocupa el hueco
    borrar_pos(gap, s_gap, -1); //Borramos el hueco
    mem.insert (make_pair (pos_gap, pro)); //Insertamos proceso
    memoria_ocupada += pro.consultar_space();
    pos.insert (make_pair (pro.consultar_id(), pos_gap));
    
    if (s_gap > pro.consultar_space()) { //Si el hueco inicial es mayor que el tamaño del proceso:
        int s1_gap = s_gap - pro.consultar_space(); //Se crea un hueco de x tamaño.
        int pos1_gap = pos_gap + pro.consultar_space(); //La posición de este nuevo hueco es la siguiente.
        insertar_hueco (gap, s1_gap, pos1_gap); 
    }
    return 0;
}


int CPU::baja_proceso (int id_pro) {
    map<int, int>::iterator ini = pos.find(id_pro); //Miramos si existe el proceso en el CPU.
    if (ini == pos.end()) return 2; 
    map<int, Proceso>::iterator it = mem.find((*ini).second);
    pos.erase(ini); //Borramos el proceso del mapa de posiciones
    return borrar_proceso(mem, gap, capacidad, memoria_ocupada, it);
}

int CPU::borrar_proceso (map<int,Proceso> &mem, map<int,set<int>> &gap, int capacidad, int &memoria_ocupada, map<int,Proceso>::iterator &it) {
    int arriba, abajo, total, posi;
    if (it == mem.begin()) {
        if((*it).first > 0) { //Miramos si el primer proceso se encuentra en la posición 0.
            borrar_pos(gap,(*it).first,0);
            arriba = (*it).first; //Arriba pasa a valer el hueco que habia entre pa posición 0 y el proceso.
        }
        else arriba = 0;
        posi = 0;

        abajo = down_size(mem, it, capacidad, gap);
        
    }
    else {
       
        arriba = up_size (it, gap); //tamaño del hueco de arriba
        abajo = down_size(mem, it, capacidad, gap); //tamaño del hueco de abajo
    }
    total = arriba + abajo + (*it).second.consultar_space(); //Hueco total que dejaremos al borrar el proceso
    posi = (*it).first - arriba; //Posicion donde empieza el hueco
    memoria_ocupada -= (*it).second.consultar_space();
    it = mem.erase (it);
    insertar_hueco (gap, total, posi);
    return 0;
}

int CPU::up_size (map<int, Proceso>::iterator it, map<int,set<int>>& gap) {
    int posi = (*it).first; //Donde empieza el proceso actual
    --it;
    int size_p_a = (*it).first +(*it).second.consultar_space();
    if (posi - size_p_a != 0) {
        borrar_pos(gap,posi - size_p_a,size_p_a);
        return posi - size_p_a; //Devolvemos en hueco que hay entre el proceso actual y el anterior.
    }
    return 0; //En caso de que no haya espacio entre proceso anterior y el actual.
}

int CPU::down_size (const map<int,Proceso> &mem, map<int, Proceso>::iterator it, int capacidad, map<int,set<int>>& gap) {
    int size_p = (*it).first + (*it).second.consultar_space();
    ++it;
    if (it == mem.end()) {
        if(capacidad - size_p > 0) borrar_pos(gap,capacidad - size_p,size_p);
        return (capacidad - size_p); //Devolvemos la diferencia entre la capacidad total y el tamaño desde la posicion q ocupa el proceso.
    }
    if ((*it).first - size_p > 0) borrar_pos(gap,(*it).first - size_p,size_p);
    return ((*it).first - size_p); //Devolvemos el espacio que hay entre el proceso actual y el posterior.
}

void CPU::insertar_hueco (map<int, set<int>> &gap, int size, int position) {
    map<int,set<int>>::iterator it_g = gap.find(size); 
     if (it_g == gap.end()) { //Si no existe un hueco del tamaño size, creamos nuevo hueco.
        set<int> aux;
        aux.insert(position); 
        gap.insert (make_pair (size, aux));
    }
    else (*it_g).second.insert(position); //Añadimos la posición del hueco al set.
}

void CPU::avanzar_tmp(int t) {
    map<int, Proceso>::iterator it = mem.begin();
    while (it != mem.end()) {
        int x = (*it).second.avanzar_t(t);
        if (x < 0) { //Si el tiempo queda invalido, borramos el proceso del CPU.
            map<int, int>::iterator ini = pos.find((*it).second.consultar_id());
            pos.erase(ini);
            borrar_proceso(mem, gap, capacidad, memoria_ocupada, it);
        }
        else ++it;
    }
}

void CPU::compactar () {
    map<int,Proceso>::iterator it = mem.begin();
        if ((*it).first != 0) { //Miramos que el primer proceso se encuentre en la posición 0.
            Proceso pro = (*it).second;
            mem.erase (it);
            mem.insert (make_pair (0, pro));
            map<int,int>::iterator it_p = pos.find(pro.consultar_id()); //encontramos el proceso en pos i actualizamos la posicón de este.
            (*it_p).second = 0;
        }
        it = mem.begin();
        ++it;
        while (it != mem.end()) {
            map <int, Proceso>::iterator aux = it;
            --aux; //Apuntamos al proceso anterior.
            int pos_anterior = (*aux).first + (*aux).second.consultar_space();
            if ((*it).first - pos_anterior != 0) {
                map<int,int>::iterator it_p = pos.find((*it).second.consultar_id()); //encontramos el proceso i actualizamos su posicón .
                (*it_p).second =  pos_anterior;//Comenzará a partir de donde acaba el proceso anterior.
                Proceso pro = (*it).second;
                mem.erase (it);
                mem.insert (make_pair (pos_anterior, pro));
                it = mem.find(pos_anterior);  
            }  
            ++it;
        }
    --it;
    int x = (*it).first + (*it).second.consultar_space();
    map <int, set<int>> aux;
    gap = aux; //Actualizamos mapa de huecos.
    if (capacidad - x != 0) insertar_hueco (gap, capacidad - x, x); //Si hay hueco entre el último proceso y la capacidad total, lo añadimos.
}

void CPU::borrar_pos(map<int, set<int>> &gap, int s_gap, int pos_gap) {
    map<int, set<int>>::iterator it = gap.find(s_gap);
    set<int>::iterator it1;
    if (pos_gap < 0) it1 = (*it).second.begin(); //Si pos_gap < 0, sabemos que borraremos la primera posición del hueco (alta_proceso).
    else it1 = (*it).second.find(pos_gap);
    (*it).second.erase(it1);
    if ((*it).second.empty()) gap.erase(it);
}

int CPU::espacio_para_proceso (int size) const {
    map<int, set<int>>::const_iterator it = gap.lower_bound(size);
    if (it != gap.end()) return (*it).first;
    return -1;
}

int CPU::consultar_capacidad() const {
    return capacidad;
}

bool CPU::existe(int id) const {
    return pos.find(id) != pos.end();
}

bool CPU::vacio() const {
    return mem.empty();
}

int CPU::consultar_memoria_libre() const {
    return capacidad - memoria_ocupada;
}

bool CPU::procesos_pendientes() const{
    return memoria_ocupada != 0;
}

void CPU::llegir() {
    cin >> capacidad;
    set<int> aux;
    aux.insert(0);
    gap.insert (make_pair (capacidad, aux));
}

void CPU::imprimir() const {
    map<int, Proceso>::const_iterator it = mem.begin();
    while (it != mem.end()) {
        cout << (*it).first << ' ';
        (*it).second.escribir();
        ++it;
    }
}