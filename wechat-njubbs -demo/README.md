# 微信小程序 南大小百合
功能基于南大小百合bbs，这个小程序相当于是个查询工具，信息都抓取于http://bbs.nju.edu.cn/


功能：1，浏览bbs十大和各区精华帖
     2，查看和搜索讨论区帖子
     3，查看个人bbs主页
     4，微信与小百合账号绑定
     5，评论和点赞

### 项目结构
<pre>
│  app.js                # 小程序登录
│  app.json              # 小程序公共设置（页面路径、窗口表现、设置网络超时时间、设置多tab）
│  app.wxss              # 小程序公共样式表
│  README.md             # 小程序项目说明
│  
├─image                  # 小程序图片资源
|
├─pages                  # 小程序文件
   ├─about               # 关于
│  ├─common              # 模板
│  ├─detail              # 帖子详情
│  ├─index               # 我的
│  ├─login               # 绑定小百合账号
|  ├─newpost             # 发帖
|  ├─personalInfo        # 个人主页
│  ├─switchzone          # 选择查找谈论区
│  ├─topics              # 主页面
│  └─zone                # 浏览特定版面
│          
└─utils                  # 小程序公用方法模块         
└─wxParse                # 富文本解析
└─wxSearch               # 搜索
└─data                   # 本地数据
</pre>


### 开发环境
下载地址 ：https://mp.weixin.qq.com/debug/wxadoc/dev/devtools/download.html?t=1474887501214





### 特别感谢
感谢 coolfish 的项目案例

coolfish的github: https://github.com/coolfishstudio
