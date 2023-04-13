package bus;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.*;

public interface INhanVienBus extends Remote {

  boolean suaNhanVien(NhanVien nv) throws RemoteException;

  int validator(String sdt, String viTri, String ten, String diaChi, String email) throws RemoteException;

  List<NhanVien> getAllNhanVien() throws RemoteException;
}
