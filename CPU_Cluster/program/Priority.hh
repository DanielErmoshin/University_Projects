/** @file Priority.hh
    @brief Especificación de la clase Priority
*/

#ifndef PRIORITY_HH
#define PRIORITY_HH
#ifndef NO_DIAGRAM
#include <list>
#endif
#include "Proceso.hh"
#include "Cluster.hh"
using namespace std;

/** @class Priority
    @brief Representa una Prioridad. 

    Esta Prioridad dispone de una serie de procesos "pendientes" ordenados por antigüedad.
*/
class Priority {

private:
    /** @brief Lista de procesos. 
     
      Contiene todos los procesos "pendientes" de la prioridad. 
    */
    list <Proceso> p_pendientes;
    /** @brief Número de procesos enviados al Cluster */
    int enviados;
    /** @brief Número de procesos rechazados al Cluster */
    int rechazados;

public:

    //Creadoras

    /** @brief Creadora por defecto. 

      Se ejecuta automáticamente al declarar una prioridad.
      \pre <em>cierto</em>
      \post El resultado es una prioridad vacía.
    */
    Priority(); 

    //Modificadoras

    /** @brief Enviar procesos al Cluster. 

      Envía los procesos de la prioridad al Cluster.
      \pre Cluster <em>c</em>; n > 0.
      \post n es el número de procesos que se han enviado al Cluster.
      El Cluster c se ve modificado en función de n.
    */
    void enviar_procesos(int &n, Cluster& c);

    /** @brief Consultar proceso específico. 
      \pre <em>cierto</em>.
      \post Devuelve -1 si el proceso pro existe.
      En caso contrario, inserta el proceso pro en la prioridad y devuelve 0.
    */
    int existe_proceso (const Proceso &pro);

    //Consultoras    

    /** @brief Consultar existencia de procesos pendientes. 
      \pre <em>cierto</em>
      \post True si tiene procesos pendientes. False si la prioridad está vacía.
    */
    bool procesos_pendientes() const;
    
    //Entrada / Salida
    
    /** @brief Imprimir los procesos enviados y rechazados durante el envío de procesos a cluster.
      \pre <em>cierto</em>
      \post Escribe el número de procesos enviados y rechazados por el Cluster.
    */
    void imprimir_envios() const;

    /** @brief Imprimir prioridad.
      \pre <em>cierto</em>
      \post Escribe los procesos pendientes de la prioridad.
    */
    void imprimir_p() const;
};
#endif