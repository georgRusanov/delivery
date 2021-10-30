import entities.Delivery;
import enums.Dimensions;
import enums.Workload;

public class DeliveryCost {
    private static final double MINIMAL_PRICE = 400.0;

    public static double calculate(Delivery details) {
        double price = 0;
        checkRestrictions(details);
        price += calculateDistancePrice(details.getDistance());
        price += calculateDimensionsPrice(details.getSize());
        price += calculateFragilityPrice(details.isFragile());
        price *= calculateWorkLoadRate(details.getWorkload());
        return Math.max(price, MINIMAL_PRICE);
    }

    private static void checkRestrictions(Delivery details) {
        if (details.isFragile() && details.getDistance() > 30) {
            throw new IllegalArgumentException("Can't deliver fragile package more than 30 km");
        }
    }

    private static int calculateDistancePrice(int distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Distance should be more than 0");
        }
        if (distance > 30) {
            return 300;
        }
        if (distance > 10) {
            return 200;
        }
        if (distance > 2) {
            return 100;
        }
        return 50;
    }

    private static int calculateFragilityPrice(boolean isFragile) {
        return isFragile ? 300 : 0;
    }

    private static int calculateDimensionsPrice(Dimensions size) {
        if (size == Dimensions.SMALL) {
            return 100;
        }
        if (size == Dimensions.BIG) {
            return 200;
        }
        throw new IllegalArgumentException("Distance should be more than 0");
    }

    private static double calculateWorkLoadRate(Workload load) {
        if (load == Workload.VERYHIGH) {
            return 1.6;
        }
        if (load == Workload.HIGH) {
            return 1.4;
        }
        if (load == Workload.INCREASED) {
            return 1.2;
        }
        return 1;
    }
}
