package entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "xe")
public class Xe implements Serializable {

  @Id
  @Column(nullable = false)
  private String maXe;

  @Column(nullable = false, columnDefinition = "nvarchar(50)")
  private String tenXe;

  @Column(nullable = false, columnDefinition = "nvarchar(20)")
  private String mauSac;

  @Column(nullable = false)
  private long giaTien;

  @Column(nullable = false)
  private int soGhe;

  @Column(nullable = false, columnDefinition = "varchar(250)")
  private String hinhAnh;

  @Enumerated(EnumType.STRING)
  @Column(name = "loai_xe", nullable = false)
  private LoaiXeEnum loaiXe;

  @Enumerated(EnumType.STRING)
  @Column(name = "thuong_hieu", nullable = false)
  private ThuongHieuEnum thuongHieu;

  @OneToMany(mappedBy = "xe")
  private List<HopDong> hopDong;

  public Xe() {
  }

  public Xe(String maXe, String tenXe, String mauSac, long giaTien, int soGhe, String hinhAnh, LoaiXeEnum loaiXe,
      ThuongHieuEnum thuongHieu) {
    this.maXe = maXe;
    this.tenXe = tenXe;
    this.mauSac = mauSac;
    this.giaTien = giaTien;
    this.soGhe = soGhe;
    this.hinhAnh = hinhAnh;
    this.loaiXe = loaiXe;
    this.thuongHieu = thuongHieu;
  }

  public String getMaXe() {
    return maXe;
  }

  public void setMaXe(String maXe) {
    this.maXe = maXe;
  }

  public String getTenXe() {
    return tenXe;
  }

  public void setTenXe(String tenXe) {
    this.tenXe = tenXe;
  }

  public String getMauSac() {
    return mauSac;
  }

  public void setMauSac(String mauSac) {
    this.mauSac = mauSac;
  }

  public long getGiaTien() {
    return giaTien;
  }

  public void setGiaTien(long giaTien) {
    this.giaTien = giaTien;
  }

  public int getSoGhe() {
    return soGhe;
  }

  public void setSoGhe(int soGhe) {
    this.soGhe = soGhe;
  }

  public String getHinhAnh() {
    return hinhAnh;
  }

  public void setHinhAnh(String hinhAnh) {
    this.hinhAnh = hinhAnh;
  }

  public LoaiXeEnum getLoaiXe() {
    return loaiXe;
  }

  public void setLoaiXe(LoaiXeEnum loaiXe) {
    this.loaiXe = loaiXe;
  }

  public ThuongHieuEnum getThuongHieu() {
    return thuongHieu;
  }

  public void setThuongHieu(ThuongHieuEnum thuongHieu) {
    this.thuongHieu = thuongHieu;
  }

  public List<HopDong> getHopDong() {
    return hopDong;
  }

  public void setHopDong(List<HopDong> hopDong) {
    this.hopDong = hopDong;
  }

  @Override
  public String toString() {
    return "Xe [maXe=" + maXe + ", tenXe=" + tenXe + ", mauSac=" + mauSac + ", giaTien=" + giaTien + ", soGhe=" + soGhe
        + ", hinhAnh=" + hinhAnh + ", loaiXe=" + loaiXe + ", thuongHieu=" + thuongHieu + "]";
  }

}
