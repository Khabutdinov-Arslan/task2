import com.java_hw.task2.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GarageManagerTest {
    GarageManager testGarage;
    Owner ivan;
    Owner oleg;
    Car focus;
    Car fiesta;
    Car ds;
    Car jetta;
    Car cooper;
    Car explorer;

    @Before
    public void init(){
        testGarage = new GarageManager();
        ivan = new Owner(1, "Ivan", "Ivanov", 42);
        oleg = new Owner(2, "Oleg", "Olegov", 51);
        focus = new Car(1, "Ford", "Focus", 1, 3, 1);
        fiesta = new Car(2, "Ford", "Fiesta", 2, 3, 1);
        ds = new Car(3, "Citroen", "DS", 4, 3, 2);
        jetta = new Car(4, "Volkswagen", "Jetta", 3, 5, 2);
        cooper = new Car(5, "Mini", "Cooper", 5, 4, 1);
        explorer = new Car(6, "Ford", "Explorer", 3, 6, 2);
    }

    @Test
    public void noCarExceptionTest(){
        try {
            testGarage.removeCar(3);
            fail("Removed non-existent car");
        } catch (NoCarException e){
            //
        }
    }

    @Test
    public void noCarsOfBrandExceptionTest(){
        try {
            testGarage.allCarsOfBrand("Ford");
            fail("Got cars of non-existent brand");
        } catch (NoBrandException e){
            //
        }
    }

    @Test
    public void noBrandExceptionTest(){
        try {
            testGarage.meanOwnersAgeOfCarBrand("Ford");
            fail("Got mean age of non-existent brand");
        } catch (NoBrandException e){
            //
        }
    }

    @Test
    public void noOwnerExceptionTest(){
        try {
            testGarage.allCarsOfOwner(oleg);
            fail("Got cars of non-existent owner");
        } catch (NoOwnerException e){
            //
        }
    }

    @Test
    public void incorrectOwnerExceptionTest(){
        try {
            testGarage.addNewCar(focus, oleg);
            fail("Added car with wrong owner");
        } catch (IncorrectOwnerException e){
            //
        } catch (GarageException e){
            fail("Invalid exception");
        }
    }

    @Test
    public  void duplicateCarExceptionTest(){
        try {
            testGarage.addNewCar(focus, ivan);
            testGarage.addNewCar(focus, ivan);
        } catch (DuplicateCarException e){
            //
        } catch (GarageException e){
            fail("Invalid exception");
        }
    }

    @Test
    public void topThreeCarsByMaxVelocityTest(){
        try {
            testGarage.addNewCar(focus, ivan);
            testGarage.addNewCar(fiesta, ivan);
            testGarage.addNewCar(ds, oleg);
            testGarage.addNewCar(jetta, oleg);
            testGarage.addNewCar(cooper, ivan);
            ArrayList<Car> realCars = new ArrayList<>(testGarage.topThreeCarsByMaxVelocity());
            ArrayList<Car> expectedCars = new ArrayList<>();
            expectedCars.add(cooper);
            expectedCars.add(ds);
            expectedCars.add(jetta);
            assertEquals(realCars, expectedCars);
        } catch (GarageException e) {
            fail("Exception thrown during normal execution");
        }
    }

    @Test
    public void allCarsOfBrandTest(){
        try{
            testGarage.addNewCar(focus, ivan);
            testGarage.addNewCar(fiesta, ivan);
            HashSet<Car> realCars = new HashSet<>(testGarage.allCarsOfBrand("Ford"));
            HashSet<Car> expectedCars = new HashSet<>();
            expectedCars.add(focus);
            expectedCars.add(fiesta);
            assertEquals(realCars, expectedCars);
        } catch (GarageException e){
            fail("Exception thrown during normal execution");
        }
    }

    @Test
    public void allCarsOfOwnerTest(){
        try{
            testGarage.addNewCar(focus, ivan);
            testGarage.addNewCar(fiesta, ivan);
            testGarage.addNewCar(cooper, ivan);
            HashSet<Car> realCars = new HashSet<>(testGarage.allCarsOfOwner(ivan));
            HashSet<Car> expectedCars = new HashSet<>();
            expectedCars.add(focus);
            expectedCars.add(fiesta);
            expectedCars.add(cooper);
            assertEquals(realCars, expectedCars);
        } catch (GarageException e){
            fail("Exception thrown during normal execution");
        }
    }

    @Test
    public void meanCarNumberForEachOwnerTest(){
        try{
            testGarage.addNewCar(focus, ivan);
            testGarage.addNewCar(fiesta, ivan);
            testGarage.addNewCar(cooper, ivan);
            testGarage.addNewCar(ds, oleg);
            assertEquals(testGarage.meanCarNumberForEachOwner(), 2);
        } catch (GarageException e){
            fail("Exception thrown during normal execution");
        }
    }

    @Test
    public void meanOwnersAgeOfCarBrandTest(){
        try{
            testGarage.addNewCar(focus, ivan);
            testGarage.addNewCar(fiesta, ivan);
            testGarage.addNewCar(explorer, oleg);
            assertEquals(testGarage.meanOwnersAgeOfCarBrand("Ford"), 45);
        } catch (GarageException e){
            fail("Exception thrown during normal execution");
        }
    }

    @Test
    public void allUniqueOwnersTest(){
        try{
            testGarage.addNewCar(focus, ivan);
            testGarage.addNewCar(explorer, oleg);
            testGarage.addNewCar(ds, oleg);
            assertEquals(testGarage.allCarsUniqueOwners().size(), 2);
        } catch (GarageException e){
            fail("Exception thrown during normal execution");
        }
    }

    @Test
    public void carsWithMorePowerThanTest(){
        try{
            testGarage.addNewCar(focus, ivan);
            testGarage.addNewCar(explorer, oleg);
            testGarage.addNewCar(ds, oleg);
            HashSet<Car> realCars = new HashSet<>(testGarage.carsWithPowerMoreThan(5));
            HashSet<Car> expectedCars = new HashSet<>();
            expectedCars.add(explorer);
            assertEquals(realCars, expectedCars);
        } catch (GarageException e){
            fail("Exception thrown during normal execution");
        }
    }

    @Test
    public void removeCarTest(){
        testGarage.addNewCar(ds, oleg);
        testGarage.removeCar(3);
        try {
            testGarage.meanOwnersAgeOfCarBrand("Citroen");
            fail("Didn't remove car");
        } catch (GarageException e){
            //
        }
    }



}
