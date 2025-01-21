package main.controllers;

import main.exceptions.MyException;

import java.util.InputMismatchException;


public class CtrlPresentacio {

    private static final CtrlDomini ctrlDomini = CtrlDomini.getInstance();
    private static final CtrlIO IO = new CtrlIO();


    public static void execucio() {
        mostrarOpcions();
        String opcio;
        do {
            opcio = IO.readLine();
            switch (opcio) {
                case "cplan": crearPlantilla(); break;
                case "eplan": eliminarPlantilla(); break;
                case "mplan": setNomPlantilla(); break;
                case "gplan": guardarPlantilla(); break;
                case "gplanNou": guardarPlantillaNova(); break;

                case "cprod": crearProducte(); break;
                case "eprod": eliminarProducte(); break;
                case "mprod": modificarProducte(); break;
                case "prod": mostrarLlistaProductes(); break;

                case "ccat": crearCategoria(); break;
                case "ecat": eliminarCategoria(); break;
                case "mcat": editarDescripcioCategoria(); break;
                case "cat": mostrarLlistaCategories(); break;

                case "cpres": crearPrestatgeria(); break;
                case "epres": modificarPrestatgeria(); break;
                case "mpres": eliminarPrestatgeria(); break;
                case "pres": mostrarLlistaPrestatgeries(); break;

                case "csim": afegirSimilitud(); break;
                case "esim": eliminarSimilitud(); break;
                case "sim": mostrarLlistaSimilituds(); break;

                case "opcions": mostrarOpcions(); break;
                case "exit": System.out.println("Sortint..."); break;
                default: System.out.println("Opcio no valida."); break;
            }
        } while (!opcio.equals("exit"));
        IO.closeScanner();
    }

