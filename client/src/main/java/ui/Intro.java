package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import util.Constants;
import util.RoundedBorderWithColor;

public class Intro {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.setVisible(true);
      }
    });
  }
}

class SplashScreen extends JWindow {
  static JProgressBar progressBar = new JProgressBar();
  static int count = 1, TIMER_PAUSE = 25, PROGBAR_MAX = 100;
  static Timer progressBarTimer;
  ActionListener al = new ActionListener() {
    @Override
    public void actionPerformed(java.awt.event.ActionEvent evt) {
      progressBar.setValue(count);
      if (PROGBAR_MAX == count) {
        progressBarTimer.stop();
        SplashScreen.this.setVisible(false);
        createAndShowFrame();
      }
      count++;
    }
  };

  public SplashScreen() {
    this.setSize(400, 300);
    this.setLocationRelativeTo(null);

    JPanel panel = new JPanel();
    add(panel, BorderLayout.NORTH);
    panel.setBackground(Color.WHITE);

    JLabel label = new JLabel(Constants.APP_NAME);

    label.setFont(new Font("Verdana", Font.BOLD, 35));
    panel.add(label);

    JPanel panel2 = new JPanel();
    panel2.setBackground(Color.WHITE);
    add(panel2, BorderLayout.CENTER);
    JLabel label3 = new JLabel();
    try {
      ImageIcon icon = new ImageIcon(new URL(
          "https://res.cloudinary.com/kuga/image/upload/v1669082344/blog/intro_gfl3qd.jpg"));
      label3.setIcon(new ImageIcon(icon.getImage().getScaledInstance(480, 270,
          Image.SCALE_SMOOTH)));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    panel2.add(label3);

    JPanel panel3 = new JPanel();
    panel3.setBackground(Color.WHITE);
    add(panel3, BorderLayout.SOUTH);
    progressBar.setMaximum(PROGBAR_MAX);
    progressBar.setBorder(new RoundedBorderWithColor(Color.WHITE, 1, 15));
    panel3.add(progressBar);
    JLabel label2 = new JLabel();
    label2.setFont(new Font("Verdana", Font.BOLD, 13));

    new Timer(10, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {

        label2.setText("Loading..." + count + "%");
      }
    }).start();
    panel3.add(label2);

    pack();
    setVisible(true);
    startProgressBar();
  }

  private void startProgressBar() {
    progressBarTimer = new Timer(TIMER_PAUSE, al);
    progressBarTimer.start();
  }

  private void createAndShowFrame() {
    try {
      new DangNhap().setVisible(true);
    } catch (MalformedURLException | RemoteException | NotBoundException e) {
      e.printStackTrace();
    }
  }
}