// pages/newpost/newpost.js
var Api = require('../../utils/api.js');
var appInstance = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    positiontext: "点击获取位置",
    zoneName: '',
    title: '',
    content: '',
    images: [],
    disabled: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      title: 'Re:' + options.detail,
      zoneName: appInstance.boardName
    })
  },

  submitClick: function () {
    wx.showLoading({
      title: '发送中。。',
    })
    var that = this;
    var imageUrls = that.data.images;
    var webchatID = wx.getStorageSync('user').openid;

    // return;

    var title = that.data.title;
    var content = that.data.content;
    var zoneName = that.data.zoneName;
    // zoneName = 'test';
    // console.log(content);
    // console.log(title);
    // if (content == '') return;
    wx.showNavigationBarLoading() //在标题栏中显示加载

    if (imageUrls.length > 0) {
      that.uploadDIY(imageUrls, imageUrls.length, 0, 0, webchatID, zoneName, content);
    } else {
      var url = Api.Host + "board/" + that.data.zoneName + "/reply";
      var data = {

      };
      console.log(data);
      Api.submitComment(url, data, (err, res) => {
        wx.hideNavigationBarLoading() //完成停止加载
        setTimeout(function () {
          wx.hideLoading()
        }, 200)
        //更新数据
        if (res.result == "success") {
          wx.showToast({
            title: '发送成功',
            icon: 'success',
            duration: 1000,
            mask: true
          })
          var pages = getCurrentPages();

          pages[pages.length - 2].onPullDownRefresh();
          wx.navigateBack({})

        } else {
          wx.showToast({
            title: '发送失败',
            icon: 'loading',
            duration: 1000,
            mask: true
          })
        }


      })
    }

    // return;
    // zoneName = 'test';


  },

  /* 函数描述：作为上传文件时递归上传的函数体体；
     * 参数描述： 
     * filePaths是文件路径数组
     * successUp是成功上传的个数
     * failUp是上传失败的个数
     * i是文件路径数组的指标
     * length是文件路径数组的长度
     */
  uploadDIY(filePaths, urlLength, failUp, i, webchatID, boardName, content) {
    var that = this;
    //wx.showToast(0, "图片上传中...", 20000000, 1);
    var isuploaderror = false;
    wx.uploadFile({
      url: 'https://url/upload',
      filePath: filePaths[i],
      name: 'file',
      formData: {
        //提交formdata
      },
      success: (res) => {
        console.log(res);
        content = content + '\n' + res.data;
        //successUp++;
      },
      fail: (res) => {
        failUp++;
        isuploaderror = true;
      },
      complete: () => {
        i++;
        if (i == filePaths.length) {
          // wx.hideToast();
          // var txt = '总共' + successUp + '张上传成功,' + failUp + '张上传失败！';
          // wx.showToast(0, txt, 2000, 1);

          var title = that.data.title;
          //var content = that.data.content;
          var zoneName = boardName;
          var url = Api.Host + "board/" + boardName + "/reply";
          // var url = Api.Host + "board/" + zoneName;
          var data = {

          };
          console.log(data);
          Api.submitComment(url, data, (err, res) => {
            wx.hideNavigationBarLoading() //完成停止加载
            setTimeout(function () {
              wx.hideLoading()
            }, 200)
            //更新数据
            if (res.result == "success") {
              wx.showToast({
                title: '发送成功',
                icon: 'succes',
                duration: 1000,
                mask: true
              })
              var pages = getCurrentPages();

              pages[pages.length - 2].onPullDownRefresh();
              wx.navigateBack({})

            } else {
              wx.showToast({
                title: '发送失败',
                icon: 'loading',
                duration: 1000,
                mask: true
              })
            }


          })

        } else {  //递归调用uploadDIY函数
          if (isuploaderror) {
            wx.showToast(0, '图片上传失败，请重新选择上传', 2000, 1);
          } else {
            that.uploadDIY(filePaths, urlLength, failUp, i, webchatID, boardName, content);
          }
        }
      }
    });
  },


  bindTextAreaBlur: function (e) {
    this.data.content = e.detail.value;
    // this.setData({ disabled: e.detail.value === '' });
    // console.log(e.detail.value);
  },


  imgClick: function (e) {
    var that = this;
    wx.chooseImage({
      count: 9, // 默认9
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        // var tempFilePaths = ;
        var objArr = that.data.images
        for (var i = 0; i < res.tempFilePaths.length; i++) {
          objArr.push(res.tempFilePaths[i]);
        };
        // var tempFilePaths = res.tempFilePaths

        that.setData({ images: objArr });
      }
    })
  },
  /**
      * 查看大图
      * @param {String} cur 当前展示图片
      * @param {Array}  imageList 展示的图片列表
      */
  previewImage: function (e) {
    wx.previewImage({
      current: e.currentTarget.dataset.url, // 当前显示图片的http链接
      urls: e.currentTarget.dataset.urls // 需要预览的图片http链接列表
    })
  },


  /**
   * 删除选中图片
   * @param {Number} idx 要删除的图片索引
   */
  deleteImage: function deleteImage(idx) {
    var objArr = this.data.images;
    objArr.splice(idx, 1);
    this.setData({ images: objArr });
  },

})