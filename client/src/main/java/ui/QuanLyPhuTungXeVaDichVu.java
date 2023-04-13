package ui;

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

import bus.IDichVuBus;
import entities.DichVu;
import entities.PhuTung;
import entities.TaiKhoan;
import util.Constants;
import util.Generator;
import util.MyButton;
import util.RoundJTextField;
import util.RoundedBorderWithColor;

public class QuanLyPhuTungXeVaDichVu extends JFrame implements ActionListener {
  private JLabel lblIconLogo, lblTitle, lblLoai;
  private JLayeredPane pnlMainThongTin;

  private JPanel pnlMenu, pnlTop, pnInfo, pnlCenter, pnlMainUI, pnlThongTinPhuTung, pnlThongTinDichVu;
  private JLabel lblQuanLyPhuTung;
  private JButton btnMenu, btnIconUser, btnIconLogout;

  private Cursor handCursor;
  private JComboBox<String> cmbChonChucNang;
  private DefaultComboBoxModel<String> dfLoai;

  // slide bar
  private JPanel pnlSlideBar, pnlMainSlider;
  private JButton btnNhanVien, btnHopDong, btnKhachHang, btnQuanLyXe, btnPhuTung, btnTroGiup, btnClose;
  private JLabel lblMenu;
  private JLayeredPane layeredPane;
  private boolean flag = false;

  // phụ tùng
  private JPanel pnlPhuTung, pnlBtnPhuTung;
  private JLabel lblTitlePhuTung, lblTenPhuTung, lblSoLuong,
      lblDonGia;
  private JTextField txtTimPhuTung, txtTenPhuTung, txtSoLuong, txtDonGia;
  private MyButton btnThem, btnSua, btnXoa, btnTimPhuTung;
  private JTable tablePhuTung;
  private DefaultTableModel tableModelPhuTung;

  // dịch vụ
  private JPanel pnlDichVu, pnlBtnDichVu;
  private JLabel lblTitleDichVu, lblTenDichVu, lblDonGiaDichVu;
  private JTextField txtTimDichVu, txtTenDichVu, txtDonGiaDichVu;
  private MyButton btnTimDichVu;
  private JTable tableDichVu;
  private DefaultTableModel tableModelDichVu;

