import static enums.Workload.HIGH;
import static enums.Workload.INCREASED;
import static enums.Workload.VERYHIGH;
import static org.testng.Assert.assertEquals;

import entities.Delivery;
import enums.Dimensions;
import enums.Workload;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DeliveryCostTest {
    private static final double MINIMAL_PRICE = 400.0;

    @Test
    public void shouldReturnMinimalPrice() {
        Delivery details = new Delivery(1, false, Dimensions.SMALL, null);
        double price = DeliveryCost.calculate(details);
        assertEquals(price, MINIMAL_PRICE);
    }

    @DataProvider(name = "distanceException")
    public Object[][] distanceException(){
        return new Object[][] {{-1}, {0}};
    }

    @Test(expectedExceptions = {IllegalArgumentException.class}, dataProvider = "distanceException")
    public void shouldThrowAnErrorWithDistanceIsZeroAndLess(int distance) {
        Delivery details = new Delivery(distance, false, Dimensions.SMALL, null);
        DeliveryCost.calculate(details);
    }

    @DataProvider(name = "distance")
    public Object[][] distance(){
        return new Object[][] {{1, Dimensions.BIG, 550}, {2, Dimensions.SMALL, 450}, {10, Dimensions.BIG, 600}, {30, Dimensions.BIG, 700}};
    }

    @Test(dataProvider = "distance")
    public void checkCalculationForDifferentDistancesAndPackageSizes(int distance, Dimensions size, double expected) {
        Delivery details = new Delivery(distance, true, size, null);
        double price = DeliveryCost.calculate(details);
        assertEquals(price, expected);
    }

    @Test()
    public void checkCalculationForLongDistance() {
        Delivery details = new Delivery(31, false, Dimensions.BIG, null);
        double price = DeliveryCost.calculate(details);
        assertEquals(price, 500);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void shouldThrowAnErrorForFragilityWithLongDistance() {
        Delivery details = new Delivery(31, true, Dimensions.SMALL, null);
        DeliveryCost.calculate(details);
    }

    @DataProvider(name = "workload")
    public Object[][] workload(){
        return new Object[][] {{INCREASED, 720}, {HIGH, 840}, {VERYHIGH, 960}};
    }

    @Test(dataProvider = "workload")
    public void checkCalculationForDifferentWorkload(Workload load, double expected) {
        Delivery details = new Delivery(30, true, Dimensions.SMALL, load);
        double price = DeliveryCost.calculate(details);
        assertEquals(price, expected);
    }
}
