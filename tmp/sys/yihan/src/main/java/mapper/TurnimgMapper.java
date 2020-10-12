package mapper;

import pojo.Turnimg;

import java.util.List;

public interface TurnimgMapper {

    List<Turnimg> getAllImg();

    Turnimg getTurnimgById(Integer id);

    void editTurnimg(Turnimg turnimg);

    void addTurnimg(Turnimg turnimg);

    void DelTurnimgById(Integer id);
}
