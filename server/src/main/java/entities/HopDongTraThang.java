package entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "hopDongTraThang")
public class HopDongTraThang extends HopDong {
  private long soTienCanThanhToan;

  public HopDongTraThang() {
  }

  public HopDongTraThang(String maHopDong, LocalDate ngayLapHD, int thoiGianBaoHanh, long tongTien,
      LoaiHopDongEnum loaiHopDong, Xe xe, KhachHang khachHang, NhanVien nhanVien, long soTienCanThanhToan) {
    super(maHopDong, ngayLapHD, thoiGianBaoHanh, tongTien, loaiHopDong, xe, khachHang, nhanVien);
    this.soTienCanThanhToan = soTienCanThanhToan;
  }

  public long getSoTienCanThanhToan() {
    return soTienCanThanhToan;
  }

  public void setSoTienCanThanhToan(long soTienCanThanhToan) {
    this.soTienCanThanhToan = soTienCanThanhToan;
  }

  @Override
  public String toString() {
    return "HopDongTraThang [soTienCanThanhToan=" + soTienCanThanhToan + ", toString()=" + super.toString() + "]";
  }

}
