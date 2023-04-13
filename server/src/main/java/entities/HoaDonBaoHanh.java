package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "hoaDonBaoHanh")
public class HoaDonBaoHanh implements Serializable {

  @Id
  @Column(nullable = false)
  private String maHDBH;

  @OneToMany(mappedBy = "hoaDonBaoHanh")
  private List<ChiTietHoaDonBaoHanh> dsChiTietBH;

  @OneToMany(mappedBy = "hoaDonBaoHanh")
  private List<ChiTietDichVu> dsChiTietDichVu;

  @Column(nullable = false)
  private LocalDate ngayLapHD;

  @ManyToOne
  @JoinColumn(name = "maNV")
  private NhanVien nhanVien;

  @ManyToOne
  @JoinColumn(name = "maKH")
  private KhachHang khachHang;

  public HoaDonBaoHanh() {
  }

  public HoaDonBaoHanh(String maHDBH, LocalDate ngayLapHD) {
    this.maHDBH = maHDBH;
    this.ngayLapHD = ngayLapHD;
  }

  public HoaDonBaoHanh(String maHDBH, LocalDate ngayLapHD, NhanVien nhanVien, KhachHang khachHang) {
    this.maHDBH = maHDBH;
    this.ngayLapHD = ngayLapHD;
    this.nhanVien = nhanVien;
    this.khachHang = khachHang;
  }

  public String getMaHDBH() {
    return maHDBH;
  }

  public void setMaHDBH(String maHDBH) {
    this.maHDBH = maHDBH;
  }

  public LocalDate getNgayLapHD() {
    return ngayLapHD;
  }

  public void setNgayLapHD(LocalDate ngayLapHD) {
    this.ngayLapHD = ngayLapHD;
  }

  public List<ChiTietHoaDonBaoHanh> getDsChiTietBH() {
    return dsChiTietBH;
  }

  public void setDsChiTietBH(List<ChiTietHoaDonBaoHanh> dsChiTietBH) {
    this.dsChiTietBH = dsChiTietBH;
  }

  public NhanVien getNhanVien() {
    return nhanVien;
  }

  public void setNhanVien(NhanVien nhanVien) {
    this.nhanVien = nhanVien;
  }

  public KhachHang getKhachHang() {
    return khachHang;
  }

  public void setKhachHang(KhachHang khachHang) {
    this.khachHang = khachHang;
  }

  public List<ChiTietDichVu> getDsChiTietDichVu() {
    return dsChiTietDichVu;
  }

  public void setDsChiTietDichVu(List<ChiTietDichVu> dsChiTietDichVu) {
    this.dsChiTietDichVu = dsChiTietDichVu;
  }

  @Override
  public String toString() {
    return "HoaDonBaoHanh [maHDBH=" + maHDBH + ", ngayLapHD=" + ngayLapHD
        + ", nhanVien=" + nhanVien + ", khachHang=" + khachHang + "]";
  }

}
