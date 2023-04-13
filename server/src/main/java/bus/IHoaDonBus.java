package bus;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entities.*;

public interface IHoaDonBus extends Remote {
  boolean themHoaDonBaoHanh(HoaDonBaoHanh hd) throws RemoteException;

}
