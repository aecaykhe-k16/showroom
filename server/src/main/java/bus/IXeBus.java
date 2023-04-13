package bus;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.*;

public interface IXeBus extends Remote {
  boolean themXe(Xe xe) throws RemoteException;

  boolean xoaXe(String maXe) throws RemoteException;

  boolean suaXe(Xe xe) throws RemoteException;

  List<Xe> getAllXe() throws RemoteException;

  Xe timXe(String tenXe) throws RemoteException;

  int validator(String tenXe, String mauSac, String giaXe, String soGhe, String hinhAnh) throws RemoteException;
}
