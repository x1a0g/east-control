package com.east.control.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.east.control.data.MyBatisPlusSqlSessionFactory;
import com.east.control.mapper.FlowMapper;
import com.east.control.model.FlowInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class AddFlowController {



    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private TextField flowName;

    @FXML
    private TextField flowDesc;

    @FXML
    public Label errMsg;

    @FXML
    public void addClick(ActionEvent actionEvent) {
        String text = flowName.getText();
        String desc = flowDesc.getText();
        if (StrUtil.isEmpty(text)) {
            errMsg.setText("名称不允许为空");
            return;
        }

        SqlSessionFactory sqlSessionFactory = MyBatisPlusSqlSessionFactory.getInstance();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            FlowMapper flowMapper = sqlSession.getMapper(FlowMapper.class);
            FlowInfo flowInfo = new FlowInfo();
            flowInfo.setId(IdUtil.getSnowflakeNextIdStr());
            flowInfo.setName(text);
            flowInfo.setDesc(desc);
            flowInfo.setCreate_time(DateUtil.now());
            flowMapper.insertFlow(flowInfo);
        }
        stage.close();
    }
}
