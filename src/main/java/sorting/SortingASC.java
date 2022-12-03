package sorting;

import coffee.Blocks_amount;
import java.util.Comparator;

public class SortingASC implements Comparator<Blocks_amount> {
    /**
     * sorts by ascending
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return sorted
     */
    @Override
    public int compare(Blocks_amount o1, Blocks_amount o2) {
        return o1.getBlock().getCoffee().getKgPrice() - o2.getBlock().getCoffee().getKgPrice();
    }
}
