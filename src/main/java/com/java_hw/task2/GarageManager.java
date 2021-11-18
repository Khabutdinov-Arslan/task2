package com.java_hw.task2;

import java.util.*;

public class GarageManager implements Garage {

    Comparator<Car> compareByMaxVelocity = (c1, c2) -> {
        if (c1.equals(c2)){
            return 0;
        }else{
            return c1.getMaxVelocity() - c2.getMaxVelocity();
        }
    };
    Comparator<Car> compareByPower = (c1, c2) -> {
        if (c1.equals(c2)){
            return 0;
        }else{
            if(c1.getPower() == c2.getPower()){
                return c1.getCarId() - c2.getCarId();
            }else{
                return c1.getPower() - c2.getPower();
            }
        }
    };

    private final HashMap<Integer, Car> carById = new HashMap<>();
    private final HashMap<Integer, Owner> ownerById = new HashMap<>();
    private final HashMap<Integer, HashSet<Car>> carsByOwner = new HashMap<>();
    private final HashMap<String, HashSet<Car>> carsByBrand = new HashMap<>();
    private final TreeSet<Car> carsByVelocity = new TreeSet<>(compareByMaxVelocity);
    private final TreeSet<Car> carsByPower = new TreeSet<>(compareByPower);

    public Collection<Owner> allCarsUniqueOwners(){
        return new HashSet<>(ownerById.values());
    }

    /**
     * Complexity should be less than O(n)
     */
    public Collection<Car> topThreeCarsByMaxVelocity(){
        ArrayList<Car> topCars = new ArrayList<>();
        int carCount = 0;
        Iterator<Car> it = carsByVelocity.descendingIterator();
        while (it.hasNext()){
            topCars.add(it.next());
            carCount++;
            if (carCount == 3){
                break;
            }
        }
        return topCars;
    }

    /**
     * Complexity should be O(1)
     */
    public Collection<Car> allCarsOfBrand(String brand) throws NoBrandException {
        if (carsByBrand.containsKey(brand)) {
            return carsByBrand.get(brand);
        }
        throw new NoBrandException("No such brand " + brand);
    }

    /**
     * Complexity should be less than O(n)
     */
    public Collection<Car> carsWithPowerMoreThan(int power){
        Car fakeCar = new Car(0, null, null, 0, power, 0);
            return carsByPower.tailSet(fakeCar);
    }

    /**
     * Complexity should be O(1)
     */
    public Collection<Car> allCarsOfOwner(Owner owner) throws NoOwnerException {
        if (carsByOwner.containsKey(owner.getId())) {
            return carsByOwner.get(owner.getId());
        }
        throw new NoOwnerException("No such owner " + owner);
    }

    /**
     * @return mean value of owner age that has cars with given brand
     */
    public int meanOwnersAgeOfCarBrand(String brand) throws NoBrandException {
        if (carsByBrand.containsKey(brand)) {
            var meanAge = carsByBrand.get(brand).stream().
                    mapToInt(car -> ownerById.get(car.getOwnerId()).getAge())
                    .average();
            return (int)meanAge.orElse(0);
        }
        throw new NoBrandException("No such brand " + brand);
    }

    /**
     * @return mean value of cars for all owners
     */
    public int meanCarNumberForEachOwner(){
        var meanNumber = carsByOwner.values().stream().mapToInt(HashSet::size).average();
        return  (int)meanNumber.orElse(0);
    }

    /**
     * Complexity should be less than O(n)
     * @return removed car
     */
    public Car removeCar(int carId) throws NoCarException {
        if (carById.containsKey(carId)) {
            Car car = carById.get(carId);
            carById.remove(carId);

            if (carsByOwner.get(car.getOwnerId()) != null) {
                carsByOwner.get(car.getOwnerId()).remove(car);
            }
            if (carsByOwner.get(car.getOwnerId()).isEmpty()) {
                ownerById.remove(car.getOwnerId());
                carsByOwner.remove(car.getOwnerId());
            }

            if (carsByBrand.get(car.getBrand()) != null) {
                carsByBrand.get(car.getBrand()).remove(car);
            }
            if (carsByBrand.get(car.getBrand()).isEmpty()) {
                carsByBrand.remove(car.getBrand());
            }

            carsByVelocity.remove(car);
            carsByPower.add(car);
            return car;
        }
        throw new NoCarException("No car with id " + carId);
    }

    /**
     * Complexity should be less than O(n)
     */
    public void addNewCar(Car car, Owner owner) throws DuplicateCarException, NoOwnerException, IncorrectOwnerException {
        if (carById.containsKey(car.getCarId())){
            throw new DuplicateCarException("Already has car with id " + car.getCarId());
        }

        if (car.getOwnerId() != owner.getId()){
            throw new IncorrectOwnerException("Mismatch owner id for car " + car.getCarId());
        }

        carById.put(car.getCarId(), car);

        if (!ownerById.containsKey(owner.getId())) {
            ownerById.put(owner.getId(), owner);
            carsByOwner.put(owner.getId(), new HashSet<>());
        }
        carsByOwner.get(owner.getId()).add(car);

        carsByBrand.computeIfAbsent(car.getBrand(), key -> new HashSet<>());
        carsByBrand.get(car.getBrand()).add(car);

        carsByVelocity.add(car);
        carsByPower.add(car);
    }



}
