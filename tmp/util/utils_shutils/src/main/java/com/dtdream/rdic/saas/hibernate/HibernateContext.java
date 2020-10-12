package com.dtdream.rdic.saas.hibernate;

/**
 * Created by Ozz8 on 2015/06/26.
 */
public class HibernateContext<T> {
    public enum QRY_MODE {
        QRY_COUNT,
        QRY_DATA,
        ;
    }
    QRY_MODE qryMode;

    public final static HibernateContext<Long> Counter = new HibernateContext<>(QRY_MODE.QRY_COUNT);

    /**
     * To judge whether current mode is QRY_COUNT.
     * @return
     */
    public boolean oc () {
        return this.qryMode.equals(QRY_MODE.QRY_COUNT);
    }

    /**
     * To judge whether current mode is QRY_DATA.
     */
    public boolean od () {
        return this.qryMode.equals(QRY_MODE.QRY_DATA);
    }

    public HibernateContext () {
        this.qryMode = QRY_MODE.QRY_DATA;
    }

    public HibernateContext (QRY_MODE mode) {
        this.qryMode = mode;
    }

    public QRY_MODE getQryMode() {
        return qryMode;
    }

    public void setQryMode(QRY_MODE qryMode) {
        this.qryMode = qryMode;
    }
}
