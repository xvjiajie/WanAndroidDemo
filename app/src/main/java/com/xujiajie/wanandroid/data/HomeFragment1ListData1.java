package com.xujiajie.wanandroid.data;

import com.xujiajie.wanandroid.HomeFragment1Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期 2020/9/25
 * 描述：
 */
public class HomeFragment1ListData1 implements Serializable {
    //{"offset":0,"over":false,"pageCount":463,"size":20,"total":9258}
    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<HomeFragment1Bean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<HomeFragment1Bean> getDatas() {
        if (datas == null) {
            return new ArrayList<>();
        }
        return datas;
    }

    @Override
    public String toString() {
        return "HomeFragment1ListData1{" +
                "curPage=" + curPage +
                ", offset=" + offset +
                ", over=" + over +
                ", pageCount=" + pageCount +
                ", size=" + size +
                ", total=" + total +
                ", datas=" + datas +
                '}';
    }

    public void setDatas(List<HomeFragment1Bean> datas) {
        this.datas = datas;
    }

    public boolean isHasNext(){
        return pageCount>=curPage;
    }
}
