package bus.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import bus.INhanVienBus;
import dao.INhanVienDao;
import entities.*;

public class NhanVienImpl extends UnicastRemoteObject implements INhanVienBus {
  private static final long serialVersionUID = 2L;
  private INhanVienDao dao;

  public NhanVienImpl() throws RemoteException {
    super();
    dao = new dao.impl.NhanVienImpl();
  }

  @Override
  public boolean suaNhanVien(NhanVien nv) throws RemoteException {
    if (nv.getViTriCongViec().equals("Quản lý")) {
      nv.setQuanLy(nv);
    }
    return dao.suaNhanVien(nv) ? true : false;
  }

  @Override
  public int validator(String sdt, String viTri, String ten, String diaChi, String email) throws RemoteException {
    if (ten.isEmpty()) {
      return 1;
    } else if (sdt.isEmpty()) {
      return 2;
    } else if (viTri.isEmpty()) {
      return 3;
    } else if (diaChi.isEmpty()) {
      return 4;
    } else if (email.isEmpty()) {
      return 5;
    } else if (ten.matches(".*[!@#$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/].*")) {
      return 6;
    } else if (sdt.matches(".*[!@#$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/].*")
        || !sdt.matches("[0-9]{10}$")) {
      return 7;
    } else if (diaChi.matches(".*[!@#$%^&*()_+|~=`{}\\[\\]:\";'<>?.].*")) {
      return 8;
    } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
      return 9;
    }
    return 0;
  }

  @Override
  public List<NhanVien> getAllNhanVien() throws RemoteException {
    return dao.getAllNhanVien();
  }
}