    private static void crearPlantilla() {
        try {
            System.out.print("Introdueix el nom de la plantilla: ");
            String nom = IO.readLine();
            ctrlDomini.crearPlantilla(nom);
            System.out.println("Plantilla creada amb exit.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void eliminarPlantilla() {
        try {
            System.out.print("Estas segur? SI/NO ");
            String resp = IO.readLine();
            if (resp.equals("SI")) {
                ctrlDomini.eliminarPlantilla();
                System.out.println("Plantilla eliminada amb exit.");
            } else System.out.println("Plantilla no eliminada.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void setNomPlantilla() {
        try {
            System.out.print("Introdueix el nou nom de la plantilla: ");
            String nom = IO.readLine();
            ctrlDomini.setNomPlantilla(nom);
            System.out.println("Plantilla modificada amb exit.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void guardarPlantilla() {
        try {
            ctrlDomini.guardarPlantilla();
            System.out.println("Plantilla guardada amb exit.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void guardarPlantillaNova() {
        try {
            ctrlDomini.guardarPlantillaNova();
            System.out.println("Plantilla nova guardada amb exit.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void crearProducte() {
        try {
            System.out.print("Introdueix les dades del producte (ID, nom, categoria, preu): ");
            int id = IO.readInt();
            String nom = IO.readString();
            String categoria = IO.readString();
            double preu = IO.readDouble();
            IO.readLine();
            ctrlDomini.crearProducte(id, nom, categoria, preu);
            System.out.println("Producte creat amb exit.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.err.println("Error con el tipo de variable introducido.");
        }
    }

    private static void eliminarProducte() {
        try {
            System.out.print("Introdueix l'ID del producte a eliminar: ");
            int id = IO.readInt();
            IO.readLine();
            ctrlDomini.eliminarProducte(id);
            System.out.println("Producte eliminat amb exit.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.err.println("Error con el tipo de variable introducido.");
        }
    }

    private static void modificarProducte() {
        try {
            System.out.print("Introdueix l'ID del producte a modificar: ");
            int id = IO.readInt();
            IO.readLine();
            System.out.print("Introdueix el nou nom i preu del producte: ");
            String nouNom = IO.readLine();
            double nouPreu = IO.readDouble();
            IO.readLine();
            ctrlDomini.modificarProducte(id, nouNom, nouPreu);
            System.out.println("Producte modificat amb exit.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.err.println("Error con el tipo de variable introducido.");
        }
    }

    private static void mostrarLlistaProductes() {
        try {
            System.out.print("Aquesta es la llista de productes:\n");
            System.out.print(ctrlDomini.getAtributs_Productes());
            System.out.print("Llista generada amb exit. ");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void crearCategoria() {
        try {
            System.out.print("Introdueix el nom de la categoria: ");
            String nom = IO.readLine();
            ctrlDomini.crearCategoria(nom);
            System.out.println("Categoria creada amb exit.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void eliminarCategoria() {
        try {
            System.out.print("Introdueix el nom de la categoria a eliminar: ");
            String nom = IO.readLine();
            ctrlDomini.eliminarCategoria(nom);
            System.out.println("Categoria eliminada amb exit.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void editarDescripcioCategoria() {
        try {
            System.out.print("Introdueix el nom de la categoria: ");
            String nom = IO.readLine();
            System.out.print("Introdueix la nova descripcio de la categoria: ");
            String novaDescripcio = IO.readLine();
            ctrlDomini.editarDescripcioCategoria(nom, novaDescripcio);
            System.out.println("Descripcio de la categoria actualitzada.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void mostrarLlistaCategories() {
        try {
            System.out.print("Aquesta es la llista de categories:\n");
            System.out.print(ctrlDomini.getAtributs_Categories());
            System.out.print("Llista generada amb exit. ");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void crearPrestatgeria() {
        try {
            System.out.print("Introdueix l'ID de la prestatgeria i el nombre de prestatges: ");
            int id = IO.readInt();
            int numPrestatges = IO.readInt();
            IO.readLine();
            ctrlDomini.crearPrestatgeria(id, numPrestatges);
            System.out.println("Prestatgeria creada amb exit.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.err.println("Error con el tipo de variable introducido.");
        }
    }

    private static void modificarPrestatgeria() {
        try {
            System.out.print("Introdueix l'ID de la prestatgeria a modificar i el nou nombre de prestatges: ");
            int id = IO.readInt();
            int numPrestatges = IO.readInt();
            IO.readLine();
            ctrlDomini.modificarNumPrestatges(id, numPrestatges);
            System.out.println("Nombre de prestatges modificat amb exit.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.err.println("Error con el tipo de variable introducido.");
        }
    }

    private static void eliminarPrestatgeria() {
        try {
            System.out.print("Introdueix l'ID de la prestatgeria a eliminar: ");
            int id = IO.readInt();
            IO.readLine();
            ctrlDomini.eliminarPrestatgeria(id);
            System.out.println("Prestatgeria eliminada amb exit.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.err.println("Error con el tipo de variable introducido.");
        }
    }

    private static void mostrarLlistaPrestatgeries() {
        try {
            System.out.print("Aquesta es la llista de prestatgeries:\n");
            System.out.print(ctrlDomini.getAtributs_Prestatges());
            System.out.print("Llista generada amb exit. ");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void afegirSimilitud() {
        try {
            System.out.print("Introdueix l'ID del primer producte, l'ID del segon i el grau de similitud: ");
            int id1 = IO.readInt();
            int id2 = IO.readInt();
            double grauSimilitud = IO.readDouble();
            IO.readLine();
            ctrlDomini.afegirSimilitud(id1, id2, grauSimilitud);
            System.out.println("Similitud afegida amb exit.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.err.println("Error con el tipo de variable introducido.");
        }
    }

    private static void eliminarSimilitud() {
        try {
            System.out.print("Introdueix l'ID del primer i segon producte: ");
            int id1 = IO.readInt();
            int id2 = IO.readInt();
            IO.readLine();
            ctrlDomini.eliminarSimilitud(id1, id2);
            System.out.println("Similitud eliminada amb exit.");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.err.println("Error con el tipo de variable introducido.");
        }
    }

    private static void mostrarLlistaSimilituds() {
        try {
            System.out.print("Aquesta es la llista de similituds:\n");
            System.out.print(ctrlDomini.getAtributs_Similitud());
            System.out.print("Llista generada amb exit. ");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void mostrarOpcions() {
        System.out.println("\nSeleccioneu una opcio:");
        System.out.println("cplan: Crear plantilla");
        System.out.println("eplan: Eliminar plantilla");
        System.out.println("mplan: Modificar nom plantilla");
        System.out.println("gplanNou: Guardar plantilla nova");
        System.out.println("gplanAct: Guardar plantilla actual");

        System.out.println("cprod: Crear producte");
        System.out.println("eprod: Eliminar producte");
        System.out.println("mprod: Modificar producte");
        System.out.println("prod: Mostrar llista de productes (no funciona)");

        System.out.println("ccat: Crear categoria");
        System.out.println("ccat2: Crear categoria amb descripció");
        System.out.println("ecat: Eliminar categoria");
        System.out.println("mcat: Editar descripcio de categoria");
        System.out.println("cat: Mostrar llista de categories (no funciona)");

        System.out.println("cpres: Crear prestatgeria");
        System.out.println("epres: Eliminar prestatgeria");
        System.out.println("mpres: Modificar prestatgeria");
        System.out.println("pres: Mostrar llista de prestatgeries (no funciona)");

        System.out.println("csim: Afegir similitud entre productes");
        System.out.println("esim: Eliminar similitud entre productes");
        System.out.println("sim: Mostrar llista de similituds (no funciona)");

        System.out.println("opcions: Veure les opciones possibles");
        System.out.println("exit: Sortir");
    }

}
