package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entities.*;

public interface IHoaDonDao extends Remote {
  boolean themHoaDonBaoHanh(HoaDonBaoHanh hd) throws RemoteException;

}
