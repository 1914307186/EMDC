package com.estore.dao;


import com.estore.entity.Line;

import java.util.List;

public interface ILineDao  {

	List<Line> findByOrder(long orderId);
	void deleteByOrder(long orderId);
	void updateLine(Line line);
	void addLine(Line line);
}
