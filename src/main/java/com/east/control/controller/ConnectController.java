package com.east.control.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.east.control.EastApplication;
import com.east.control.cons.EastCons;
import com.east.control.data.MyBatisPlusSqlSessionFactory;
import com.east.control.mapper.FlowItemMapper;
import com.east.control.mapper.FlowMapper;
import com.east.control.model.FlowInfo;
import com.east.control.model.FlowItem;
import com.east.control.utils.AdbAction;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ConnectController implements Initializable {


    public ChoiceBox<String> chbox;

    public String getPcid() {
        return pcid;
    }

    public void setPcid(String pcid) {
        this.pcid = pcid;
    }

    private String pcid;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private Label errmsg;
    @FXML
    public VBox rightCon;
    @FXML
    private VBox dynButton;
    @FXML
    private TableView<AdbAction.PhoneInfo> infoTableView;

    @FXML
    private TableColumn<AdbAction.PhoneInfo, String> deviceId;

    @FXML
    private TableColumn<AdbAction.PhoneInfo, String> name;

    @FXML
    private TableColumn<AdbAction.PhoneInfo, String> enname;

    @FXML
    private TableColumn<AdbAction.PhoneInfo, String> productType;

    @FXML
    private TableColumn<AdbAction.PhoneInfo, String> abilist32;

    @FXML
    private TableColumn<AdbAction.PhoneInfo, String> abilist64;

    private void init() {
        deviceId.setCellValueFactory(new PropertyValueFactory<>("deviceId")); // 绑定到 name
        name.setCellValueFactory(new PropertyValueFactory<>("name")); // 绑定到 enname
        enname.setCellValueFactory(new PropertyValueFactory<>("enname")); // 注意这里重复了，可以改为更有意义的列
        productType.setCellValueFactory(new PropertyValueFactory<>("productType")); // 绑定到 productType
        abilist32.setCellValueFactory(new PropertyValueFactory<>("abilist32")); // 绑定到 abilist32
        abilist64.setCellValueFactory(new PropertyValueFactory<>("abilist64")); // 绑定到 abilist64
    }

    Set<String> deviceIds = new HashSet<>();
    @FXML
    public void onConnClick(ActionEvent actionEvent) {
        AdbAction adbAction = new AdbAction();
        Map<String, String> device = adbAction.getDevice();
        Set<String> ids = device.keySet();
        deviceIds.addAll(ids);
        init();
        if (!ids.isEmpty()) {
            List<AdbAction.PhoneInfo> phoneInfo = adbAction.getPhoneInfo(ids);
            infoTableView.setItems(FXCollections.observableArrayList(phoneInfo));
        }
    }

    private Button createButton(String text, String id) {
        Button button = new Button(text);
        button.setPrefHeight(30.0);
        button.setPrefWidth(111.0);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: #101010;"); // 设置透明背景和黑色文字
        button.setId(id);
        button.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //被单机后展示label
                setPcid(id);
                refensh(id);
            }
        });
        return button;
    }

    private void refensh(String id) {
        rightCon.getChildren().clear();

        SqlSessionFactory sqlSessionFactory = MyBatisPlusSqlSessionFactory.getInstance();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            FlowItemMapper flowItemMapper = sqlSession.getMapper(FlowItemMapper.class);
            List<FlowItem> all = flowItemMapper.getOneById(id);
            for (FlowItem flow : all) {
                switch (flow.getAction_type()) {
                    case 1:
                        String name = "操作:" + EastCons.ACTION_MAPPING.get(flow.getAction_type());
                        rightCon.getChildren().add(createAnchorPaneWithLabelAndButton(name, flow.getId(), id));
                        break;
                    case 2:
                        String name1 = "操作:" + EastCons.ACTION_MAPPING.get(flow.getAction_type());
                        rightCon.getChildren().add(createAnchorPaneWithLabelAndButton(name1, flow.getId(), id));
                        break;
                    case 3:
                        String name2 = "操作:" + EastCons.ACTION_MAPPING.get(flow.getAction_type());
                        rightCon.getChildren().add(createAnchorPaneWithLabelAndButton(name2, flow.getId(), id));
                        break;
                    case 4:
                        String name3 = "操作:" + EastCons.ACTION_MAPPING.get(flow.getAction_type());
                        rightCon.getChildren().add(createAnchorPaneWithLabelAndButton(name3, flow.getId(), id));
                        break;
                    case 5:
                        String name4 = "操作:" + EastCons.ACTION_MAPPING.get(flow.getAction_type()) + " 输入内容:" + flow.getCont();
                        rightCon.getChildren().add(createAnchorPaneWithLabelAndButton(name4, flow.getId(), id));
                        break;
                    case 6:
                        String name5 = "操作:" + EastCons.ACTION_MAPPING.get(flow.getAction_type()) + " 单击坐标:(" + flow.getX() + "," + flow.getY() + ")";
                        System.out.println(name5);
                        rightCon.getChildren().add(createAnchorPaneWithLabelAndButton(name5, flow.getId(), id));
                        break;
                    default:
                        String name6 = "操作:" + EastCons.ACTION_MAPPING.get(flow.getAction_type());
                        rightCon.getChildren().add(createAnchorPaneWithLabelAndButton(name6, flow.getId(), id));
                        break;
                }
            }
        }
    }

    public void addFlowClick(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(EastApplication.class.getResource("flow-view.fxml"));
        try {
            Parent root = loader.load();

            // 创建场景
            Scene scene = new Scene(root, 618, 331);

            Stage popStage = new Stage();
            // 设置舞台标题和场景
            popStage.setTitle("添加流程");
            popStage.setScene(scene);

            // 设置 Modal 属性（可选）
            // 这样在弹出框关闭之前，用户无法与主窗口交互
            popStage.initModality(Modality.APPLICATION_MODAL);
            popStage.initOwner(stage);

            //传递给AddFlowController
            AddFlowController addFlowController = loader.getController();
            addFlowController.setStage(popStage);


            // 显示弹出框
            popStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public AnchorPane createAnchorPaneWithLabelAndButton(String text, String id, String pid) {
        // 创建 AnchorPane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(826.0, 39.0);

        // 创建 Label
        Label label = new Label(text);
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setLayoutX(67.0);
        label.setPrefSize(449.0, 34.0);

        // 创建 Button
        Button button = new Button("删除");
        button.setMnemonicParsing(false);
        button.setLayoutX(553.0);
        button.setLayoutY(8.0);
        button.setPrefSize(41.0, 22.0);

        button.setOnAction(actionEvent -> {
            SqlSessionFactory sqlSessionFactory = MyBatisPlusSqlSessionFactory.getInstance();
            try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
                FlowItemMapper flowItemMapper = sqlSession.getMapper(FlowItemMapper.class);
                flowItemMapper.delById(id);
                refensh(pid);
            }
        });

        // 将 Label 和 Button 添加到 AnchorPane 中
        anchorPane.getChildren().addAll(label, button);

        return anchorPane;
    }

    public void flowInit(Event event) {
        SqlSessionFactory sqlSessionFactory = MyBatisPlusSqlSessionFactory.getInstance();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            FlowMapper flowMapper = sqlSession.getMapper(FlowMapper.class);
            List<FlowInfo> all = flowMapper.getAll();
            dynButton.getChildren().clear();
            for (FlowInfo flowInfo : all) {
                dynButton.getChildren().add(createButton(flowInfo.getName(), flowInfo.getId()));
            }
        }
    }

    public void upclick(ActionEvent actionEvent) {
        if (StrUtil.isEmpty(getPcid())) {
            errmsg.setText("请先选择流程");
            return;
        }
        FlowItem flowItem = new FlowItem();
        flowItem.setFlow_id(getPcid());
        flowItem.setAction_type(1);
        flowItem.setId(IdUtil.getSnowflakeNextIdStr());
        flowItem.setCreate_time(DateUtil.now());
        SqlSessionFactory sqlSessionFactory = MyBatisPlusSqlSessionFactory.getInstance();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            FlowItemMapper flowItemMapper = sqlSession.getMapper(FlowItemMapper.class);
            flowItemMapper.insertFlow(flowItem);
        }
        refensh(getPcid());
        errmsg.setText("");
    }

    public void dclick(ActionEvent actionEvent) {
        if (StrUtil.isEmpty(getPcid())) {
            errmsg.setText("请先选择流程");
            return;
        }
        FlowItem flowItem = new FlowItem();
        flowItem.setFlow_id(getPcid());
        flowItem.setAction_type(2);
        flowItem.setId(IdUtil.getSnowflakeNextIdStr());
        flowItem.setCreate_time(DateUtil.now());
        SqlSessionFactory sqlSessionFactory = MyBatisPlusSqlSessionFactory.getInstance();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            FlowItemMapper flowItemMapper = sqlSession.getMapper(FlowItemMapper.class);
            flowItemMapper.insertFlow(flowItem);
        }
        refensh(getPcid());
        errmsg.setText("");
    }

    public void lpclick(ActionEvent actionEvent) {
        if (StrUtil.isEmpty(getPcid())) {
            errmsg.setText("请先选择流程");
            return;
        }
        FlowItem flowItem = new FlowItem();
        flowItem.setFlow_id(getPcid());
        flowItem.setAction_type(3);
        flowItem.setId(IdUtil.getSnowflakeNextIdStr());
        flowItem.setCreate_time(DateUtil.now());
        SqlSessionFactory sqlSessionFactory = MyBatisPlusSqlSessionFactory.getInstance();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            FlowItemMapper flowItemMapper = sqlSession.getMapper(FlowItemMapper.class);
            flowItemMapper.insertFlow(flowItem);
        }
        refensh(getPcid());
        errmsg.setText("");
    }

    public void rpclick(ActionEvent actionEvent) {
        if (StrUtil.isEmpty(getPcid())) {
            errmsg.setText("请先选择流程");
            return;
        }
        FlowItem flowItem = new FlowItem();
        flowItem.setFlow_id(getPcid());
        flowItem.setAction_type(4);
        flowItem.setId(IdUtil.getSnowflakeNextIdStr());
        flowItem.setCreate_time(DateUtil.now());
        SqlSessionFactory sqlSessionFactory = MyBatisPlusSqlSessionFactory.getInstance();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            FlowItemMapper flowItemMapper = sqlSession.getMapper(FlowItemMapper.class);
            flowItemMapper.insertFlow(flowItem);
        }

        refensh(getPcid());
        errmsg.setText("");
    }

    public void retclick(ActionEvent actionEvent) {
        if (StrUtil.isEmpty(getPcid())) {
            errmsg.setText("请先选择流程");
            return;
        }
        FlowItem flowItem = new FlowItem();
        flowItem.setFlow_id(getPcid());
        flowItem.setAction_type(7);
        flowItem.setId(IdUtil.getSnowflakeNextIdStr());
        flowItem.setCreate_time(DateUtil.now());
        SqlSessionFactory sqlSessionFactory = MyBatisPlusSqlSessionFactory.getInstance();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            FlowItemMapper flowItemMapper = sqlSession.getMapper(FlowItemMapper.class);
            flowItemMapper.insertFlow(flowItem);
        }

        refensh(getPcid());
        errmsg.setText("");
    }

    public void touchClick(ActionEvent actionEvent) {
        if (StrUtil.isEmpty(getPcid())) {
            errmsg.setText("请先选择流程");
            return;
        }
        FXMLLoader loader = new FXMLLoader(EastApplication.class.getResource("clic-view.fxml"));
        try {
            Parent root = loader.load();

            // 创建场景
            Scene scene = new Scene(root, 392, 212);

            Stage popStage = new Stage();
            // 设置舞台标题和场景
            popStage.setTitle("添加点击事件");
            popStage.setScene(scene);

            // 设置 Modal 属性（可选）
            // 这样在弹出框关闭之前，用户无法与主窗口交互
            popStage.initModality(Modality.APPLICATION_MODAL);
            popStage.initOwner(stage);

            //传递给AddFlowController
            TouchController touchController = loader.getController();
            touchController.setStage(popStage);
            touchController.setRightCon(rightCon);
            touchController.setPcid(getPcid());
            // 显示弹出框
            popStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        errmsg.setText("");

    }

    @FXML
    public void inclick(ActionEvent actionEvent) {
        if (StrUtil.isEmpty(getPcid())) {
            errmsg.setText("请先选择流程");
            return;
        }
        FXMLLoader loader = new FXMLLoader(EastApplication.class.getResource("cons-view.fxml"));
        try {
            Parent root = loader.load();

            // 创建场景
            Scene scene = new Scene(root, 392, 212);

            Stage popStage = new Stage();
            // 设置舞台标题和场景
            popStage.setTitle("添加输入事件");
            popStage.setScene(scene);

            // 设置 Modal 属性（可选）
            // 这样在弹出框关闭之前，用户无法与主窗口交互
            popStage.initModality(Modality.APPLICATION_MODAL);
            popStage.initOwner(stage);

            //传递给AddFlowController
            InputController inputController = loader.getController();
            inputController.setStage(popStage);
            inputController.setRightCon(rightCon);
            inputController.setPcid(getPcid());
            // 显示弹出框
            popStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        errmsg.setText("");

    }

    public void startCon(ActionEvent actionEvent) {
        SqlSessionFactory sqlSessionFactory = MyBatisPlusSqlSessionFactory.getInstance();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            FlowItemMapper mapper = sqlSession.getMapper(FlowItemMapper.class);
            List<FlowItem> oneById = mapper.getOneById(flowMap.get(current_flow));
            if (!deviceIds.isEmpty() && !oneById.isEmpty()){
                for (FlowItem flowItem : oneById) {
                    AdbAction adbAction = new AdbAction();
                    switch (flowItem.getAction_type()){
                        case 1:
                            adbAction.hpUp(deviceIds);
                            break;
                        case 2:
                            adbAction.hpDown(deviceIds);
                            break;
                        case 3:
                            adbAction.hpLeft(deviceIds);
                            break;
                        case 4:
                            adbAction.hpRight(deviceIds);
                            break;
                        case 5:
                            adbAction.inputText(deviceIds,flowItem.getCont());//输出
                            adbAction.sleep(1000);
                            break;
                        case 6:
                            adbAction.click(deviceIds,flowItem.getX(),flowItem.getY());
                            adbAction.sleep(1000);
                            break;
                        default:
                            adbAction.ret(deviceIds);
                            break;
                    }
                }
            }
        }
    }
    private String current_flow;
    private Map<String,String> flowMap = new HashMap<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chbox.getItems().clear();
        SqlSessionFactory sqlSessionFactory = MyBatisPlusSqlSessionFactory.getInstance();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            FlowMapper flowMapper = sqlSession.getMapper(FlowMapper.class);
            List<FlowInfo> all = flowMapper.getAll();
            flowMap = all.stream().collect(Collectors.toMap(FlowInfo::getName, FlowInfo::getId));
            List<String> collect = all.stream().map(FlowInfo::getName).toList();
            if (!collect.isEmpty()) {
                chbox.getItems().addAll(collect);
                chbox.setValue(collect.getFirst());
                if (current_flow==null){
                    current_flow = collect.getFirst();
                }
            }
        }
        chbox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                System.out.println("Selected value changed from " + s + " to " + t1);
                if (t1==null){
                    chbox.setValue(current_flow);
                }else{
                    current_flow = t1;
                }
            }
        });
    }

//    public void chboxClick(ActionEvent actionEvent) {
//        System.out.println(actionEvent.getEventType().getName());
//        current_flow = chbox.getValue();
//        System.out.println(current_flow);
//    }

    public void tabChange(Event event) {
        System.out.println("tabchange:====================>"+current_flow);
        chbox.getItems().clear();
        SqlSessionFactory sqlSessionFactory = MyBatisPlusSqlSessionFactory.getInstance();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            FlowMapper flowMapper = sqlSession.getMapper(FlowMapper.class);
            List<FlowInfo> all = flowMapper.getAll();
            flowMap = all.stream().collect(Collectors.toMap(FlowInfo::getName, FlowInfo::getId));
            List<String> collect = all.stream().map(FlowInfo::getName).toList();
            if (!collect.isEmpty()) {
                chbox.getItems().addAll(collect);
                chbox.setValue(current_flow);
            }
        }

    }

}
