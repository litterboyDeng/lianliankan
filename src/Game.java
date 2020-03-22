import javax.swing.*;
import javax.swing.plaf.ProgressBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Game extends JFrame{
    private final int row = 6;//行数 final:常量
    private final int col = 10;//列数
    private int totalBlocks;//总的空格数
    private JButton[][] array;//每一个方块都是一个按钮，用二维数组来存，角标表示按钮的位置
    private int data[][];//每一个按钮对应位置的图片的标识符，用来确认是否能够相消
    private JPanel gamePanel;//游戏的主要panel,就是按钮所在的区域
    private JPanel menuPanel;//菜单的panel，刚进入游戏时的界面
    private JPanel mainPanel;//游戏界面，包含gamepanel,也包含时间，新游戏，返回等按钮
    private JProgressBar timeBar;//剩余时间进度条
    private int tipTimes;//提示次数
    private int refreshTimes;//刷新次数
    private int scores;//分数
    private JButton jButton1;//游戏时，第一次选中的图片
    private JButton jButton2;//第二次选中的按钮
    private JButton start;//游戏内新游戏按钮
    private JButton backmenu;//返回主菜单的按钮
    private JButton tip;//提示按钮
    private JButton newGame;//主菜单栏里面的新游戏
    private JButton refresh;//刷新按钮
    private JButton backFromRecord;//从记录页面返回到主菜单
    private JButton backFromHelp;//从帮助页面返回到主菜单
    private JLabel jLabel;//显示分数
    private JLabel backImage;//用来显示背景图片
    private JButton help; //主菜单上面的“帮助”按钮
    private JButton record;//主菜单上面的“游戏记录”按钮
    private Timer gameTime;//时间计时
    private int timeLeft;//剩余时间
    private boolean isOver;//标志游戏是否结束
    private ImageIcon imageIcon1;//游戏按钮的图片
    private ImageIcon imageIcon2;
    private ImageIcon imageIcon3;
    private ImageIcon imageIcon4;
    private ImageIcon imageIcon5;
    private ImageIcon imageIcon6;
    private ImageIcon imageIcon7;
    private ImageIcon imageIcon8;
    private ImageIcon imageIcon9;
    private ImageIcon imageIcon10;
    private ImageIcon backIcon;//背景图片
    private Music music;//音乐
    private Random random;//生成随机数，用来打乱图片
    private Graphics g;//画笔，用来画线
    private JTextArea recordText;//用来显示游戏记录或者帮助内容
    private JLabel rule;//只用来显示“游戏规则”四个字
    /**
     * 构造函数，变量的初始化
     */
    public Game() {
        array = new JButton[row][col];
        data = new int[row+2][col+2];
        gamePanel = new JPanel();
        menuPanel = new JPanel();
        mainPanel = new JPanel();
        timeBar = new JProgressBar(){
            @Override
            /**
             * 时间栏的样式重写
             */
            public void setUI(ProgressBarUI ui) {
                // TODO Auto-generated method stub
                super.setUI(new ProgressUI(this,Color.BLUE));//设置的进度条颜色
            }
        };

        for (int i = 0; i < row; i++)
            for (int j = 0; j <col ; j++) {
                array[i][j] = new JButton();
                //System.out.print(data[i][j]);
            }

        imageIcon1 = new ImageIcon("resource/pictures/1.jpg");
        imageIcon1.setImage(imageIcon1.getImage().getScaledInstance(
                52, 52,
                Image.SCALE_DEFAULT));
        imageIcon2 = new ImageIcon("resource/pictures/2.jpg");
        imageIcon2.setImage(imageIcon2.getImage().getScaledInstance(
                52, 52,
                Image.SCALE_DEFAULT));
        imageIcon3 = new ImageIcon("resource/pictures/3.jpg");
        imageIcon3.setImage(imageIcon3.getImage().getScaledInstance(
                52, 52,
                Image.SCALE_DEFAULT));
        imageIcon4 = new ImageIcon("resource/pictures/4.jpg");
        imageIcon4.setImage(imageIcon4.getImage().getScaledInstance(
                52, 52,
                Image.SCALE_DEFAULT));
        imageIcon5 = new ImageIcon("resource/pictures/5.jpg");
        imageIcon5.setImage(imageIcon5.getImage().getScaledInstance(
                52, 52,
                Image.SCALE_DEFAULT));
        imageIcon6 = new ImageIcon("resource/pictures/6.jpg");
        imageIcon6.setImage(imageIcon6.getImage().getScaledInstance(
                52, 52,
                Image.SCALE_DEFAULT));
        imageIcon7 = new ImageIcon("resource/pictures/7.jpg");
        imageIcon7.setImage(imageIcon7.getImage().getScaledInstance(
                52, 52,
                Image.SCALE_DEFAULT));
        imageIcon8 = new ImageIcon("resource/pictures/8.jpg");
        imageIcon8.setImage(imageIcon8.getImage().getScaledInstance(
                52, 52,
                Image.SCALE_DEFAULT));
        imageIcon9 = new ImageIcon("resource/pictures/9.jpg");
        imageIcon9.setImage(imageIcon9.getImage().getScaledInstance(
                52, 52,
                Image.SCALE_DEFAULT));
        imageIcon10 = new ImageIcon("resource/pictures/10.jpg");
        imageIcon10.setImage(imageIcon10.getImage().getScaledInstance(
                52, 52,
                Image.SCALE_DEFAULT));

        backIcon = new ImageIcon("resource/pictures/backGround.jpg");
        backIcon.setImage(backIcon.getImage().getScaledInstance(700,600,Image.SCALE_DEFAULT));

        random = new Random();
        music = new Music();
        recordText = new JTextArea();

    }


    /**
     * ui的布局设计
     */
    public void showUI() {
        setTitle("涛涛连连看");//标题
        setSize(700, 600);//大小
        setDefaultCloseOperation(EXIT_ON_CLOSE);//退出时关闭程序
        setLocationRelativeTo(null);//居中显示
        setResizable(false);//界面大小不可通过拖拽改变
        setLayout(null);//jFrame不设置布局方式

        Font font = new Font("微软雅黑",Font.BOLD,30);//字体
        ((JPanel)this.getContentPane()).setOpaque(false);//第三层
        backImage = new JLabel(backIcon);
        this.getLayeredPane().add(backImage,new Integer(Integer.MIN_VALUE));//把背景图片显示出来。第二层
        backImage.setBounds(0,0,700,600);//背景图片显示的大小及位置
        Container container = getContentPane();
        mainPanel.setOpaque(false);
        mainPanel.setBounds(0,0,700,600);
        mainPanel.setLayout(null);
        container.add(mainPanel);

        recordText.setBounds(150,100,400,350);
        recordText.setEnabled(false);//设置为不可编辑
        recordText.setOpaque(false);//设置为透明
        recordText.setFont(new Font("微软雅黑",Font.BOLD,21));
        recordText.setVisible(false);
        mainPanel.add(recordText);


        rule = new JLabel("游戏规则");
        rule.setBounds(300,50,200,50);
        rule.setFont(new Font("微软雅黑",Font.BOLD,30));
        rule.setVisible(false);
        mainPanel.add(rule);

        backFromRecord = new JButton("返回主菜单");
        backFromRecord.setFont(font);
        backFromRecord.setForeground(Color.YELLOW);
        backFromRecord.setContentAreaFilled(false);
        backFromRecord.setBounds(480,0,200,50);
        backFromRecord.setVisible(false);
        backFromRecord.addActionListener(new BackFromRecord());
        mainPanel.add(backFromRecord);

        backFromHelp = new JButton("返回主菜单");
        backFromHelp.setForeground(Color.YELLOW);
        backFromHelp.setFont(font);
        backFromHelp.setContentAreaFilled(false);
        backFromHelp.setBounds(480,0,200,50);
        backFromHelp.setVisible(false);
        backFromHelp.addActionListener(new BackFromHelp());
        mainPanel.add(backFromHelp);

        menuPanel.setBounds(0,0,700,600);
        menuPanel.setOpaque(false);
        menuPanel.setLayout(null);//设置为透明

        newGame = new JButton("开始游戏");
        newGame.setFont(font);
        newGame.setForeground(Color.YELLOW);
        newGame.addActionListener(new NewGame_Menu());
        newGame.setBounds(260,150,180,50);//?
        newGame.setContentAreaFilled(false);//按钮设置为透明
        menuPanel.add(newGame);

        record = new JButton("游戏记录");
        record.setFont(font);
        record.setForeground(Color.YELLOW);
        record.setBounds(260,250,180,50);
        record.addActionListener(new Record());
        record.setContentAreaFilled(false);
        menuPanel.add(record);

        help = new JButton("帮 助");
        help.setFont(font);
        help.setForeground(Color.YELLOW);
        help.setBounds(260,350,180,50);
        help.setContentAreaFilled(false);
        help.addActionListener(new Help());
        menuPanel.add(help);

        mainPanel.add(menuPanel);

        timeBar.setIndeterminate(false);//用整数显示，不是用百分数显示
        timeBar.setStringPainted(true);
        timeBar.setString("时间剩余");
        timeBar.setMaximum(60);
        timeBar.setMinimum(0);
        timeBar.setValue(60);
        timeBar.setBounds(40,10,600,20);
        timeBar.setOpaque(false);//时间栏透明
        mainPanel.add(timeBar);

        gamePanel.setBounds(40,70,60*col,60*row);
        gamePanel.setLayout(new GridLayout(row,col));//gamePanel采用网格布局
        //gamePanel.setOpaque(false);
        mainPanel.add(gamePanel);

        for (int i = 0;i < row; i++)
            for (int j = 0; j < col ; j++){
                array[i][j].addActionListener(new clickButton());//为每个button添加动作监听事件
                //array[i][j].setContentAreaFilled(false);
                gamePanel.add(array[i][j]);
            }

        jLabel = new JLabel();
        jLabel.setFont(font);
        jLabel.setForeground(Color.red);//字体颜色
        jLabel.setBounds(530,470,140,40);
        mainPanel.add(jLabel);

        tip = new JButton("提示:"+tipTimes);
        tip.setFont(font);
        //tip.setOpaque(false);
        tip.setContentAreaFilled(false);
        tip.setBorderPainted(false);//设置边界消失
        tip.setForeground(Color.RED);
        tip.addActionListener(new Tip());
        tip.setBounds(285, 470, 125, 40);
        mainPanel.add(tip);

        refresh = new JButton("刷新:"+refreshTimes);
        refresh.setFont(font);
        refresh.setBorderPainted(false);
        refresh.setForeground(Color.red);
        refresh.setBounds(410,470,125,40);
        refresh.addActionListener(new Change() );
        refresh.setContentAreaFilled(false);
        mainPanel.add(refresh);
        initTime();//初始化时间
        initData();//初始化游戏按钮的标识符data数组
        initGameButton();//根据data数组初始化按钮内容

        start = new JButton("新游戏");
        start.setFont(font);
        start.setContentAreaFilled(false);
        start.setBorderPainted(false);
        start.setForeground(Color.red);
        start.setBounds(30, 470, 125, 40);
        start.addActionListener(new NewGame());

        mainPanel.add(start);

        backmenu = new JButton("返 回");
        backmenu.setFont(font);
        backmenu.setOpaque(false);
        backmenu.setBorderPainted(false);
        backmenu.setForeground(Color.red);
        backmenu.setContentAreaFilled(false);
        backmenu.addActionListener(new Back());
        backmenu.setBounds(160, 470, 125, 40);
        mainPanel.add(backmenu);

        timeBar.setVisible(false);//false设置为不可见
        gamePanel.setVisible(false);
        start.setVisible(false);
        backmenu.setVisible(false);
        tip.setVisible(false);
        jLabel.setVisible(false);
        refresh.setVisible(false);
        recordText.setVisible(false);
        menuPanel.setVisible(true);//true可见

        getContentPane().add(mainPanel);
        mainPanel.setOpaque(false);

        setVisible(true);//主界面可见

        music.BackMusicLoop();//音乐循环播放
        //int i=JOptionPane.showConfirmDialog(this, "是否读取上次的存档",
          //      "读档", JOptionPane.YES_NO_OPTION);

    }

    /**
     * 初始化数据
     */
    private void initData(){
        totalBlocks = row * col;
        tipTimes = 3;
        jButton1 = null;
        jButton2 = null;
        timeLeft = 60;
        isOver = false;
        refreshTimes = 1;
        scores = 0;
        tip.setText("提示:"+tipTimes);
        jLabel.setText("分数:"+scores);
        refresh.setText("刷新:"+refreshTimes);
        timeBar.setValue(timeLeft);
        for (int i = 0; i < row; i++)
            for (int j = 0; j <col ; j++) {
                data[i+1][j+1] = (i*10+j)%10+1 ;//随机生成成对的数字，用来做标识符

                array[i][j].setEnabled(true);//设置能否点击
                array[i][j].setBackground(Color.WHITE);
                //System.out.println(data[i+1][j+1]);
            }//成对产生

        for (int i = 0; i < row+2; i++) {
            data[i][0] = 0;
            data[i][col+1] = 0;
        }
        for (int i = 0; i < col +2; i++) {
            data[0][i] = 0;
            data[row+1][i] = 0;
        }
        //用来打乱data数组，保证图片随机排列
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < row*col; k++) {
                int row1 = random.nextInt(row)+1;
                int col1 = random.nextInt(col)+1;
                int row2 = random.nextInt(row)+1;
                int col2 = random.nextInt(col)+1;
                // 交换这两个坐标处的数
                int temp = data[row1][col1];
                data[row1][col1] = data[row2][col2];
                data[row2][col2] = temp;
            }
        }
    }

    /**
     *初始化按钮内容
     */
    private void initGameButton(){
        //打乱坐标
        for (int i = 0;i < row; i++)
            for (int j = 0; j < col ; j++) {
                array[i][j].setIcon(null);
                if (data[i+1][j+1] == 0){
                    array[i][j].setEnabled(false);
                }else
                    array[i][j].setEnabled(true);
                switch (data[i+1][j+1]){
                    case 1:
                        array[i][j].setIcon(imageIcon1);
                        break;
                    case 2:
                        array[i][j].setIcon(imageIcon2);
                        break;
                    case 3:
                        array[i][j].setIcon(imageIcon3);
                        break;
                    case 4:
                        array[i][j].setIcon(imageIcon4);
                        break;
                    case 5:
                        array[i][j].setIcon(imageIcon5);
                        break;
                    case 6:
                        array[i][j].setIcon(imageIcon6);
                        break;
                    case 7:
                        array[i][j].setIcon(imageIcon7);
                        break;
                    case 8:
                        array[i][j].setIcon(imageIcon8);
                        break;
                    case 9:
                        array[i][j].setIcon(imageIcon9);
                        break;
                    case 10:
                        array[i][j].setIcon(imageIcon10);
                        break;
                }

            }

    }

    /**
     * 图片按钮的动作监听事件
     */
    class clickButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isOver){
                music.PushMusicStop();
                music.PusnMusicStart();
                JButton jButton = (JButton)e.getSource();
                if (jButton1 == null){
                    jButton1 = jButton;
                    jButton1.setBackground(Color.GREEN);
                }
                else if (jButton1 != jButton){
                    jButton2 = jButton;
                    jButton2.setBackground(Color.GREEN);

                    // 记录第一个按钮的行数
                    int row1 = (jButton1.getY()/jButton1.getHeight()) + 1;
                    // 记录第一个按钮的列数
                    int col1 = (jButton1.getX() / jButton1.getWidth()) + 1;
                    // 记录第二个按钮的行数
                    int row2 = (jButton2.getY() /jButton2.getHeight()) + 1;
                    // 记录第二个按钮的列数
                    int col2 = (jButton2.getX() /jButton2.getWidth()) + 1;
                    // 输出两个按钮的位置 以及 相应的data值
                    //System.out.println(row1 + " " + col1);
                    //System.out.println(row2 + " " + col2);
                    if (isConnect(data,row1,col1,row2,col2,false)){
                        //如果能相连
                        //jButton1.setVisible(false);
                        //jButton2.setVisible(false);
                        timeLeft +=2;
                        if (timeLeft>60){
                            timeLeft = 60;
                            timeBar.setValue(timeLeft);
                        }

                        try {
                            Robot r=new Robot();
                            r.delay(200);
                        }catch (AWTException es){
                            es.printStackTrace();
                        }
                        repaint();
                        jButton1.setBackground(Color.white);
                        jButton2.setBackground(Color.white);
                        jButton1.setEnabled(false);
                        jButton2.setEnabled(false);
                        jButton1.setIcon(null);//删除图片
                        jButton2.setIcon(null);
                        data[row1][col1] = 0;
                        data[row2][col2] = 0;
                        totalBlocks-=2;
                        scores+=10;
                        jLabel.setText("分数:"+scores);

                        if (totalBlocks == 0){
                            isOver = true;
                            gameTime.stop();
                            WriteData(scores);
                            Object[] options = { " 新游戏 ", " 结束游戏" };
                            int response = JOptionPane.showOptionDialog(null,
                                    "你还想获得更高分吗？", "继续游戏", JOptionPane.YES_OPTION,
                                    JOptionPane.QUESTION_MESSAGE, null, options,
                                    options[0]);
                            if (response == 0){
                                initData();
                                gameTime.start();
                                System.out.println(timeLeft);
                                System.out.println(totalBlocks);
                                initGameButton();
                                gameTime.start();
                            }else {
                                timeBar.setVisible(false);
                                gamePanel.setVisible(false);
                                start.setVisible(false);
                                backmenu.setVisible(false);
                                tip.setVisible(false);
                                jLabel.setVisible(false);
                                refresh.setVisible(false);
                                menuPanel.setVisible(true);
                                gameTime.stop();
                                music.BackMusicLoop();
                            }
                        }
                        //当无可消除的时候，提示刷新
                        if (!HasMatching()&&!isOver){
                            JOptionPane.showMessageDialog(null,"所有方块均无法消除",
                                    "提示",JOptionPane.INFORMATION_MESSAGE);
                            Refresh();
                        }
                        jButton1 = null;
                        jButton2 = null;
                    }else{
                        jButton1.setBackground(Color.white);
                        jButton1 = jButton2;
                        jButton2 = null;
                    }
                }else
                    return;
            }

        }
    }

    /**
     * 返回主菜单界面的动作监听
     */
    class Back implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            timeBar.setVisible(false);
            gamePanel.setVisible(false);
            start.setVisible(false);
            backmenu.setVisible(false);
            tip.setVisible(false);
            jLabel.setVisible(false);
            refresh.setVisible(false);
            menuPanel.setVisible(true);

            gameTime.stop();
            music.BackMusicLoop();
        }
    }

    /**
     * 从记录页面返回到主菜单的动作监听
     */

    class BackFromRecord implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            menuPanel.setVisible(true);
            recordText.setVisible(false);
            backFromRecord.setVisible(false);
        }
    }

    /**
     * 从帮助页面返回到主菜单的动作监听
     */
    class BackFromHelp implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            menuPanel.setVisible(true);
            recordText.setVisible(false);
            backFromHelp.setVisible(false);
            rule.setVisible(false);
        }
    }

    /**
     * 主菜单页面的开始游戏的动作监听
     */
    class NewGame_Menu implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            timeBar.setVisible(true);
            gamePanel.setVisible(true);
            start.setVisible(true);
            backmenu.setVisible(true);
            tip.setVisible(true);
            jLabel.setVisible(true);
            refresh.setVisible(true);
            menuPanel.setVisible(false);
            initData();
            initGameButton();
            music.BackMusicStop();
            gameTime.start();
        }
    }

    /**
     * 主菜单页面上的游戏记录按钮的动作监听
     */
    class Record implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            menuPanel.setVisible(false);
            recordText.setVisible(true);
            backFromRecord.setVisible(true);
            recordText.setText("");
            try {
                int number = 0;
                String[] record = new String[1000];
                BufferedReader bufferedReader = new BufferedReader(new FileReader("record.txt"));
                String data = null;
                while((data=bufferedReader.readLine())!=null) {
                    String[] r = data.split("#");
//                    recordText.setForeground(Color.YELLOW);
                    record[number] = "时间：" + r[1] + "  |   分数" + r[0]+"\n";
                    number++;
                }


                int d = 0;
                while (number>0){
                    recordText.append(record[number]);
                    number--;
                    d++;
                    if (d == 10)
                        break;
                }
            }catch (IOException e1){
                e1.printStackTrace();
            }

        }
    }

    /**
     * 主菜单页面上的帮助按钮的动作监听
     */
    class Help implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            menuPanel.setVisible(false);
            recordText.setVisible(true);
            backFromHelp.setVisible(true);
            rule.setVisible(true);
            recordText.setText("1.在游戏时间内，玩家点击两张相同的图片，" + "\n" +
                    "在规则允许的范围内消除方块" + "\n" +
                    "2.当剩余时间为0或者所有方块均消除时，" + "\n" +
                    "游戏结束" + "\n" +
                    "3.消除规则：" + "\n" +
                    "（1）直线相消：两张相同的图片之间没有" + "\n" +
                    "障碍时，便可以消除" + "\n" +
                    "（2）一拐角相消：两张图片之间可以用两" + "\n" +
                    "条直线相连，并且没有障碍，便可以消除" + "\n" +
                    "（3）二拐角相消：两张图片之间可以用两" + "\n" +
                    "条直线相连，并且没有障碍，便可以消除");

        }
    }

    /**
     * 提示的动作监听
     */
    class Tip implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (tipTimes == 0){
                return;
            }
            if (jButton1!=null)
                jButton1.setBackground(Color.WHITE);
            for (int i = 1;i <= row; i++)
                for (int j = 1; j <= col ; j++) {
                    if (data[i][j] == 0) continue;
                    for (int k = 1; k <= row; k++)
                        for (int l = 1; l <= col; l++) {
                            if (i==k&&j==l) continue;
                            if (isConnect(data,i,j,k,l,true)){
                                array[i-1][j-1].setBackground(Color.RED);
                                array[k-1][l-1].setBackground(Color.RED);
                                tipTimes--;
                                tip.setText("提示:"+tipTimes);
                                return;
                            }
                        }
                }
        }
    }

    private void initTime(){
        gameTime = new Timer(1000,new TimeLeft());
        gameTime.stop();
    }

    @Override
    /**
     * 画笔
     */
    public void paint(Graphics g) {
        super.paint(g);
    }

    /**
     * 时间栏的动作监听
     */
    class TimeLeft implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timeLeft--; // 时间自减1
            timeBar.setValue(timeLeft);
            if (timeLeft <= 0) {
                ((Timer) e.getSource()).stop();// 时间停止
                WriteData(scores);
                Object[] options = {" 确定 ", " 取消 "};
                int response = JOptionPane.showOptionDialog(null,
                        "时间到了，重新开始游戏吗？", "游戏结束 ", JOptionPane.YES_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options,
                        options[0]);

                if (response == 0){
                    initData();
                    System.out.println(timeLeft);
                    System.out.println(totalBlocks);
                    initGameButton();
                    gameTime.start();
                }else {
                    isOver = true;
                    timeBar.setVisible(false);
                    gamePanel.setVisible(false);
                    start.setVisible(false);
                    backmenu.setVisible(false);
                    tip.setVisible(false);
                    jLabel.setVisible(false);
                    refresh.setVisible(false);
                    menuPanel.setVisible(true);
                    gameTime.stop();
                    music.BackMusicLoop();
                }
            }

        }

    }

    /**
     * 游戏界面的新游戏按钮的动作监听
     */
    class NewGame implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            initData();
            System.out.println(timeLeft);
            System.out.println(totalBlocks);
            initGameButton();
            gameTime.start();
        }
    }

    /**
     * 刷新界面按钮的动作监听
     */
    class Change implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Refresh();
        }
    }

    private void Refresh(){
        if (jButton1!=null){
            jButton1.setBackground(Color.WHITE);
        }
        if (refreshTimes == 0)
            return;
        for (int k = 0; k < row*col; k++) {
            int row1 = random.nextInt(row)+1;
            int col1 = random.nextInt(col)+1;
            int row2 = random.nextInt(row)+1;
            int col2 = random.nextInt(col)+1;
            // 交换这两个坐标处的数
            int temp = data[row1][col1];
            data[row1][col1] = data[row2][col2];
            data[row2][col2] = temp;
        }
        refreshTimes--;
        refresh.setText("刷新:"+refreshTimes);
        initGameButton();
    }

    private boolean isConnect(int[][] data,int row1,int col1,int row2,int col2,boolean isCheck){
        //int index1 = (row1-1)*10 + col1; // 计算第一个按钮的索引
        //int index2 = (row2-1)*10 + col2;// 计算第二个按钮的索引
        if (data[row1][col1]!=data[row2][col2])
            return false;

        if(connect0(data,row1 ,col1,row2,col2,isCheck)){
            System.out.println("直线相连");
            return true;
        }

        // 一拐角相连
        if(connect1(data,row1 ,col1,row2,col2,isCheck)){
            System.out.println("一拐角相连");
            return true;
        }

        // 二拐角相连
        if(connect2(data,row1 ,col1,row2,col2,isCheck)){
            System.out.println("二拐角相连");
            return true;
        }

        return false;
    }

    /**
     * 一条线相连
     * @param data
     * @param row1
     * @param col1
     * @param row2
     * @param col2
     */
    private boolean connect0 (int[][] data, int row1, int col1, int row2, int col2,boolean isCheck){
        if(canArrived(data,row1,col1,row2,col2)){
            if (!isCheck){
//                线的端点的坐标
                int x1 = 60*col1 + 20;
                int y1 = 60*row1 + 70;
                int x2 = 60*col2 + 20;
                int y2 = 60*row2 + 70;
                g = this.getGraphics();

                Graphics2D g2 = (Graphics2D)g;  //g是Graphics对象
                g2.setStroke(new BasicStroke(3.0f));//设置线的粗细
                g2.setColor(Color.RED);//线的颜色
                g2.drawLine(x1,y1,x2,y2);//画线
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * 两条线相连
     * @param data
     * @param row1
     * @param col1
     * @param row2
     * @param col2
     * @return
     */
    private boolean connect1(int[][] data, int row1, int col1, int row2, int col2,boolean isCheck){
        if(canArrived(data,row1,col1,row1,col2) && canArrived(data,row1,col2,row2,col2) && data[row1][col2]==0){
            if (!isCheck){
                int x1 = 60*col1 + 20;
                int y1 = 60*row1 + 70;
                int x2 = 60*col2 + 20;
                int y2 = 60*row1 + 70;
                int x3 = 60*col2 + 20;
                int y3 = 60*row2 + 70;
                g = this.getGraphics();
                Graphics2D g2 = (Graphics2D)g;
                g2.setStroke(new BasicStroke(3.0f));
                g2.setColor(Color.RED);
                g2.drawLine(x1,y1,x2,y2);
                g2.drawLine(x2,y2,x3,y3);
            }

            return true;
        }
        if(canArrived(data,row1,col1,row2,col1) && canArrived(data,row2,col1,row2,col2)&& data[row2][col1]==0){
            if (!isCheck){
                int x1 = 60*col1 + 20;
                int y1 = 60*row1 + 70;
                int x2 = 60*col1 + 20;
                int y2 = 60*row2 + 70;
                int x3 = 60*col2 + 20;
                int y3 = 60*row2 + 70;
                g = this.getGraphics();
                Graphics2D g2 = (Graphics2D)g;
                g2.setStroke(new BasicStroke(3.0f));
                g2.setColor(Color.RED);
                g2.drawLine(x1,y1,x2,y2);
                g2.drawLine(x2,y2,x3,y3);
            }
            return true;
        }
        return false;
    }

    /**
     * 三条线相连
     * @param data
     * @param row1
     * @param col1
     * @param row2
     * @param col2
     * @return
     */
    private boolean connect2(int[][] data, int row1, int col1, int row2, int col2,boolean isCheck){

        for(int col_x = 0; col_x < col + 2; col_x++){
//			int index = row1*GameData.cols + col;
            if(canArrived(data,row1,col1,row1,col_x)&& data[row1][col_x]==0 &&connect1(data,row1,col_x,row2,col2,isCheck)){
                if (!isCheck){
                    int x1 = 60*col1 + 20;
                    int y1 = 60*row1 + 70;
                    int x2 = 60*col_x + 20;
                    int y2 = 60*row1 + 70;
                    g = this.getGraphics();
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setStroke(new BasicStroke(3.0f));
                    g2.setColor(Color.RED);
                    g2.drawLine(x1,y1,x2,y2);
                }
                return true;
            }
        }
        for(int row_x = 0; row_x < row+2; row_x++){
//			int index = row*GameData.cols + col1;
            if(canArrived(data,row1,col1,row_x,col1)&& data[row_x][col1]==0 &&connect1(data,row_x,col1,row2,col2,isCheck)){
                if (!isCheck){
                    int x1 = 60*col1 + 20;
                    int y1 = 60*row1 + 70;
                    int x2 = 60*col1 + 20;
                    int y2 = 60*row_x + 70;
                    g = this.getGraphics();
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setStroke(new BasicStroke(3.0f));
                    g2.setColor(Color.RED);
                    g2.drawLine(x1,y1,x2,y2);
                }
                return true;
            }
        }
        return false;
    }

    public boolean canArrived(int[][] data, int row1, int col1, int row2, int col2){
        int sum = 0;
       // System.out.println(row1 + " " + col1);
       // System.out.println(row2 + " " + col2);
        //同一行时，总是使第一个点击的按钮的纵坐标更小
        if (row1 == row2){
            if (col1>col2){
                int t = col1;
                col1 = col2;
                col2 = t;
            }

            for (int i = col1+1 ;i < col2;i++){
                sum += data[row1][i];
            }

            if (sum == 0){
                return true;

            }

        }

        if (col1 == col2){
            if (row1>row2){
                int t = row1;
                row1 = row2;
                row2 = t;
            }

            for (int i = row1+1 ; i < row2; i++) {
                sum+=data[i][col1];
            }

            if (sum == 0)
                return true;
        }

        return false;
    }

    private boolean HasMatching(){
        boolean isHas = false;
        for (int i = 1;i <= row; i++)
            for (int j = 1; j <= col ; j++) {
                if (data[i][j] == 0) continue;
                for (int k = 1; k <= row; k++)
                    for (int l = 1; l <= col; l++) {
                        if (i==k&&j==l) continue;
                        if (isConnect(data,i,j,k,l,true)){
                            isHas = true;
                            break;
                        }
                    }
            }
        return isHas;
    }

    /**
     * 西安本地文件中写入每局游戏的成绩
     * 记录作用
     * @param score 游戏分数
     */
    private void WriteData(int score){
        try{
            OutputStream outputStream = new FileOutputStream("record.txt",true);
            PrintWriter printWriter=new PrintWriter(outputStream);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            // new Date()为获取当前系统时间
            String s = score + "#" + df.format(new Date());
            printWriter.println(s);
            printWriter.close();
            outputStream.close();
        } catch (IOException e1){
            e1.printStackTrace();
        }
    }

}
