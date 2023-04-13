package bus.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import bus.IKhachHangBus;
import dao.IKhachHangDao;
import entities.*;

public class KhachHangImpl extends UnicastRemoteObject implements IKhachHangBus {
  private static final long serialVersionUID = 2L;
  private IKhachHangDao dao;

  public KhachHangImpl() throws RemoteException {
    super();
    dao = new dao.impl.KhachHangImpl();
  }

  @Override
  public boolean themKhachHang(KhachHang kh) throws RemoteException {
    return dao.themKhachHang(kh) ? true : false;
  }

  @Override
  public boolean suaKhachHang(KhachHang kh) throws RemoteException {
    return dao.suaKhachHang(kh) ? true : false;
  }

  @Override
  public List<KhachHang> getAllKhachHang() throws RemoteException {
    return dao.getAllKhachHang();
  }

  @Override
  public KhachHang getKhachHang(String sdt) throws RemoteException {
    for (KhachHang kh : dao.getAllKhachHang()) {
      if (kh.getSdt().equals(sdt)) {
        return kh;
      }
    }
    return null;
  }

  @Override
  public int validator(String sdt, String diaChi, String ten, String email) throws RemoteException {
    if (ten.isEmpty()) {
      return 1;
    } else if (sdt.isEmpty()) {
      return 2;
    } else if (diaChi.isEmpty()) {
      return 3;
    } else if (email.isEmpty()) {
      return 4;
    } else if (ten.matches(".*[!@#$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/].*")) {
      return 5;
    } else if (sdt.matches(".*[!@#$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/].*")
        || !sdt.matches("[0-9]{10}$")) {
      return 6;
    } else if (diaChi.matches(".*[!@#$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/].*")) {
      return 7;
    } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
      return 8;
    }
    return 0;
  }
}
