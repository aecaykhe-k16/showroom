package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.HopDong;
import entities.HopDongTraGop;
import entities.HopDongTraThang;

public interface IHopDongDao extends Remote {

  List<HopDong> getAllHopDong() throws RemoteException;

  boolean themHopDongTraThang(HopDongTraThang hopDong) throws RemoteException;

  boolean suaHopDongTraThang(HopDongTraThang hopDong) throws RemoteException;

  boolean themHopDongTraGop(HopDongTraGop hopDong) throws RemoteException;

  boolean suaHopDongTraGop(HopDongTraGop hopDong) throws RemoteException;
}