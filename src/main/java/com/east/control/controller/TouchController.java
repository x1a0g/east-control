package com.east.control.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.east.control.cons.EastCons;
import com.east.control.data.MyBatisPlusSqlSessionFactory;
import com.east.control.mapper.FlowItemMapper;
import com.east.control.mapper.FlowMapper;
import com.east.control.model.FlowItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class TouchController {
    @FXML
    public TextField xary;
    @FXML
    public TextField yary;
    @FXML
    public Label errmsg;
    public void setRightCon(VBox rightCon) {
        this.rightCon = rightCon;
    }

    public VBox rightCon;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private String pcid;

    public void setPcid(String pcid) {
        this.pcid = pcid;
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

    private void refensh(String id) {
        rightCon.getChildren().clear();

        SqlSessionFactory sqlSessionFactory = MyBatisPlusSqlSessionFactory.getInstance();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            FlowItemMapper flowItemMapper = sqlSession.getMapper(FlowItemMapper.class);
            List<FlowItem> all = flowItemMapper.getOneById(id);
            for (FlowItem flow : all) {
                switch (flow.getAction_type()){
                    case 1:
                        String name = "操作:"+ EastCons.ACTION_MAPPING.get(flow.getAction_type());
                        rightCon.getChildren().add(createAnchorPaneWithLabelAndButton(name,flow.getId(),id));
                        break;
                    case 2:
                        String name1 = "操作:"+EastCons.ACTION_MAPPING.get(flow.getAction_type());
                        rightCon.getChildren().add(createAnchorPaneWithLabelAndButton(name1,flow.getId(),id));
                        break;
                    case 3:
                        String name2 = "操作:"+EastCons.ACTION_MAPPING.get(flow.getAction_type());
                        rightCon.getChildren().add(createAnchorPaneWithLabelAndButton(name2,flow.getId(),id));
                        break;
                    case 4:
                        String name3 = "操作:"+EastCons.ACTION_MAPPING.get(flow.getAction_type());
                        rightCon.getChildren().add(createAnchorPaneWithLabelAndButton(name3,flow.getId(),id));
                        break;
                    case 5:
                        String name4 = "操作:"+EastCons.ACTION_MAPPING.get(flow.getAction_type())+" 输入内容:"+flow.getCont();
                        rightCon.getChildren().add(createAnchorPaneWithLabelAndButton(name4,flow.getId(),id));
                        break;
                    case 6:
                        String name5 = "操作:"+EastCons.ACTION_MAPPING.get(flow.getAction_type())+" 单击坐标:("+flow.getX()+","+flow.getY()+")";
                        System.out.println(name5);
                        rightCon.getChildren().add(createAnchorPaneWithLabelAndButton(name5,flow.getId(),id));
                        break;
                    default:
                        String name6 = "操作:"+EastCons.ACTION_MAPPING.get(flow.getAction_type());
                        rightCon.getChildren().add(createAnchorPaneWithLabelAndButton(name6,flow.getId(),id));
                        break;
                }
            }
        }
    }
    @FXML
    public void save(ActionEvent actionEvent) {

        if (xary.getText().length() <= 0 || yary.getText().length() <= 0) {
            errmsg.setText("x坐标和y坐标不允许为空");
            return;
        }
        SqlSessionFactory sqlSessionFactory = MyBatisPlusSqlSessionFactory.getInstance();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            FlowItemMapper flowItemMapper = sqlSession.getMapper(FlowItemMapper.class);
            FlowItem flowItem = new FlowItem();
            flowItem.setFlow_id(pcid);
            flowItem.setId(IdUtil.getSnowflakeNextIdStr());
            flowItem.setCreate_time(DateUtil.now());
            flowItem.setX(Integer.parseInt(xary.getText()));
            flowItem.setY(Integer.parseInt(yary.getText()));
            flowItem.setAction_type(6);
            flowItemMapper.insertFlow(flowItem);
        }catch (Exception e){
            errmsg.setText("参数有误");
            return;
        }
        errmsg.setText("");
        stage.close();

        refensh(pcid);

    }
}
