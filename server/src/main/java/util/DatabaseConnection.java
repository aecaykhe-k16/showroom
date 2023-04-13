package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import entities.ChiTietDichVu;
import entities.ChiTietHoaDonBaoHanh;
import entities.DichVu;
import entities.HoaDonBaoHanh;
import entities.HopDong;
import entities.HopDongTraGop;
import entities.HopDongTraThang;
import entities.KhachHang;
import entities.NhanVien;
import entities.PhuTung;
import entities.TaiKhoan;
import entities.Xe;

public class DatabaseConnection {
  private SessionFactory sessionFactory;
  private static DatabaseConnection databaseConnection = null;

  private DatabaseConnection() {
    StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
        .configure()
        .build();

    Metadata metadata = new MetadataSources(registry)
        .addAnnotatedClass(NhanVien.class)
        .addAnnotatedClass(TaiKhoan.class)
        .addAnnotatedClass(PhuTung.class)
        .addAnnotatedClass(ChiTietHoaDonBaoHanh.class)
        .addAnnotatedClass(HoaDonBaoHanh.class)
        .addAnnotatedClass(KhachHang.class)
        .addAnnotatedClass(ChiTietDichVu.class)
        .addAnnotatedClass(DichVu.class)
        .addAnnotatedClass(Xe.class)
        .addAnnotatedClass(HopDong.class)
        .addAnnotatedClass(HopDongTraGop.class)
        .addAnnotatedClass(HopDongTraThang.class)

        .getMetadataBuilder()
        .build();

    this.sessionFactory = metadata.getSessionFactoryBuilder().build();

  }

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public synchronized static DatabaseConnection getInstance() {
    if (databaseConnection == null)
      databaseConnection = new DatabaseConnection();

    return databaseConnection;
  }
}