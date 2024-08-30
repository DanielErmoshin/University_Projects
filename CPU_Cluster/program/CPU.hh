/** @file CPU.hh
    @brief Especificación de la clase CPU
*/

#ifndef CPU_HH
#define CPU_HH
#include "Proceso.hh"
#ifndef NO_DIAGRAM
#include <map>
#include <set>
#endif
using namespace std;

/** @class CPU
    @brief Representa un Procesador. 

    Este procesador dispone de una serie de procesos ordenados por disponibilidad de huecos libres de este mismo.
*/
class CPU {

private:
    /** @brief Mapa de procesos. 
     
      La clave contiene la posición del proceso en la memoria y el segundo valor es el proceso como tal. 
    */
    map<int,Proceso> mem;
    /** @brief Mapa de posiciones. 
     
      La clave contiene el identificador del proceso y el segundo valor es el proceso como tal. 
    */
    map<int,int> pos;
    /** @brief Mapa de huecos. 
     
      La clave contiene el tamaño del hueco y el segundo valor es un set que contiene todas las posiciones de los huecos con ese mismo tamaño. 
    */
    map<int,set<int>> gap;
    /** @brief Capacidad del CPU */
    int capacidad;
    /** @brief Memoria libre del CPU */
    int memoria_ocupada;    
    
    /** @brief Insertar hueco. 

      \pre size > 0; position >= 0.
      \post Si ya existíaa un hueco con el tamaño <em>size</em>, se añade la posición <em>position</em> al conjunto de posiciones de ese tamaño.
      En caso contrario, se crea una nueva entrada con ese tamaño de hueco y posición.
    */ 
    static void insertar_hueco (map<int, set<int>> &gap, int size, int position);

    /** @brief Borrar hueco. 

      \pre gap NO vacío; s_gap > 0.
      \post Se borra la posición <em>pos_gap</em> del hueco de tamaño <em>s_gap</em>. En el caso de que el conjunto de posiciones quede
      vacío, se borra el hueco en si.
    */ 
    static void borrar_pos(map<int, set<int>> &gap, int s_gap, int pos_gap);

    /** @brief Mirar el hueco de arriba. 

      \pre Entran como parámetros implícitos un iterador del mapa de procesos y el mapa de huecos.
      \post Si hay hueco entre el proceso actual y el procesador anterior, se borra ese hueco y lo retorna.
      En el caso contrario, retorna 0 (no hay hueco).
    */ 
    static int up_size (map<int,Proceso>::iterator it,map<int,set<int>>& gap);
    
    /** @brief Mirar el hueco de abajo. 

      \pre Mapa de procesos mem NO vacío, capacidad entero NO negativo.
      \post Si hay hueco entre el proceso actual y el procesador posterior, se borra ese hueco y lo retorna.
      En el caso de que no haya hueco o se trate del último proceso, retorna 0.
    */ 
    static int down_size(const map<int,Proceso> &mem, map<int,Proceso>::iterator it, int capacidad, map<int,set<int>>& gap);

    /** @brief Borrar proceso. 

      \pre mem NO vacío; capacidad y memoria_ocupada enteros NO negativos.
      \post Borra el proceso al cual apunta el iterador it y añade un nuevo hueco a gap.
    */
    static int borrar_proceso (map<int,Proceso> &mem, map<int,set<int>> &gap, int capacidad, int &memoria_ocupada, map<int,Proceso>::iterator &it);

public:
    /** @brief Creadora por defecto. 

      Se ejecuta automáticamente al declarar un procesador.
      \pre <em>cierto</em>
      \post El resultado es un procesador vacío.
    */
    CPU();

    //Modificadoras
    
    /** @brief Alta proceso procesador.

      \pre <em>cierto</em>.
      \post Si existe espacio en la memoria, añade el proceso al CPU y devuelve 0. 
      En caso contrario devuelve 3.
    */
    int alta_proceso (const Proceso &pro);

    /** @brief Borrar proceso del CPU. 

      \pre <em>ciert</em>.
      \post En el caso de encontrar un proceso con el mismo identificador, lo borra y devuelve 0.
      En el caso que no exista el proceso, devuelve 0.
    */
    int baja_proceso(int id_proceso);

    /** @brief Avanzar tiempo. 

      Avanzar el tiempo de todos los procesos de la CPU.
      \pre t >= 0.
      \post Avanza el tiempo de todos los procesos de la CPU. En caso de que el tiempo resultante no sea válido, se elimina el proceso.
    */
    void avanzar_tmp(int t);

    /** @brief Compactar la memoria del CPU. 

      \pre <em>cierto</em>.
      \post Compacta la memoria del procesador de manera que no deja huecos, los procesos no se solapan y se respeta el orden inicial.
    */
    void compactar();
    
    //Consultoras

    /** @brief Consultar capacidad. 

      \pre <em>cierto</em>.
      \post Retorna la capacidad de la CPU.
    */
    int consultar_capacidad() const;

    /** @brief Consultar la existencia de un proceso. 

      \pre <em>cierto</em>.
      \post True si existe un proceso con el mismo identificador. False en caso contrario.
    */
    bool existe(int id) const;

    /** @brief Consultar estado de ocupación. 

      \pre <em>cierto</em>.
      \post True si no contiene ningún proceso. False en caso contrario.
    */
    bool vacio () const;

    /** @brief Consultar memoria libre. 

      \pre <em>cierto</em>.
      \post Retorna la memoria libre de la cual dispone el CPU.
    */
    int consultar_memoria_libre() const;

    /** @brief Consultar hueco para proceso. 

      \pre <em>cierto</em>.
      \post En el caso de haber un hueco mayor o igual a <em>size</em> retorna este mismo. 
      En caso contrario retorna -1.
    */
    int espacio_para_proceso (int size) const; 
    
    /** @brief Procesos pendientes. 
      \pre <em>cierto</em>
      \post True si tiene procesos pendientes. False si no tiene procesos pendientes.
    */
    bool procesos_pendientes() const;
    
    //Entrada / Salida

    /** @brief Leer procesador. 

      \pre <em>cierto</em>
      \post El parámetro implícito es el procesador tomado del canal de entrada estándar.
    */
    void llegir();

    /** @brief Imprimir todos los procesos de un procesador. 

      \pre <em>cierto</em>
      \post Imprime todos los procesos junto al espacio que ocupan, el tiempo de ejecución que les falta y la posición en la memoria de cada uno,
      por el canal de salida estándar.
    */
    void imprimir() const;
};
#endif
