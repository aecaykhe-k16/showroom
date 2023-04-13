package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.NhanVien;

public interface INhanVienDao extends Remote {

  boolean suaNhanVien(NhanVien nv) throws RemoteException;

  List<NhanVien> getAllNhanVien() throws RemoteException;

}