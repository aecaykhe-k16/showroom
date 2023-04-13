package dao.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import dao.INhanVienDao;
import entities.NhanVien;
import util.DatabaseConnection;

public class NhanVienImpl extends UnicastRemoteObject implements INhanVienDao {
  private Session session;

  public NhanVienImpl() throws RemoteException {
    session = DatabaseConnection.getInstance().getSessionFactory().openSession();
  }

  @Override
  public boolean suaNhanVien(NhanVien nv) throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      String update = "update NhanVien set tenNV = :ten, sdt = :sdt, viTriCongViec = :viTri, diaChi = :diaChi, email = :email, gioiTinh = :gioiTinh, ngaySinh = :ngaySinh, ngayTuyenDung= :ngayTuyenDung, trangThai= :trangThai where maNV = :maNhanVien";
      NativeQuery<NhanVien> query = session.createNativeQuery(update, NhanVien.class);
      query.setParameter("ten", nv.getTenNV());
      query.setParameter("sdt", nv.getSdt());
      query.setParameter("viTri", nv.getViTriCongViec());
      query.setParameter("diaChi", nv.getDiaChi());
      query.setParameter("email", nv.getEmail());
      query.setParameter("gioiTinh", nv.getGioiTinh());
      query.setParameter("ngaySinh", nv.getNgaySinh());
      query.setParameter("ngayTuyenDung", nv.getNgayTuyenDung());
      query.setParameter("trangThai", nv.getTrangThai());
      query.setParameter("maNhanVien", nv.getMaNV());
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
  public List<NhanVien> getAllNhanVien() throws RemoteException {
    Transaction transaction = session.beginTransaction();
    try {
      List<NhanVien> list = session.createNativeQuery("select * from nhanVien", NhanVien.class).getResultList();

      transaction.commit();
      return list;
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    return null;
  }

}