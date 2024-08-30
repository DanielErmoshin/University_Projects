  /** @file Proceso.hh
    @brief Especificación de la clase Proceso
*/

#ifndef PROCESO_HH
#define PROCESO_HH
#ifndef NO_DIAGRAM
#include <iostream>
#endif

using namespace std;

/** @class Proceso
    @brief Representa un Proceso. 

    Este proceso dispone de un identificador, el espacio que ocupa y el tiempo que tarda en ejecutarse.
*/
class Proceso {
private:
    /** @brief Identificador del proceso */
    int id_proceso;
    /** @brief Tamaño del proceso */
    int space;
    /** @brief Tiempo de ejecución del proceso */
    int t_ej;

public:

    //Creadoras

    /** @brief Creadora por defecto. 

      Se ejecuta automáticamente al declarar un proceso.
      \pre <em>cierto</em>
      \post El resultado es un proceso vacío.
    */
    Proceso();

    //Modificadoras

    /** @brief Avanzar el tiempo de ejecución. 

      \pre t >= 0.
      \post Resta el entero t al tiempo de ejecución. Si el resultado es menor o igual a 0 devuelve -1. En caso contrario, devuelve 0.
    */
    int avanzar_t(int t);

    //Consultoras

    /** @brief Consultora de identificador. 

      \pre <em>cierto</em>
      \post Devuelve el identificador del proceso.
    */
    int consultar_id() const;

    /** @brief Consultora de tamaño. 

      \pre <em>cierto</em>
      \post Devuelve el tamaño del proceso.
    */
    int consultar_space() const;

    //Entrada / Salida

    /** @brief Operación de lectura. 

      \pre Entra el identificador, el tamaño y el tiempo de ejecución por el canal de entrada principal.
      \post Proceso NO vacío.
    */
    void leer();

    /** @brief Operación de escritura. 

      \pre <em>cierto</em>
      \post Imprime el identificador del proceso, el espacio que ocupa y el tiempo de ejecución que le queda.
    */
    void escribir() const;
};
#endif