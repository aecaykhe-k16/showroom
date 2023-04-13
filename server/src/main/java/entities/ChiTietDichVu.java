package entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chiTietDichVu")
public class ChiTietDichVu implements Serializable {

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "maHDBH")
  private HoaDonBaoHanh hoaDonBaoHanh;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "maDV")
  private DichVu dichVu;

  private int soLuong;

  public ChiTietDichVu() {
  }

  public ChiTietDichVu(DichVu dichVu, int soLuong) {
    this.dichVu = dichVu;
    this.soLuong = soLuong;
  }

  public HoaDonBaoHanh getHoaDonBaoHanh() {
    return hoaDonBaoHanh;
  }

  public void setHoaDonBaoHanh(HoaDonBaoHanh hoaDonBaoHanh) {
    this.hoaDonBaoHanh = hoaDonBaoHanh;
  }

  public DichVu getDichVu() {
    return dichVu;
  }

  public void setDichVu(DichVu dichVu) {
    this.dichVu = dichVu;
  }

  public int getSoLuong() {
    return soLuong;
  }

  public void setSoLuong(int soLuong) {
    this.soLuong = soLuong;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((hoaDonBaoHanh == null) ? 0 : hoaDonBaoHanh.hashCode());
    result = prime * result + ((dichVu == null) ? 0 : dichVu.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ChiTietDichVu other = (ChiTietDichVu) obj;
    if (hoaDonBaoHanh == null) {
      if (other.hoaDonBaoHanh != null)
        return false;
    } else if (!hoaDonBaoHanh.equals(other.hoaDonBaoHanh))
      return false;
    if (dichVu == null) {
      if (other.dichVu != null)
        return false;
    } else if (!dichVu.equals(other.dichVu))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "ChiTietDichVu [hoaDonBaoHanh=" + hoaDonBaoHanh + ", dichVu=" + dichVu + ", soLuong=" + soLuong + "]";
  }

}
