package entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "hopDongTraGop")
public class HopDongTraGop extends HopDong {

  private LocalDate ngayTra;
  private long soTienTraMoiLan;
  private int soLanTra;

  public HopDongTraGop() {

  }

  public HopDongTraGop(String maHopDong, LocalDate ngayLapHD, int thoiGianBaoHanh, long tongTien,
      LoaiHopDongEnum loaiHopDong, Xe xe, KhachHang khachHang, NhanVien nhanVien, LocalDate ngayTra,
      long soTienTraMoiLan, int soLanTra) {
    super(maHopDong, ngayLapHD, thoiGianBaoHanh, tongTien, loaiHopDong, xe, khachHang, nhanVien);
    this.ngayTra = ngayTra;
    this.soTienTraMoiLan = soTienTraMoiLan;
    this.soLanTra = soLanTra;
  }

  public LocalDate getNgayTra() {
    return ngayTra;
  }

  public void setNgayTra(LocalDate ngayTra) {
    this.ngayTra = ngayTra;
  }

  public long getSoTienTraMoiLan() {
    return soTienTraMoiLan;
  }

  public void setSoTienTraMoiLan(long soTienTraMoiLan) {
    this.soTienTraMoiLan = soTienTraMoiLan;
  }

  public int getSoLanTra() {
    return soLanTra;
  }

  public void setSoLanTra(int soLanTra) {
    this.soLanTra = soLanTra;
  }

  @Override
  public String toString() {
    return "HopDongTraGop [ngayTra=" + ngayTra + ", soTienTraMoiLan=" + soTienTraMoiLan + ", soLanTra=" + soLanTra
        + ", toString()=" + super.toString() + "]";
  }

}
