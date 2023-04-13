package bus;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entities.*;

public interface ITaiKhoanBus extends Remote {
  boolean themTaiKhoan(TaiKhoan tk) throws RemoteException;

  boolean suaTaiKhoan(TaiKhoan tk) throws RemoteException;

  TaiKhoan login(String tenDangNhap, String matKhau) throws RemoteException;

  boolean logout(TaiKhoan tk) throws RemoteException;
}
