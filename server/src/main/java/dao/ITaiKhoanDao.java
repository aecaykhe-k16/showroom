package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entities.TaiKhoan;

public interface ITaiKhoanDao extends Remote {
  boolean themTaiKhoan(TaiKhoan tk) throws RemoteException;

  boolean suaTaiKhoan(TaiKhoan tk) throws RemoteException;

  TaiKhoan login(String tenDangNhap, String matKahu) throws RemoteException;

  boolean logout(TaiKhoan tk) throws RemoteException;
}