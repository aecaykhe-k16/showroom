package bus.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import bus.IDichVuBus;
import dao.IDichVuDao;
import entities.DichVu;
import entities.PhuTung;

public class DichVuImpl extends UnicastRemoteObject implements IDichVuBus {
  private static final long serialVersionUID = 2L;
  private IDichVuDao dao;

  public DichVuImpl() throws RemoteException {
    super();
    dao = new dao.impl.DichVuImpl();
  }

  @Override
  public boolean themDichVu(DichVu dv) throws RemoteException {
    return dao.themDichVu(dv) ? true : false;
  }

  @Override
  public boolean xoaDichVu(String maDV) throws RemoteException {
    return dao.xoaDichVu(maDV) ? true : false;
  }

  @Override
  public boolean suaDichVu(DichVu dv) throws RemoteException {
    return dao.suaDichVu(dv) ? true : false;
  }

  @Override
  public boolean themPhuTung(PhuTung pt) throws RemoteException {
    return dao.themPhuTung(pt) ? true : false;
  }

  @Override
  public boolean xoaPhuTung(String mapt) throws RemoteException {
    return dao.xoaPhuTung(mapt) ? true : false;
  }

  @Override
  public boolean suaPhuTung(PhuTung pt) throws RemoteException {
    return dao.suaPhuTung(pt) ? true : false;
  }

  @Override
  public List<DichVu> getAllDichVu() throws RemoteException {
    return dao.getAllDichVu();
  }

  @Override
  public List<PhuTung> getAllPhuTung() throws RemoteException {
    return dao.getAllPhuTung();
  }

  @Override
  public void tangSoLuongPhuTung(String maPT) throws RemoteException {
    dao.tangSoLuongPhuTung(maPT);
  }

  @Override
  public void giamSoLuongPhuTung(String maPT) throws RemoteException {
    dao.giamSoLuongPhuTung(maPT);
  }

  @Override
  public int validatorDichVu(String tenDV, String donGia) throws RemoteException {
    if (tenDV.isEmpty()) {
      return 1;
    } else if (tenDV.matches(".*[!@#$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/].*")) {
      return 2;
    } else if (donGia.isEmpty()) {
      return 3;
    } else if (!donGia.matches("^[0-9]+(.[0-9]+)?$")) {
      return 4;
    }
    return 0;

  }

  @Override
  public int validatorPhuTung(String tenPT, String giaPT, String soLuong) throws RemoteException {
    if (tenPT.isEmpty()) {
      return 1;
    } else if (tenPT.matches(".*[!@#$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/].*")) {
      return 2;
    } else if (giaPT.isEmpty()) {
      return 3;
    } else if (!giaPT.matches("^[0-9]+(.[0-9]+)?$")) {
      return 4;
    } else if (soLuong.isEmpty()) {
      return 5;
    } else if (!soLuong.matches("^[1-9]\\d*$")) {
      return 6;
    }
    return 0;
  }

}
