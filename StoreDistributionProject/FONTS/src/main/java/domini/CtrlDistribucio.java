package domini;

import domini.distributions.Distribution;
import domini.distributions.DistributionAprox;
import domini.distributions.DistributionFuerzaBruta;
import exceptions.MyException;
import java.util.List;

import domini.models.Prestatgeria;
import domini.models.Producte;

import java.util.HashMap;

/**
 * Aquesta classe representa el controlador de la distribució.
 * Aquest controlador permet calcular la distribució de productes en prestatges.
 */
public class CtrlDistribucio {

    /**
     * L'única instància de la classe CtrlDistribucio.
     */
    private static CtrlDistribucio singletonObject;

    /**
     * La distribució de força bruta actual.
     */
    private Distribution distribucioBrutaActual = null;

    /**
     * La distribució aproximada actual.
     */
    private Distribution distribucioAproximadaActual = null;

    /**
     * Obte l'única instància de la classe CtrlDistribucio.
     * @return L'única instància de la classe CtrlDistribucio.
     */
    public static CtrlDistribucio getInstance() {
        if (singletonObject == null) {
            singletonObject = new CtrlDistribucio();
        }
        return singletonObject;
    }

    /**
     * Crea una nova instància de la classe CtrlDistribucio.
     */
    private CtrlDistribucio() {}

    /**
     * Calcula la distribució de productes en prestatges mitjançant força bruta.
     * @param llistaProductes La llista de productes de la plantilla
     * @param llistaPrestatges La llista de prestatges de la plantilla
     * @param num_total_prestatges El número total de prestatges de la plantilla
     * @throws MyException Si el número de prestatges és diferent del número de productes
     */
    public void calcularDistribucioBruta(HashMap<Integer, Producte> llistaProductes, HashMap<Integer, Prestatgeria> llistaPrestatges, int num_total_prestatges) throws MyException {
        if (llistaProductes.size() != num_total_prestatges) {
            throw new MyException("El numero de prestatges es diferent al numero de productes");
        }
        distribucioBrutaActual = new DistributionFuerzaBruta(llistaProductes,llistaPrestatges);
        distribucioBrutaActual.calcularDist();
    }

    /**
     * Calcula la distribució de productes en prestatges mitjançant aproximació.
     * @param llistaProductes La llista de productes de la plantilla
     * @param llistaPrestatges La llista de prestatges de la plantilla
     * @param num_total_prestatges El número total de prestatges de la plantilla
     * @param iterations El número d'iteracions
     * @throws MyException Si el número de prestatges és diferent del número de productes
     */
    public void calcularDistribucioAproximada(HashMap<Integer, Producte> llistaProductes, HashMap<Integer, Prestatgeria> llistaPrestatges, int num_total_prestatges, int iterations) throws MyException {
        if (llistaProductes.size() != num_total_prestatges) {
            throw new MyException("El numero de prestatges es diferent al numero de productes");
        }
        distribucioAproximadaActual = new DistributionAprox(llistaProductes,llistaPrestatges, iterations);
        distribucioAproximadaActual.calcularDist();
    }

    /**
     * Comprova si la distribució de força bruta està activa.
     * @return Cert si la distribució de força bruta està activa
     */
    public boolean DistribucioBrutaEstaActiva() {
        if (distribucioBrutaActual == null) return false;
        return true;
    }

    /**
     * Comprova si la distribució d'aproximació està activa.
     * @return Cert si la distribució d'aproximació està activa
     */
    public boolean DistribucioAproximadaEstaActiva() {
        if (distribucioAproximadaActual == null) return false;
        return true;
    }

    /**
     * Elimina la distribució de força bruta.
     * @throws MyException Si no hi ha una distribució de força bruta activa
     */
    public void eliminarDistribucioBruta() throws MyException {
        if(!DistribucioBrutaEstaActiva()) throw new MyException("No hi ha una distribució de força bruta activa.");;
        distribucioBrutaActual = null;
    }

    /**
     * Elimina la distribució d'aproximació.
     * @throws MyException Si no hi ha una distribució d'aproximació activa
     */
    public void eliminarDistribucioAproximada() throws MyException {
        if(!DistribucioAproximadaEstaActiva()) throw new MyException("No hi ha una distribució d'aproximació activa.");;
        distribucioAproximadaActual = null;
    }

    /**
     * Modifica la distribució d'aproximació, canviant la distribució de dos productes.
     * @param id1 Identificador del primer producte
     * @param id2 Identificador del segon producte
     * @throws MyException Si no hi ha una distribució d'aproximació activa
     */
    public void modificarDistribucioAproximada(int id1, int id2) throws MyException {
        if(!DistribucioAproximadaEstaActiva()) {
            throw new MyException("No hi ha una distribució d'aproximació activa.");
        }
        distribucioAproximadaActual.modificarDistribucio(id1, id2);
    }

    /**
     * Modifica la distribució de força bruta, canviant la distribució de dos productes.
     * @param id1 Identificador del primer producte
     * @param id2 Identificador del segon producte
     * @throws MyException Si no hi ha una distribució de força bruta activa
     */
    public void modificarDistribucioBruta(int id1, int id2) throws MyException {
        if(!DistribucioBrutaEstaActiva()) {
            throw new MyException("No hi ha una distribució de força bruta activa.");
        }
        distribucioBrutaActual.modificarDistribucio(id1, id2);
    }

    /**
     * Obte la distribució de força bruta en format de llista de llistes de strings, on cada llista de strings representa una prestatgeria i hi conté els identificadors dels productes.
     * @return La distribució de força bruta
     * @throws MyException Si no hi ha una distribució de força bruta activa
     */
    public List<List<String>> imprimirDistribucioBrutaPerId() throws MyException {
        if(!DistribucioBrutaEstaActiva()) {
            throw new MyException("No hi ha una distribució de força bruta activa.");
        }
         return distribucioBrutaActual.imprimirPerID();
    }

    /**
     * Obte la distribució de força bruta en format de llista de llistes de strings, on cada llista de strings representa una prestatgeria i hi conté els noms dels productes.
     * @return La distribució de força bruta
     * @throws MyException Si no hi ha una distribució de força bruta activa
     */
    public List<List<String>> imprimirDistribucioBrutaPerNom() throws MyException {
        if(!DistribucioBrutaEstaActiva()) {
            throw new MyException("No hi ha una distribució de força bruta activa.");
        }
        return distribucioBrutaActual.imprimirPerNom();
    }

    /**
     * Obte la distribució d'aproximació en format de llista de llistes de strings, on cada llista de strings representa una prestatgeria i hi conté els identificadors dels productes.
     * @return La distribució d'aproximació
     * @throws MyException Si no hi ha una distribució d'aproximació activa
     */
    public List<List<String>> imprimirDistribucioAproximadaPerId() throws MyException {
        if(!DistribucioAproximadaEstaActiva()) {
            throw new MyException("No hi ha una distribució d'aproximació activa.");
        }
        return distribucioAproximadaActual.imprimirPerID();
    }

    /**
     * Obte la distribució d'aproximació en format de llista de llistes de strings, on cada llista de strings representa una prestatgeria i hi conté els noms dels productes.
     * @return La distribució d'aproximació
     * @throws MyException Si no hi ha una distribució d'aproximació activa
     */
    public List<List<String>> imprimirDistribucioAproximadaPerNom() throws MyException {
        if(!DistribucioAproximadaEstaActiva()) {
            throw new MyException("No hi ha una distribució d'aproximació activa.");
        }
        return distribucioAproximadaActual.imprimirPerNom();
    }
}