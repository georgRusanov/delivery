package entities;

import enums.Dimensions;
import enums.Workload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class Delivery {
    @NonNull
    private int distance;
    @NonNull
    private boolean isFragile;
    @NonNull
    private Dimensions size;
    private Workload workload;
}
