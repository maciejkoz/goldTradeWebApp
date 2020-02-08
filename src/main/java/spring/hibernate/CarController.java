package spring.hibernate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("car")
public class CarController {
    private List<Cars> carsList;
    private List<Employees> employeesList;
    private HibernateDao hibernateDao;

    public CarController() {
        try {
            hibernateDao = new HibernateDao();
            DataSource.supplyDatabase();
            employeesList = hibernateDao.get(Employees.class);
            carsList = hibernateDao.get(Cars.class);
        } catch (NullPointerException exception) {
            System.out.println("No connection with database");
            exception.getMessage();

            employeesList = new ArrayList<>();
            Employees employee1 = new Employees(1, "Jan", "Kran", "Długa 1", "Warszawa", 40, 3000, new Date(), 1);
            Employees employee2 = new Employees(2, "Zbigniew", "Miednica", "Emaliowa 2", "Kraków", 30, 4000, new Date(), 0);
            Employees employee3 = new Employees(3, "Anna", "Żelazna", "Rdzawa 3", "Warszawa", 35, 4500, new Date(), 1);
            employeesList.addAll(Arrays.asList(employee1, employee2, employee3));

            carsList = new ArrayList<>();
            Cars car1 = new Cars(employee1, 1, "Audi", "A4", new Date());
            Cars car2 = new Cars(employee2, 2, "BMW", "525", new Date());
            carsList.addAll(Arrays.asList(car1, car2));
        }
    }

    @RequestMapping("/seeAll")
    public ModelAndView showCarList(Model model) {
        return new ModelAndView("cars-list", "carsList", carsList);
    }

    @RequestMapping(value = "/getForm", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("car", new Cars());
        model.addAttribute("employeesList", employeesList);
        return "add-car";
    }

    @RequestMapping(value = "/save")
    public ModelAndView save(@ModelAttribute(value = "car") Cars car
            , @ModelAttribute(value = "item.id") String itemId
            , @RequestParam(value = "date") String date) {
        int idAsInt = Integer.parseInt(itemId);
        Employees employeeToSet = employeesList.stream().filter(f -> f.getId() == idAsInt).findFirst().get();
        car.setEmployees(employeeToSet);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date dateParsed = format.parse(date);
            car.setRegistrationDate(dateParsed);
        } catch (ParseException e) {
            e.printStackTrace();
            car.setRegistrationDate(new Date());
        }

        if (car.getId() == 0) {
            addCarToDatabase(car);
            car.setId(carsList.size());
            carsList.add(car);
        } else {
            updateCarInDatabase(car);
            carsList.set(car.getId() - 1, car);
        }
        return new ModelAndView("redirect:/car/seeAll");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@ModelAttribute(value = "car_id") String car_id) {
        Cars car = getCarById(Integer.parseInt(car_id));
        deleteCarFromDatabase(car);
        carsList.remove(car);
        return new ModelAndView("redirect:/car/seeAll");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam(value = "car_id") String car_id) {
        Cars car = getCarById(Integer.parseInt(car_id));
        return new ModelAndView("add-car", "car", car);
    }

    private Cars getCarById(@RequestParam int id) {
        return carsList.stream().filter(f -> f.getId() == id).findFirst().get();
    }

    private void addCarToDatabase(Cars car) {
        try {
            hibernateDao.saveEntity(car);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void updateCarInDatabase(Cars car) {
        try {
            hibernateDao.updateEntity(car);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void deleteCarFromDatabase(Cars car) {
        try {
            hibernateDao.deleteEntity(car);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
