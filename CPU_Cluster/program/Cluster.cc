/** @file Cluster.cc
    @brief Código de la clase Cluster
*/
#include "Cluster.hh"

Cluster::Cluster() {
}

int Cluster::modificar_cluster (const string &id_cpu, Cluster &c2) {
    map<string, CPU>::iterator it = cList.find(id_cpu);
    if (it == cList.end()) return 1;
    if (not (*it).second.vacio()) return 2; 
    int x; //Sirve para que la función recursiva <em>modificar</em> se oriente.
    modificar (id_cpu, cTree, c2.my_tree(), x);
    if (x == 0) { //Si el procesador no contiene procesadores auxiliares.
        cList.erase (it);
        map<string, CPU> aux = c2.my_map();
        it = aux.begin();
        while (it != aux.end()) {
            cList.insert ((*it));
            ++it;
        }
        return x;
    }
    return x;
}

void Cluster::modificar(const string &id_cpu, BinTree<string> &c, const BinTree<string> &c2, int &x) {
    if (not c.empty()) {
        if (c.value() == id_cpu) {            
            if (c.left().empty() and c.right().empty()) { //Si el CPU no contiene CPUs auxiliares, el nodose sutituye por el arbol c2. 
                x = 0;
                c = c2; 
            }
            else x = 3;  //Contiene CPUs auxiliares.
        }
        else {
            /*Como en la clase BinTree c.left() y c.right() son constantes y no se pueden modificar, creo dos arboles e y d, 
            tales que son identicos a c.left() y c.right() pero no son constantes, por lo cual puedo modificarlos.
            */
            BinTree<string> e, d;
            e = c.left();
            d = c.right();
            modificar (id_cpu, e, c2, x);
            if (x == 0) c = BinTree<string> (c.value(), e, d); //Si hemos logrado sustituir el nodo por c2, e(rama modificada) pasa a ser de c. 
            else if(x == 1) { //Miramos rama derecha d.
                modificar (id_cpu, d, c2, x);
                c = BinTree<string> (c.value(), e, d);
            }
        }
    }
    else x = 1; //No ha encontrado el CPU que coincida con el id_cpu.
}

int Cluster::alta_proceso_procesador (const string& id_cpu, const Proceso &pro) {
    map<string, CPU>::iterator it = cList.find (id_cpu);
    if (it == cList.end()) return 1;
    if ((*it).second.existe(pro.consultar_id())) return 2; //Si ya existe el proceso en el CPU, devuelve error 2.
    return (*it).second.alta_proceso(pro); //Si se ha podido dar de alta, devuelve 0.
}

int Cluster::baja_proceso_procesador (const string &id_cpu, int id_proceso) {
    map<string, CPU>::iterator it = cList.find (id_cpu);
    if (it == cList.end()) return 1;
    return (*it).second.baja_proceso (id_proceso); //Si se ha podido dar de baja, devuelve 0.
}

void Cluster::avanzar_tiempo(int t) {
    map<string, CPU>::iterator it = cList.begin();
    while (it != cList.end()) {
        (*it).second.avanzar_tmp(t);
        ++it;
    }
}
int Cluster::compactar_memoria_procesador (const string &id_cpu) {
    map<string, CPU>::iterator it = cList.find (id_cpu);
    if (it == cList.end()) return 1;
    if (not (*it).second.vacio()) (*it).second.compactar(); //Si el procesador no esta vacio, se compacta.
    return 0;
}

void Cluster::compactar_memoria_cluster () {
    map<string, CPU>::iterator it = cList.begin();
    while (it != cList.end()) {
        if (not (*it).second.vacio()) (*it).second.compactar();
        ++it;
    }
}

map<string, CPU> Cluster::my_map() const {
    return cList;
}

BinTree<string> Cluster::my_tree() const {
    return cTree;
} 

