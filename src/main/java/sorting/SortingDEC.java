package sorting;

import coffee.Blocks_amount;
import java.util.Comparator;

public class SortingDEC implements Comparator<Blocks_amount> {
    /**
     * sorts by descending
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return sorted
     */
    @Override
    public int compare(Blocks_amount o1, Blocks_amount o2) {
        return o2.getBlock().getCoffee().getKgPrice() - o1.getBlock().getCoffee().getKgPrice();
    }
}
