package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
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
import java.util.List;

import javax.swing.AbstractAction;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import bus.INhanVienBus;
import bus.ITaiKhoanBus;
import entities.NhanVien;
import entities.TaiKhoan;
import util.Constants;
import util.Generator;
import util.MyButton;
import util.RoundJTextField;
import util.RoundedBorderWithColor;

public class QLNhanVien extends JFrame implements ActionListener, MouseListener {
  private JLabel lblIconLogo, lblTitle;
  private JButton btnIconLogout;
  private JButton btnIconUser;
  private JPanel pnlMenu, pnlTop, pnInfo, pnlChucNang, pnlFilter, pnlCenter, pnlMainUI;
  private JLabel lblQuanLyNV, lblDSNV;
  private JButton btnThemNV, btnSuaNV, btnXoaNV, btnTimkiemNV, btnMenu;
  private JTextField txtTimkiemNV;
  private DefaultComboBoxModel<String> dfBoxModelGioiTinh, dfBoxModelvitri, dfBoxModelViTriCongViec;
  private JComboBox<String> cmbGioiTinh, cmbViTriFilter, cmbViTriCongViec, cmbGioiTinhFilter;
  private JTable tblNhanVien;
  private DefaultTableModel tblModelNV;
  private JButton btnRefresh, btnFilter;

  private JLabel lblTenNV, lblSdt, lblNgayTuyenDung, lblNgaySinh, lblGioiTinh, lblViTriCongViec, lblDiaChi, lblEmail;
  private JTextField txtTenNV, txtSdt, txtDiaChi, txtEmail;
  DatePicker txtNgaySinh;
  private DatePicker txtNgayTuyenDung;
  private DatePickerSettings dateSettings;
  private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

  // slide bar
  private JPanel pnlSlideBar, pnlMainSlider;
  private JButton btnNhanVien, btnHopDong, btnKhachHang, btnQuanLyXe, btnPhuTung, btnTroGiup, btnClose;
  private JLabel lblMenu;
  private JLayeredPane layeredPane;
  private boolean flag = false;

