package entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "taiKhoan")
public class TaiKhoan implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "matk")
  private String maTK;

  @Column(nullable = false, columnDefinition = "varchar(8)")
  private String matKhau;

  @Column(nullable = false, columnDefinition = "nvarchar(50)")
  private String tenDangNhap;

  @Column(nullable = false)
  private boolean trangThai;

  @OneToOne
  @MapsId
  @JoinColumn(name = "maNV")
  private NhanVien nhanVien;

  public TaiKhoan() {
  }

  public TaiKhoan(String tenDangNhap, String matKhau, boolean trangThai, NhanVien nv) {
    this.matKhau = matKhau;
    this.tenDangNhap = tenDangNhap;
    this.trangThai = trangThai;
    this.nhanVien = nv;
  }

  public String getTenDangNhap() {
    return tenDangNhap;
  }

  public void setTenDangNhap(String tenDangNhap) {
    this.tenDangNhap = tenDangNhap;
  }

  public String getMatKhau() {
    return matKhau;
  }

  public void setMatKhau(String matKhau) {
    this.matKhau = matKhau;
  }

  public boolean isTrangThai() {
    return trangThai;
  }

  public void setTrangThai(boolean trangThai) {
    this.trangThai = trangThai;
  }

  public NhanVien getNhanVien() {
    return nhanVien;
  }

  public void setNhanVien(NhanVien nhanVien) {
    this.nhanVien = nhanVien;
    this.tenDangNhap = nhanVien.getMaNV();
  }

  @Override
  public String toString() {
    return "tenDangNhap [tenDangNhap=" + tenDangNhap + ", matKhau=" + matKhau + ", trangThai=" + trangThai
        + ", nhanVien=" + nhanVien
        + "]";
  }
}
