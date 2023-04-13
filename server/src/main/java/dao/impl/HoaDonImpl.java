package dao.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.IHoaDonDao;
import entities.ChiTietDichVu;
import entities.ChiTietHoaDonBaoHanh;
import entities.HoaDonBaoHanh;
import util.DatabaseConnection;

public class HoaDonImpl extends UnicastRemoteObject implements IHoaDonDao {
  private Session session;

  public HoaDonImpl() throws RemoteException {
    session = DatabaseConnection.getInstance().getSessionFactory().openSession();
  }

  @Override
  public boolean themHoaDonBaoHanh(HoaDonBaoHanh hd) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    List<ChiTietDichVu> dsChiTietDV = hd.getDsChiTietDichVu();
    List<ChiTietHoaDonBaoHanh> dsChiTietBH = hd.getDsChiTietBH();
    try {
      session.persist(hd);
      for (ChiTietDichVu chiTietDichVu : dsChiTietDV) {
        chiTietDichVu.setHoaDonBaoHanh(hd);
        session.persist(chiTietDichVu);
      }
      for (ChiTietHoaDonBaoHanh chiTietHoaDonBaoHanh : dsChiTietBH) {
        chiTietHoaDonBaoHanh.setHoaDonBaoHanh(hd);
        session.persist(chiTietHoaDonBaoHanh);
      }
      transaction.commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return false;
  }

}