  // function
  private Generator generator = new Generator();
  INhanVienBus nhanVienBus = (INhanVienBus) Naming.lookup("rmi://localhost:8080/nhanVien");
  ITaiKhoanBus taiKhoanBus = (ITaiKhoanBus) Naming.lookup("rmi://localhost:8080/taiKhoan");
  private String maNV = "";
  private NhanVien nhanVien = new NhanVien();
  private TaiKhoan taiKhoan = new TaiKhoan();
  URL url = new URL(Constants.ICON_LOGO);
  Image imageIcon = new ImageIcon(url).getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);

  public QLNhanVien(TaiKhoan taiKhoan) throws MalformedURLException, RemoteException, NotBoundException {
    this.nhanVien = taiKhoan.getNhanVien();
    this.taiKhoan = taiKhoan;
    setTitle(Constants.APP_NAME);
    setExtendedState(MAXIMIZED_BOTH);
    setMinimumSize(new Dimension(1560, 830));

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    this.setBackground(Color.WHITE);
    this.setIconImage(imageIcon);
    setLayout(null);
    layeredPane = new JLayeredPane();
    layeredPane.setBounds(0, 0, 2000, 1500);
    add(layeredPane);
    MainUI();
    handCursor = new Cursor(Cursor.HAND_CURSOR);

    dateSettings = new DatePickerSettings();
    dateSettings.setAllowKeyboardEditing(false);
    dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);

    btnThemNV.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
        "btnThemNV");
    btnThemNV.getActionMap().put("btnThemNV", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnThemNV.doClick();
      }
    });
    btnSuaNV.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0),
        "btnSuaNV");
    btnSuaNV.getActionMap().put("btnSuaNV", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnSuaNV.doClick();
      }
    });
    btnXoaNV.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0),
        "btnXoaNV");
    btnXoaNV.getActionMap().put("btnXoaNV", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnXoaNV.doClick();
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
    btnTimkiemNV.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
        "btnTimkiemNV");
    btnTimkiemNV.getActionMap().put("btnTimkiemNV", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnTimkiemNV.doClick();
      }
    });
  }

  private void MainUI() {
    pnlMainUI = new JPanel();
    pnlMainUI.setLayout(null);
    pnlMainUI.setBounds(0, 0, 2000, 1400);
    pnlMainUI.setBackground(Color.decode("#E3FDFD"));
    pnlTop();
    PnlMenu();
    PnlCenter();
    PnlTable();
    layeredPane.add(pnlMainUI, Integer.valueOf(1));
    tblNhanVien.addMouseListener(new MouseListener() {

      public void mouseReleased(MouseEvent e) {

      }

      public void mousePressed(MouseEvent e) {

      }

      public void mouseExited(MouseEvent e) {

      }

      public void mouseEntered(MouseEvent e) {

      }

      public void mouseClicked(MouseEvent e) {
        int row = tblNhanVien.getSelectedRow();
        maNV = tblNhanVien.getValueAt(row, 0).toString();
        txtTenNV.setText(tblNhanVien.getValueAt(row, 1).toString());
        txtSdt.setText(tblNhanVien.getValueAt(row, 2).toString());
        txtNgaySinh.setText(generator.parseLocaldateToDatetimepicker(tblNhanVien.getValueAt(row, 3).toString()));
        cmbGioiTinh.setSelectedItem(tblNhanVien.getValueAt(row, 4).toString());
        txtNgayTuyenDung.setText(generator.parseLocaldateToDatetimepicker(tblNhanVien.getValueAt(row, 5).toString()));
        cmbViTriCongViec.setSelectedItem(tblNhanVien.getValueAt(row, 6).toString());
        txtDiaChi.setText(tblNhanVien.getValueAt(row, 7).toString());
        txtEmail.setText(tblNhanVien.getValueAt(row, 8).toString());
      }
    });
  }

  void PnlTable() {
    JPanel pnlBang = new JPanel();
    pnlBang.setLayout(new BorderLayout());

    String[] header = { "Mã nhân viên", "Họ Tên", "Số điện thoại", "Ngày Sinh", "Giới Tính ",
        "Ngày Tuyển Dụng", "Vị trí công việc", "Địa chỉ", "Email" };
    tblModelNV = new DefaultTableModel(header, 0);
    tblNhanVien = new JTable(tblModelNV);
    JScrollPane scrollPane = new JScrollPane(tblNhanVien);
    tblNhanVien.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 15));
    tblNhanVien.setRowHeight(20);
    tblNhanVien.getTableHeader().setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 15));
    tblNhanVien.getTableHeader().setBackground(Color.WHITE);
    pnlBang.add(scrollPane);
    pnlBang.setBounds(10, 430, 1520, 360);
    pnlBang.setBackground(Color.decode("#E3FDFD"));
    tblNhanVien.setDefaultEditor(Object.class, null);

    tblNhanVien.getColumnModel().getColumn(8).setMinWidth(170);
    tblNhanVien.getColumnModel().getColumn(4).setMaxWidth(80);
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int x = 0; x < 9; x++) {
      tblNhanVien.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
    }

    pnlMainUI.add(pnlBang);
    loaData();

  }

  void PnlCenter() {

    DatePickerSettings dateSettings1 = new DatePickerSettings();
    dateSettings1.setAllowKeyboardEditing(false);
    dateSettings1.setFirstDayOfWeek(DayOfWeek.MONDAY);
    DatePickerSettings dateSettings2 = new DatePickerSettings();
    dateSettings2.setAllowKeyboardEditing(false);
    dateSettings2.setFirstDayOfWeek(DayOfWeek.MONDAY);
    pnlCenter = new JPanel();
    pnlCenter.setBounds(20, 180, 1800, 250);
    pnlCenter.setLayout(null);
    pnlCenter.setBackground(Color.decode("#E3FDFD"));
    lblDSNV = new JLabel("Danh sách nhân viên:");
    lblDSNV.setBounds(0, 200, 450, 70);
    lblDSNV.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    lblDSNV.setForeground(Color.decode(Constants.MENU_TITLE_COLOR));
    pnlCenter.add(lblDSNV);

    pnlChucNang = new JPanel();
    pnlChucNang.setLayout(null);
    pnlChucNang.setBounds(0, 0, 1800, 170);
    pnlChucNang.setBackground(Color.decode("#E3FDFD"));

    lblTenNV = new JLabel("Họ tên nhân viên:");
    lblTenNV.setBounds(50, 10, 200, 30);
    lblTenNV.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlChucNang.add(lblTenNV);
    txtTenNV = new RoundJTextField(15);
    txtTenNV.setBounds(200, 10, 400, 30);
    txtTenNV.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 15));
    txtTenNV.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    txtTenNV.setBackground(Color.WHITE);
    txtTenNV.setForeground(Color.BLACK);
    pnlChucNang.add(txtTenNV);

    lblSdt = new JLabel("Số điện thoại:");

    lblSdt.setBounds(50, 50, 200, 30);
    lblSdt.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlChucNang.add(lblSdt);
    txtSdt = new RoundJTextField(15);
    txtSdt.setBounds(200, 50, 400, 30);
    txtSdt.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 15));
    txtSdt.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlChucNang.add(txtSdt);

    lblNgaySinh = new JLabel("Ngày sinh:");
    lblNgaySinh.setBounds(50, 90, 200, 30);
    lblNgaySinh.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlChucNang.add(lblNgaySinh);
    txtNgaySinh = new DatePicker(dateSettings1);
    txtNgaySinh.setBounds(200, 90, 400, 30);
    pnlChucNang.add(txtNgaySinh);

    lblGioiTinh = new JLabel("Giới tính:");
    lblGioiTinh.setBounds(670, 10, 200, 30);
    lblGioiTinh.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlChucNang.add(lblGioiTinh);
    cmbGioiTinh = new JComboBox<String>();
    cmbGioiTinh.addItem("Nam");
    cmbGioiTinh.addItem("Nữ");
    cmbGioiTinh.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    cmbGioiTinh.setBounds(820, 10, 400, 30);
    cmbGioiTinh.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 10));
    cmbGioiTinh.setBackground(Color.decode("#ffffff"));
    pnlChucNang.add(cmbGioiTinh);

    lblNgayTuyenDung = new JLabel("Ngày tuyển dụng:");
    lblNgayTuyenDung.setBounds(670, 50, 200, 30);
    lblNgayTuyenDung.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlChucNang.add(lblNgayTuyenDung);
    txtNgayTuyenDung = new DatePicker(dateSettings2);
    txtNgayTuyenDung.setBounds(820, 50, 400, 30);

    pnlChucNang.add(txtNgayTuyenDung);

    lblViTriCongViec = new JLabel("Vị trí công việc:");
    lblViTriCongViec.setBounds(670, 90, 200, 30);
    lblViTriCongViec.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlChucNang.add(lblViTriCongViec);
    String[] viTri = { "Quản lý", "Nhân viên" };
    dfBoxModelViTriCongViec = new DefaultComboBoxModel<String>(viTri);
    dfBoxModelViTriCongViec.setSelectedItem("Quản lý");
    cmbViTriCongViec = new JComboBox<String>(dfBoxModelViTriCongViec);
    cmbViTriCongViec.setBounds(820, 90, 400, 30);
    cmbViTriCongViec.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    cmbViTriCongViec.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 10));
    cmbViTriCongViec.setBackground(Color.decode("#ffffff"));
    pnlChucNang.add(cmbViTriCongViec);

    lblDiaChi = new JLabel("Địa chỉ:");
    lblDiaChi.setBounds(50, 130, 200, 30);
    lblDiaChi.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlChucNang.add(lblDiaChi);
    txtDiaChi = new RoundJTextField(15);
    txtDiaChi.setBounds(200, 130, 400, 30);
    txtDiaChi.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 15));
    txtDiaChi.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlChucNang.add(txtDiaChi);

    lblEmail = new JLabel("Email:");
    lblEmail.setBounds(670, 130, 200, 30);
    lblEmail.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlChucNang.add(lblEmail);
    txtEmail = new RoundJTextField(15);
    txtEmail.setBounds(820, 130, 400, 30);
    txtEmail.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 15));
    txtEmail.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlChucNang.add(txtEmail);

    btnThemNV = new MyButton(20, Color.decode(Constants.BTN_CONFIRM_COLOR), Color.decode(Constants.THIRD_COLOR));
    btnThemNV.setText("Thêm(F1)");
    btnThemNV.setBounds(1300, 15, 150, 35);
    FontIcon add = FontIcon.of(FontAwesomeSolid.PLUS);
    add.setIconSize(25);
    btnThemNV.setIcon(add);
    btnThemNV.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    btnThemNV.setCursor(handCursor);
    btnThemNV.setBorderPainted(false);
    btnThemNV.setFocusPainted(false);
    pnlChucNang.add(btnThemNV);

    btnSuaNV = new MyButton(20, Color.decode(Constants.BTN_EDIT_COLOR), Color.decode(Constants.THIRD_COLOR));
    btnSuaNV.setText("Sửa(F2)");
    btnSuaNV.setBounds(1300, 65, 150, 35);
    FontIcon update = FontIcon.of(FontAwesomeSolid.SAVE);
    update.setIconSize(25);
    btnThemNV.setIcon(update);
    btnSuaNV.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 20));

    btnSuaNV.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    btnSuaNV.setCursor(handCursor);
    btnSuaNV.setBorderPainted(false);
    btnSuaNV.setFocusPainted(false);
    pnlChucNang.add(btnSuaNV);

    btnXoaNV = new MyButton(20, Color.WHITE, Color.WHITE);
    btnXoaNV.setText("Xóa(F3)");
    btnXoaNV.setBounds(1300, 115, 150, 35);
    FontIcon delete = FontIcon.of(FontAwesomeSolid.TRASH_ALT, 20, Color.RED);
    btnXoaNV.setIcon(delete);
    btnXoaNV.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 20));

    btnXoaNV.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    btnXoaNV.setCursor(handCursor);
    btnXoaNV.setBorderPainted(false);
    btnXoaNV.setFocusPainted(false);
    pnlChucNang.add(btnXoaNV);

    pnlCenter.add(pnlChucNang);

    pnlFilter = new JPanel();
    pnlFilter.setLayout(null);
    pnlFilter.setBounds(0, 180, 1800, 70);
    pnlFilter.setBackground(Color.decode("#E3FDFD"));

    JLabel lblGioiTinhFilter = new JLabel("Giới tính:");
    lblGioiTinhFilter.setBounds(100, 3, 200, 20);
    lblGioiTinhFilter.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 16));
    pnlFilter.add(lblGioiTinhFilter);

    dfBoxModelGioiTinh = new DefaultComboBoxModel<String>();
    dfBoxModelGioiTinh.addElement("");
    dfBoxModelGioiTinh.addElement("Nam");
    dfBoxModelGioiTinh.addElement("Nữ");
    cmbGioiTinhFilter = new JComboBox<String>(dfBoxModelGioiTinh);
    cmbGioiTinhFilter.setBounds(170, 3, 100, 25);
    cmbGioiTinhFilter.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 16));
    cmbGioiTinhFilter.setBackground(Color.WHITE);
    pnlFilter.add(cmbGioiTinhFilter);

    JLabel lblViTriCongViecFilter = new JLabel("Vị trí công việc:");
    lblViTriCongViecFilter.setBounds(300, 3, 200, 20);
    lblViTriCongViecFilter.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 16));
    pnlFilter.add(lblViTriCongViecFilter);

    dfBoxModelvitri = new DefaultComboBoxModel<String>();
    dfBoxModelvitri.addElement("");
    dfBoxModelvitri.addElement("Nhân viên");
    dfBoxModelvitri.addElement("Quản lý");
    cmbViTriFilter = new JComboBox<String>(dfBoxModelvitri);
    cmbViTriFilter.setBounds(420, 3, 150, 25);
    cmbViTriFilter.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 16));
    cmbViTriFilter.setBackground(Color.decode("#ffffff"));
    pnlFilter.add(cmbViTriFilter);

    btnFilter = new MyButton(15, Color.decode(Constants.BTN_FILTER_COLOR), Color.decode(Constants.BTN_FILTER_COLOR));
    btnFilter.setText("Lọc");
    btnFilter.setBounds(600, 0, 120, 30);
    FontIcon filter = FontIcon.of(FontAwesomeSolid.FILTER, 20, Color.BLACK);
    btnFilter.setIcon(filter);

    btnFilter.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 16));
    btnFilter.setCursor(handCursor);
    btnFilter.setBorderPainted(false);
    btnFilter.setFocusPainted(false);
    pnlFilter.add(btnFilter);

    btnTimkiemNV = new MyButton(15, Color.decode(Constants.BTN_FILTER_COLOR), Color.decode(Constants.BTN_FILTER_COLOR));
    btnTimkiemNV.setBounds(1160, 0, 150, 30);
    FontIcon search = FontIcon.of(FontAwesomeSolid.SEARCH, 20, Color.BLACK);
    btnTimkiemNV.setIcon(search);

    btnTimkiemNV.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    btnTimkiemNV.setCursor(handCursor);
    btnTimkiemNV.setBorderPainted(false);
    btnTimkiemNV.setFocusPainted(false);
    pnlFilter.add(btnTimkiemNV);
    txtTimkiemNV = new RoundJTextField(15);
    txtTimkiemNV.setBounds(900, 0, 250, 30);
    txtTimkiemNV.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    txtTimkiemNV.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 20));
    pnlFilter.add(txtTimkiemNV);

    btnRefresh = new JButton();
    btnRefresh.setBounds(1350, 0, 30, 30);
    btnRefresh.setBorder(null);
    FontIcon refresh = FontIcon.of(FontAwesomeSolid.REDO, 20, Color.BLACK);
    btnRefresh.setIcon(refresh);

    btnRefresh.setToolTipText("Refresh");
    btnRefresh.setCursor(handCursor);
    btnRefresh.setBorderPainted(false);
    btnRefresh.setContentAreaFilled(false);
    btnRefresh.setFocusPainted(false);
    btnRefresh.setOpaque(false);
    pnlFilter.add(btnRefresh);

    pnlCenter.add(pnlFilter);
    pnlMainUI.add(pnlCenter);
    btnThemNV.addActionListener(this);
    btnXoaNV.addActionListener(this);
    btnSuaNV.addActionListener(this);
    btnTimkiemNV.addActionListener(this);
    btnRefresh.addActionListener(this);
    btnFilter.addActionListener(this);
  }

  void PnlMenu() {
    pnlMenu = new JPanel();
    pnlMenu.setBounds(0, 100, 1800, 60);
    pnlMenu.setLayout(null);
    pnlMenu.setBackground(Color.decode(Constants.MENU_COLOR));
    lblQuanLyNV = new JLabel("Quản lý nhân viên");
    lblQuanLyNV.setBounds(600, 0, 500, 60);
    lblQuanLyNV.setBackground(Color.WHITE);
    lblQuanLyNV.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 35));
    lblQuanLyNV.setForeground(Color.WHITE);
    btnMenu = new JButton();
    btnMenu.setBounds(0, 0, 60, 60);
    btnMenu.setBackground(Color.decode(Constants.MENU_COLOR));
    btnMenu.setIcon(FontIcon.of(FontAwesomeSolid.BARS, 40));
    btnMenu.setCursor(handCursor);
    btnMenu.setBorderPainted(false);
    btnMenu.setFocusPainted(false);
    pnlMenu.add(btnMenu);
    pnlMenu.add(lblQuanLyNV);
    pnlMainUI.add(pnlMenu);
    btnMenu.addActionListener(this);

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

      } else if (e.getSource() == btnHopDong) {
        new QuanLyHopDong(taiKhoan, null).setVisible(true);
        this.dispose();
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
      if (e.getSource() == btnRefresh) {
        clearForm();
        loaData();
      } else if (e.getSource() == btnThemNV) {
        String tenNV = txtTenNV.getText();
        String sdt = txtSdt.getText();
        if (txtNgaySinh.getDate() == null) {
          JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày sinh", "Thông báo",
              JOptionPane.ERROR_MESSAGE);
          return;
        } else if (txtNgayTuyenDung.getDate() == null) {
          JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày tuyển dụng", "Thông báo",
              JOptionPane.ERROR_MESSAGE);
          return;
        }
        LocalDate ngaySinh = txtNgaySinh.getDate();
        String diaChi = txtDiaChi.getText();
        String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
        LocalDate ngayTuyenDung = txtNgayTuyenDung.getDate();
        String viTriCongViec = cmbViTriCongViec.getSelectedItem().toString();
        String loaiNv = viTriCongViec.equals("Quản lý") ? "QL" : "NV";
        String maNV = generator.tuTaoMaNV(loaiNv, Integer.parseInt(ngaySinh.toString().split("-")[0]));

        String email = txtEmail.getText();

        NhanVien nv = new NhanVien(maNV, tenNV, sdt, ngaySinh, gioiTinh.equals("Nam") ? true : false, ngayTuyenDung,
            viTriCongViec,
            email, diaChi, "lam");
        int year = Integer.parseInt(ngaySinh.toString().split("-")[0]);

        try {
          if (ngayTuyenDung.isAfter(LocalDate.now())) {
            JOptionPane.showMessageDialog(null, "Ngày tuyển dụng phải nhỏ hơn ngày hiện tại", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            return;
          }
          int age = LocalDate.now().getYear() - year;
          if (age < 18) {
            JOptionPane.showMessageDialog(null, "Tuổi nhân viên phải lớn hơn 18", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            return;
          }
          if (validator(sdt, viTriCongViec, tenNV, diaChi, email)) {
            TaiKhoan tk = new TaiKhoan(tenNV, "1111", true, nv);
            if (taiKhoanBus.themTaiKhoan(tk)) {
              JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công");
              clearForm();
              loaData();
            } else {
              JOptionPane.showMessageDialog(null, "Thêm nhân viên thất bại");
            }
          }
        } catch (RemoteException e1) {
          e1.printStackTrace();
        }
      } else if (e.getSource() == btnRefresh) {
        clearForm();
      } else if (e.getSource() == btnSuaNV) {
        String tenNV = txtTenNV.getText();
        String sdt = txtSdt.getText();
        LocalDate ngaySinh = txtNgaySinh.getDate();
        String diaChi = txtDiaChi.getText();
        String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
        LocalDate ngayTuyenDung = txtNgayTuyenDung.getDate();
        String viTriCongViec = cmbViTriCongViec.getSelectedItem().toString();

        String email = txtEmail.getText();

        try {
          if (validator(sdt, viTriCongViec, tenNV, diaChi, email)) {
            NhanVien nv = new NhanVien(maNV, tenNV, sdt, ngaySinh, gioiTinh.equals("Nam") ? true : false, ngayTuyenDung,
                viTriCongViec,
                email, diaChi, "lam");
            if (nhanVienBus.suaNhanVien(nv)) {
              JOptionPane.showMessageDialog(null, "Sửa nhân viên thành công");
              clearForm();
              loaData();
            } else {
              JOptionPane.showMessageDialog(null, "Sửa nhân viên thất bại");
            }
          }
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      } else if (e.getSource() == btnXoaNV) {

        String tenNV = txtTenNV.getText();
        String sdt = txtSdt.getText();
        LocalDate ngaySinh = txtNgaySinh.getDate();
        String diaChi = txtDiaChi.getText();
        String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
        LocalDate ngayTuyenDung = txtNgayTuyenDung.getDate();
        String viTriCongViec = cmbViTriCongViec.getSelectedItem().toString();

        String email = generator.taoEmail(tenNV, maNV);

        NhanVien nv = new NhanVien(maNV, tenNV, sdt, ngaySinh, gioiTinh.equals("Nam") ? true : false, ngayTuyenDung,
            viTriCongViec,
            email, diaChi, "nghi");
        try {
          if (nhanVienBus.suaNhanVien(nv)) {
            JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công");
            clearForm();
            loaData();
          } else {
            JOptionPane.showMessageDialog(null, "Xóa nhân viên thất bại");
          }
        } catch (Exception e2) {
          e2.printStackTrace();
        }
      } else if (e.getSource() == btnTimkiemNV) {
        if (txtTimkiemNV.getText().equals("")) {
          JOptionPane.showMessageDialog(null, "Vui lòng nhập thông tin tìm kiếm");
        } else {
          loaData();
          String tenCanTim = txtTimkiemNV.getText();
          Boolean kq = false;
          for (int i = 0; i < tblNhanVien.getRowCount(); i++) {
            Boolean result = false;
            String tenNV = tblNhanVien.getValueAt(i, 1).toString();
            String[] arrName = tenNV.split(" ");
            for (int a = 0; a < arrName.length; a++) {
              if (arrName[a].equals(tenCanTim)) {
                result = true;
                kq = true;
              } else if (arrName[a].toLowerCase().equals(tenCanTim)) {
                result = true;
                kq = true;
              } else if (generator.boDauTrongTu(arrName[a]).equals(tenCanTim)) {
                result = true;
                kq = true;
              } else if (generator.boDauTrongTu(arrName[a]).toLowerCase().equals(tenCanTim)) {
                result = true;
                kq = true;
              }
            }
            if (tenNV.equals(tenCanTim)) {
              result = true;
              kq = true;
            }
            if (result == false) {
              tblModelNV.removeRow(i);
              i--;
            }
          }
          if (kq == false) {
            loaData();
            JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên ");
          } else if (kq == true) {
            JOptionPane.showMessageDialog(null, "Tìm thấy nhân viên ");
            txtTimkiemNV.setText("");
          }
        }
      } else if (e.getSource() == btnFilter) {
        String gioiTinhFilter = cmbGioiTinhFilter.getSelectedItem().toString();
        String viTriFilter = cmbViTriFilter.getSelectedItem().toString();
        loaData();
        if (gioiTinhFilter != "" && viTriFilter != "") {
          for (int i = 0; i < tblNhanVien.getRowCount(); i++) {
            flag = false;
            if (tblNhanVien.getValueAt(i, 4).toString().equals(gioiTinhFilter)
                && tblNhanVien.getValueAt(i, 6).toString().equals(viTriFilter)) {
              flag = true;
            }
            if (flag == false) {
              tblModelNV.removeRow(i);
              i--;
            }
          }
        } else if (gioiTinhFilter != "") {
          for (int i = 0; i < tblNhanVien.getRowCount(); i++) {
            if (tblNhanVien.getValueAt(i, 4).equals(gioiTinhFilter)) {
            } else {
              tblModelNV.removeRow(i);
              i--;
            }
          }
        } else if (viTriFilter != "") {
          for (int i = 0; i < tblNhanVien.getRowCount(); i++) {
            if (tblNhanVien.getValueAt(i, 6).equals(viTriFilter)) {
            } else {
              tblModelNV.removeRow(i);
              i--;
            }
          }
        } else if (gioiTinhFilter == "" && viTriFilter == "") {
          loaData();
        } else if (e.getSource() == btnIconLogout) {
          if (taiKhoanBus.logout(taiKhoan)) {
            JOptionPane.showMessageDialog(null, "Đăng xuất thành công");
            new DangNhap().setVisible(true);
            this.dispose();
          }
        }
      }
    } catch (MalformedURLException | RemoteException | NotBoundException e3) {
      e3.printStackTrace();
    }
  }

  public void clearForm() {
    txtTenNV.setText("");
    txtSdt.setText("");
    txtNgaySinh.setDate(null);
    txtDiaChi.setText("");
    cmbGioiTinh.setSelectedIndex(0);
    txtNgayTuyenDung.setDate(null);
    cmbViTriCongViec.setSelectedIndex(0);
    txtEmail.setText("");
  }

  public void loaData() {
    try {
      List<NhanVien> listNhanVien = nhanVienBus.getAllNhanVien();
      tblModelNV.setRowCount(0);
      for (NhanVien nv : listNhanVien) {
        if (nv.getTrangThai().equals("lam")) {
          if (!nv.getMaNV().equals("1111"))
            tblModelNV.addRow(new Object[] { nv.getMaNV(), nv.getTenNV(), nv.getSdt(),
                generator.parseLocalDateToDate(nv.getNgaySinh().toString()), nv.getGioiTinh() == true ? "Nam" : "Nữ",
                generator.parseLocalDateToDate(nv.getNgayTuyenDung().toString()),
                nv.getViTriCongViec(), nv.getDiaChi(), nv.getEmail() });
        }
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  public boolean validator(String sdt, String viTri, String ten, String diaChi, String email)
      throws HeadlessException, RemoteException {
    for (int i = 0; i <= 10; i++) {
      if (nhanVienBus.validator(sdt, viTri, ten, diaChi, email) == i) {
        switch (i) {
          case 1:
            JOptionPane.showMessageDialog(null, "Không được để trống họ tên", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtTenNV.requestFocus();
            return false;
          case 2:
            JOptionPane.showMessageDialog(null, "Không được để trống số điện thoại", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtSdt.requestFocus();
            return false;
          case 3:
            JOptionPane.showMessageDialog(null, "Chọn vị trí công việc", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            return false;
          case 4:
            JOptionPane.showMessageDialog(null, "Không được để trống địa chỉ", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtDiaChi.requestFocus();
            return false;
          case 5:
            JOptionPane.showMessageDialog(null, "Email không được để trống", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
          case 6:
            JOptionPane.showMessageDialog(null, "Tên không được chứa các ký tự đặc biệt", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtTenNV.requestFocus();
            return false;
          case 7:
            JOptionPane.showMessageDialog(null, "Số điện thoại không được chứa các ký tự đặc biệt và phải đủ 10 số",
                "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtSdt.requestFocus();
            return false;
          case 8:
            JOptionPane.showMessageDialog(null, "Địa chỉ không được chứa các ký tự đặc biệt", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtDiaChi.requestFocus();
            return false;
          case 9:
            JOptionPane.showMessageDialog(null, "Email không hợp lệ", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
          case 0:
            return true;
        }
      }
    }
    return false;
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {

  }

  @Override
  public void mouseEntered(MouseEvent arg0) {

  }

  @Override
  public void mouseExited(MouseEvent arg0) {

  }

  @Override
  public void mousePressed(MouseEvent arg0) {

  }

  @Override
  public void mouseReleased(MouseEvent arg0) {

  }

}