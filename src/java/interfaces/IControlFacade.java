package interfaces;

import entity.Car;
import exception.CarNotFoundException;
import java.util.List;


public interface IControlFacade {
    
    public void addCar(Car car);
    public List<Car> getCars();
    public Car getCar(int id) throws CarNotFoundException;
    public void editCar(Car car);
    public void deleteCar(int id);
    
}
