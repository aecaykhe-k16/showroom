package bus;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.*;

public interface IKhachHangBus extends Remote {
  boolean themKhachHang(KhachHang kh) throws RemoteException;

  boolean suaKhachHang(KhachHang kh) throws RemoteException;

  List<KhachHang> getAllKhachHang() throws RemoteException;

  KhachHang getKhachHang(String sdt) throws RemoteException;

  int validator(String sdt, String diaChi, String ten, String email) throws RemoteException;
}
