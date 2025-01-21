/*
import distributions.Distribution;
import distributions.DistributionFuerzaBruta;
import distributions.DistributionEficiente;
import models.Product;

public class CtrlDistribucio {
    private static CtrlDistribucio singletonObject;

    public static CtrlDistribucio getInstance() {
        if (singletonObject == null) {
            singletonObject = new CtrlDistribucio();
        }
        return singletonObject;
    }

    private CtrlDistribucio() {}

    private Plantilla plantillaActual;
    private Distribution distribucioBrutaActual;
    private Distribution distribucioEficientActual;

    public void crearDistribucio(int ID, String nombre, String path, Plantilla originalPlantilla) {
        distribucioBrutaActual = new DistributionFuerzaBruta(ID, nombre, path, originalPlantilla);
        distribucioEficientActual = new DistributionEficiente(ID, nombre, path, originalPlantilla);
    }

    public Distribution obtindreDistribucioBrutaActual() {
        if (distribucioActual == null) {
            throw new IllegalStateException("No hay ninguna distribucion activa.");
        }
        return distribucioBrutaActual;
    }

    public Distribution obtindredistribucioEficientActual() {
        if (distribucioActual == null) {
            throw new IllegalStateException("No hay ninguna distribucion activa.");
        }
        return distribucioEficientActual;
    }

    public void setNombreDistribucion(String nuevoNombre, boolean esFuerzaBruta) {
        if (esFuerzaBruta) {
            if (distribucioBrutaActual == null) {
                throw new IllegalStateException("No hay ninguna distribucion activa.");
            }
            distribucioBrutaActual.setNom(nuevoNombre);
        }
        else {
            if (distribucioEficientActual == null) {
                throw new IllegalStateException("No hay ninguna distribucion activa.");
            }
            distribucioEficientActual.setNom(nuevoNombre);
        }
    }

    public void setPathDistribucion(String nuevoPath, boolean esFuerzaBruta) {
        if (esFuerzaBruta) {
            distribucioBrutaActual.setPath(nuevoPath);
        }
        else {
            distribucioEficientActual.setPath(nuevoPath);
        }
    }

    public String nombreDistribucionBrutaActual() {
        distribucioBrutaActual.getNombre();
    }

    public String nombreDistribucionEficientActual() {
        distribucioEficientActual.getNombre();
    }

    public int IDDistribucionActual(boolean esFuerzaBruta) {

        if (esFuerzaBruta) {
            distribucioBrutaActual.getID();
        }
        else {
            distribucioEficientActual.getID();
        }
    }

    // IMPLEMENTAR EN CtrlDomini
    public void guardarDistribucion() {
        if (distribucioActual == null) {
            throw new IllegalStateException("No hay ninguna distribucion activa.");
        }
        CtrlDomini.getInstance().gestionarGuardarDistribucion(distribucioActual, false);
    }

    / IMPLEMENTAR EN CtrlDomini
    public void guardarNuevoDistribucion() {
        if (distribucioActual == null) {
            throw new IllegalStateException("No hay ninguna distribucion activa.");
        }
        CtrlDomini.getInstance().gestionarGuardarNuevoDistribucion(distribucioActual, true);
    }

    public Plantilla obtenerPlantillaActual() {
        if (plantillaActual == null) {
            throw new IllegalStateException("No hay ninguna plantilla activa.");
        }
        return plantillaActual;
    }

    public void guardarPlantilla(Plantilla plantilla) {
        plantillaActual = plantilla;
    }
} */