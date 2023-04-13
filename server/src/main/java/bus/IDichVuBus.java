package bus;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.DichVu;
import entities.PhuTung;

public interface IDichVuBus extends Remote {
  boolean themDichVu(DichVu dv) throws RemoteException;

  boolean xoaDichVu(String maDV) throws RemoteException;

  boolean suaDichVu(DichVu dv) throws RemoteException;

  boolean themPhuTung(PhuTung pt) throws RemoteException;

  boolean xoaPhuTung(String mapt) throws RemoteException;

  boolean suaPhuTung(PhuTung pt) throws RemoteException;

  List<DichVu> getAllDichVu() throws RemoteException;

  List<PhuTung> getAllPhuTung() throws RemoteException;

  void tangSoLuongPhuTung(String maPT) throws RemoteException;

  void giamSoLuongPhuTung(String maPT) throws RemoteException;

  int validatorPhuTung(String tenPT, String giaPT, String soLuong) throws RemoteException;

  int validatorDichVu(String tenDV, String donGia) throws RemoteException;

}
