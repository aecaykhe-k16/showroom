package dao.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dao.ITaiKhoanDao;
import entities.TaiKhoan;
import util.DatabaseConnection;

public class TaiKhoanImpl extends UnicastRemoteObject implements ITaiKhoanDao {
  private Session session;

  public TaiKhoanImpl() throws RemoteException {
    session = DatabaseConnection.getInstance().getSessionFactory().openSession();
  }

  @Override
  public boolean themTaiKhoan(TaiKhoan tk) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      session.persist(tk);
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return false;
  }

  @Override
  public boolean suaTaiKhoan(TaiKhoan tk) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      String sqlQuery = "update taiKhoan set trangThai = 0 where maNV = :x";
      Query<TaiKhoan> query = session.createNativeQuery(sqlQuery, TaiKhoan.class);
      query.setParameter("x", tk.getNhanVien().getMaNV());
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
  public TaiKhoan login(String tenDangNhap, String matKhau) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      String sqlQuery = "select * from taiKhoan where tenDangNhap = :userName and matKhau = :password";
      Query<TaiKhoan> query = session.createNativeQuery(sqlQuery, TaiKhoan.class);
      query.setParameter("userName", tenDangNhap);
      query.setParameter("password", matKhau);
      TaiKhoan tk = query.getSingleResult();
      transaction.commit();
      return tk;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return null;
  }

  @Override
  public boolean logout(TaiKhoan tk) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      String sqlQuery = "update taiKhoan set trangThai = 0 where maNV = :x";
      Query<TaiKhoan> query = session.createNativeQuery(sqlQuery, TaiKhoan.class);
      query.setParameter("x", tk.getNhanVien().getMaNV());
      query.executeUpdate();
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return false;
  }

}