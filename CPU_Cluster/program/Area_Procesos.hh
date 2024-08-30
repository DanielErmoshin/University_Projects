/** @file Area_Procesos.hh
    @brief Especificación de la clase Area_Procesos
*/

#ifndef Area_Procesos_HH
#define Area_Procesos_HH
#include "Priority.hh"
#include "Cluster.hh"
#ifndef NO_DIAGRAM
#include <map>
#endif

using namespace std;

/** @class Area_Procesos
    @brief Representa un Area de Procesos pendientes. 

    Esta área dispone de diferentes Prioridades ordenadas por nombre de manera ascendente. Ofrece operaciones
    para acceder, imprimir y leer Prioridades.
*/
class Area_Procesos {

private:
    /* Para la buena gestión del Area de Procesos pendientes, utilizaremos mapas para guardar las Prioridades, 
    de manera que estas siempre estarán ordenadas por nombre de manera creciente en el Area. */

    /** @brief Mapa de Prioridades. 

      Contiene todas las Prioridades que hayan sido dadas de alta (y no contiene a las que se hayan dado de baja, respectivamente) 
    */
    map<string,Priority> area;

public:
    //Constructoras

    /** @brief Creadora por defecto. 

      Se ejecuta automáticamente al declarar una Area de Procesos.
      \pre <em>cierto</em>
      \post El resultado es una area de procesos pendientes vacía.
    */
    Area_Procesos();

    //Modificadoras

    /** @brief Alta de una prioridad. 

      Da de alta una prioridad.
      \pre El parámetro id_pri es un identificador de una prioridad.
      \post Hace un recorrido de las prioridades existentes en busca de una con el mismo id que el parámetro id_pri.
      Si no existe tal prioridad en el area de procesos, retorna 1. 
      En caso contrario se añade una prioridad vacíaa con ese identificador y retorna 0.
    */  
    int alta_prioridad (const string &id_pri);

    /** @brief Baja de una prioridad. 

      Da de baja una prioridad.
      \pre El parámetro id_pri es un identificador de una prioridad.
      \post Hace un recorrido de las prioridades existentes en busca de una con el mismo id que el parámetro implícito.
      Si existe una prioridad con el mismo identificador y no tiene procesos pendientes, se elimina la prioridad y devuelve 0. 
      En caso de que no exista ninguna prioridad con ese id devuelve 1. 
      En el caso de que la prioridad tenga procesos pendientes, devuelve 2. 
    */  
    int baja_prioridad (const string &id_pri);

    /** @brief Alta de un proceso en el Area de Procesos pendientes. 

      Da de alta un proceso en una prioridad especifica.
      \pre pro es un proceso NO vacío y el parámetro id_pri es un identificador de una prioridad.
      \post Hace un recorrido en el area de procesos en busca de una prioridad con el mismo identificador que el parámetro implícito. 
      Si lo encuentra y no existe ningún proceso con el mismo identificador que de pro, se añade el proceso y devuelve 0. 
      En el caso de que no exista una prioridad con ese id, devuelve un 1. 
      En el caso de que ya exista un proceso con el mismo id, devuelve un 2.
    */ 
    int alta_proceso_espera (const Proceso &pro, const string &id_pri);

    /** @brief Envia n procesos de las prioridades al cluster. 

      Distribuye n procesos en el clúster siguiendo un determinado criterio.
      \pre Entero no negativo <em>n</em> y Cluster NO vacío c.
      \post Envía n procesos de las prioridades (por orden de prioridad) al Cluster c
      y este escoge un procesador para meter el n proceso, siguiendo el siguiente criterio:
      Escoge el procesador con el hueco más ajustado. 
      En caso de empate, escoge el procesador que tenga la memoria libre mas grande.
      En caso de empate, escoge el procesador que este a menor profundidad. 
      Y por último, si hay caso de empate, escoge el procesador siguiendo el método de preorden 
      (el que se encuentre lo más a la izquierda posible).
    */ 
    void enviar_procesos_cluster (int n, Cluster &c);

    // Entrada / Salida   
    
    /** @brief Inizializador del area de procesos. 

      Inicializa el area de procesos. 
      \pre Lectura de N > 0 Prioridades y los identificadores de las N prioridades.
      \post El resultado es una Area inicializada con N prioridades.
    */  
    void inicializar();

    /** @brief Imprime los procesos pendientes de una prioridad. 

      Imprime los procesos pendientes de una prioridad y los procesos enviados y rechazados por el clúster.
      \pre Identificador de prioridad id_pri.
      \post En caso de que exista una prioridad con ese id, imprime los procesos pendientes por orden 
      decreciente de antigüedad por el canal de salida estándar. y devuelve 0. 
      En caso contrario devuelve 1.
    */
    int imprimir_prioridad (const string &id_pri);

    /** @brief Imprime los procesos pendientes del area de procesos. 

      Imprime los procesos pendientes de todas las prioridades junto a los procesos enviados y rechazados por el clúster de cada una de ellas.
      \pre <em>cierto</em>.
      \post Imprime los procesos pendientes de todas las prioridades del área de procesos por orden creciente de prioridad. 
      Para cada prioridad imprime lo mismo que la función <em>imprimir_prioridad</em>. 
    */
    void imprimir_area_espera ();

};
#endif