package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import bus.IHopDongBus;
import bus.IKhachHangBus;
import bus.INhanVienBus;
import bus.ITaiKhoanBus;
import bus.IXeBus;
import entities.HopDong;
import entities.HopDongTraGop;
import entities.HopDongTraThang;
import entities.KhachHang;
import entities.LoaiHopDongEnum;
import entities.NhanVien;
import entities.TaiKhoan;
import entities.Xe;
import util.Constants;
import util.Generator;
import util.MyButton;
import util.RoundJTextField;

public class QuanLyHopDong extends JFrame implements ActionListener, MouseListener {
  private JLabel lblIconLogo, lblTitle;
  private JLayeredPane pnlCenter;
  private JPanel pnlMenu, pnlTop, pnInfo, pnlBottom, pnlMainUI;
  private JLabel lblQuanLyHD, lblNgayLap, lblTenKhach, lblTenXe;
  private JButton btnMenu, btnBaoHanhXe, btnIconUser, btnIconLogout, btnRefresh;

  private DatePickerSettings dateSettings;
  private Cursor handCursor;
  private DefaultComboBoxModel<String> dfTenKH, dfTenXe;
  private JComboBox<String> cmbTenKH, cmbTenXe;
  private DatePicker txtNgayLap;
  private JComboBox<String> cmbLoaiHopDong, cmbThoiGianBaoHanh;
  private DefaultComboBoxModel<String> dfLoaiHopDong, dfThoiGianBaoHanh;

  // hợp động trả thẳng
  private JPanel pnlTraThang, pnlThongTinTraThang;
  private JLabel lblTraThang;
  private JTable tableTraThang;
  private DefaultTableModel tableModelTraThang;

  // hợp động trả góp
  private JPanel pnlTraGop, pnlThongTinTraGop;
  private JLabel lblTraGop;
  private JTable tableTraGop;
  private DefaultTableModel tableModelTraGop;

  // slide bar
  private JPanel pnlSlideBar, pnlMainSlider;
  private JButton btnNhanVien, btnHopDong, btnKhachHang, btnQuanLyXe, btnPhuTung, btnTroGiup, btnClose;
  private JLabel lblMenu;
  private JLayeredPane layeredPane;
  private boolean flag = false;

