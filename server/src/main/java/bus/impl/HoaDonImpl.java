package bus.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bus.IHoaDonBus;
import dao.IHoaDonDao;
import entities.HoaDonBaoHanh;

public class HoaDonImpl extends UnicastRemoteObject implements IHoaDonBus {
  private static final long serialVersionUID = 2L;
  private IHoaDonDao dao;

  public HoaDonImpl() throws RemoteException {
    super();
    dao = new dao.impl.HoaDonImpl();
  }

  @Override
  public boolean themHoaDonBaoHanh(HoaDonBaoHanh hd) throws RemoteException {
    return dao.themHoaDonBaoHanh(hd) ? true : false;
  }

}
