var groupMethod = require('../../utils/group.js');


Page({
  data: {
    searchLetter: [],
    showLetter: "",
    winHeight: 0,
    tHeight: 0,
    bHeight: 0,
    startPageY: 0,
    zoneList: [],
    isShowLetter: false,
    scrollTop: 0,
    zone: "",
    allzoneList: []
  },
  onLoad: function (options) {
    // 生命周期函数--监听页面加载
    var searchLetter = groupMethod.searchLetter;
    var zoneList = groupMethod.zoneList();
    // console.log(zoneInfo);

    var sysInfo = wx.getSystemInfoSync();
    console.log(sysInfo);
    var winHeight = sysInfo.windowHeight;

    //添加要匹配的字母范围值
    //1、更加屏幕高度设置子元素的高度
    var itemH = winHeight / searchLetter.length;
    var tempObj = [];
    for (var i = 0; i < searchLetter.length; i++) {
      var temp = {};
      temp.name = searchLetter[i];
      temp.tHeight = i * itemH;
      temp.bHeight = (i + 1) * itemH;

      tempObj.push(temp)
    }

    this.setData({
      winHeight: winHeight,
      itemH: itemH,
      searchLetter: tempObj,
      zoneList: zoneList,
      allzoneList: zoneList
    })

    console.log(this.data.zoneInfo);
  },

  wxSearchInput: function (e) {
    var that = this
    //WxSearch.wxSearchInput(e, that);
    var searchWord = e.detail.value;
    var tempObj = [];
    var zoneList = that.data.allzoneList;
    for (var i = 0; i < zoneList.length; i++) {
      var zoneInfo = [];
      var tempArr = {};
      tempArr.initial = zoneList[i].initial;
      for (var j = 0; j < zoneList[i].zoneInfo.length; j++) {
        var index = zoneList[i].zoneInfo[j].id.indexOf(searchWord);
        if (zoneList[i].zoneInfo[j].id.toLowerCase().indexOf(searchWord.toLowerCase()) > -1 || zoneList[i].zoneInfo[j].zone.toLowerCase().indexOf(searchWord.toLowerCase()) > -1) {
          zoneInfo.push(zoneList[i].zoneInfo[j]);
        }
      }
      tempArr.zoneInfo = zoneInfo;
      tempObj.push(tempArr);
    }
    that.setData({
      zoneList: tempObj
    })
  },
  bindzone: function (e) {
    var zoneId = e.currentTarget.dataset.id;
    var pages = getCurrentPages();
    var currPage = pages[pages.length - 1];   //当前页面
    var prevPage = pages[pages.length - 2];  //上一个页面

    prevPage.setData({
      zoneName: zoneId
    })
    wx.navigateBack({
    })

  }
})