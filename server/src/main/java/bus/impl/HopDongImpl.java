package bus.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import bus.IHopDongBus;
import dao.IHopDongDao;
import entities.*;

public class HopDongImpl extends UnicastRemoteObject implements IHopDongBus {
  private static final long serialVersionUID = 2L;
  private IHopDongDao dao;

  public HopDongImpl() throws RemoteException {
    super();
    dao = new dao.impl.HopDongImpl();
  }

  @Override
  public boolean themHopDongTraThang(HopDongTraThang hopDong) throws RemoteException {
    return dao.themHopDongTraThang(hopDong) ? true : false;
  }

  @Override
  public boolean suaHopDongTraThang(HopDongTraThang hopDong) throws RemoteException {
    return dao.suaHopDongTraThang(hopDong) ? true : false;
  }

  @Override
  public boolean themHopDongTraGop(HopDongTraGop hopDong) throws RemoteException {
    return dao.themHopDongTraGop(hopDong) ? true : false;
  }

  @Override
  public boolean suaHopDongTraGop(HopDongTraGop hopDong) throws RemoteException {
    return dao.suaHopDongTraGop(hopDong) ? true : false;
  }

  @Override
  public int validatorTraThang(String soTienCanThanhToan) throws RemoteException {

    if (soTienCanThanhToan.isEmpty()) {
      return 1;
    } else if (!soTienCanThanhToan.matches("^[0-9]+(.[0-9]+)?$")) {
      return 2;
    } else if (Double.parseDouble(soTienCanThanhToan) < 0) {
      return 3;
    }
    return 0;
  }

  @Override
  public int validatorTraGop(String soTienTra, String soLan) throws RemoteException {
    if (String.valueOf(soTienTra).isEmpty()) {
      return 1;
    } else if (!soTienTra.matches("^[0-9]+(.[0-9]+)?$")) {
      return 2;
    } else if (Double.parseDouble(soTienTra) < 0) {
      return 3;
    } else if (soLan.isEmpty()) {
      return 4;
    } else if (!soLan.matches("^[1-9]\\d*$")) {
      return 5;
    }
    return 0;
  }

  @Override
  public List<HopDongTraThang> getAllHopDongTraThang() throws RemoteException {
    List<HopDongTraThang> list = new ArrayList<>();
    for (HopDong hd : dao.getAllHopDong()) {
      if (hd.getLoaiHopDong() == LoaiHopDongEnum.THANG) {
        list.add((HopDongTraThang) hd);
      }
    }
    return list;
  }

  @Override
  public List<HopDongTraGop> getAllHopDongTraGop() throws RemoteException {
    List<HopDongTraGop> list = new ArrayList<>();
    for (HopDong hd : dao.getAllHopDong()) {
      if (hd.getLoaiHopDong() == LoaiHopDongEnum.GOP) {
        list.add((HopDongTraGop) hd);
      }
    }
    return list;
  }

  @Override
  public List<HopDong> getAllHopDong() throws RemoteException {
    return dao.getAllHopDong();
  }

}
