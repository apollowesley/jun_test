package io.lemur.map.model.baidu.web.direction.car.route;

import io.lemur.map.model.baidu.web.base.LocationModel;

import java.io.Serializable;

/**
 * 导航道路步骤信息
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionCarRouteStepModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 标示该路段是否在城市内部：0：不在城市内部；1：在城市内部
     */
    private int               area;
    /**
     * 枚举值，返回在0-9之间的一个值，共10个枚举值；分别代表的含义是：0-高速公路；1-城市高速路；2-国道；3-省道；4-县道；5-乡镇村道；6-其他道路；7-九级路；8-航线；9-行人道路
     */
    private int               type;
    /**
     * 枚举值，返回0-16之间的一个值，共17个枚举值；分别代表的含义是：0-无效；1-直行；2-右前方转弯；3-右转；4-右后方转弯；5-掉头；6-左后方转弯；7-左转；8-左前方转弯；9-左侧；10-右侧；11-分歧-左；12-分歧中央；13-分歧右；14-环岛；15-进渡口；16-出渡口
     **/
    private int               turn;
    /**
     * 方案距离 /米
     */
    private double            distance;
    /**
     * 枚举值，返回值在0-11之间的一个值，共12个枚举值，以30度递进，即每个值代表角度范围为30度；其中返回"0"代表345度到15度，以此类推，返回"11"代表315度到345度"；分别代表的含义是：0-[345°-15°]；1-[15°-45°]；2-[45°-75°]；3-[75°-105°]；4-[105°-135°]；5-[135°-165°]；6-[165°-195°]；7-[195°-225°]；8-[225°-255°]；9-[255°-285°]；10-[285°-315°]；11-[315°-345°]
     */
    private int               direction;
    /**
     * 线路耗时 /秒
     */
    private double            duration;
    /**
     * 路段描述
     */
    private String            instruction;
    /**
     * 路段位置坐标描述
     */
    private String            path;
    /**
     * 道路起点
     */
    private LocationModel     stepOriginLocation;
    /**
     * 道路终点
     */
    private LocationModel     stepDestinationLocation;

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocationModel getStepOriginLocation() {
        return stepOriginLocation;
    }

    public void setStepOriginLocation(LocationModel stepOriginLocation) {
        this.stepOriginLocation = stepOriginLocation;
    }

    public LocationModel getStepDestinationLocation() {
        return stepDestinationLocation;
    }

    public void setStepDestinationLocation(LocationModel stepDestinationLocation) {
        this.stepDestinationLocation = stepDestinationLocation;
    }

}
