package dao.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import dao.IDichVuDao;
import entities.DichVu;
import entities.PhuTung;
import util.DatabaseConnection;

public class DichVuImpl extends UnicastRemoteObject implements IDichVuDao {
  private Session session;

  public DichVuImpl() throws RemoteException {
    session = DatabaseConnection.getInstance().getSessionFactory().openSession();
  }

  @Override
  public boolean themDichVu(DichVu dv) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      session.persist(dv);
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return false;
  }

  @Override
  public boolean xoaDichVu(String maDV) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      String sqlQuery = "delete from dichVu where maDV = :x";
      Query<DichVu> query = session.createNativeQuery(sqlQuery, DichVu.class);
      query.setParameter("x", maDV);
      query.executeUpdate();
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return false;
  }

  @Override
  public boolean suaDichVu(DichVu dv) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      session.merge(dv);
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return false;
  }

  @Override
  public boolean themPhuTung(PhuTung pt) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      session.persist(pt);
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return false;
  }

  @Override
  public boolean xoaPhuTung(String mapt) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      String sqlQuery = "delete from phuTung where maPhuTung = :x";
      Query<PhuTung> query = session.createNativeQuery(sqlQuery, PhuTung.class);
      query.setParameter("x", mapt);
      query.executeUpdate();
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return false;
  }

  @Override
  public boolean suaPhuTung(PhuTung pt) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      session.merge(pt);
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return false;
  }

  @Override
  public List<DichVu> getAllDichVu() throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      String sqlQuery = "select * from dichVu";
      NativeQuery<DichVu> query = session.createNativeQuery(sqlQuery, DichVu.class);
      List<DichVu> list = query.getResultList();
      transaction.commit();
      return list;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return null;
  }

  @Override
  public List<PhuTung> getAllPhuTung() throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      String sqlQuery = "select * from phuTung";
      NativeQuery<PhuTung> query = session.createNativeQuery(sqlQuery, PhuTung.class);
      List<PhuTung> list = query.getResultList();
      transaction.commit();
      return list;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return null;
  }

  @Override
  public void tangSoLuongPhuTung(String maPT) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      String sqlQuery = "update phuTung set soLuong = soLuong + 1 where maPhuTung = :y";
      NativeQuery<PhuTung> query = session.createNativeQuery(sqlQuery, PhuTung.class);
      query.setParameter("y", maPT);
      query.executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }

  }

  @Override
  public void giamSoLuongPhuTung(String maPT) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      String sqlQuery = "update phuTung set soLuong = soLuong - 1 where maPhuTung = :y";
      NativeQuery<PhuTung> query = session.createNativeQuery(sqlQuery, PhuTung.class);
      query.setParameter("y", maPT);
      query.executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
  }

}
