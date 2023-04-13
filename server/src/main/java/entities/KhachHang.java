package entities;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.Proxy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "khachHang")
@Proxy(lazy = false)
public class KhachHang implements Serializable {
  @Id
  @Column(nullable = false)
  private String maKH;

  @Column(nullable = false, columnDefinition = "nvarchar(50)")
  private String tenKH;

  @Column(nullable = false, columnDefinition = "nvarchar(10)")
  private String sdt;

  @Column(nullable = false, columnDefinition = "nvarchar(40)")
  private String email;

  @Column(nullable = false)
  private boolean gioiTinh;

  @Column(nullable = false, columnDefinition = "nvarchar(50)")
  private String diaChi;

  @Column(nullable = false)
  private boolean trangThai;

  @OneToMany(mappedBy = "khachHang")
  private List<HoaDonBaoHanh> dsHDBH;

  public KhachHang() {
  }

  public KhachHang(String maKH, String tenKH, String sdt, boolean gioiTinh, String diaChi, String email,
      boolean trangThai) {
    this.maKH = maKH;
    this.tenKH = tenKH;
    this.sdt = sdt;
    this.gioiTinh = gioiTinh;
    this.diaChi = diaChi;
    this.email = email;
    this.trangThai = trangThai;
  }

  public String getMaKH() {
    return maKH;
  }

  public void setMaKH(String maKH) {
    this.maKH = maKH;
  }

  public String getTenKH() {
    return tenKH;
  }

  public void setTenKH(String tenKH) {
    this.tenKH = tenKH;
  }

  public String getSdt() {
    return sdt;
  }

  public void setSdt(String sdt) {
    this.sdt = sdt;
  }

  public boolean isGioiTinh() {
    return gioiTinh;
  }

  public void setGioiTinh(boolean gioiTinh) {
    this.gioiTinh = gioiTinh;
  }

  public String getDiaChi() {
    return diaChi;
  }

  public void setDiaChi(String diaChi) {
    this.diaChi = diaChi;
  }

  public List<HoaDonBaoHanh> getDsHDBH() {
    return dsHDBH;
  }

  public void setDsHDBH(List<HoaDonBaoHanh> dsHDBH) {
    this.dsHDBH = dsHDBH;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isTrangThai() {
    return trangThai;
  }

  public void setTrangThai(boolean trangThai) {
    this.trangThai = trangThai;
  }

  @Override
  public String toString() {
    return "KhachHang [maKH=" + maKH + ", tenKH=" + tenKH + ", sdt=" + sdt + ", email=" + email + ", gioiTinh="
        + gioiTinh + ", trangThai=" + trangThai + ", diaChi=" + diaChi + "]";
  }

}
