package com.zone.quartz_module.pojo;

import com.zone.quartz_module.param.DeviceParam;

import java.util.List;

public class Devices {
    private List<ModuleResponseDataBean> devices;
    private List<DeviceParam> deviceParams;

    public List<ModuleResponseDataBean> getDevices() {
        return devices;
    }

    public void setDevices(List<ModuleResponseDataBean> devices) {
        this.devices = devices;
    }

    public List<DeviceParam> getDeviceParams() {
        return deviceParams;
    }

    public void setDeviceParams(List<DeviceParam> deviceParams) {
        this.deviceParams = deviceParams;
    }
}
