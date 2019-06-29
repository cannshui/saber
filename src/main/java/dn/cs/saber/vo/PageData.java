package dn.cs.saber.vo;

import java.util.List;

/**
 * Pagination logic.
 * 
 * @author hewen.deng
 * @param <T>
 */
public class PageData<T> {

    /** Data total number. */
    private long total = 0;

    /** A page's size. */
    private int size = 10;

    /** Current page number. */
    private int page = 1;

    /** Result Datas return to view. */
    private List<T> datas = null;

    /** The start index of table row. */
    private int index;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getIndex() {
        index = (page - 1) * size;
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "PageData: { total: " + total + ", page: " + page + ", size: " + size + ", index: " + index + ", datas: " + datas + " }";
    }

}
