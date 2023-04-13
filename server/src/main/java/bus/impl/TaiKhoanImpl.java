package bus.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bus.ITaiKhoanBus;
import dao.ITaiKhoanDao;
import entities.*;

public class TaiKhoanImpl extends UnicastRemoteObject implements ITaiKhoanBus {
  private static final long serialVersionUID = 2L;
  private ITaiKhoanDao dao;

  public TaiKhoanImpl() throws RemoteException {
    super();
    dao = new dao.impl.TaiKhoanImpl();
  }

  @Override
  public boolean themTaiKhoan(TaiKhoan tk) throws RemoteException {
    return dao.themTaiKhoan(tk) ? true : false;
  }

  @Override
  public boolean suaTaiKhoan(TaiKhoan tk) throws RemoteException {
    return dao.suaTaiKhoan(tk) ? true : false;
  }

  @Override
  public TaiKhoan login(String tenDangNhap, String matKhau) throws RemoteException {
    return dao.login(tenDangNhap, matKhau);
  }

  @Override
  public boolean logout(TaiKhoan tk) throws RemoteException {
    return dao.logout(tk) ? true : false;
  }
}
