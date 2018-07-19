package com.estore.service;

import com.estore.entity.Line;

import java.util.List;

public interface ILineService {
    List<Line> findLines(long orderId);
    void addLine(Line line);
    void deleteLines(long orderId);
    void updateLine(Line line);
}
