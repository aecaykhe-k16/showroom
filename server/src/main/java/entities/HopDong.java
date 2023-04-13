package entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "hopDong")
@Inheritance(strategy = InheritanceType.JOINED)
public class HopDong implements Serializable {
  @Id
  @Column(nullable = false)
  private String maHopDong;

  @Column(nullable = false)
  private LocalDate ngayLapHD;

  @Column(nullable = false)
  private int thoiGianBaoHanh;

  @Column(nullable = false)
  private long tongTien;

  @Enumerated(EnumType.STRING)
  @Column(name = "loai_hop_dong", columnDefinition = "char(10)")
  private LoaiHopDongEnum loaiHopDong;

  @ManyToOne
  @JoinColumn(name = "maNV", foreignKey = @ForeignKey(name = "FK_HD_NhanVien"))
  private NhanVien nhanVien;

  @ManyToOne
  @JoinColumn(name = "maKH", foreignKey = @ForeignKey(name = "FK_HD_KhachHang"))
  private KhachHang khachHang;

  @ManyToOne
  @JoinColumn(name = "maXe", foreignKey = @ForeignKey(name = "FK_HD_Xe"))
  private Xe xe;

  public HopDong() {
  }

  public HopDong(String maHopDong, LocalDate ngayLapHD, int thoiGianBaoHanh, long tongTien,
      LoaiHopDongEnum loaiHopDong, Xe xe, KhachHang khachHang, NhanVien nhanVien) {
    this.maHopDong = maHopDong;
    this.ngayLapHD = ngayLapHD;
    this.thoiGianBaoHanh = thoiGianBaoHanh;
    this.tongTien = tongTien;
    this.loaiHopDong = loaiHopDong;
    this.xe = xe;
    this.khachHang = khachHang;
    this.nhanVien = nhanVien;
  }

  public String getMaHopDong() {
    return maHopDong;
  }

  public void setMaHopDong(String maHopDong) {
    this.maHopDong = maHopDong;
  }

  public LocalDate getNgayLapHD() {
    return ngayLapHD;
  }

  public void setNgayLapHD(LocalDate ngayLapHD) {
    this.ngayLapHD = ngayLapHD;
  }

  public int getThoiGianBaoHanh() {
    return thoiGianBaoHanh;
  }

  public void setThoiGianBaoHanh(int thoiGianBaoHanh) {
    this.thoiGianBaoHanh = thoiGianBaoHanh;
  }

  public long getTongTien() {
    return tongTien;
  }

  public void setTongTien(long tongTien) {
    this.tongTien = tongTien;
  }

  public LoaiHopDongEnum getLoaiHopDong() {
    return loaiHopDong;
  }

  public void setLoaiHopDong(LoaiHopDongEnum loaiHopDong) {
    this.loaiHopDong = loaiHopDong;
  }

  @Override
  public String toString() {
    return "HopDong [maHopDong=" + maHopDong + ", ngayLapHD=" + ngayLapHD + ", thoiGianBaoHanh=" + thoiGianBaoHanh
        + ", tongTien=" + tongTien + ", loaiHopDong=" + loaiHopDong + ", nhanVien=" + nhanVien + ", khachHang="
        + khachHang + ", xe=" + xe + "]";
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

  public Xe getXe() {
    return xe;
  }

  public void setXe(Xe xe) {
    this.xe = xe;
  }

}
