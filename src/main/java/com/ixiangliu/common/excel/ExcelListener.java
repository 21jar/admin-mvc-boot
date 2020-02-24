package com.ixiangliu.common.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExcelListener<E> extends AnalysisEventListener<E> {

    /**
     * 批处理阈值
     */
//    private static final int BATCH_COUNT = 2;
//    private List<E> dataList = new ArrayList<E>(BATCH_COUNT);
    private List<E> dataList = new ArrayList<E>();

    @Override
    public void invoke(E object, AnalysisContext context) {
        dataList.add(object);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }

    public List<E> getDataList() {
        return dataList;
    }

    public void setDataList(List<E> dataList) {
        this.dataList = dataList;
    }
}
