package spring.hibernate;

import java.util.*;

public class DataSource {

    public static boolean isDatabaseConnection = Boolean.FALSE;

    public static void supplyDatabase() {
        HibernateDao hibernateDao = null;

        try {
            hibernateDao = new HibernateDao();
            isDatabaseConnection = Boolean.TRUE;
        } catch (NullPointerException e) {
            System.out.println("No database connection.");
            e.getStackTrace();
        }

        Employees employee1 = new Employees("Piotr", "Pawlak", "Grójecka 28", "Warszawa", 1000, 18, new Date(), 0, "piotr.p@ourcompany.pl");
        Employees employee2 = new Employees("Paweł", "Kaczyński", "Zielona 28", "Kraków", 2000, 28, new Date(), 1, "pawel@ourcompany.pl");
        Employees employee3 = new Employees("Anna", "Pawlak", "Grójecka 28", "Warszawa", 3000, 45, new Date(), 1, "anna@ourcompany.pl");
        Employees employee4 = new Employees("Katarzyna", "Gierszałt", "Marymoncka 28", "Gdynia", 4000, 39, new Date(), 1, "katarzyna@ourcompany.pl");
        Employees employee5 = new Employees("Maciej", "Józefowicz", "Koszykowa 28", "Warszawa", 5000, 31, new Date(), 1, "maciej@ourcompany.pl");
        Employees employee6 = new Employees("Genowefa", "Pigwa", "Rybickiego 128", "Zamość", 6000, 29, new Date(), 1, "genowefa@ourcompany.pl");
        Employees employee7 = new Employees("Piotr", "Złomczyński", "Szucha 8", "Warszawa", 1500, 18, new Date(), 0, "piotr.z@ourcompany.pl");

        // sprawdzamy, czy pracownicy są już w bazie czy nie
        List<Employees> verificationList = hibernateDao.get(Employees.class);

        List<Employees> listToAdd = new ArrayList<>(Arrays.asList(employee1, employee2, employee3, employee4, employee5, employee6, employee7));
        listToAdd.removeAll(verificationList);

        for (Employees employee : listToAdd) {
            hibernateDao.saveEntity(employee);
        }

        Cars car1 = new Cars(employee6, "Audi", "B4", new Date());
        Cars car2 = new Cars(employee2, "BMW", "525d", new Date());
        Cars car3 = new Cars(employee3, "Chrysler", "Voyager", new Date());
        Cars car4 = new Cars(employee4, "Autko", "Czerwone", new Date());
        Cars car5 = new Cars(employee5, "VW", "Golfoik w tedeiku", new Date());

        hibernateDao.saveEntity(car1);
        hibernateDao.saveEntity(car2);
        hibernateDao.saveEntity(car3);
        hibernateDao.saveEntity(car4);
        hibernateDao.saveEntity(car5);

        Set<Employees> setForPrinter1 = new HashSet<>(listToAdd);
        Set<Employees> setForPrinter2 = new HashSet<>(Arrays.asList(employee1, employee6, employee7));

        Printers printer1 = new Printers(setForPrinter1, "Cannon ", "C01");
        Printers printer2 = new Printers("Lexmark", "L02");
        Printers printer3 = new Printers(setForPrinter2, "HP", "HP03");

        List<Printers> printersVerificationList = hibernateDao.get(Printers.class);

        List<Printers> printersListToAdd = new ArrayList<>(Arrays.asList(printer1, printer2, printer3));
        printersListToAdd.removeAll(printersVerificationList);

        for (Printers printer : printersListToAdd) {
            hibernateDao.saveEntity(printer);
        }

    }


}
