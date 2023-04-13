package dao.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import dao.IKhachHangDao;
import entities.KhachHang;
import util.DatabaseConnection;

public class KhachHangImpl extends UnicastRemoteObject implements IKhachHangDao {
  private Session session;

  public KhachHangImpl() throws RemoteException {
    session = DatabaseConnection.getInstance().getSessionFactory().openSession();
  }

  @Override
  public boolean themKhachHang(KhachHang kh) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      session.persist(kh);
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return false;
  }

  @Override
  public boolean suaKhachHang(KhachHang kh) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      session.merge(kh);
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return false;
  }

  @Override
  public List<KhachHang> getAllKhachHang() throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      String sqlQuery = "select * from khachHang";
      NativeQuery<KhachHang> query = session.createNativeQuery(sqlQuery, KhachHang.class);
      List<KhachHang> list = query.getResultList();
      transaction.commit();
      return list;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return null;
  }
}