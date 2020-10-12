package service.impl;

import mapper.TurnimgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Turnimg;
import service.TurnimgService;

import java.util.List;

@Service
public class TurnimgServiceImpl implements TurnimgService {
    @Autowired
    private TurnimgMapper mapper;


    @Override
    public List<Turnimg> getAllImg() {

        return mapper.getAllImg();
    }

    @Override
    public Turnimg getTurnimgById(Integer id) {
        return mapper.getTurnimgById(id);
    }

    @Override
    public void editTurnimg(Turnimg turnimg) {
        if (turnimg.getId() == null) {
            mapper.addTurnimg(turnimg);
        } else {
            mapper.editTurnimg(turnimg);
        }
    }

    @Override
    public void DelTurnimgById(Integer id) {
        mapper.DelTurnimgById(id);
    }
}