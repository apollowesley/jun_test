package service;

import pojo.Turnimg;

import java.util.List;

public interface TurnimgService {

    List<Turnimg> getAllImg();

    Turnimg getTurnimgById(Integer id);

    void editTurnimg(Turnimg turnimg);

    void DelTurnimgById(Integer id);
}
