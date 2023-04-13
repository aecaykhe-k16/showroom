package bus.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import bus.IXeBus;
import dao.IXeDao;
import entities.*;

public class XeImpl extends UnicastRemoteObject implements IXeBus {
  private static final long serialVersionUID = 2L;
  private IXeDao dao;

  public XeImpl() throws RemoteException {
    super();
    dao = new dao.impl.XeImpl();
  }

  @Override
  public boolean themXe(Xe xe) throws RemoteException {
    return dao.themXe(xe) ? true : false;
  }

  @Override
  public boolean xoaXe(String maXe) throws RemoteException {
    return dao.xoaXe(maXe) ? true : false;
  }

  @Override
  public boolean suaXe(Xe xe) throws RemoteException {
    return dao.suaXe(xe) ? true : false;
  }

  @Override
  public List<Xe> getAllXe() throws RemoteException {
    return dao.getAllXe();
  }

  @Override
  public int validator(String tenXe, String mauSac, String giaXe, String soGhe, String hinhAnh) throws RemoteException {
    if (tenXe.isEmpty()) {
      return 1;
    } else if (mauSac.isEmpty()) {
      return 2;
    } else if (giaXe.isEmpty()) {
      return 3;
    } else if (soGhe.isEmpty()) {
      return 4;
    } else if (hinhAnh.isEmpty()) {
      return 5;
    } else if (!giaXe.matches("[0-9]+")) {
      return 6;
    } else if (!soGhe.matches("[0-9]+")) {
      return 7;
    }
    return 0;
  }

  @Override
  public Xe timXe(String tenXe) throws RemoteException {
    return dao.timXe(tenXe);
  }
}
