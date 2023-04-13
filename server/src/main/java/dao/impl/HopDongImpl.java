package dao.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.IHopDongDao;
import entities.HopDong;
import entities.HopDongTraGop;
import entities.HopDongTraThang;
import util.DatabaseConnection;

public class HopDongImpl extends UnicastRemoteObject implements IHopDongDao {
  private Session session;

  public HopDongImpl() throws RemoteException {
    super();
    session = DatabaseConnection.getInstance().getSessionFactory().openSession();
  }

  @Override
  public boolean themHopDongTraThang(HopDongTraThang hopDong) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      session.persist(hopDong);
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return false;
  }

  @Override
  public boolean suaHopDongTraThang(HopDongTraThang hopDong) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      session.merge(hopDong);
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
      return false;
    }

  }

  @Override
  public boolean themHopDongTraGop(HopDongTraGop hopDong) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      session.persist(hopDong);
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return false;
  }

  @Override
  public boolean suaHopDongTraGop(HopDongTraGop hopDong) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      session.merge(hopDong);
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
      return false;
    }
  }

  @Override
  public List<HopDong> getAllHopDong() throws RemoteException {
    Transaction transaction = session.beginTransaction();
    List<HopDong> dsHopDong = new ArrayList<>();
    try {
      String query = "select * from hopDong";
      List<Object[]> list = (List<Object[]>) session
          .createNativeQuery(query)
          .getResultList();
      for (Object[] objects : list) {
        String maHD = (String) objects[0];
        HopDong hd = session.find(HopDong.class, maHD);
        dsHopDong.add(hd);
      }
      transaction.commit();
      return dsHopDong;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return null;
  }
}