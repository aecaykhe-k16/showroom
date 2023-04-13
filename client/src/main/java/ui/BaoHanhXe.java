package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import bus.IDichVuBus;
import bus.IHoaDonBus;
import entities.ChiTietDichVu;
import entities.ChiTietHoaDonBaoHanh;
import entities.DichVu;
import entities.HoaDonBaoHanh;
import entities.HopDong;
import entities.PhuTung;
import util.ButtonRenderer;
import util.Constants;
import util.Generator;
import util.JPictureBox;
import util.MyButton;

public class BaoHanhXe extends JFrame implements ActionListener, MouseListener {
  private JLabel lblTitle, lblBaoHanh, lblDichVu;

  private JPanel pnlBottom, pnlCenter, pnlMainUI;
  private JButton btnThanhToan;

  JLayeredPane layeredPane;
  private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);;

  private DefaultTableModel modelBaoHanh, modelDichVu;
  private JTable tblBaoHanh, tblDichVu;
  private JComboBox<String> cmbBaoHanhPhuTung, cmbDichVu, cmbSLBaoHanh;
  private DefaultComboBoxModel<String> dfBaoHanhPhuTung, dfDichVu, dfSLBaoHanhPhutTung;
  private JButton btnThemBaoHanhPhuTung, btnThemDichVu, btnThoat;
  private HopDong hopDong;

  IDichVuBus dichVuBus = (IDichVuBus) Naming.lookup("rmi://localhost:8080/dichVu");
  IHoaDonBus hoaDonBus = (IHoaDonBus) Naming.lookup("rmi://localhost:8080/hoaDon");
  List<DichVu> listDichVu = dichVuBus.getAllDichVu();
  List<PhuTung> listPhuTung = dichVuBus.getAllPhuTung();
  List<ChiTietDichVu> dsDV = new ArrayList<ChiTietDichVu>();
  List<ChiTietHoaDonBaoHanh> dsPT = new ArrayList<ChiTietHoaDonBaoHanh>();
  private JButton btnIn, btnDe;
  private String tenPhuTungCu = "";
  private int tongSoLuong = 0;
  private Generator generator = new Generator();
  URL url = new URL(Constants.ICON_LOGO);
  Image imageIcon = new ImageIcon(url).getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);

  public BaoHanhXe(HopDong hopDong)
      throws MalformedURLException, RemoteException, NotBoundException {
    this.hopDong = hopDong;
    setTitle(Constants.APP_NAME);
    setSize(1400, 660);
    this.setIconImage(imageIcon);

    setLocationRelativeTo(null);
    this.setBackground(Color.WHITE);
    setResizable(false);
    setLayout(null);
    layeredPane = new JLayeredPane();
    layeredPane.setBounds(0, 0, 1400, 660);
    add(layeredPane);
    MainUI();
    btnThoat.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
        "btnThoat");
    btnThoat.getActionMap().put("btnThoat", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnThoat.doClick();
      }
    });

  }

  private void MainUI() {
    pnlMainUI = new JPanel();
    pnlMainUI.setLayout(null);
    pnlMainUI.setBounds(0, 0, 1400, 660);
    pnlMainUI.setBackground(Color.WHITE);
    lblTitle = new JLabel(hopDong.getKhachHang().getTenKH());
    lblTitle.setFont(new Font("Arial", Font.BOLD, 30));
    lblTitle.setBounds(20, 20, 1400, 30);
    btnThoat = new JButton("");
    btnThoat.setBounds(1300, 20, 50, 50);
    btnThoat.setBorderPainted(false);
    btnThoat.setContentAreaFilled(false);
    btnThoat.setFocusPainted(false);
    btnThoat.setOpaque(false);
    pnlMainUI.add(btnThoat);
    lblTitle.setHorizontalAlignment(JLabel.CENTER);
    pnlMainUI.add(lblTitle);
    btnThoat.addActionListener(this);
    PnlCenter();
    PnlBottom();
    layeredPane.add(pnlMainUI, Integer.valueOf(1));

  }

  class ButtonIn extends DefaultCellEditor {
    private String label;

    public ButtonIn(JCheckBox checkBox) {
      super(checkBox);
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {
      label = (value == null) ? "" : value.toString();
      btnIn.setText(label);
      btnIn.setBorderPainted(false);
      btnIn.setFocusPainted(false);
      btnIn.setContentAreaFilled(false);
      btnIn.setOpaque(false);
      FontIcon icon = FontIcon.of(FontAwesomeSolid.PLUS, 15, Color.BLACK);
      btnIn.setIcon(icon);
      btnIn.setBackground(Color.WHITE);

      return btnIn;
    }

    public Object getCellEditorValue() {
      return new String(label);
    }
  }

  class ButtonDe extends DefaultCellEditor {
    private String label;

    public ButtonDe(JCheckBox checkBox) {
      super(checkBox);
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {
      label = (value == null) ? "" : value.toString();
      btnDe.setText(label);
      btnDe.setBorderPainted(false);
      btnDe.setFocusPainted(false);
      btnDe.setContentAreaFilled(false);
      btnDe.setOpaque(false);
      FontIcon icon = FontIcon.of(FontAwesomeSolid.MINUS, 15, Color.BLACK);
      btnDe.setIcon(icon);
      btnDe.setBackground(Color.WHITE);
      return btnDe;
    }

    public Object getCellEditorValue() {
      return new String(label);
    }
  }

  void PnlCenter() {
    pnlCenter = new JPanel();
    pnlCenter.setBounds(0, 50, 1400, 500);
    pnlCenter.setLayout(null);
    pnlCenter.setBackground(Color.WHITE);
    lblBaoHanh = new JLabel("Bảo hành xe");
    lblBaoHanh.setBounds(50, 10, 200, 50);
    lblBaoHanh.setFont(new Font("Arial", Font.BOLD, 20));
    pnlCenter.add(lblBaoHanh);

    String[] headerBaoHanh = { "STT", "Tên dịch vụ", "Số lượng", "Đơn giá",
        "Thành tiền", "Tăng số lượng", "Giảm số lượng", "ma" };
    modelBaoHanh = new DefaultTableModel(headerBaoHanh, 0);
    tblBaoHanh = new JTable(modelBaoHanh);
    tblBaoHanh.setDefaultEditor(Object.class, null);

    tblBaoHanh.setRowHeight(30);
    tblBaoHanh.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
    tblBaoHanh.getTableHeader().setBackground(Color.WHITE);
    tblBaoHanh.setBackground(Color.WHITE);
    tblBaoHanh.setFont(new Font("Arial", Font.PLAIN, 15));
    JScrollPane scrollBaoHanh = new JScrollPane(tblBaoHanh);
    scrollBaoHanh.setBounds(50, 60, 800, 200);
    tblBaoHanh.getColumnModel().getColumn(7).setMinWidth(0);
    tblBaoHanh.getColumnModel().getColumn(7).setMaxWidth(0);
    tblBaoHanh.getColumnModel().getColumn(7).setWidth(0);
    pnlCenter.add(scrollBaoHanh);

    lblDichVu = new JLabel("Dịch vụ");
    lblDichVu.setBounds(50, 250, 200, 50);
    lblDichVu.setFont(new Font("Arial", Font.BOLD, 20));
    pnlCenter.add(lblDichVu);

    String[] headerDichVu = { "STT", "Tên dịch vụ", "Đơn giá", "ma" };
    modelDichVu = new DefaultTableModel(headerDichVu, 0);
    tblDichVu = new JTable(modelDichVu);
    tblDichVu.getColumnModel().getColumn(3).setMinWidth(0);
    tblDichVu.getColumnModel().getColumn(3).setMaxWidth(0);
    tblDichVu.getColumnModel().getColumn(3).setWidth(0);
    tblDichVu.setDefaultEditor(Object.class, null);
    tblDichVu.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
    tblDichVu.getTableHeader().setBackground(Color.WHITE);
    tblDichVu.setBackground(Color.WHITE);
    tblDichVu.setRowHeight(30);
    tblDichVu.setFont(new Font("Arial", Font.PLAIN, 15));
    JScrollPane scrollDichVu = new JScrollPane(tblDichVu);
    scrollDichVu.setBounds(50, 300, 800, 200);
    pnlCenter.add(scrollDichVu);

    pnlMainUI.add(pnlCenter);

    JPictureBox anhXe = new JPictureBox();
    anhXe.setBounds(880, 100, 480, 270);
    anhXe.setIcon(new ImageIcon(hopDong.getXe().getHinhAnh()));
    pnlCenter.add(anhXe);

    btnThanhToan = new MyButton(20, Color.green, Color.green);
    btnThanhToan.setText("Lập hóa đơn");
    btnThanhToan.setBorderPainted(false);
    btnThanhToan.setFocusPainted(false);
    btnThanhToan.setBounds(940, 450, 400, 50);
    btnThanhToan.setForeground(Color.WHITE);
    btnThanhToan.setFont(new Font("Arial", Font.BOLD, 20));
    btnThanhToan.setCursor(handCursor);
    btnThanhToan.addActionListener(this);
    pnlCenter.add(btnThanhToan);
    tblBaoHanh.addMouseListener(this);

  }

  void PnlBottom() {
    pnlBottom = new JPanel();
    pnlBottom.setBounds(0, 550, 1800, 100);
    pnlBottom.setLayout(null);
    pnlBottom.setBackground(Color.WHITE);
    pnlMainUI.add(pnlBottom);

    dfBaoHanhPhuTung = new DefaultComboBoxModel<String>();
    dfBaoHanhPhuTung.addElement("Chọn phụ tùng");
    try {
      for (PhuTung pt : dichVuBus.getAllPhuTung()) {
        dfBaoHanhPhuTung.addElement(pt.getTenPhuTung());
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    }
    cmbBaoHanhPhuTung = new JComboBox<String>(dfBaoHanhPhuTung);
    cmbBaoHanhPhuTung.setBounds(50, 20, 200, 30);
    cmbBaoHanhPhuTung.setFont(new Font("Arial", Font.PLAIN, 17));
    pnlBottom.add(cmbBaoHanhPhuTung);

    dfSLBaoHanhPhutTung = new DefaultComboBoxModel<String>();

    cmbSLBaoHanh = new JComboBox<String>(dfSLBaoHanhPhutTung);

    cmbSLBaoHanh.setBounds(270, 20, 60, 30);
    cmbSLBaoHanh.setFont(new Font("Arial", Font.PLAIN, 17));
    pnlBottom.add(cmbSLBaoHanh);

    btnThemBaoHanhPhuTung = new MyButton(15, Color.GREEN, Color.GREEN);
    btnThemBaoHanhPhuTung.setText("Thêm");
    btnThemBaoHanhPhuTung.setBounds(350, 20, 100, 30);
    btnThemBaoHanhPhuTung.setBorderPainted(false);
    btnThemBaoHanhPhuTung.setFocusPainted(false);
    btnThemBaoHanhPhuTung.setCursor(handCursor);
    btnThemBaoHanhPhuTung.setFont(new Font("Arial", Font.PLAIN, 17));
    btnThemBaoHanhPhuTung.setForeground(Color.WHITE);
    btnThemBaoHanhPhuTung.setCursor(handCursor);
    pnlBottom.add(btnThemBaoHanhPhuTung);

    dfDichVu = new DefaultComboBoxModel<String>();
    dfDichVu.addElement("Chọn dịch vụ");
    try {
      for (DichVu dv : dichVuBus.getAllDichVu()) {
        dfDichVu.addElement(dv.getTenDichVu());
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    }
    cmbDichVu = new JComboBox<String>(dfDichVu);

    cmbDichVu.setBounds(530, 20, 200, 30);
    cmbDichVu.setFont(new Font("Arial", Font.PLAIN, 17));
    pnlBottom.add(cmbDichVu);

    btnThemDichVu = new MyButton(15, Color.GREEN, Color.GREEN);
    btnThemDichVu.setText("Thêm");
    btnThemDichVu.setBounds(750, 20, 100, 30);
    btnThemDichVu.setBorderPainted(false);
    btnThemDichVu.setFocusPainted(false);
    btnThemDichVu.setCursor(handCursor);
    btnThemDichVu.setFont(new Font("Arial", Font.PLAIN, 17));
    btnThemDichVu.setForeground(Color.WHITE);
    btnThemDichVu.setCursor(handCursor);
    pnlBottom.add(btnThemDichVu);
    cmbBaoHanhPhuTung.addActionListener(this);
    btnThemBaoHanhPhuTung.addActionListener(this);
    btnThemDichVu.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    FontIcon in = FontIcon.of(FontAwesomeSolid.PLUS, 15, Color.BLACK);
    FontIcon de = FontIcon.of(FontAwesomeSolid.MINUS, 15, Color.BLACK);
    btnIn = new JButton();
    tblBaoHanh.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer(in));
    tblBaoHanh.getColumnModel().getColumn(5).setCellEditor(new ButtonIn(new JCheckBox()));
    btnIn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int row = tblBaoHanh.getSelectedRow();
        int soLuong = (Integer) tblBaoHanh.getValueAt(row, 2);
        if (tongSoLuong < soLuong) {
          JOptionPane.showMessageDialog(null, "Số lượng phụ tùng không đủ");
        } else {
          tblBaoHanh.setValueAt((Integer) tblBaoHanh.getValueAt(row, 2) + 1, row, 2);
        }
        if (tongSoLuong == (Integer) tblBaoHanh.getValueAt(row, 2)) {
          btnIn.setEnabled(false);
        } else {
          tblBaoHanh.setValueAt((Integer) tblBaoHanh.getValueAt(row, 2) * (Double) tblBaoHanh.getValueAt(row, 3), row,
              4);
        }
      }
    });

    btnDe = new JButton();

    tblBaoHanh.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer(de));
    tblBaoHanh.getColumnModel().getColumn(6).setCellEditor(new ButtonDe(new JCheckBox()));
    btnDe.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int row = tblBaoHanh.getSelectedRow();

        tblBaoHanh.setValueAt((Integer) tblBaoHanh.getValueAt(row, 2) - 1, row, 2);

        if ((Integer) tblBaoHanh.getValueAt(row, 2) < tongSoLuong && (Integer) tblBaoHanh.getValueAt(row, 2) != 0) {
          btnIn.setEnabled(true);
          btnDe.setEnabled(true);
        }
        if ((Integer) tblBaoHanh.getValueAt(row, 2) == 0) {
          modelBaoHanh.removeRow(row);
          for (int i = 0; i < tblBaoHanh.getRowCount(); i++) {
            tblBaoHanh.setValueAt((Integer) tblBaoHanh.getValueAt(row, 0) - 1, i, 0);
          }
          dfBaoHanhPhuTung.addElement(tenPhuTungCu);
        } else {
          tblBaoHanh.setValueAt((Integer) tblBaoHanh.getValueAt(row, 2) * (Double) tblBaoHanh.getValueAt(row, 3), row,
              4);
        }
      }
    });
    if (e.getSource() == cmbBaoHanhPhuTung) {
      if (cmbBaoHanhPhuTung.getSelectedIndex() > 0) {
        String tenPhuTung = cmbBaoHanhPhuTung.getSelectedItem().toString();
        for (PhuTung pt : listPhuTung) {
          if (pt.getTenPhuTung().equals(tenPhuTung)) {
            dfSLBaoHanhPhutTung.removeAllElements();
            for (int i = 0; i < pt.getSoLuong(); i++) {
              dfSLBaoHanhPhutTung.addElement(i + 1 + "");
            }
            break;
          }
        }

      }
    } else if (e.getSource() == btnThemBaoHanhPhuTung) {
      String tenPhuTung = cmbBaoHanhPhuTung.getSelectedItem().toString();
      if (tenPhuTung.equals("Chọn phụ tùng")) {
        JOptionPane.showMessageDialog(null, "Chưa chọn phụ tùng", "Thông báo", JOptionPane.WARNING_MESSAGE);
      } else {
        if (cmbSLBaoHanh.getSelectedIndex() > 0) {

          int soLuong = Integer.parseInt(cmbSLBaoHanh.getSelectedItem().toString());
          for (PhuTung pt : listPhuTung) {
            if (pt.getTenPhuTung().equals(tenPhuTung)) {
              Object[] row = { tblBaoHanh.getRowCount() + 1, pt.getTenPhuTung(),
                  soLuong, pt.getGiaPhuTung(), soLuong * pt.getGiaPhuTung(), btnIn.getText(), btnDe.getText(),
                  pt.getMaPhuTung() };
              modelBaoHanh.addRow(row);
              tongSoLuong = Integer.parseInt(cmbSLBaoHanh.getItemAt(cmbSLBaoHanh.getItemCount() - 1));
              tenPhuTungCu = tenPhuTung;
              dfBaoHanhPhuTung.removeElement(tenPhuTung);
              dfSLBaoHanhPhutTung.removeAllElements();
            }

          }
        }

      }

    } else if (e.getSource() == btnThemDichVu) {
      String tenDichVu = cmbDichVu.getSelectedItem().toString();
      if (tenDichVu.equals("Chọn dịch vụ")) {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn dịch vụ", "Thông báo", JOptionPane.WARNING_MESSAGE);
      } else
        for (DichVu dv : listDichVu) {
          if (tenDichVu.equals(dv.getTenDichVu())) {
            modelDichVu.addRow(new Object[] { tblDichVu.getRowCount() + 1, dv.getTenDichVu(), dv.getDonGia(),
                dv.getMaDichVu() });
            dfDichVu.removeElement(tenDichVu);
            break;
          }
        }
    } else if (e.getSource() == btnThanhToan) {
      for (int i = 0; i < tblDichVu.getRowCount(); i++) {
        DichVu dv = new DichVu();
        dv.setTenDichVu(tblDichVu.getValueAt(i, 1).toString());
        dv.setDonGia((Double) tblDichVu.getValueAt(i, 2));
        dv.setMaDichVu(tblDichVu.getValueAt(i, 3).toString());
        ChiTietDichVu ctdv = new ChiTietDichVu(dv, 1);
        dsDV.add(ctdv);
      }
      for (int i = 0; i < tblBaoHanh.getRowCount(); i++) {
        PhuTung pt = new PhuTung();
        pt.setTenPhuTung(tblBaoHanh.getValueAt(i, 1).toString());
        pt.setGiaPhuTung((Double) tblBaoHanh.getValueAt(i, 3));
        pt.setMaPhuTung(tblBaoHanh.getValueAt(i, 7).toString());
        ChiTietHoaDonBaoHanh cthdbh = new ChiTietHoaDonBaoHanh(pt, (Integer) tblBaoHanh.getValueAt(i, 2));
        dsPT.add(cthdbh);
      }
      String[][] info = new String[1][2];
      info[0][0] = "Tên khách hàng";
      info[0][1] = "Nguyễn Văn A";

      exportFilePDF(info);
    } else if (e.getSource() == btnThoat) {
      this.dispose();
    }

  }

  public void exportFilePDF(String[][] info) {

    String defaultCurrentDirectoryPath = "G:\\";

    JFileChooser chooser = new JFileChooser(defaultCurrentDirectoryPath);
    FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF File", "pdf");
    chooser.setFileFilter(filter);
    chooser.setDialogTitle("Save as");
    chooser.setAcceptAllFileFilterUsed(false);

    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      Document document = new Document();
      document.setPageSize(PageSize.A5);
      document.setMargins(0, 0, 0, 0);
      com.itextpdf.text.Font fontPDF = null;
      com.itextpdf.text.Font fontBoldColor = null;
      com.itextpdf.text.Font fontBold = null;
      com.itextpdf.text.Font fontPDFSmall = null;
      com.itextpdf.text.Font fontPDFSmallItalic = null;
      try {

        FileOutputStream file = new FileOutputStream(chooser.getSelectedFile() + ".pdf");
        fontPDF = FontFactory.getFont("C:\\Windows\\Fonts\\vuArial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 13);
        fontPDFSmall = FontFactory.getFont("C:\\Windows\\Fonts\\vuArial.ttf", BaseFont.IDENTITY_H,
            BaseFont.EMBEDDED,
            10);
        fontPDFSmallItalic = FontFactory.getFont("C:\\Windows\\Fonts\\vuArialItalic.ttf", BaseFont.IDENTITY_H,
            BaseFont.EMBEDDED,
            10);
        fontBold = FontFactory.getFont("C:\\Windows\\Fonts\\vuArialBold.ttf", BaseFont.IDENTITY_H,
            BaseFont.EMBEDDED,
            15);
        fontBoldColor = FontFactory.getFont("C:\\Windows\\Fonts\\vuArialBold.ttf",
            20, 0, BaseColor.RED);
        PdfWriter.getInstance(document, file);

        document.open();
        Paragraph tieuDe = new Paragraph(Constants.APP_NAME, fontBoldColor);
        tieuDe.setAlignment(Element.ALIGN_CENTER);
        tieuDe.setSpacingAfter(20);
        document.add(tieuDe);

        Chunk chunkDiaChi = new Chunk("Địa chỉ: 123 Nguyễn Văn Linh, Quận 7, Tp.HCM",
            FontFactory.getFont("C:\\Windows\\Fonts\\vuArialItalic.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 15));
        chunkDiaChi.setTextRise(10);
        chunkDiaChi.setUnderline(0.1f, -(0.1f));
        chunkDiaChi.setAnchor("https://github.com/phan-tan/showRoom");
        Paragraph diaChi = new Paragraph(chunkDiaChi);
        diaChi.setAlignment(Element.ALIGN_CENTER);
        diaChi.setSpacingAfter(5);
        document.add(diaChi);

        Paragraph hoaDon = new Paragraph(
            generator.tuTaoMaHoaDon(hopDong.getMaHopDong()) + " | "
                + new SimpleDateFormat("dd/MM/yyyy").format(new Date()),
            fontPDFSmallItalic);
        hoaDon.setIndentationLeft(220);
        document.add(hoaDon);

        Paragraph nv = new Paragraph(hopDong.getNhanVien().getTenNV(), fontPDFSmallItalic);
        nv.setIndentationLeft(295);
        document.add(nv);

        Paragraph titleHD = new Paragraph("Hóa đơn bảo hành", fontBold);
        titleHD.setAlignment(Element.ALIGN_CENTER);
        titleHD.setSpacingAfter(3);
        document.add(titleHD);

        PdfPTable tableInfo = new PdfPTable(2);
        tableInfo.setWidths(new int[] { 3, 2 });

        tableInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableInfo.setWidthPercentage(80);

        for (int i = 0; i < 1; i++) {
          for (int j = 0; j < 2; j++) {
            PdfPCell cell = new PdfPCell(new Paragraph(info[i][j], fontPDF));
            cell.setFixedHeight(20);
            cell.setBorder(Rectangle.NO_BORDER);
            tableInfo.addCell(cell);
          }
        }
        tableInfo.setSpacingAfter(10);
        document.add(tableInfo);

        Paragraph titlePT = new Paragraph("Phụ tùng", fontPDF);
        titlePT.setAlignment(Element.ALIGN_LEFT);
        titlePT.setSpacingAfter(5);
        titlePT.setIndentationLeft(20);
        if (tblBaoHanh.getRowCount() > 0) {
          document.add(titlePT);
        }

        PdfPTable tablePT = new PdfPTable(tblBaoHanh.getColumnCount() - 3);
        tablePT.setWidthPercentage(90); // set tablePT width to 90%

        for (int i = 0; i < tblBaoHanh.getColumnCount() - 3; i++) {
          PdfPCell cell = new PdfPCell(new Paragraph(tblBaoHanh.getColumnName(i), fontPDFSmall));
          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
          tablePT.addCell(cell);
        }
        for (int rows = 0; rows < tblBaoHanh.getRowCount(); rows++) {
          for (int cols = 0; cols < tblBaoHanh.getColumnCount() - 3; cols++) {
            PdfPCell cell = new PdfPCell(new Paragraph(tblBaoHanh.getModel().getValueAt(rows, cols).toString(),
                fontPDFSmall));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablePT.addCell(cell);
          }
        }
        tablePT.setSpacingAfter(10);
        if (tblBaoHanh.getRowCount() > 0) {
          document.add(tablePT);
        }

        Paragraph titleDv = new Paragraph("Dịch vụ", fontPDF);
        titleDv.setAlignment(Element.ALIGN_LEFT);
        titleDv.setSpacingAfter(5);
        titleDv.setIndentationLeft(20);
        if (tblDichVu.getRowCount() > 0) {
          document.add(titleDv);
        }

        PdfPTable tableDV = new PdfPTable(tblDichVu.getColumnCount() - 1);
        tableDV.setWidthPercentage(90); // set tableDV width to 90%

        for (int i = 0; i < tblDichVu.getColumnCount() - 1; i++) {
          PdfPCell cell = new PdfPCell(new Paragraph(tblDichVu.getColumnName(i), fontPDFSmall));
          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
          tableDV.addCell(cell);
        }
        for (int rows = 0; rows < tblDichVu.getRowCount(); rows++) {
          for (int cols = 0; cols < tblDichVu.getColumnCount() - 1; cols++) {
            PdfPCell cell = new PdfPCell(new Paragraph(tblDichVu.getModel().getValueAt(rows, cols).toString(),
                fontPDFSmall));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableDV.addCell(cell);
          }
        }
        tableDV.setSpacingAfter(10);
        if (tblDichVu.getRowCount() > 0) {
          document.add(tableDV);
        }

        Paragraph underLine = new Paragraph("-------------------------------------------------------------------",
            fontPDFSmallItalic);
        underLine.setAlignment(Element.ALIGN_CENTER);
        underLine.setSpacingAfter(3);
        document.add(underLine);

        document.close();
        file.close();
        int xemFile = JOptionPane.showConfirmDialog(null, "Xuất file thành công. Bạn có muốn xem file không?",
            "Thông báo", JOptionPane.YES_NO_OPTION);
        for (PhuTung pt : listPhuTung) {
          for (int index = 0; index < tblBaoHanh.getRowCount(); index++) {
            pt.setSoLuong(pt.getSoLuong() - Integer.parseInt(tblBaoHanh.getValueAt(index, 2).toString()));
            if (tblBaoHanh.getValueAt(index, 7).equals(pt.getMaPhuTung())) {
              dichVuBus.suaPhuTung(pt);
            }
          }
        }
        HoaDonBaoHanh hoaDonBaoHanh = new HoaDonBaoHanh(
            generator.tuTaoMaHoaDon(hopDong.getMaHopDong()), LocalDate.now(), hopDong.getNhanVien(),
            hopDong.getKhachHang());
        hoaDonBaoHanh.setDsChiTietBH(dsPT);
        hoaDonBaoHanh.setDsChiTietDichVu(dsDV);
        hoaDonBus.themHoaDonBaoHanh(hoaDonBaoHanh);
        if (xemFile == JOptionPane.YES_OPTION) {
          this.dispose();
          try {
            Desktop.getDesktop().open(new File(chooser.getSelectedFile() + ".pdf"));
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Xuất file thất bại");
        e.printStackTrace();
      }

    }

  }

  @Override
  public void mouseClicked(MouseEvent e) {

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

  }

}