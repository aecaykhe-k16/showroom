package app;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import bus.*;
import bus.impl.*;
import entities.NhanVien;
import entities.TaiKhoan;

public class Server {
  public static void main(String[] args) throws IOException, NamingException {
    NhanVien nv = new NhanVien("1111", "Admin", "0123456783", LocalDate.now(), true, LocalDate.now(), "Quản lý",
        "long@gmail.com",
        "Hanoi", "lam");
    nv.setQuanLy(nv);
    TaiKhoan taiKhoan = new TaiKhoan("admin", "1111", true, nv);
    Hashtable<String, String> env = new Hashtable<String, String>();
    env.put("java.security.policy", "rmi/policy.policy");
    LocateRegistry.createRegistry(8080);
    System.out.println("Server started on port 8080.");
    Context ctx = new InitialContext(env);
    IDichVuBus dichVuBus = new DichVuImpl();
    IHoaDonBus hoaDonBus = new HoaDonImpl();
    IHopDongBus hopDongBus = new HopDongImpl();
    IKhachHangBus khachHangBus = new KhachHangImpl();
    INhanVienBus nhanVienBus = new NhanVienImpl();
    IXeBus xeBus = new XeImpl();
    ITaiKhoanBus taiKhoanBus = new TaiKhoanImpl();

    ctx.bind("rmi://localhost:8080/dichVu", dichVuBus);
    ctx.bind("rmi://localhost:8080/hoaDon", hoaDonBus);
    ctx.bind("rmi://localhost:8080/hopDong", hopDongBus);
    ctx.bind("rmi://localhost:8080/khachHang", khachHangBus);
    ctx.bind("rmi://localhost:8080/nhanVien", nhanVienBus);
    ctx.bind("rmi://localhost:8080/xe", xeBus);
    ctx.bind("rmi://localhost:8080/taiKhoan", taiKhoanBus);
    if (nhanVienBus.getAllNhanVien().size() == 0) {
      taiKhoanBus.themTaiKhoan(taiKhoan);
    }

    System.out.println("Connected to server");
  }
}
