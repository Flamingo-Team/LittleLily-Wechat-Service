//about.js
Page({
  data: {
  },
  onLoad: function(){

  },
  previewImage: function (e) {
    wx.previewImage({
      current: '/images/icon/wechat.png', // 当前显示图片的http链接
      urls: ['']// 需要预览的图片http链接列表
    })
  },
});
