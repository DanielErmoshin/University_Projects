/** @file Cluster.hh
    @brief Especificación de la clase Cluster
*/

#ifndef CLUSTER_HH
#define CLUSTER_HH
#include "CPU.hh"
#ifndef NO_DIAGRAM
#include <map>
#include <queue>
#include <list>
#include "BinTree.hh"
#endif
using namespace std;

/** @class Cluster
    @brief Representa un Cluster. 

    Este clúster dispone de diferentes procesadores (CPU) ordenados por antigüedad.
*/
class Cluster {

private:

    /** @brief Parámetros necesarios para la selección del procesador más conveniente.
      
      Esta estructura tiene el propósito de facilitar la operación de enviar procesos cluster.
    */
    struct Parametros{
      string id;
      int hueco;
      int mem;
      int p;
    };
    /** @brief Árbol de procesadores */
    BinTree<string> cTree;
    /** @brief Mapa de procesadores. 
     
      La clave contiene el identificador del procesador y el segundo valor es el procesador como tal. 
    */
    map<string,CPU> cList;

    /** @brief Lectura de árbol. 

      Lee un árbol de manera recursiva y añade los procesos en el mapa de procesadores.
      \pre Entra un árbol vacío y un mapa vacío por el canal de entrada como parámetros.
      \post El árbol és NO vacío y esta organizado por el método de preorden. El mapa es NO vacío.
    */ 
    static void lectura_arbre (BinTree<string>& cTree, map<string,CPU>& cList);

    /** @brief Modificación de árbol. 

      Modifica el árbol añadiendo un nuevo nodo en el sitio de un nodo determinado.
      \pre Entran como parámetros un id de prioridad y un entero vacío auxiliar. Y como parámetros implícitos un árbol(principal) a modificar 
      y un árbol auxiliar.
      \post El árbol auxiliar es añadido como nodo del árbol principal si se cumple que del nodo modificado no salen otros nodos. 
      En caso de que el procesador contenga procesadores auxiliares, no se modifica el árbol principal.
    */ 
    static void modificar(const string &id_cpu, BinTree<string> &c, const BinTree<string> &c2, int &x);

    /** @brief Imprimir árbol. 

      \pre Entra como parametro un árbol NO vacío.
      \post Imprime los nodos del árbol utilizando el método de preorden por el canal de salida estándar.
    */ 
    static void imprimir (const BinTree<string>& cTree);

    /** @brief Función criterio para la operación enviar procesos cluster. 

      \pre Entran como parámetros un mapa, una estructura de párametros auxiliar, un proceso, un árbol 
      y un entero con valor inicial de 0.
      \post Devuelve un 0 si se ha podido encontrar el procesador más óptimo. 
      En el caso de no encontrarlo, devuelve un -1.
    */ 
    static int miramos (const map<string, CPU> &cList, Parametros &aux, const Proceso &pro, const BinTree<string> &c, int p);

public:
    //Creadora

    /** @brief Creadora por defecto. 

      Se ejecuta automáticamente al declarar un cluster.
      \pre <em>cierto</em>
      \post El resultado es un cluster vacío.
    */
    Cluster();

    //Modificadoras

    /** @brief Modificar un clúster NO vacío. 

      Modifica un clúster NO vacío.
      \pre Lectura de un identificador de un procesador y de un nuevo cluster previamente configurado.
      \post Si el procesador no existe devuelve 1. Si existe y tiene procesos pendientes devuelve 2. 
      Si existe y tiene procesadores auxiliares devuelve 3. En caso contario, es sustituido por el nuevo Cluster y devuelve 0.
    */
    int modificar_cluster(const string &id_cpu, Cluster &c2);
    
    /** @brief Alta de un proceso en un procesador. 

     Da de alta un proceso en un procesador específico.
      \pre Lectura de un id de procesador y un proceso.
      \post Hace un recorrido en el cluster en busca de un procesador con el mismo identificador. 
      Si lo encuentra y existe algún hueco con un espacio suficiente o no existe un proceso con el mismo id, se añade el proceso y devuelve 0. 
      En el caso que no exista un procesador con ese id devuelve 1. 
      Si hay un proceso con el mismo id en el procesador devuelve 2. 
      Si no hay espacio suficiente en la memoria devuelve 3.
    */ 
    int alta_proceso_procesador (const string &id_cpu, const Proceso &pro);


