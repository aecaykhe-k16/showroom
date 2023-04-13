package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.KhachHang;

public interface IKhachHangDao extends Remote {
  boolean themKhachHang(KhachHang kh) throws RemoteException;

  boolean suaKhachHang(KhachHang kh) throws RemoteException;

  List<KhachHang> getAllKhachHang() throws RemoteException;
}