int Cluster::enviar_proceso_procesador (const Proceso &pro) {
    Parametros aux;
    aux.hueco = -1; //Inicializamos hueco a -1 para que la función recursiva sepa cuando se trata del primer posible candidato.
    int z = miramos (cList, aux, pro, cTree, 0);
    if (z == 0) { //Si hemos encontrado CPU optimo, se da de alta en proceso en este mismo.
        map<string, CPU>::iterator it = cList.find(aux.id);
        (*it).second.alta_proceso (pro);
    }
    return z;
}

int Cluster::miramos (const map<string, CPU> &cList, Parametros &aux, const Proceso &pro, const BinTree<string> &c, int p) {
    if (not c.empty()) {
        map<string, CPU>::const_iterator it = cList.find (c.value());
        if (not (*it).second.existe(pro.consultar_id())) { //Si existe un proceso con el mismo id no entramos.
            int x = (*it).second.espacio_para_proceso(pro.consultar_space()); //Devuelve el hueco mas ajustado que tenga el CPU para el proceso.
            if (x >= pro.consultar_space()) {
                int m = (*it).second.consultar_memoria_libre();
                if (aux.hueco == -1) { //Primera iteración.
                    aux.id = (*it).first;
                    aux.hueco = x;
                    aux.mem = m;
                    aux.p = p;
                }
                else if (x < aux.hueco) {
                    aux.id = (*it).first;
                    aux.hueco = x;
                    aux.mem = m;
                    aux.p = p;
                }
                else if (x == aux.hueco) { //Empate, miramos mayor memoria libre.
                    if (m < aux.mem) {
                        aux.id = (*it).first;
                        aux.hueco = x;
                        aux.mem = m;
                        aux.p = p;
                    }
                    else if (m == aux.mem) { //Empate, miramos menor profundidad.
                        if (p < aux.p) {
                            aux.id = (*it).first;
                            aux.hueco = x;
                            aux.mem = m;
                            aux.p = p;
                        }
                        
                    }
                }
            }
        }
        
        miramos (cList, aux, pro, c.left(), p+1);
        miramos (cList, aux, pro, c.right(), p+1);
        if(aux.hueco == -1) return -1; //Si no se ha encontrado ningún CPU que cumpla las características, devuelve -1.
        else return 0;
    }
    else return -1;
}

void Cluster::configurar_cluster () {
    BinTree<string> auxTree;
    map<string,CPU> auxList;
    cTree = auxTree; //Crea un Cluster nuevo siempre que se llame a esta operación.
    cList = auxList; 
    lectura_arbre (cTree, cList); 
}

void Cluster::lectura_arbre (BinTree<string> &cTree, map<string,CPU> &cList) {
    string c;
    cin >> c;
    if(c != "*") { //Crea y añade procesadores (CPUs) vacios y los añade al Custer mediante preorden.
        CPU cpu;
        cpu.llegir();
        cList.insert (make_pair(c, cpu));
        BinTree<string> e,d;        
        lectura_arbre(e, cList);
        lectura_arbre(d, cList);
        cTree = BinTree<string>(c,e,d);
    }
}

void Cluster::imprimir_estructura_cluster () const {
    imprimir (cTree); //Imprime el árbol en preorden.
    cout << endl;
}

void Cluster::imprimir (const BinTree<string>& cTree) { 
    if (cTree.empty()) cout << ' ';
    else {
        cout << '(' << cTree.value();
        imprimir(cTree.left());
        imprimir (cTree.right());
        cout << ')';
    }
}

int Cluster::imprimir_procesador (const string& id_cpu) const {
    map<string,CPU>::const_iterator it = cList.find(id_cpu);
    if (it == cList.end()) return 1;
    else (*it).second.imprimir();
    return 0;
}

void Cluster::imprimir_procesadores_cluster() const { 
    map<string, CPU>::const_iterator it = cList.begin();
    while (it != cList.end()) {
        cout << (*it).first << endl;
        if ((*it).second.procesos_pendientes()) (*it).second.imprimir();
        ++it;
    }
}

