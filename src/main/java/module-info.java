module com.east.control {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.controlsfx.controls;
    requires cn.hutool;
    requires static lombok;
    requires java.sql;
    requires com.zaxxer.hikari;
    requires org.mybatis;
    requires org.mybatis.spring;
    requires com.baomidou.mybatis.plus.core;
    requires com.baomidou.mybatis.plus.annotation;
    requires sqlite.jdbc;
    requires java.desktop;

    opens com.east.control to javafx.fxml;
    exports com.east.control;

    exports com.east.control.utils; // 导出需要访问的包
    opens com.east.control.utils to javafx.base;

    exports com.east.control.model; // 导出需要访问的包
    opens com.east.control.model to javafx.base;

    exports com.east.control.data; // 导出需要访问的包
    opens com.east.control.data to javafx.base;

    exports com.east.control.mapper; // 导出需要访问的包
    opens com.east.control.mapper to javafx.base;

    exports com.east.control.controller;
    opens com.east.control.controller to javafx.fxml; // 打开访问权限
}