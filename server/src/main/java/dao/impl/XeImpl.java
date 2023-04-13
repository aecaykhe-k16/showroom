package dao.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import dao.IXeDao;
import entities.Xe;
import util.DatabaseConnection;

public class XeImpl extends UnicastRemoteObject implements IXeDao {
  private Session session;

  /**
   *
   */
  public XeImpl() throws RemoteException {
    session = DatabaseConnection.getInstance().getSessionFactory().openSession();
  }

  @Override
  public boolean themXe(Xe xe) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      session.persist(xe);
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return false;
  }

  @Override
  public boolean xoaXe(String tenXe) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      String sqlQuery = "delete xe where tenXe=:x";
      Query<Xe> query = session.createNativeQuery(sqlQuery, Xe.class);
      query.setParameter("x", tenXe);
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
  public boolean suaXe(Xe xe) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      session.merge(xe);
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return false;
  }

  @Override
  public List<Xe> getAllXe() throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      String sqlQuery = "select * from xe";
      NativeQuery<Xe> query = session.createNativeQuery(sqlQuery, Xe.class);
      List<Xe> list = query.getResultList();
      transaction.commit();
      return list;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return null;
  }

  @Override
  public Xe timXe(String tenXe) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      String sqlQuery = "select * from xe where tenXe=:x";
      NativeQuery<Xe> query = session.createNativeQuery(sqlQuery, Xe.class);
      query.setParameter("x", tenXe);
      Xe xe = query.getSingleResult();
      transaction.commit();
      return xe;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return null;
  }

}