  // sửa thông tin hợp đồng
  private JLayeredPane pnlMainThongTin;
  private JLabel lblTenKHSua, lblTenXeSua, lblNgayLapSua, lblTongTien, lblTienKhachThanhToan, lblThoiGianBaoHanh,
      lblSDT, lblNgayTra, lblSoLanTra, lblLoaiHopDong;
  private JButton btnSuaThongTin, btnHuyThongTin, btnThem;
  private JTextField txtTenKH, txtTenXeSua, txtTongTien, txtTienKhachThanhToan, txtSoLanTra,
      txtSDT;
  private DatePicker txtNgayLapSua, txtNgayTra;
  IHopDongBus hopDongBus = (IHopDongBus) Naming.lookup("rmi://localhost:8080/hopDong");
  ITaiKhoanBus taiKhoanBus = (ITaiKhoanBus) Naming.lookup("rmi://localhost:8080/taiKhoan");
  INhanVienBus nhanVienBus = (INhanVienBus) Naming.lookup("rmi://localhost:8080/nhanVien");
  IKhachHangBus khachHangBus = (IKhachHangBus) Naming.lookup("rmi://localhost:8080/khachHang");
  IXeBus xeBus = (IXeBus) Naming.lookup("rmi://localhost:8080/xe");
  private Generator generator = new Generator();
  private Xe xe = new Xe();
  private Xe xeSua = new Xe();
  private KhachHang khachHang;
  private NhanVien nhanVien = new NhanVien();
  List<HopDongTraThang> dsTraThang = hopDongBus.getAllHopDongTraThang();
  List<HopDongTraGop> dsTraGop = hopDongBus.getAllHopDongTraGop();
  List<HopDong> dsHopDong = hopDongBus.getAllHopDong();
  private String maHD = "", ngayLap;
  private TaiKhoan taiKhoan = new TaiKhoan();
  URL url = new URL(Constants.ICON_LOGO);
  Image imageIcon = new ImageIcon(url).getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);

  public QuanLyHopDong(TaiKhoan taiKhoan, Xe x) throws MalformedURLException, RemoteException, NotBoundException {
    this.xe = x;
    this.nhanVien = taiKhoan.getNhanVien();
    this.taiKhoan = taiKhoan;
    setTitle(Constants.APP_NAME);
    setExtendedState(MAXIMIZED_BOTH);
    setMinimumSize(new Dimension(1500, 800));
    this.setIconImage(imageIcon);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    this.setBackground(Color.WHITE);
    setLayout(null);
    layeredPane = new JLayeredPane();
    layeredPane.setBounds(0, 0, 2000, 1500);
    add(layeredPane);
    MainUI();
    handCursor = new Cursor(Cursor.HAND_CURSOR);
    btnThem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
        "btnThem");
    btnThem.getActionMap().put("btnThem", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnThem.doClick();
      }
    });
    btnHuyThongTin.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0),
        "btnHuyThongTin");
    btnHuyThongTin.getActionMap().put("btnHuyThongTin", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnHuyThongTin.doClick();
      }
    });
    btnSuaThongTin.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0),
        "btnSuaThongTin");
    btnSuaThongTin.getActionMap().put("btnSuaThongTin", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnSuaThongTin.doClick();
      }
    });
    btnBaoHanhXe.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0),
        "btnBaoHanhXe");
    btnBaoHanhXe.getActionMap().put("btnBaoHanhXe", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnBaoHanhXe.doClick();
      }
    });
    btnRefresh.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0),
        "btnRefresh");
    btnRefresh.getActionMap().put("btnRefresh", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnRefresh.doClick();
      }
    });

  }

  private void MainUI() {
    pnlMainUI = new JPanel();
    pnlMainUI.setLayout(null);
    pnlMainUI.setBounds(0, 0, 2000, 1400);
    pnlMainUI.setBackground(Color.WHITE);
    pnlTop();
    pnlMenu();
    pnlCenter();
    pnlBottom();
    layeredPane.add(pnlMainUI, Integer.valueOf(1));

  }

  private void pnlCenter() {
    pnlCenter = new JLayeredPane();
    pnlCenter.setBounds(0, 160, 1800, 580);
    pnlCenter.setLayout(null);
    pnlCenter.setBackground(Color.WHITE);
    pnlThang();
    pnlGop();
    uiThongTin();

    pnlMainUI.add(pnlCenter);

  }

  private void uiThongTin() {
    dateSettings = new DatePickerSettings();
    dateSettings.setAllowKeyboardEditing(false);
    dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
    pnlMainThongTin = new JLayeredPane();
    TitledBorder titlDanhSach = new TitledBorder(BorderFactory.createLineBorder(Color.black), "Thêm hợp đồng");
    pnlMainThongTin.setBorder(titlDanhSach);
    pnlMainThongTin.setLayout(null);
    pnlMainThongTin.setBounds(950, 0, 700, 570);
    pnlMainThongTin.setBackground(Color.WHITE);

    lblTenKHSua = new JLabel("Tên khách hàng");
    lblTenKHSua.setBounds(20, 40, 200, 30);
    lblTenKHSua.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(lblTenKHSua);
    txtTenKH = new RoundJTextField(15);
    txtTenKH.setBounds(250, 40, 300, 25);
    txtTenKH.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(txtTenKH);

    lblSDT = new JLabel("Số điện thoại");
    lblSDT.setBounds(20, 90, 200, 30);
    lblSDT.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(lblSDT);
    txtSDT = new RoundJTextField(15);
    txtSDT.setBounds(250, 90, 300, 25);
    txtSDT.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    txtSDT.setEditable(true);
    pnlMainThongTin.add(txtSDT);

    lblTenXeSua = new JLabel("Tên xe");
    lblTenXeSua.setBounds(20, 140, 200, 30);
    lblTenXeSua.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(lblTenXeSua);
    txtTenXeSua = new RoundJTextField(15);
    if (xe != null)
      txtTenXeSua.setText(xe.getTenXe());
    txtTenXeSua.setBounds(250, 140, 300, 25);
    txtTenXeSua.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(txtTenXeSua);

    lblNgayLapSua = new JLabel("Ngày lập");
    lblNgayLapSua.setBounds(20, 190, 200, 30);
    lblNgayLapSua.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(lblNgayLapSua);
    txtNgayLapSua = new DatePicker(dateSettings);
    txtNgayLapSua.setDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    txtNgayLapSua.setBounds(250, 190, 300, 25);
    txtNgayLapSua.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(txtNgayLapSua);

    lblTongTien = new JLabel("Tổng tiền");
    lblTongTien.setBounds(20, 240, 200, 30);
    lblTongTien.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(lblTongTien);
    txtTongTien = new RoundJTextField(15);
    if (xe != null)
      txtTongTien.setText(xe.getGiaTien() + " VNĐ");
    txtTongTien.setBounds(250, 240, 300, 25);
    txtTongTien.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(txtTongTien);

    lblThoiGianBaoHanh = new JLabel("Thời gian bảo hành");
    lblThoiGianBaoHanh.setBounds(20, 290, 200, 30);
    lblThoiGianBaoHanh.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(lblThoiGianBaoHanh);
    dfThoiGianBaoHanh = new DefaultComboBoxModel<String>();
    dfThoiGianBaoHanh.addElement("Không bảo hành");
    dfThoiGianBaoHanh.addElement("6 tháng");
    dfThoiGianBaoHanh.addElement("12 tháng");
    dfThoiGianBaoHanh.addElement("18 tháng");
    dfThoiGianBaoHanh.addElement("24 tháng");
    cmbThoiGianBaoHanh = new JComboBox<String>(dfThoiGianBaoHanh);
    cmbThoiGianBaoHanh.setBounds(250, 290, 300, 25);
    cmbThoiGianBaoHanh.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    cmbThoiGianBaoHanh.setBackground(Color.WHITE);
    pnlMainThongTin.add(cmbThoiGianBaoHanh);

    lblLoaiHopDong = new JLabel("Loại hợp đông");
    lblLoaiHopDong.setBounds(20, 340, 200, 30);
    lblLoaiHopDong.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(lblLoaiHopDong);
    dfLoaiHopDong = new DefaultComboBoxModel<String>();
    dfLoaiHopDong.addElement("Trả thẳng");
    dfLoaiHopDong.addElement("Trả góp");
    cmbLoaiHopDong = new JComboBox<String>(dfLoaiHopDong);
    cmbLoaiHopDong.setBounds(250, 340, 300, 25);
    cmbLoaiHopDong.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    cmbLoaiHopDong.setBackground(Color.WHITE);
    pnlMainThongTin.add(cmbLoaiHopDong);
    uiThongTinTraThang();

    btnThem = new MyButton(10, Color.GREEN, Color.GREEN);
    btnThem.setBounds(60, 530, 200, 30);
    btnThem.setText("Thêm(F1)");
    btnThem.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));

    FontIcon icon = FontIcon.of(FontAwesomeSolid.PLUS);
    icon.setIconSize(25);
    btnThem.setIcon(icon);
    btnThem.setBorderPainted(false);
    btnThem.setFocusPainted(false);
    btnThem.setCursor(handCursor);
    if (xe == null) {
      btnThem.setEnabled(false);
    } else {
      btnThem.addActionListener(this);
    }
    pnlMainThongTin.add(btnThem);
    cmbLoaiHopDong.addActionListener(this);

    btnHuyThongTin = new MyButton(10, Color.decode("#F77E21"), Color.decode("#F77E21"));
    btnHuyThongTin.setText("Hủy(F2)");
    FontIcon delete = FontIcon.of(FontAwesomeSolid.TRASH_ALT, 20, Color.RED);
    btnHuyThongTin.setIcon(delete);
    btnHuyThongTin.setBounds(300, 530, 200, 30);

    btnHuyThongTin.setForeground(Color.BLACK);
    btnHuyThongTin.setBorderPainted(false);
    btnHuyThongTin.setFocusPainted(false);
    btnHuyThongTin.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    btnHuyThongTin.setCursor(handCursor);
    pnlMainThongTin.add(btnHuyThongTin);
    btnHuyThongTin.addActionListener(this);

    pnlCenter.add(pnlMainThongTin, 1);

    txtSDT.addActionListener(this);

  }

  private void uiSuaThongTin() {
    dateSettings = new DatePickerSettings();
    dateSettings.setAllowKeyboardEditing(false);
    dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
    pnlMainThongTin = new JLayeredPane();
    TitledBorder titlDanhSach = new TitledBorder(BorderFactory.createLineBorder(Color.black), "Sửa thông tin");
    pnlMainThongTin.setBorder(titlDanhSach);
    pnlMainThongTin.setLayout(null);
    pnlMainThongTin.setBounds(950, 0, 700, 570);
    pnlMainThongTin.setBackground(Color.WHITE);

    lblSDT = new JLabel("Số điện thoại");
    lblSDT.setBounds(20, 40, 200, 30);
    lblSDT.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(lblSDT);
    txtSDT = new RoundJTextField(15);
    txtSDT.setBounds(250, 40, 300, 25);
    txtSDT.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    txtSDT.setEditable(true);
    pnlMainThongTin.add(txtSDT);

    lblTenKHSua = new JLabel("Tên khách hàng");
    lblTenKHSua.setBounds(20, 90, 200, 30);
    lblTenKHSua.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(lblTenKHSua);
    txtTenKH = new RoundJTextField(15);
    txtTenKH.setBounds(250, 90, 300, 25);
    txtTenKH.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));

    pnlMainThongTin.add(txtTenKH);

    lblTenXeSua = new JLabel("Tên xe");
    lblTenXeSua.setBounds(20, 140, 200, 30);
    lblTenXeSua.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(lblTenXeSua);
    txtTenXeSua = new RoundJTextField(15);
    txtTenXeSua.setBounds(250, 140, 300, 25);
    txtTenXeSua.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(txtTenXeSua);

    lblNgayLapSua = new JLabel("Ngày lập");
    lblNgayLapSua.setBounds(20, 190, 200, 30);
    lblNgayLapSua.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(lblNgayLapSua);
    txtNgayLapSua = new DatePicker(dateSettings);
    txtNgayLapSua.setBounds(250, 190, 300, 25);
    txtNgayLapSua.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(txtNgayLapSua);

    lblTongTien = new JLabel("Tổng tiền");
    lblTongTien.setBounds(20, 240, 200, 30);
    lblTongTien.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(lblTongTien);
    txtTongTien = new RoundJTextField(15);
    txtTongTien.setBounds(250, 240, 300, 25);
    txtTongTien.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(txtTongTien);

    lblThoiGianBaoHanh = new JLabel("Thời gian bảo hành");
    lblThoiGianBaoHanh.setBounds(20, 290, 200, 30);
    lblThoiGianBaoHanh.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(lblThoiGianBaoHanh);
    dfThoiGianBaoHanh = new DefaultComboBoxModel<String>();
    dfThoiGianBaoHanh.addElement("Không bảo hành");
    dfThoiGianBaoHanh.addElement("6 tháng");
    dfThoiGianBaoHanh.addElement("12 tháng");
    dfThoiGianBaoHanh.addElement("18 tháng");
    dfThoiGianBaoHanh.addElement("24 tháng");
    cmbThoiGianBaoHanh = new JComboBox<String>(dfThoiGianBaoHanh);
    cmbThoiGianBaoHanh.setBounds(250, 290, 300, 25);
    cmbThoiGianBaoHanh.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    cmbThoiGianBaoHanh.setBackground(Color.WHITE);
    pnlMainThongTin.add(cmbThoiGianBaoHanh);

    lblLoaiHopDong = new JLabel("Loại hợp đông");
    lblLoaiHopDong.setBounds(20, 340, 200, 30);
    lblLoaiHopDong.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(lblLoaiHopDong);
    dfLoaiHopDong = new DefaultComboBoxModel<String>();
    dfLoaiHopDong.addElement("Trả thẳng");
    dfLoaiHopDong.addElement("Trả góp");
    cmbLoaiHopDong = new JComboBox<String>(dfLoaiHopDong);
    cmbLoaiHopDong.setBounds(250, 340, 300, 25);
    cmbLoaiHopDong.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    cmbLoaiHopDong.setBackground(Color.WHITE);
    pnlMainThongTin.add(cmbLoaiHopDong);
    uiThongTinTraThang();

    btnThem = new MyButton(10, Color.GREEN, Color.GREEN);
    btnThem.setBounds(60, 530, 200, 30);
    btnThem.setText("Lưu(F1)");
    FontIcon icon = FontIcon.of(FontAwesomeSolid.SAVE, 20, Color.BLACK);
    btnThem.setIcon(icon);
    btnThem.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));

    btnThem.setBorderPainted(false);
    btnThem.setFocusPainted(false);
    btnThem.setCursor(handCursor);
    pnlMainThongTin.add(btnThem);
    btnThem.addActionListener(this);
    cmbLoaiHopDong.addActionListener(this);

    btnHuyThongTin = new MyButton(10, Color.decode("#F77E21"), Color.decode("#F77E21"));
    btnHuyThongTin.setText("Hủy(F2)");
    FontIcon trash = FontIcon.of(FontAwesomeSolid.TRASH_ALT, 20, Color.RED);
    btnHuyThongTin.setBounds(300, 530, 200, 30);
    btnHuyThongTin.setIcon(trash);
    btnHuyThongTin.setForeground(Color.BLACK);
    btnHuyThongTin.setBorderPainted(false);
    btnHuyThongTin.setFocusPainted(false);
    btnHuyThongTin.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    btnHuyThongTin.setCursor(handCursor);
    pnlMainThongTin.add(btnHuyThongTin);
    btnHuyThongTin.addActionListener(this);

    pnlCenter.add(pnlMainThongTin);
    txtSDT.addActionListener(this);

  }

  private void uiThongTinTraThang() {
    pnlThongTinTraThang = new JPanel();
    pnlThongTinTraThang.setLayout(null);
    pnlThongTinTraThang.setBounds(20, 390, 550, 140);
    pnlThongTinTraThang.setBackground(Color.WHITE);
    pnlMainThongTin.add(pnlThongTinTraThang, 1);

    lblTienKhachThanhToan = new JLabel("Tiền khách thanh toán");
    lblTienKhachThanhToan.setBounds(0, 0, 200, 30);
    lblTienKhachThanhToan.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinTraThang.add(lblTienKhachThanhToan);
    txtTienKhachThanhToan = new RoundJTextField(15);
    txtTienKhachThanhToan.setBounds(230, 0, 300, 25);
    txtTienKhachThanhToan.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinTraThang.add(txtTienKhachThanhToan);

  }

  private void uiThongTinTraGop() {
    dateSettings = new DatePickerSettings();
    dateSettings.setAllowKeyboardEditing(false);
    dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
    pnlThongTinTraGop = new JPanel();
    pnlThongTinTraGop.setLayout(null);
    pnlThongTinTraGop.setBounds(20, 390, 550, 140);
    pnlThongTinTraGop.setBackground(Color.WHITE);
    pnlMainThongTin.add(pnlThongTinTraGop);

    lblTienKhachThanhToan = new JLabel("Tiền khách trả");
    lblTienKhachThanhToan.setBounds(0, 0, 200, 30);
    lblTienKhachThanhToan.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinTraGop.add(lblTienKhachThanhToan);
    txtTienKhachThanhToan = new RoundJTextField(15);
    txtTienKhachThanhToan.setBounds(230, 0, 300, 25);
    txtTienKhachThanhToan.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinTraGop.add(txtTienKhachThanhToan);

    lblSoLanTra = new JLabel("Số lần trả");
    lblSoLanTra.setBounds(0, 50, 200, 30);
    lblSoLanTra.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinTraGop.add(lblSoLanTra);
    txtSoLanTra = new RoundJTextField(15);
    txtSoLanTra.setBounds(230, 50, 300, 25);
    txtSoLanTra.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinTraGop.add(txtSoLanTra);

    lblNgayTra = new JLabel("Ngày trả");
    lblNgayTra.setBounds(0, 100, 200, 30);
    lblNgayTra.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinTraGop.add(lblNgayTra);
    txtNgayTra = new DatePicker(dateSettings);
    txtNgayTra.setBounds(230, 100, 300, 25);
    txtNgayTra.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinTraGop.add(txtNgayTra);
  }

  private void pnlGop() {
    pnlTraGop = new JPanel();
    pnlTraGop.setLayout(null);
    pnlTraGop.setBounds(0, 280, 940, 270);
    pnlTraGop.setBackground(Color.WHITE);

    lblTraGop = new JLabel("Hợp đồng trả góp");
    lblTraGop.setFont(new Font("Tahoma", Font.BOLD, 20));
    lblTraGop.setBounds(20, 0, 200, 30);
    pnlTraGop.add(lblTraGop);

    String[] header = { "Tên khách hàng", "Tên xe", "Ngày lập", "Tổng thanh toán", "Tiền đã trả",
        "Hạn bảo hành", "Ngày trả", "Số lần trả",
        "Nhân viên lập", "SDT", "maHD" };
    tableModelTraGop = new DefaultTableModel(header, 0);
    tableTraGop = new JTable(tableModelTraGop);
    tableTraGop.getColumnModel().getColumn(9).setMinWidth(0);
    tableTraGop.getColumnModel().getColumn(9).setMaxWidth(0);
    tableTraGop.getColumnModel().getColumn(9).setWidth(0);
    tableTraGop.getColumnModel().getColumn(10).setMinWidth(0);
    tableTraGop.getColumnModel().getColumn(10).setMaxWidth(0);
    tableTraGop.getColumnModel().getColumn(10).setWidth(0);
    tableTraGop.setRowHeight(30);
    tableTraGop.setDefaultEditor(Object.class, null);

    tableTraGop.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
    tableTraGop.setFont(new Font("Tahoma", Font.PLAIN, 14));
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);

    for (int i = 0; i < tableTraGop.getColumnCount(); i++) {
      tableTraGop.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    JScrollPane scrollPanePhuTung = new JScrollPane(tableTraGop);
    pnlTraGop.add(scrollPanePhuTung);
    scrollPanePhuTung.setBounds(20, 40, 900, 230);
    pnlCenter.add(pnlTraGop);
    loadDataGop();
    tableTraGop.addMouseListener(this);

  }

  private void pnlThang() {
    pnlTraThang = new JPanel();
    pnlTraThang.setLayout(null);
    pnlTraThang.setBounds(0, 0, 940, 270);
    pnlTraThang.setBackground(Color.WHITE);
    lblTraThang = new JLabel("Hợp đồng trả thẳng");
    lblTraThang.setFont(new Font("Tahoma", Font.BOLD, 20));
    lblTraThang.setBounds(20, 5, 200, 30);
    pnlTraThang.add(lblTraThang);

    String[] header = { "Tên khách hàng", "Tên xe", "Ngày lập", "Tổng thanh toán", "Tiền thanh toán",
        "Hạn bảo hành",
        "Nhân viên lập", "SDT", "maHD" };
    tableModelTraThang = new DefaultTableModel(header, 0);

    tableTraThang = new JTable(tableModelTraThang);
    tableTraThang.getColumnModel().getColumn(7).setMinWidth(0);
    tableTraThang.getColumnModel().getColumn(7).setMaxWidth(0);
    tableTraThang.getColumnModel().getColumn(7).setWidth(0);
    tableTraThang.getColumnModel().getColumn(8).setMinWidth(0);
    tableTraThang.getColumnModel().getColumn(8).setMaxWidth(0);
    tableTraThang.getColumnModel().getColumn(8).setWidth(0);
    tableTraThang.setRowHeight(30);
    tableTraThang.setDefaultEditor(Object.class, null);
    tableTraThang.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
    tableTraThang.setFont(new Font("Tahoma", Font.PLAIN, 14));
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);

    for (int i = 0; i < tableTraThang.getColumnCount(); i++) {
      tableTraThang.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    JScrollPane scrollPanePhuTung = new JScrollPane(tableTraThang);
    scrollPanePhuTung.setBounds(20, 40, 900, 230);
    pnlTraThang.add(scrollPanePhuTung);
    pnlCenter.add(pnlTraThang);
    loadDataThang();
    tableTraThang.addMouseListener(this);
  }

  private void pnlBottom() {

    pnlBottom = new JPanel();
    pnlBottom.setLayout(null);
    pnlBottom.setBounds(0, 720, 1800, 150);
    pnlBottom.setBackground(Color.WHITE);

    dateSettings = new DatePickerSettings();
    dateSettings.setAllowKeyboardEditing(false);
    dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);

    lblNgayLap = new JLabel("Ngày lập");
    lblNgayLap.setFont(new Font("Tahoma", Font.BOLD, 16));
    lblNgayLap.setBounds(20, 20, 100, 30);
    pnlBottom.add(lblNgayLap);
    txtNgayLap = new DatePicker(dateSettings);
    txtNgayLap.setBounds(110, 20, 160, 25);
    txtNgayLap.setFont(new Font("Arial", Font.PLAIN, 30));
    pnlBottom.add(txtNgayLap);
    txtNgayLap.addDateChangeListener(new DateChangeListener() {
      @Override
      public void dateChanged(DateChangeEvent dce) {
        if (dce.getNewDate() != null) {

          ngayLap = generator.parseLocalDateToDate(dce.getNewDate().toString());

          tableModelTraGop.setRowCount(0);
          try {
            List<HopDongTraGop> dsTraGop = hopDongBus.getAllHopDongTraGop();
            if (dsTraGop.size() > 0)
              for (HopDongTraGop gop : dsTraGop) {
                String ngayLap1 = generator.parseLocalDateToDate(gop.getNgayLapHD().toString());
                String ngayTra = generator.parseLocalDateToDate(gop.getNgayTra().toString());
                if (ngayLap1.equals(ngayLap))
                  tableModelTraGop
                      .addRow(new Object[] { gop.getKhachHang().getTenKH(),
                          gop.getXe().getTenXe(), ngayLap1, gop.getTongTien(), gop.getSoTienTraMoiLan(),
                          gop.getThoiGianBaoHanh() + " tháng", ngayTra, gop.getSoLanTra(),
                          gop.getNhanVien().getTenNV(), gop.getKhachHang().getSdt(), gop.getMaHopDong() });
              }
          } catch (Exception e) {
            e.printStackTrace();
          }
          tableModelTraThang.setRowCount(0);
          try {
            List<HopDongTraThang> dsTraThang = hopDongBus.getAllHopDongTraThang();
            if (dsTraThang.size() > 0)
              for (HopDongTraThang thang : dsTraThang) {

                String ngayLap2 = generator.parseLocalDateToDate(thang.getNgayLapHD().toString());
                if (ngayLap2.equals(ngayLap))
                  tableModelTraThang
                      .addRow(new Object[] { thang.getKhachHang().getTenKH(),
                          thang.getXe().getTenXe(), ngayLap2, thang.getTongTien(), thang.getSoTienCanThanhToan(),
                          thang.getThoiGianBaoHanh() + " tháng", thang.getNhanVien().getTenNV(),
                          thang.getKhachHang().getSdt(), thang.getMaHopDong() });
              }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    });

    lblTenKhach = new JLabel("Khách hàng");
    lblTenKhach.setFont(new Font("Tahoma", Font.BOLD, 16));
    lblTenKhach.setBounds(320, 20, 100, 30);
    pnlBottom.add(lblTenKhach);
    dfTenKH = new DefaultComboBoxModel<String>();
    try {
      for (KhachHang kh : khachHangBus.getAllKhachHang()) {
        dfTenKH.addElement(kh.getTenKH());
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    }
    cmbTenKH = new JComboBox<String>(dfTenKH);
    cmbTenKH.setBounds(430, 20, 200, 25);
    cmbTenKH.setFont(new Font("Arial", Font.PLAIN, 18));
    pnlBottom.add(cmbTenKH);

    lblTenXe = new JLabel("Tên xe");
    lblTenXe.setFont(new Font("Tahoma", Font.BOLD, 16));
    lblTenXe.setBounds(670, 20, 100, 30);
    pnlBottom.add(lblTenXe);
    dfTenXe = new DefaultComboBoxModel<String>();
    try {
      for (Xe xe : xeBus.getAllXe()) {
        dfTenXe.addElement(xe.getTenXe());
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    }
    cmbTenXe = new JComboBox<String>(dfTenXe);
    cmbTenXe.setBounds(740, 20, 150, 25);
    cmbTenXe.setFont(new Font("Arial", Font.PLAIN, 18));
    pnlBottom.add(cmbTenXe);

    btnRefresh = new JButton();
    FontIcon iconRefresh = FontIcon.of(FontAwesomeSolid.REDO, 20, Color.BLACK);
    btnRefresh.setIcon(iconRefresh);
    btnRefresh.setBounds(900, 20, 30, 30);
    btnRefresh.setBorder(null);

    btnRefresh.setToolTipText("Refresh");
    btnRefresh.setCursor(handCursor);
    btnRefresh.setBorderPainted(false);
    btnRefresh.setContentAreaFilled(false);
    btnRefresh.setFocusPainted(false);
    btnRefresh.setOpaque(false);
    pnlBottom.add(btnRefresh);

    btnBaoHanhXe = new MyButton(15, Color.cyan, Color.cyan);
    btnBaoHanhXe.setText("Bảo hành xe(F4)");
    btnBaoHanhXe.setBounds(1280, 20, 230, 30);
    btnBaoHanhXe.setBorderPainted(false);
    btnBaoHanhXe.setFocusPainted(false);
    btnBaoHanhXe.setFont(new Font("Arial", Font.PLAIN, 20));
    pnlBottom.add(btnBaoHanhXe);

    btnSuaThongTin = new MyButton(15, Color.GREEN, Color.GREEN);
    btnSuaThongTin.setBounds(970, 20, 250, 30);
    btnSuaThongTin.setText("Sửa thông tin(F3)");
    btnSuaThongTin.setBorderPainted(false);
    btnSuaThongTin.setFocusPainted(false);
    btnSuaThongTin.setBackground(Color.GREEN);
    btnSuaThongTin.setCursor(handCursor);
    btnSuaThongTin.setFont(new Font("Arial", Font.PLAIN, 20));
    pnlBottom.add(btnSuaThongTin);

    pnlMainUI.add(pnlBottom);

    btnBaoHanhXe.addActionListener(this);
    btnSuaThongTin.addActionListener(this);
    cmbTenKH.addActionListener(this);
    cmbTenXe.addActionListener(this);
    btnRefresh.addActionListener(this);
  }

  private void pnlTop() {
    pnlTop = new JPanel();
    pnlTop.setBounds(0, 0, 2000, 100);

    pnlTop.setBackground(Color.decode(Constants.MAIN_COLOR));
    pnlTop.setLayout(null);

    lblIconLogo = new JLabel();
    lblIconLogo.setBounds(50, -15, 300, 150);
    lblIconLogo.setIcon(new ImageIcon(imageIcon));

    lblTitle = new JLabel(Constants.TITLE);
    lblTitle.setBounds(570, 0, 750, 100);
    lblTitle.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 70));
    lblTitle.setForeground(Color.decode("#112D4E"));

    pnInfo = new JPanel();
    pnInfo.setBounds(1200, 0, 350, 100);
    pnInfo.setBackground(Color.decode(Constants.MAIN_COLOR));
    pnInfo.setLayout(null);
    btnIconUser = new JButton();
    btnIconUser.setText(nhanVien.getTenNV());

    btnIconUser.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 14));
    btnIconUser.setBounds(20, 0, 300, 50);
    btnIconUser.setBorder(null);
    btnIconUser.setFocusPainted(false);
    btnIconUser.setBorderPainted(false);
    btnIconUser.setBackground(Color.decode(Constants.MAIN_COLOR));
    btnIconUser.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 18));
    FontIcon user = FontIcon.of(FontAwesomeSolid.USER_CIRCLE, 30, Color.decode("#112D4E"));
    btnIconUser.setIcon(user);
    btnIconLogout = new JButton(Constants.DANG_XUAT);
    btnIconLogout.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 14));
    btnIconLogout.setBounds(0, 50, 300, 50);
    btnIconLogout.setBorder(null);
    btnIconLogout.setBackground(Color.decode(Constants.MAIN_COLOR));
    btnIconLogout.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 18));
    FontIcon logout = FontIcon.of(FontAwesomeSolid.SIGN_OUT_ALT, 30, Color.RED);
    btnIconLogout.setIcon(logout);

    pnInfo.add(btnIconUser);
    pnInfo.add(btnIconLogout);
    btnIconLogout.addActionListener(this);

    pnlTop.add(lblIconLogo);
    pnlTop.add(lblTitle);
    pnlTop.add(pnInfo);
    pnlMainUI.add(pnlTop);
  }

  private void pnlMenu() {
    pnlMenu = new JPanel();
    pnlMenu.setBounds(0, 100, 1800, 60);
    pnlMenu.setLayout(null);
    pnlMenu.setBackground(Color.decode("#71C9CE"));
    lblQuanLyHD = new JLabel("Quản lý hợp đồng");
    lblQuanLyHD.setBounds(580, 0, 400, 60);
    lblQuanLyHD.setBackground(Color.red);
    lblQuanLyHD.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 40));
    lblQuanLyHD.setForeground(Color.WHITE);
    btnMenu = new JButton();
    btnMenu.setCursor(handCursor);
    btnMenu.setBorder(null);
    btnMenu.setFocusPainted(false);
    btnMenu.setBounds(0, 0, 60, 60);
    btnMenu.setBackground(Color.decode("#71C9CE"));
    btnMenu.setIcon(FontIcon.of(FontAwesomeSolid.BARS, 40));
    pnlMenu.add(btnMenu);
    pnlMenu.add(lblQuanLyHD);
    pnlMainUI.add(pnlMenu);
    btnMenu.addActionListener(this);

  }

  private void SlideBar() {
    pnlMainSlider = new JPanel();
    pnlMainSlider.setBounds(0, 0, 300, 1400);
    pnlMainSlider.setLayout(null);

    pnlSlideBar = new JPanel();
    pnlSlideBar.setLayout(null);
    pnlSlideBar.setBounds(0, 0, 300, 2000);
    pnlSlideBar.setBackground(Color.decode("#CBF1F5"));

    Font f1 = new Font("Tahoma", Font.BOLD, 50);
    pnlSlideBar.add(lblMenu = new JLabel("Menu"));
    lblMenu.setFont(f1);
    lblMenu.setBounds(100, 0, 200, 80);

    Font f2 = new Font("Tahoma", Font.PLAIN, 25);
    btnNhanVien = new MyButton(0, Color.decode("#CBF1F5"), Color.decode("#CBF1F5"));
    btnNhanVien.setText("   Nhân viên");
    btnNhanVien.setHorizontalAlignment(SwingConstants.CENTER);
    btnNhanVien.setFont(f2);
    btnNhanVien.setBounds(40, 100, 220, 60);
    btnNhanVien.setBackground(Color.decode("#CBF1F5"));
    btnNhanVien.setBorder(null);
    FontIcon iconNhanVien = FontIcon.of(FontAwesomeSolid.USERS, 45);
    btnNhanVien.setIcon(iconNhanVien);
    btnNhanVien.setCursor(handCursor);
    pnlSlideBar.add(btnNhanVien);

    pnlSlideBar.add(btnHopDong = new MyButton(0, Color.decode("#CBF1F5"), Color.decode("#CBF1F5")));
    btnHopDong.setText("  Hợp đồng");
    btnHopDong.setHorizontalAlignment(SwingConstants.CENTER);
    btnHopDong.setFont(f2);
    btnHopDong.setBounds(40, 180, 220, 60);
    btnHopDong.setBorder(null);
    btnHopDong.setCursor(handCursor);
    FontIcon iconHopDong = FontIcon.of(FontAwesomeSolid.FILE_CONTRACT, 45);
    btnHopDong.setIcon(iconHopDong);

    pnlSlideBar.add(btnKhachHang = new MyButton(0, Color.decode("#CBF1F5"), Color.decode("#CBF1F5")));
    btnKhachHang.setText("  Khách hàng");
    btnKhachHang.setHorizontalAlignment(SwingConstants.CENTER);
    btnKhachHang.setFont(f2);
    btnKhachHang.setBounds(40, 260, 220, 60);
    btnKhachHang.setBorder(null);
    btnKhachHang.setCursor(handCursor);
    FontIcon iconKhachHang = FontIcon.of(FontAwesomeSolid.USER_TIE, 45);
    btnKhachHang.setIcon(iconKhachHang);

    pnlSlideBar.add(btnQuanLyXe = new MyButton(0, Color.decode("#CBF1F5"), Color.decode("#CBF1F5")));
    btnQuanLyXe.setText("  Quản lý xe");
    btnQuanLyXe.setHorizontalAlignment(SwingConstants.CENTER);
    btnQuanLyXe.setFont(f2);
    btnQuanLyXe.setBounds(40, 340, 220, 60);
    btnQuanLyXe.setBorder(null);
    btnQuanLyXe.setBackground(Color.decode("#CBF1F5"));
    btnQuanLyXe.setCursor(handCursor);
    FontIcon iconXe = FontIcon.of(FontAwesomeSolid.CAR, 45);
    btnQuanLyXe.setIcon(iconXe);

    pnlSlideBar.add(btnPhuTung = new MyButton(0, Color.decode("#CBF1F5"), Color.decode("#CBF1F5")));
    btnPhuTung.setText("  Phụ tùng");
    btnPhuTung.setHorizontalAlignment(SwingConstants.CENTER);
    btnPhuTung.setFont(f2);
    btnPhuTung.setBounds(40, 420, 220, 60);
    btnPhuTung.setBorder(null);
    btnPhuTung.setBackground(Color.decode("#CBF1F5"));
    btnPhuTung.setCursor(handCursor);
    FontIcon iconPhuTung = FontIcon.of(FontAwesomeSolid.COGS, 45);
    btnPhuTung.setIcon(iconPhuTung);

    pnlSlideBar.add(btnTroGiup = new MyButton(0, Color.decode("#CBF1F5"), Color.decode("#CBF1F5")));
    btnTroGiup.setText("  Trợ giúp");
    btnTroGiup.setHorizontalAlignment(SwingConstants.CENTER);
    btnTroGiup.setFont(f2);
    btnTroGiup.setBounds(40, 500, 220, 60);
    btnTroGiup.setBorder(null);
    btnTroGiup.setBackground(Color.decode("#CBF1F5"));
    btnTroGiup.setCursor(handCursor);
    FontIcon iconTroGiup = FontIcon.of(FontAwesomeSolid.QUESTION_CIRCLE, 45);
    btnTroGiup.setIcon(iconTroGiup);

    pnlSlideBar.add(btnClose = new JButton(""));
    btnClose.setFont(f2);
    btnClose.setBounds(240, 20, 50, 50);
    FontIcon iconPre = FontIcon.of(FontAwesomeSolid.ANGLE_LEFT, 40);
    btnClose.setIcon(iconPre);
    btnClose.setBorder(null);
    btnClose.setBackground(Color.decode("#CBF1F5"));
    btnClose.setCursor(handCursor);

    pnlMainSlider.add(pnlSlideBar);
    this.add(pnlMainSlider);
    btnClose.addActionListener(this);
    btnNhanVien.addActionListener(this);
    btnHopDong.addActionListener(this);
    btnKhachHang.addActionListener(this);
    btnQuanLyXe.addActionListener(this);
    btnPhuTung.addActionListener(this);
    btnTroGiup.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      int x = 300;
      if (e.getSource() == btnClose) {
        if (flag == true) {
          new Thread(new Runnable() {
            @Override
            public void run() {
              try {
                for (int i = 300; i > 0; i -= 2) {
                  Thread.sleep(1);
                  pnlSlideBar.setSize(i, 2000);

                }
                layeredPane.add(pnlMainUI, Integer.valueOf(1));
                layeredPane.remove(pnlMainSlider);
              } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);

              }
            }

          }).start();
          flag = false;
        }
      } else if (e.getSource() == btnMenu) {
        if (flag == false) {
          layeredPane.remove(pnlMainUI);
          SlideBar();
          layeredPane.add(pnlMainSlider, Integer.valueOf(2));
          pnlSlideBar.setSize(x, 2000);

          new Thread(new Runnable() {

            @Override
            public void run() {
              try {
                for (int i = 0; i < 300; i += 2) {
                  Thread.sleep(1);
                  pnlSlideBar.setSize(i, 2000);

                }
              } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);

              }
            }

          }).start();
          flag = true;
        }
      } else if (e.getSource() == btnNhanVien) {
        new QLNhanVien(taiKhoan).setVisible(true);
        this.dispose();
      } else if (e.getSource() == btnHopDong) {
        if (flag == true) {
          new Thread(new Runnable() {
            @Override
            public void run() {
              try {
                for (int i = 300; i > 0; i -= 2) {
                  Thread.sleep(1);
                  pnlSlideBar.setSize(i, 2000);

                }
                layeredPane.add(pnlMainUI, Integer.valueOf(1));
                layeredPane.remove(pnlMainSlider);
              } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);

              }
            }

          }).start();
          flag = false;
        }

      } else if (e.getSource() == btnKhachHang) {
        new QuanLyKhachHang(taiKhoan).setVisible(true);
        this.dispose();
      } else if (e.getSource() == btnQuanLyXe) {
        new QuanLyXe(taiKhoan).setVisible(true);
        this.dispose();
      } else if (e.getSource() == btnPhuTung) {
        new QuanLyPhuTungXeVaDichVu(taiKhoan).setVisible(true);
        this.dispose();
      } else if (e.getSource() == btnTroGiup) {
        try {
          Desktop.getDesktop().browse(new URL("https://phantan-nhom28.netlify.app/").toURI());
        } catch (Exception e2) {
          e2.printStackTrace();
        }
      }
      if (e.getSource() == cmbLoaiHopDong) {
        String loaiHopDong = cmbLoaiHopDong.getSelectedItem().toString();
        if (loaiHopDong.equals("Trả thẳng")) {
          pnlMainThongTin.remove(pnlThongTinTraGop);
          uiThongTinTraThang();
          pnlMainThongTin.add(pnlThongTinTraThang, 1);
        } else {
          pnlMainThongTin.remove(pnlThongTinTraThang);
          uiThongTinTraGop();
          pnlMainThongTin.add(pnlThongTinTraGop, 1);
        }
        cmbLoaiHopDong.setFocusable(false);

      } else if (e.getSource() == btnHuyThongTin) {
        clearForm();
      } else if (e.getSource() == btnBaoHanhXe) {
        String maBaoHanh = JOptionPane.showInputDialog(null, "Nhập mã hóa đơn", "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);
        if (maBaoHanh != null) {

          for (HopDong hd : dsHopDong) {
            if (hd.getMaHopDong().equals(maBaoHanh)) {
              try {
                new BaoHanhXe(hd).setVisible(true);
              } catch (MalformedURLException | RemoteException | NotBoundException e1) {
                e1.printStackTrace();
              }
            }
          }
        }
      } else if (e.getSource() == btnSuaThongTin) {
        if (btnSuaThongTin.getText().equals("Sửa thông tin(F3)")) {
          btnSuaThongTin.setText("Thêm hợp đồng(F3)");
          pnlCenter.remove(pnlMainThongTin);
          uiSuaThongTin();
          pnlCenter.add(pnlMainThongTin, 1);
        } else {
          btnSuaThongTin.setText("Sửa thông tin(F3)");
          pnlCenter.remove(pnlMainThongTin);
          uiThongTin();
          pnlCenter.add(pnlMainThongTin, 1);
        }
      } else if (e.getSource() == btnThem) {
        if (btnThem.getText() == "Thêm(F1)") {
          LocalDate ngayLap = txtNgayLapSua.getDate();
          int thoiGianBaoHanh = Integer.parseInt(cmbThoiGianBaoHanh.getSelectedItem().toString().split(" ")[0]);
          String loaiHopDong = cmbLoaiHopDong.getSelectedItem().toString();
          long tienKhachThanhToan = Long.parseLong(txtTienKhachThanhToan.getText());
          String maHD = generator.taoMaHopDong(ngayLap.toString().split("-")[2],
              loaiHopDong.equals("Trả thẳng") ? "TT" : "TG");

          if (loaiHopDong.equals("Trả thẳng")) {
            HopDongTraThang traThang = new HopDongTraThang(maHD, ngayLap,
                thoiGianBaoHanh, xe.getGiaTien(), LoaiHopDongEnum.THANG,
                xe, khachHang, nhanVien,
                tienKhachThanhToan);
            try {
              if (validatorTraThang(String.valueOf(tienKhachThanhToan))) {
                if (hopDongBus.themHopDongTraThang(traThang)) {
                  JOptionPane.showMessageDialog(null, "Thêm thành công");
                  clearForm();
                  loadDataThang();
                }
              }
            } catch (Exception e1) {
              e1.printStackTrace();
            }
          } else {
            int soLanTra = Integer.parseInt(txtSoLanTra.getText());
            LocalDate ngayTra = txtNgayTra.getDate();
            HopDongTraGop traGop = new HopDongTraGop(maHD, ngayLap,
                thoiGianBaoHanh, xe.getGiaTien(), LoaiHopDongEnum.GOP,
                xe, khachHang, nhanVien,
                ngayTra, tienKhachThanhToan, soLanTra);
            try {
              if (validatorTraGop(String.valueOf(tienKhachThanhToan), String.valueOf(soLanTra))) {
                if (hopDongBus.themHopDongTraGop(traGop)) {
                  JOptionPane.showMessageDialog(null, "Thêm thành công");
                  clearForm();
                  loadDataGop();
                }
              }
            } catch (Exception e1) {
              e1.printStackTrace();
            }
          }
        } else {
          LocalDate ngayLap = txtNgayLapSua.getDate();
          int thoiGianBaoHanh = Integer.parseInt(cmbThoiGianBaoHanh.getSelectedItem().toString().split(" ")[0]);
          String loaiHopDong = cmbLoaiHopDong.getSelectedItem().toString();
          long tienKhachThanhToan = Long.parseLong(txtTienKhachThanhToan.getText().split(" ")[0]);
          if (loaiHopDong.equals("Trả thẳng")) {
            HopDongTraThang traThang = new HopDongTraThang(maHD, ngayLap,
                thoiGianBaoHanh, xeSua.getGiaTien(), LoaiHopDongEnum.THANG,
                xeSua, khachHang, nhanVien,
                tienKhachThanhToan);
            try {
              if (validatorTraThang(String.valueOf(tienKhachThanhToan))) {
                if (hopDongBus.suaHopDongTraThang(traThang)) {
                  JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                  clearForm();
                  loadDataThang();

                }
              }
            } catch (Exception e1) {
              e1.printStackTrace();
            }
          } else {
            int soLanTra = Integer.parseInt(txtSoLanTra.getText());
            LocalDate ngayTra = txtNgayTra.getDate();
            HopDongTraGop traGop = new HopDongTraGop(maHD, ngayLap,
                thoiGianBaoHanh, xeSua.getGiaTien(), LoaiHopDongEnum.GOP,
                xeSua, khachHang, nhanVien,
                ngayTra, tienKhachThanhToan, soLanTra);
            try {
              if (validatorTraGop(String.valueOf(tienKhachThanhToan), String.valueOf(soLanTra))) {
                if (hopDongBus.suaHopDongTraGop(traGop)) {
                  JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                  clearForm();
                  loadDataGop();

                }
              }
            } catch (Exception e1) {
              e1.printStackTrace();
            }
          }
        }
      } else if (e.getSource() == txtSDT) {
        String sdt = txtSDT.getText();
        try {
          khachHang = khachHangBus.getKhachHang(sdt);
          if (khachHang != null) {
            txtTenKH.setText(khachHang.getTenKH());
            for (HopDong hd : dsHopDong) {
              if (btnSuaThongTin.getText().equals("Thêm hợp đồng(F3)")) {
                if (hd.getKhachHang().getSdt().equals(sdt)) {
                  tableModelTraThang.setRowCount(0);
                  tableModelTraGop.setRowCount(0);
                  String ngayDat = generator.parseLocalDateToDate(hd.getNgayLapHD().toString());
                  txtNgayLapSua.setText(generator.parseLocaldateToDatetimepicker(ngayDat));
                  dfThoiGianBaoHanh.setSelectedItem(hd.getThoiGianBaoHanh() + " tháng");
                  txtTenXeSua.setText(hd.getXe().getTenXe());
                  txtTongTien.setText(hd.getTongTien() + " VNĐ");
                  xeSua = hd.getXe();
                  loadDataThang();
                  loadDataGop();
                  for (int i = 0; i < tableModelTraThang.getRowCount(); i++) {
                    if (!tableModelTraThang.getValueAt(i, 7).equals(khachHang.getSdt())) {
                      tableModelTraThang.removeRow(i);
                    }
                  }
                  for (int i = 0; i < tableModelTraGop.getRowCount(); i++) {
                    if (!tableModelTraGop.getValueAt(i, 9).equals(khachHang.getSdt())) {
                      tableModelTraGop.removeRow(i);
                    }
                  }

                }
              }
            }

          } else {
            JOptionPane.showMessageDialog(null, "Khách hàng không tồn tại", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtTenKH.setText("");
          }
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      } else if (e.getSource() == cmbTenKH) {
        String tenKH = cmbTenKH.getSelectedItem().toString();
        tableModelTraGop.setRowCount(0);
        try {
          List<HopDongTraGop> dsTraGop = hopDongBus.getAllHopDongTraGop();
          if (dsTraGop.size() > 0)
            for (HopDongTraGop gop : dsTraGop) {
              String ngayLap = generator.parseLocalDateToDate(gop.getNgayLapHD().toString());
              String ngayTra = generator.parseLocalDateToDate(gop.getNgayTra().toString());
              if (gop.getKhachHang().getTenKH().equals(tenKH))
                tableModelTraGop
                    .addRow(new Object[] { gop.getKhachHang().getTenKH(),
                        gop.getXe().getTenXe(), ngayLap, gop.getTongTien(), gop.getSoTienTraMoiLan(),
                        gop.getThoiGianBaoHanh() + " tháng", ngayTra, gop.getSoLanTra(),
                        gop.getNhanVien().getTenNV(), gop.getKhachHang().getSdt(), gop.getMaHopDong() });
            }
        } catch (Exception e1) {
          e1.printStackTrace();
        }
        tableModelTraThang.setRowCount(0);
        try {
          List<HopDongTraThang> dsTraThang = hopDongBus.getAllHopDongTraThang();
          if (dsTraThang.size() > 0)
            for (HopDongTraThang thang : dsTraThang) {

              String ngayLap = generator.parseLocalDateToDate(thang.getNgayLapHD().toString());
              if (thang.getKhachHang().getTenKH().equals(tenKH))
                tableModelTraThang
                    .addRow(new Object[] { thang.getKhachHang().getTenKH(),
                        thang.getXe().getTenXe(), ngayLap, thang.getTongTien(), thang.getSoTienCanThanhToan(),
                        thang.getThoiGianBaoHanh() + " tháng", thang.getNhanVien().getTenNV(),
                        thang.getKhachHang().getSdt(), thang.getMaHopDong() });
            }
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      } else if (e.getSource() == cmbTenXe) {
        String tenXe = cmbTenXe.getSelectedItem().toString();
        tableModelTraGop.setRowCount(0);
        try {
          List<HopDongTraGop> dsTraGop = hopDongBus.getAllHopDongTraGop();
          if (dsTraGop.size() > 0)
            for (HopDongTraGop gop : dsTraGop) {
              String ngayLap = generator.parseLocalDateToDate(gop.getNgayLapHD().toString());
              String ngayTra = generator.parseLocalDateToDate(gop.getNgayTra().toString());
              if (gop.getXe().getTenXe().equals(tenXe))
                tableModelTraGop
                    .addRow(new Object[] { gop.getKhachHang().getTenKH(),
                        gop.getXe().getTenXe(), ngayLap, gop.getTongTien(), gop.getSoTienTraMoiLan(),
                        gop.getThoiGianBaoHanh() + " tháng", ngayTra, gop.getSoLanTra(),
                        gop.getNhanVien().getTenNV(), gop.getKhachHang().getSdt(), gop.getMaHopDong() });
            }
        } catch (Exception e1) {
          e1.printStackTrace();
        }
        tableModelTraThang.setRowCount(0);
        try {
          List<HopDongTraThang> dsTraThang = hopDongBus.getAllHopDongTraThang();
          if (dsTraThang.size() > 0)
            for (HopDongTraThang thang : dsTraThang) {

              String ngayLap = generator.parseLocalDateToDate(thang.getNgayLapHD().toString());
              if (thang.getXe().getTenXe().equals(tenXe))
                tableModelTraThang
                    .addRow(new Object[] { thang.getKhachHang().getTenKH(),
                        thang.getXe().getTenXe(), ngayLap, thang.getTongTien(), thang.getSoTienCanThanhToan(),
                        thang.getThoiGianBaoHanh() + " tháng", thang.getNhanVien().getTenNV(),
                        thang.getKhachHang().getSdt(), thang.getMaHopDong() });
            }
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      } else if (e.getSource() == btnRefresh) {
        clearForm();
        loadDataGop();
        loadDataThang();
        txtNgayLap.setDate(null);

      }

      else if (e.getSource() == btnIconLogout) {
        if (taiKhoanBus.logout(taiKhoan)) {
          JOptionPane.showMessageDialog(null, "Đăng xuất thành công");
          new DangNhap().setVisible(true);
          this.dispose();
        }
      }

    } catch (MalformedURLException | RemoteException | NotBoundException o) {
      o.printStackTrace();
    }

  }

  private void loadDataThang() {
    tableModelTraThang.setRowCount(0);
    try {
      List<HopDongTraThang> dsTraThang = hopDongBus.getAllHopDongTraThang();

      if (dsTraThang.size() > 0)
        for (HopDongTraThang thang : dsTraThang) {

          String ngayLap = generator.parseLocalDateToDate(thang.getNgayLapHD().toString());
          tableModelTraThang
              .addRow(new Object[] { thang.getKhachHang().getTenKH(),
                  thang.getXe().getTenXe(), ngayLap, thang.getTongTien(), thang.getSoTienCanThanhToan(),
                  thang.getThoiGianBaoHanh() + " tháng", thang.getNhanVien().getTenNV(),
                  thang.getKhachHang().getSdt(), thang.getMaHopDong() });
        }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void loadDataGop() {
    tableModelTraGop.setRowCount(0);

    try {
      List<HopDongTraGop> dsTraGop = hopDongBus.getAllHopDongTraGop();
      if (dsTraGop.size() > 0)
        for (HopDongTraGop gop : dsTraGop) {
          String ngayLap = generator.parseLocalDateToDate(gop.getNgayLapHD().toString());
          String ngayTra = generator.parseLocalDateToDate(gop.getNgayTra().toString());
          tableModelTraGop
              .addRow(new Object[] { gop.getKhachHang().getTenKH(),
                  gop.getXe().getTenXe(), ngayLap, gop.getTongTien(), gop.getSoTienTraMoiLan(),
                  gop.getThoiGianBaoHanh() + " tháng", ngayTra, gop.getSoLanTra(),
                  gop.getNhanVien().getTenNV(), gop.getKhachHang().getSdt(), gop.getMaHopDong() });
        }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void clearForm() {
    txtTenKH.setText("");
    txtSDT.setText("");
    txtTenXeSua.setText("");
    txtNgayLapSua.setDate(null);
    txtTongTien.setText("");
    txtTienKhachThanhToan.setText("");
    if (cmbLoaiHopDong.getSelectedItem().toString().equals("Trả góp")) {
      txtSoLanTra.setText("");
      txtNgayTra.setDate(null);
    }

  }

  private boolean validatorTraThang(String soTienCanThanhToan) throws RemoteException {
    for (int i = 0; i <= 10; i++) {
      if (hopDongBus.validatorTraThang(soTienCanThanhToan) == i) {
        switch (i) {
          case 1:
            JOptionPane.showMessageDialog(null, "Tiền khách trả không được để trống", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtTienKhachThanhToan.requestFocus();
            return false;
          case 2:
            JOptionPane.showMessageDialog(null, "Tiền khách trả phải là số  và không chứa kí tự đặc biệt", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtTienKhachThanhToan.requestFocus();
            return false;
          case 3:
            JOptionPane.showMessageDialog(null, "Tiền khách trả phải lớn hơn 0", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtTienKhachThanhToan.requestFocus();
            return false;
          case 0:
            return true;
        }
      }
    }
    return false;
  }

  public boolean validatorTraGop(String soTienTra, String soLan) throws RemoteException {
    for (int i = 0; i <= 10; i++) {
      if (hopDongBus.validatorTraGop(soTienTra, soLan) == i) {
        switch (i) {
          case 1:
            JOptionPane.showMessageDialog(null, "Tiền khách trả không được để trống", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtTienKhachThanhToan.requestFocus();
            return false;
          case 2:
            JOptionPane.showMessageDialog(null, "Tiền khách trả phải là số  và không chứa kí tự đặc biệt", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtTienKhachThanhToan.requestFocus();
            return false;
          case 3:
            JOptionPane.showMessageDialog(null, "Tiền khách trả phải lớn hơn 0", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtTienKhachThanhToan.requestFocus();
            return false;
          case 4:
            JOptionPane.showMessageDialog(null, "Số lần trả không được để trống", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtTienKhachThanhToan.requestFocus();
            return false;
          case 5:
            JOptionPane.showMessageDialog(null, "Số lần trả phải lớn hơn 0", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtSoLanTra.requestFocus();
            return false;
          case 0:
            return true;
        }
      }
    }
    return false;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Object o = e.getComponent();
    if (btnSuaThongTin.getText().equals("Thêm hợp đồng(F3)") && khachHang != null) {

      if (o.equals(tableTraGop)) {
        int row = tableTraGop.getSelectedRow();
        maHD = tableTraGop.getValueAt(row, 10).toString();
        if (cmbLoaiHopDong.getSelectedItem().toString().equals("Trả thẳng")) {
          pnlMainThongTin.remove(pnlThongTinTraThang);
          uiThongTinTraGop();
          pnlMainThongTin.add(pnlThongTinTraGop, 1);
          String ngayTra2 = generator.parseLocaldateToDatetimepicker(tableTraGop.getValueAt(row,
              6).toString());
          txtNgayTra.setText(ngayTra2);
          txtTienKhachThanhToan.setText(tableTraGop.getValueAt(row, 4).toString() + " VNĐ");
          dfLoaiHopDong.setSelectedItem("Trả góp");
          txtSoLanTra.setText(tableTraGop.getValueAt(row, 7).toString());
        } else {
          String ngayTra2 = generator.parseLocaldateToDatetimepicker(tableTraGop.getValueAt(row,
              6).toString());
          txtNgayTra.setText(ngayTra2);
          txtTienKhachThanhToan.setText(tableTraGop.getValueAt(row, 4).toString() + " VNĐ");
          dfLoaiHopDong.setSelectedItem("Trả góp");
          txtSoLanTra.setText(tableTraGop.getValueAt(row, 7).toString());
        }
      }
      if (o.equals(tableTraThang)) {
        int row = tableTraThang.getSelectedRow();
        maHD = tableTraThang.getValueAt(row, 8).toString();
        if (!cmbLoaiHopDong.getSelectedItem().toString().equals("Trả thẳng")) {
          pnlMainThongTin.remove(pnlThongTinTraGop);
          uiThongTinTraThang();
          pnlMainThongTin.add(pnlThongTinTraThang, 1);
          dfLoaiHopDong.setSelectedItem("Trả thẳng");
          txtTienKhachThanhToan.setText(tableTraThang.getValueAt(row, 4).toString() + " VNĐ");
        } else {
          dfLoaiHopDong.setSelectedItem("Trả thẳng");
          txtTienKhachThanhToan.setText(tableTraThang.getValueAt(row, 4).toString() + " VNĐ");
        }
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {
    // out focus new color
    // btnSuaKH.setBackground(Color.decode("#FFDF2B"));
  }

}
