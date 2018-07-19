package com.estore.service;

import com.estore.dao.ILineDao;
import com.estore.entity.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ILineServiceImpl implements ILineService {

    @Autowired
    private ILineDao iLineDao;

    @Override
    public List<Line> findLines(long orderId) {
        List<Line> lines = iLineDao.findByOrder(orderId);
        return lines;
    }

    @Override
    public void addLine(Line line) {
//        SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
//        long id = sqlSession.selectOne("selectLineKey");
//        line.setId(id);
//        ILineDao mapper = sqlSession.getMapper(ILineDao.class);
        iLineDao.addLine(line);
    }

    @Override
    public void deleteLines(long orderId) {
        iLineDao.deleteByOrder(orderId);
    }

    @Override
    public void updateLine(Line line) {
        iLineDao.updateLine(line);
    }
}
