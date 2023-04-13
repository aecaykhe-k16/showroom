package bus;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.*;

public interface IHopDongBus extends Remote {

  boolean themHopDongTraThang(HopDongTraThang hopDong) throws RemoteException;

  boolean suaHopDongTraThang(HopDongTraThang hopDong) throws RemoteException;

  boolean themHopDongTraGop(HopDongTraGop hopDong) throws RemoteException;

  boolean suaHopDongTraGop(HopDongTraGop hopDong) throws RemoteException;

  int validatorTraThang(String soTienCanThanhToan) throws RemoteException;

  int validatorTraGop(String soTienTra, String soLan) throws RemoteException;

  List<HopDongTraThang> getAllHopDongTraThang() throws RemoteException;

  List<HopDongTraGop> getAllHopDongTraGop() throws RemoteException;

  List<HopDong> getAllHopDong() throws RemoteException;
}