    /** @brief Baja de un proceso en un procesador. 

      Da de baja un proceso en unprocesador específico.
      \pre Lectura de un id de procesador y un proceso.
      \post Hace un recorrido en el cluster en busca de un procesador con el mismo identificador. Si lo 
      encuentra y el proceso esta dentro del procesador, lo elimina y devuelve 0. 
      En el caso de que no exista un procesador con el mismo id devuelve 1. Si el proceso no se encuentre en el procesador devuelve 2.
    */
    int baja_proceso_procesador (const string &id_cpu, int id_proceso);

    /** @brief Avanzar el tiempo global. 

      Avanza el tiempo de todos los procesos.
      \pre Entero positivo t.
      \post Hace un recorrido por todos los procesadores del cluster, avanzando (restando) el tiempo de todos los procesos. 
      Si los procesos han acabado durante ese intervalo de tiempo (resta <= 0), se eliminan del procesador. En caso contrario permanecen.
    */ 
    void avanzar_tiempo(int t);   
    
    /** @brief Compactar la memoria de un procesador determinado. 

      \pre Identificador de un procesador.
      \post En caso de que existe un procesador con el mismo id en el clúster, 
      se mueven todos los procesos hacia el principio de la memoria del procesador, sin dejar huecos, 
      sin solaparse y respetando el orden inicial, y devuelve 0.
      En caso contrario devuelve 1.
    */
    int compactar_memoria_procesador(const string &id_cpu);

    /** @brief Compactar la memoria de todos los procesadores del clúster. 

      \pre <em>cierto</em>.
      \post Se mueven todos los procesos hacia el principio de la memoria de cada procesador del clúster, 
      sin dejar huecos, sin solaparse y respetando el orden inicial.
    */
    void compactar_memoria_cluster(); 
    
    /** @brief Enviar un proceso al procesador. 

      \pre Entra como parámetro un Proceso pro.
      \post Devuelve 0 si el proceso se ha metido en el procesador óptimo.
      En caso contrario devuelve un -1.
    */
    int enviar_proceso_procesador (const Proceso &pro);

    //Consultoras
    
    /** @brief Consultora de Procesadores. 

    
      \pre <em>cierto</em>
      \post Retorna una copia de la estructura encargada de guardar los procesadores del Cluster.
    */
    map<string, CPU> my_map() const;

    /** @brief Consultara de estructura. 

    
      \pre <em>cierto</em>
      \post Retorna una copia de la estructura del Cluster.
    */
    BinTree<string> my_tree() const;

    //Entrada / Salida

    /** @brief Configurar un clúster vacío. 

    Configura un clúster vacío.
      \pre Parámetro implícito Cluster esta vacío.
      \post Clúster configurado mediante la lecutra de determinados porcesadores, sus conexiones y la memoria de cada uno.
      En caso de que exista un clúster anterior a la llamada de la función, este queda eliminado.
    */
    void configurar_cluster ();

    /** @brief Imprimir los procesos de un procesador determinado. 

      Imprime los procesos de un procesador determinado en orden junto a su posición en la memoria
      \pre Identificador de procesador.
      \post En caso de que exista un procesador con ese id, imprime los procesos junto al espacio que ocupan, 
      el tiempo de ejecución que les falta y la posición en la memoria de cada uno, y devuelve 0.
      En caso contrario devuelve 1. 
    */
    int imprimir_procesador(const string &id_cpu) const;

    /** @brief Imprimir los procesos de cada procesador del clúster. 

      \pre <em>cierto</em>
      \post Imprime los procesos de cada procesador del clúster junto al espacio que ocupan, el tiempo de ejecución que les falta 
      y la posición en la memoria de cada uno en el procesador determinado por el canal de salida estándar.
    */
    void imprimir_procesadores_cluster() const;

    /** @brief Imprimir la estructura del clúster. 

      \pre <em>cierto</em>
      \post Imprime la estructura del clúster en formato PREORDEN por el canal de salida estándar.
    */
    void imprimir_estructura_cluster() const;
};
#endif