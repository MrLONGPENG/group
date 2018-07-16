package com.mujugroup.wx.service.impl;

import com.mujugroup.wx.mapper.WxImagesMapper;
import com.mujugroup.wx.model.WxImages;
import com.mujugroup.wx.service.WxImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service("wxImagesService")
public class WxImagesServiceImpl implements WxImagesService {



    private final WxImagesMapper wxImagesMapper;

    @Autowired
    public WxImagesServiceImpl(WxImagesMapper wxImagesMapper) {
        this.wxImagesMapper = wxImagesMapper;
    }

    @Override
    public void insertRepairImage(Integer id, String images) {
        WxImages wxImages = new WxImages();
        wxImages.setPid(id);
        wxImages.setImageUrl(images);
        wxImages.setCrtTime(new Date());
        wxImages.setType(WxImages.TYPE_REPAIR);
        wxImagesMapper.insert(wxImages);
    }
}