  private Generator generator = new Generator();
  IDichVuBus dichVuBus = (IDichVuBus) Naming.lookup("rmi://localhost:8080/dichVu");
  private String maDV = "";
  private String maPT = "";
  private TaiKhoan taiKhoan = new TaiKhoan();
  URL url = new URL(Constants.ICON_LOGO);
  Image imageIcon = new ImageIcon(url).getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);

  public QuanLyPhuTungXeVaDichVu(TaiKhoan taiKhoan) throws MalformedURLException, RemoteException, NotBoundException {
    setTitle(Constants.APP_NAME);
    this.taiKhoan = taiKhoan;
    setExtendedState(MAXIMIZED_BOTH);
    setMinimumSize(new Dimension(1500, 800));
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
    btnSua.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0),
        "btnSua");
    btnSua.getActionMap().put("btnSua", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnSua.doClick();
      }
    });
    btnXoa.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0),
        "btnXoa");
    btnXoa.getActionMap().put("btnXoa", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnXoa.doClick();
      }
    });
    btnTimPhuTung.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
        "btnTimPhuTung");
    btnTimPhuTung.getActionMap().put("btnTimPhuTung", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnTimPhuTung.doClick();
      }
    });
    btnTimDichVu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
        "btnTimDichVu");
    btnTimDichVu.getActionMap().put("btnTimDichVu", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnTimDichVu.doClick();
      }
    });

  }

  private void MainUI() {
    pnlMainUI = new JPanel();
    pnlMainUI.setLayout(null);
    pnlMainUI.setBounds(0, 0, 2000, 1400);
    pnlTop();
    pnlMenu();
    PnlCenter();
    layeredPane.add(pnlMainUI, Integer.valueOf(1));

  }

  void PnlCenter() {
    pnlCenter = new JPanel();
    pnlCenter.setBounds(0, 150, 1800, 900);
    pnlCenter.setLayout(null);
    pnlCenter.setBackground(Color.decode("#E3FDFD"));

    // pnlChucNang = new JPanel();
    // pnlChucNang.setBounds(0, 0, 1800, 150);
    // pnlChucNang.setLayout(null);
    // pnlChucNang.setBackground(Color.decode("#E3FDFD"));

    // pnlCenter.add(pnlChucNang);
    PnlPhuTung();
    PnlDichVu();
    uiThongTin();

    pnlMainUI.add(pnlCenter);

  }

  private void uiThongTin() {
    pnlMainThongTin = new JLayeredPane();
    pnlMainThongTin.setBounds(0, 0, 1800, 750);
    pnlMainThongTin.setLayout(null);
    pnlMainThongTin.setBackground(Color.decode("#E3FDFD"));

    lblLoai = new JLabel("Chọn chức năng:");
    lblLoai.setBounds(40, 22, 150, 50);
    lblLoai.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlMainThongTin.add(lblLoai);
    dfLoai = new DefaultComboBoxModel<String>();
    dfLoai.addElement("Phụ tùng");
    dfLoai.addElement("Dịch vụ");

    cmbChonChucNang = new JComboBox<String>(dfLoai);
    cmbChonChucNang.setBounds(200, 25, 150, 40);
    cmbChonChucNang.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    cmbChonChucNang.setBackground(Color.WHITE);
    pnlMainThongTin.add(cmbChonChucNang);
    cmbChonChucNang.addActionListener(this);
    uiThongTinPhuTung();

    btnThem = new MyButton(20, Color.decode(Constants.BTN_CONFIRM_COLOR), Color.decode(Constants.THIRD_COLOR));
    btnThem.setBounds(1100, 30, 150, 35);
    btnThem.setText("Thêm(F1)");
    btnThem.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    FontIcon add = FontIcon.of(FontAwesomeSolid.PLUS);
    add.setIconSize(25);
    btnThem.setIcon(add);
    btnThem.setBorderPainted(false);
    btnThem.setFocusPainted(false);
    btnThem.setCursor(handCursor);
    pnlMainThongTin.add(btnThem);
    btnThem.addActionListener(this);
    pnlCenter.add(pnlMainThongTin, 1);

    btnSua = new MyButton(20, Color.decode(Constants.BTN_EDIT_COLOR), Color.decode(Constants.THIRD_COLOR));
    btnSua.setBounds(1100, 80, 150, 35);
    btnSua.setText("Sửa(F2)");
    btnSua.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    FontIcon update = FontIcon.of(FontAwesomeSolid.SAVE);
    update.setIconSize(25);
    btnSua.setIcon(update);
    btnSua.setBorderPainted(false);
    btnSua.setFocusPainted(false);
    btnSua.setCursor(handCursor);
    pnlMainThongTin.add(btnSua);
    btnSua.addActionListener(this);

    btnXoa = new MyButton(20, Color.WHITE, Color.WHITE);
    btnXoa.setBounds(1100, 130, 150, 35);
    btnXoa.setText("Xóa(F3)");
    btnXoa.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    FontIcon delete = FontIcon.of(FontAwesomeSolid.TRASH_ALT, 20, Color.RED);
    btnXoa.setIcon(delete);
    btnXoa.setFocusPainted(false);
    btnXoa.setBorderPainted(false);
    btnXoa.setCursor(handCursor);
    pnlMainThongTin.add(btnXoa);
    btnXoa.addActionListener(this);

  }

  private void uiThongTinPhuTung() {
    pnlThongTinPhuTung = new JPanel();
    pnlThongTinPhuTung.setBounds(380, 20, 700, 120);
    pnlThongTinPhuTung.setLayout(null);
    pnlThongTinPhuTung.setBackground(Color.decode("#E3FDFD"));
    pnlMainThongTin.add(pnlThongTinPhuTung, 1);

    lblTenPhuTung = new JLabel("Tên phụ tùng:");
    lblTenPhuTung.setBounds(0, 0, 150, 50);
    lblTenPhuTung.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinPhuTung.add(lblTenPhuTung);
    txtTenPhuTung = new RoundJTextField(15);
    txtTenPhuTung.setBounds(120, 5, 300, 35);
    txtTenPhuTung.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 15));
    txtTenPhuTung.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinPhuTung.add(txtTenPhuTung);

    lblDonGia = new JLabel("Giá phụ tùng:");
    lblDonGia.setBounds(0, 50, 150, 50);
    lblDonGia.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinPhuTung.add(lblDonGia);
    txtDonGia = new RoundJTextField(15);
    txtDonGia.setBounds(120, 55, 300, 35);
    txtDonGia.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 15));
    txtDonGia.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinPhuTung.add(txtDonGia);

    lblSoLuong = new JLabel("Số lượng:");
    lblSoLuong.setBounds(450, 0, 150, 50);
    lblSoLuong.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinPhuTung.add(lblSoLuong);
    txtSoLuong = new RoundJTextField(15);
    txtSoLuong.setBounds(550, 10, 100, 35);
    txtSoLuong.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 15));
    txtSoLuong.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinPhuTung.add(txtSoLuong);
  }

  private void uiThongTinDichVu() {
    pnlThongTinDichVu = new JPanel();
    pnlThongTinDichVu.setBounds(380, 20, 700, 120);
    pnlThongTinDichVu.setLayout(null);
    pnlThongTinDichVu.setBackground(Color.decode("#E3FDFD"));
    pnlMainThongTin.add(pnlThongTinDichVu, 1);

    lblTenDichVu = new JLabel("Tên dịch vụ:");
    lblTenDichVu.setBounds(0, 0, 150, 50);
    lblTenDichVu.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinDichVu.add(lblTenDichVu);
    txtTenDichVu = new RoundJTextField(15);
    txtTenDichVu.setBounds(120, 5, 300, 35);
    txtTenDichVu.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 15));
    txtTenDichVu.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinDichVu.add(txtTenDichVu);

    lblDonGiaDichVu = new JLabel("Giá dịch vụ:");
    lblDonGiaDichVu.setBounds(0, 50, 150, 50);
    lblDonGiaDichVu.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinDichVu.add(lblDonGiaDichVu);
    txtDonGiaDichVu = new RoundJTextField(15);
    txtDonGiaDichVu.setBounds(120, 55, 300, 35);
    txtDonGiaDichVu.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 15));
    txtDonGiaDichVu.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 18));
    pnlThongTinDichVu.add(txtDonGiaDichVu);
  }

  void PnlDichVu() {
    pnlDichVu = new JPanel();
    pnlDichVu.setBounds(750, 120, 800, 800);
    pnlDichVu.setLayout(null);
    pnlDichVu.setBackground(Color.decode("#E3FDFD"));

    lblTitleDichVu = new JLabel("Dịch vụ");
    lblTitleDichVu.setBounds(20, 20, 260, 50);
    // lblTitleDichVu.setForeground(Color.decode("#DC5E78"));
    lblTitleDichVu.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 30));
    pnlDichVu.add(lblTitleDichVu);

    String[] header = { "Mã dịch vụ", "Tên dịch vụ", "Giá dịch vụ" };
    tableModelDichVu = new DefaultTableModel(header, 0);
    tableDichVu = new JTable(tableModelDichVu);
    tableDichVu.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 15));
    tableDichVu.getTableHeader().setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 15));
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    tableDichVu.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
    tableDichVu.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
    tableDichVu.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

    tableDichVu.setRowHeight(40);

    JScrollPane scrollPaneDichVu = new JScrollPane(tableDichVu);
    scrollPaneDichVu.setBounds(20, 140, 740, 340);
    pnlDichVu.add(scrollPaneDichVu);
    tableDichVu.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

        // pnlMainThongTin.remove(pnlThongTinPhuTung);
        uiThongTinDichVu();
        pnlMainThongTin.add(pnlThongTinDichVu, 1);
        dfLoai.setSelectedItem("Dịch vụ");
        loadDataPhuTung();
        int row = tableDichVu.getSelectedRow();
        maDV = tableDichVu.getValueAt(row, 0).toString();
        txtTenDichVu.setText(tableDichVu.getValueAt(row, 1).toString());
        txtDonGiaDichVu.setText(tableDichVu.getValueAt(row, 2).toString());

      }

      @Override
      public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

      }

    });

    txtTimDichVu = new RoundJTextField(15);
    txtTimDichVu.setBounds(20, 80, 500, 35);
    txtTimDichVu.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    txtTimDichVu.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 20));
    pnlDichVu.add(txtTimDichVu);

    btnTimDichVu = new MyButton(15, Color.decode(Constants.BTN_FILTER_COLOR), Color.decode(Constants.BTN_FILTER_COLOR));
    btnTimDichVu.setBounds(530, 80, 100, 35);
    FontIcon search = FontIcon.of(FontAwesomeSolid.SEARCH, 20, Color.BLACK);
    btnTimDichVu.setIcon(search);
    btnTimDichVu.setFocusable(false);
    btnTimDichVu.setBorderPainted(false);
    btnTimDichVu.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 15));
    btnTimDichVu.setCursor(handCursor);
    pnlDichVu.add(btnTimDichVu);

    pnlBtnDichVu = new JPanel();
    pnlBtnDichVu.setBounds(20, 500, 800, 50);
    pnlBtnDichVu.setLayout(null);
    pnlBtnDichVu.setBackground(Color.decode("#E3FDFD"));
    pnlDichVu.add(pnlBtnDichVu);
    loadDataDichVu();
    pnlCenter.add(pnlDichVu);
    btnTimDichVu.addActionListener(this);

  }

  void PnlPhuTung() {
    pnlPhuTung = new JPanel();
    pnlPhuTung.setBounds(0, 120, 750, 800);
    pnlPhuTung.setLayout(null);
    pnlPhuTung.setBackground(Color.decode("#E3FDFD"));

    txtTimPhuTung = new RoundJTextField(15);
    txtTimPhuTung.setBounds(20, 80, 500, 35);
    txtTimPhuTung.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    txtTimPhuTung.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 20));
    pnlPhuTung.add(txtTimPhuTung);

    btnTimPhuTung = new MyButton(15, Color.decode(Constants.BTN_FILTER_COLOR),
        Color.decode(Constants.BTN_FILTER_COLOR));
    btnTimPhuTung.setFocusable(false);
    btnTimPhuTung.setBorderPainted(false);
    btnTimPhuTung.setBounds(530, 80, 100, 35);
    FontIcon search = FontIcon.of(FontAwesomeSolid.SEARCH, 20, Color.BLACK);
    btnTimPhuTung.setIcon(search);
    btnTimPhuTung.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 15));
    btnTimPhuTung.setCursor(handCursor);
    pnlPhuTung.add(btnTimPhuTung);

    lblTitlePhuTung = new JLabel("Phụ tùng");
    lblTitlePhuTung.setBounds(20, 20, 260, 50);
    lblTitlePhuTung.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 30));
    pnlPhuTung.add(lblTitlePhuTung);

    String[] header = { "Mã phụ tùng", "Tên phụ tùng", "Giá phụ tùng", "Số lượng" };
    tableModelPhuTung = new DefaultTableModel(header, 0);
    tablePhuTung = new JTable(tableModelPhuTung);
    tablePhuTung.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 15));
    tablePhuTung.getTableHeader().setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 15));
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    tablePhuTung.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
    tablePhuTung.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
    tablePhuTung.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
    tablePhuTung.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
    tablePhuTung.setRowHeight(40);

    JScrollPane scrollPanePhuTung = new JScrollPane(tablePhuTung);
    scrollPanePhuTung.setBounds(20, 140, 730, 340);
    tablePhuTung.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent e) {
        // pnlMainThongTin.remove(pnlThongTinDichVu);
        uiThongTinPhuTung();
        pnlMainThongTin.add(pnlThongTinPhuTung, 1);
        dfLoai.setSelectedItem("Phụ tùng");
        loadDataDichVu();
        int row = tablePhuTung.getSelectedRow();
        maPT = tablePhuTung.getValueAt(row, 0).toString();
        String tenPT = tablePhuTung.getValueAt(row, 1).toString();
        String giaPT = tablePhuTung.getValueAt(row, 2).toString();
        String soLuong = tablePhuTung.getValueAt(row, 3).toString();
        txtTenPhuTung.setText(tenPT);
        txtDonGia.setText(giaPT);
        txtSoLuong.setText(soLuong);
      }

      @Override
      public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

      }

    });
    pnlPhuTung.add(scrollPanePhuTung);

    pnlBtnPhuTung = new JPanel();
    pnlBtnPhuTung.setBounds(20, 500, 730, 50);
    pnlBtnPhuTung.setLayout(null);
    pnlBtnPhuTung.setBackground(Color.decode("#E3FDFD"));
    pnlPhuTung.add(pnlBtnPhuTung);
    loadDataPhuTung();
    pnlCenter.add(pnlPhuTung);
    btnTimPhuTung.addActionListener(this);

  }

  void pnlTop() {
    pnlTop = new JPanel();
    pnlTop.setBounds(0, 0, 2000, 100);

    pnlTop.setBackground(Color.decode("#A6E3E9"));
    pnlTop.setLayout(null);

    lblIconLogo = new JLabel();
    lblIconLogo.setBounds(50, -15, 300, 150);
    lblIconLogo.setIcon(new ImageIcon(imageIcon));

    lblTitle = new JLabel(Constants.TITLE);
    lblTitle.setBounds(570, 0, 750, 100);
    lblTitle.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 70));
    lblTitle.setForeground(Color.decode("#112D4E"));

    pnInfo = new JPanel();
    pnInfo.setBounds(1300, 0, 250, 100);
    pnInfo.setBackground(Color.decode("#A6E3E9"));
    pnInfo.setLayout(null);

    btnIconUser = new JButton("Đổi tên chỗ");
    btnIconUser.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 16));
    btnIconUser.setBounds(0, 0, 250, 50);
    btnIconUser.setBorder(null);
    btnIconUser.setBackground(Color.decode("#A6E3E9"));
    FontIcon user = FontIcon.of(FontAwesomeSolid.USER_CIRCLE, 30, Color.decode("#112D4E"));
    btnIconUser.setIcon(user);
    btnIconUser.setBorderPainted(false);
    btnIconUser.setFocusPainted(false);
    // add btnIconLogout to pnInfo
    btnIconLogout = new JButton("Đăng xuất");
    btnIconLogout.setBounds(30, 50, 200, 50);
    btnIconLogout.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 16));
    btnIconLogout.setBorder(null);
    btnIconLogout.setBackground(Color.decode("#A6E3E9"));
    FontIcon logout = FontIcon.of(FontAwesomeSolid.SIGN_OUT_ALT, 30, Color.RED);
    btnIconLogout.setIcon(logout);
    btnIconLogout.setBorderPainted(false);
    btnIconLogout.setFocusPainted(false);

    pnInfo.add(btnIconUser);
    pnInfo.add(btnIconLogout);

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
    lblQuanLyPhuTung = new JLabel("Quản lý phụ tùng và dịch vụ");
    lblQuanLyPhuTung.setBounds(450, 0, 800, 60);
    lblQuanLyPhuTung.setBackground(Color.WHITE);
    lblQuanLyPhuTung.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 40));
    lblQuanLyPhuTung.setForeground(Color.WHITE);
    btnMenu = new JButton();
    btnMenu.setCursor(handCursor);
    btnMenu.setBorder(null);
    btnMenu.setFocusPainted(false);
    btnMenu.setBounds(0, 0, 60, 60);
    btnMenu.setBackground(Color.decode("#71C9CE"));
    btnMenu.setIcon(FontIcon.of(FontAwesomeSolid.BARS, 40));
    pnlMenu.add(btnMenu);
    pnlMenu.add(lblQuanLyPhuTung);
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
                e.printStackTrace();

              }
            }

          }).start();
          flag = true;
        }
      } else if (e.getSource() == btnNhanVien) {
        new QLNhanVien(taiKhoan).setVisible(true);
        this.dispose();
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
      } else if (e.getSource() == btnTroGiup) {
        try {
          Desktop.getDesktop().browse(new URL("https://phantan-nhom28.netlify.app/").toURI());
        } catch (Exception e2) {
          e2.printStackTrace();
        }
      }
      if (e.getSource() == cmbChonChucNang) {
        String loaiChucNang = cmbChonChucNang.getSelectedItem().toString();
        if (loaiChucNang.equals("Phụ tùng")) {
          pnlMainThongTin.remove(pnlThongTinDichVu);
          uiThongTinPhuTung();
          pnlMainThongTin.add(pnlThongTinPhuTung, 1);
        } else {
          pnlMainThongTin.remove(pnlThongTinPhuTung);
          uiThongTinDichVu();
          pnlMainThongTin.add(pnlThongTinDichVu, 1);
        }
        cmbChonChucNang.setFocusable(false);

      } else if (e.getSource() == btnThem) {
        String loaiChucNang = cmbChonChucNang.getSelectedItem().toString();
        if (loaiChucNang.equals("Phụ tùng")) {
          String tenPhuTung = txtTenPhuTung.getText();
          String maPT = generator.tuTaoMaPT();

          if (validatorPhuTung(tenPhuTung, txtDonGia.getText(), txtSoLuong.getText())) {
            double gia = Double.parseDouble(txtDonGia.getText());
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            PhuTung pt = new PhuTung(maPT, tenPhuTung, gia, soLuong);

            if (dichVuBus.themPhuTung(pt)) {
              JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
              clearFormPhuTung();
              loadDataPhuTung();
            } else {
              JOptionPane.showMessageDialog(null, "Thêm thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
          }

        } else {
          String tenDV = txtTenDichVu.getText();
          String maDV = generator.taoMaDichVu();

          if (validatorDichVu(tenDV, txtDonGiaDichVu.getText())) {
            double giaDV = Double.parseDouble(txtDonGiaDichVu.getText());
            DichVu dv = new DichVu(maDV, tenDV, giaDV);
            if (dichVuBus.themDichVu(dv)) {
              JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
              clearFormDichVu();
              loadDataDichVu();
            } else {
              JOptionPane.showMessageDialog(null, "Thêm thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
          }

        }
      } else if (e.getSource() == btnSua) {
        String loaiChucNang = cmbChonChucNang.getSelectedItem().toString();
        if (loaiChucNang.equals("Phụ tùng")) {
          String tenPhuTung = txtTenPhuTung.getText();
          if (validatorPhuTung(tenPhuTung, txtDonGia.getText(), txtSoLuong.getText())) {
            double gia = Double.parseDouble(txtDonGia.getText());
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            PhuTung pt = new PhuTung(maPT, tenPhuTung, gia, soLuong);
            if (dichVuBus.suaPhuTung(pt)) {
              JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
              clearFormPhuTung();
              loadDataPhuTung();
            } else {
              JOptionPane.showMessageDialog(null, "Sửa thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
          }

        } else {
          String tenDV = txtTenDichVu.getText();
          if (validatorDichVu(tenDV, txtDonGiaDichVu.getText())) {
            double giaDV = Double.parseDouble(txtDonGiaDichVu.getText());
            DichVu dv = new DichVu(maDV, tenDV, giaDV);
            if (dichVuBus.suaDichVu(dv)) {
              JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
              clearFormDichVu();
              loadDataDichVu();
            } else {
              JOptionPane.showMessageDialog(null, "Sửa thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
          }
        }
      } else if (e.getSource() == btnXoa) {
        String loaiChucNang = cmbChonChucNang.getSelectedItem().toString();
        if (loaiChucNang.equals("Phụ tùng")) {
          if (dichVuBus.xoaPhuTung(maPT)) {
            JOptionPane.showMessageDialog(null, "Xóa thành công");
            clearFormPhuTung();
            loadDataPhuTung();
          }
        } else {
          if (dichVuBus.xoaDichVu(maDV)) {
            JOptionPane.showMessageDialog(null, "Xóa thành công");
            clearFormDichVu();
            loadDataDichVu();
          }
        }
      } else if (e.getSource() == btnTimPhuTung) {
        if (txtTimPhuTung.getText().equals("")) {

          JOptionPane.showMessageDialog(null, "Vui lòng nhập tên phụ tùng");
        } else {
          loadDataPhuTung();
          String tenCanTim = txtTimPhuTung.getText();
          Boolean kq = false;
          for (int i = 0; i < tablePhuTung.getRowCount(); i++) {
            Boolean result = false;
            String tenPT = tablePhuTung.getValueAt(i, 1).toString();
            String[] arrName = tenPT.split(" ");
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
            if (tenPT.equals(tenCanTim)) {
              result = true;
              kq = true;
            }
            if (result == false) {
              tableModelPhuTung.removeRow(i);
              i--;
            }
          }
          if (kq == false) {
            loadDataPhuTung();
            JOptionPane.showMessageDialog(null, "Không tìm thấy phụ tùng ");
          } else if (kq == true) {
            JOptionPane.showMessageDialog(null, "Tìm thấy phụ tùng ");
            txtTimPhuTung.setText("");
          }
        }
      } else if (e.getSource() == btnTimDichVu) {
        if (txtTimDichVu.getText().equals("")) {

          JOptionPane.showMessageDialog(null, "Vui lòng nhập tên dịch vụ");
        } else {
          loadDataPhuTung();
          String tenCanTim = txtTimDichVu.getText();
          Boolean kq = false;
          for (int i = 0; i < tableDichVu.getRowCount(); i++) {
            Boolean result = false;
            String tenDV = tableDichVu.getValueAt(i, 1).toString();
            String[] arrName = tenDV.split(" ");
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
            if (tenDV.equals(tenCanTim)) {
              result = true;
              kq = true;
            }
            if (result == false) {
              tableModelDichVu.removeRow(i);
              i--;
            }
          }
          if (kq == false) {
            loadDataDichVu();
            JOptionPane.showMessageDialog(null, "Không tìm thấy dịch vụ ");
          } else if (kq == true) {
            JOptionPane.showMessageDialog(null, "Tìm thấy dịch vụ");
            txtTimDichVu.setText("");
          }
        }
      }
    } catch (Exception e3) {
      e3.printStackTrace();
    }
  }

  private void clearFormPhuTung() {
    txtTenPhuTung.setText("");
    txtDonGia.setText("");
    txtSoLuong.setText("");
  }

  private void clearFormDichVu() {
    txtTenDichVu.setText("");
    txtDonGiaDichVu.setText("");
  }

  private boolean validatorDichVu(String tenDV, String donGia) throws HeadlessException, RemoteException {
    for (int i = 0; i <= 10; i++) {
      if (dichVuBus.validatorDichVu(tenDV, donGia) == i) {
        switch (i) {
          case 1:
            JOptionPane.showMessageDialog(null, "Tên dịch vụ không được để trống", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            return false;
          case 2:
            JOptionPane.showMessageDialog(null, "Tên dịch vụ không chứa các ký tự đặc biệt", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            return false;
          case 3:
            JOptionPane.showMessageDialog(null, "Giá dịch vụ không được để trống", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            return false;
          case 4:
            JOptionPane.showMessageDialog(null, "Giá dịch vụ phải là số", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
          case 0:
            return true;
        }
      }
    }
    return false;
  }

  private boolean validatorPhuTung(String tenPT, String giaPT, String soLuong)
      throws HeadlessException, RemoteException {
    for (int i = 0; i <= 10; i++) {
      if (dichVuBus.validatorPhuTung(tenPT, giaPT, soLuong) == i) {
        switch (i) {
          case 1:
            JOptionPane.showMessageDialog(null, "Tên phụ tùng không được để trống", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            return false;
          case 2:
            JOptionPane.showMessageDialog(null, "Tên phụ tùng không chứa các ký tự đặc biệt", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            return false;
          case 3:
            JOptionPane.showMessageDialog(null, "Giá phụ tùng không được để trống", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            return false;
          case 4:
            JOptionPane.showMessageDialog(null, "Giá phụ tùng phải là số", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
          case 5:
            JOptionPane.showMessageDialog(null, "Số lượng không được để trống", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
          case 6:
            JOptionPane.showMessageDialog(null, "Số lượng phải là số và lớn hơn 0", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            return false;
          case 0:

            return true;
        }
      }
    }
    return false;
  }

  private void loadDataDichVu() {
    tableModelDichVu.setRowCount(0);
    try {
      List<DichVu> list = dichVuBus.getAllDichVu();
      for (DichVu dichVu : list) {
        tableModelDichVu.addRow(new Object[] { dichVu.getMaDichVu(), dichVu.getTenDichVu(), dichVu.getDonGia() });
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  private void loadDataPhuTung() {
    tableModelPhuTung.setRowCount(0);
    try {
      List<PhuTung> list = dichVuBus.getAllPhuTung();
      for (PhuTung phuTung : list) {
        tableModelPhuTung.addRow(new Object[] { phuTung.getMaPhuTung(), phuTung.getTenPhuTung(),
            phuTung.getGiaPhuTung(), phuTung.getSoLuong() });
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

}