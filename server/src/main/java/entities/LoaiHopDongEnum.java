package entities;

public enum LoaiHopDongEnum {
  THANG("THANG"),
  GOP("GOP");

  private String loaiHopDong;

  private LoaiHopDongEnum(String loaiHopDong) {
    this.loaiHopDong = loaiHopDong;
  }

  public String getLoaiHopDong() {
    return loaiHopDong;
  }
}
