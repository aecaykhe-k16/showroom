package entities;

public enum LoaiXeEnum {
  XE_MUI_TRAN("Xe mui trần"),
  XE_THE_THAO("Xe thể thao"),
  XE_BAN_TAI("Xe bán tải"),
  XE_SEDAN("Xe sedan");

  private String loaiXe;

  private LoaiXeEnum(String loaiXe) {
    this.loaiXe = loaiXe;
  }

  public String getLoaiXe() {
    return loaiXe;
  }
}