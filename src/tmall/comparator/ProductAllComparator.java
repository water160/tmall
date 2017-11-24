package tmall.comparator;

import tmall.bean.Product;

import java.util.Comparator;

public class ProductAllComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        double t1 = p1.getSaleCount() * 0.8 + p1.getReviewCount() * 0.2;
        double t2 = p2.getSaleCount() * 0.8 + p2.getReviewCount() * 0.2;
        if(t2 > t1)
            return 1;
        else
            return -1;
    }

}
