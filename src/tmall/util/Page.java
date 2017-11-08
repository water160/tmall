package tmall.util;

public class Page {
    private int start;
    private int count;
    private int total;
    private String param;

    public Page(int start, int count) {
        super();
        this.start = start;
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public boolean hasPrevious() {
        return start != 0;
    }

    public boolean hasNext() {
        return start != getLast();
    }

    public int getTotalPage() {
        int totalPage;
        if (total % count == 0) {
            totalPage = total / count;
        } else {
            totalPage = total / count + 1;
        }
        if (totalPage == 0) {
            totalPage = 1;
        }
        return totalPage;
    }

    public int getLast() {
        int last;
        if (total % count == 0) {// total 50, count 5, the start of lastPage is 45
            last = total - count;
        } else { //total 51, count 5, the start of lastPage is 50
            last = total - total % count;
        }
        last = last < 0 ? 0 : last;
        return last;
    }
}
