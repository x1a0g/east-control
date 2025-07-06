package com.east.control.utils;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.log.StaticLog;
import com.east.control.cons.EastCons;
import lombok.Data;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AdbAction {

    public Map<String,String> getDevice(){
        try {
            List<String> res = RuntimeUtil.execForLines(EastCons.GET_DEVICE);
            res.removeFirst();
            res.removeLast();
            Map<String, String> collect = res.stream().collect(Collectors.toMap(item -> item.split("\t")[0], item -> item.split("\t")[1]));
            return collect;
        } catch (IORuntimeException e) {
            return null;
        }
    }
    private String buildShell(String id,String cons){
        StringBuilder sb = new StringBuilder(EastCons.INIT);
        sb.append(id);
        sb.append(cons);
        return sb.toString();
    }
    public String buildSc(String id){
        StringBuilder sb = new StringBuilder(EastCons.INIT);
        sb.append(id);
        sb.append(EastCons.SC);
        sb.append(id);
        sb.append(".png");
        return sb.toString();
    }
    public String buildPullSc(String id,String path){
        StringBuilder sb = new StringBuilder(EastCons.INIT);
        sb.append(id);
        sb.append(EastCons.PULL_SC);
        sb.append(id);
        sb.append(".png ");
        sb.append(path);
        sb.append(id);
        sb.append(".png ");
        return sb.toString();
    }

    private String buildShellInput(String id,String cons){
        StringBuilder sb = new StringBuilder(EastCons.INIT);
        sb.append(id);
        sb.append(EastCons.INPUT_TEXT);
        sb.append("\"");
        sb.append(cons);
        sb.append("\"");
        return sb.toString();
    }
    public AdbAction sleep(long x){
        try {
            Thread.sleep(x);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    public AdbAction lockPhone(Set<String> ids){
        for (String id : ids) {
            try {
                RuntimeUtil.execForStr(buildShell(id,EastCons.LOCK_PHONE));
            } catch (IORuntimeException e) {
                StaticLog.error("关闭锁屏失败：设备唯一编号:[{}]",id);
            }
        }
        return this;
    }

    public AdbAction unlockPhone(Set<String> ids){
        for (String id : ids) {
            try {
                RuntimeUtil.execForStr(buildShell(id,EastCons.UNLOCK_PHONE));
            } catch (IORuntimeException e) {
                StaticLog.error("开启锁屏失败：设备唯一编号:[{}]",id);
            }
        }
        return this;
    }

    public AdbAction hpUp(Set<String> ids){
        for (String id : ids) {
            try {
                RuntimeUtil.execForStr(buildShell(id,EastCons.HP_UP));
            } catch (IORuntimeException e) {
                StaticLog.error("上滑失败：设备唯一编号:[{}]",id);
            }
        }
        return this;
    }
    public AdbAction hpDown(Set<String> ids){
        for (String id : ids) {
            try {
                RuntimeUtil.execForStr(buildShell(id,EastCons.HP_DOWN));
            } catch (IORuntimeException e) {
                StaticLog.error("下滑失败：设备唯一编号:[{}]",id);
            }
        }
        return this;
    }
    public AdbAction hpLeft(Set<String> ids){
        for (String id : ids) {
            try {
                RuntimeUtil.execForStr(buildShell(id,EastCons.HP_LEFT));
            } catch (IORuntimeException e) {
                StaticLog.error("左滑失败：设备唯一编号:[{}]",id);
            }
        }
        return this;
    }
    public AdbAction hpRight(Set<String> ids){
        for (String id : ids) {
            try {
                RuntimeUtil.execForStr(buildShell(id,EastCons.HP_RIGHT));
            } catch (IORuntimeException e) {
                StaticLog.error("右滑失败：设备唯一编号:[{}]",id);
            }
        }
        return this;
    }

    public AdbAction ret(Set<String> ids){
        for (String id : ids) {
            try {
                RuntimeUtil.execForStr(buildShell(id,EastCons.RETURN));
            } catch (IORuntimeException e) {
                StaticLog.error("返回失败：设备唯一编号:[{}]",id);
            }
        }
        return this;
    }


    public AdbAction inputText(Set<String> ids, String text){
        for (String id : ids) {
            try {
                String s = buildShellInput(id, text);
                System.out.println(s);
                RuntimeUtil.execForStr(s);
            } catch (IORuntimeException e) {
                StaticLog.error("文本输入失败：设备唯一编号:[{}]",id);
            }
        }
        return this;
    }

    public AdbAction click(Set<String> ids,int x,int y){
        for (String id : ids) {
            try {
                StringBuilder sb = new StringBuilder(EastCons.INIT);
                sb.append(id);
                sb.append(EastCons.CLICK);
                sb.append(" ");
                sb.append(x);
                sb.append(" ");
                sb.append(y);
                RuntimeUtil.execForStr(sb.toString());
            } catch (IORuntimeException e) {
                StaticLog.error("返回失败：设备唯一编号:[{}]",id);
            }
        }
        return this;
    }

    public List<PhoneInfo> getPhoneInfo(Set<String> ids){
        List<PhoneInfo> res = new ArrayList<>();
        for (String id : ids) {
            List<String> result = RuntimeUtil.execForLines(StandardCharsets.UTF_8,buildShell(id, EastCons.INFO));
            PhoneInfo phoneInfo = new PhoneInfo();
            phoneInfo.setDeviceId(id);
            for (String s : result) {
                if (s.contains("ro.vendor.oplus.market.name")){
                    String[] split = s.split(": ");
                    String name = split[1].replace("[", "").replace("]", "");
                    phoneInfo.setName(name);
                } else if (s.contains("ro.vendor.oplus.market.enname")) {
                    String[] split = s.split(": ");
                    String name = split[1].replace("[", "").replace("]", "");
                    phoneInfo.setEnname(name);
                }else if (s.contains("ro.build.product")) {
                    String[] split = s.split(": ");
                    String name = split[1].replace("[", "").replace("]", "");
                    phoneInfo.setProductType(name);
                }else if (s.contains("ro.vendor.product.cpu.abilist32")) {
                    String[] split = s.split(": ");
                    String name = split[1].replace("[", "").replace("]", "");
                    phoneInfo.setAbilist32(name);
                }else if (s.contains("ro.vendor.product.cpu.abilist64")) {
                    String[] split = s.split(": ");
                    String name = split[1].replace("[", "").replace("]", "");
                    phoneInfo.setAbilist64(name);
                }
            }
            res.add(phoneInfo);
        }
        return res;
    }


    public static class PhoneInfo{
        private String name;//ro.vendor.oplus.market.name
        private String enname;//ro.vendor.oplus.market.enname]
        private String productType;//[ro.build.product]
        /**
         * [ro.vendor.product.cpu.abilist32]: [armeabi-v7a,armeabi]
         * [ro.vendor.product.cpu.abilist64]: [arm64-v8a]
         */
        private String abilist32;
        private String abilist64;
        private String deviceId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEnname() {
            return enname;
        }

        public void setEnname(String enname) {
            this.enname = enname;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getAbilist32() {
            return abilist32;
        }

        public void setAbilist32(String abilist32) {
            this.abilist32 = abilist32;
        }

        public String getAbilist64() {
            return abilist64;
        }

        public void setAbilist64(String abilist64) {
            this.abilist64 = abilist64;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }
    }

    public static void main(String[] args) {
        AdbAction adbAction = new AdbAction();
        System.out.println(adbAction.buildPullSc("123","/image/"));
    }

}
