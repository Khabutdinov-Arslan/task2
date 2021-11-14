package com.java_hw.task2;

import java.util.Collection;
import java.util.HashMap;

public interface Garage {

    Collection<Owner> allCarsUniqueOwners();

    /**
     * Complexity should be less than O(n)
     */
    Collection<Car> topThreeCarsByMaxVelocity();

    /**
     * Complexity should be O(1)
     */
    Collection<Car> allCarsOfBrand(String brand) throws NoBrandException;

    /**
     * Complexity should be less than O(n)
     */
    Collection<Car> carsWithPowerMoreThan(int power);

    /**
     * Complexity should be O(1)
     */
    Collection<Car> allCarsOfOwner(Owner owner) throws NoOwnerException;

    /**
     * @return mean value of owner age that has cars with given brand
     */
    int meanOwnersAgeOfCarBrand(String brand) throws NoBrandException;

    /**
     * @return mean value of cars for all owners
     */
    int meanCarNumberForEachOwner();

    /**
     * Complexity should be less than O(n)
     * @return removed car
     */
    Car removeCar(int carId) throws NoCarException;

    /**
     * Complexity should be less than O(n)
     */
    void addNewCar(Car car, Owner owner) throws DuplicateCarException, NoOwnerException, IncorrectOwnerException;
}
